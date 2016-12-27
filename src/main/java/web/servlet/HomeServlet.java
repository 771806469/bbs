package web.servlet;

import entity.Node;
import entity.Topic;
import service.NodeService;
import service.TopicService;
import util.BaseServlet;
import util.Page;
import util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends BaseServlet {
    private static NodeService nodeSerivce = new NodeService();
    private static TopicService topicService = new TopicService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nodeId = req.getParameter("nodeId");
        String p = req.getParameter("p");
        Integer pageNum = StringUtils.isNumeric(p) ? Integer.valueOf(p) : 1;
        if(!StringUtils.isEmpty(nodeId) && !StringUtils.isNumeric(nodeId)){
            forward("/home",req,resp);
        }
        Page<Topic> page = topicService.findTopicByPage(nodeId,pageNum);
        List<Node> nodeList = nodeSerivce.findAllNode();
        req.setAttribute("page",page);
        req.setAttribute("nodeList",nodeList);
        forward("home",req,resp);
    }
}
