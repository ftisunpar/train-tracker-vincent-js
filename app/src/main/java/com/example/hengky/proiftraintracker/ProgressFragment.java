package com.example.hengky.proiftraintracker;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;
import static android.content.Context.NOTIFICATION_SERVICE;


public class ProgressFragment extends Fragment implements LocationListener {

    private FragmentListener listener;
    MapsActivity context;
    TextView txt, awal, akhir;
    ChooseDestination dataStasiun;
    String stasiunAwal;
    String stasiunAkhir;
    public ProgressFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.on_progress_trip, container, false);
        this.txt = view.findViewById(R.id.velocity);
        this.awal = view.findViewById(R.id.stasiunAwal);
        this.akhir = view.findViewById(R.id.stasiunAkhir);
        dataStasiun = new ChooseDestination();
        stasiunAwal = dataStasiun.stasiunAwal;
        stasiunAkhir = dataStasiun.stasiunAkhir;
        awal.setText("Stasiun Awal: "+stasiunAwal);
        akhir.setText("Stasiun Akhir: "+stasiunAkhir);
        LocationManager locationManager = (LocationManager) this.context.getSystemService(Context.LOCATION_SERVICE);
        this.context.setNotification();
        if (ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
        this.onLocationChanged(null);
        TextView distance = (TextView) view.findViewById(R.id.distance);
        double disRes = this.calculateDistance(6.9142638, 107.6023507 , -7.265422,112.751889 );
        distance.setText(String.format("%.2f", disRes)+" km");

        return view;
    }

    public static  ProgressFragment newInstance(String title){
        ProgressFragment fragment = new ProgressFragment();
        Bundle args = new Bundle();
        args.putString("title",title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = (MapsActivity) context;
        if (context instanceof FragmentListener) {
            listener = (FragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        if(location==null){
            txt.setText("-.- km/h");
        }
        else{
            float nCurrentSpeed = (float) (location.getSpeed() * 3.6);

            txt.setText(String.format("%.0f km/h", nCurrentSpeed));
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

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

}
