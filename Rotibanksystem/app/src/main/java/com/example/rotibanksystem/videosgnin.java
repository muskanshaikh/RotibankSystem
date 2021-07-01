package com.example.rotibanksystem;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


/**
 * Created by Sarun on 05-04-2017.
 */
public class videosgnin  extends AppCompatActivity {
    private VideoView mVideoView;
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnSkip, btnNext;
    AudioManager audioManager;
    private PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videosgnin);

        prefManager = new PrefManager(videosgnin.this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }



        mVideoView = (VideoView) findViewById(R.id.mVideoView);

        MediaController mc=new MediaController(this);
mc.setAnchorView(mVideoView);
mc.setMediaPlayer(mVideoView);
mVideoView.setMediaController(mc);

        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.backgroundvideo);
        mVideoView.setVideoURI(uri);

        mVideoView.setOnPreparedListener(PreparedListener);
        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = new int[]{
                R.layout.activity_slider1,
                R.layout.activity_slider2,
                R.layout.activity_slider1,
                R.layout.activity_slider2,
        };
        // adding bottom dots



    }


    MediaPlayer.OnPreparedListener PreparedListener=new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
         try {
             if(mp.isPlaying()){
                mp.stop();
                mp.release();
                mp=new MediaPlayer();
             }

            mp.setVolume(0,0);
             mp.setLooping(false);
             mp.start();
         }
         catch (Exception e){
             e.printStackTrace();
         }
        }
    };

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }
    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(videosgnin.this, MainActivity.class));
        finish();
    }
    //  viewpager change listener

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;
        public MyViewPagerAdapter() {
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);
            return view;
        }
        @Override
        public int getCount() {
            return layouts.length;
        }
        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
