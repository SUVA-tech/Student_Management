package com.sms.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.sms.util.DBConnection;
import com.sms.model.Student; // Ensure this class exists

public class StudentLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement("SELECT usn, name, age, password FROM students WHERE usn = ?");
            ps.setString(1, username);
            rs = ps.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");

                if (password.equals(storedPassword)) {
                    HttpSession session = request.getSession();
                    session.setAttribute("username", username);

                    // ✅ Store student object in session
                    Student student = new Student();
                    student.setUsn(rs.getString("usn"));
                    student.setName(rs.getString("name"));
                    student.setAge(rs.getInt("age"));
                    session.setAttribute("student", student);

                    response.sendRedirect("student_dashboard.jsp");
                    return;
                }
            }

            response.sendRedirect("studentLogin.jsp?error=invalid");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("studentLogin.jsp?error=server_error");
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (ps != null) ps.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }
}
