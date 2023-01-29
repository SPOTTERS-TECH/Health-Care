package com.caregiver.healthcare;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class Password extends AppCompatActivity {

    Loadingdialog loadingdialog = new Loadingdialog(this);
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_FNAME = "fname";
    private static final String KEY_LNAME = "lname";
    private static final String KEY_ID = "id";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_GENDER = "status";

    EditText old_password,new_password,re_type_password;
    Button pass_btn;
    String fid;
    final static String url_updatepassword = "https://spotters.tech/health/android/edit_password.php";

    String fnamen, lnamen, phonen, gender,lemail,lpassword;
    String rider_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        sharedPreferences = this.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        phonen = sharedPreferences.getString(KEY_PHONE, null);
        fnamen = sharedPreferences.getString(KEY_FNAME, null);
        lnamen = sharedPreferences.getString(KEY_LNAME, null);
        gender = sharedPreferences.getString(KEY_GENDER, null);
        lemail = sharedPreferences.getString(KEY_EMAIL, null);
        fid = sharedPreferences.getString(KEY_ID, null);
        gender = sharedPreferences.getString(KEY_GENDER, null);
        lpassword = sharedPreferences.getString(KEY_PASSWORD, null);
        rider_name = fnamen + " " + lnamen;

        old_password = findViewById(R.id.old_password);
        new_password =  findViewById(R.id.new_password);
        re_type_password = findViewById(R.id.Retype_new_password);
        pass_btn =  findViewById(R.id.updatepassbtn);

        pass_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldpass = old_password.getText().toString().trim();
                String newpass = new_password.getText().toString().trim();
                String re_type_pass = re_type_password.getText().toString().trim();

                if(!(oldpass.isEmpty() || newpass.isEmpty() || re_type_pass.isEmpty())){
                    if(lpassword.equals(oldpass)){
                        if(newpass.equals(re_type_pass)){
                            updatepassword();
                        }else{
                            re_type_password.setError("passwords do not match");
                        }
                    }else{
                        old_password.setError("Old password is in-correct");
                    }
                }else{
                    Toast.makeText(Password.this, "passwords cannot be empty", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void updatepassword() {
        loadingdialog.showLoadingDialog();
        String newpassword = this.new_password.getText().toString().trim();
        String id = fid;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_updatepassword, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    if (success.equals("1")){
                        loadingdialog.dismissDialog();
                        Toast.makeText(Password.this, "Passwords updated successfully", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    loadingdialog.dismissDialog();
                    Toast.makeText(Password.this, "error" + e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingdialog.dismissDialog();
                Toast.makeText(Password.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("staff_password", newpassword);
                params.put("staff_id", id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void backbtn(View view) {
        onBackPressed();
    }
}