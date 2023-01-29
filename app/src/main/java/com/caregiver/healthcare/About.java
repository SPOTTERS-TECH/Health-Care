package com.caregiver.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

public class About extends AppCompatActivity {

        TextView jch;
        @SuppressLint("MissingInflatedId")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_about);


            jch = findViewById(R.id.jch);

            jch.setMovementMethod(LinkMovementMethod.getInstance());
        }
        public void backbtn(View view) {
            onBackPressed();
        }
    }
