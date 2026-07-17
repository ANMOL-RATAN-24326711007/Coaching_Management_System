package com.coaching.controller;

import com.coaching.model.InstituteConfig;
import com.coaching.service.*;
import com.coaching.util.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.IOException;

@WebServlet("/controlPanel")
@MultipartConfig(maxFileSize = 5242880)
public class ControlPanelServlet extends HttpServlet {
    private final InstituteConfigService configService = new InstituteConfigService();
    private final CourseService courseService = new CourseService();
    private final FacultyService facultyService = new FacultyService();
    private final StudentService studentService = new StudentService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("config", configService.getConfig());
            req.setAttribute("totalStudents", studentService.countAll());
            req.setAttribute("totalFaculty", facultyService.countAll());
            req.setAttribute("courses", courseService.getAllCourses());
            req.setAttribute("subjects", courseService.getAllSubjects());
            req.getRequestDispatcher("/WEB-INF/views/controlPanel/index.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if ("updateConfig".equals(action)) {
                InstituteConfig c = configService.getConfig();
                c.setInstituteName(req.getParameter("instituteName"));
                c.setTagline(req.getParameter("tagline"));
                c.setAddress(req.getParameter("address"));
                c.setContactPhone(req.getParameter("contactPhone"));
                c.setContactEmail(req.getParameter("contactEmail"));
                configService.updateConfig(c);
                Part logoPart = req.getPart("logo");
                if (logoPart != null && logoPart.getSize() > 0) {
                    String uploadDir = getServletContext().getRealPath("/uploads");
                    String logoPath = FileUploadUtil.saveFile(logoPart, uploadDir, "logos");
                    configService.updateLogo(c.getId(), logoPath);
                }
                resp.sendRedirect(req.getContextPath() + "/controlPanel?msg=updated");
            } else if ("addCourse".equals(action)) {
                com.coaching.model.Course c = new com.coaching.model.Course();
                c.setCourseName(req.getParameter("courseName"));
                c.setDurationMonths(Integer.parseInt(req.getParameter("durationMonths")));
                c.setFeeAmount(Double.parseDouble(req.getParameter("feeAmount")));
                c.setDescription(req.getParameter("description"));
                courseService.createCourse(c);
                resp.sendRedirect(req.getContextPath() + "/controlPanel?msg=course_added");
            } else if ("addSubject".equals(action)) {
                com.coaching.model.Subject s = new com.coaching.model.Subject();
                s.setSubjectName(req.getParameter("subjectName"));
                s.setCourseId(Integer.parseInt(req.getParameter("courseId")));
                courseService.createSubject(s);
                resp.sendRedirect(req.getContextPath() + "/controlPanel?msg=subject_added");
            } else {
                resp.sendRedirect(req.getContextPath() + "/controlPanel");
            }
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }
}
