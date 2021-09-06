package com.donnekt.attendanceapp.staff;

// Model class
public class Staff {
    int staffId;
    String firstname, lastname, email, phone, role, password;

    // Constructor with 1 parameter for email
    public Staff(String email) {
        this.email = email;
    }

    // Constructor with two parameters name and password
    public Staff(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Parameter constructor containing all three parameters
    public Staff(int staffId, String firstname, String lastname, String email, String phone, String role, String password) {
        this.staffId = staffId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.password = password;
    }

    // Parameter constructor containing all three parameters
    public Staff(String firstname, String lastname, String email, String phone, String role, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.password = password;
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
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

}
