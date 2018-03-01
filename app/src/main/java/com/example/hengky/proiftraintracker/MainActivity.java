package com.example.hengky.proiftraintracker;

import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("listKereta");

    ArrayList<String> List = new ArrayList<>();
    private Button sendData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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



        this.sendData = this.findViewById(R.id.tes);
        this.sendData.setOnClickListener(this);

        findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SimpleSearchDialogCompat(MainActivity.this, "Search...", "Pilih Kereta",
                        null, getData(), new SearchResultListener<Searchable>() {
                    @Override
                    public void onSelected(BaseSearchDialogCompat baseSearchDialogCompat, Searchable searchable, int i) {
                        moveToAnotherActivity(searchable.getTitle());
                    }
                }).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view == sendData){
            DatabaseReference mRefChild = rootRef.child("Name");

            mRefChild.setValue("Yosua");
        }
    }

    private ArrayList<SearchModel> getData(){
        ArrayList<SearchModel> list = new ArrayList<>();
        String[] some_array = new String[List.size()];
        for (int i=0 ; i<List.size();i++){
            some_array[i]=List.get(i);
        }
        for (int i = 0; i < some_array.length; i++){
            SearchModel model = new SearchModel(some_array[i]);
            list.add(model);
        }


        return list;
    }

    private ArrayList<SearchModel> createSampleData(){
        ArrayList<SearchModel> list = new ArrayList<>();
        String[] some_array = new String[List.size()];
        for (int i=0 ; i<List.size();i++){
            some_array[i]=List.get(i);
        }
        for (int i = 0; i < some_array.length; i++){
            SearchModel model = new SearchModel(some_array[i]);
            list.add(model);
        }


        return list;
    }
    public void moveToAnotherActivity(String train){
        Intent intent = new Intent(this, ChooseDestination.class);
        intent.putExtra(EXTRA_MESSAGE, train);
        startActivity(intent);
    }

}
