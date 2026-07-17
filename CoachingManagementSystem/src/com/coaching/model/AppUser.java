package com.coaching.model;

public class AppUser {
    private int userId;
    private String username;
    private String passwordHash;
    private String salt;
    private String role; // STUDENT, FACULTY, HEAD
    private int linkedId;
    private String status;

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public String getSalt() { return salt; }
    public void setSalt(String salt) { this.salt = salt; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public int getLinkedId() { return linkedId; }
    public void setLinkedId(int linkedId) { this.linkedId = linkedId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
