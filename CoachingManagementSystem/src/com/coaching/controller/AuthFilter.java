package com.coaching.controller;

import com.coaching.util.SessionUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    private static final String[] PUBLIC = {"/login", "/login.jsp", "/css/", "/js/", "/uploads/"};

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String uri = request.getRequestURI();
        String ctx = request.getContextPath();
        String path = uri.substring(ctx.length());

        for (String p : PUBLIC) {
            if (path.equals(p) || path.startsWith(p)) {
                chain.doFilter(req, res);
                return;
            }
        }
        if (SessionUtil.isLoggedIn(request.getSession(false))) {
            chain.doFilter(req, res);
        } else {
            response.sendRedirect(ctx + "/login");
        }
    }
}
