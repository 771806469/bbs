package dao;

import entity.Topic;
import util.DBHelp;

/**
 * Created by Administrator on 2016/12/21 0021.
 */
public class TopicDAO {

    public Integer save(Topic topic) {
        String sql = "insert into t_topic(title,content,userid,nodeid) values(?,?,?,?)";
        return DBHelp.insert(sql,topic.getTitle(),topic.getContent(),topic.getUserId(),topic.getNodeId());
    }
}
