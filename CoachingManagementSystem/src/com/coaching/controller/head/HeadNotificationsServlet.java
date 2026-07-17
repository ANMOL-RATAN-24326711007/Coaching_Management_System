package com.coaching.controller.head;

import com.coaching.service.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.IOException;

@WebServlet("/head/notifications")
public class HeadNotificationsServlet extends HttpServlet {
    private final NotificationService notifService = new NotificationService();
    private final StudentService studentService = new StudentService();
    private final InstituteConfigService configService = new InstituteConfigService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("notifications", notifService.getAll());
            req.setAttribute("students", studentService.getAllStudents());
            req.setAttribute("config", configService.getConfig());
            req.getRequestDispatcher("/WEB-INF/views/head/notifications.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String title = req.getParameter("title");
            String message = req.getParameter("message");
            String sidStr = req.getParameter("studentId");
            if (sidStr == null || sidStr.isEmpty() || "0".equals(sidStr)) {
                notifService.broadcast(title, message);
            } else {
                notifService.sendToStudent(Integer.parseInt(sidStr), title, message);
            }
            resp.sendRedirect(req.getContextPath() + "/head/notifications?msg=sent");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }
}
