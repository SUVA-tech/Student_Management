package com.sms.util;
import java.sql.Connection;
import java.sql.DriverManager;
//Database connectivity
public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/students_management";
    private static final String USER = "root";  //  MySQL username
    private static final String PASSWORD = "12345";  //  MySQL password

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database Connected Successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
