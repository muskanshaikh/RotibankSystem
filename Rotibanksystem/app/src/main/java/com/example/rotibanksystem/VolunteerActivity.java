package com.example.rotibanksystem;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class VolunteerActivity extends AppCompatActivity {

    private EditText name,mobile,txtemail,txtpassword,cpassword,before;
    private RadioButton radiomale,radiofemale;
    private TextView txtlocation;
    private Spinner spinnerdays;
    private Spinner spinnertime;
    private CheckBox checkBoxPick;
    private FirebaseAuth auth;
    private  String coordinates;
    private String gender = "male";
    GeoFire geoFire;
    private Button register;
    private GoogleApiClient mGoogleApiClient;
    private int PLACE_PICKER_REQUEST = 1;
    private String[] days,time;
    private Double currentlatitude;
    private Double currentlongitude;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("volunteer");






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_volunteer);
        auth = FirebaseAuth.getInstance();

        name=findViewById(R.id.editText_sign_orgName);
        mobile=findViewById(R.id.editText_mobile);
        txtemail=findViewById(R.id.editText_sign_email);
        txtpassword=findViewById(R.id.editText_sign_password);
        cpassword=findViewById(R.id.editText_sign_password_confirmpassword);
        radiofemale=findViewById(R.id.radio_female);
        radiomale=findViewById(R.id.radio_male);
        spinnerdays=findViewById(R.id.spinner_donation_donation);
        spinnertime=findViewById(R.id.spinner_donation_time);
        checkBoxPick=findViewById(R.id.checkBox_donation_pick);
        before=findViewById(R.id.yesngo);
        register=findViewById(R.id.button_sign_register);
        txtlocation = findViewById(R.id.txt_select_location);
        days = getResources().getStringArray(R.array.days);
        time= getResources().getStringArray(R.array.time);





        findViewById(R.id.txt_select_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayPlacePicker();
            }

            private void displayPlacePicker() {


                PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(intentBuilder.build(VolunteerActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        radiomale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    gender = "male";
                }
            }
        });
        radiofemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    gender = "female";
                }
            }
        });

register.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (connectivityManager.getActiveNetworkInfo() != null && networkInfo.isConnectedOrConnecting()) {


         final   boolean isPick = checkBoxPick.isChecked();
       final String isdays = spinnerdays.getSelectedItem().toString();
       final String istime = spinnertime.getSelectedItem().toString();

        final String location = txtlocation.getText().toString().trim();
        final String email = txtemail.getText().toString().trim();
        String password = txtpassword.getText().toString().trim();
        String passwordConfirm = cpassword.getText().toString().trim();
        final String fullname = name.getText().toString().trim();
        final String txtbefore = before.getText().toString().trim();
        final String phone = mobile.getText().toString().trim();
            final String address = txtlocation.getText().toString().trim();

        String display = "";
        if (TextUtils.isEmpty(fullname)) {
            display = "Please enter  name";
            name.requestFocus();
        }else if (TextUtils.isEmpty(gender)) {
            display = "Please enter gender";
            radiomale.requestFocus();
        }

        else if (TextUtils.isEmpty(phone)) {
            display = "Please enter mobile number";
            mobile.requestFocus();
        } else if (TextUtils.isEmpty(txtbefore)) {
            display = "Please enter ";
            before.requestFocus();
        } else if (TextUtils.isEmpty(location)) {
            display = "Please select a valid location";
            txtlocation.requestFocus();
        } else if (TextUtils.isEmpty(email)) {
            display = "Please enter an e-mail address";
            txtemail.requestFocus();
        } else if (TextUtils.isEmpty(password) || TextUtils.isEmpty(passwordConfirm)) {
            display = "Please enter a password";
            txtpassword.requestFocus();
        } else if (!password.equals(passwordConfirm)) {
            display = "The two passwords do not match";
           cpassword.requestFocus();
        } else if (phone.length() < 10) {
            display = "Enter a valid phone number";
            mobile.requestFocus();
        } else if (password.length() < 6) {
            display = "Password must be 6 or more characters";
            txtpassword.requestFocus();
        } else {

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        volunteer Voluser= new volunteer(fullname, phone,  email, txtbefore, isdays, istime,gender, coordinates,address);
                        String userId = auth.getCurrentUser().getUid();
                        databaseReference.child(userId).setValue(Voluser);
                        geoFire = new GeoFire(databaseReference.child("coordinates"));
                        geoFire.setLocation(userId, new GeoLocation(currentlatitude, currentlongitude));
                        Toast.makeText(VolunteerActivity.this, "Successfully created user", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(VolunteerActivity.this, MainActivity.class));
                        Intent intent = new Intent();
                        PendingIntent pendingIntent = PendingIntent.getActivity(VolunteerActivity.this,0,intent,0);

                        Notification.Builder notificationBuilder = new Notification.Builder(VolunteerActivity.this)
                                .setTicker("Volunteer ").setContentTitle("Volunteer")
                                .setContentText("Thank you for becomin part of roti bank system").setSmallIcon(R.drawable.roti)
                                .setContentIntent(pendingIntent);
                        Notification notification = notificationBuilder.build();
                        notification.flags = Notification.FLAG_AUTO_CANCEL;
                        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        assert nm != null;
                        nm.notify(0,notification);
                        finish();
                    } else {
                        Log.e("User not created", task.getException().getMessage());
                        Toast.makeText(VolunteerActivity.this, "Unable to create user", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
        if (!display.equals(""))
            Toast.makeText(VolunteerActivity.this, display, Toast.LENGTH_SHORT).show();
    }else {
        Toast.makeText(VolunteerActivity.this, "Please connect to the internet", Toast.LENGTH_SHORT).show();
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

                currentlatitude = place.getLatLng().latitude;
                currentlongitude=place.getLatLng().longitude;
                String add=getAddress(VolunteerActivity.this,latitude,longitude);
                txtlocation.setText(add);


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
