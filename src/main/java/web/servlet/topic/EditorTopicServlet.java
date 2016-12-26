package web.servlet.topic;

import dto.JsonResult;
import entity.Node;
import entity.Topic;
import exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.NodeService;
import service.TopicService;
import util.BaseServlet;
import util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/editortopic")
public class EditorTopicServlet extends BaseServlet {

    private static TopicService topicService = new TopicService();
    private static Logger logger = LoggerFactory.getLogger(EditorTopicServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicId = req.getParameter("topicId");
        if(!StringUtils.isNumeric(topicId)) {
            req.setAttribute("error","该帖不存在或已被删除！");
            forward("/topic/topicerror",req,resp);
        } else {
            Topic topic = topicService.findTopicById(topicId);

            List<Node> nodeList = new NodeService().findAllNode();
            req.setAttribute("topic",topic);
            req.setAttribute("nodeList",nodeList);
            forward("/topic/topicedit",req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicId = req.getParameter("topicId");
        String nodeId = req.getParameter("node");
        String title = req.getParameter("title");
        String content = req.getParameter("content");

        JsonResult result = new JsonResult();

        if(StringUtils.isNumeric(topicId)) {
            try {
                topicService.update(title, content, nodeId, topicId);
                result.setState(JsonResult.SUCCESS);
                logger.debug("json中传入的帖子ID为：{}",topicId);
                result.setData(topicId);
            } catch(ServiceException ex) {
                result.setState(JsonResult.ERROR);
                result.setMessage(ex.getMessage());
            }
            renderJson(resp,result);
        } else {
            logger.debug("传入EditServlet中的帖子id为空或不是数字！{}",topicId);
            req.setAttribute("error","该贴子不存在或已被删除！");
            forward("/topic/topicerror",req,resp);
        }
    }
}
