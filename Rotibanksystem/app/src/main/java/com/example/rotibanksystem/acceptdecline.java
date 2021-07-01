package com.example.rotibanksystem;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class acceptdecline extends AppCompatActivity {

    Button accept,decline;
    TextView counter,did;
    String currentuserid;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    TextView donorid;
    int countdonation=0;
    FirebaseDatabase firebaseDatabase;
    final String id="cJU3TCTeG6ZS9h0ZsXIeAKvK2pn1";

    private DatabaseReference donationcount= FirebaseDatabase.getInstance().getReference().child("donation");

    private DatabaseReference accepted= FirebaseDatabase.getInstance().getReference().child("accepted");
    private DatabaseReference donate= FirebaseDatabase.getInstance().getReference().child("notificationref");



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceptdecline);







        firebaseDatabase=FirebaseDatabase.getInstance();
counter=findViewById(R.id.count);
firebaseAuth=FirebaseAuth.getInstance();
currentuserid=firebaseAuth.getCurrentUser().getUid();


accept=findViewById(R.id.profileSendReqButton);

decline=findViewById(R.id.profileDeclineReqButton);
decline.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        senddeclinenotification();
    }
});

accept.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        sendacceptnotification();
    }


});


donationcount.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        donation donation=new donation();
        String donorid=  donation.getUserid();
        User user=new User();
      String userid= user.getUserId();
        if(dataSnapshot.exists()){
            if(donorid==userid) {
                countdonation = (int) dataSnapshot.getChildrenCount();
                counter.setText(countdonation + "donation");
            }
        }
        else {
            counter.setText("0 donation");
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});




    }



    private void senddeclinenotification() {







        Toast.makeText(acceptdecline.this,"Request decline",Toast.LENGTH_SHORT).show();













    }

    private void sendacceptnotification() {
        String uniquekey=accepted.push().getKey();





        accepted.child(uniquekey).child("status").setValue("accept");




        Toast.makeText(acceptdecline.this,"Request accept",Toast.LENGTH_SHORT).show();








    }


}
