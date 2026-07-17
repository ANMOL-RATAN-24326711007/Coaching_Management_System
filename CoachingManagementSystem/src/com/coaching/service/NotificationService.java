package com.coaching.service;

import com.coaching.dao.NotificationDAO;
import com.coaching.model.Notification;
import java.sql.*;
import java.util.List;

public class NotificationService {
    private final NotificationDAO notifDAO = new NotificationDAO();

    public void broadcast(String title, String message) throws SQLException {
        Notification n = new Notification();
        n.setStudentId(null); n.setTitle(title); n.setMessage(message);
        notifDAO.create(n);
    }

    public void sendToStudent(int studentId, String title, String message) throws SQLException {
        Notification n = new Notification();
        n.setStudentId(studentId); n.setTitle(title); n.setMessage(message);
        notifDAO.create(n);
    }

    public List<Notification> getForStudent(int studentId) throws SQLException {
        return notifDAO.findForStudent(studentId);
    }

    public List<Notification> getAll() throws SQLException { return notifDAO.findAll(); }
}
