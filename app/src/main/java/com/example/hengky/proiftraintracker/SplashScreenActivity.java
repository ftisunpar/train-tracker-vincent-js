package com.example.hengky.proiftraintracker;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);

            //menghilangkan ActionBar
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.splash_screen);



            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }, 1000L); //3000 L = 3 detik
        }

}
