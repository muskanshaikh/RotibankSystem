package com.example.rotibanksystem;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class feedback extends AppCompatActivity {
    private  EditText feed;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    Button feedbackbtn;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        feed=findViewById(R.id.editText2);
        firebaseAuth=FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("feedback");
        feedbackbtn=findViewById(R.id.button1);

        feedbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=user.getUid();



                    String uniquekey=databaseReference.push().getKey();
                    String response=feed.getText().toString().trim();
                String display = "";


                if(TextUtils.isEmpty(response)) {
                    display = "Please enter  feedback";
                    feed.requestFocus();
                }else{
                    databaseReference.child(uniquekey).child("feedback").setValue(response);
                    databaseReference.child(uniquekey).child("id").setValue(id);


                    Toast.makeText(feedback.this,"Feedback send successfully",Toast.LENGTH_SHORT).show();


                }if (!display.equals("")){
                    Toast.makeText(feedback.this, display, Toast.LENGTH_SHORT).show();
            }
                else {
                Toast.makeText(feedback.this, "Please connect to the internet", Toast.LENGTH_SHORT).show();
            }


            }

        });




    }
}
