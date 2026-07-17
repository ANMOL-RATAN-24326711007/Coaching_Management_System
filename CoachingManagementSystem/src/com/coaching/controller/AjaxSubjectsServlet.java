package com.coaching.controller;

import com.coaching.model.Subject;
import com.coaching.service.CourseService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/** Returns JSON array of subjects for a given courseId — used by AJAX dropdowns. */
@WebServlet("/ajax/subjects")
public class AjaxSubjectsServlet extends HttpServlet {
    private final CourseService courseService = new CourseService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        String courseIdStr = req.getParameter("courseId");
        if (courseIdStr == null || courseIdStr.isEmpty()) {
            out.print("[]");
            return;
        }
        try {
            List<Subject> subjects = courseService.getSubjectsByCourse(Integer.parseInt(courseIdStr));
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < subjects.size(); i++) {
                Subject s = subjects.get(i);
                if (i > 0) sb.append(",");
                sb.append("{\"subjectId\":").append(s.getSubjectId())
                  .append(",\"subjectName\":\"").append(escape(s.getSubjectName())).append("\"}");
            }
            sb.append("]");
            out.print(sb.toString());
        } catch (Exception e) {
            out.print("[]");
        }
    }

    private String escape(String s) {
        return s == null ? "" : s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
