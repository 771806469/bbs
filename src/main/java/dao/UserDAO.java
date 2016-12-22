package dao;

import entity.User;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.DBHelp;



public class UserDAO {

    private static Logger logger = LoggerFactory.getLogger(UserDAO.class);

    public User findByUsername(String username) {
        String sql = "select id,username,password,email,createTime,phone,state,avatar from user where username=?";
        return DBHelp.query(sql,new BeanHandler<>(User.class),username);
    }

    public User findByEmail(String email) {
        String sql = "select id,username,password,email,createTime,phone,state,avatar from user where email=?";
        return DBHelp.query(sql,new BeanHandler<User>(User.class),email);
    }

    public void save(User user) {
        String sql = "insert into user(username,password,email,phone,state,avatar) values(?,?,?,?,?,?)";
        DBHelp.update(sql,user.getUsername(),user.getPassword(),user.getEmail(),user.getPhone(),user.getState(),user.getAvatar());
        logger.trace("UserDAO存入的值{},{},{},{},{},{}",user.getUsername(),user.getPassword(),user.getEmail(),user.getPhone(),user.getState(),user.getAvatar());
        logger.debug(sql);
    }

    public void update(User user) {
        String sql = "update user set password=?,email=?,phone=?,state=?,avatar=? where id=?";
        DBHelp.update(sql,user.getPassword(),user.getEmail(),user.getPhone(),user.getState(),user.getAvatar(),user.getId());
        logger.debug("update的sql语句为：{}",sql);
    }

    public User findById(Integer userId) {
        String sql = "select id,username,password,email,createTime,phone,state,avatar from user where id=? ";
        return DBHelp.query(sql,new BeanHandler<>(User.class),userId);
    }
}
