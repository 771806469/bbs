package service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import dao.LoginLogDAO;
import dao.UserDAO;
import entity.LoginLog;
import entity.User;
import exception.ServiceException;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Config;
import util.EmailUtil;
import util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class UserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);
    private UserDAO userDAO = new UserDAO();
    private static LoginLogDAO loginLogDAO = new LoginLogDAO();
    /**
     * 激活账号cache
     */
    private static LoadingCache<String, String> cache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(5, TimeUnit.HOURS)
            .build(new CacheLoader<String, String>() {

                @Override
                public String load(String s) {
                    return null;
                }
            });
    /**
     * 限制找回密码操作点击次数cache
     */
    private static LoadingCache<String, String> timesCache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(60, TimeUnit.SECONDS)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String s) throws Exception {
                    return null;
                }
            });
    /**
     * 找回密码cache
     */
    private static LoadingCache<String, String> passwordCache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .build(new CacheLoader<String, String>() {

                @Override
                public String load(String s) {
                    return null;
                }
            });


    /**
     * 根据token激活对应的账号
     *
     * @param token
     * @return true表示已激活，false表示未激活
     */
    public void activeUser(String token) throws ServiceException {
        //根据放入缓存中token的键找值
        String username = cache.getUnchecked(token);
        if (username != null) {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.findByUsername(username);
            if (user != null) {
                user.setState(User.USERSTATE_ACTIVE);
                userDAO.update(user);
                //账号激活成功后删除token
                cache.invalidate(token);
            } else {
                throw new ServiceException("无法根据token值找到账户");
            }
        } else {
            throw new ServiceException("此token值不存在或已过期");
        }
    }

    /**
     * @param username
     * @return true  账号未被占用
     */
    public Boolean findUsername(String username) {

        String name = Config.get("no.signup.username");
        logger.debug(name);

        List<String> listUsername = Arrays.asList(name.split(","));

        if (listUsername.contains(username)) {
            return false;
        } else {
            return userDAO.findByUsername(username) == null;
        }
    }

    /**
     * @param email
     * @return true 邮箱未被占用
     */
    public Boolean findEmail(String email) {

        return userDAO.findByEmail(email) == null;
    }

    /**
     * 将用户注册信息存入数据库
     *
     * @param username
     * @param password
     * @param email
     * @param phone
     */
    public void save(String username, String password, String email, String phone) {
        User user = new User(username, DigestUtils.md5Hex(Config.get("singup.password.salt") + password), email, phone, User.USERSTATE_UNACTIVE, User.DEFAULT_AVATAR_NAME);
        logger.debug("注册用户存储的默认头像地址为：{}{}",Config.get("qiniu.domain"),User.DEFAULT_AVATAR_NAME);

        userDAO.save(user);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String uuid = UUID.randomUUID().toString();
                String url = "http://bbs.kaishengit.com/activeUser?_=" + uuid;
                String html = "<h3>Dear" + username + "</h3>请点击" + "<a href='" + url + "'>此链接</a>激活你的账号。<br>凯胜软件";
                //将token和用户名放入缓存
                cache.put(uuid, username);
                EmailUtil.sendHtmlEmail(email, "激活账号", html);
                logger.debug("激活账号的地址wei{}",url);
            }
        });

        thread.start();
    }

    public User login(String username, String password, String ip) {

        User user = userDAO.findByUsername(username);
        password = DigestUtils.md5Hex(Config.get("singup.password.salt") + password);
        if (user != null && password.equals(user.getPassword())) {
            if (user.getState().equals(User.USERSTATE_ACTIVE)) {
                //记录登录日志
                LoginLog log = new LoginLog();
                log.setLoginIp(ip);
                log.setUserId(user.getId());
                loginLogDAO.saveLog(log);

                logger.info("{}登录了系统,ip为：{}", username, ip);
                return user;
            } else if(user.getState().equals(User.USERSTATE_DISABLED)){
                logger.error("{}尝试登录系统但是账号被禁用", username);
                throw new ServiceException("该账户已被禁用！");
            } else {
                throw new ServiceException("该账户未激活！");
            }
        } else {
            logger.error("{}尝试登录系统但是账号与密码不匹配", username);
            throw new ServiceException("账号和密码错误，请重试");
        }

    }

    /**
     * 判断是否发送找回密码邮件
     *
     * @param type      找回密码方式
     * @param value     电子邮件或手机号码
     * @param sessionId
     * @throws ServiceException
     */
    public void findPassword(String type, String value, String sessionId) throws ServiceException {
        if (timesCache.getIfPresent(sessionId) == null) {
            if ("email".equals(type)) {
                User user = userDAO.findByEmail(value);
                if (user != null) {

                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String uuid = UUID.randomUUID().toString();

                            logger.trace("产生的UUID值为：{}",uuid);
                            //将uuid，和用户账号放入缓存
                            passwordCache.put(uuid, user.getUsername());
                            //设置找回密码连接
                            String url = "http://bbs.kaishengit.com/resetpassword?_=" + uuid;
                            logger.trace("找回密码连接为：{}",url);
                            String html = user.getUsername() + "您好：</br>请点击该<a href='" + url + "'>链接</a>进行找回密码操作，此链接30分钟内有效！";

                            EmailUtil.sendHtmlEmail(value, "找回密码邮件", html);
                        }
                    });
                    thread.start();

                } else {
                    throw new ServiceException("此邮箱不存在！");
                }
            }
            timesCache.put(sessionId, "xxx");
        } else {
            throw new ServiceException("操作频率过快请稍候再试！");
        }
    }

    /**
     * 当用户点击找回密码连接时，根据链接上的token值找相应存在cache中的账号
     * @param token
     * @return
     */
    public User findPasswordByToken(String token) {
       String username = passwordCache.getUnchecked(token);
       if(StringUtils.isNotEmpty(username)) {
           User user = userDAO.findByUsername(username);
           if(user == null) {
               throw new ServiceException("账号不存在");
           } else {
               return user;
           }
       } else {
           throw new ServiceException("token错误或已过期");
       }
    }

    /**
     * @param token 点击重置密码连接的token
     * @param username 重置密码的用户名
     * @param newpassword 新密码
     */
    public void resetPassword(String token, String username, String newpassword) {
        String tokenUsername = passwordCache.getIfPresent(token);
        if(tokenUsername != null) {
            User user = userDAO.findByUsername(username);
            if(user != null) {
                user.setPassword(DigestUtils.md5Hex(Config.get("singup.password.salt") + newpassword));
                userDAO.update(user);
                logger.info("{}重置了密码",tokenUsername);
                //删除passwordCache缓存中的token
                passwordCache.invalidate("uuid");
                //删除timesCache缓存中的sessionID
                timesCache.invalidate("sessionId");
            } else {
                throw new ServiceException("此帐号不存在！");
            }
        } else {
            throw new ServiceException("验证时间已过期！");
        }
    }

    /**
     * 修改用户的电子邮件
     * @param user 需修改用户
     * @param email 新Email
     */
    public void updateEmail(User user, String email) {
        if(!email.equals(user.getEmail())) {
                user.setEmail(email);
                userDAO.update(user);
        }
    }

    /**
     * 修改用户密码
     * @param user 需修改用户
     * @param newpassword 新密码
     * @param oldpassword 旧密码
     */
    public void updatePassword(User user, String oldpassword,String newpassword) {
        logger.trace("接收的新密码：{}，旧密码：{}",newpassword,oldpassword);
        String md5OldPassword = DigestUtils.md5Hex(Config.get("singup.password.salt") + oldpassword);
        logger.trace("user.getPassword():{},md5OldPassword:{}",user.getPassword(),md5OldPassword);
        if(md5OldPassword.equals(user.getPassword())) {
            user.setPassword(DigestUtils.md5Hex(Config.get("singup.password.salt") + newpassword));
            userDAO.update(user);
        } else {
            throw new ServiceException("原始密码错误");
        }


    }

    /**
     * 修改用户头像
     * @param user
     * @param avatarKey 更改后的头像KEY
     */
    public void updateAvatar(User user, String avatarKey) {
        if(user != null && !avatarKey.equals(user.getAvatar())) {
            user.setAvatar(avatarKey);
            userDAO.update(user);
        }
    }

    public void updateState(Integer userId, Integer userState) {
        User user = userDAO.findById(userId);
        if(user != null) {
            if (userState == 1) {
                user.setState(User.USERSTATE_DISABLED);
            }else if(userState == 2){
                user.setState(User.USERSTATE_ACTIVE);
            }
            userDAO.update(user);
        } else {
            throw new ServiceException("该用户不存在！");
        }
    }
}


