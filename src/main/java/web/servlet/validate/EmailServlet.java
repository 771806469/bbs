package web.servlet.validate;

import service.UserService;
import util.BaseServlet;
import util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/validate/email")
public class EmailServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = StringUtils.isoToUtf8(req.getParameter("email"));

        UserService userService = new UserService();
        Boolean result = userService.findEmail(email);
        if(result) {
            renderText(resp,"true");
        } else {
            renderText(resp,"false");
        }
    }
}
