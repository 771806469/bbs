package dao;

import entity.Reply;
import util.DBHelp;

/**
 * Created by Administrator on 2016/12/23 0023.
 */
public class ReplyDAO {

    public void addReply(Reply reply) {
        String sql = "insert into t_reply(content,topicid,userid) values(?,?,?)";
        DBHelp.update(sql,reply.getContent(),reply.getTopicId(),reply.getUserId());
    }

}
