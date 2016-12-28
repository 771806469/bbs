package web.servlet.user;


import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import dto.JsonResult;
import entity.Notify;
import entity.User;
import exception.ServiceException;
import service.NotifyService;
import util.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/notify")
public class NotifyServlet extends BaseServlet{
    private static NotifyService notifyService = new NotifyService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) getSessionValue(req,"curr_user");
        if(user == null) {
            req.setAttribute("error","请登录后在查看消息");
            forward("topic/topicerror",req,resp);
            System.out.println("测试！");
        }
        List<Notify> notifyList = notifyService.findListbyUserId(user.getId());
        req.setAttribute("notifyList",notifyList);
        forward("/user/notify",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User)getSessionValue(req,"curr_user");
        List<Notify> notifyList;
        List<Notify> unreadList;
        JsonResult result = new JsonResult();
        try {
            notifyList = notifyService.findListbyUserId(user.getId());
            unreadList = Lists.newArrayList(Collections2.filter(notifyList,new Predicate<Notify>() {
                @Override
                public boolean apply(Notify notify) {
                    System.out.println(notify.getState() == 0 ? notify.getState() : "");
                    return notify.getState() == 0;
                }
            }));
            result.setState(JsonResult.SUCCESS);
            result.setData(unreadList.size());
        } catch(ServiceException e) {
            result.setState(JsonResult.ERROR);
            result.setMessage(e.getMessage());
        }

        renderJson(resp,result);

    }
}

