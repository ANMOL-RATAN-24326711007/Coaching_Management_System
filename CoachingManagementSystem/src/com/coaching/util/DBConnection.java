package com.coaching.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Provides JDBC connections to the MySQL database.
 * Reads configuration from /db.properties (placed on the classpath,
 * i.e. WEB-INF/classes/db.properties after build).
 */
public class DBConnection {

    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    static {
        Properties props = new Properties();
        try (InputStream in = DBConnection.class.getResourceAsStream("/db.properties")) {
            if (in == null) {
                throw new RuntimeException("db.properties not found on classpath. " +
                        "Make sure it is placed in WEB-INF/classes (source root).");
            }
            props.load(in);
            Class.forName(props.getProperty("db.driver"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize DB driver/config: " + e.getMessage(), e);
        }
        URL = props.getProperty("db.url");
        USERNAME = props.getProperty("db.username");
        PASSWORD = props.getProperty("db.password");
    }

    private DBConnection() {
    }

    /** Returns a fresh JDBC connection. Caller is responsible for closing it. */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
