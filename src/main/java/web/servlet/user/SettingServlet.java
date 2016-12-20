package web.servlet.user;

import com.qiniu.util.Auth;
import dto.JsonResult;
import entity.User;
import exception.ServiceException;
import service.UserService;
import util.BaseServlet;
import util.Config;

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
        Auth auth = Auth.create(Config.get("ak"),Config.get("sk"));

        String token = auth.uploadToken(Config.get("bucket"));

        req.setAttribute("token",token);
        forward("user/setting", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("email".equals(action)) {
            updateEmail(req, resp);
        } else if("password".equals(action)) {
            updatePassword(req,resp);
        } else if("avatar".equals(action)) {
            updateAvatar(req,resp);
        }
    }

    private void updateAvatar(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String avatarKey = req.getParameter("fileKey");
        User user = (User)getSessionValue(req,"curr_user");
        JsonResult result = new JsonResult();

        userService.updateAvatar(user,avatarKey);
        result.setState(JsonResult.SUCCESS);

        renderJson(resp,result);
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
