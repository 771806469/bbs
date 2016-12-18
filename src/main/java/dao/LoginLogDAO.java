package dao;

import entity.LoginLog;
import util.DBHelp;

/**
 * Created by Administrator on 2016/12/15 0015.
 */
public class LoginLogDAO {

    public void saveLog(LoginLog loginLog) {
        String sql = "insert into t_login_log(loginip,userid) values(?,?)";
        DBHelp.update(sql,loginLog.getLoginIp(),loginLog.getUserId());
    }

}
