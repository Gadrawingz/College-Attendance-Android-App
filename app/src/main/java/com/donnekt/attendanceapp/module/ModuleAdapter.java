package com.donnekt.attendanceapp.module;

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
import com.donnekt.attendanceapp.URLs;
import com.donnekt.attendanceapp.classroom.ClassroomViewAll;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ModuleAdapter extends ArrayAdapter<Module> {

    private final Context mCtx;
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
        TextView textViewDepartment = LVItem.findViewById(R.id.tvModDept);
        TextView textViewLecturerId = LVItem.findViewById(R.id.tvModLecturer);
        ProgressBar deleteProgBar = LVItem.findViewById(R.id.deleteProgBar);

        // Adding data to views
        textViewModuleName.setText(module.getModuleName());
        textViewModuleCode.setText(module.getModuleCode());
        textViewDepartment.setText(module.getDepartmentId());
        textViewLecturerId.setText(module.getLecturerId());

        // We will use these buttons later for update and delete operation
        Button buttonDelete = LVItem.findViewById(R.id.buttonDeleteMod);
        Button buttonEdit = LVItem.findViewById(R.id.buttonEditMod);


        // Adding a clickListener to button
        buttonEdit.setOnClickListener(view -> {
            //updateModule(module);
        });

        // D&U-SHIT
        buttonDelete.setOnClickListener(view -> {
            deleteProgBar.setVisibility(View.VISIBLE);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.MOD_DELETE+module.getModuleId(),
                    response -> {
                        mCtx.startActivity(new Intent(mCtx.getApplicationContext(), ModuleViewAll.class));
                        try {
                            deleteProgBar.setVisibility(View.INVISIBLE);
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(mCtx.getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> Toast.makeText(mCtx.getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show()) {
            };
            RequestQueue requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
            requestQueue.add(stringRequest);
        });

        // Return the list item
        return LVItem;
    }

}