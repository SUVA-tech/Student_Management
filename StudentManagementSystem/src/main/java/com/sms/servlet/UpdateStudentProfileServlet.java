package com.sms.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sms.util.DBConnection;
import com.sms.model.Student;

public class UpdateStudentProfileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String usn = request.getParameter("usn");
        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE students SET name = ?, age = ? WHERE usn = ?")) {
            
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, usn);
            int result = ps.executeUpdate();

            if (result > 0) {
                HttpSession session = request.getSession();
                Student student = (Student) session.getAttribute("student");
                student.setName(name);
                student.setAge(age);
                session.setAttribute("student", student);
                response.sendRedirect("StudentDashboardServlet");
            } else {
                response.getWriter().println("Update Failed!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
