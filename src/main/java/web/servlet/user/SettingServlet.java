package web.servlet.user;

import dto.JsonResult;
import entity.User;
import exception.ServiceException;
import service.UserService;
import util.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/setting")
public class SettingServlet extends BaseServlet {

    private static UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forward("user/setting", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("email".equals(action)) {
            updateEmail(req, resp);
        } else if("password".equals(action)) {
            updatePassword(req,resp);
        }
    }

    private void updatePassword(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        String oldpassword = req.getParameter("oldpassword");
        String newpassword = req.getParameter("newpassword");
        User user = (User) getSessionValue(req,"curr_user");
        JsonResult result = new JsonResult();
        try {
            userService.updatePassword(user, oldpassword, newpassword);
            result.setState(JsonResult.SUCCESS);
        } catch(ServiceException ex){
            result.setState(JsonResult.ERROR);
            result.setMessage(ex.getMessage());
            renderJson(resp,result);
        }
        renderJson(resp,result);
    }

    private void updateEmail(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        User user = (User) getSessionValue(req, "curr_user");
        userService.updateEmail(user, email);

        JsonResult result = new JsonResult();


        result.setState(JsonResult.SUCCESS);
        renderJson(resp,result);


    }
}
