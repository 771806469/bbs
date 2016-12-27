package web.servlet.topic;

import dto.JsonResult;
import entity.Topic;
import entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.FavService;
import service.TopicService;
import util.BaseServlet;
import util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/fav")
public class FavTopicServlet extends BaseServlet {

    private static FavService favService = new FavService();
    private static TopicService topicService = new TopicService();
    private static Logger logger = LoggerFactory.getLogger(FavTopicServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicId = req.getParameter("topicId");
        User curr_user = (User)getSessionValue(req,"curr_user");
        String fav = req.getParameter("fav");

        logger.debug("fav的值为：{}",fav);
        JsonResult result = new JsonResult();
        if(StringUtils.isNumeric(topicId) && !StringUtils.isEmpty(fav)) {
            if("加入收藏".equals(fav)) {
                favService.addFav(curr_user.getId(),Integer.valueOf(topicId));
            } else if("取消收藏".equals(fav)) {
                favService.unFav(curr_user.getId(),Integer.valueOf(topicId));
            }

            Topic topic = topicService.findTopicById(topicId);

            result.setState(JsonResult.SUCCESS);
            result.setData(topic.getFavNum());
        } else {
            result.setState(JsonResult.ERROR);
            result.setMessage("参数错误，操作失败！");
        }
        renderJson(resp,result);
    }
}
