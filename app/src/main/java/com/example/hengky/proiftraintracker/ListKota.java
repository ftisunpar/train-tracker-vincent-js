package com.example.hengky.proiftraintracker;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by adrian stefanus on 07/04/2018.
 */

public class ListKota {
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("ListKereta").child("argo wilis");
    ArrayList<String> List = new ArrayList<>();
    ArrayList<String> latitude = new ArrayList<>();
    ArrayList<String> longitude = new ArrayList<>();
    public ListKota(){
        ValueEventListener eventListener = new ValueEventListener() {

            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dss : dataSnapshot.getChildren()) {
                    String namaKota=dss.getKey();
                    List.add(namaKota);
                   latitude.add(dss.child("latitude").getValue().toString());
                    longitude.add(dss.child("longitude").getValue().toString());
                    Log.d("-----------------",""+ namaKota);
                    Log.d("-----------------",""+ dss.child("longitude").getValue());
                    Log.d("-----------------",""+ dss.child("latitude").getValue());

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                List.add("gagal");
            }
        };
        rootRef.addValueEventListener(eventListener);
    }
    public ArrayList<String> getKota(){
        return this.List;
    }
    public String getLatitude(String namaKota){
        int position=0;
        for (int i =0;i<List.size();i++){
            if(List.get(i)==namaKota){
                position=i;

            }
        }
        return latitude.get(position);
    }
    public String getLongitude(String namaKota){
        int position=0;
        for (int i =0;i<List.size();i++){
            if(List.get(i)==namaKota){
                position=i;

            }
        }
        return longitude.get(position);
    }

}
