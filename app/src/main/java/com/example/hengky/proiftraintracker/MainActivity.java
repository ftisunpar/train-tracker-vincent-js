package com.example.hengky.proiftraintracker;

import android.os.Bundle;
import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import ir.mirrajabi.searchdialog.core.Searchable;

/**
 * Created by Lenovo Iyoss on 10/02/2018.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public String selectedTrain;
    ArrayList<String> namaKereta = new ArrayList<String>();
    SplashScreenActivity splashScreenAct;
    private static ArrayList<String>listStasiun=new ArrayList<>();
    DatabaseReference rootRef;
    Button buttonNext,buttonSearch;


    //CountingIdlingResource idlingResource=new CountingIdlingResource("LIST_STASIUN_LOADER");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.splashScreenAct = new SplashScreenActivity();
        namaKereta = splashScreenAct.getKereta();
        setContentView(R.layout.main_page);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String name = ds.getValue(String.class);
                    List.add(name);
                    Log.d("-------------------", name);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                List.add("gagal");
            }
        };
        rootRef.addValueEventListener(eventListener);

        buttonNext = findViewById(R.id.btn_next);
        buttonSearch = findViewById(R.id.btn_search);
        buttonSearch.setOnClickListener(this);
        buttonNext.setOnClickListener(this);

    }

    private ArrayList<SearchModel> getData(){
        ArrayList<SearchModel> list = new ArrayList<>();
        String[] some_array = new String[namaKereta.size()];
        for (int i=0 ; i<namaKereta.size();i++){
            some_array[i]=namaKereta.get(i);
        }
        for (int i = 0; i < some_array.length; i++){
            SearchModel model = new SearchModel(some_array[i]);
            list.add(model);
        }
        return list;
    }

    public void moveToAnotherActivity(String train){
        //finish();
        Intent intent = new Intent(this, ChooseDestination.class);
        intent.putExtra(EXTRA_MESSAGE, train);
        startActivity(intent);
    }

    public void initializeFirebaseListStasiun(){
        rootRef = FirebaseDatabase.getInstance().getReference(selectedTrain);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listStasiun.clear();
                for(DataSnapshot dss : dataSnapshot.getChildren()) {
                    String namaKota=dss.getValue().toString();
                    listStasiun.add(namaKota);
                   // idlingResource.increment();
                    //Log.d("-------------------", namaKota);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                listStasiun.add("gagal");
            }
        };
        rootRef.addValueEventListener(eventListener);
        //moveToAnotherActivity(selectedTrain);
    }

    public static ArrayList<String> getListStasiun() {
        return listStasiun;
    }


    @Override
    public void onClick(View view) {
        if (view == buttonSearch){
            new SimpleSearchDialogCompat(MainActivity.this, "Search...", "Pilih Kereta",
                    null, getData(), new SearchResultListener<Searchable>() {
                @Override
                public void onSelected(BaseSearchDialogCompat baseSearchDialogCompat, Searchable searchable, int i) {
                    selectedTrain = searchable.getTitle();
                    initializeFirebaseListStasiun();
                    Toast.makeText(MainActivity.this, selectedTrain, Toast.LENGTH_LONG).show();
                    baseSearchDialogCompat.dismiss();
                }

            }).show();
        }
        else if(view == buttonNext){
            if(selectedTrain==null){
                Toast.makeText(MainActivity.this, "Silahkan pilih kereta terlebih dahulu", Toast.LENGTH_LONG).show();
            }
            else{
                moveToAnotherActivity(selectedTrain);
            }
        }
    }
}
