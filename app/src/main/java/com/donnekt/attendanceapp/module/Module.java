package com.donnekt.attendanceapp.module;

import android.widget.EditText;

public class Module {
    int moduleId;
    String moduleName, moduleCode, departmentId, lecturerId;

    // Constructor One
    public Module(String moduleName, String moduleCode, String departmentId, String lecturerId) {
        this.moduleName = moduleName;
        this.moduleCode = moduleCode;
        this.departmentId = departmentId;
        this.lecturerId = lecturerId;
    }

    // Constructor Two
    public Module(int moduleId, String moduleName, String moduleCode, String departmentId, String lecturerId) {
        this.moduleId = moduleId;
        this.moduleName = moduleName;
        this.moduleCode = moduleCode;
        this.departmentId = departmentId;
        this.lecturerId = lecturerId;
    }

    // Constructor Three
    public Module(int moduleId, String moduleName, String moduleCode) {
        this.moduleId = moduleId;
        this.moduleName = moduleName;
        this.moduleCode = moduleCode;
    }

    // Constructor
    public Module() { }

    // Getters & Setters id, name, code, dept, lect
    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
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

    public String getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(String lecturerId) {
        this.lecturerId = lecturerId;
    }
}



/*

package com.donnekt.attendanceapp.module;

import android.widget.EditText;

public class Module {
    int moduleId;
    String moduleName, moduleCode, departmentId, lecturerId;

    // Constructor One
    public Module(String moduleName, String moduleCode, String departmentId, String lecturerId) {
        this.moduleName = moduleName;
        this.moduleCode = moduleCode;
        this.departmentId = departmentId;
        this.lecturerId = lecturerId;
    }

    // Constructor Two
    public Module(int moduleId, String moduleName, String moduleCode, String departmentId, String lecturerId) {
        this.moduleId = moduleId;
        this.moduleName = moduleName;
        this.moduleCode = moduleCode;
        this.departmentId = departmentId;
        this.lecturerId = lecturerId;
    }

    // Constructor
    public Module() { }


    // Getters & Setters id, name, code, dept, lect
    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
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

    public String getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(String lecturerId) {
        this.lecturerId = lecturerId;
    }
}


*/