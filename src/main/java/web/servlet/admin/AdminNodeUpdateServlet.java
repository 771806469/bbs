package web.servlet.admin;

import com.sun.org.apache.xpath.internal.SourceTree;
import dto.JsonResult;
import entity.Node;
import exception.ServiceException;
import service.NodeService;
import util.BaseServlet;
import util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/nodeupdate")
public class AdminNodeUpdateServlet extends BaseServlet {

    private static NodeService nodeService = new NodeService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nodeId = req.getParameter("nodeId");
        if(StringUtils.isNumeric(nodeId)) {

            try {
                Node node = nodeService.findById(Integer.valueOf(nodeId));
                req.setAttribute("node",node);
                forward("admin/nodeUpdate",req,resp);
            }catch (ServiceException se) {
                req.setAttribute("error",se.getMessage());
                forward("topic/topicerror",req,resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nodeName = req.getParameter("nodenames");
        System.out.println("nodeName -> " + nodeName);
        String nodeId = req.getParameter("nodeId");

        System.out.println("nodeId -> " + nodeId);

        JsonResult rs = new JsonResult();
        try {
            nodeService.updateNode(Integer.valueOf(nodeId), nodeName);
            rs.setState(JsonResult.SUCCESS);
        }catch (ServiceException ex) {
            rs.setMessage(ex.getMessage());
            rs.setState(JsonResult.ERROR);
        }
        renderJson(resp,rs);
    }
}
