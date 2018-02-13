package com.example.hengky.proiftraintracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ajithvgiri.searchdialog.*;

import java.util.ArrayList;
import java.util.List;

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
                sd = new SearchableDialog(MainActivity.this, getData(),"List Kereta Api");
                sd.show();
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

}
