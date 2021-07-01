package com.example.rotibanksystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ngoabout extends AppCompatActivity {

    Button mission,faqs,profile,donation,feedback,work,who,volunteer,needy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ngoabout);

        faqs=findViewById(R.id.buttonfaqs);
        feedback=findViewById(R.id.buttonfeed);
        donation=findViewById(R.id.buttonedu);
        profile=findViewById(R.id.button);
        work=findViewById(R.id.buttonhow);
        who=findViewById(R.id.buttonwho);





        who.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ngoabout.this,whoweare.class);
                startActivity(intent);
            }
        });

        work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ngoabout.this,howwework.class);
                startActivity(intent);
            }
        });
        faqs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ngoabout.this,faqs.class);
                startActivity(intent);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ngoabout.this,mission.class);
                startActivity(intent);
            }
        });
        donation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ngoabout.this,ngolist.class);
                startActivity(intent);
            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ngoabout.this,feedback.class);
                startActivity(intent);

            }
        });



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(ngoabout.this, welcome.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
