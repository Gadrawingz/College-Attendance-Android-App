package com.donnekt.attendanceapp.users.reports;

public class Report {
    int reportId, reportReceiver, lID, hId, hDeptId, hClassCounts, xStudId, xModuleId;
    String lFistName, lLastName, mainStats, xModuleName, xModuleCode, xStaffFN, xStaffLN;
    String sFName, sLName, sRegNo, sDept, sClass, sShows, sPres, sAbsence, actDate;
    String hFirstname, hLastname, hDeptName, classCounting;

    public Report () { }
    public Report (int reportId, int reportReceiver) {
        this.reportId = reportId;
        this.reportReceiver = reportReceiver;
    }

    public Report (int lID, String lFistName, String lLastName) {
        this.lID = lID;
        this.lFistName = lFistName;
        this.lLastName = lLastName;
    }

    public Report(String sFName, String sLName, String sRegNo, String sDept, String sClass, String sShows, String sPres, String sAbsence, String actDate) {
        this.sFName = sFName;
        this.sLName = sLName;
        this.sRegNo = sRegNo;
        this.sDept = sDept;
        this.sClass = sClass;
        this.sShows = sShows;
        this.sPres = sPres;
        this.sAbsence = sAbsence;
        this.actDate = actDate;
    }

    public Report(int hId, String hFirstname, String hLastname, int hDeptId, String hDeptName, String classCounting) {
        this.hId = hId;
        this.hFirstname = hFirstname;
        this.hLastname = hLastname;
        this.hDeptId = hDeptId;
        this.hDeptName = hDeptName;
        this.classCounting = classCounting;
    }

    public Report(int xStudId, int xModuleId, String xModuleName, String xModuleCode, String xStaffFN, String xStaffLN) {
        this.xStudId = xStudId;
        this.xModuleId = xModuleId;
        this.xModuleName = xModuleName;
        this.xModuleCode = xModuleCode;
        this.xStaffFN = xStaffFN;
        this.xStaffLN = xStaffLN;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public int getReportId() {
        return reportId;
    }

    public int getReportReceiver() {
        return reportReceiver;
    }

    public void setReportReceiver(int reportReceiver) {
        this.reportReceiver = reportReceiver;
    }

    // More G & S
    public String getlFistName() {
        return lFistName;
    }

    public void setlFistName(String lFistName) {
        this.lFistName = lFistName;
    }

    public String getlLastName() {
        return lLastName;
    }

    public void setlLastName(String lLastName) {
        this.lLastName = lLastName;
    }

    public String getMainStats() {
        return mainStats;
    }

    public void setMainStats(String mainStats) {
        this.mainStats = mainStats;
    }


    public int getlID() {
        return lID;
    }

    public void setlID(int lID) {
        this.lID = lID;
    }

    public void setsFName(String sFName) {
        this.sFName = sFName;
    }

    public String getsFName() {
        return sFName;
    }

    public void setsLName(String sLName) {
        this.sLName = sLName;
    }

    public String getsLName() {
        return sLName;
    }

    public void setsRegNo(String sRegNo) {
        this.sRegNo = sRegNo;
    }

    public String getsRegNo() {
        return sRegNo;
    }

    public void setsDept(String sDept) {
        this.sDept = sDept;
    }

    public String getsDept() {
        return sDept;
    }

    public void setsClass(String sClass) {
        this.sClass = sClass;
    }

    public String getsClass() {
        return sClass;
    }

    public void setsShows(String sShows) {
        this.sShows = sShows;
    }

    public String getsShows() {
        return sShows;
    }

    public void setsPres(String sPres) {
        this.sPres = sPres;
    }

    public String getsPres() {
        return sPres;
    }

    public void setsAbsence(String sAbsence) {
        this.sAbsence = sAbsence;
    }

    public String getsAbsence() {
        return sAbsence;
    }

    public void setActDate(String actDate) {
        this.actDate = actDate;
    }

    public String getActDate() {
        return actDate;
    }

    // HODs

    public int gethId() {
        return hId;
    }

    public void sethId(int hId) {
        this.hId = hId;
    }

    public String gethFirstname() {
        return hFirstname;
    }

    public void sethFirstname(String hFirstname) {
        this.hFirstname = hFirstname;
    }

    public String gethLastname() {
        return hLastname;
    }

    public void sethLastname(String hLastname) {
        this.hLastname = hLastname;
    }

    public int gethDeptId() {
        return hDeptId;
    }

    public void sethDeptId(int hDeptId) {
        this.hDeptId = hDeptId;
    }

    public String gethDeptName() {
        return hDeptName;
    }

    public void sethDeptName(String hDeptName) {
        this.hDeptName = hDeptName;
    }

    public int gethClassCounts() {
        return hClassCounts;
    }

    public void sethClassCounts(int hClassCounts) {
        this.hClassCounts = hClassCounts;
    }

    public String getClassCounting() {
        return classCounting;
    }

    public void setClassCounting(String classCounting) {
        this.classCounting = classCounting;
    }


    // LAST SHIT


    public int getxStudId() {
        return xStudId;
    }

    public void setxStudId(int xStudId) {
        this.xStudId = xStudId;
    }

    public String getxStaffFN() {
        return xStaffFN;
    }

    public void setxStaffFN(String xStaffFN) {
        this.xStaffFN = xStaffFN;
    }

    public String getxStaffLN() {
        return xStaffLN;
    }

    public void setxStaffLN(String xStaffLN) {
        this.xStaffLN = xStaffLN;
    }

    public int getxModuleId() {
        return xModuleId;
    }

    public void setxModuleId(int xModuleId) {
        this.xModuleId = xModuleId;
    }

    public String getxModuleCode() {
        return xModuleCode;
    }

    public void setxModuleCode(String xModuleCode) {
        this.xModuleCode = xModuleCode;
    }

    public String getxModuleName() {
        return xModuleName;
    }

    public void setxModuleName(String xModuleName) {
        this.xModuleName = xModuleName;
    }
}
