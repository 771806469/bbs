package web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.AbstractFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;


public class LoginFilter extends AbstractFilter {

    private List<String> urlList = new ArrayList<>();
    private List<String> adminList = new ArrayList<>();
    private Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String validateUrl = filterConfig.getInitParameter("validateUrl");
        String validateAdmin = filterConfig.getInitParameter("validateAdmin");
        urlList = Arrays.asList(validateUrl.split(","));
        adminList = Arrays.asList(validateAdmin.split(","));
        logger.debug("adminList -> {}", adminList.toArray());

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        //获取用户要访问的url
        String requestUrl = req.getRequestURI();
        if(requestUrl.startsWith("/admin")) {
            logger.debug("以/admin开头，值为：{}",requestUrl);
            if(adminList != null && adminList.contains(requestUrl)) {
                if(req.getSession().getAttribute("curr_admin") == null) {
                    logger.debug("curr_admin为空");
                    resp.sendRedirect("/admin/login?redirect=" + requestUrl);
                } else {
                    logger.debug("curr_admin不为空");
                    filterChain.doFilter(req,resp);
                }
            } else {
                filterChain.doFilter(req,resp);
            }
        } else if(urlList != null && urlList.contains(requestUrl)) {
            if (req.getSession().getAttribute("curr_user") != null) {
                filterChain.doFilter(req, resp);
            } else {
                Map map = req.getParameterMap();
                Set paramSet = map.entrySet();
                Iterator it = paramSet.iterator();
                if (it.hasNext()) {
                    requestUrl += "?";

                    while (it.hasNext()) {
                        Map.Entry me = (Map.Entry) it.next();
                        Object key = me.getKey();
                        Object value = me.getValue();
                        String valString[] = (String[]) value;
                        String param = "";
                        for (int i = 0; i < valString.length; i++) {
                            param = key + "=" + valString[i] + "&";
                            requestUrl += param;
                        }
                    }
                    requestUrl = requestUrl.substring(0, requestUrl.length() - 1);
                    logger.trace("登录过滤器，请求转发到login页面,redirect网页为：{}", requestUrl);
                }
                resp.sendRedirect("/login?redirect=" + requestUrl);
            }
        } else {
            filterChain.doFilter(req,resp);
        }

    }
}
