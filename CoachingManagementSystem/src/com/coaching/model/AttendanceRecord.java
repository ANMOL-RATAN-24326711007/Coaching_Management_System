package com.coaching.model;

import java.sql.Date;

/** Used for both student and faculty attendance display rows. */
public class AttendanceRecord {
    private int id;
    private int personId;
    private String personName;
    private Date date;
    private String status; // PRESENT, ABSENT
    private String extra; // e.g. subject/topic for student attendance rows

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getPersonId() { return personId; }
    public void setPersonId(int personId) { this.personId = personId; }
    public String getPersonName() { return personName; }
    public void setPersonName(String personName) { this.personName = personName; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getExtra() { return extra; }
    public void setExtra(String extra) { this.extra = extra; }
}
