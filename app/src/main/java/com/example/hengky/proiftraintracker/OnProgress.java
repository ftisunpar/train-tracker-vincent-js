package com.example.hengky.proiftraintracker;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Hengky on 2/10/2018.
 */

public class OnProgress extends AppCompatActivity implements LocationListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.on_progress_trip);

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        this.onLocationChanged(null);

    }


    @Override
    public void onLocationChanged(Location location) {
        TextView txt = (TextView) this.findViewById(R.id.velocity);
        if(location==null){
            txt.setText("-.- m/s");
        }
        else{
            float nCurrentSpeed = location.getSpeed();
            txt.setText(nCurrentSpeed + " m/s");
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }



}
