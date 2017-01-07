package web.servlet.admin;

import dto.JsonResult;
import exception.ServiceException;
import service.NodeService;
import util.BaseServlet;
import util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/1/2 0002.
 */
@WebServlet("/admin/nodeDel")
public class NodeDelServlet extends BaseServlet {
    private static NodeService nodeService = new NodeService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nodeId = req.getParameter("id");

        JsonResult rs = new JsonResult();
        if(StringUtils.isNumeric(nodeId)) {
            try {
                nodeService.delById(Integer.valueOf(nodeId));
                rs.setState(JsonResult.SUCCESS);
            } catch (ServiceException ex) {
                rs.setState(JsonResult.ERROR);
                rs.setMessage(ex.getMessage());
            }
        }

        renderJson(resp,rs);
    }
}
