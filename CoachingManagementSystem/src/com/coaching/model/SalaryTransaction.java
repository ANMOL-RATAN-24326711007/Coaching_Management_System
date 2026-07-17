package com.coaching.model;

import java.sql.Date;

public class SalaryTransaction {
    private int txnId;
    private int facultyId;
    private String facultyName; // join
    private int payMonth;
    private int payYear;
    private double basicPay;
    private double arrears;
    private double deductions;
    private double netPay;
    private Date paidDate;
    private String remarks;

    public int getTxnId() { return txnId; }
    public void setTxnId(int txnId) { this.txnId = txnId; }
    public int getFacultyId() { return facultyId; }
    public void setFacultyId(int facultyId) { this.facultyId = facultyId; }
    public String getFacultyName() { return facultyName; }
    public void setFacultyName(String facultyName) { this.facultyName = facultyName; }
    public int getPayMonth() { return payMonth; }
    public void setPayMonth(int payMonth) { this.payMonth = payMonth; }
    public int getPayYear() { return payYear; }
    public void setPayYear(int payYear) { this.payYear = payYear; }
    public double getBasicPay() { return basicPay; }
    public void setBasicPay(double basicPay) { this.basicPay = basicPay; }
    public double getArrears() { return arrears; }
    public void setArrears(double arrears) { this.arrears = arrears; }
    public double getDeductions() { return deductions; }
    public void setDeductions(double deductions) { this.deductions = deductions; }
    public double getNetPay() { return netPay; }
    public void setNetPay(double netPay) { this.netPay = netPay; }
    public Date getPaidDate() { return paidDate; }
    public void setPaidDate(Date paidDate) { this.paidDate = paidDate; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}
