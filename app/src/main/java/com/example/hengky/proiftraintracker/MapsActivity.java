package com.example.hengky.proiftraintracker;


import android.location.Location;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.location.LocationListener;

import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.location.LocationRequest;

import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.model.Marker;

import com.google.android.gms.maps.model.Polyline;


public class MapsActivity extends FragmentActivity implements FragmentListener {

    GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    Polyline polyline;
    FragmentManager fragmentManager;
    GMapFragment gMapFragment;
    ProgressFragment progressFragment;
    OnProgress onProgress;

    public static final int MY_PERMISSIONS_REQUEST_LOCATION=99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        gMapFragment = GMapFragment.newInstance("fragment 1");
        progressFragment = ProgressFragment.newInstance("fragment 2");

        this.fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction ft = this.fragmentManager.beginTransaction();
        ft.add(R.id.frame_container,progressFragment);
        ft.add(R.id.frame_container,gMapFragment);
        ft.commit();

    }

    @Override
    public void changePage() {

    }

}