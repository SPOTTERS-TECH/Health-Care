package com.caregiver.healthcare;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Dasboard extends AppCompatActivity implements RecyclerAdapter2.onItemClickListener {

    BottomSheetDialog dialog;
    TextView dateformat,greet;
    RecyclerView recyclerView2;
    RecyclerAdapter2 adapter2;
    String fnamen, lnamen, phonen, gender, lemail;
    String rider_name, fid,lpassword;
    TextView today_orders, username, user_phone;
    private ArrayList<Product> userListt = new ArrayList<>();
    final static String load_items_assigned = "https://spotters.tech/health/android/appointment_request.php";
    final static String url_accept_appointment = "https://spotters.tech/health/android/accept_appointment.php";
    final static String url_decline_appointment = "https://spotters.tech/health/android/decline_appointment.php";
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
   // final Loadingdialog loadingdialog = new Loadingdialog(MainActivity.this);

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasboard);

        loadingdialog.showLoadingDialog();


        dateformat = findViewById(R.id.dateformat);
        username = (TextView) findViewById(R.id.users_name);
        greet = findViewById(R.id.greet);
        today_orders = findViewById(R.id.numofappointment);
        ImageView notifyication = findViewById(R.id.notificationicon);
        dialog = new BottomSheetDialog(this);

        notifyication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Notification.class));
            }
        });

        sharedPreferences = this.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        phonen = sharedPreferences.getString(KEY_PHONE, null);
        fnamen = sharedPreferences.getString(KEY_FNAME, null);
        lnamen = sharedPreferences.getString(KEY_LNAME, null);
        gender = sharedPreferences.getString(KEY_GENDER, null);
         lemail = sharedPreferences.getString(KEY_EMAIL, null);
        lpassword = sharedPreferences.getString(KEY_PASSWORD, null);
        fid = sharedPreferences.getString(KEY_ID, null);
        gender = sharedPreferences.getString(KEY_GENDER, null);
        rider_name = fnamen + " " + lnamen;
        if (phonen != null && fnamen != null && fid != null) {
            username.setText(fnamen);

        }

        //initialize and assign the variable to the id
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnavigation);

        //setting home as default navigation
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        finish();
                        return true;
                    case R.id.appointment:
                        startActivity(new Intent(getApplicationContext(),Appointment.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.setting:
                       createdialog();
                       // overridePendingTransition(0,0);

                        return true;
                }
                return false;
            }
        });

        greet.setText(getgreet());

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");


        Date date2 = new Date();
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("MM");
      String monthnum = simpleDateFormat2.format(date2);

      String months;
        switch(monthnum){
            case "01" :
                months  = "January";
                dateformat.setText("Today, " + simpleDateFormat.format(date) + " " +months );
                break;
            case "02" :
                months  = "February";
                dateformat.setText("Today, " + simpleDateFormat.format(date) + " " +months );
                break;
            case "03" :
                months  = "March";
                dateformat.setText("Today, " + simpleDateFormat.format(date) + " " +months );
                break;
            case "04" :
                months  = "April";
                dateformat.setText("Today, " + simpleDateFormat.format(date) + " " +months );
                break;
            case "05" :
                months  = "May";
                dateformat.setText("Today, " + simpleDateFormat.format(date) + " " +months );
                break;
            case "06" :
                months  = "June";
                dateformat.setText("Today, " + simpleDateFormat.format(date) + " " +months );
                break;
            case "07" :
                months  = "July";
                dateformat.setText("Today, " + simpleDateFormat.format(date) + " " +months );
                break;
            case "08" :
                months  = "August";
                dateformat.setText("Today, " + simpleDateFormat.format(date) + " " +months );
                break;
            case "09" :
                months  = "September";
                dateformat.setText("Today, " + simpleDateFormat.format(date) + " " +months );
                break;
            case "10" :
                months  = "October";
                dateformat.setText("Today, " + simpleDateFormat.format(date) + " " +months );
                break;
            case "11" :
                months  = "November";
                dateformat.setText("Today, " + simpleDateFormat.format(date) + " " +months );
                break;
            case "12" :
                months  = "December";
                dateformat.setText("Today, " + simpleDateFormat.format(date) + " " +months );
                break;
            default:
                Toast.makeText(this, "Error: Unknown Data format", Toast.LENGTH_SHORT).show();
        }


        //  RECYCLERVIEW2
        recyclerView2 = (RecyclerView) findViewById(R.id.recyclerview2);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getApplicationContext());
        recyclerView2.setLayoutManager(layoutManager2);
        layoutManager2.setOrientation(RecyclerView.VERTICAL);
        recyclerView2.setHasFixedSize(true);

load_items_accepted();




    }
    public static String getgreet(){
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay < 12){
            return "Good Morning,";
        }else if(timeOfDay < 16){
            return "Good Afternoon,";
        }else if(timeOfDay < 21){
            return "Good Evening,";
        }else{
            return "Good Night,";
        }
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
                        String user_id = object.getString("user_id").trim();
                        String user_name = object.getString("user_name").trim();
                        String user_phone = object.getString("user_phone").trim();
                        String user_email = object.getString("user_email").trim();
                        String user_address = object.getString("user_address").trim();
                        String user_note = object.getString("user_note").trim();
                        String user_gender = object.getString("user_gender").trim();
                        String appointment_date = object.getString("appointment_date").trim();
                        String appointment_time = object.getString("appointment_time").trim();
                        String admin_note = object.getString("admin_note").trim();


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


                    adapter2 = new RecyclerAdapter2(Dasboard.this, userListt);
                    recyclerView2.setAdapter(adapter2);
                    adapter2.notifyDataSetChanged();
                    int count = 0;
                    if (adapter2 != null) {
                        count = adapter2.getItemCount();
                        String counts = String.valueOf(count);
                        today_orders.setText("You have "+ counts + " appointment today");
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
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(), "error"+ error+toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("staff_id", id);
                params.put("status", "Pending");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    //ACCEPT THE APPOINTMENT ON CLICK
    @Override
    public void onItemClickproduct(Product product) {
        String id  = product.getId();

loadingdialog.showLoadingDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_accept_appointment, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    if (success.equals("1")){
                        loadingdialog.dismissDialog();
                        Toast.makeText(Dasboard.this, "You Have accepted "+product.getUser_name()+ " request", Toast.LENGTH_SHORT).show();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    loadingdialog.dismissDialog();
                    Toast.makeText(Dasboard.this, "eroor"+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingdialog.dismissDialog();
                Toast.makeText(Dasboard.this, "Error"+error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id", id);
                params.put("status", "Accepted");
                params.put("staff_name", fnamen+ " " + lnamen);
                params.put("staff_phone", phonen);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    //DECLINE THE APPOINTMENT ON CLICK
    @Override
    public void onItemClickproduct2(Product product) {
        String id  = product.getId();

loadingdialog.showLoadingDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_decline_appointment, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    if (success.equals("1")){
                        loadingdialog.dismissDialog();
                        Toast.makeText(Dasboard.this, "You declined "+product.getUser_name()+ " request", Toast.LENGTH_SHORT).show();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    loadingdialog.dismissDialog();
                    Toast.makeText(Dasboard.this, "eroor"+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingdialog.dismissDialog();
                Toast.makeText(Dasboard.this, "Error"+error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id", id);
                params.put("status", "Pending");
                params.put("staff_id", "");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(Dasboard.this)
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
                                ActivityCompat.finishAffinity(Dasboard.this);
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
                startActivity(new Intent(getApplicationContext(), Password.class));
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