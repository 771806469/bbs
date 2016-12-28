package dao;

import entity.Notify;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import util.DBHelp;

import java.util.List;

/**
 * Created by Administrator on 2016/12/27 0027.
 */
public class NotifyDAO {

    public List<Notify> findListbyUserId(Integer userId) {
        String sql = "select id,userid,createtime,content,state,readtime from t_notify where userid = ?";
        return DBHelp.query(sql,new BeanListHandler<>(Notify.class),userId);
    }

    public void save(Notify notify) {
        String sql = "insert into t_notify(userid,content,state) values(?,?,?)";
        DBHelp.update(sql,notify.getUserId(),notify.getContent(),notify.getState());
    }

    public Notify findById(Integer id) {
        String sql = "select id,userid,createtime,content,state,readtime from t_notify where id = ?";
        return DBHelp.query(sql,new BeanHandler<>(Notify.class),id);
    }

    public void updateNotify(Notify notify) {
        String sql = "update t_notify set state = ?,readtime = ? where id = ?";
        DBHelp.update(sql,notify.getState(),notify.getReadTime(),notify.getId());
    }
}
