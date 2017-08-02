package com.fct.TEDxISTAlameda;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {


    private static  int SPLASH_TIME_OUT = 2100;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MediaPlayer mp = MediaPlayer.create(this, R.raw.pad_confirm);
        mp.start();
        setContentView(R.layout.splash_screen);



        new Handler().postDelayed(new  Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(SplashScreen.this,MainActivity.class);
                startActivity(homeIntent);
                this.finish();
            }
            protected  void finish(){

            }
        },SPLASH_TIME_OUT);





    }
}
