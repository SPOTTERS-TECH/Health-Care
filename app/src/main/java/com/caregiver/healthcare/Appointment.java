package com.caregiver.healthcare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Appointment extends AppCompatActivity implements AppointmentAdapter.onItemClickListener {
    BottomSheetDialog dialog;
    RecyclerView recyclerView2;
    AppointmentAdapter adapter2;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_FNAME = "fname";
    private static final String KEY_LNAME = "lname";
    private static final String KEY_ID = "id";
    private static final String KEY_GENDER = "status";
    Loadingdialog loadingdialog = new Loadingdialog(this);
    final static String load_items_assigned = "https://spotters.tech/health/android/appointment_request.php";
    String fnamen, lnamen, phonen, gender;
    String rider_name, fid;
    private ArrayList<Product> userListt = new ArrayList<>();
    String idofrequest,user_note,user_gender,appointment_date,appointment_time,admin_note,user_address,user_email,user_phone,user_name,user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        loadingdialog.showLoadingDialog();
        //initialize and assign the variable to the id
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnavigation);

        //setting home as default navigation
        bottomNavigationView.setSelectedItemId(R.id.appointment);
        dialog = new BottomSheetDialog(this);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),Dasboard.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.setting:
                        createdialog();
//                        startActivity(new Intent(getApplicationContext(),Settings.class));
//                        overridePendingTransition(0,0);
                        return true;
                    case R.id.appointment:
                        return true;
                }
                return false;
            }
        });


        sharedPreferences = this.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        phonen = sharedPreferences.getString(KEY_PHONE, null);
        fnamen = sharedPreferences.getString(KEY_FNAME, null);
        lnamen = sharedPreferences.getString(KEY_LNAME, null);
        gender = sharedPreferences.getString(KEY_GENDER, null);
        fid = sharedPreferences.getString(KEY_ID, null);
        gender = sharedPreferences.getString(KEY_GENDER, null);
        rider_name = fnamen + " " + lnamen;

        //  RECYCLERVIEW2
        recyclerView2 = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getApplicationContext());
        recyclerView2.setLayoutManager(layoutManager2);
        layoutManager2.setOrientation(RecyclerView.VERTICAL);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setItemAnimator(new DefaultItemAnimator());

        load_items_accepted();



    }
    private void load_items_accepted() {
        String id = fid;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, load_items_assigned, new Response.Listener<String>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String idofrequest = object.getString("id").trim();
                         user_id = object.getString("user_id").trim();
                         user_name = object.getString("user_name").trim();
                         user_phone = object.getString("user_phone").trim();
                         user_email = object.getString("user_email").trim();
                         user_address = object.getString("user_address").trim();
                         user_note = object.getString("user_note").trim();
                         user_gender = object.getString("user_gender").trim();
                         appointment_date = object.getString("appointment_date").trim();
                         appointment_time = object.getString("appointment_time").trim();
                         admin_note = object.getString("admin_note").trim();


                        Product product = new Product(idofrequest, user_id, user_name, user_phone, appointment_date, appointment_time, user_address, user_gender, user_note, admin_note);

                        userListt.add(0, product);
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                 loadingdialog.dismissDialog();
                            }
                        }, 1000);

                    }


                    adapter2 = new AppointmentAdapter(Appointment.this, userListt);
                    recyclerView2.setAdapter(adapter2);
                    adapter2.notifyDataSetChanged();
                    int count = 0;
                    if (adapter2 != null) {
                        count = adapter2.getItemCount();
                        String counts = String.valueOf(count);
                      //  today_orders.setText("You have "+ counts + " appointment today");
                        if (counts.equals("0")) {
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    loadingdialog.dismissDialog();
                                }
                            }, 1000);
                        }
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "error" + e + toString(), Toast.LENGTH_SHORT).show();
                    loadingdialog.dismissDialog();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(), "error"+ error+toString(), Toast.LENGTH_SHORT).show();
                loadingdialog.dismissDialog();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("staff_id", id);
                params.put("status", "Accepted");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onItemClickproduct(Product product) {
        //Toast.makeText(this, "yooo world", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getApplicationContext(),UserData.class);
        String username = product.getUser_name();
        String user_id = product.getUser_id();
        String user_gender = product.getUser_gender();
        String user_phone = product.getUser_phone();
        String admin_note = product.getAdmin_note();
        String user_note = product.getUser_note();
        String user_address = product.getUser_address();

        i.putExtra("user_name", username);
        i.putExtra("user_id", user_id);
        i.putExtra("user_email", user_email);
        i.putExtra("user_phone", user_phone);
        i.putExtra("user_address", user_address);
        i.putExtra("user_note",user_note);
        i.putExtra("admin_note",admin_note);
        i.putExtra("gender",user_gender);
        startActivity(i);
    }

    public void createdialog() {
        View view = getLayoutInflater().inflate(R.layout.settings_dialog, null, false);
        LinearLayout history = (LinearLayout) view.findViewById(R.id.history_lay);
        LinearLayout edit_password = (LinearLayout) view.findViewById(R.id.password_edit_lay);
        LinearLayout complaint = (LinearLayout) view.findViewById(R.id.complaint_lay);
        TextView terms = (TextView) view.findViewById(R.id.terms_lay);
        TextView policies = (TextView) view.findViewById(R.id.policies_lay);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout logout = (LinearLayout) view.findViewById(R.id.logout);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout abouut = (LinearLayout) view.findViewById(R.id.about_lay);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout profile = (LinearLayout) view.findViewById(R.id.profile_lay);

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                 startActivity(new Intent(getApplicationContext(), History.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Appointment.this)
                        .setTitle("Logout")
                        .setCancelable(false)
                        .setMessage("Are your sure want to exit?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear();
                                editor.apply();
                                startActivity(new Intent(getApplicationContext(),UserLogin.class));
                                ActivityCompat.finishAffinity(Appointment.this);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                builder.show();
            }
        });

        abouut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                startActivity(new Intent(getApplicationContext(), About.class));
            }
        });


//        policies.setMovementMethod(LinkMovementMethod.getInstance());
//        terms.setMovementMethod(LinkMovementMethod.getInstance());
//        policies.setLinkTextColor(getResources().getColor(R.color.main));
//        terms.setLinkTextColor(getResources().getColor(R.color.main));


        complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                //  startActivity(new Intent(getApplicationContext(), Password.class));
            }
        });

        edit_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                startActivity(new Intent(getApplicationContext(), Password.class));
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                 startActivity(new Intent(getApplicationContext(), EditProfile.class));
            }
        });
        dialog.setContentView(view);
        dialog.show();
    }
}