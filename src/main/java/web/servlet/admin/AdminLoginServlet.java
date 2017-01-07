package web.servlet.admin;

import dto.JsonResult;
import entity.Admin;
import exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.AdminService;
import util.BaseServlet;
import util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Administrator on 2016/12/29 0029.
 */
@WebServlet("/admin/login")
public class AdminLoginServlet extends BaseServlet {

    private static AdminService adminService = new AdminService();
    private static Logger logger = LoggerFactory.getLogger(AdminLoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("curr_admin");

        forward("admin/login", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String adminName = req.getParameter("adminName");
        String password = req.getParameter("password");

        String ip = req.getRemoteAddr();
        JsonResult result = new JsonResult();


        try {
            Admin admin = adminService.login(adminName, password, ip);
            HttpSession session = req.getSession();
            session.setAttribute("curr_admin", admin);
            logger.debug("系统登录验证成功！");
            result.setState(JsonResult.SUCCESS);
        } catch (ServiceException e) {
            result.setState(JsonResult.ERROR);
            result.setMessage(e.getMessage());
        }
        renderJson(resp, result);
    }
}
