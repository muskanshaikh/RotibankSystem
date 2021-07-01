package com.example.rotibanksystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;

public class ngowelcome extends AppCompatActivity {
    Location myLocation;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ngowelcome);
        BottomNavigationView navigationView=findViewById(R.id.btn_nav);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id=menuItem.getItemId();
                if(id==R.id.home){
                    home fragment=new home();
                    FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout,fragment);
                    fragmentTransaction.commit();
                }

                if(id==R.id.profile){
                    ngo_profile fragment=new ngo_profile();
                    FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout,fragment);
                    fragmentTransaction.commit();
                }

                return  true;
            }
        });

        navigationView.setSelectedItemId(R.id.home);




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ngomenu, menu);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_aboutus) {


            startActivity(new Intent(ngowelcome.this,ngoabout.class));
            return true;
        }
        else if (id == R.id.action_mapview) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(ngowelcome.this, MapsActivity2.class));
            finish();
        }


        else if (id == R.id.action_sign_out) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(ngowelcome.this, LoginActivity.class));
            finish();
        }
        else if(id==R.id.share){
            ApplicationInfo api=getApplicationContext().getApplicationInfo();
            String apkpath=api.sourceDir;
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setType("application/vnd.android.package-archive");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(apkpath)));
            startActivity(Intent.createChooser(intent,"ShareVia"));
        }







        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(ngowelcome.this);
        builder.setMessage("Really Exit ??");
        builder.setTitle("Exit");
        builder.setCancelable(false);
        builder.setPositiveButton("Ok",new MyListener());
        builder.setNegativeButton("Cancel",null);
        builder.show();

    }
    public class MyListener implements DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialog, int which) {
            finish();
        }
    }




}

