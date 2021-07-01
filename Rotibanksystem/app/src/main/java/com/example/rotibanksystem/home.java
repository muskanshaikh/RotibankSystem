package com.example.rotibanksystem;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */

public class home extends Fragment {
    private VideoView mVideoView;
    private ViewPager viewPager;
    private com.example.rotibanksystem.videosgnin.MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    FirebaseAuth firebaseAuth;
    TextView web1,web2;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private int[] layouts;
    private EditText name,email;
    private Button btnSkip, btnNext,btndonate,btnsubscribe,btnsocial;
    private PrefManager prefManager;




    public home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final FirebaseUser user;
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_videosgnin, container, false);
 prefManager = new PrefManager(getActivity());
                if (!prefManager.isFirstTimeLaunch()) {
                    launchHomeScreen();
                   getActivity().finish();
                }

                btndonate=view.findViewById(R.id.btn_donate);
                btnsubscribe=view.findViewById(R.id.btnsub);
                btnsocial=view.findViewById(R.id.btnsocail);


                name=view.findViewById(R.id.subname);
                email=view.findViewById(R.id.subemail);
        firebaseAuth=FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("subscription");





                mVideoView = (VideoView) view.findViewById(R.id.mVideoView);
                Uri uri = Uri.parse("android.resource://"+getActivity().getPackageName()+"/"+R.raw.backgroundvideo);
                mVideoView.setVideoURI(uri);
                mVideoView.start();
                mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        mediaPlayer.setLooping(true);
                    }
                });


        btnsubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id=user.getUid();



                String uniquekey=databaseReference.push().getKey();
                String namesub=name.getText().toString().trim();
                String emailsub=email.getText().toString().trim();


                String display = "";


                if(TextUtils.isEmpty(namesub)) {
                    display = "Please enter  your name";
                    name.requestFocus();
                }else if(TextUtils.isEmpty(emailsub)){
                    display = "Please enter  your email";
                    email.requestFocus();
                }else

                {
                    databaseReference.child(uniquekey).child("name").setValue(namesub);
                    databaseReference.child(uniquekey).child("email").setValue(emailsub);

                    databaseReference.child(uniquekey).child("id").setValue(id);


                    Toast.makeText(getActivity(),"Thank you for your subscription",Toast.LENGTH_SHORT).show();


                }
                if (!display.equals("")){
                    Toast.makeText(getActivity(), display, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), "Please connect to the internet", Toast.LENGTH_SHORT).show();
                }





            }


    });
                btndonate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getActivity(),dialogue.class);
                        startActivity(intent);
                        getActivity().finish();

                    }
                });





                btnsocial.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myweb();
                    }

                    private void myweb() {

                        openuri("https://shaikhmuskan.imfast.io/muskan/");
                    }

                    private void openuri(String s) {
                        Uri uri=Uri.parse(s);
                        Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                        startActivity(intent);
                    }
                });

                // layouts of all welcome sliders
                // add few more layouts if you want
                layouts = new int[]{
                        R.layout.activity_slider1,
                        R.layout.activity_slider2,
                        R.layout.activity_slider1,
                        R.layout.activity_slider2,
                };
                // adding bottom dots



                return view;
            }

            private int getItem(int i) {
                return viewPager.getCurrentItem() + i;
            }
            private void launchHomeScreen() {
                prefManager.setFirstTimeLaunch(false);
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
            //  viewpager change listener

            /**
             * Making notification bar transparent
             */
            private void changeStatusBarColor() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = getActivity().getWindow();
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
                    layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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




















