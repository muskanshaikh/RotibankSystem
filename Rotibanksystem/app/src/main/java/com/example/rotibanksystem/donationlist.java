package com.example.rotibanksystem;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class donationlist extends AppCompatActivity {

    DatabaseReference databaseReference;
    DatabaseReference donorreference;
    ListView listView;
    private String id;

    FirebaseAuth firebaseAuth;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donationlist);

        User user=new User();

        databaseReference= FirebaseDatabase.getInstance().getReference("donor");
        donorreference=FirebaseDatabase.getInstance().getReference().child("donation");
        firebaseAuth = FirebaseAuth.getInstance();








        listView=(ListView) findViewById(R.id.listviewtxt);
        arrayAdapter=new ArrayAdapter<String>(donationlist.this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);

        final String myuserid=FirebaseAuth.getInstance().getCurrentUser().getUid();





        donorreference.orderByChild("userid").equalTo(myuserid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String value = dataSnapshot.getValue(donation.class).toString();
                arrayList.add(value);
                arrayAdapter.notifyDataSetChanged();
            }



            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });














    }
}
