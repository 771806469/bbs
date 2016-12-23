package service;

import dao.ReplyDAO;
import dao.TopicDAO;
import entity.Reply;
import entity.Topic;
import entity.User;
import exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Administrator on 2016/12/23 0023.
 */
public class ReplyService {

    private static Logger logger = LoggerFactory.getLogger(ReplyService.class);
    private static ReplyDAO replyDAO = new ReplyDAO();

    public void addReply(String topicId,String content,User user) throws ServiceException{
        TopicDAO topicDAO = new TopicDAO();
        Topic topic = topicDAO.findById(Integer.valueOf(topicId));
        //判断帖子是否存在
        if(topic != null) {
            //增加回复到回复表
            Integer userId = user.getId();
            Reply reply = new Reply();
            reply.setContent(content);
            reply.setTopicId(Integer.valueOf(topicId));
            reply.setUserId(userId);
            replyDAO.addReply(reply);
            //更新topic表中对应的回复数量和最后回复时间
            topic.setReplyNum(topic.getReplyNum() + 1);
            topic.setLastReplyTime(new Timestamp(new Date().getTime()));
            topicDAO.updateTopic(topic);
        } else {
            throw new ServiceException("该帖不存在或已被删除！");
        }
    }


}
