package com.caregiver.healthcare;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Vitals_dialog {
    final static String submit_vitals = "https://spotters.tech/health/android/vitals_info.php";

    Activity activity;
    AlertDialog dialog;

EditText temperature,pulse,respiration,bloodpressure,pulse_oximetry,pain,bloodsugar;
Button submit;
ImageView cancel;
    Vitals_dialog(Activity activity){
        this.activity = activity;
    }

    public void showdialog(String staff_id, String user_id, String user_name, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.vitals_dialog, null, false);
        temperature = (EditText) view.findViewById(R.id.temperature);
        pulse = (EditText) view.findViewById(R.id.pulse);
        respiration = (EditText) view.findViewById(R.id.respiration);
        bloodpressure = (EditText) view.findViewById(R.id.bloodpressure);
        pulse_oximetry = (EditText) view.findViewById(R.id.PulseOximetry);
        pain = (EditText) view.findViewById(R.id.pain);
        bloodsugar = (EditText) view.findViewById(R.id.bloodsugar);
         submit = (Button) view.findViewById(R.id.submit);
         cancel = (ImageView) view.findViewById(R.id.cancelvitals);

        //cancel(cancel);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Ttemp = temperature.getText().toString().trim();
                String Tpulse = pulse.getText().toString().trim();
                String Trespiration = respiration.getText().toString().trim();
                String Tbloodpressure = bloodpressure.getText().toString().trim();
                String Tpulse_oximetry = pulse_oximetry.getText().toString().trim();
                String Tpain = pain.getText().toString().trim();
                String Tbloodsugar = bloodsugar.getText().toString().trim();

                if (Ttemp.isEmpty() || Tpulse.isEmpty() || Trespiration.isEmpty() || Tbloodpressure.isEmpty() || Tpulse_oximetry.isEmpty() || Tpain.isEmpty() || Tbloodsugar.isEmpty()) {
                    temperature.setError("Empty field");
                    pulse.setError("Empty field");
                    respiration.setError("Empty field");
                    bloodpressure.setError("Empty field");
                    pulse_oximetry.setError("Empty field");
                    pain.setError("Empty field");
                    bloodsugar.setError("Empty field");
                }else{
                    submitvitals( staff_id, user_id, user_name, context);
                }

            }
        });
        
        builder.setView(view);

        dialog = builder.create();
        dialog.setCancelable(true);
        builder.show();
    }

    private void submitvitals(String staff_id, String user_id, String user_name, Context context) {
        String Ttemp = temperature.getText().toString().trim();
        String Tpulse = pulse.getText().toString().trim();
        String Trespiration = respiration.getText().toString().trim();
        String Tbloodpressure = bloodpressure.getText().toString().trim();
        String Tpulse_oximetry = pulse_oximetry.getText().toString().trim();
        String Tpain = pain.getText().toString().trim();
        String Tbloodsugar = bloodsugar.getText().toString().trim();



       submit.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, submit_vitals, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    if (success.equals("1")){

                        submit.setVisibility(View.VISIBLE);
                        Toast.makeText(context, "Vitals Recorded", Toast.LENGTH_SHORT).show();
                    dismissDialog();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    //loadingdialog.dismissDialog();
                    submit.setVisibility(View.VISIBLE);
                    Toast.makeText(context, "profile updated Failed" + e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                submit.setVisibility(View.VISIBLE);
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("temperature", Ttemp);
                params.put("pulse", Trespiration);
                params.put("respiration", Trespiration);
                params.put("bloodpressure", Tbloodpressure);
                params.put("pulseoximetry", Tpulse_oximetry);
                params.put("pain", Tpain);
                params.put("bloodsugar", Tbloodsugar);
                params.put("user_name", user_name);
                params.put("user_id", user_id);
                params.put("staff_id", staff_id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    void dismissDialog() {
        dialog.dismiss();
    }

//    //void  cancel(ImageView cancel){
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dismissDialog();
//            }
//        });
//    }

}
