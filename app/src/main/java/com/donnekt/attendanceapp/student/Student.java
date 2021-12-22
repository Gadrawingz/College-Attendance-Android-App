package com.donnekt.attendanceapp.student;

public class Student {

    // These finalModuleId, finalClassId are stolen from Session
    int finalModuleId, finalClassId, updMod, updAttDay, updLect, pkAttendId;
    int studentId, dataCount, dataNo, classId;
    String firstname, lastname, regNumber, email, telephone, gender, className, classDept, regDate, activeAStatus;

    // Parameter constructor containing all parameters
    public Student(int studentId, String firstname, String lastname, String regNumber, String email, String telephone, String gender, int classId, String cName, String cDept, String regDate) {
        this.studentId = studentId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.regNumber = regNumber;
        this.email    = email;
        this.telephone = telephone;
        this.gender = gender;
        this.classId = classId;
        this.className = cName;
        this.classDept = cDept;
        this.regDate = regDate;
    }

    public Student(int studentId, String firstname, String lastname, String regNumber) {
        this.studentId = studentId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.regNumber = regNumber;
    }


    // FOR ATTENDANCE MAKING, AND HOD INSPECTING STUD BY MOD/CLASS
    public Student(int studentId, String firstname, String lastname, String regNumber, int finalModId, int finalClassId) {
        this.studentId = studentId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.regNumber = regNumber;
        this.finalModuleId= finalModId;
        this.finalClassId = finalClassId;
    }

    // Modificx
    public Student(int pkAttendId, int studentId, String firstname, String lastname, String regNumber, int updMod, int updLect, int updAttDay, String activeAStatus) {
        this.pkAttendId = pkAttendId;
        this.studentId = studentId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.regNumber = regNumber;
        this.updMod= updMod;
        this.updLect= updLect;
        this.updAttDay = updAttDay;
        this.activeAStatus= activeAStatus;
    }


    // DOQ THING FOR TOP DETAILS
    public Student(int studentId, String firstname, String lastname, String regNumber, String telephone, String gender, int classId) {
        this.studentId = studentId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.regNumber = regNumber;
        this.telephone = telephone;
        this.gender = gender;
        this.classId = classId;
    }


    public Student(int sCounts, int sNum, int studentId, String firstname, String lastname, String regNumber) {
        this.dataCount = sCounts;
        this.dataNo  = sNum;
        this.studentId = studentId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.regNumber = regNumber;
    }

    // Getting id
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    // Getting firstname
    public String getFirstname() {
        return firstname;
    }

    // Setting firstname
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    // Getting lastname
    public String getLastname() {
        return lastname;
    }

    // Setting lastname
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    // Getting email
    public String getEmail() {
        return email;
    }

    // Setting name
    public void setEmail(String email) {
        this.email = email;
    }

    // Getting phone
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String phone) {
        this.telephone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    // Getting role

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public int getDataCount() {
        return dataCount;
    }

    public void setDataCount(int dataCount) {
        this.dataCount = dataCount;
    }

    public int getDataNo() {
        return dataNo;
    }

    public void setDataNo(int dataNo) {
        this.dataNo = dataNo;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassDept() {
        return classDept;
    }

    public void setClassDept(String classDept) {
        this.classDept = classDept;
    }


    // FINAL ID FOR MAKING ATTENDANCE COME AS SESSION
    public int getFinalModuleId() {
        return finalModuleId;
    }

    public void setFinalModuleId(int finalModuleId) {
        this.finalModuleId = finalModuleId;
    }

    public int getFinalClassId() {
        return finalClassId;
    }
    public void setFinalClassId(int finalClassId) {
        this.finalClassId = finalClassId;
    }

    // Updating miscellaneous constructors input data


    public int getUpdMod() {
        return updMod;
    }

    public void setUpdMod(int updMod) {
        this.updMod = updMod;
    }

    public int getUpdLect() {
        return updLect;
    }

    public void setUpdLect(int updLect) {
        this.updLect = updLect;
    }

    public int getUpdAttDay() {
        return updAttDay;
    }

    public void setUpdAttDay(int updAttDay) {
        this.updAttDay = updAttDay;
    }

    public String getActiveAStatus() {
        return activeAStatus;
    }

    public void setActiveAStatus(String activeAStatus) {
        this.activeAStatus = activeAStatus;
    }

    public int getPkAttendId() {
        return pkAttendId;
    }

    public void setPkAttendId(int pkAttendId) {
        this.pkAttendId = pkAttendId;
    }
}