package com.donnekt.attendanceapp.student;

import static android.view.View.GONE;

import static com.donnekt.attendanceapp.DialogShit.exitDamnProgressDialog;
import static com.donnekt.attendanceapp.DialogShit.showDamnProgressDialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.donnekt.attendanceapp.R;
import com.donnekt.attendanceapp.SharedPrefManager;
import com.donnekt.attendanceapp.URLs;
import com.donnekt.attendanceapp.VolleySingleton;
import com.donnekt.attendanceapp.classroom.Classroom;
import com.donnekt.attendanceapp.classroom.ClassroomAdapter;
import com.donnekt.attendanceapp.staff.Staff;
import com.donnekt.attendanceapp.users.reports.Report;
import com.donnekt.attendanceapp.users.reports.ReportDoq;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentAdapter extends ArrayAdapter<Student> {

    private final List<Student> studentList;
    private final Context mCtx;

    public StudentAdapter(List<Student> studentList, Context appContext) {
        super(appContext, R.layout.list_layout_student, studentList);
        this.mCtx = appContext;
        this.studentList = studentList;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View LVItem = inflater.inflate(R.layout.list_layout_student, null, true);

        // Getting views
        TextView textViewNames = LVItem.findViewById(R.id.tvStudentNames);
        TextView textViewRN = LVItem.findViewById(R.id.tvStudentRegNo);
        Button buttonVDet = LVItem.findViewById(R.id.buttonViewDetails);

        // Getting record of the specified position
        Student studShit = studentList.get(position);

        // Adding data to views Constructor class
        textViewNames.setText("Names: " + studShit.getFirstname() + " " + studShit.getLastname());
        textViewRN.setText("Reg No: " + studShit.getRegNumber());

        buttonVDet.setOnClickListener(view -> {
            Intent g = new Intent(mCtx.getApplicationContext(), StudentDetails.class);
            String studentId = String.valueOf(studShit.getStudentId());
            g.putExtra("student_id_key", studentId);

            g.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            g.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mCtx.startActivity(g);
        });

        // Return the list item
        return LVItem;
    }
}



class StAttendanceAdapter extends ArrayAdapter<Student> {

    private final List<Student> studentList;
    private List<MainData> mainDataList;
    private ArrayList<MainData> mainDataArrayList;
    private final Context mCtx;
    int totalStudents;
    int GOT_LAST_DAY;

