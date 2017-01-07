package dao;

import entity.Admin;
import exception.DataAccessException;
import org.apache.commons.dbutils.handlers.BeanHandler;
import util.DBHelp;

/**
 * Created by Administrator on 2016/12/29 0029.
 */
public class AdminDAO {

    public Admin findAdminByAdminName(String adminName) {
        String sql = "select id,adminName,password,phone,email,createtime from t_admin where adminname = ?";
        return DBHelp.query(sql,new BeanHandler<>(Admin.class),adminName);
    }

}
