package web.servlet.admin;

import dto.JsonResult;
import exception.ServiceException;
import service.TopicService;
import util.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/1/3 0003.
 */
@WebServlet("/admin/topicupdate")
public class TopicUpdateServlet extends BaseServlet {

    private static TopicService topicService = new TopicService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nodeId = req.getParameter("nodeId");
        String id = req.getParameter("id");

        JsonResult rs = new JsonResult();
        try {
            topicService.updateNode(Integer.valueOf(nodeId), Integer.valueOf(id));
            rs.setState(JsonResult.SUCCESS);
        } catch (ServiceException ex){
            rs.setState(JsonResult.ERROR);
            rs.setMessage(ex.getMessage());
        }

        renderJson(resp,rs);
    }
}
