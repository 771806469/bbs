package web.servlet.validate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import util.BaseServlet;
import util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/validate/username")
public class ValidateUsernameServlet extends BaseServlet {

    private static Logger logger = LoggerFactory.getLogger(ValidateUsernameServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = StringUtils.isoToUtf8(req.getParameter("username"));
        UserService userService = new UserService();
        Boolean result = userService.findUsername(username);
        if(result) {
            renderText(resp,"true");
        } else {
            renderText(resp,"false");
        }
    }
}
