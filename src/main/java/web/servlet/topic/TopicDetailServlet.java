package web.servlet.topic;

import entity.Node;
import entity.Topic;
import entity.User;
import exception.ServiceException;
import service.NodeService;
import service.TopicService;
import util.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/topicdetail")
public class TopicDetailServlet extends BaseServlet{

    private static TopicService topicService = new TopicService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicId = req.getParameter("topicId");

        try {
            Node node = topicService.findNodeByTopicId(topicId);
            User user = topicService.findUserNameByTopicId(topicId);
            Topic topic = topicService.findTopicById(topicId);
            req.setAttribute("nodeName",node.getNodeName());
            req.setAttribute("topic",topic);
            req.setAttribute("userAvatar",user.getAvatar());
            req.setAttribute("userName",user.getUsername());
        } catch (ServiceException ex) {
            req.setAttribute("message",ex.getMessage());
        }
        forward("topic/topicdetail",req,resp);

    }
}
