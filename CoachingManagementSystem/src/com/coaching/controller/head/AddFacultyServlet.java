package com.coaching.controller.head;

import com.coaching.model.Faculty;
import com.coaching.service.*;
import com.coaching.util.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.IOException;
import java.sql.Date;

@WebServlet("/head/addFaculty")
@MultipartConfig(maxFileSize = 5242880)
public class AddFacultyServlet extends HttpServlet {
    private final FacultyService facultyService = new FacultyService();
    private final CourseService courseService = new CourseService();
    private final AuthService authService = new AuthService();
    private final InstituteConfigService configService = new InstituteConfigService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("subjects", courseService.getAllSubjects());
            req.setAttribute("faculties", facultyService.getAll());
            req.setAttribute("config", configService.getConfig());
            req.getRequestDispatcher("/WEB-INF/views/head/addFaculty.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Faculty f = new Faculty();
            f.setName(req.getParameter("name"));
            f.setGender(req.getParameter("gender"));
            f.setPhone(req.getParameter("phone"));
            f.setEmail(req.getParameter("email"));
            f.setAddress(req.getParameter("address"));
            f.setQualification(req.getParameter("qualification"));
            f.setSubjectId(Integer.parseInt(req.getParameter("subjectId")));
            f.setBasicPay(Double.parseDouble(req.getParameter("basicPay")));
            String dob = req.getParameter("dob");
            f.setDob(dob != null && !dob.isEmpty() ? Date.valueOf(dob) : null);
            f.setStatus("ACTIVE");

            int facultyId = facultyService.add(f);

            Part photoPart = req.getPart("photo");
            if (photoPart != null && photoPart.getSize() > 0) {
                String uploadDir = getServletContext().getRealPath("/uploads");
                String photoPath = FileUploadUtil.saveFile(photoPart, uploadDir, "faculty");
                facultyService.updatePhoto(facultyId, photoPath);
            }
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            if (username != null && !username.isEmpty()) {
                authService.createFacultyAccount(username, password, facultyId);
            }
            resp.sendRedirect(req.getContextPath() + "/head/addFaculty?msg=added");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }
}
