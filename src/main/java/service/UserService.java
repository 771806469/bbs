package service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import dao.UserDAO;
import entity.User;
import exception.ServiceException;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Config;
import util.EmailUtil;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class UserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);
    private UserDAO userDAO = new UserDAO();
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
            if(user != null) {
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
                logger.debug(url);
            }
        });

        thread.start();
    }
}


