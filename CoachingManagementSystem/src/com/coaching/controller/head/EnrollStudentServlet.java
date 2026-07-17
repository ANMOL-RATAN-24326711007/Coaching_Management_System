package com.coaching.controller.head;

import com.coaching.model.*;
import com.coaching.service.*;
import com.coaching.util.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.IOException;
import java.sql.Date;

@WebServlet("/head/enrollStudent")
@MultipartConfig(maxFileSize = 5242880)
public class EnrollStudentServlet extends HttpServlet {
    private final StudentService studentService = new StudentService();
    private final CourseService courseService = new CourseService();
    private final AuthService authService = new AuthService();
    private final InstituteConfigService configService = new InstituteConfigService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("courses", courseService.getAllCourses());
            req.setAttribute("students", studentService.getAllStudents());
            req.setAttribute("config", configService.getConfig());
            req.getRequestDispatcher("/WEB-INF/views/head/enrollStudent.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Parent p = new Parent();
            p.setName(req.getParameter("parentName"));
            p.setPhone(req.getParameter("parentPhone"));
            p.setEmail(req.getParameter("parentEmail"));
            p.setOccupation(req.getParameter("parentOccupation"));
            p.setAddress(req.getParameter("parentAddress"));

            Student s = new Student();
            s.setName(req.getParameter("name"));
            s.setGender(req.getParameter("gender"));
            s.setPhone(req.getParameter("phone"));
            s.setEmail(req.getParameter("email"));
            s.setAddress(req.getParameter("address"));
            s.setCourseId(Integer.parseInt(req.getParameter("courseId")));
            String dob = req.getParameter("dob");
            s.setDob(dob != null && !dob.isEmpty() ? Date.valueOf(dob) : null);
            s.setStatus("ACTIVE");

            int studentId = studentService.enroll(s, p);

            Part photoPart = req.getPart("photo");
            if (photoPart != null && photoPart.getSize() > 0) {
                String uploadDir = getServletContext().getRealPath("/uploads");
                String photoPath = FileUploadUtil.saveFile(photoPart, uploadDir, "students");
                studentService.updatePhoto(studentId, photoPath);
            }
            // Create login account
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            if (username != null && !username.isEmpty()) {
                authService.createStudentAccount(username, password, studentId);
            }
            resp.sendRedirect(req.getContextPath() + "/head/enrollStudent?msg=enrolled");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }
}
