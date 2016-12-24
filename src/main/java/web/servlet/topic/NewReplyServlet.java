package web.servlet.topic;

import entity.User;
import exception.ServiceException;
import service.ReplyService;
import util.BaseServlet;
import util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/reply")
public class NewReplyServlet extends BaseServlet {

    private static ReplyService replyService = new ReplyService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicId = req.getParameter("topicId");
        String content = req.getParameter("content");
        User user = (User)getSessionValue(req,"curr_user");
        //判断topicId是否为空
        if(StringUtils.isEmpty(topicId)) {
            req.setAttribute("error","该帖不存在或已被删除！");
            forward("topic/topicerror",req,resp);
        }else {
            if (StringUtils.isNotEmpty(content)) {
                try {
                    replyService.addReply(topicId, content, user);
                    resp.sendRedirect("/topicdetail?topicId=" + topicId);
                } catch (ServiceException ex) {
                    req.setAttribute("error", ex.getMessage());
                    forward("topic/topicerror",req,resp);
                }
            } else {
                req.setAttribute("error", "回复不能为空！");
                forward("topic/topicdetail?topicId=" + topicId,req,resp);
            }
        }










    }
}
