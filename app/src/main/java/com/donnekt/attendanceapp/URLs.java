package com.donnekt.attendanceapp;

public class URLs {

    private static final String MAIN_URL = "https://southapi.000webhostapp.com/";

    public static final String STAFF_REGISTER = MAIN_URL+"staff_api?call=register";
    public static final String STAFF_LOGIN = MAIN_URL +"staff_api?call=login";
    public static final String STAFF_VIEW_ALL = MAIN_URL +"staff_api?call=view";
    public static final String STAFF_LECTURERS = MAIN_URL +"staff_api?call=v_lecturers";
    public static final String STAFF_VIEW_ONE = MAIN_URL +"staff/view/";
    public static final String STAFF_ENABLE = MAIN_URL +"staff/enable/";
    public static final String STAFF_DISABLE = MAIN_URL +"staff/disable/";

    public static final String STAFF_UPDATE = MAIN_URL +"staff/update/";

    public static final String DEPT_VIEW_ALL = MAIN_URL +"dept_api?call=view";
    public static final String DEPT_CREATE = MAIN_URL +"dept_api?call=register";
    public static final String DEPT_DELETE = MAIN_URL +"department/delete/";
    public static final String DEPT_UPDATE = MAIN_URL +"department/update/";

    public static final String CLASS_VIEW_ALL = MAIN_URL +"class_api?call=view";
    public static final String CLASS_CREATE = MAIN_URL +"class_api?call=register";
    public static final String MOD_C_GROUP = MAIN_URL +"class_api?call=mkgroup";
    public static final String CLASS_DELETE = MAIN_URL +"classroom/delete/";
    public static final String CLASS_UPDATE = MAIN_URL +"classroom/update/";

    public static final String MOD_VIEW_ALL = MAIN_URL +"module_api?call=view";
    public static final String MOD_CREATE = MAIN_URL +"module_api?call=register";
    public static final String MOD_DELETE = MAIN_URL +"module/delete/";
    public static final String MOD_UPDATE = MAIN_URL +"module/update/";
    public static final String MOD_VIEW_LECT = MAIN_URL +"module/lect/";
    public static final String MOD_VIEW_ONE = MAIN_URL +"module/view/";
    public static final String MOD_VIEW_GROUP = MAIN_URL +"module/group/"; // modulegroup

    public static final String STUD_VIEW_ALL = MAIN_URL +"student_api?call=view";
    public static final String STUD_REGISTER = MAIN_URL +"student_api?call=register";
    public static final String STUD_DELETE = MAIN_URL +"student/delete/";
    public static final String STUD_UPDATE = MAIN_URL +"student/update/";
    public static final String STUD_VIEW_ONE = MAIN_URL +"student/view/";
    public static final String STUD_CLASS = MAIN_URL +"student/class/"; // studclasses


    public static final String ATTEND_STUDENT = MAIN_URL +"student_api?call=view";
    public static final String ATTEND_MODULE = MAIN_URL +"attendance?";
    public static final String ATTEND_PRESENT = MAIN_URL +"attendance?";
    public static final String ATTEND_ABSENT  = MAIN_URL +"attendance?";

    // a_status=A,P: att_stud, att_module => stud_marks_absent, stud_marks_present
    public static final String ATTEND_DELETE = MAIN_URL +"attend/delete/";
    public static final String ATTEND_UPDATE = MAIN_URL +"attend/update/";



}
