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
import javax.servlet.RequestDispatcher;

import com.sms.model.Student;
import com.sms.util.DBConnection;

public class StudentListServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        // Get page number from request, default to 1
        int page = 1;
        int recordsPerPage = 5; // Change as needed
        
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        
        int start = (page - 1) * recordsPerPage;

        List<Student> studentList = new ArrayList<>();
        int totalRecords = 0;
        
        try {
            conn = DBConnection.getConnection();
            
            // Get paginated student list
            String query = "SELECT * FROM students LIMIT ? OFFSET ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, recordsPerPage);
            ps.setInt(2, start);
            rs = ps.executeQuery();

            while (rs.next()) {
                Student student = new Student();
                student.setUsn(rs.getString("usn"));
                student.setName(rs.getString("name"));
                student.setAge(rs.getInt("age"));
                student.setDepartment(rs.getString("department"));
                studentList.add(student);
            }
            rs.close();
            ps.close();
            
            // Get total number of records
            query = "SELECT COUNT(*) FROM students";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.next()) {
                totalRecords = rs.getInt(1);
            }

            int totalPages = (int) Math.ceil(totalRecords * 1.0 / recordsPerPage);
            
            request.setAttribute("studentList", studentList);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("admin_student_list.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (ps != null) ps.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }
}
