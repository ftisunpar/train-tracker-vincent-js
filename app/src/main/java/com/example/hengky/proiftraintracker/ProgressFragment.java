package com.example.hengky.proiftraintracker;

import android.Manifest;
import android.app.Notification;
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
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;
import static android.content.Context.NOTIFICATION_SERVICE;
import static android.content.Context.VIBRATOR_SERVICE;


public class ProgressFragment extends Fragment implements LocationListener {

    private FragmentListener listener;
    MapsActivity mapsActivity;
    TextView txt, selanjutnya, akhir;
    ChooseDestination dataStasiun;
    String stasiunSelanjutnya;
    String stasiunAkhir;
    int idxSekarang,idxSelanjutnya, idxAkhir;
    LocationManager locationManager;
    double curLatitude, curLongitude; //lokasi dimana user berada
    double lat1, lat2, latLast ;
    double lng1, lng2, lngLast;

    boolean[] isRinging;
    boolean isArrived;

    TextView finalEstimation;
    TextView nextEstimation;

    public ProgressFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.on_progress_trip, container, false);
        this.isRinging = new boolean[dataStasiun.indexStasiunAkhir+1];
        this.txt = view.findViewById(R.id.velocity);
        this.selanjutnya = view.findViewById(R.id.stasiunSelanjutnya);
        this.akhir = view.findViewById(R.id.stasiunAkhir);
        this.finalEstimation =  view.findViewById(R.id.finalStationEstimation);
        this.nextEstimation = view.findViewById(R.id.nextStationEstimation);
        dataStasiun = new ChooseDestination();
        this.isArrived = false;
        this.idxSekarang = dataStasiun.indexStasiunAwal;
        this.idxAkhir = dataStasiun.indexStasiunAkhir;

        this.idxSelanjutnya = (idxSekarang < idxAkhir)? (idxSekarang + 1) : (idxSekarang-1);
        Log.d("index awal", String.valueOf(idxSekarang));
        Log.d("index selanjutnya", String.valueOf(idxSelanjutnya));
        Log.d("index akhir", String.valueOf(idxAkhir));
        stasiunSelanjutnya = dataStasiun.listStasiun.get(idxSelanjutnya);
        stasiunAkhir = dataStasiun.stasiunAkhir;

        selanjutnya.setText("Stasiun Selanjutnya: "+stasiunSelanjutnya);
        akhir.setText("Stasiun Akhir: "+stasiunAkhir);
        this.locationManager = (LocationManager) this.mapsActivity.getSystemService(Context.LOCATION_SERVICE);
        this.mapsActivity.setNotification();

        if (ActivityCompat.checkSelfPermission(this.mapsActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.mapsActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
//        if(idxSekarang < idxAkhir ){
//            lat1 = dataStasiun.latitude.get(nextIdx-1);
//            lng1 = dataStasiun.longitude.get(nextIdx-1);
//        }
//        else{
//            lat1 = dataStasiun.latitude.get(nextIdx+1);
//            lng1 = dataStasiun.longitude.get(nextIdx+1);
//        }
        lat1= dataStasiun.latitude.get(idxSekarang);
        lng1 = dataStasiun.longitude.get(idxSekarang);

//        String lokasi1=dataStasiun.listStasiun.get(nextIdx);
//        Log.d("test lokasi 1", lokasi1+ ": "+ String.valueOf(lat1)+" , "+String.valueOf(lng1));

        lat2 = dataStasiun.latitude.get(idxSelanjutnya);
        lng2 = dataStasiun.longitude.get(idxSelanjutnya);
        String lokasi2=dataStasiun.listStasiun.get(idxSelanjutnya);
        Log.d("test lokasi selanjutnya", lokasi2+ ": "+ String.valueOf(lat2)+" , "+String.valueOf(lng2));

        latLast = dataStasiun.latitude.get(dataStasiun.indexStasiunAkhir);
        lngLast = dataStasiun.longitude.get(dataStasiun.indexStasiunAkhir);
        String lokasi3=dataStasiun.listStasiun.get(dataStasiun.indexStasiunAkhir);
        Log.d("test lokasi akhir", lokasi3+ ": "+ String.valueOf(latLast)+" , "+String.valueOf(lngLast));

        double totalDisRes = this.calculateDistance(lat1, lng1, latLast, lngLast );
        double nextDisRes = this.calculateDistance(lat1, lng1, lat2, lng2);
        String estimasiStasiunSelanjutnya, estimasiStasiunAkhir;

        estimasiStasiunSelanjutnya = calculateTime(nextDisRes*1000, 25/3.6);
        estimasiStasiunAkhir = calculateTime(totalDisRes*1000, 25/3.6);

        nextEstimation.setText("Stasiun selanjutnya : "+ estimasiStasiunSelanjutnya);
        finalEstimation.setText("Stasiun akhir : " + estimasiStasiunAkhir);

        return view;
    }

    public static ProgressFragment newInstance(String title){
        ProgressFragment fragment = new ProgressFragment();
        Bundle args = new Bundle();
        args.putString("title",title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mapsActivity = (MapsActivity) context;
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
            txt.setText("0.0 km/h");
        }
        else{
            float nCurrentSpeed = (float) (location.getSpeed());

            txt.setText(String.format("%.0f km/h", nCurrentSpeed*3.6));

            curLatitude = location.getLatitude();
            curLongitude = location.getLongitude();


            double totalDisRes = this.calculateDistance(curLatitude, curLongitude , latLast,lngLast );
            double nextDisRes = this.calculateDistance(curLatitude, curLongitude, lat2, lng2);
            String estimasiStasiunSelanjutnya, estimasiStasiunAkhir;
            if(nCurrentSpeed >= 25){
                estimasiStasiunSelanjutnya = calculateTime(nextDisRes*1000, nCurrentSpeed);
                estimasiStasiunAkhir = calculateTime(totalDisRes*1000, nCurrentSpeed);
            }
            else{
                estimasiStasiunSelanjutnya = calculateTime(nextDisRes*1000, 25/3.6);
                estimasiStasiunAkhir = calculateTime(totalDisRes*1000, 25/3.6);
            }

            nextEstimation.setText("Stasiun selanjutnya : "+ estimasiStasiunSelanjutnya);
            finalEstimation.setText("Stasiun akhir : " + estimasiStasiunAkhir);

            //untuk memberi notifikasi saat sudah dekat stasiun
            if(nextDisRes<0.75) { // jika jarak lebih kecil dari 750 meter, diasumsikan sudah dekat dengan stasiun berikutnya
                if(isRinging[idxSelanjutnya]==false){//agar tidak berdering lebih dari satu kali
                    setNotifSaatDekatStasiun(dataStasiun.listStasiun.get(idxSelanjutnya));
                    isRinging[idxSelanjutnya] = true;
                }

                if(idxSelanjutnya == dataStasiun.indexStasiunAkhir && isArrived == false && nextDisRes<0.05){//jika jarak lebih kecil dari 50 meter, diasumsikan sudah sampai
                    this.locationManager.removeUpdates(this);
                    this.locationManager = null;
                    this.isArrived = true;
                    Intent intent = new Intent(getActivity(), FinishActivity.class);
                    startActivity(intent);
                }
                else{
                    if(idxSelanjutnya<dataStasiun.indexStasiunAkhir){
                        idxSelanjutnya++;
                    }
                    else if(idxSelanjutnya>dataStasiun.indexStasiunAkhir){
                        idxSelanjutnya--;
                    }
                    lat2 = dataStasiun.latitude.get(idxSelanjutnya);
                    lng2 = dataStasiun.longitude.get(idxSelanjutnya);

                    this.stasiunSelanjutnya = dataStasiun.listStasiun.get(idxSelanjutnya);
                    this.selanjutnya.setText("Stasiun Selanjutnya: "+stasiunSelanjutnya);
                }
            }
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

    public void setNotifSaatDekatStasiun(String stasiun){
        Intent intent =new Intent();
        PendingIntent pIntent = PendingIntent.getActivity(this.mapsActivity, 0, intent, 0);
        Notification noti = new Notification.Builder(this.mapsActivity)
                .setTicker("Train Tracker")
                .setContentTitle("Train Tracker")
                .setContentText("Sebentar lagi anda akan tiba di " + stasiun)
                .setSmallIcon(R.drawable.train_icon)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pIntent).getNotification();
        noti.flags = Notification.FLAG_AUTO_CANCEL;
        NotificationManager nm = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
        nm.notify(0, noti);
        Vibrator v = (Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.DEFAULT_AMPLITUDE));
        }else{
            //deprecated in API 26
            v.vibrate(500);
        }
    }

    public String calculateTime(double distance, double speed){
        double totalSecs = distance/speed;
        double hours = totalSecs / 3600;
        double minutes = (totalSecs % 3600) / 60;
        //double seconds = totalSecs % 60;
        String timeString="";
        if(hours<1){
            timeString = String.format("%.0f Menit", minutes);

        }
        else{
            timeString = String.format("%.0f Jam %.0f Menit", hours, minutes);
        }


        return timeString;
    }

}
