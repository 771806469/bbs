package service;

import dao.LoginLogDAO;
import entity.LoginLog;


public class LoginLogService {

    private LoginLogDAO loginLogDAO = new LoginLogDAO();

    public void save(LoginLog loginLog) {
        loginLogDAO.saveLog(loginLog);
    }

}
