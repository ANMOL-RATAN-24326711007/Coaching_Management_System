package com.coaching.controller;

import com.coaching.model.AppUser;
import com.coaching.service.AuthService;
import com.coaching.service.InstituteConfigService;
import com.coaching.util.SessionUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final AuthService authService = new AuthService();
    private final InstituteConfigService configService = new InstituteConfigService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (SessionUtil.isLoggedIn(req.getSession(false))) {
            redirect(req, resp, SessionUtil.getRole(req.getSession(false)));
            return;
        }
        try {
            req.setAttribute("config", configService.getConfig());
        } catch (Exception ignored) {}
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        try {
            AppUser user = authService.login(username, password);
            if (user == null) {
                req.setAttribute("error", "Invalid credentials or account inactive.");
                req.setAttribute("config", configService.getConfig());
                req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
                return;
            }
            HttpSession session = req.getSession(true);
            session.setAttribute(SessionUtil.ATTR_USER_ID, user.getUserId());
            session.setAttribute(SessionUtil.ATTR_USERNAME, user.getUsername());
            session.setAttribute(SessionUtil.ATTR_ROLE, user.getRole());
            session.setAttribute(SessionUtil.ATTR_LINKED_ID, user.getLinkedId());
            redirect(req, resp, user.getRole());
        } catch (Exception e) {
            req.setAttribute("error", "System error: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }

    private void redirect(HttpServletRequest req, HttpServletResponse resp, String role) throws IOException {
        String ctx = req.getContextPath();
        if ("HEAD".equals(role)) resp.sendRedirect(ctx + "/head/dashboard");
        else if ("FACULTY".equals(role)) resp.sendRedirect(ctx + "/faculty/dashboard");
        else resp.sendRedirect(ctx + "/student/dashboard");
    }
}
