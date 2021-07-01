package com.example.rotibanksystem;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SignUp extends AppCompatActivity {


    private FirebaseAuth auth;
    private EditText editTextEmail, editTextPassword, editTextConfirmPassword, editTextFirstName, editTextLastName, editTextPhone;
    private Button buttonRegister;
    private TextView textViewForgot, textViewLogin, textViewLocation;
    private ImageView imageLocation;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("donor");
    private String coordinates;
    private ProgressBar progressBar;
    int PLACE_PICKER_REQUEST=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sign_up);
        auth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.editText_sign_email);
        editTextPassword = findViewById(R.id.editText_sign_password);
        editTextConfirmPassword = findViewById(R.id.editText_sign_password_confirm);
        buttonRegister = findViewById(R.id.button_sign_register);
        textViewForgot = findViewById(R.id.textView_sign_forgot);
        textViewLogin = findViewById(R.id.textView_sign_login);
        editTextFirstName = findViewById(R.id.editText_sign_firstName);
        editTextLastName = findViewById(R.id.editText_sign_lastName);
        editTextPhone = findViewById(R.id.editText_sign_phone);
        textViewLocation = findViewById(R.id.textView_sign_location);
        imageLocation = findViewById(R.id.imageView_sign_location);
        progressBar = findViewById(R.id.progressBar_sign);
        progressBar.setVisibility(View.GONE);
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this, LoginActivity.class));
                finish();
            }
        });
        textViewForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this, Resetpass.class));
            }
        });
        imageLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(intentBuilder.build(SignUp.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (connectivityManager.getActiveNetworkInfo() != null && networkInfo.isConnectedOrConnecting()) {
                    final String email = editTextEmail.getText().toString().trim();
                    String password = editTextPassword.getText().toString().trim();
                    String passwordConfirm = editTextConfirmPassword.getText().toString().trim();
                    final String firstName = editTextFirstName.getText().toString().trim();
                    final String lastName = editTextLastName.getText().toString().trim();
                    final String phone = editTextPhone.getText().toString().trim();
                    final String location = textViewLocation.getText().toString();
                   final String address = textViewLocation.getText().toString().trim();
                    String display = "";
                    if (TextUtils.isEmpty(firstName)) {
                        display = "Please enter first name";
                        editTextFirstName.requestFocus();
                    } else if (TextUtils.isEmpty(lastName)) {
                        display = "Please enter last name";
                        editTextLastName.requestFocus();
                    } else if (TextUtils.isEmpty(phone)) {
                        display = "Please enter a phone number";
                        editTextPhone.requestFocus();
                    } else if (TextUtils.isEmpty(location)) {
                        display = "Please select a valid location";
                        textViewLocation.requestFocus();
                    } else if (TextUtils.isEmpty(email)) {
                        display = "Please enter an e-mail address";
                        editTextEmail.requestFocus();
                    } else if (TextUtils.isEmpty(password) || TextUtils.isEmpty(passwordConfirm)) {
                        display = "Please enter a password";
                        editTextPassword.requestFocus();
                    } else if (!password.equals(passwordConfirm)) {
                        display = "The two passwords do not match";
                        editTextConfirmPassword.requestFocus();
                    } else if (phone.length() < 10) {
                        display = "Enter a valid phone number";
                        editTextPhone.requestFocus();
                    } else if (password.length() < 6) {
                        display = "Password must be 6 or more characters";
                        editTextPassword.requestFocus();
                    } else {







                        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    String deviceToken= FirebaseInstanceId.getInstance().getToken();
                                    String userId = auth.getCurrentUser().getUid();

                                    User user = new User(firstName,lastName,phone,coordinates,address,email,userId);



                                    databaseReference.child(userId).setValue(user);
                                    databaseReference.child(userId).child("devicetoken").setValue(deviceToken).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            Toast.makeText(SignUp.this, "Successfully created user", Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(SignUp.this, MainActivity.class));
                                            finish();
                                        }
                                    });

                                } else {
                                    Log.e("User not created", task.getException().getMessage());
                                    Toast.makeText(SignUp.this, "Unable to create user", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                    }
                    if (!display.equals(""))
                        Toast.makeText(SignUp.this, display, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(SignUp.this, "Please connect to the internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                StringBuilder stringBuilder=new StringBuilder();
                String latitude=String.valueOf(place.getLatLng().latitude);
                String longitude=String.valueOf(place.getLatLng().longitude);
                stringBuilder.append("LATITUDE");
                stringBuilder.append(latitude);
                stringBuilder.append("\n");
                stringBuilder.append("LONGITUDE");
                stringBuilder.append(longitude);


                coordinates = place.getLatLng().latitude + ", " + place.getLatLng().longitude;
                String add=getAddress(SignUp.this,latitude,longitude);
                textViewLocation.setText(add);
            }
        }
    }

    public String getAddress(Context ctx, String latitude, String longitude){

        String fulladd=null;
        double lat=Double.parseDouble(latitude);
        double longi=Double.parseDouble(longitude);
        try {
            Geocoder geocoder=new Geocoder(ctx, Locale.getDefault());
            List<Address> addresses=geocoder.getFromLocation(lat,longi,1);
            if(addresses.size()>0){
                Address address=addresses.get(0);
                fulladd=address.getAddressLine(0);

            }
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return  fulladd;

    }


}