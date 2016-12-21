package web.servlet.topic;

import entity.Node;
import entity.User;
import service.NodeService;
import service.TopicService;
import util.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/newtopic")
public class NewTopicServlet extends BaseServlet {

    private NodeService nodeService = new NodeService();
    private TopicService topicService = new TopicService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Node> nodeList = nodeService.findAllNode();

        req.setAttribute("nodeList",nodeList);
        forward("topic/newtopic",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        Integer nodeId = Integer.valueOf(req.getParameter("node"));

        User user = (User)getSessionValue(req,"curr_user");
        topicService.saveTopic(user,title,content,nodeId);
    }
}
