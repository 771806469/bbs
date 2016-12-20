package web.filter;

import util.AbstractFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/12/20 0020.
 */
public class LoginFilter extends AbstractFilter {

    private  List<String> urlList = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String validateUrl = filterConfig.getInitParameter("validateUrl");
        urlList = Arrays.asList(validateUrl.split(","));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)servletRequest;
        HttpServletResponse resp = (HttpServletResponse)servletResponse;

        //获取用户要访问的url
        String requestUrl = req.getRequestURI();

        if(urlList != null && urlList.contains(requestUrl)) {
            if(req.getSession().getAttribute("curr_user") != null) {
                filterChain.doFilter(req,resp);
            } else {
                req.getRequestDispatcher("/login?redirect=" + requestUrl);
            }
        } else {
            filterChain.doFilter(req,resp);
        }

    }
}
