package com.donnekt.attendanceapp.module;

import static android.content.Intent.FLAG_ACTIVITY_NO_HISTORY;
import static android.view.View.GONE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.donnekt.attendanceapp.R;
import com.donnekt.attendanceapp.SharedPrefManager;
import com.donnekt.attendanceapp.URLs;
import com.donnekt.attendanceapp.classroom.Classroom;
import com.donnekt.attendanceapp.classroom.ClassroomGroup;
import com.donnekt.attendanceapp.classroom.ClassroomUpdate;
import com.donnekt.attendanceapp.staff.Staff;
import com.donnekt.attendanceapp.student.AttendanceAct;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.donnekt.attendanceapp.DialogShit.exitDamnProgressDialog;
import static com.donnekt.attendanceapp.DialogShit.showDamnProgressDialog;
import static com.donnekt.attendanceapp.Methods.capitalizeAllFirstLetters;

public class ModuleAdapter extends ArrayAdapter<Module> {

    protected Context mCtx;
    private final List<Module> moduleList;

    public ModuleAdapter(List<Module> moduleList, Context mCtx) {
        super(mCtx, R.layout.list_layout_module, moduleList);
        this.mCtx = mCtx;
        this.moduleList = moduleList;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View LVItem = inflater.inflate(R.layout.list_layout_module, null, true);

        // Getting module of the specified position
        Module module = moduleList.get(position);

        // Getting views
        TextView textViewModuleName = LVItem.findViewById(R.id.tvModuleName);
        TextView textViewModuleCode = LVItem.findViewById(R.id.tvModuleCode);
        TextView textViewLecturerId = LVItem.findViewById(R.id.tvModLecturer);
        TextView textViewAllClasses = LVItem.findViewById(R.id.tvModuleClasses);
        Button buttonEdit = LVItem.findViewById(R.id.buttonEdit);
        Button buttonDelete   = LVItem.findViewById(R.id.buttonDeleteMod);
        Button buttonManageMod= LVItem.findViewById(R.id.buttonManageMod);

        // Adding data to views
        textViewModuleName.setText(module.getModuleName());
        textViewModuleCode.setText("Code: "+module.getModuleCode());
        String fullName = capitalizeAllFirstLetters(module.getlFirstName()+" "+module.getlLastName());
        textViewLecturerId.setText("Lect. "+fullName);

        showAllClassroomByGroup(String.valueOf(module.getModuleId()));

        String moduleIDShit= String.valueOf(module.getModuleId());
        String M_NM= String.valueOf(module.getModuleName());
        String M_CD= String.valueOf(module.getModuleCode());

        Staff member = SharedPrefManager.getInstance(mCtx.getApplicationContext()).getLoggedInStaff();
        String sRole = member.getRole();

        if(sRole.equals("HOD")) {
            buttonEdit.setVisibility(GONE);
            buttonDelete.setVisibility(GONE);
        }

        // UPDATE SHIT
        buttonEdit.setOnClickListener(view -> {
            Intent g = new Intent(mCtx.getApplicationContext(), ModuleUpdate.class);
            g.putExtra("m_id_key", moduleIDShit);
            g.putExtra("m_name_key", M_NM);
            g.putExtra("m_code_key", M_CD);
            g.setFlags(FLAG_ACTIVITY_NO_HISTORY);
            g.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mCtx.startActivity(g);

        });


        // Adding a clickListener to button
        buttonManageMod.setOnClickListener(view -> {
            Intent g = new Intent(mCtx.getApplicationContext(), ModuleAssigning.class);
            g.putExtra("module_id_key", moduleIDShit);
            g.putExtra("module_name_key", module.getModuleName());
            g.setFlags(FLAG_ACTIVITY_NO_HISTORY);
            g.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mCtx.startActivity(g);
            // For killing activity
        });

        // D&U-SHIT
        buttonDelete.setOnClickListener(view -> {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.MOD_DELETE+module.getModuleId(),
                    response -> {
                Intent intent=new Intent(mCtx.getApplicationContext(), ModuleViewAll.class);
                        intent.setFlags(FLAG_ACTIVITY_NO_HISTORY);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mCtx.startActivity(intent);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(mCtx.getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            exitDamnProgressDialog();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> Toast.makeText(mCtx.getApplicationContext(), "No network!", Toast.LENGTH_SHORT).show()) {
            };
            RequestQueue requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
            requestQueue.add(stringRequest);
        });


        // Show assigned classes
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.MOD_VIEW_GROUP+module.getModuleId(), response -> {
            try {

                ArrayList<Classroom> assignedMList = new ArrayList<>();

                JSONObject dataObject = new JSONObject(response);
                JSONArray dataArray = dataObject.getJSONArray("modulegroup");

                for(int i=0; i<dataArray.length(); i++){
                    Classroom classModel = new Classroom();
                    JSONObject dataObj = dataArray.getJSONObject(i);
                    textViewAllClasses.setText("Classes ("+dataObj.getString("class_name")+")");
                    assignedMList.add(classModel);
                }
            } catch (JSONException error) {
                error.printStackTrace();
            }
        }, error -> Toast.makeText(mCtx.getApplicationContext(), "Network issues!", Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        requestQueue.add(stringRequest);

        // Return the list item
        return LVItem;
    }

    

    private void showAllClassroomByGroup(String MODULE_ID) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.MOD_VIEW_GROUP+MODULE_ID, response -> {
            try {
                JSONObject object = new JSONObject(response);
                JSONArray dataArray = object.getJSONArray("modulegroup");

                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject dataObject = dataArray.getJSONObject(i);
                    Classroom classroom = new Classroom(
                            dataObject.getInt("class_id"),
                            dataObject.getString("class_name"),
                            dataObject.getString("class_level"),
                            dataObject.getInt("module_id"),
                            dataObject.getString("module_name"),
                            dataObject.getString("module_code"),
                            dataObject.getString("dept_name"),
                            dataObject.getString("dept_caption")
                    );
                }

            } catch (JSONException error) {
                error.printStackTrace();
            }
        }, error -> Toast.makeText(mCtx.getApplicationContext(), "Network issues!", Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        requestQueue.add(stringRequest);
    }
}







class ModuleAdapterML extends ArrayAdapter<Module> {
    private final Context mCtx;
    private final List<Module> moduleLecturerList;

