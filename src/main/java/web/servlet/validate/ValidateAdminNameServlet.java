package web.servlet.validate;

import com.sun.org.apache.xpath.internal.SourceTree;
import entity.Admin;
import service.AdminService;
import util.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2016/12/29 0029.
 */
@WebServlet("/validate/adminname")
public class ValidateAdminNameServlet extends BaseServlet {

    private static AdminService adminService = new AdminService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String adminName = req.getParameter("adminName");

        Admin admin = adminService.findAminName(adminName);
        if(admin != null) {
            renderText(resp,"true");
        } else {
            renderText(resp,"false");
        }
    }
}
