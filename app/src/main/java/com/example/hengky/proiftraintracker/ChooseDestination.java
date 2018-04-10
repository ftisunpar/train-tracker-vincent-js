package com.example.hengky.proiftraintracker;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.Manifest;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;




import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


import static com.example.hengky.proiftraintracker.R.id.spinner_start_wilis;




public class ChooseDestination extends AppCompatActivity implements  View.OnClickListener{
    private static final int MY_PERMISSION_REQUEST_ACCESS_FINE_LOCATION = 10;
    private static final int MY_PERMISSION_REQUEST_ACCESS_INTERNET = 10;
    private static final int MY_PERMISSION_REQUEST_ACCESS_WRITE_SETTINGS = 10;
    Button buttonMap, buttonGo;

    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("ListKereta").child("argo wilis");
    ArrayList<String> List = new ArrayList<>();
    HashMap<String,Object> map= new HashMap<>();
    String asal;
    String tujuan;
    Spinner spinner1;
    Spinner spinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_destination);

        ValueEventListener eventListener = new ValueEventListener() {

            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dss : dataSnapshot.getChildren()) {
                    String namaKota=dss.getKey();
                    List.add(namaKota);
                    map.put(namaKota,dss.getValue());
                    Log.d("-----------------",""+ namaKota);
                    Log.d("-----------------",""+ dss.child("longitude").getValue());
                    Log.d("-----------------",""+ dss.child("latitude").getValue());
                    Log.d("-----------------",""+ map.get(namaKota));
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                List.add("gagal");
            }
        };
        rootRef.addValueEventListener(eventListener);


        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = findViewById(R.id.argo_wilis);
        textView.setText(message);



        String[] some_array = new String[List.size()];
        for (int i=0 ; i<List.size();i++){
            some_array[i]=List.get(i);
        }

        String[]list_stasiun = some_array;
        spinner1 = this.findViewById(R.id.spinner_start_wilis);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, List);
        spinner1.setAdapter(adapter);

        spinner2 =this.findViewById(R.id.spinner_end_wilis);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, List);

        spinner2.setAdapter(adapter2);
        reqPermission();


        buttonMap = this.findViewById(R.id.btnOpenMap);
        buttonGo = this.findViewById(R.id.btn_go);

        buttonGo.setOnClickListener(this);
        buttonMap.setOnClickListener(this);

//        buttonMap.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openMap(view);
//
//            }
//        });
//        buttonGo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                moveToTripPage(view);
//            }
//        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==buttonGo.getId()){

            moveToTripPage(view);
        }
        else if(view.getId()==buttonMap.getId()){
            openMap(view);
        }
    }

    private void reqPermission(){
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSION_REQUEST_ACCESS_FINE_LOCATION
            );
        }
        else{
            accessLocation();
        }
    }

    private void accessLocation() {
        Toast.makeText(this, "Allow Apps to Access Location", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSION_REQUEST_ACCESS_FINE_LOCATION :
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    accessLocation();
                }
                else{
                    if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)){
                        new AlertDialog.Builder(this).
                                setTitle("Access Location permission").
                                setMessage("You need to grant access location permission" +
                                        "to use this feature. Please retry and grant the permission.").show();
                    }
                    else {
                        new AlertDialog.Builder(this).
                                setTitle("Access Location permission dennied").setMessage("You dennied" +
                                "access location permission. So, the feature will be disabled." +
                                "To enable it, go on setting -> app -> Train Tracker and grant location service permission").show();
                    }
                }

                break;
        }
    }

    public void moveToTripPage(View view){


        Intent intent = new Intent(this, OnProgress.class);

        intent.putExtra("asal",asal);
        intent.putExtra("tujuan",tujuan);
        Log.d("-----------------",""+asal);

        startActivity(intent);
    }

    public void openMap(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }


}