package com.donnekt.attendanceapp.department;

public class Department {
    int departmentId;
    String departmentName, departmentCaption;

    // Constructor
    public Department(int deptId, String deptName, String deptCaption) {
        this.departmentId = deptId;
        this.departmentName = deptName;
        this.departmentCaption = deptCaption;
    }

    // getter & setter: id
    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    // getter & setter:name
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    // getter & setter:name
    public String getDepartmentCaption() {
        return departmentCaption;
    }
    public void setDepartmentCaption(String departmentCaption) {
        this.departmentCaption = departmentCaption;
    }
}
