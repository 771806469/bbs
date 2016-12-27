package service;

import com.google.common.collect.Maps;
import dao.NodeDAO;
import dao.TopicDAO;
import dao.UserDAO;
import entity.Node;
import entity.Topic;
import entity.User;
import exception.ServiceException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Page;
import util.StringUtils;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

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
        if (user == null) {
            throw new ServiceException("请登录后发帖");
        } else {
            Integer userId = user.getId();
            //封装Topic
            Topic topic;
            if (util.StringUtils.isEmpty(content)) {
                content = title;
                logger.debug("content为null时，content值为：{}", content);
            }
            topic = new Topic(title, content, userId, nodeId);
            //设置最后回复时间为帖子创建时间
            topic.setLastReplyTime(new Timestamp(new DateTime().getMillis()));
            //首先根据nodeId查找node,将node的帖子数加一后存入数据库
            Node node = nodeDAO.findNodeById(nodeId);
            node.setTopicNum(node.getTopicNum() + 1);
            nodeDAO.update(node);
            //将帖子存入数据库
            topicId = topicDAO.save(topic);
            logger.debug("{}发表了id为{}的帖子", user.getUsername(), topicId);
        }
        return topicId;
    }

    public Topic findTopicById(String topicId) {
        Topic topic = topicDAO.findById(Integer.valueOf(topicId));
        if (topic == null) {
            throw new ServiceException("该帖不存在或已被删除");
        } else {
            //根据帖子Id找到对应的帖子node,user,并封装到topic中
            Node node = nodeDAO.findNodeById(Integer.valueOf(topic.getNodeId()));
            User user = userDAO.findByTopicId(Integer.valueOf(topic.getUserId()));
            topic.setNode(node);
            topic.setUser(user);
            return topic;
        }

    }

    /**
     * 根据帖子ID查找帖子
     *
     * @param topicId
     * @return
     * @throws ServiceException
     */
    public Topic findTopicById(String topicId, Integer curr_userId) {
        Topic topic = findTopicById(topicId);
        //发帖者ID
        Integer userId = topic.getUserId();
        //如果查看帖子的用户不是帖子的作者，帖子点击量加一
        if (curr_userId != null && userId != null) {
            if (!curr_userId.equals(userId)) {
                topic.setClickNum(topic.getClickNum() + 1);
                topicDAO.updateTopic(topic);
            }
        }
        return topic;
    }

    /**
     * 修改帖子
     */
    public void update(String title,String content,String nodeId,String topicId) throws ServiceException{
        Topic topic = findTopicById(topicId);
        if(topic != null && topic.isEditor()) {
            topic.setContent(content);
            topic.setTitle(title);
            topic.setNodeId(Integer.valueOf(nodeId));
            topicDAO.updateTopic(topic);
        } else {
            throw new ServiceException("此帖子不存在，或已不可修改！");
        }

    }

    public Page<Topic> findTopicByPage(String nodeId, Integer pageNum) {
        HashMap<String,Object> map = Maps.newHashMap();
        int count = 0;
        if(StringUtils.isEmpty(nodeId)) {
            count = topicDAO.count();
        } else {
            logger.debug("nodeId值为{}",nodeId);
            count = topicDAO.count(Integer.valueOf(nodeId));
        }
        logger.debug("分页所需总条数为：{}",count);
        Page<Topic> topicPage = new Page<>(count,pageNum);
        map.put("start",topicPage.getStart());
        map.put("nodeId",nodeId);
        map.put("pageSize", topicPage.getPageSize());

        List<Topic> topicList = topicDAO.findTopicPage(map);
        topicPage.setPageList(topicList);
        return topicPage;
    }

}
