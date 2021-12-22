package com.donnekt.attendanceapp.staff;

// Model class
public class Staff {
    int staffId, refDeptId, refClassId;
    String firstname, lastname, email, telephone, gender, role, status, password, regDate, refDeptName, refClassName;

    // Constructor with two parameters name and password
    public Staff(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Parameter constructor containing all parameters
    public Staff(int staffId, String firstname, String lastname, String email, String telephone, String gender, String role, String status, String password, String regDate) {
        this.staffId = staffId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email    = email;
        this.telephone = telephone;
        this.gender = gender;
        this.role = role;
        this.status = status;
        this.password = password;
        this.regDate = regDate;
    }

    // Parameter constructor containing data for session
    public Staff(int staffId, String firstname, String lastname, String email, String gender, String role) {
        this.staffId = staffId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.gender = gender;
        this.role = role;
    }

    // Parameter constructor containing all params except staffId
    public Staff(String firstname, String lastname, String email, String telephone, String role, String password, String regDate) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.telephone = telephone;
        this.role = role;
        this.password = password;
        this.regDate = regDate;
    }

    // SESSION FOR MEMBERS
    public Staff(int staffId, int refDeptId, String refDeptName, String firstname, String lastname, String email, String telephone, String gender, String role, String status) {
        this.refDeptId= refDeptId;
        this.refDeptName= refDeptName;
        this.staffId = staffId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email    = email;
        this.telephone = telephone;
        this.gender = gender;
        this.role = role;
        this.status = status;
    }

    public Staff() { }

    // Getting id
    public int getStaffId() {
        return staffId;
    }

    // Setting id
    public void setStaffId(int id) {
        this.staffId = id;
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
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Getting password
    public String getPassword() {
        return password;
    }

    // Setting password
    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRefDeptId() {
        return refDeptId;
    }

    public void setRefDeptId(int refDeptId) {
        this.refDeptId = refDeptId;
    }

    // Getter and Setter
    public String getRefDeptName() {
        return refDeptName;
    }

    public void setRefDeptName(String refDeptName) {
        this.refDeptName = refDeptName;
    }

    public void setRefClassId(int refClassId) {
        this.refClassId = refClassId;
    }

    public void setRefClassName(String refClassName) {
        this.refClassName = refClassName;
    }

    public int getRefClassId() {
        return refClassId;
    }

    public String getRefClassName() {
        return refClassName;
    }
}
