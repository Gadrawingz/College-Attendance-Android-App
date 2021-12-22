package com.donnekt.attendanceapp.department;

import android.annotation.SuppressLint;
import android.app.backup.BackupDataInputStream;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.donnekt.attendanceapp.R;
import com.donnekt.attendanceapp.URLs;
import com.donnekt.attendanceapp.UpdatingActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class DepartmentAdapter extends ArrayAdapter<Department> {

    private final List<Department> departmentList;
    private final Context mCtx;

    DepartmentAdapter(List<Department> departmentList, Context mCtx) {
        super(mCtx, R.layout.list_layout_staff, departmentList);
        this.mCtx = mCtx;
        this.departmentList = departmentList;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View LVItem = inflater.inflate(R.layout.list_layout_department, null, true);

        // Getting views
        TextView textViewDNAME = LVItem.findViewById(R.id.tvDeptName);
        TextView textViewDCAPT = LVItem.findViewById(R.id.tvDeptCaption);
        Button buttonDelete = LVItem.findViewById(R.id.buttonDeleteDept);
        Button buttonEdit = LVItem.findViewById(R.id.buttonEdit);
        ProgressBar deleteProgBar = LVItem.findViewById(R.id.deleteProgBar);

        // Getting record of the specified position
        Department department = departmentList.get(position);

        String D_ID = String.valueOf(department.getDepartmentId());
        String D_NM = String.valueOf(department.getDepartmentName());
        String D_CP = String.valueOf(department.getDepartmentCaption());

        textViewDNAME.setText(D_NM);
        textViewDCAPT.setText(D_CP);

        // UPDATE SHIT
        buttonEdit.setOnClickListener(view -> {
            Intent g = new Intent(mCtx.getApplicationContext(), DepartmentUpdate.class);
            g.putExtra("d_id_key", D_ID);
            g.putExtra("d_name_key", D_NM);
            g.putExtra("d_caption_key", D_CP);

            g.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            g.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mCtx.startActivity(g);
        });

        // DELETE SHIT:
        buttonDelete.setOnClickListener(view -> {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.DEPT_DELETE + department.getDepartmentId(),
                    response -> {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(mCtx.getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(mCtx.getApplicationContext(), DepartmentViewAll.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mCtx.startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> Toast.makeText(mCtx.getApplicationContext(), "Network Issues", Toast.LENGTH_SHORT).show()) {
            };

            RequestQueue requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
            requestQueue.add(stringRequest);
        });

        // Return the list item
        return LVItem;
    }


}