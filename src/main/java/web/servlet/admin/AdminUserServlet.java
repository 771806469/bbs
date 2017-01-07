package web.servlet.admin;

import dto.JsonResult;
import exception.ServiceException;
import service.AdminService;
import service.UserService;
import util.BaseServlet;
import util.Page;
import util.StringUtils;
import vo.UserVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2016/12/29 0029.
 */
@WebServlet("/admin/user")
public class AdminUserServlet extends BaseServlet {

    private static AdminService adminService = new AdminService();
    private static UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String p = req.getParameter("p");
        Integer pageNum = StringUtils.isNumeric(p) ? Integer.valueOf(p) : 1;

        Page<UserVo> userVoPage = adminService.findUserVoList(pageNum);

        req.setAttribute("userVoPage",userVoPage);
        forward("admin/user",req,resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        String userState = req.getParameter("userState");

        JsonResult js = new JsonResult();
        try {
            userService.updateState(Integer.valueOf(userId), Integer.valueOf(userState));
            js.setState(JsonResult.SUCCESS);
        }catch (ServiceException ex){
            js.setState(JsonResult.ERROR);
            js.setMessage(ex.getMessage());
        }
        renderJson(resp,js);
    }
}
