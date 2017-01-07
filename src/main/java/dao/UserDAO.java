package dao;

import entity.User;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.DBHelp;
import util.Page;
import vo.UserVo;

import java.util.List;


public class UserDAO {

    private static Logger logger = LoggerFactory.getLogger(UserDAO.class);

    public User findByUsername(String username) {
        String sql = "select id,username,password,email,createTime,phone,state,avatar from user where username=?";
        return DBHelp.query(sql,new BeanHandler<>(User.class),username);
    }

    public User findByEmail(String email) {
        String sql = "select id,username,password,email,createTime,phone,state,avatar from user where email=?";
        return DBHelp.query(sql,new BeanHandler<>(User.class),email);
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

    public User findById(Integer id) {
        String sql = "select id,username,password,email,createTime,phone,state,avatar from user where id=?";
        return DBHelp.query(sql,new BeanHandler<>(User.class),id);
    }


    public Integer count() {
        String sql = "select count(*) from user order by id";
        return DBHelp.query(sql,new ScalarHandler<Long>()).intValue();
    }

    public List<User> findByPage(Page<UserVo> userVoPage) {
        String sql = "select * from user where state != 0 order by createtime limit ?,?";
        return DBHelp.query(sql, new BeanListHandler<>(User.class),userVoPage.getStart(),userVoPage.getPageSize());
    }

    public UserVo findUserVo(Integer id) {
        String sql = "select tll.logintime lastLoginTime,tll.loginip lastloginIP,tu.id id,tu.username username,tu.createtime ,tu.state state from t_login_log tll ,user tu where tu.id = tll.userid AND userid = ? order by logintime desc limit 0,1";
        return DBHelp.query(sql,new BeanHandler<>(UserVo.class),id);
    }
}
