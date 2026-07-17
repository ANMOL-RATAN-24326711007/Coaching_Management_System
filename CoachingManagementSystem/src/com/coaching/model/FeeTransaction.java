package com.coaching.model;

import java.sql.Date;

public class FeeTransaction {
    private int txnId;
    private int studentId;
    private String studentName; // join
    private double amount;
    private Date txnDate;
    private String mode;
    private String remarks;
    private String receiptNo;

    public int getTxnId() { return txnId; }
    public void setTxnId(int txnId) { this.txnId = txnId; }
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public Date getTxnDate() { return txnDate; }
    public void setTxnDate(Date txnDate) { this.txnDate = txnDate; }
    public String getMode() { return mode; }
    public void setMode(String mode) { this.mode = mode; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    public String getReceiptNo() { return receiptNo; }
    public void setReceiptNo(String receiptNo) { this.receiptNo = receiptNo; }
}
