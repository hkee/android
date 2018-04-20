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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LinearLayout second;
    RelativeLayout first,register_page;
    LinearLayout lay_map;
    WebView web_dash;
    FrameLayout game_fm;
    private MarkerOptions mOpt;
    LocationTask locationTask;
    LoginTask loginTask;
    Double slat,slog;
    TextView tv_register;
    EditText input_tel, input_pwd,input_name,input_id,login_id,login_pwd;

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
        input_id=findViewById(R.id.input_id);
        input_tel=findViewById(R.id.input_tel);
        input_pwd=findViewById(R.id.input_pwd);
        input_name=findViewById(R.id.input_name);
        login_id=findViewById(R.id.login_id);
        login_pwd=findViewById(R.id.login_pwd);
        register_page=findViewById(R.id.register_page);
        tv_register=findViewById(R.id.tv_register);
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                first.setVisibility(View.INVISIBLE);
                second.setVisibility(View.INVISIBLE);
                register_page.setVisibility(View.VISIBLE);
                Toast.makeText(MapsActivity.this, "클릭됨", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void clickBt_reg_cancle(View v){
        input_id.setText("");
        input_name.setText("");
        input_pwd.setText("");
        input_tel.setText("");

        first.setVisibility(View.VISIBLE);
        second.setVisibility(View.INVISIBLE);
        register_page.setVisibility(View.INVISIBLE);
    }
    public void clickBt_login(View v){
        //로그인 버튼
        String id = login_id.getText().toString();
        String pwd = login_pwd.getText().toString();
        //more safe to code
        if(id == null || pwd == null || id.equals("") || pwd.equals("")){
            return;
        }

        loginTask = new LoginTask("http://70.12.114.134/bowling/itsMe.do");
        loginTask.execute(id.trim(), pwd.trim());
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
                    locationTask=new LocationTask("http://70.12.114.134/bowling/location.jsp");

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


    //로그인 처리부분
    class LoginTask extends AsyncTask<String, String, String>{

        String url;
        //constructor
        public LoginTask(){

        }
        public LoginTask(String url){
            this.url = url;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... strings) {
            String id = strings[0];
            String pwd = strings[1];
            //To JSP
            url += "?id="+id+"&pwd="+pwd;

            //HTTP REQUEST
            StringBuilder sb = new StringBuilder();
            URL url;
            HttpURLConnection con = null;
            BufferedReader reader=null;
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
             /*       reader  = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String line=null;
                    while(true){
                        line=reader.readLine();
                        if(line==null){
                            break;
                        }
                        sb.append(line);
                    }*/
             return "";
                }
            }catch (Exception e){
                return e.getMessage();
            }finally {
                try {
                    if(reader !=null){
                        reader.close();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                con.disconnect();
            }

            return sb.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            //Toast.makeText(MainActivity.this, "" + s, Toast.LENGTH_SHORT).show();
            if(s.trim().equals("1")){
                Toast.makeText(MapsActivity.this, "success", Toast.LENGTH_SHORT).show();
            }else if(s.trim().equals("2")){
                Toast.makeText(MapsActivity.this, "fail", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
