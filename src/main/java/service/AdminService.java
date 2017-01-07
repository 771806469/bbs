package service;

import dao.AdminDAO;
import dao.UserDAO;
import entity.Admin;
import entity.User;
import exception.ServiceException;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Config;
import util.Page;
import vo.UserVo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/29 0029.
 */
public class AdminService {

    private static AdminDAO adminDAO = new AdminDAO();
    private static UserDAO userDAO = new UserDAO();
    private static Logger logger = LoggerFactory.getLogger(AdminService.class);

    public Admin findAminName(String adminName) {
        return adminDAO.findAdminByAdminName(adminName);
    }

    public Admin login(String adminName, String password, String ip) throws ServiceException {

        Admin admin = adminDAO.findAdminByAdminName(adminName);
        password = DigestUtils.md5Hex(Config.get("singup.password.salt") + password);
        logger.debug("password -> ",password);
        if(admin != null && password.equals(admin.getPassword())) {
            logger.debug("{}登录了系统管理，ip为{}",admin.getAdminName(),ip);
            return admin;
        } else {
            throw new ServiceException("用户名密码不匹配！");
        }


    }

    public Page<UserVo> findUserVoList(Integer pageNum) {

        int count = userDAO.count();
        Page<UserVo> userVoPage = new Page<>(count,pageNum);
        List<User> userList = userDAO.findByPage(userVoPage);
        List<UserVo> userVoList = new ArrayList<>();
        for(User user : userList) {
            UserVo userVo = userDAO.findUserVo(user.getId());
            userVoList.add(userVo);
            logger.debug("userName -> {}",user.getUsername());
        }
        userVoPage.setPageList(userVoList);
        return userVoPage;

    }
}
