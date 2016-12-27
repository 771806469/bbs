package dao;

import entity.Topic;
import entity.User;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.handlers.AbstractListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.DBHelp;
import util.Page;
import util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Administrator on 2016/12/21 0021.
 */
public class TopicDAO {

    private static Logger logger = LoggerFactory.getLogger(TopicDAO.class);
    /**
     * @param topic 要存入数据库的帖子
     * @return 返回被存入帖子自动生成的id
     */
    public Integer save(Topic topic) {
        String sql = "insert into t_topic(title,content,userid,nodeid,lastreplytime) values(?,?,?,?,?)";
        logger.debug("存入数据库中的帖子内容为：{}",topic.getContent());
        return DBHelp.insert(sql,topic.getTitle(),topic.getContent(),topic.getUserId(),topic.getNodeId(),topic.getLastReplyTime());
    }

    public Topic findById(Integer topicId) {
        String sql = "select id,title,content,createtime,clicknum,favnum,thanksnum,replynum,lastreplytime,userid,nodeid from t_topic where id=?";
        return DBHelp.query(sql,new BeanHandler<>(Topic.class),topicId);
    }

    public void updateTopic(Topic topic) {
        String sql = "update t_topic set title = ? ,content = ? ,clicknum = ?,favnum = ?,thanksnum = ?,replynum = ?,lastreplytime = ?, nodeid = ?,userid = ? where id = ?";
        DBHelp.update(sql,topic.getTitle(),topic.getContent(),topic.getClickNum(),topic.getFavNum(),topic.getThanksNum(),topic.getReplyNum(),topic.getLastReplyTime(),topic.getNodeId(),topic.getUserId(),topic.getId());

    }

    public int count() {
        String sql = "select count(*) from t_topic";
        return DBHelp.query(sql,new ScalarHandler<Long>()).intValue();
    }

    public int count(Integer nodeId) {
        String sql = "select count(*) from t_topic where nodeId = ?";
        return DBHelp.query(sql,new ScalarHandler<Long>(),nodeId).intValue();
    }

    public List<Topic> findTopicPage(HashMap<String, Object> map) {
        String sql = "select tu.avatar,tu.username,tt.* from t_topic tt,user tu where tt.userid = tu.id";
        String and = "";

        String nodeId = map.get("nodeId") == null ? null : String.valueOf(map.get("nodeId"));

        List<Object> array = new ArrayList<>();
        if(StringUtils.isNotEmpty(nodeId)) {
            and += " and nodeid=?";
            array.add(nodeId);
        }
        and += " order by tt.lastreplytime desc limit ?,?";
        array.add(map.get("start"));
        array.add(map.get("pageSize"));
        sql += and;

        return DBHelp.query(sql,new AbstractListHandler<Topic>() {

            @Override
            protected Topic handleRow(ResultSet rs) throws SQLException {
                Topic topic = new BasicRowProcessor().toBean(rs,Topic.class);
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setAvatar(rs.getString("avatar"));
                topic.setUser(user);
                return topic;
            }
        },array.toArray());

    }
}
