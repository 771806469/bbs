package dao;

import entity.Reply;
import entity.User;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.handlers.AbstractListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Config;
import util.DBHelp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/12/23 0023.
 */
public class ReplyDAO {

    private static Logger logger = LoggerFactory.getLogger(ReplyDAO.class);

    public void addReply(Reply reply) {
        String sql = "insert into t_reply(content,topicid,userid) values(?,?,?)";
        DBHelp.update(sql,reply.getContent(),reply.getTopicId(),reply.getUserId());
    }

    public List<Reply> findListByTopicId(Integer topicId) {
        String sql = "select tu.id,tu.username,tu.avatar,tr.* from t_reply tr,user tu where tr.userid = tu.id and topicid = ? order by tr.id asc";

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

    public void delListByTopicId(Integer id) {
        String sql = "delete from t_reply where topicid = ?";
        logger.debug("执行的SQL语句为：{},帖子ID为：{}",sql,id);
        DBHelp.update(sql,id);
    }
}
