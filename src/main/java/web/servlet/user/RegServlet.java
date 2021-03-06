package web.servlet.user;

import dto.JsonResult;
import exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import util.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/reg")
public class RegServlet extends BaseServlet{

    private static Logger logger = LoggerFactory.getLogger(RegServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forward("user/reg",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        logger.trace("RegServlet接受username的值{}",username);

        JsonResult result = new JsonResult();

        try {
            UserService userService = new UserService();
            userService.save(username,password,email,phone);

            result.setState(JsonResult.SUCCESS);
        } catch(ServiceException ex) {
            ex.printStackTrace();
            result.setState(JsonResult.ERROR);
            result.setMessage("注册失败，请稍后再试！");
            logger.error("注册失败");
        }

        renderJson(resp,result);

    }
}
