package com.coaching.controller.student;

import com.coaching.service.*;
import com.coaching.util.SessionUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.IOException;

@WebServlet("/student/notifications")
public class StudentNotificationsServlet extends HttpServlet {
    private final NotificationService notifService = new NotificationService();
    private final InstituteConfigService configService = new InstituteConfigService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int studentId = (int) req.getSession(false).getAttribute(SessionUtil.ATTR_LINKED_ID);
        try {
            req.setAttribute("notifications", notifService.getForStudent(studentId));
            req.setAttribute("config", configService.getConfig());
            req.getRequestDispatcher("/WEB-INF/views/student/notifications.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }
}
