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
import com.sms.util.DBConnection;
import com.sms.model.Student;

public class SearchStudentServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchQuery = request.getParameter("query");
        List<Student> studentList = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT usn, name, age, department FROM students WHERE usn LIKE ? OR name LIKE ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + searchQuery + "%");
            ps.setString(2, "%" + searchQuery + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                Student student = new Student();
                student.setUsn(rs.getString("usn"));
                student.setName(rs.getString("name"));
                student.setAge(rs.getInt("age"));
                student.setDepartment(rs.getString("department"));
                studentList.add(student);
            }

            request.setAttribute("studentList", studentList);
            request.getRequestDispatcher("admin_student_list.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (ps != null) ps.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }
}
