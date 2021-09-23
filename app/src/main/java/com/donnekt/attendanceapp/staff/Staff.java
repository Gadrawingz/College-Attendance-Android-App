package com.donnekt.attendanceapp.staff;

// Model class
public class Staff {
    int staffId;
    String firstname, lastname, email, telephone, gender, role, password, regDate;

    // Constructor with two parameters name and password
    public Staff(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Parameter constructor containing all parameters
    public Staff(int staffId, String firstname, String lastname, String email, String telephone, String role, String password, String regDate) {
        this.staffId = staffId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email    = email;
        this.telephone = telephone;
        this.role = role;
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

    // Getting id
    public int getStaffId() {
        return staffId;
    }

    // Setting id
    public void getStaffId(int id) {
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
}
