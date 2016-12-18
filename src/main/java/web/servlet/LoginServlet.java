package web.servlet;

import entity.User;
import exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import util.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/login")
public class LoginServlet extends BaseServlet{

    private static Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forward("user/login",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        logger.trace("登录者的账号为{}",username);
        resp.setContentType("application/json;charset=UTF-8");

        //获取登录者IP
        String ip = req.getRemoteAddr();
        UserService userService = new UserService();
        Map<String,Object> result = new HashMap<>();

        try {
            User user = userService.login(username,password,ip);
            HttpSession session = req.getSession();
            session.setAttribute("curr_user",user);

            result.put("state","success");
        } catch(ServiceException ex) {
            logger.error("{}" + ex.getMessage() + "ip为{}",username,ip);
            result.put("state","error");
            result.put("message",ex.getMessage());
        }
        renderJson(resp,result);

    }
}
