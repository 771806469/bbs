package web.servlet.admin;

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
@WebServlet("/admin/nodeValidate")
public class NodeValidateServlet extends BaseServlet {

    private static NodeService nodeService = new NodeService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nodeId = req.getParameter("nodeId");
        String nodeName = StringUtils.isoToUtf8(req.getParameter("nodenames"));

        renderText(resp, nodeService.validateNodeName(Integer.valueOf(nodeId), nodeName));

    }
}
