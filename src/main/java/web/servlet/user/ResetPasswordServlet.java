package web.servlet.user;

import dto.JsonResult;
import entity.User;
import exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import util.StringUtils;
import util.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/resetpassword")
public class ResetPasswordServlet extends BaseServlet {

    private static Logger logger = LoggerFactory.getLogger(ResetPasswordServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("_");
        if(StringUtils.isEmpty(token)) {
            resp.sendError(404);
        } else {
            UserService userService = new UserService();
            try {
                User user = userService.findPasswordByToken(token);
                //token 此时不能删除，否则不安全，用户重置密码是要根据此token
                req.setAttribute("user",user);
                req.setAttribute("token",token);
                logger.trace("重重置密码网址截取的token,{}",token);
                forward("user/resetpassword",req,resp);
            } catch(ServiceException ex) {
                req.setAttribute("message",ex.getMessage());
                forward("user/resetpsderror",req,resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String newpassword = req.getParameter("password");
        String token = req.getParameter("token");
        String username = req.getParameter("username");

        logger.trace("提交修改后的密码时的token:{}",token);
        UserService userService = new UserService();
        JsonResult jsonResult = new JsonResult();
        try {
            userService.resetPassword(token, username, newpassword);
            jsonResult.setState(JsonResult.SUCCESS);
        } catch(ServiceException ex) {
            jsonResult.setState(JsonResult.ERROR);
            jsonResult.setMessage(ex.getMessage());
            renderJson(resp,jsonResult);
        }
        renderJson(resp,jsonResult);
    }
}
