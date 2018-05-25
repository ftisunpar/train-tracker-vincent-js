package com.example.hengky.proiftraintracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FinishActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        btnFinish = findViewById(R.id.btn_homepage);
        btnFinish.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == btnFinish){
            //finish();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
