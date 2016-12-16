package service;

import dao.UserDAO;
import entity.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Config;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class UserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);
    private UserDAO userDAO = new UserDAO();

    /**
     *
     * @param username
     * @return true  账号未被占用
     */
    public Boolean findUsername(String username) {

        String name = Config.get("no.signup.username");
        logger.debug(name);

        List<String> listUsername = Arrays.asList(name.split(","));

       if(listUsername.contains(username)) {
           return false;
       } else {
           return userDAO.findByUsername(username) == null;
       }
    }

    /**
     *
     * @param email
     * @return true 邮箱未被占用
     */
    public Boolean findEmail(String email) {

        return userDAO.findByEmail(email) == null ;
    }

    public void save(String username, String password, String email, String phone) {
        User user = new User(username, DigestUtils.md5Hex(Config.get("singup.password.salt") + password),email,phone,User.USERSTATE_UNACTIVE,User.DEFAULT_AVATAR_NAME);

        userDAO.save(user);

        /*Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //给用户发送激活邮件
                String uuid = UUID.randomUUID().toString();
            }
        });*/
    }
}


