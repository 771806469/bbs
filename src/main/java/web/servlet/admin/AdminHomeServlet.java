package web.servlet.admin;

import org.apache.commons.lang3.StringUtils;
import service.TopicService;
import util.BaseServlet;
import util.Page;
import vo.TopicReplyCount;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/admin/home")
public class AdminHomeServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String p = req.getParameter("p");
        Integer pageNum = StringUtils.isNumeric(p) ? Integer.valueOf(p) : 1;
        Page<TopicReplyCount> page = new TopicService().getTopicAndReplyNumByDayList(pageNum);
        req.setAttribute("page",page);
        forward("admin/home",req,resp);
    }
}
