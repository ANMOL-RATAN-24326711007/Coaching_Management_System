package com.coaching.controller.student;

import com.coaching.service.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.IOException;

@WebServlet("/student/quiz/result")
public class StudentQuizResultServlet extends HttpServlet {
    private final QuizService quizService = new QuizService();
    private final InstituteConfigService configService = new InstituteConfigService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int attemptId = Integer.parseInt(req.getParameter("attemptId"));
        try {
            req.setAttribute("attempt", quizService.getAttempt(attemptId));
            req.setAttribute("responses", quizService.getResponses(attemptId));
            req.setAttribute("config", configService.getConfig());
            req.getRequestDispatcher("/WEB-INF/views/student/quizResult.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }
}
