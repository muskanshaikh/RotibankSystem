package com.example.rotibanksystem;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks,
        LocationListener

{
    FirebaseAuth firebaseAuth;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private GoogleMap mMap;
    double currentLatitude,currentLongitude;
 private  Location myLocation;


    GoogleApiClient mgoogleApiClient;

    private final static int REQUEST_CHECK_SETTINGS_GPS=0x1;
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS=0x2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);




        if(getSupportActionBar()!=null){
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
}
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        setUPGCLient();
    }

    private void setUPGCLient() {
        mgoogleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,0,this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mgoogleApiClient.connect();



    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


    }


    @Override
    public void onLocationChanged(Location location) {

        myLocation=location;
        if(myLocation!=null) {
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();

            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.navigation);
            mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(currentLatitude, currentLongitude)));
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(currentLatitude, currentLongitude));
            markerOptions.title("You");
            markerOptions.icon(icon);
            mMap.addMarker(markerOptions);



            if(!getngoaround && !getvolunteeraround && !getneedyaround ){
                getclosedvolunteer();
                getclosedngo();
                getclosedneedy();

            }





        }


    }
    boolean  getneedyaround=false;

    List<Marker> mskList=new ArrayList<Marker>();
    private void getclosedneedy() {
        getneedyaround=true;

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Needy").child("coordinates");
        GeoFire gfneedy = new GeoFire(databaseReference);
        GeoQuery geoQuery = gfneedy.queryAtLocation(new GeoLocation(myLocation.getLatitude(),myLocation.getLongitude()), 80);
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                for (Marker markerIt:mskList){
                    if (markerIt.getTag().equals(key))
                        return;


                }
                LatLng needylocation=new LatLng(location.latitude,location.longitude);
                Marker mneedy=mMap.addMarker(new MarkerOptions().position(needylocation).title("needy"));
                mneedy.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
                mneedy.setTag(key);
                mskList.add(mneedy);


            }


            @Override
            public void onKeyExited(String key) {

                for (Marker markerIt:mskList){
                    if (markerIt.getTag().equals(key)){
                        markerIt.remove();
                        mskList.remove(markerIt);
                        return;
                    }
                }

            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {
                for (Marker markerIt:mskList){
                    if (markerIt.getTag().equals(key)) {

                        markerIt.setPosition(new LatLng(location.latitude,location.longitude));
                    }

                }

            }

            @Override
            public void onGeoQueryReady() {


            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });


    }


    boolean  getvolunteeraround=false;

    List<Marker> markList=new ArrayList<Marker>();


    private void getclosedvolunteer() {
        getvolunteeraround=true;



        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("volunteer").child("coordinates");
        GeoFire gfvolunteer = new GeoFire(databaseReference);
        GeoQuery geoQuery = gfvolunteer.queryAtLocation(new GeoLocation(myLocation.getLatitude(),myLocation.getLongitude()), 80);
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                for (Marker markerIt:markList){
                    if (markerIt.getTag().equals(key))
                        return;


                }

                LatLng volunteerlocation=new LatLng(location.latitude,location.longitude);
                Marker mvol=mMap.addMarker(new MarkerOptions().position(volunteerlocation).title("volunteer"));
                mvol.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                mvol.setTag(key);
                markList.add(mvol);



            }


            @Override
            public void onKeyExited(String key) {

                for (Marker markerIt:markList){
                    if (markerIt.getTag().equals(key)){
                        markerIt.remove();
                        markList.remove(markerIt);
                        return;
                    }
                }

            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {
                for (Marker markerIt:markList){
                    if (markerIt.getTag().equals(key)) {

                        markerIt.setPosition(new LatLng(location.latitude,location.longitude));
                    }

                }

            }

            @Override
            public void onGeoQueryReady() {


            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });


    }



    boolean getngoaround=false;

