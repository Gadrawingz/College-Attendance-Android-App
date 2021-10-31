package com.donnekt.attendanceapp;

public class URLs {

    private static final String MAIN_URL = "https://southapi.000webhostapp.com/";

    public static final String STAFF_REGISTER = MAIN_URL+"staff_api?call=register";
    public static final String STAFF_LOGIN = MAIN_URL +"staff_api?call=login";
    public static final String STAFF_VIEW_ALL = MAIN_URL +"staff_api?call=view";
    public static final String STAFF_LECTURERS = MAIN_URL +"staff_api?call=v_lecturers";
    public static final String STAFF_VIEW_ONE = MAIN_URL +"staff/view/";

    public static final String STAFF_UPDATE = MAIN_URL +"staff/update/";

    public static final String DEPT_VIEW_ALL = MAIN_URL +"dept_api?call=view";
    public static final String DEPT_CREATE = MAIN_URL +"dept_api?call=register";
    public static final String DEPT_DELETE = MAIN_URL +"department/delete/";
    public static final String DEPT_UPDATE = MAIN_URL +"department/update/";

    public static final String CLASS_VIEW_ALL = MAIN_URL +"class_api?call=view";
    public static final String CLASS_CREATE = MAIN_URL +"class_api?call=register";
    public static final String CLASS_DELETE = MAIN_URL +"classroom/delete/";
    public static final String CLASS_UPDATE = MAIN_URL +"classroom/update/";
    public static final String CLASS_GROUP = MAIN_URL +"classroom/group/";

    public static final String MOD_VIEW_ALL = MAIN_URL +"module_api?call=view";
    public static final String MOD_CREATE = MAIN_URL +"module_api?call=register";
    public static final String MOD_DELETE = MAIN_URL +"module/delete/";
    public static final String MOD_UPDATE = MAIN_URL +"module/update/";
    public static final String MOD_VIEW_LECT = MAIN_URL +"module/lect/";
    public static final String MOD_VIEW_ONE = MAIN_URL +"module/view/";

    public static final String STUD_VIEW_ALL = MAIN_URL +"student_api?call=view";
    public static final String STUD_REGISTER = MAIN_URL +"student_api?call=register";
    public static final String STUD_DELETE = MAIN_URL +"student/delete/";
    public static final String STUD_UPDATE = MAIN_URL +"student/update/";




}
