package web.servlet.user;

import dto.JsonResult;
import exception.ServiceException;
import service.NotifyService;
import util.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2016/12/28 0028.
 */
@WebServlet("/notifyread")
public class NotifyUpdateServlet extends BaseServlet {

    private static NotifyService notifyService = new NotifyService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ids = req.getParameter("ids");
        JsonResult result = new JsonResult();
        try {
            notifyService.updateNotifyByIds(ids);
            result.setState(JsonResult.SUCCESS);
        } catch (ServiceException e) {
            //TODO idError的 处理
            e.printStackTrace();
        }
        renderJson(resp,result);
    }
}
