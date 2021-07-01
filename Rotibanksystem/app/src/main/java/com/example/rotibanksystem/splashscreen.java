package com.example.rotibanksystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import static java.lang.Thread.sleep;

public class splashscreen extends AppCompatActivity {


    ImageView splashimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splashscreen);


        splashimage=findViewById(R.id.splash);

        Animation myanim= AnimationUtils.loadAnimation(this,R.anim.myanimation);

        splashimage.startAnimation(myanim);

        Thread myThread=new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    sleep(2000);

                    Intent i=new Intent(splashscreen.this,MainActivity.class);
                    startActivity(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        myThread.start();
    }
}
