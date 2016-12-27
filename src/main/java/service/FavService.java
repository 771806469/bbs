package service;

import dao.FavDAO;
import dao.TopicDAO;
import entity.Fav;
import entity.Topic;

import java.util.List;

/**
 * Created by Administrator on 2016/12/27 0027.
 */
public class FavService {

    private static FavDAO favDao = new FavDAO();
    private static TopicDAO topicDAO = new TopicDAO();

    public Fav findFavByUserIdAndTopicId(Integer userId,Integer topicId) {
        return favDao.findFavByUserIdAndTopicId(userId,topicId);
    }

    public void addFav(Integer userId, Integer topicId) {
        favDao.addFav(userId,topicId);
        Topic topic = topicDAO.findById(topicId);
        if(topic != null) {
            topic.setFavNum(topic.getFavNum() + 1);
            topicDAO.updateTopic(topic);
        }

    }

    public void unFav(Integer userId,Integer topicId) {
        favDao.unFav(userId,topicId);
        Topic topic = topicDAO.findById(topicId);
        if(topic != null) {
            topic.setFavNum(topic.getFavNum() - 1);
            topicDAO.updateTopic(topic);
        }
    }
}
