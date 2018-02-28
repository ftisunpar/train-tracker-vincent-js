package com.example.hengky.proiftraintracker;

import android.os.Bundle;
import android.content.Intent;
import android.service.chooser.ChooserTargetService;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Toast;

import com.ajithvgiri.searchdialog.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.SimpleSearchFilter;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import ir.mirrajabi.searchdialog.core.Searchable;

/**
 * Created by Lenovo Iyoss on 10/02/2018.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private DatabaseReference rootRef;
    private Button sendData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        if(!FirebaseApp.getApps(this).isEmpty()) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
        rootRef = FirebaseDatabase.getInstance().getReference();
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
        String[] some_array = getResources().getStringArray(R.array.list_kereta);
        for (int i = 0; i < getResources().getStringArray(R.array.list_kereta).length; i++){
            SearchModel model = new SearchModel(some_array[i]);
            list.add(model);
        }


        return list;
    }

    private ArrayList<SampleSearchModel> createSampleData(){
        ArrayList<SampleSearchModel> items = new ArrayList<>();
        String[] some_array = getResources().getStringArray(R.array.list_kereta);
        for (int i = 0; i < getResources().getStringArray(R.array.list_kereta).length; i++){
            SampleSearchModel searchModel = new SampleSearchModel(some_array[i]);
            items.add(searchModel);
        }
        return items;
    }
    public void moveToAnotherActivity(String train){
        Intent intent = new Intent(this, ChooseDestination.class);
        intent.putExtra(EXTRA_MESSAGE, train);
        startActivity(intent);
    }

}
