package web.servlet.admin;

import util.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Administrator on 2017/1/1 0001.
 */
@WebServlet("/admin/loginout")
public class AdminLogoutServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        session.removeAttribute("curr_admin");
        req.setAttribute("logout","您已安全退出！");

        forward("admin/login",req,resp);
    }
}