    public ModuleAdapterML(List<Module> moduleLecturerList, Context mCtx) {
        super(mCtx, R.layout.list_layout_modlect, moduleLecturerList);
        this.mCtx = mCtx;
        this.moduleLecturerList = moduleLecturerList;
    }

    @SuppressLint({"ViewHolder", "InflateParams", "SetTextI18n"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View LVItem = inflater.inflate(R.layout.list_layout_modlect, null, true);

        // Getting module of the specified position
        Module module = moduleLecturerList.get(position);

        // Getting views
        TextView textViewModuleName = LVItem.findViewById(R.id.tvModuleName);
        TextView textViewModuleCode = LVItem.findViewById(R.id.tvModuleCode);
        TextView tvModTotalClasses = LVItem.findViewById(R.id.tvModTotalClasses);
        Button makeAttendance = LVItem.findViewById(R.id.makeAttendance);
        Button attendanceDetails = LVItem.findViewById(R.id.attendanceDetails);
        ProgressBar deleteProgBar = LVItem.findViewById(R.id.deleteProgBar);

        // Adding data to views
        textViewModuleName.setText("Name: "+module.getModuleName());
        textViewModuleCode.setText("Code ("+module.getModuleCode()+" )");

        // tvModTotalClasses.set something in!
        StringRequest countClassGetModz = new StringRequest(Request.Method.GET, URLs.MOD_VIEW_GROUP+module.getModuleId(), response -> {
            try {
                JSONObject object = new JSONObject(response);

                // Push shit!
                int classCount = Integer.parseInt(object.optString("counts"));
                String initShit = classCount == 1  ? classCount+" class " : classCount+" classes ";
                String countFullInit = classCount == 0 ? "No class " : initShit;
                String getOrGets = classCount <= 1 ? " gets " : " get ";
                tvModTotalClasses.setText(countFullInit+""+getOrGets+" this course!");
            } catch (JSONException error) {
                error.printStackTrace();
            }
        }, error -> Toast.makeText(mCtx.getApplicationContext(), "Network Issues", Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        requestQueue.add(countClassGetModz);

        // Go through Attendance shit
        // See classrooms receive this course!
        String moduleID = String.valueOf(module.getModuleId());

        makeAttendance.setOnClickListener(v -> {

            Intent g = new Intent(mCtx.getApplicationContext(), ClassroomGroup.class);
            g.putExtra("module_id_key", moduleID);

            g.setFlags(FLAG_ACTIVITY_NO_HISTORY);
            g.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mCtx.startActivity(g);

            //Intent h = new Intent(mCtx.getApplicationContext(), AttendanceAct.class);
            //h.putExtra("final_module_id_key", moduleID);

        });

        attendanceDetails.setOnClickListener(view -> {
            Intent g = new Intent(mCtx.getApplicationContext(), ModuleDetails.class);
            g.putExtra("module_id_key", moduleID);
            g.setFlags(FLAG_ACTIVITY_NO_HISTORY);
            g.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mCtx.startActivity(g);

        });

        // Return the list item
        return LVItem;
    }
}




// About Report:
class ModuleAdapterMLReport extends ArrayAdapter<Module> {
    private final Context mCtx;
    private final List<Module> moduleLecturerList;

    public ModuleAdapterMLReport(List<Module> moduleLecturerList, Context mCtx) {
        super(mCtx, R.layout.list_layout_modlect_report, moduleLecturerList);
        this.mCtx = mCtx;
        this.moduleLecturerList = moduleLecturerList;
    }

    @SuppressLint({"ViewHolder", "InflateParams", "SetTextI18n"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View LVItem = inflater.inflate(R.layout.list_layout_modlect_report, null, true);
        Module module = moduleLecturerList.get(position);
        TextView textViewModuleName = LVItem.findViewById(R.id.tvModuleName);
        TextView textViewModuleCode = LVItem.findViewById(R.id.tvModuleCode);
        TextView tvModTotalClasses = LVItem.findViewById(R.id.tvModTotalClasses);
        Button reportStudents = LVItem.findViewById(R.id.reportStudents);

        textViewModuleName.setText("Name: "+module.getModuleName());
        textViewModuleCode.setText("Code ("+module.getModuleCode()+" )");

        StringRequest countClassGetModz = new StringRequest(Request.Method.GET, URLs.MOD_VIEW_GROUP+module.getModuleId(), response -> {
            try {
                JSONObject object = new JSONObject(response);

                int classCount = Integer.parseInt(object.optString("counts"));
                String initShit = classCount == 1  ? classCount+" class " : classCount+" classes ";
                String countFullInit = classCount == 0 ? "No class " : initShit;
                String getOrGets = classCount <= 1 ? " gets " : " get ";
                tvModTotalClasses.setText(countFullInit+""+getOrGets+" this course!");
            } catch (JSONException error) {
                error.printStackTrace();
            }
        }, error -> Toast.makeText(mCtx.getApplicationContext(), "Network Issues", Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        requestQueue.add(countClassGetModz);

        String moduleID = String.valueOf(module.getModuleId());

        reportStudents.setOnClickListener(v -> {
            Intent g = new Intent(mCtx.getApplicationContext(), ClassroomGroup.class);
            g.putExtra("module_id_key", moduleID);

            g.setFlags(FLAG_ACTIVITY_NO_HISTORY);
            g.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mCtx.startActivity(g);
        });

        return LVItem;
    }
}




class ModuleAdapterDetail extends ArrayAdapter<Module> {

    private final Context mCtx;
    private final List<Module> moduleLecturerList;

    public ModuleAdapterDetail(List<Module> moduleLecturerList, Context mCtx) {
        super(mCtx, R.layout.layout_module_details, moduleLecturerList);
        this.mCtx = mCtx;
        this.moduleLecturerList = moduleLecturerList;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater inflater = LayoutInflater.from(mCtx);
            View LVItem = inflater.inflate(R.layout.layout_module_details, null, true);

            // Getting module of the specified position
            Module module = moduleLecturerList.get(position);

            // Getting views
            TextView textViewModuleName = LVItem.findViewById(R.id.tvModuleName);
            TextView textViewModuleCode = LVItem.findViewById(R.id.tvModuleCode);
            TextView tvModTotalClasses = LVItem.findViewById(R.id.tvModTotalClasses);
            TextView tvModuleLecturer = LVItem.findViewById(R.id.tvModuleLecturer);
            TextView tvMLecturerEmail = LVItem.findViewById(R.id.tvMLecturerEmail);
            Button makeAttendance = LVItem.findViewById(R.id.makeAttendance);
            Button attendanceDetails = LVItem.findViewById(R.id.attendanceDetails);

            // Adding data to views
            textViewModuleName.setText(module.getModuleName());
            textViewModuleCode.setText(module.getModuleCode());
            tvModuleLecturer.setText(module.getlFirstName() + " " + module.getlLastName());
            tvMLecturerEmail.setText(module.getlEmil());

        // Return the list item
        return LVItem;
    }
}