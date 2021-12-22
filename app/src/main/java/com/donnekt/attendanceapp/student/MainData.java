package com.donnekt.attendanceapp.student;

public class MainData {
    int finalModuleId, finalClassId;

    public MainData() {}

    public MainData(int finalModuleId, int finalClassId) {
        this.finalModuleId = finalModuleId;
        this.finalClassId = finalClassId;
    }


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


}