package com.example.hengky.proiftraintracker;


import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;

import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.KeyEvent;


import static android.app.NotificationManager.IMPORTANCE_DEFAULT;


public class MapsActivity extends FragmentActivity implements FragmentListener {

    FragmentManager fragmentManager;
    GMapFragment gMapFragment;
    ProgressFragment progressFragment;

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

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();

            //moveTaskToBack(false);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void exitByBackKey() {
        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("Apakah anda yakin ingin keluar?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                        //close();


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();

    }

    @Override
    public void changePage() {

    }

    @Override
    public void setNotification() {
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

            Intent intent = new Intent();
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
            Intent intent = new Intent();
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
        Vibrator v = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.DEFAULT_AMPLITUDE));
        }else{
            //deprecated in API 26
            v.vibrate(500);
        }

    }

}

