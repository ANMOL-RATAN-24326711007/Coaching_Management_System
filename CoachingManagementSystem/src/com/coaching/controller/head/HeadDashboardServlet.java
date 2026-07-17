package com.coaching.controller.head;

import com.coaching.service.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.IOException;

@WebServlet("/head/dashboard")
public class HeadDashboardServlet extends HttpServlet {
    private final AnalyticsService analyticsService = new AnalyticsService();
    private final StudentService studentService = new StudentService();
    private final FacultyService facultyService = new FacultyService();
    private final InstituteConfigService configService = new InstituteConfigService();
    private final FeeService feeService = new FeeService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("totalStudents", analyticsService.totalStudents());
            req.setAttribute("totalFaculty", analyticsService.totalFaculty());
            String[] enrolment = analyticsService.enrolmentChartData();
            req.setAttribute("enrolmentLabels", enrolment[0]);
            req.setAttribute("enrolmentData", enrolment[1]);
            String[] feeTrend = analyticsService.feeCollectionChartData(6);
            req.setAttribute("feeLabels", feeTrend[0]);
            req.setAttribute("feeData", feeTrend[1]);
            req.setAttribute("recentStudents", studentService.getAllStudents());
            req.setAttribute("config", configService.getConfig());
            req.getRequestDispatcher("/WEB-INF/views/head/dashboard.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }
}
