package web.servlet.admin;

import dto.JsonResult;
import entity.Node;
import entity.Topic;
import exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@WebServlet("/admin/topic")
public class AdminTopicServlet extends BaseServlet {

    private static TopicService topicService = new TopicService();
    private static NodeService nodeService = new NodeService();
    private static Logger logger = LoggerFactory.getLogger(AdminTopicServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String p = req.getParameter("p");
        Integer pageNum = StringUtils.isNumeric(p) ? Integer.valueOf(p) : 1;

        Page<Topic> page = topicService.findTopicByPage("", pageNum);
        req.setAttribute("page", page);

        List<Node> nodeList = nodeService.findAllNode();
        req.setAttribute("nodeList", nodeList);
        forward("admin/topic", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        logger.debug("要删除的帖子Id是：{}", id);
        JsonResult js = new JsonResult();

        try {
            topicService.delTopicById(Integer.valueOf(id));
            js.setState(JsonResult.SUCCESS);
        } catch (ServiceException ex) {
            js.setState(JsonResult.ERROR);
            js.setMessage(ex.getMessage());
        }

        renderJson(resp,js);

    }
}
