package com.sms.model;

public class Student {
    private String usn;
    private String name;
    private int age;
    private String department;

    // Default constructor (required for JavaBeans)
    public Student() {}

    // Constructor for full details
    public Student(String usn, String name, int age, String department) {
        this.usn = usn;
        this.name = name;
        this.age = age;
        this.department = department;
    }

    // Constructor for only USN and Name (used in StudentDashboardServlet)
    public Student(String usn, String name) {
        this.usn = usn;
        this.name = name;
    }

    // Getters and Setters
    public String getUsn() { return usn; }
    public void setUsn(String usn) { this.usn = usn; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
}
