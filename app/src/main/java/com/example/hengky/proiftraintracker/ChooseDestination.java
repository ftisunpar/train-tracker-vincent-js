package com.example.hengky.proiftraintracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;



import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class ChooseDestination extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_destination);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = findViewById(R.id.argo_wilis);
        textView.setText(message);

        String[]list_stasiun = getResources().getStringArray(R.array.list_stasiun_argo_wilis);
        Spinner spinner1 = (Spinner) findViewById(R.id.spinner_start_wilis);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list_stasiun);
        spinner1.setAdapter(adapter);

        Spinner spinner2 = (Spinner) findViewById(R.id.spinner_end_wilis);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list_stasiun);
        spinner2.setAdapter(adapter2);
    }

    public void moveToTripPage(View view){
        Intent intent = new Intent(this, OnProgress.class);
        startActivity(intent);
    }

    public void openMap(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}