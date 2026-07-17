package com.coaching.service;

import com.coaching.dao.*;
import com.coaching.model.*;
import com.coaching.util.PdfGenerator;

import java.io.OutputStream;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class PDFReportService {
    private final FeeDAO feeDAO = new FeeDAO();
    private final SalaryDAO salaryDAO = new SalaryDAO();
    private final MarksDAO marksDAO = new MarksDAO();
    private final ExamDAO examDAO = new ExamDAO();

    public void feeReport(OutputStream out, Date from, Date to) throws Exception {
        List<FeeTransaction> txns = feeDAO.findAll(from, to);
        String[] headers = {"Receipt No", "Student Name", "Amount (Rs)", "Date", "Mode", "Remarks"};
        List<String[]> rows = new ArrayList<>();
        for (FeeTransaction t : txns) {
            rows.add(new String[]{
                safe(t.getReceiptNo()), t.getStudentName(),
                String.format("%.2f", t.getAmount()),
                t.getTxnDate() != null ? t.getTxnDate().toString() : "",
                safe(t.getMode()), safe(t.getRemarks())
            });
        }
        PdfGenerator.generateTablePdf(out, "Fee Collection Report",
                "Period: " + from + " to " + to, headers, rows);
    }

    public void salaryReport(OutputStream out, int month, int year) throws Exception {
        List<SalaryTransaction> txns = salaryDAO.findAll(month, year);
        String[] headers = {"Faculty Name", "Month/Year", "Basic Pay", "Arrears", "Deductions", "Net Pay"};
        List<String[]> rows = new ArrayList<>();
        for (SalaryTransaction t : txns) {
            rows.add(new String[]{
                t.getFacultyName(),
                t.getPayMonth() + "/" + t.getPayYear(),
                String.format("%.2f", t.getBasicPay()),
                String.format("%.2f", t.getArrears()),
                String.format("%.2f", t.getDeductions()),
                String.format("%.2f", t.getNetPay())
            });
        }
        PdfGenerator.generateTablePdf(out, "Faculty Salary Report",
                "Month: " + month + " / Year: " + year, headers, rows);
    }

    public void examResultReport(OutputStream out, int examId) throws Exception {
        Exam exam = examDAO.findById(examId);
        List<StudentMark> marks = marksDAO.findByExam(examId);
        String[] headers = {"Student Name", "Marks Obtained", "Total Marks", "Percentage", "Remarks"};
        List<String[]> rows = new ArrayList<>();
        for (StudentMark m : marks) {
            double pct = m.getTotalMarks() > 0 ? (m.getMarksObtained() * 100.0 / m.getTotalMarks()) : 0;
            rows.add(new String[]{
                m.getStudentName(),
                String.format("%.2f", m.getMarksObtained()),
                String.valueOf(m.getTotalMarks()),
                String.format("%.1f%%", pct),
                safe(m.getRemarks())
            });
        }
        String title = exam != null ? "Result: " + exam.getExamName() : "Exam Result Report";
        String sub = exam != null ? "Date: " + exam.getExamDate() + " | Total Marks: " + exam.getTotalMarks() : "";
        PdfGenerator.generateTablePdf(out, title, sub, headers, rows);
    }

    private String safe(String s) { return s != null ? s : ""; }
}
