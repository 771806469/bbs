package dao;

import entity.Reply;
import entity.User;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.handlers.AbstractListHandler;
import util.Config;
import util.DBHelp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/12/23 0023.
 */
public class ReplyDAO {

    public void addReply(Reply reply) {
        String sql = "insert into t_reply(content,topicid,userid) values(?,?,?)";
        DBHelp.update(sql,reply.getContent(),reply.getTopicId(),reply.getUserId());
    }

    public List<Reply> findListByTopicId(Integer topicId) {
        String sql = "select tu.id,tu.username,tu.avatar,tr.* from t_reply tr,user tu where tr.userid = tu.id and topicid = ?";

        return DBHelp.query(sql,new AbstractListHandler<Reply>() {
            @Override
            protected Reply handleRow(ResultSet rs) throws SQLException {
                Reply reply = new BasicRowProcessor().toBean(rs,Reply.class);
                User user = new User();

                user.setId(rs.getInt("id"));
                user.setAvatar(Config.get("qiniu.domain") + rs.getString("avatar"));
                user.setUsername(rs.getString("username"));

                reply.setUser(user);

                return reply;
            }
        }, topicId);
    }

}
