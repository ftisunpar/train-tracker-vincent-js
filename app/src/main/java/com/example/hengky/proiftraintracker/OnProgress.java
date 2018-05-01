package com.example.hengky.proiftraintracker;

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
import android.os.Build;
import android.os.Bundle;
import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import static android.app.NotificationManager.*;

/**
 * Created by Hengky on 2/10/2018.
 */

public class OnProgress extends AppCompatActivity implements LocationListener {
    private double distance;
    private double timeEstimation;
    private double currentSpeed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setNotification();
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
        //TextView distance = (TextView) this.findViewById(R.id.distance);
        double disRes = this.calculateDistance(6.9142638, 107.6023507 , -7.265422,112.751889 );
        //distance.setText(String.format("%.2f", disRes)+" km");
    }

    public void setNotification(){
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            String Channel_ID = "com.example.hengky.proiftraintracker";
            int importance = IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(Channel_ID, name, importance);
            channel.setDescription(description);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.setShowBadge(true);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            // Register the channel with the system
            nm.createNotificationChannel(channel);

            Intent intent = new Intent(this, OnProgress.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, Channel_ID)
                    .setSmallIcon(R.drawable.train_icon)
                    .setContentTitle("Train Tracker")
                    .setContentText("Anda akan memulai perjalanan")
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText("Anda akan memulai perjalanan"))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            int notificationId = 123456;
            notificationManager.notify(notificationId, mBuilder.build());
        }
        else{
            Intent intent = new Intent(this, OnProgress.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.train_icon)
                    .setContentTitle("Train Tracker")
                    .setContentText("Anda akan memulai perjalanan")
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText("Anda akan memulai perjalanan"))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            int notificationId = 123456;
            notificationManager.notify(notificationId, mBuilder.build());
        }

    }


    @Override
    public void onLocationChanged(Location location) {
        TextView txt = (TextView) this.findViewById(R.id.velocity);
        TextView txtTime = (TextView) this.findViewById(R.id.TimeE);
        if(location==null){
            txt.setText("0.0 m/s");
        }
        else{
            double nCurrentSpeed = location.getSpeed();
            this.currentSpeed=nCurrentSpeed;
            txt.setText(nCurrentSpeed + " m/s");
            if(this.currentSpeed<8.5){
                this.currentSpeed = 12.0;
            }
            this.timeEstimation = this.distance / this.timeEstimation;
            txtTime.setText(String.format("%.2f", timeEstimation)+" s");
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
