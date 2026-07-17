package com.coaching.model;

import java.sql.Date;

public class FeeStructure {
    private int feeId;
    private int studentId;
    private double totalAmount;
    private Date dueDate;
    private double paidAmount;   // computed: sum of transactions
    private double balanceAmount; // computed

    public int getFeeId() { return feeId; }
    public void setFeeId(int feeId) { this.feeId = feeId; }
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }
    public double getPaidAmount() { return paidAmount; }
    public void setPaidAmount(double paidAmount) { this.paidAmount = paidAmount; }
    public double getBalanceAmount() { return balanceAmount; }
    public void setBalanceAmount(double balanceAmount) { this.balanceAmount = balanceAmount; }
}
