package com.donnekt.attendanceapp.module;

import android.widget.EditText;

public class Module {
    int moduleId, lecturerId;
    String moduleName, moduleCode, lFirstName, lLastName, lEmil, lPhone, lRole;

    // Constructor One
    public Module(String moduleName, String moduleCode, int lecturerId) {
        this.moduleName = moduleName;
        this.moduleCode = moduleCode;
        this.lecturerId = lecturerId;
    }

    // Constructor Two
    public Module(int moduleId, String moduleName, String moduleCode, int lecturerId, String lFName, String lLName, String lPhone, String lEmail, String lRole) {
        this.moduleId = moduleId;
        this.moduleName = moduleName;
        this.moduleCode = moduleCode;
        this.lecturerId = lecturerId;
        this.lFirstName = lFName;
        this.lLastName = lFName;
        this.lPhone = lPhone;
        this.lEmil = lEmail;
        this.lRole = lRole;
    }

    // Constructor Three
    public Module(int moduleId, String moduleName, String moduleCode) {
        this.moduleId = moduleId;
        this.moduleName = moduleName;
        this.moduleCode = moduleCode;
    }

    // Constructor
    public Module() { }

    // Getters & Setters id, name, code, lecturer
    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
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

    public int getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(int lecturerId) {
        this.lecturerId = lecturerId;
    }

    public String getlPhone() {
        return lPhone;
    }

    public void setlPhone(String lPhone) {
        this.lPhone = lPhone;
    }

    public String getlEmil() {
        return lEmil;
    }

    public void setlEmil(String lEmil) {
        this.lEmil = lEmil;
    }

    public String getlFirstName() {
        return lFirstName;
    }

    public void setlFirstName(String lFirstName) {
        this.lFirstName = lFirstName;
    }

    public String getlLastName() {
        return lLastName;
    }

    public void setlLastName(String lLastName) {
        this.lLastName = lLastName;
    }

    public String getlRole() {
        return lRole;
    }

    public void setlRole(String lRole) {
        this.lRole = lRole;
    }
}