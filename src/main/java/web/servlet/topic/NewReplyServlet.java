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
            HttpSession session = req.getSession();
            session.setAttribute("error","该帖不存在或已被删除！");
            resp.sendRedirect("/WEB-INF/views/topic/topicerror.jsp");
        }

        if(StringUtils.isNotEmpty(content)) {
            try {
                replyService.addReply(topicId, content, user);
                resp.sendRedirect("/topicdetail?topicId=" + topicId);
            } catch(ServiceException ex) {
                HttpSession session = req.getSession();
                session.setAttribute("error",ex.getMessage());
                resp.sendRedirect("/WEB-INF/views/topic/topicdetail.jsp?error=1001");
            }
        } else {
            HttpSession session = req.getSession();
            session.setAttribute("error","回复不能为空！");
            resp.sendRedirect("/WEB-INF/views/topic/topicerror.jsp");
        }










    }
}
