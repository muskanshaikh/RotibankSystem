package com.example.rotibanksystem;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */





public class needyprofile extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Button deleteacc;
    ProgressBar progressBar;
    Button update;


    EditText profilefirstname, profilelastname, profileemail, profilemobile,profileaddress;



    public needyprofile(){
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view =inflater.inflate(R.layout.fragment_needyprofile, container, false);

        firebaseAuth=FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Needy");

        profilefirstname=view.findViewById(R.id.editText_sign_firstName);
        profilelastname=view.findViewById(R.id.editText_sign_age);
        profileaddress=view.findViewById(R.id.editText_sign_address);


        profilemobile=view.findViewById(R.id.editText_sign_aadhar);
        profileemail=view.findViewById(R.id.editText_sign_email);
        deleteacc=view.findViewById(R.id.textView_sign_delete);
        progressBar=view.findViewById(R.id.progressbar);
        update=view.findViewById(R.id.button_sign_register);



        deleteacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(getActivity());
                dialog.setTitle("Are you sure?");
                dialog.setMessage("Deleting this account will result in completing removing your account fro the system and you won't able to access the app");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressBar.setVisibility(View.VISIBLE);

                        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressBar.setVisibility(View.GONE);
                                if(task.isSuccessful()){
                                    Toast.makeText(getActivity(),"Account deleted",Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(getActivity(),LoginActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(getActivity(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                });

                dialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                    }
                });

                AlertDialog alertDialog=dialog.create();
                alertDialog.show();
            }
        });

        Query query=databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String fname = "" + ds.child("fullname").getValue();
                    String lname = "" + ds.child("age").getValue();
                    String phone = "" + ds.child("aadharno").getValue();
                    String address="" +ds.child("address").getValue();

                    String Email= "" + ds.child("email").getValue();


                    profilefirstname.setText(fname);
                    profilelastname.setText(lname);
                    profileaddress.setText(address);

                    profileemail.setText(Email);
                    profilemobile.setText(phone);


                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=profilefirstname.getText().toString();
                String age=profilelastname.getText().toString();
                String address=profileaddress.getText().toString();
                String aadharno=profilemobile.getText().toString();
                String email=profileemail.getText().toString();

                editdata(name,age,address,aadharno,email);
            }


        });

        return  view;



    }

    private void editdata(final String name, final String age, final String address, final String aadharno, final String email) {


        Query query=databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {





                    ds.getRef().child("fullname").setValue(name);
                    ds.getRef().child("age").setValue(age);
                    ds.getRef().child("aadharno").setValue(aadharno);
                    ds.getRef().child("address").setValue(address);
                    ds.getRef().child("email").setValue(email);

                    Toast.makeText(getActivity(),"Updated successfully",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}



// Inflate the layout for this fragment



