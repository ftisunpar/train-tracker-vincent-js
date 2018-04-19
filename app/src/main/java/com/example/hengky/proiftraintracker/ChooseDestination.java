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


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class ChooseDestination extends AppCompatActivity implements  View.OnClickListener{
    private static final int MY_PERMISSION_REQUEST_ACCESS_FINE_LOCATION = 10;
    Button buttonMap;
    MainActivity daftarKota;
    ArrayList<String>listKota = new ArrayList<>();
    static String stasiunAwal;
    static String stasiunAkhir;
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("listStasiun");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_destination);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = findViewById(R.id.nama_kereta);
        textView.setText(message);

        daftarKota = new MainActivity();
        listKota = daftarKota.getListKota();

        final Spinner spinner1 = this.findViewById(R.id.spinner_start_stasiun);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getListKotaArr());
        spinner1.setAdapter(adapter);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                stasiunAwal = spinner1.getSelectedItem().toString();
                Toast.makeText(ChooseDestination.this, ("Stasiun awal : "+ stasiunAwal ), Toast.LENGTH_LONG).show();

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        final Spinner spinner2 =this.findViewById(R.id.spinner_end_stasiun);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getListKotaArr());
        spinner2.setAdapter(adapter2);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                stasiunAkhir = spinner2.getSelectedItem().toString();
                Toast.makeText(ChooseDestination.this, ("Stasiun akhir : "+ stasiunAkhir ), Toast.LENGTH_LONG).show();

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        reqPermission();

        buttonMap = this.findViewById(R.id.btnOpenMap);
        //getLangitudeLotitudeKota();
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==buttonMap.getId()){
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

    public void openMap(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    private String[] getListKotaArr(){
        String[] some_array = new String[listKota.size()];
        for (int i=0 ; i<listKota.size();i++){
            some_array[i]=listKota.get(i);
        }
        return some_array;
    }

    public void getLangitudeLotitudeKota(){
        final String [] listKota = getListKotaArr();
        for(int i=0;i<listKota.length;i++){
            final String kotaSekarang = listKota[i];
            ValueEventListener eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot dss : dataSnapshot.getChildren()) {
                        String longitude= (String) dss.child(kotaSekarang).child("longitude").getValue();
                        String latitude = (String) dss.child(kotaSekarang).child("latitude").getValue();
                        Log.d("longitude: ", longitude);
                        Log.d("latitude: ", latitude);
                        System.out.println("longitude : "+longitude);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    ;
                }
            };
            rootRef.addValueEventListener(eventListener);
        }


    }
}