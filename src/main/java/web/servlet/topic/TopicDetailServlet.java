package web.servlet.topic;

import entity.Node;
import entity.Reply;
import entity.Topic;
import entity.User;
import exception.ServiceException;
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
public class TopicDetailServlet extends BaseServlet{

    private static TopicService topicService = new TopicService();
    private static ReplyService replyService = new ReplyService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicId = req.getParameter("topicId");
        if(!StringUtils.isEmpty(topicId) && StringUtils.isNumeric(topicId)) {

            try {
                User curr_user = (User) getSessionValue(req, "curr_user");
                Node node = topicService.findNodeByTopicId(topicId);
                User user = topicService.findUserNameByTopicId(topicId);
                Topic topic;
                if (curr_user != null) {
                    topic = topicService.findTopicById(topicId, curr_user.getId());
                } else {
                    topic = topicService.findTopicById(topicId);
                }

                List<Reply> replyList = replyService.findListByTopicId(Integer.valueOf(topicId));

                req.setAttribute("replyList",replyList);
                req.setAttribute("nodeName", node.getNodeName());
                req.setAttribute("topic", topic);
                req.setAttribute("userAvatar", user.getAvatar());
                req.setAttribute("userName", user.getUsername());
            } catch (ServiceException ex) {
                req.setAttribute("message", ex.getMessage());
            }
            forward("topic/topicdetail", req, resp);
        } else {
            req.setAttribute("error","该帖子不存在或已被删除！");
            forward("topic/topicerror",req,resp);
        }

    }
}
