package web.servlet.topic;

import entity.Node;
import entity.Reply;
import entity.Topic;
import entity.User;
import exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.NodeService;
import service.ReplyService;
import service.TopicService;
import util.BaseServlet;
import util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/topicdetail")
public class TopicDetailServlet extends BaseServlet {

    private static TopicService topicService = new TopicService();
    private static ReplyService replyService = new ReplyService();
    private static Logger logger = LoggerFactory.getLogger(TopicDetailServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicId = req.getParameter("topicId");
        logger.debug("要查看的帖子id为{}",topicId);
        if (StringUtils.isNumeric(topicId)) {
            User curr_user = (User) getSessionValue(req, "curr_user");
            Topic topic = null;
            if (curr_user != null) {
                logger.debug("curr_user不为空，curr_user的ID为{}",curr_user.getId());
                topic = topicService.findTopicById(topicId, curr_user.getId());
            } else {
                logger.debug("curr_user为空");
                topic = topicService.findTopicById(topicId);
            }

            List<Reply> replyList = replyService.findListByTopicId(Integer.valueOf(topicId));

            req.setAttribute("replyList", replyList);
            req.setAttribute("topic", topic);
            forward("topic/topicdetail", req, resp);
        } else {
            req.setAttribute("error", "该帖子不存在或已被删除！");
            forward("topic/topicerror", req, resp);
        }

    }
}
