package com.coaching.controller.pdf;

import com.coaching.service.PDFReportService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.IOException;

@WebServlet("/pdf/examReport")
public class ExamReportPDFServlet extends HttpServlet {
    private final PDFReportService pdfService = new PDFReportService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String examIdStr = req.getParameter("examId");
        if (examIdStr == null || examIdStr.trim().isEmpty()) {
            // Redirect to create exam page if no examId given
            resp.sendRedirect(req.getContextPath() + "/faculty/createExam?error=select_exam");
            return;
        }
        try {
            int examId = Integer.parseInt(examIdStr);
            resp.setContentType("application/pdf");
            resp.setHeader("Content-Disposition", "attachment; filename=exam_result_" + examId + ".pdf");
            pdfService.examResultReport(resp.getOutputStream(), examId);
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/faculty/createExam?error=invalid_exam");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
