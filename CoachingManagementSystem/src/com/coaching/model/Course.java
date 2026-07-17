package com.coaching.model;

public class Course {
    private int courseId;
    private String courseName;
    private int durationMonths;
    private double feeAmount;
    private String description;

    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public int getDurationMonths() { return durationMonths; }
    public void setDurationMonths(int durationMonths) { this.durationMonths = durationMonths; }
    public double getFeeAmount() { return feeAmount; }
    public void setFeeAmount(double feeAmount) { this.feeAmount = feeAmount; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
