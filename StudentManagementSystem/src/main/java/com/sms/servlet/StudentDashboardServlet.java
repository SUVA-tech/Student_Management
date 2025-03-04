package com.sms.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sms.model.Student;
import com.sms.util.DBConnection;

public class StudentDashboardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");

        if (student == null) {
            response.sendRedirect("studentLogin.jsp");
            return;
        }

        List<Student> peers = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT usn, name FROM students WHERE department = ?")) {
            
            ps.setString(1, student.getDepartment());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                peers.add(new Student(rs.getString("usn"), rs.getString("name")));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("peers", peers);
        request.getRequestDispatcher("student_dashboard.jsp").forward(request, response);
    }
}
