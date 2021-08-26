package com.donnekt.attendanceapp;
import com.donnekt.attendanceapp.R;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class CustomToast {

    // Custom Toast method
    public void ShowToast(Context context, View view, String error) {

        // Layout Inflater for inflating custom view
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Inflate the layout over view
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) view.findViewById(R.id.toast_root));

        // Get TextView id and set error
        TextView text = (TextView) layout.findViewById(R.id.toast_error);
        text.setText(error);

        Toast toast = new Toast(context);// Get Toast Context
        toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, 0); // Set

        toast.setDuration(Toast.LENGTH_SHORT);// Set duration
        toast.setView(layout); // Set Custom view over toast
        toast.show();// Finally show toast
    }
}