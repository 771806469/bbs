package web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.AbstractFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


public class LoginFilter extends AbstractFilter {

    private List<String> urlList = new ArrayList<>();
    private Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String validateUrl = filterConfig.getInitParameter("validateUrl");
        urlList = Arrays.asList(validateUrl.split(","));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        //获取用户要访问的url
        String requestUrl = req.getRequestURI();

        if (urlList != null && urlList.contains(requestUrl)) {
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
            filterChain.doFilter(req, resp);
        }

    }
}
