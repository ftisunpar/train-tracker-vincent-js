package com.example.hengky.proiftraintracker;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

public class ListKereta {
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("listKereta");
    ArrayList<String> listKereta = new ArrayList<>();

    public ListKereta(){
        ValueEventListener eventListener = new ValueEventListener() {

            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dss : dataSnapshot.getChildren()) {
                    String namaKereta=dss.getKey();
                    listKereta.add(namaKereta);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                listKereta.add("gagal");
            }
        };
        rootRef.addValueEventListener(eventListener);
    }

    public ArrayList<String> getKereta(){
        return this.listKereta;
    }

}
