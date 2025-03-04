package com.sms.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sms.util.DBConnection;

public class UpdateStudentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String usn = request.getParameter("usn");
        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        String department = request.getParameter("department");

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement("UPDATE students SET name=?, age=?, department=? WHERE usn=?");
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, department);
            ps.setString(4, usn);

            int result = ps.executeUpdate();
            if (result > 0) {
                response.sendRedirect("admin_dashboard.jsp?update=success");
            } else {
                response.getWriter().println("Update Failed! No student found with USN: " + usn);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("An error occurred: " + e.getMessage());
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }
}
