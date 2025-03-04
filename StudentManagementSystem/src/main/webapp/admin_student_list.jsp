<%@ page import="java.util.List" %>
<%@ page import="com.sms.model.Student" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Student List</title>
    <style>
        table { width: 100%; border-collapse: collapse; }
        th, td { border: 1px solid black; padding: 10px; text-align: center; }
        th { background-color: #f2f2f2; }
        .pagination { margin-top: 10px; text-align: center; }
        .pagination a { padding: 8px 16px; text-decoration: none; border: 1px solid #ccc; margin: 2px; }
        .pagination a.active { background-color: #4CAF50; color: white; }
        .pagination a:hover { background-color: #ddd; }
    </style>
</head>
<body>

<h2>Student List</h2>

<!-- Search Bar -->
<form action="SearchStudentServlet" method="get">
    <input type="text" name="query" placeholder="Search by Name or USN">
    <input type="submit" value="Search">
</form>

<table>
    <tr>
        <th>USN</th>
        <th>Name</th>
        <th>Age</th>
        <th>Department</th>
    </tr>
    
    <%
        List<Student> studentList = (List<Student>) request.getAttribute("studentList");
        if (studentList != null && !studentList.isEmpty()) {
            for (Student student : studentList) {
    %>
    <tr>
        <td><%= student.getUsn() %></td>
        <td><%= student.getName() %></td>
        <td><%= student.getAge() %></td>
        <td><%= student.getDepartment() %></td>
    </tr>
    <%
            }
        } else {
    %>
    <tr><td colspan="4">No students found.</td></tr>
    <% } %>
</table>

<!-- Pagination -->
<%
    int currentPage = (request.getAttribute("currentPage") != null) ? (int) request.getAttribute("currentPage") : 1;
    int totalPages = (request.getAttribute("totalPages") != null) ? (int) request.getAttribute("totalPages") : 1;
%>

<div class="pagination">
    <% if (currentPage > 1) { %>
        <a href="StudentListServlet?page=<%= currentPage - 1 %>">Previous</a>
    <% } %>

    <% for (int i = 1; i <= totalPages; i++) { %>
        <a href="StudentListServlet?page=<%= i %>" class="<%= (i == currentPage) ? "active" : "" %>"><%= i %></a>
    <% } %>

    <% if (currentPage < totalPages) { %>
        <a href="StudentListServlet?page=<%= currentPage + 1 %>">Next</a>
    <% } %>
</div>

</body>
</html>
