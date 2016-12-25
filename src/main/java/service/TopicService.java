package service;

import dao.NodeDAO;
import dao.TopicDAO;
import dao.UserDAO;
import entity.Node;
import entity.Topic;
import entity.User;
import exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2016/12/21 0021.
 */
public class TopicService {

    private static UserDAO userDAO = new UserDAO();
    private static TopicDAO topicDAO = new TopicDAO();
    private static NodeDAO nodeDAO = new NodeDAO();
    private static Logger logger = LoggerFactory.getLogger(TopicService.class);

    public Integer saveTopic(User user, String title, String content, Integer nodeId) throws ServiceException {
        Integer topicId;
        if(user == null) {
            throw new ServiceException("请登录后发帖");
        } else {
            Integer userId = user.getId();
            //封装Topic
            Topic topic;
            if(util.StringUtils.isEmpty(content)) {
                content = title;
                logger.debug("content为null时，content值为：{}",content);
            }
            topic = new Topic(title, content, userId, nodeId);
            //设置最后回复时间为帖子创建时间
            topic.setLastReplyTime(new Timestamp(new DateTime().getMillis()));
            //首先根据nodeId查找node,将node的帖子数加一后存入数据库
            Node node = nodeDAO.findNodeById(nodeId);
            node.setTopicNum(node.getTopicNum()+1);
            nodeDAO.update(node);
            //将帖子存入数据库
            topicId = topicDAO.save(topic);
            logger.debug("{}发表了id为{}的帖子",user.getUsername(),topicId);
        }
        return topicId;
    }
    public Topic findTopicById(String topicId) throws ServiceException{
        if(StringUtils.isNumeric(topicId)) {
            Topic topic = topicDAO.findById(Integer.valueOf(topicId));
            if(topic == null) {
                throw new ServiceException("该帖不存在或已被删除");
            } else {
                return topic;
            }
        } else {
            throw new ServiceException("该帖不存在或已被删除");
        }
    }

    /**
     * 根据帖子ID查找帖子
     * @param topicId
     * @return
     * @throws ServiceException
     */
    public Topic findTopicById(String topicId,Integer curr_userId) throws ServiceException{
        Topic topic = findTopicById(topicId);
        Integer userId = topic.getUserId();
        //如果查看帖子的用户不是帖子的作者，帖子点击量加一
        if(curr_userId != null && userId != null){
            if(!curr_userId.equals(userId)) {
                topic.setClickNum(topic.getClickNum() + 1);
                topicDAO.updateTopic(topic);
            }
        }
        return topic;
    }

    /**
     * 根据帖子id,找用户名
     * @param topicId
     * @return String 用户名
     */
    public User findUserNameByTopicId(String topicId) {
        if(StringUtils.isNumeric(topicId)) {
            Topic topic = findTopicById(topicId);
            if(topic != null) {
                return userDAO.findById(topic.getUserId());
            } else {
                throw new ServiceException("该帖不存在或已被删除");
            }
        } else {
            throw new ServiceException("该帖不存在或已被删除");
        }

    }

    /**
     * 根据帖子Id找node
     * @param topicId
     * @return
     */
    public Node findNodeByTopicId(String topicId) throws ServiceException{
        if(StringUtils.isNotEmpty(topicId)) {
            //根据topicId找到topic
            Topic topic = findTopicById(topicId);
            if (topic != null) {
                //根据topic对应的nodeId找node
                Node node = nodeDAO.findNodeById(topic.getNodeId());
                if (node != null) {
                    return node;
                } else {
                    throw new ServiceException("该帖不存在或已被删除");
                }
            } else {
                throw new ServiceException("该帖不存在或已被删除");
            }
        } else {
            throw new ServiceException("该帖不存在或已被删除");
        }
    }

}
