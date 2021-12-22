package com.donnekt.attendanceapp.classroom;

public class Classroom {

    int classroomId, refDeptId, refModuleId, refStaffId;
    String classroomName, classroomLevel, department, staffFirstname, refDeptName, refDeptCaption;
    String moduleName, moduleCode;

    // Const without ID:
    public Classroom(String className, String classLevel, String department) {
        this.classroomName = className;
        this.classroomLevel = classLevel;
        this.department = department;
    }

    // Usage in DOQ rep 1st
    public Classroom(int classId, String className, String classLevel, int refDeptId, String refDeptName) {
        this.classroomId = classId;
        this.classroomName = className;
        this.classroomLevel = classLevel;
        this.refDeptId = refDeptId;
        this.refDeptName = refDeptName;
    }

    // Full one
    public Classroom(int classId, String className, String classLevel, int refDeptId, String refDeptName, String refCaption) {
        this.classroomId = classId;
        this.classroomName = className;
        this.classroomLevel = classLevel;
        this.refDeptId = refDeptId;
        this.refDeptName= refDeptName;
        this.refDeptCaption= refCaption;
    }

    public Classroom(int classId, String className, String classLevel, int moduleId, String moduleName, String moduleCode, String deptName, String deptCaption) {
        this.classroomId = classId;
        this.classroomName = className;
        this.classroomLevel = classLevel;
        this.refModuleId = moduleId;
        this.moduleName = moduleName;
        this.moduleCode = moduleCode;
        this.refDeptName= deptName;
        this.refDeptCaption= deptCaption;
    }

    public Classroom(int classId, String className, String classLevel, int refStaffId, String staffFirstname, String deptName, String deptCaption) {
        this.classroomId = classId;
        this.classroomName = className;
        this.classroomLevel = classLevel;
        this.refStaffId = refStaffId;
        this.staffFirstname = staffFirstname;
        this.refDeptName= deptName;
        this.refDeptCaption= deptCaption;
    }


    public Classroom() { }

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

    // (5)
    public int getRefDeptId() {
        return refDeptId;
    }

    public void setRefDeptId(int refDeptId) {
        this.refDeptId = refDeptId;
    }

    // (6)
    public String getRefDeptName() {
        return refDeptName;
    }

    public void setRefDeptName(String refDeptName) {
        this.refDeptName = refDeptName;
    }

    // (7)

    public String getRefDeptCaption() {
        return refDeptCaption;
    }

    public void setRefDeptCaption(String refDeptCaption) {
        this.refDeptCaption = refDeptCaption;
    }


    // Additional stuffs
    public int getRefModuleId() {
        return refModuleId;
    }

    public void setRefModuleId(int refModuleId) {
        this.refModuleId = refModuleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    // extra data for class room

    public int getRefStaffId() {
        return refStaffId;
    }

    public void setRefStaffId(int refStaffId) {
        this.refStaffId = refStaffId;
    }

    public String getStaffFirstname() {
        return staffFirstname;
    }

    public void setStaffFirstname(String staffFirstname) {
        this.staffFirstname = staffFirstname;
    }
}
