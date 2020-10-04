package com.jai.hostelappwarden;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends Activity {

    TextView t1,t2,t3;
    ImageView smile;

    TextView letchat;

    TextView belowtext;

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        sharedPreferences=getSharedPreferences("status",MODE_PRIVATE);

        boolean status=sharedPreferences.getBoolean("onlineStatus",false);





        smile=findViewById(R.id.smile12);

        belowtext=findViewById(R.id.belowtext);


        letchat=findViewById(R.id.letchat);

        t1=findViewById(R.id.imageView6);
        t2=findViewById(R.id.textView12);
        t3=findViewById(R.id.textView13);



        Animation smileanim= AnimationUtils.loadAnimation(this,R.anim.splash);



        Animation bottom=AnimationUtils.loadAnimation(this,R.anim.bottom);

        smile.startAnimation(bottom);
        letchat.startAnimation(smileanim);
        belowtext.startAnimation(smileanim);


        t1.startAnimation(smileanim);
        t2.startAnimation(smileanim);
        t3.startAnimation(smileanim);


        if(status==true)
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent intent=new Intent(SplashActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            },3000);
        }



        else
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            },3000);
        }



    }

}
