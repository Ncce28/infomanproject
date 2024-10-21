package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Method to get database connection
    public Connection getConnection() {

        String url = "jdbc:mysql://localhost:3306/student_db";  // Database URL
        String user = "root";  // Default XAMPP MySQL username
        String password = "";  // Default XAMPP MySQL password

        Connection conn = null;

        try {
            // Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish the connection
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Database connected successfully.");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error connecting to the database.");
        }

        return conn;  // Return the database connection object
    }
}
