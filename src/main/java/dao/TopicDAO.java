package dao;

import entity.Topic;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.DBHelp;



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
        String sql = "insert into t_topic(title,content,userid,nodeid) values(?,?,?,?)";
        logger.debug("存入数据库中的帖子内容为：{}",topic.getContent());
        return DBHelp.insert(sql,topic.getTitle(),topic.getContent(),topic.getUserId(),topic.getNodeId());
    }

    public Topic findById(Integer topicId) {
        String sql = "select id,title,content,createtime,clicknum,favnum,thanksnum,replynum,lastreplytime,userid,nodeid from t_topic where id=?";
        return DBHelp.query(sql,new BeanHandler<>(Topic.class),topicId);
    }
}
