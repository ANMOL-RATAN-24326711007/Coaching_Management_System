package com.coaching.controller.student;

import com.coaching.model.Student;
import com.coaching.service.*;
import com.coaching.util.SessionUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.IOException;

@WebServlet("/student/quizzes")
public class StudentQuizListServlet extends HttpServlet {
    private final ExamService examService = new ExamService();
    private final QuizService quizService = new QuizService();
    private final StudentService studentService = new StudentService();
    private final InstituteConfigService configService = new InstituteConfigService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int studentId = (int) req.getSession(false).getAttribute(SessionUtil.ATTR_LINKED_ID);
        try {
            Student s = studentService.getById(studentId);
            req.setAttribute("quizzes", examService.getOnlineQuizzesForCourse(s.getCourseId()));
            req.setAttribute("attempts", quizService.getAttemptsByStudent(studentId));
            req.setAttribute("config", configService.getConfig());
            req.getRequestDispatcher("/WEB-INF/views/student/quizList.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }
}
