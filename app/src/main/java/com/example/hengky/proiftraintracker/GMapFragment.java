package com.example.hengky.proiftraintracker;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;


public class GMapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener, GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,LocationListener{
    GoogleMap mMap;
    SupportMapFragment mapFragment;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    FragmentListener listener;
    MapsActivity context;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    Polyline rute;
    ChooseDestination dataLongitudeLatitude;
    LatLng koordinatAwal;
    LatLng koordinatAkhir;
    public GMapFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_fragment,container,false);
        dataLongitudeLatitude = new ChooseDestination();
        double longitudeAwal = dataLongitudeLatitude.longitude.get(dataLongitudeLatitude.indexStasiunAwal);
        double latitudeAwal = dataLongitudeLatitude.latitude.get(dataLongitudeLatitude.indexStasiunAwal);
        double longitudeAkhir = dataLongitudeLatitude.longitude.get(dataLongitudeLatitude.indexStasiunAkhir);
        double latitudeAkhir = dataLongitudeLatitude.latitude.get(dataLongitudeLatitude.indexStasiunAkhir);

        this.koordinatAwal = new LatLng(latitudeAwal, longitudeAwal);
        this.koordinatAkhir = new LatLng(latitudeAkhir, longitudeAkhir);


        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if(mapFragment == null){
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.map,mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        return view;
    }

    public static  GMapFragment newInstance(String title){
        GMapFragment fragment = new GMapFragment();
        Bundle args = new Bundle();
        args.putString("title",title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = (MapsActivity) context;
        if(context instanceof FragmentListener){
            this.listener = (FragmentListener)context;
        }else{
            throw new ClassCastException(context.toString() + " must implement FragmentListener");
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Bandung and move the camera
        //LatLng sydney = new LatLng(-6.875104, 107.605011);


        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this.context,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);

            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(3f));
       /* if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
         */
        mMap.setMyLocationEnabled(true);

        PolylineOptions pOptions = new PolylineOptions();



//        if(dataLongitudeLatitude.indexStasiunAwal < dataLongitudeLatitude.indexStasiunAkhir){
//            int curIdx = dataLongitudeLatitude.indexStasiunAwal;
//            double curLongitude = dataLongitudeLatitude.longitude.get(curIdx);
//            double curLatitude = dataLongitudeLatitude.latitude.get(curIdx);
//            LatLng curPosition= new LatLng(curLatitude, curLongitude);
//            for(int i=dataLongitudeLatitude.indexStasiunAwal;i<dataLongitudeLatitude.indexStasiunAkhir;i++){
//                pOptions.add(curPosition);
//                curIdx ++;
//                curLongitude = dataLongitudeLatitude.longitude.get(curIdx);
//                curLatitude = dataLongitudeLatitude.latitude.get(curIdx);
//                curPosition= new LatLng(curLatitude, curLongitude);
//            }
//        }
//        else{
//            int curIdx = dataLongitudeLatitude.indexStasiunAwal;
//            double curLongitude = dataLongitudeLatitude.longitude.get(curIdx);
//            double curLatitude = dataLongitudeLatitude.latitude.get(curIdx);
//            LatLng curPosition= new LatLng(curLatitude, curLongitude);
//            for(int i=dataLongitudeLatitude.indexStasiunAwal;i>dataLongitudeLatitude.indexStasiunAkhir;i--){
//
//                pOptions.add(curPosition);
//                curIdx --;
//                curLongitude = dataLongitudeLatitude.longitude.get(curIdx);
//                curLatitude = dataLongitudeLatitude.latitude.get(curIdx);
//                curPosition= new LatLng(curLatitude, curLongitude);
//            }
//        }
        ProgressFragment test = new ProgressFragment();

        LatLng lokasi1 = new LatLng(test.testLatitude[0], test.testLongitude[0]);
        LatLng lokasi2 = new LatLng(test.testLatitude[1], test.testLongitude[1]);
        LatLng lokasi3 = new LatLng(test.testLatitude[2], test.testLongitude[2]);
        pOptions.add(lokasi1).add(lokasi2).add(lokasi3);
        pOptions.width(5).color(Color.RED).geodesic(true);
        MarkerOptions mOptionAWal = new MarkerOptions().position(koordinatAwal);
        MarkerOptions mOptionAkhir = new MarkerOptions().position(koordinatAkhir);
        mMap.addMarker(mOptionAWal);
        mMap.addMarker(mOptionAkhir);
        mMap.addPolyline(pOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(koordinatAwal, 17));

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this.context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this.context,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (LocationListener) this);
        }
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(latLng);
//        markerOptions.title("Current Position");
//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
//        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(14.5f));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }
}