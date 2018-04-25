package com.example.hengky.proiftraintracker;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;




public class ProgressFragment extends Fragment implements LocationListener {

    private FragmentListener listener;
    MapsActivity context;
    TextView txt, selanjutnya, akhir;
    ChooseDestination dataStasiun;
    String stasiunSelanjutnya;
    String stasiunAkhir;
    ChooseDestination dataLongitudeLatitude;
    int idxAwal = dataLongitudeLatitude.indexStasiunAwal;
    int idxAkhir = dataLongitudeLatitude.indexStasiunAkhir;
    int currIdx;
    double curLatitude, curLongitude; //lokasi dimana user berada
    double lat1, lat2, latLast ;
    double lng1, lng2, lngLast;
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
        this.txt = view.findViewById(R.id.velocity);
        this.selanjutnya = view.findViewById(R.id.stasiunSelanjutnya);
        this.akhir = view.findViewById(R.id.stasiunAkhir);
        this.finalEstimation =  view.findViewById(R.id.finalStationEstimation);
        this.nextEstimation = view.findViewById(R.id.nextStationEstimation);

        this.currIdx = (idxAwal > idxAkhir)? (idxAwal -1) : (idxAwal+1);

        dataStasiun = new ChooseDestination();
        stasiunSelanjutnya = dataLongitudeLatitude.listStasiun.get(currIdx);;
        stasiunAkhir = dataStasiun.stasiunAkhir;
        dataLongitudeLatitude = new ChooseDestination();
//        Log.d("test latitude", String.valueOf(dataLongitudeLatitude.latitude.get(dataLongitudeLatitude.latitude.size()-1)));
//        Log.d("test longitude", String.valueOf(dataLongitudeLatitude.longitude.get(dataLongitudeLatitude.longitude.size()-1)));
//        Log.d("test index terpilih", stasiunAwal+"-"+dataStasiun.indexStasiunAwal);
//        Log.d("test index terpilih(2)", stasiunAkhir+"-"+dataStasiun.indexStasiunAkhir);
        selanjutnya.setText("Stasiun Selanjutnya: "+stasiunSelanjutnya);
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

        lat1 = dataLongitudeLatitude.latitude.get(currIdx-1);
        lng1 = dataLongitudeLatitude.longitude.get(currIdx-1);
//        String lokasi1=dataLongitudeLatitude.listStasiun.get(currIdx);
//        Log.d("test lokasi 1", lokasi1+ ": "+ String.valueOf(lat1)+" , "+String.valueOf(lng1));

        lat2 = dataLongitudeLatitude.latitude.get(currIdx);
        lng2 = dataLongitudeLatitude.longitude.get(currIdx);
        String lokasi2=dataLongitudeLatitude.listStasiun.get(currIdx);
        Log.d("test lokasi selanjutnya", lokasi2+ ": "+ String.valueOf(lat2)+" , "+String.valueOf(lng2));

        latLast = dataLongitudeLatitude.latitude.get(dataLongitudeLatitude.indexStasiunAkhir);
        lngLast = dataLongitudeLatitude.longitude.get(dataLongitudeLatitude.indexStasiunAkhir);
        String lokasi3=dataLongitudeLatitude.listStasiun.get(dataLongitudeLatitude.indexStasiunAkhir);
        Log.d("test lokasi akhir", lokasi3+ ": "+ String.valueOf(latLast)+" , "+String.valueOf(lngLast));

        double totalDisRes = this.calculateDistance(lat1, lng1 , latLast,lngLast );
        double nextDisRes = this.calculateDistance(lat1, lng1, lat2, lng2);
        double estimasiStasiunSelanjutnya, estimasiStasiunAkhir;

        estimasiStasiunSelanjutnya = nextDisRes / 25;
        estimasiStasiunAkhir = totalDisRes / 25;

        nextEstimation.setText(String.format("Stasiun selanjutnya : %.2f", estimasiStasiunSelanjutnya)+" jam");
        finalEstimation.setText(String.format("Stasiun akhir: %.2f", estimasiStasiunAkhir)+" jam");

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
            txt.setText("0.0 km/h");
        }
        else{
            float nCurrentSpeed = (float) (location.getSpeed() * 3.6);

            txt.setText(String.format("%.0f km/h", nCurrentSpeed));

            curLatitude = location.getLatitude();
            curLongitude = location.getLongitude();

            lat2 = dataLongitudeLatitude.latitude.get(currIdx);
            lng2 = dataLongitudeLatitude.longitude.get(currIdx);
            String lokasi2=dataLongitudeLatitude.listStasiun.get(currIdx);
            Log.d("test lokasi selanjutnya", lokasi2+ ": "+ String.valueOf(lat2)+" , "+String.valueOf(lng2));

            latLast = dataLongitudeLatitude.latitude.get(dataLongitudeLatitude.indexStasiunAkhir);
            lngLast = dataLongitudeLatitude.longitude.get(dataLongitudeLatitude.indexStasiunAkhir);
            String lokasi3=dataLongitudeLatitude.listStasiun.get(dataLongitudeLatitude.indexStasiunAkhir);
            Log.d("test lokasi akhir", lokasi3+ ": "+ String.valueOf(latLast)+" , "+String.valueOf(lngLast));

            double totalDisRes = this.calculateDistance(curLatitude, curLongitude , latLast,lngLast );
            double nextDisRes = this.calculateDistance(curLatitude, curLongitude, lat2, lng2);
            double estimasiStasiunSelanjutnya, estimasiStasiunAkhir;
            if(nCurrentSpeed >= 25){
                estimasiStasiunSelanjutnya = nextDisRes / nCurrentSpeed;
                estimasiStasiunAkhir = totalDisRes / nCurrentSpeed;
            }
            else{
                estimasiStasiunSelanjutnya = nextDisRes / 25;
                estimasiStasiunAkhir = nextDisRes / 25;
            }

            nextEstimation.setText(String.format("Stasiun selanjutnya : %.2f", estimasiStasiunSelanjutnya)+" jam");
            finalEstimation.setText(String.format("Stasiun akhir : %.2f", estimasiStasiunAkhir)+" jam");


            //untuk memberi notifikasi saat sudah dekat stasiun

            if(calculateDistance(lat2,lng2 , curLatitude, curLongitude) < 0.1) { // if distance < 0.1 miles we take locations as equal
                setNotifSaatDekatStasiun(dataStasiun.listStasiun.get(currIdx));
                if(currIdx<dataLongitudeLatitude.indexStasiunAkhir){
                    currIdx++;
                }
                else if(currIdx>dataLongitudeLatitude.indexStasiunAkhir){
                    currIdx--;
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

    }

}
