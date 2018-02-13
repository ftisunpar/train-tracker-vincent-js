package com.example.hengky.proiftraintracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Filter;
import android.widget.Toast;

import com.ajithvgiri.searchdialog.*;

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

public class MainActivity extends AppCompatActivity {

    public SearchableDialog sd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SimpleSearchDialogCompat(MainActivity.this, "Search...",
                        "What are you looking for...?", null, createSampleData(),
                        new SearchResultListener<SampleSearchModel>() {
                            @Override
                            public void onSelected(BaseSearchDialogCompat dialog,
                                                   SampleSearchModel item, int position) {
                                Toast.makeText(MainActivity.this, item.getTitle(),
                                        Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }).show();

            }
        });
    }
    private List<SearchListItem> getData(){
        List<SearchListItem> list = new ArrayList<>();
        String[] some_array = getResources().getStringArray(R.array.list_kereta);
        for (int i = 0; i < getResources().getStringArray(R.array.list_kereta).length; i++){
            SearchListItem searchListItem = new SearchListItem(i, some_array[i]);
            list.add(searchListItem);
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

}
