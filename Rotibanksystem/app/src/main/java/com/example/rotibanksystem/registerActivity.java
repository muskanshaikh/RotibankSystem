package com.example.rotibanksystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class registerActivity extends AppCompatActivity {
    Button btndonor,btnngo,btnvolunteer,btnneedy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);
        btndonor=findViewById(R.id.btnDonor);
        btnngo=findViewById(R.id.btnNgo);
        btnvolunteer=findViewById(R.id.btnvolunteer);
        btnneedy=findViewById(R.id.btnNeedy);


        btndonor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(registerActivity.this,SignUp.class);
                startActivity(i);
            }
        });
        btnngo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(registerActivity.this, NgoActivity.class);
                startActivity(i);
            }
        });
        btnvolunteer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(registerActivity.this,VolunteerActivity.class);
                startActivity(i);
            }
        });
        btnneedy.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(registerActivity.this,NeedyActivity.class);
                startActivity(i);
            }
        });

    }
}
