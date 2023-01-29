package com.caregiver.healthcare;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserData extends AppCompatActivity  implements Vitals_RecyclerAdapter.onItemClickListener{

    RecyclerView recyclerView2;
    Vitals_RecyclerAdapter adapter2;
    final static String load_items_assigned = "https://spotters.tech/health/android/previous_vitals.php";
    String user_id;
    private ArrayList<Vitals> userListt = new ArrayList<>();
    ImageView addvitals;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_FNAME = "fname";
    private static final String KEY_LNAME = "lname";
    private static final String KEY_ID = "id";
    private static final String KEY_GENDER = "status";
    String fnamen, lnamen, phonen, gender;
    String rider_name, fid;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        sharedPreferences = this.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        phonen = sharedPreferences.getString(KEY_PHONE, null);
        fnamen = sharedPreferences.getString(KEY_FNAME, null);
        lnamen = sharedPreferences.getString(KEY_LNAME, null);
        gender = sharedPreferences.getString(KEY_GENDER, null);
        fid = sharedPreferences.getString(KEY_ID, null);
        gender = sharedPreferences.getString(KEY_GENDER, null);
        rider_name = fnamen + " " + lnamen;
         user_id  = getIntent().getStringExtra("user_id");
        String user_name  = getIntent().getStringExtra("user_name");
        String gender  = getIntent().getStringExtra("gender");
        String user_address  = getIntent().getStringExtra("user_address");
        //System.out.println(user_id);

        //  RECYCLERVIEW2
        recyclerView2 = (RecyclerView) findViewById(R.id.recyclerviewvitals);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getApplicationContext());
        recyclerView2.setLayoutManager(layoutManager2);
        layoutManager2.setOrientation(RecyclerView.VERTICAL);
        recyclerView2.setHasFixedSize(true);

        load_items_accepted();

        Vitals_dialog vitals_dialog = new Vitals_dialog(this);

        addvitals = findViewById(R.id.addvitals);
        TextView username = findViewById(R.id.user_name_data);
        TextView genders = findViewById(R.id.greet);
        TextView useraddress = findViewById(R.id.user_address);
        ImageView cancelvitals = findViewById(R.id.cancelvitals);

//        cancelvitals.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                vitals_dialog.dismissDialog();
//            }
//        });

        useraddress.setText(user_address);

        if (gender.equals("Male")){
            genders.setText("Mr");
        }
        if (gender.equals("Female")){
            genders.setText("Madam");
        }

        username.setText(user_name);

        addvitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vitals_dialog.showdialog(user_id, fid, user_name, getApplicationContext());
                //cancelvitals.setVisibility(View.VISIBLE);
            }
        });
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

                        String temperature = object.getString("temperature").trim();
                        String pulse = object.getString("pulse").trim();
                        String respiration = object.getString("respiration").trim();
                        String bloodpressure = object.getString("bloodpressure").trim();
                        String bloodoximetry = object.getString("bloodoximetry").trim();
                        String pain = object.getString("pain").trim();
                        String bloodsugar = object.getString("bloodsugar").trim();
                        String date = object.getString("date").trim();
                        String time = object.getString("time").trim();

                        Vitals vitals = new Vitals(temperature,pulse,respiration,bloodpressure,bloodoximetry,pain,bloodsugar,date,time);

                        userListt.add(0, vitals);

                    }


                    adapter2 = new Vitals_RecyclerAdapter(UserData.this, userListt);
                    recyclerView2.setAdapter(adapter2);
                    adapter2.notifyDataSetChanged();
                    int count = 0;
                    if (adapter2 != null) {
                        count = adapter2.getItemCount();
                        String counts = String.valueOf(count);
                       // today_orders.setText("You have "+ counts + " appointment today");
//                        if (counts.equals("0")) {
//                            final Handler handler = new Handler();
//                            handler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    loadingdialog.dismissDialog();
//                                }
//                            }, 1000);
//                        }
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
                params.put("staff_id", "2");
                params.put("user_id", "1");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void backbtn(View view) {
        onBackPressed();
    }

    @Override
    public void onItemClickproduct(Product product) {

    }

    @Override
    public void onItemClickproduct2(Product product) {

    }
}