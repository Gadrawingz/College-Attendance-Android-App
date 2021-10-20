package com.donnekt.attendanceapp.classroom;

import com.donnekt.attendanceapp.department.Department;

import java.util.List;

public class SPModel {
    private int id;
    private String label;
    private List<Department> data = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Department> getData() {
        return data;
    }

    public void setData(List<Department> data) {
        this.data = data;
    }
}