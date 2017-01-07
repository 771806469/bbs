package web.servlet.admin;

import dto.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.NodeService;
import util.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/1/3 0003.
 */
@WebServlet("/admin/newnode")
public class NewNodeServlet extends BaseServlet {

    private static Logger logger = LoggerFactory.getLogger(NewNodeServlet.class);
    private static NodeService nodeService = new NodeService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forward("admin/newnode",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nodeName = req.getParameter("nodenames");
        logger.debug("获得的nodeName为{}",nodeName);
        nodeService.save(nodeName);
        JsonResult rs = new JsonResult();
        rs.setState(JsonResult.SUCCESS);
        renderJson(resp,rs);
    }
}