List<Marker> markerList=new ArrayList<Marker>();
    private void getclosedngo() {
        getngoaround=true;



        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("ngo").child("coordinates");
        GeoFire gfngo = new GeoFire(databaseReference);
        GeoQuery geoQuery = gfngo.queryAtLocation(new GeoLocation(myLocation.getLatitude(),myLocation.getLongitude()), 80);
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                for (Marker markerIt:markerList){
                    if (markerIt.getTag().equals(key))
                        return;


                }
                LatLng ngolocation=new LatLng(location.latitude,location.longitude);
                Marker mngo=mMap.addMarker(new MarkerOptions().position(ngolocation).title("ngo's"));
                mngo.setTag(key);
                markerList.add(mngo);



            }


            @Override
            public void onKeyExited(String key) {

                for (Marker markerIt:markerList){
                    if (markerIt.getTag().equals(key)){
                        markerIt.remove();
                        markerList.remove(markerIt);
                        return;
                    }
                }

            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {
                for (Marker markerIt:markerList){
                    if (markerIt.getTag().equals(key)) {

                        markerIt.setPosition(new LatLng(location.latitude,location.longitude));
                    }

                    }

            }

            @Override
            public void onGeoQueryReady() {


            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });




    }






    @Override
    public void onConnected(@Nullable Bundle bundle) {

        checkPermission();

    }

    private void checkPermission() {
        int permissionLocation= ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermssion=new ArrayList<>();
        if(permissionLocation!= PackageManager.PERMISSION_GRANTED){
            listPermssion.add(Manifest.permission.ACCESS_FINE_LOCATION);
            if(!listPermssion.isEmpty()){
                ActivityCompat.requestPermissions(this,
                        listPermssion.toArray(new String[listPermssion.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            }
        }
        else{
            getMyLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        int permissionLocation=ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionLocation==PackageManager.PERMISSION_GRANTED){
            getMyLocation();
        }
        else{
            checkPermission();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    private void getMyLocation(){
        if(mgoogleApiClient!=null) {
            if (mgoogleApiClient.isConnected()) {
                int permissionLocation = ContextCompat.checkSelfPermission(MapsActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                    myLocation = LocationServices.FusedLocationApi.getLastLocation(mgoogleApiClient);
                    LocationRequest locationRequest = new LocationRequest();
                    locationRequest.setInterval(3000);
                    locationRequest.setFastestInterval(3000);
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                            .addLocationRequest(locationRequest);
                    builder.setAlwaysShow(true);
                    LocationServices.FusedLocationApi
                            .requestLocationUpdates(mgoogleApiClient, locationRequest, this);
                    PendingResult<LocationSettingsResult> result =
                            LocationServices.SettingsApi
                                    .checkLocationSettings(mgoogleApiClient, builder.build());
                    result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

                        @Override
                        public void onResult(LocationSettingsResult result) {
                            final Status status = result.getStatus();
                            switch (status.getStatusCode()) {
                                case LocationSettingsStatusCodes.SUCCESS:
                                    // All location settings are satisfied.
                                    // You can initialize location requests here.
                                    int permissionLocation = ContextCompat
                                            .checkSelfPermission(MapsActivity.this,
                                                    Manifest.permission.ACCESS_FINE_LOCATION);
                                    if (permissionLocation == PackageManager.PERMISSION_GRANTED) {


                                        myLocation = LocationServices.FusedLocationApi
                                                .getLastLocation(mgoogleApiClient);


                                    }
                                    break;
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    // Location settings are not satisfied.
                                    // But could be fixed by showing the user a dialog.
                                    try {
                                        // Show the dialog by calling startResolutionForResult(),
                                        // and check the result in onActivityResult().
                                        // Ask to turn on GPS automatically
                                        status.startResolutionForResult(MapsActivity.this,
                                                REQUEST_CHECK_SETTINGS_GPS);


                                    } catch (IntentSender.SendIntentException e) {
                                        // Ignore the error.
                                    }


                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    // Location settings are not satisfied.
                                    // However, we have no way
                                    // to fix the
                                    // settings so we won't show the dialog.
                                    // finish();
                                    break;
                            }
                        }
                    });

                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(MapsActivity.this, welcome.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public  void  changetype(View view)
    {

        if(mMap.getMapType()==GoogleMap.MAP_TYPE_NORMAL)
        {


            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);


        }
        else
        {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }

    }

}