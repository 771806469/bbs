package web.servlet.user;

import dto.JsonResult;
import exception.ServiceException;
import service.UserService;
import util.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/foundpassword")
public class FoundPasswordServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forward("user/foundpassword",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String value = req.getParameter("value");
        String type = req.getParameter("type");

        String sessionId = req.getSession().getId();

        UserService userService = new UserService();
        JsonResult result = new JsonResult();

        try{
            userService.findPassword(type,value,sessionId);

            result.setState(JsonResult.SUCCESS);
        } catch(ServiceException ex) {
            result.setState(JsonResult.ERROR);
            result.setMessage(ex.getMessage());
            renderJson(resp,result);
        }

        renderJson(resp,result);

    }
}
