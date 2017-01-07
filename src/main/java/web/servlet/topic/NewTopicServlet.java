package web.servlet.topic;

import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import dto.JsonResult;
import entity.Node;
import entity.User;
import exception.ServiceException;
import service.NodeService;
import service.TopicService;
import util.BaseServlet;
import util.Config;

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
//        List<Node> nodeList = nodeService.findAllNode();
//        Auth auth = Auth.create(Config.get("ak"),Config.get("sk"));
//        StringMap stringMap = new StringMap();
//        stringMap.put("returnBody","{ \"success\": true,\"file_path\": \"" + Config.get("qiniu.domain") + "${key}\"}");
//        String token = auth.uploadToken(Config.get("bucket"),null,3600,stringMap);
//
//        req.setAttribute("token",token);
//        req.setAttribute("nodeList",nodeList);
//        forward("topic/newtopic",req,resp);
        Auth auth = Auth.create(Config.get("ak"),Config.get("sk"));
        StringMap stringMap = new StringMap();
        stringMap.put("returnBody","{ \"success\": true,\"file_path\": \""+Config.get("qiniu.domain")+"${key}\"}");
        String token = auth.uploadToken(Config.get("bucket"),null,3600,stringMap);

        //获取nodelist到jsp页面
        List<Node> nodelist = nodeService.findAllNode();
        req.setAttribute("nodeList",nodelist);
        req.setAttribute("token",token);
        forward("topic/newtopic",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        Integer nodeId = Integer.valueOf(req.getParameter("node"));

        User user = (User)getSessionValue(req,"curr_user");
        JsonResult result = new JsonResult();
        try {
            Integer topicId = topicService.saveTopic(user, title, content, nodeId);
            result.setState(JsonResult.SUCCESS);
            result.setData(topicId);
        } catch(ServiceException ex) {
            result.setState(JsonResult.ERROR);
            result.setMessage(ex.getMessage());
        }
       renderJson(resp,result);
    }
}
