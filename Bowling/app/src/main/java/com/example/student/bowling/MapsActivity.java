package com.example.student.bowling;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LinearLayout second;
    RelativeLayout first;
    LinearLayout lay_map;
    WebView web_dash;
    FrameLayout game_fm;
    private MarkerOptions mOpt;
    LocationTask locationTask;
    Double slat,slog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        first=findViewById(R.id.first);
        second=findViewById(R.id.second);
        web_dash=findViewById(R.id.web_dash);
        game_fm=findViewById(R.id.game_fm);
        lay_map=findViewById(R.id.lay_map);
    }
    public void clickBt_login(View v){
        //로그인 버튼
    }
    public void clickBt_register(View v){

    }
    public void clickBt_map(View v){

        new Thread(r).start();
     web_dash.setVisibility(View.INVISIBLE);
      game_fm.setVisibility(View.INVISIBLE);
      lay_map.setVisibility(View.VISIBLE);

    }
    Runnable r= new Runnable() {
        @Override
        public void run() {

                    //좌표를 가지고옴
                    locationTask=new LocationTask("http://70.12.114.140/android/location.jsp");

                    locationTask.execute(slat,slog);

        }
    };
//지도관련 메소드
    private void requestMyLocation() {
        LocationManager manager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try {
            long minTime = 10000;
            float minDistance = 0;
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime,
                    minDistance,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            showCurrentLocation(location);
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {

                        }

                        @Override
                        public void onProviderEnabled(String provider) {

                        }

                        @Override
                        public void onProviderDisabled(String provider) {

                        }
                    }
            );

            Location lastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastLocation != null) {
                showCurrentLocation(lastLocation);
            }

            manager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    minTime,
                    minDistance,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            showCurrentLocation(location);
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {

                        }

                        @Override
                        public void onProviderEnabled(String provider) {

                        }

                        @Override
                        public void onProviderDisabled(String provider) {

                        }
                    }
            );

        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
    private void showCurrentLocation(Location location) {
        LatLng curPoint = new LatLng(location.getLatitude(), location.getLongitude());

        slat=location.getLatitude();
        slog=location.getLongitude();
        if (mOpt == null) {
            mOpt = new MarkerOptions();
        }
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 14));
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
       // LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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
        mMap.setMyLocationEnabled(true);
        requestMyLocation();
    }
    //지도관련 메소드 끝

    //위치정보 보내고 주변 볼링장 정보 받아오기
    class LocationTask extends AsyncTask<Double, String, String> {
        String center[]=new String[15];
        String rlat[]=new String[15];
        String rlog[]=new String[15];
        BufferedReader reader=null;
        String url;
        //constructor
        public LocationTask(){

        }
        public LocationTask(String url){
            this.url = url;
        }

        @Override
        protected String doInBackground(Double... doubles) {
            double lat=doubles[0];
            double log=doubles[1];
            url += "?lat="+lat+"&log="+log;
            //HTTP REQUEST
            StringBuilder sb = new StringBuilder();
            URL url;
            HttpURLConnection con = null;
            try{
                url = new URL(this.url);
                //Connection
                con = (HttpURLConnection)url.openConnection();
                if(con != null){
                    con.setConnectTimeout(5000);
                    con.setRequestMethod("GET");
                    con.setRequestProperty("Accept","*/*");
                    if(con.getResponseCode()!=HttpURLConnection.HTTP_OK){
                        return null;
                    }
                    reader  = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String line=null;
                    while(true){
                        line=reader.readLine();
                        if(line==null){
                            break;
                        }
                        sb.append(line);
                    }
                }
            }catch (Exception e){
                return e.getMessage();

            }finally {
                con.disconnect();
            }
            return sb.toString();
           // return "";

        }

        @Override
        protected void onPostExecute(String s) {
            mMap.clear();
            LatLng lo[]=new LatLng[15];

            String temp[]=s.split("/");
            int c=Integer.parseInt(temp[temp.length-1]);
            for(int i=1;i<=c;i++){
                rlat[i]=temp[i];
                rlog[i]=temp[i+c];
                center[i]=temp[i+(c*2)];
                lo[i]=new LatLng(Double.parseDouble(rlat[i]),Double.parseDouble(rlog[i]));
                mMap.addMarker(new MarkerOptions().position(lo[i]).title(center[i]));
            }

            //Toast.makeText(MapsActivity.this, ""+center[1], Toast.LENGTH_SHORT).show();
        }
    }

}
