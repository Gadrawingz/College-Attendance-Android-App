package com.donnekt.attendanceapp.classroom;

public class Classroom {

    int classroomId;
    String classroomName, classroomLevel, department;

    // Const without ID:
    public Classroom(String className, String classLevel, String department) {
        this.classroomName = className;
        this.classroomLevel = classLevel;
        this.department = department;
    }

    public Classroom(int classId, String className, String classLevel, String department) {
        this.classroomId = classId;
        this.classroomName = className;
        this.classroomLevel = classLevel;
        this.department = department;
    }

    // (1)
    public int getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(int classroomId) {
        this.classroomId = classroomId;
    }

    // (2)
    public String getClassroomName() {
        return classroomName;
    }
    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    // (3)
    public String getClassroomLevel() {
        return classroomLevel;
    }
    public void setClassroomLevel(String classroomLevel) {
        this.classroomLevel = classroomLevel;
    }


    // (4)
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
}
