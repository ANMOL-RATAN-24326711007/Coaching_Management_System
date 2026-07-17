package com.coaching.controller.pdf;
import com.coaching.service.PDFReportService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.IOException;
import java.time.LocalDate;
@WebServlet("/pdf/salaryReport")
public class SalaryReportPDFServlet extends HttpServlet {
    private final PDFReportService pdfService = new PDFReportService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition", "attachment; filename=salary_report.pdf");
        int month=LocalDate.now().getMonthValue(), year=LocalDate.now().getYear();
        String mStr=req.getParameter("month"),yStr=req.getParameter("year");
        if(mStr!=null&&!mStr.isEmpty())month=Integer.parseInt(mStr);
        if(yStr!=null&&!yStr.isEmpty())year=Integer.parseInt(yStr);
        try { pdfService.salaryReport(resp.getOutputStream(), month, year); } catch(Exception e){throw new ServletException(e);}
    }
}
