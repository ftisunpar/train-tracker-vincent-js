package com.example.hengky.proiftraintracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class ChooseDestination extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_destination);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = findViewById(R.id.argo_wilis);
        textView.setText(message);
    }

    public void moveToTripPage(View view){
        Intent intent = new Intent(this, OnProgress.class);
        startActivity(intent);
    }
}