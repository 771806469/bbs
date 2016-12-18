package web.servlet.userState;

import exception.ServiceException;
import service.UserService;
import util.BaseServlet;
import util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/activeUser")
public class ActiveUserServlet extends BaseServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter("_");
        if(StringUtils.isEmpty(uuid)) {
            resp.sendError(404);
        } else {
            UserService userService = new UserService();
            try {
                userService.activeUser(uuid);
                forward("user/active_success",req,resp);
            } catch(ServiceException service) {
                req.setAttribute("message",service.getMessage());
                forward("user/active_error",req,resp);
            }

        }

    }
}
