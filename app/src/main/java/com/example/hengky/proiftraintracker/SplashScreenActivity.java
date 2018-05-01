package com.example.hengky.proiftraintracker;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SplashScreenActivity extends AppCompatActivity {

    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("ListKereta");
    private  static ArrayList<String> listKereta = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ValueEventListener eventListener = new ValueEventListener() {
            //int cnt = 0;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dss : dataSnapshot.getChildren()) {
                    //cnt++;
                    String namaKereta=dss.getValue().toString();
                    listKereta.add(namaKereta);
                    Log.d("-------------------", namaKereta);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                listKereta.add("gagal");
            }
        };
        rootRef.addValueEventListener(eventListener);

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

    public static ArrayList<String> getKereta(){
        return listKereta;

    }

}