    public StAttendanceAdapter(List<Student> studentList, Context appContext) {
        super(appContext, R.layout.list_layout_attendance, studentList);
        this.mCtx = appContext;
        this.studentList = studentList;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View LVItem = inflater.inflate(R.layout.list_layout_attendance, null, true);

        // Getting views
        TextView textViewNames = LVItem.findViewById(R.id.tvStudentNames);
        TextView textViewRN = LVItem.findViewById(R.id.tvStudentRegNo);
        TextView textViewDY = LVItem.findViewById(R.id.tvAttendanceDay);
        Button buttonPresent = LVItem.findViewById(R.id.isPresent);
        ImageView attendancePDone = LVItem.findViewById(R.id.attendancePDone);
        ImageView attendanceADone = LVItem.findViewById(R.id.attendanceADone);
        Button buttonAbsent = LVItem.findViewById(R.id.isAbsent);

        // Getting record of the specified position
        Student studShit = studentList.get(position);

        // Adding data to views Constructor class

        textViewNames.setText(">>" + studShit.getFirstname() + " " + studShit.getLastname());
        textViewRN.setText("Reg No: " + studShit.getRegNumber());

        // Declaration
        Staff staffMember = SharedPrefManager.getInstance(mCtx.getApplicationContext()).getLoggedInStaff();

        String statusAbsent = "A"; // ABSENT
        String statusPresent = "P"; // PRESENT
        String CLASS__ID = String.valueOf(studShit.getFinalClassId());
        String MODULE_ID = String.valueOf(studShit.getFinalModuleId());
        String LECTURER_ID = String.valueOf(staffMember.getStaffId());
        String STUDENT_ID = String.valueOf(studShit.getStudentId());
        String acYear = "4";

        // MAIN KEYS: att_status, student_id, lecturer_id, module_id, acad_year, att_day

        // Getting the day
        String LAST_DAY_URL = URLs.ATTENDANCE_URL + "last_day&m_id=" + MODULE_ID + "&l_id=" + LECTURER_ID + "&s_id=" + STUDENT_ID;
        StringRequest reqAttendanceDay = new StringRequest(Request.Method.GET, LAST_DAY_URL, response -> {
            try {
                JSONObject object = new JSONObject(response);
                int dayCounts = Integer.parseInt(object.optString("counts"));
                String lastDay = String.valueOf(object.optString("lastday"));
                textViewDY.setText("Day: " + lastDay);
                GOT_LAST_DAY = Integer.parseInt(lastDay);

                String atDay = String.valueOf(GOT_LAST_DAY);
                buttonPresent.setOnClickListener(view -> {
                    attendanceActivity(statusPresent, STUDENT_ID, LECTURER_ID, MODULE_ID, acYear, atDay);
                    buttonPresent.setVisibility(GONE);
                    buttonAbsent.setVisibility(GONE);
                    attendancePDone.setVisibility(View.VISIBLE);
                });

                buttonAbsent.setOnClickListener(view -> {
                    attendanceActivity(statusAbsent, STUDENT_ID, LECTURER_ID, MODULE_ID, acYear, atDay);
                    buttonPresent.setVisibility(GONE);
                    buttonAbsent.setVisibility(GONE);
                    attendanceADone.setVisibility(View.VISIBLE);
                });

            } catch (JSONException error) {
                error.printStackTrace();
            }
        }, error -> Toast.makeText(mCtx.getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        requestQueue.add(reqAttendanceDay);

        return LVItem;
    }

    private void attendanceActivity(String STATUS, String STUDENT, String LECTURER, String MODULE, String ACADEMIC, String DAY) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.ATTEND_MAKING, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Toast.makeText(mCtx.getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(mCtx.getApplicationContext(), "Network Error!", Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("att_status", STATUS);
                params.put("student_id", STUDENT);
                params.put("lecturer_id", LECTURER);
                params.put("module_id", MODULE);
                params.put("acad_year", ACADEMIC);
                params.put("att_day", DAY);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        requestQueue.add(stringRequest);
    }
}




class StAttendanceUpdate extends ArrayAdapter<Student> {
    private final List<Student> studentList;
    private final Context mCtx;
    private String PK_ATT_ID;

    public StAttendanceUpdate(List<Student> studentList, Context appContext) {
        super(appContext, R.layout.layout_attendance_update, studentList);
        this.mCtx = appContext;
        this.studentList = studentList;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View LVItem = inflater.inflate(R.layout.layout_attendance_update, null, true);

        // Getting views
        TextView textViewNames = LVItem.findViewById(R.id.tvStudentNames);
        TextView textViewRN = LVItem.findViewById(R.id.tvStudentRegNo);
        TextView textViewAI = LVItem.findViewById(R.id.display);
        RoundedImageView attendancePDone = LVItem.findViewById(R.id.attendancePDone);
        RoundedImageView attendanceADone = LVItem.findViewById(R.id.attendanceADone);

        Student studShit = studentList.get(position);

        textViewNames.setText(">>" + studShit.getFirstname() + " " + studShit.getLastname());
        textViewRN.setText("Reg No: " + studShit.getRegNumber());
        Staff staffMember = SharedPrefManager.getInstance(mCtx.getApplicationContext()).getLoggedInStaff();

        String statusAbsent = "A"; // ABSENT
        String statusPresent = "P"; // PRESENT
        String MODULE_ID = String.valueOf(studShit.getFinalModuleId());
        String LECTURER_ID = String.valueOf(staffMember.getStaffId());
        String STUDENT_ID = String.valueOf(studShit.getStudentId());
        String ATTEND_DAY = String.valueOf(studShit.getUpdAttDay());
        textViewAI.setText(String.valueOf(studShit.getPkAttendId()));
        PK_ATT_ID = String.valueOf(studShit.getPkAttendId());
        // att_status, student_id, lecturer_id, module_id, att_day

        if(studShit.getActiveAStatus().equals("P")) {
            attendancePDone.setVisibility(View.VISIBLE);
            attendanceADone.setVisibility(GONE);
        } else {
            attendanceADone.setVisibility(View.VISIBLE);
            attendancePDone.setVisibility(GONE);
        }

        attendancePDone.setOnClickListener(view -> {
            //attendancePDone.setVisibility(View.VISIBLE);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.ATTEND_UPDATE+studShit.getPkAttendId(), response -> {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(mCtx.getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> Toast.makeText(mCtx.getApplicationContext(), "No Network!", Toast.LENGTH_SHORT).show()) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("att_status", "A");
                    params.put("student_id", STUDENT_ID);
                    params.put("lecturer_id", LECTURER_ID);
                    params.put("module_id", MODULE_ID);
                    params.put("att_day", ATTEND_DAY);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
            requestQueue.add(stringRequest);

        });

        return LVItem;
    }
}











class StClassReportAdapter extends ArrayAdapter<Student> {

    private final List<Student> studentList;
    private final Context mCtx;
    int totalStudents;
    int GOT_LAST_DAY;
    private final ArrayList<String> student_details = new ArrayList<>();
    private List<Report> reportList;

    private ArrayList<Report> sReportArrayList;

    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final String CURRENT_DAY = dateFormat.format(calendar.getTime());

    public StClassReportAdapter(List<Student> studentList, Context appContext) {
        super(appContext, R.layout.list_layout_student_class_rep, studentList);
        this.mCtx = appContext;
        this.studentList = studentList;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View LVItem = inflater.inflate(R.layout.list_layout_student_class_rep, null, true);

        TextView textViewNames = LVItem.findViewById(R.id.tvStudentNames);
        TextView textViewRN = LVItem.findViewById(R.id.tvStudentRegNo);
        TextView textViewCL = LVItem.findViewById(R.id.tvStudentClass);
        TextView textViewMD = LVItem.findViewById(R.id.tvStudentAttMod);
        TextView textViewTM = LVItem.findViewById(R.id.tvStudentShowTym);
        TextView textViewST = LVItem.findViewById(R.id.tvAttendanceStatus);
        TextView textViewDT = LVItem.findViewById(R.id.tvActionDate);

        Student studShit = studentList.get(position);
        textViewNames.setText("" + studShit.getFirstname() + " " + studShit.getLastname());
        textViewRN.setText("Reg No: " + studShit.getRegNumber());
        Staff staffMember = SharedPrefManager.getInstance(mCtx.getApplicationContext()).getLoggedInStaff();
        String CLASS_ID = String.valueOf(studShit.getFinalClassId());
        String MODULE_ID = String.valueOf(studShit.getFinalModuleId());
        String LECTURER_ID = String.valueOf(staffMember.getStaffId());
        String STUDENT_ID = String.valueOf(studShit.getStudentId());

        String FINAL_URL_AB = STUDENT_ID + "&mod_id=" + MODULE_ID + "&date=" + CURRENT_DAY + "&status=A";
        String FINAL_URL_PR = STUDENT_ID + "&mod_id=" + MODULE_ID + "&date=" + CURRENT_DAY + "&status=P";
        String STUDENT_DET_X = STUDENT_ID + "&date="+CURRENT_DAY;
        StringRequest detailRequest = new StringRequest(Request.Method.GET, URLs.REPORT_STUD_MAIN_X+STUDENT_DET_X, response -> {
            try {
                ArrayList<Report> studentArrayList = new ArrayList<>();
                JSONObject dataObject = new JSONObject(response);
                JSONArray dataArray = dataObject.getJSONArray("student_main_report");

                for (int i = 0; i < dataArray.length(); i++) {
                    Report report = new Report();
                    JSONObject dataObj = dataArray.getJSONObject(i);

                    if(dataObj.optString("att_status").equals("P")) {
                        textViewST.setText("Present");
                    } else {
                        textViewST.setText("Absent");
                    }

                    textViewDT.setText(dataObj.optString("att_date"));
                    textViewCL.setText("Class: "+dataObj.optString("class_name"));
                    textViewMD.setText("Module: "+dataObj.optString("module_name"));
                    textViewTM.setText("Attended "+dataObj.optString("show_times")+" times");
                    studentArrayList.add(report);
                }
            } catch (JSONException error) {
                error.printStackTrace();
            }
        }, error -> Toast.makeText(mCtx.getApplicationContext(), "Network issues!", Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue7 = Volley.newRequestQueue(mCtx.getApplicationContext());
        requestQueue7.add(detailRequest);

        return LVItem;
    }

    // UNUSED...
    private void returnStudentDX(String STUDENT, String MODULE, String CURRENT_DATE, String STATUS) {
        String FINAL_URL_PR = STUDENT+ "&mod_id="+MODULE+"&date="+CURRENT_DATE+"&status=P";
        StringRequest awesomeRequest = new StringRequest(Request.Method.GET, URLs.REPORT_STUD_VIEW_X+FINAL_URL_PR + 2, response -> {
            try {
                JSONObject data = new JSONObject(response);
                if (data.optString("status").equals("fetched")) {
                    sReportArrayList = new ArrayList<>();
                    JSONArray dataArray = data.getJSONArray("student_report_x");

                    for (int i = 0; i < dataArray.length(); i++) {
                        Report report = new Report();
                        JSONObject dataObj = dataArray.getJSONObject(i);

                        report.setsFName(data.getString("firstname"));
                        report.setsLName(data.getString("lastname"));
                        report.setsRegNo(data.getString("regno"));
                        report.setsDept(data.getString("dept_name"));
                        report.setsClass(data.getString("class_name"));
                        report.setsShows(data.getString("show_times"));
                        report.setsPres(data.getString("att_status"));
                        report.setsAbsence(data.getString("att_status"));
                        report.setActDate(data.getString("att_date"));
                        sReportArrayList.add(report);
                    }
                }
            } catch (JSONException error) {
                error.printStackTrace();
            }
        }, error -> Toast.makeText(mCtx.getApplicationContext(), "No Network!", Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue4 = Volley.newRequestQueue(mCtx.getApplicationContext());
        requestQueue4.add(awesomeRequest);
    }
}




class StClassReportDoqAdapter extends ArrayAdapter<Student> {

    private final List<Student> studentList;
    private final Context mCtx;
    String FINAL_0_MODULE, FINAL_0_STUDENT;

    private ArrayList<Report> sReportArrayList;

    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final String CURRENT_DAY = dateFormat.format(calendar.getTime());

    public StClassReportDoqAdapter(List<Student> studentList, Context appContext) {
        super(appContext, R.layout.layout_student_final_details, studentList);
        this.mCtx = appContext;
        this.studentList = studentList;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View LVItem = inflater.inflate(R.layout.layout_student_final_details, null, true);

        Staff staffMember = SharedPrefManager.getInstance(mCtx.getApplicationContext()).getLoggedInStaff();

        TextView tvFName = LVItem.findViewById(R.id.tvFName);
        TextView tvLName = LVItem.findViewById(R.id.tvLName);
        TextView tvRegNo = LVItem.findViewById(R.id.tvRegNo);
        TextView tvTelephone = LVItem.findViewById(R.id.tvTelephone);
        TextView tvGender = LVItem.findViewById(R.id.tvGender);
        TextView tvShowTimes = LVItem.findViewById(R.id.tvShowTimes);
        TextView studentFinalReport = LVItem.findViewById(R.id.studentFinalReport);

        List<Report> reportFinalList = new ArrayList<>();
        ListView listViewFinalStudents = LVItem.findViewById(R.id.lvFinalModStudents);

        Student studShit = studentList.get(position);

        tvFName.setText(studShit.getFirstname());
        tvLName.setText(studShit.getLastname());
        tvRegNo.setText(studShit.getRegNumber());
        tvTelephone.setText(studShit.getTelephone());
        tvGender.setText(studShit.getGender());

        StringRequest attTimes1 = new StringRequest(Request.Method.GET, URLs.CLASS_DEPT_CLASS, response -> {
            try {
                JSONObject object = new JSONObject(response);
                // tvShowTimes.setText("##"+" times!");
            } catch (JSONException error) {
                error.printStackTrace();
            }
        }, error -> Toast.makeText(mCtx.getApplicationContext(), "No Network", Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue7 = Volley.newRequestQueue(mCtx.getApplicationContext());
        requestQueue7.add(attTimes1);


        studentFinalReport.setOnClickListener(v -> {
            String studentIdKey = String.valueOf(studShit.getStudentId());
            String classIdKey = String.valueOf(studShit.getClassId());
            Intent i = new Intent(mCtx.getApplicationContext(), StudentFinalResult.class);
            i.putExtra("student_id_final_key", studentIdKey);
            i.putExtra("ref_class_id_key", classIdKey); // For back reason
            i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mCtx.startActivity(i);
        });

        return LVItem;
    }

    // UNUSED...
    private void returnStudentDX(String STUDENT, String MODULE, String CURRENT_DATE, String STATUS) {
        String FINAL_URL_PR = STUDENT+ "&mod_id="+MODULE+"&date="+CURRENT_DATE+"&status=P";
        StringRequest awesomeRequest = new StringRequest(Request.Method.GET, URLs.REPORT_STUD_VIEW_X+FINAL_URL_PR + 2, response -> {
            try {
                JSONObject data = new JSONObject(response);
                if (data.optString("status").equals("fetched")) {
                    sReportArrayList = new ArrayList<>();
                    JSONArray dataArray = data.getJSONArray("student_report_x");

                    for (int i = 0; i < dataArray.length(); i++) {
                        Report report = new Report();
                        JSONObject dataObj = dataArray.getJSONObject(i);

                        report.setsFName(data.getString("firstname"));
                        report.setsLName(data.getString("lastname"));
                        report.setsRegNo(data.getString("regno"));
                        report.setsDept(data.getString("dept_name"));
                        report.setsClass(data.getString("class_name"));
                        report.setsShows(data.getString("show_times"));
                        report.setsPres(data.getString("att_status"));
                        report.setsAbsence(data.getString("att_status"));
                        report.setActDate(data.getString("att_date"));
                        sReportArrayList.add(report);
                    }
                }
            } catch (JSONException error) {
                error.printStackTrace();
            }
        }, error -> Toast.makeText(mCtx.getApplicationContext(), "No Network!", Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue4 = Volley.newRequestQueue(mCtx.getApplicationContext());
        requestQueue4.add(awesomeRequest);
    }


}











class StudentADetails extends ArrayAdapter<Student> {

    private final List<Student> studentList;
    private final Context mCtx;

    StudentADetails(List<Student> studentList, Context mCtx) {
        super(mCtx, R.layout.layout_student_details, studentList);
        this.mCtx = mCtx;
        this.studentList = studentList;
    }

    @NonNull
    @Override
    @SuppressLint("ViewHolder")
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View LVItem = inflater.inflate(R.layout.layout_student_details, null, true);

        // Getting views
        Button buttonExit = LVItem.findViewById(R.id.buttonExit);

        TextView tvTopTitle = LVItem.findViewById(R.id.tvTopTitle);
        TextView textViewFN = LVItem.findViewById(R.id.tvFullName);
        TextView textViewRN = LVItem.findViewById(R.id.tvRegNumber);
        TextView textViewEM = LVItem.findViewById(R.id.tvEmail);
        TextView textViewPH = LVItem.findViewById(R.id.tvPhoneNo);
        TextView textViewGD = LVItem.findViewById(R.id.tvGender);
        TextView textViewCD = LVItem.findViewById(R.id.tvClassDept);

        // Getting record of the specified position
        Student studentModel = studentList.get(position);

        // Adding data to views Constructor class
        tvTopTitle.setText("Student details".toUpperCase());
        textViewFN.setText("Names: " + studentModel.getFirstname() + " " + studentModel.getLastname());
        textViewRN.setText("Reg.No: " + studentModel.getRegNumber());
        textViewEM.setText("Email: " + studentModel.getEmail());
        textViewPH.setText("Tel.No: " + studentModel.getTelephone());
        textViewGD.setText("Gender: " + studentModel.getGender());
        textViewCD.setText("From " + studentModel.getClassName().toUpperCase() + " in " + studentModel.getClassDept().toUpperCase() + " department");

        // For details
        buttonExit.setOnClickListener(v -> {
            Intent i = new Intent(mCtx.getApplicationContext(), StudentViewAll.class);

            i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mCtx.startActivity(i);
        });

        // Return the list item
        return LVItem;
    }
}











class StudentFinalData extends ArrayAdapter<Report> {
    private final List<Report> studentModuleList;
    private final Context mCtx;

    public StudentFinalData(List<Report> studentModuleList, Context appContext) {
        super(appContext, R.layout.list_layout_student_finals, studentModuleList);
        this.mCtx = appContext;
        this.studentModuleList = studentModuleList;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View LVItem = inflater.inflate(R.layout.list_layout_student_finals, null, true);
        TextView tvLecturerNames = LVItem.findViewById(R.id.tvLecturerNames);
        TextView tvModuleName = LVItem.findViewById(R.id.tvModuleName);
        TextView tvModuleCode = LVItem.findViewById(R.id.tvModuleCode);
        TextView marksView = LVItem.findViewById(R.id.marksView);
        TextView tvPresence = LVItem.findViewById(R.id.tvPresence);
        TextView tvAbsence = LVItem.findViewById(R.id.tvAbsence);

        Report rModel = studentModuleList.get(position);
        tvModuleName.setText("M.Name: " + rModel.getxModuleName());
        tvModuleCode.setText("M.Code: " + rModel.getxModuleCode());
        tvLecturerNames.setText("Lect. " + rModel.getxStaffFN()+" "+rModel.getxStaffLN());

        String STUDENT_ABS = "A&att_stud="+rModel.getxStudId()+"&att_module="+rModel.getxModuleId();
        String STUDENT_PRE = "P&att_stud="+rModel.getxStudId()+"&att_module="+rModel.getxModuleId();

        StringRequest countClassReq1 = new StringRequest(Request.Method.GET, URLs.DOQ_STUDENT_M_ATTEND+STUDENT_PRE, response -> {
            try {
                JSONObject object = new JSONObject(response);
                tvPresence.setText(object.optString("counts")+"P");
            } catch (JSONException error) {
                error.printStackTrace();
            }
        }, error -> Toast.makeText(mCtx.getApplicationContext(), "No Network", Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue4 = Volley.newRequestQueue(mCtx.getApplicationContext());
        requestQueue4.add(countClassReq1);


        StringRequest countClassReq2 = new StringRequest(Request.Method.GET, URLs.DOQ_STUDENT_M_ATTEND+STUDENT_ABS, response -> {
            try {
                JSONObject object = new JSONObject(response);
                tvAbsence.setText(object.optString("counts")+"X");
            } catch (JSONException error) {
                error.printStackTrace();
            }
        }, error -> Toast.makeText(mCtx.getApplicationContext(), "No Network", Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue5 = Volley.newRequestQueue(mCtx.getApplicationContext());
        requestQueue5.add(countClassReq2);



        // Set percentages & Eligibility
        showDamnProgressDialog(mCtx.getApplicationContext(), "Loading...","Getting data",false);
        StringRequest finalShow = new StringRequest(Request.Method.GET, URLs.DOQ_STUD_PERCENTAGE+rModel.getxStudId()+"&att_module="+rModel.getxModuleId(), response -> {
            try {
                JSONObject dataOb = new JSONObject(response);
                JSONArray dataArr = dataOb.getJSONArray("stud_marks_mod");
                for (int k = 0; k < dataArr.length(); k++) {
                    int percents = Integer.parseInt(dataOb.optString("percentage"));

                    if (percents >= 100 || percents >= 80) {
                        marksView.setText(percents+"% / Eligible");
                    } else if (percents<80 || percents >=0) {
                        marksView.setText(percents+"% / Not Eligible");
                    }
                }
            } catch (JSONException error) {
                error.printStackTrace();
            }
        }, error -> Toast.makeText(mCtx.getApplicationContext(), "Network issues!", Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue30 = Volley.newRequestQueue(mCtx.getApplicationContext());
        requestQueue30.add(finalShow);


        return LVItem;
    }
}