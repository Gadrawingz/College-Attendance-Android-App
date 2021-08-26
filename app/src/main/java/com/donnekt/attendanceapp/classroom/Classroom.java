package com.donnekt.attendanceapp.classroom;

public class Classroom {

    int classId, departmentId, levelId, lecturerId;
    String className;

    public Classroom(int classId, String className, int departmentId, int levelId, int lecturerId) {
        this.classId = classId;
        this.className = className;
        this.departmentId = departmentId;
        this.levelId = levelId;
        this.lecturerId = lecturerId;
    }

    // (1)
    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    // (2)
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }


    // (3)
    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }


    // (4)
    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }


    // (5)
    public int getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(int lecturerId) {
        this.lecturerId = lecturerId;
    }
    
}
