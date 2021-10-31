package com.donnekt.attendanceapp.module;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.donnekt.attendanceapp.R;
import com.donnekt.attendanceapp.student.AttendanceAct;
import org.json.JSONException;

import java.util.List;

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
        ProgressBar deleteProgBar = LVItem.findViewById(R.id.deleteProgBar);

        // Adding data to views
        textViewModuleName.setText(module.getModuleName());
        textViewModuleCode.setText(module.getModuleCode());
        String fullName = capitalizeAllFirstLetters(module.getlFirstName()+" "+module.getlLastName());
        textViewLecturerId.setText("By "+fullName);

        // We will use these buttons later for update and delete operation
        Button buttonDelete = LVItem.findViewById(R.id.buttonDeleteMod);
        Button buttonEdit = LVItem.findViewById(R.id.buttonEditMod);



        // Adding a clickListener to button
        buttonEdit.setOnClickListener(view -> {
            //updateModule(module);
        });

        // D&U-SHIT
        buttonDelete.setOnClickListener(view -> {
            showDamnProgressDialog(mCtx.getApplicationContext(), "Loading...","Removing course",true);
            /*StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.MOD_DELETE+module.getModuleId(),
                    response -> {
                        mCtx.startActivity(new Intent(mCtx.getApplicationContext(), ModuleViewAll.class));
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
            requestQueue.add(stringRequest);*/
        });

        // Return the list item
        return LVItem;
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
        tvModTotalClasses.setText("4");


        // Go through Attendance shit

        makeAttendance.setOnClickListener(view -> {
            Intent g = new Intent(mCtx.getApplicationContext(), AttendanceAct.class);
            g.putExtra("student_at_key", module.getLecturerId());
            mCtx.startActivity(g);
        });

        attendanceDetails.setOnClickListener(view -> {
            Intent g = new Intent(mCtx.getApplicationContext(), ModuleDetails.class);
            String moduleId = String.valueOf(module.getModuleId());
            g.putExtra("module_id_key", moduleId);
            mCtx.startActivity(g);
            g.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY); // For killing activity
        });

        // Return the list item
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

            TextView tvModuleHours = LVItem.findViewById(R.id.tvModuleHours);
            Button makeAttendance = LVItem.findViewById(R.id.makeAttendance);
            Button attendanceDetails = LVItem.findViewById(R.id.attendanceDetails);

            // Adding data to views
            textViewModuleName.setText(module.getModuleName());
            textViewModuleCode.setText(module.getModuleCode());
            tvModuleLecturer.setText(module.getlFirstName() + " " + module.getlLastName());
            tvMLecturerEmail.setText(module.getlEmil());
            tvModuleHours.setText("55 hours");
            //tvModTotalClasses.setText("67");

        // Return the list item
        return LVItem;
    }
}