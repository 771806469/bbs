package service;

import dao.NodeDAO;
import dao.TopicDAO;
import entity.Topic;
import entity.User;
import exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2016/12/21 0021.
 */
public class TopicService {

    private TopicDAO topicDAO = new TopicDAO();
    private NodeDAO nodeDAO = new NodeDAO();
    private Logger logger = LoggerFactory.getLogger(TopicService.class);

    public void saveTopic(User user, String title, String content, Integer nodeId) {
        if(user == null) {
            throw new ServiceException("请登录后发帖");
        } else {
            Integer userId = user.getId();
            //封装Topic
            Topic topic;
            if(content == null) {
                topic = new Topic(title,title,userId,nodeId);
            } else {
                topic = new Topic(title, content, userId, nodeId);
            }
            nodeDAO.updateById(nodeId);
            Integer topicId = topicDAO.save(topic);
            logger.debug("{}发表了id为{}的帖子",user.getUsername(),topicId);
        }
    }

}
