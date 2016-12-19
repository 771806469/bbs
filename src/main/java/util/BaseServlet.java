package util;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class BaseServlet extends HttpServlet {

    public void forward(String path, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/" + path + ".jsp").forward(req, resp);
    }

    public void renderText(HttpServletResponse resq,String str) throws IOException {
        resq.setContentType("text/plain;charset=UTF-8");

        PrintWriter writer = resq.getWriter();
        writer.print(str);
        writer.flush();
        writer.close();
    }

    public Object getSessionValue(HttpServletRequest req,String sessionKey) {
        HttpSession session = req.getSession();
        Object obj = session.getAttribute(sessionKey);
        if(obj == null) {
            return null;
        }
        return obj;
    }

    public void renderJson(HttpServletResponse resp,Object obj) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");

        PrintWriter writer = resp.getWriter();
        String json = new Gson().toJson(obj);
        writer.print(json);
        writer.flush();
        writer.close();
    }
}
