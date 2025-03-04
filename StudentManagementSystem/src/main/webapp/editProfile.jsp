<%@ page import="com.sms.model.Student" %>
<%
    HttpSession sessionObj = request.getSession(false);
    Student student = (Student) sessionObj.getAttribute("student");

    if (student == null) {
        response.sendRedirect("studentLogin.jsp?error=session_expired");
        return;
    }
%>

<html>
<head><title>Edit Profile</title></head>
<body>

<h2>Edit Profile</h2>
<form action="UpdateStudentProfileServlet" method="post">
    <input type="hidden" name="usn" value="<%= student.getUsn() %>">
    Name: <input type="text" name="name" value="<%= student.getName() %>" required><br>
    Age: <input type="number" name="age" value="<%= student.getAge() %>" required><br>
    <input type="submit" value="Update">
</form>

<a href="student_dashboard.jsp">Back to Dashboard</a>

</body>
</html>
