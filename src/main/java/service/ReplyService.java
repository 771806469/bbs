package service;

import dao.NotifyDAO;
import dao.ReplyDAO;
import dao.TopicDAO;
import entity.Notify;
import entity.Reply;
import entity.Topic;
import entity.User;
import exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/12/23 0023.
 */
public class ReplyService {

    private static Logger logger = LoggerFactory.getLogger(ReplyService.class);
    private static ReplyDAO replyDAO = new ReplyDAO();
    private static NotifyDAO notifyDAO = new NotifyDAO();

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
            logger.debug("新增了回复之后，id为{}的帖子回复数量为：{}",topicId,topic.getReplyNum() + 1);
            topic.setLastReplyTime(new Timestamp(new Date().getTime()));
            topicDAO.updateTopic(topic);
            logger.debug("id为：{}的用户回复了id为{}的帖子",user.getId(),topicId);

            //如果回复帖子的用户不是帖子的作者则新建通知消息
            if(!user.getId().equals(topic.getUserId())) {
                Notify notify = new Notify();
                notify.setContent("您的主题为<a href=\"/topicdetail?topicId=" + topicId + "\">[" + topic.getTitle() + "]</a>的帖子有了新回复！");
                notify.setUserId(topic.getUserId());
                notify.setState(Notify.NOTIFY_STATE_UNREAD);
                notifyDAO.save(notify);
            }
        } else {
            throw new ServiceException("该帖不存在或已被删除！");
        }
    }

    public List<Reply> findListByTopicId(Integer topicId) {
        return replyDAO.findListByTopicId(topicId);
    }

}
