package com.example.rotibanksystem;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;
    private TextView textViewForgot, textViewSign;
    private ProgressBar progressBar;

    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference dReference;
    private RadioGroup radiog;
    private RadioButton u;
    private DatabaseReference donor;
    private DatabaseReference ngo;
    private DatabaseReference volunteer;
    private DatabaseReference needy;

    public final static String ngoid="com.example.rotibanksystem";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        ngo = FirebaseDatabase.getInstance().getReference().child("ngo");

        donor = FirebaseDatabase.getInstance().getReference().child("donor");

        volunteer=FirebaseDatabase.getInstance().getReference().child("volunteer");
        needy=FirebaseDatabase.getInstance().getReference().child("Needy");

        editTextEmail = (EditText) findViewById(R.id.editText_login_email);
        editTextPassword = (EditText) findViewById(R.id.editText_login_password);
        buttonLogin = (Button) findViewById(R.id.button_login_login);
        textViewForgot = (TextView) findViewById(R.id.textView_login_forgot);
        textViewSign = (TextView) findViewById(R.id.textView_login_register);
        progressBar = findViewById(R.id.progressBar_login);
        progressBar.setVisibility(View.GONE);
        radiog = (RadioGroup) findViewById(R.id.rg);
        int rbid = radiog.getCheckedRadioButtonId();





        firebaseAuth = FirebaseAuth.getInstance();
        u = (RadioButton) findViewById(rbid);

        FirebaseUser user=firebaseAuth.getCurrentUser();

        String emailpatt="[a-zA-Z0-9._-]+@ngo+\\.+[a-z]+";
        if (user != null) {




            String email=user.getEmail();
            if(email.matches(emailpatt)){
                Intent dh = new Intent(LoginActivity.this, ngowelcome.class);
                startActivity(dh);
            }
            else {
                Intent dh = new Intent(LoginActivity.this, welcome.class);
                startActivity(dh);
            }

        }
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = v.getId();
                if (i == R.id.button_login_login)
                {
                    int rbid=radiog.getCheckedRadioButtonId();
                    if(rbid ==(-1))
                    {
                        Toast.makeText(LoginActivity.this, "Please Select Type", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        u = (RadioButton) findViewById(rbid);
                        signIn();
                    }
                }
                else if (i == R.id.textView_login_register)
                {
                    //Toast.makeText(MainActivity.this, "Register...",Toast.LENGTH_SHORT).show();

                    Intent us = new Intent(LoginActivity.this, SignUp.class);
                    startActivity(us);
                }
            }
        });
        textViewSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, registerActivity.class));
                finish();
            }
        });

        textViewForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, Resetpass.class));
            }
        });

    }
    private void signIn() {

        if (!validateForm()) {
            return;
        }

        String em = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        /*Toast.makeText(MainActivity.this,rbss+em+password,Toast.LENGTH_SHORT).show();

        mAuth.signInWithEmailAndPassword(em, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (task.isSuccessful())
                        {
                            Toast.makeText(MainActivity.this,"Successfully Login....",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this,"Login Failed...",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/

            firebaseAuth.signInWithEmailAndPassword(em, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if (task.isSuccessful()) {


                                String currentUserid = firebaseAuth.getCurrentUser().getUid();
                                String deviceToken = FirebaseInstanceId.getInstance().getToken();


                                String rbss = (String) u.getText();


                                //Toast.makeText(MainActivity.this,rbss,Toast.LENGTH_SHORT).show();

                                if (rbss.equals("donor")) {


                                    donor.child(currentUserid).child("devicetoken").setValue(deviceToken).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Intent dh = new Intent(LoginActivity.this, MapsActivity.class);
                                            startActivity(dh);

                                        }
                                    });
                                    //Toast.makeText(MainActivity.this, "Driver LogIn",Toast.LENGTH_SHORT).show();


                                } else if (rbss.equals("ngo")) {
                                    ngo.child(currentUserid).child("devicetoken").setValue(deviceToken).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Intent us = new Intent(LoginActivity.this, ngowelcome.class);
                                            startActivity(us);
                                        }
                                    });


                                    //Toast.makeText(MainActivity.this, "User LogIn",Toast.LENGTH_SHORT).show();


                                } else if (rbss.equals("volunteer")) {
                                    volunteer.child(currentUserid).child("devicetoken").setValue(deviceToken).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Intent us = new Intent(LoginActivity.this,newprofile.class);
                                            startActivity(us);
                                        }
                                    });


                                } else if (rbss.equals("needy")) {


                                    needy.child(currentUserid).child("devicetoken").setValue(deviceToken).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Intent us = new Intent(LoginActivity.this, volunteerwelcome.class);
                                            startActivity(us);
                                        }
                                    });
                                } else {
                                    Toast.makeText(LoginActivity.this, "Please Select Type", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            }

                        }


                    });
        }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }


    private boolean validateForm()
    {
        boolean result = true;
        if (TextUtils.isEmpty(editTextEmail.getText().toString())) {
            editTextEmail.setError("Required");
            result = false;
        } else {
            editTextEmail.setError(null);
        }

        if (TextUtils.isEmpty(editTextPassword.getText().toString())) {
            editTextPassword.setError("Required");
            result = false;
        } else {
            editTextPassword.setError(null);
        }
        return result;
    }








}
