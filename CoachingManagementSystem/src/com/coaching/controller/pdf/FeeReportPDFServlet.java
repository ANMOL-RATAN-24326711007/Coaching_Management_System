package com.coaching.controller.pdf;
import com.coaching.service.PDFReportService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.IOException;
import java.sql.Date;
@WebServlet("/pdf/feeReport")
public class FeeReportPDFServlet extends HttpServlet {
    private final PDFReportService pdfService = new PDFReportService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition", "attachment; filename=fee_report.pdf");
        String from = req.getParameter("from"); String to = req.getParameter("to");
        Date fromDate = (from!=null&&!from.isEmpty())?Date.valueOf(from):Date.valueOf("2000-01-01");
        Date toDate = (to!=null&&!to.isEmpty())?Date.valueOf(to):new Date(System.currentTimeMillis());
        try { pdfService.feeReport(resp.getOutputStream(), fromDate, toDate); } catch(Exception e){throw new ServletException(e);}
    }
}
