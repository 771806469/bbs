package dao;

import entity.Fav;
import org.apache.commons.dbutils.handlers.BeanHandler;
import util.DBHelp;

/**
 * Created by Administrator on 2016/12/27 0027.
 */
public class FavDAO {

    public Fav findFavByUserIdAndTopicId(Integer userId, Integer topicId) {
        String sql = "select userid,topicid,createtime from t_fav where userid=? and topicid=?";
        return DBHelp.query(sql,new BeanHandler<>(Fav.class),userId,topicId);
    }

    public void addFav(Integer userId, Integer topicId) {
        String sql = "insert into t_fav(userid,topicid) values(?,?)";
        DBHelp.update(sql,userId,topicId);
    }

    public void unFav(Integer userId,Integer topicId) {
        String sql = "delete from t_fav where userid=? and topicid=?";
        DBHelp.update(sql,userId,topicId);
    }
}
