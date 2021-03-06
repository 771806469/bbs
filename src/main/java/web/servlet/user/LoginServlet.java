package web.servlet.user;

import dto.JsonResult;
import entity.User;
import exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import util.BaseServlet;
import util.Config;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends BaseServlet{

    private static Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.removeAttribute("curr_user");

        forward("user/login",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        logger.trace("登录者的账号为{}",username);

        //获取登录者IP
        String ip = req.getRemoteAddr();
        UserService userService = new UserService();
        JsonResult result = new JsonResult();

        try {
            User user = userService.login(username,password,ip);
            HttpSession session = req.getSession();
            session.setAttribute("curr_user",user);
            logger.debug("用户的头像域名为：{}{}", Config.get("qiniu.domain"),user.getAvatar());

            result.setState(JsonResult.SUCCESS);
        } catch(ServiceException ex) {
            logger.error("{}" + ex.getMessage() + "ip为{}",username,ip);
            result.setState(JsonResult.ERROR);
            result.setMessage(ex.getMessage());
        }
        renderJson(resp,result);

    }
}
