package web.servlet.admin;

import entity.Node;
import service.NodeService;
import util.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/node")
public class AdminNodeServlet extends BaseServlet {

    private static NodeService nodeService = new NodeService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Node> nodeList = nodeService.findAllNode();
        req.setAttribute("nodeList",nodeList);
        forward("admin/node",req,resp);
    }
}
