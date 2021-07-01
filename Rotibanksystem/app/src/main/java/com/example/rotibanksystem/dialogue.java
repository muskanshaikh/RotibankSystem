package com.example.rotibanksystem;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class dialogue extends AppCompatActivity {

    private GoogleApiClient mGoogleApiClient;
    private int PLACE_PICKER_REQUEST = 1;
    private RadioButton radioBreakfast,radioLunch,radioDinner;
    private EditText edtQuantity,edtAddress,edtfooddescribe;
    private TextView txtlocation;
    private String foodType = "dinner",quantity = "",address = "",lat = "",lng = "",userid= "";
    private Button donate;
    private FirebaseAuth auth;
    private GoogleMap mMap;
    private FirebaseUser user;

    private Location myLocation;
    private  String foodinto;



    private  String coordinates;
    private String  add;

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
long maxid=0;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;



    //Create an object of DatabaseReference to create second table


    final String id="cJU3TCTeG6ZS9h0ZsXIeAKvK2pn1";






    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("donationform");
private  DatabaseReference chatdonate=FirebaseDatabase.getInstance().getReference().child("donate");
private DatabaseReference notificationref=FirebaseDatabase.getInstance().getReference().child("notificationref");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_dialogue);









        txtlocation = findViewById(R.id.txt_select_location);


        radioBreakfast = (RadioButton) findViewById(R.id.radio_breakfast);
        radioLunch = (RadioButton) findViewById(R.id.radio_lunch);
        radioDinner = (RadioButton) findViewById(R.id.radio_dinner);

        edtQuantity = (EditText) findViewById(R.id.edt_quantity);
        edtAddress = (EditText) findViewById(R.id.edt_address);
        edtfooddescribe=(EditText) findViewById(R.id.fooddescribe);
        donate=findViewById(R.id.btn_donate);






        findViewById(R.id.txt_select_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayPlacePicker();
            }

            private void displayPlacePicker() {


                PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
                try {


                    startActivityForResult(intentBuilder.build(dialogue.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        radioBreakfast.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    foodType = "breakfast";
                }
            }
        });
        radioLunch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    foodType = "lunch";
                }
            }
        });
        radioDinner.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    foodType = "dinner";
                }
            }
        });

        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (connectivityManager.getActiveNetworkInfo() != null && networkInfo.isConnectedOrConnecting()) {


                    quantity = edtQuantity.getText().toString().trim();


                     address = edtAddress.getText().toString().trim();
                    final String location = txtlocation.getText().toString().trim();
                    foodinto=edtfooddescribe.getText().toString().trim();



                    String display = "";
                    if (TextUtils.isEmpty(foodType)) {
                        display = "Please select";

                    } else if (TextUtils.isEmpty(foodinto)) {
                        display = "Please enter description";
                        edtfooddescribe.requestFocus();
                    }


                    else if (TextUtils.isEmpty(quantity)) {
                        display = "Please enter quantity";
                        edtQuantity.requestFocus();
                    }


                    else if (TextUtils.isEmpty(address)) {
                        display = "Please enter a address";
                        edtAddress.requestFocus();
                    } else if (TextUtils.isEmpty(location)) {
                        display = "Please select a valid location";
                        txtlocation.requestFocus();
                    }else {




                        mFirebaseInstance = FirebaseDatabase.getInstance();
                       Date date=new Date();

                       SimpleDateFormat ISO_8601_FORMAT=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss'Z'");

                      final String now=ISO_8601_FORMAT.format(new Date());
                        userid = auth.getInstance().getCurrentUser().getUid();
                        String status=" ";

                        // get reference to 'RepositoryName' node
                        mFirebaseDatabase = mFirebaseInstance.getReference("donation");
			donation donation = new donation(foodType,foodinto, quantity, coordinates, address,now,userid,status);
	 		mFirebaseDatabase.push().setValue(donation);

			sendnotification();
			 Toast.makeText(dialogue.this, "Successfully donated user", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(dialogue.this, thanks.class));


                    }

                }

            }
         });
    }

    private void sendnotification() {
        chatdonate.child(userid).child(id).child("requesttype").setValue("donate").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
           if(task.isSuccessful()){

               chatdonate.child(id).child(userid).child("requesttype").setValue("received").addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {
                       HashMap<String,String> chatnotification=new HashMap<>();
                       chatnotification.put("from",userid);
                       chatnotification.put("type","donate");
                       chatnotification.put("fooddescp",foodType);
                       chatnotification.put("fooddetail",foodinto);
                       chatnotification.put("Address",address);
                       chatnotification.put("Quantity",quantity);
                       notificationref.child(id).push().setValue(chatnotification).addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                          if(task.isSuccessful()){
                              Toast.makeText(dialogue.this,"received",Toast.LENGTH_SHORT).show();
                              finish();
                          }
                           }
                       });










                   }
               });

           }
            }


        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                StringBuilder stringBuilder=new StringBuilder();
                String latitude=String.valueOf(place.getLatLng().latitude);
                String longitude=String.valueOf(place.getLatLng().longitude);
                stringBuilder.append("LATITUDE");
                stringBuilder.append(latitude);
                stringBuilder.append("LONGITUDE");
                stringBuilder.append(longitude);

                txtlocation.setText(stringBuilder.toString());
                coordinates = place.getLatLng().latitude + ", " + place.getLatLng().longitude;
                String add=getAddress(dialogue.this,latitude,longitude);
                edtAddress.setText(add);




            }
        }
    }

    public String getAddress(Context ctx, String latitude, String longitude){

        String fulladd=null;
        double lat=Double.parseDouble(latitude);
        double longi=Double.parseDouble(longitude);
        try {
            Geocoder geocoder=new Geocoder(ctx, Locale.getDefault());
            List<android.location.Address> addresses=geocoder.getFromLocation(lat,longi,1);
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

