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
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
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
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LinearLayout second;
    RelativeLayout first,register_page,lay_game;
    LinearLayout lay_map,lay_input;
    WebView web_dash;
    FrameLayout game_fm;
    private MarkerOptions mOpt;
    LocationTask locationTask;
    LoginTask loginTask;
    RegisterTask registerTask;
    GameTask gameTask;
    GameOpenTask gameOpenTask;
    ScoreInputTask scoreInputTask;
    Double slat,slog;
    TextView tv_register,tv_sum;
    EditText input_tel, input_pwd,input_name,input_id,login_id,login_pwd;
    EditText tx_gp,tx_gt,tx_gn;
    String myId="";
    Button bt_gst,bt_game,bt_dash,bt_map;
    String g_num="";
    String g_title="";
    String g_master="";
    String g_date="";

    EditText selectedText = null;

    EditText frame1Score1Player1;
    EditText frame1Score2Player1;
    EditText frame2Score1Player1;
    EditText frame2Score2Player1;
    EditText frame3Score1Player1;
    EditText frame3Score2Player1;
    EditText frame4Score1Player1;
    EditText frame4Score2Player1;
    EditText frame5Score1Player1;
    EditText frame5Score2Player1;
    EditText frame6Score1Player1;
    EditText frame6Score2Player1;
    EditText frame7Score1Player1;
    EditText frame7Score2Player1;
    EditText frame8Score1Player1;
    EditText frame8Score2Player1;
    EditText frame9Score1Player1;
    EditText frame9Score2Player1;
    EditText frame10Score1Player1;
    EditText frame10Score2Player1;
    EditText frame10Score3Player1;

    EditText[] frameScore = {frame1Score1Player1, frame1Score2Player1, frame2Score1Player1, frame2Score2Player1, frame3Score1Player1,
            frame3Score2Player1,frame4Score1Player1,frame4Score2Player1,frame5Score1Player1,frame5Score2Player1,frame6Score1Player1,
            frame6Score2Player1,frame7Score1Player1,frame7Score2Player1,frame8Score1Player1, frame8Score2Player1,frame9Score1Player1,
            frame9Score2Player1,frame10Score1Player1,frame10Score2Player1,frame10Score3Player1};

    int[] frameIDScoresPlayer1 = {R.id.frame1Score1Player1, R.id.frame1Score2Player1, R.id.frame2Score1Player1, R.id.frame2Score2Player1, R.id.frame3Score1Player1,
            R.id.frame3Score2Player1,R.id.frame4Score1Player1,R.id.frame4Score2Player1,R.id.frame5Score1Player1,R.id.frame5Score2Player1,R.id.frame6Score1Player1,
            R.id.frame6Score2Player1,R.id.frame7Score1Player1,R.id.frame7Score2Player1,R.id.frame8Score1Player1, R.id.frame8Score2Player1,R.id.frame9Score1Player1,
            R.id.frame9Score2Player1,R.id.frame10Score1Player1,R.id.frame10Score2Player1,R.id.frame10Score3Player1};

    int[] frameScorePlayer1;
    int[] frameTotalPlayer1;

    TextView frame1TotalScorePlayer1;
    TextView frame2TotalScorePlayer1;
    TextView frame3TotalScorePlayer1;
    TextView frame4TotalScorePlayer1;
    TextView frame5TotalScorePlayer1;
    TextView frame6TotalScorePlayer1;
    TextView frame7TotalScorePlayer1;
    TextView frame8TotalScorePlayer1;
    TextView frame9TotalScorePlayer1;
    TextView frame10TotalScorePlayer1;

    TextView[] frameTotalScorePlayer1 = {frame1TotalScorePlayer1, frame2TotalScorePlayer1,frame3TotalScorePlayer1, frame4TotalScorePlayer1,
            frame5TotalScorePlayer1, frame6TotalScorePlayer1, frame7TotalScorePlayer1, frame8TotalScorePlayer1, frame9TotalScorePlayer1,
            frame10TotalScorePlayer1};
    int[] frameIDTotalPlayer1 = {R.id.frame1TotalScorePlayer1, R.id.frame2TotalScorePlayer1,R.id.frame3TotalScorePlayer1, R.id.frame4TotalScorePlayer1,
            R.id.frame5TotalScorePlayer1, R.id.frame6TotalScorePlayer1, R.id.frame7TotalScorePlayer1, R.id.frame8TotalScorePlayer1, R.id.frame9TotalScorePlayer1,
            R.id.frame10TotalScorePlayer1
    };

    int countStrike = 0;
    int countSpare = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        bt_gst= findViewById(R.id.bt_gst);
        bt_map=findViewById(R.id.bt_map);
        lay_game=findViewById(R.id.lay_game);
        bt_dash=findViewById(R.id.bt_dash);
        bt_game=findViewById(R.id.bt_game);
        first=findViewById(R.id.first);
        second=findViewById(R.id.second);
        web_dash=findViewById(R.id.web_dash);
        tv_sum=findViewById(R.id.tv_sum);
        game_fm=findViewById(R.id.game_fm);
        lay_map=findViewById(R.id.lay_map);
        lay_input=findViewById(R.id.lay_input);
        input_id=findViewById(R.id.input_id);
        input_tel=findViewById(R.id.input_tel);
        input_pwd=findViewById(R.id.input_pwd);
        input_name=findViewById(R.id.input_name);
        login_id=findViewById(R.id.login_id);
        login_pwd=findViewById(R.id.login_pwd);
        tx_gp=findViewById(R.id.tx_gp);
        tx_gn=findViewById(R.id.tx_gn);
        tx_gt=findViewById(R.id.tx_gt);
        tx_gt.addTextChangedListener(gc_tx);
        tx_gn.addTextChangedListener(gj_tx);
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
        init();
    }
    //게임시작 버튼 활성화-게임 개설시
    private TextWatcher gc_tx=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(charSequence.length()>0){
                bt_gst.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
//게임 시작버튼 활성화-게임 참가시
    private  TextWatcher gj_tx=new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(charSequence.length()>0){
            bt_gst.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
};
//점수 입력부분 취소버튼
    public void clickBt_scncl(View v){
        lay_game.setVisibility(View.VISIBLE);
        lay_input.setVisibility(View.INVISIBLE);
    }
    //점수입력 부분 완료버튼
    public void clickBt_scs(View v){
        scoreInputTask=new ScoreInputTask("http://70.12.114.134/bowling/inputScore.do");
        scoreInputTask.execute();
        lay_game.setVisibility(View.VISIBLE);
        lay_input.setVisibility(View.INVISIBLE);

    }
    //하단 게임 버튼
    public void clickBt_game(View v){
        bt_game.setSelected(true);
       bt_dash.setSelected(false);
        bt_map.setSelected(false);
        game_fm.setVisibility(View.VISIBLE);
        web_dash.setVisibility(View.INVISIBLE);
        lay_map.setVisibility(View.INVISIBLE);
    }
    //하단 대시보드 버튼
    public void clickBt_dash(View v){
        bt_game.setSelected(false);
        bt_dash.setSelected(true);
        bt_map.setSelected(false);
        game_fm.setVisibility(View.INVISIBLE);
        web_dash.setVisibility(View.VISIBLE);
        lay_map.setVisibility(View.INVISIBLE);
        WebSettings webSettings = web_dash.getSettings();
        webSettings.setJavaScriptEnabled(true);
        web_dash.setWebViewClient(new WebViewClient());
        web_dash.loadUrl("http://70.12.114.134/bowling/dashBoard.do?id="+myId);


    }

    //게임 개설버튼
    public void clickBt_gc(View v){
        tx_gt.setVisibility(View.VISIBLE);
        tx_gp.setVisibility(View.VISIBLE);
        tx_gn.setVisibility(View.INVISIBLE);
        tx_gp.setText(myId);
        tx_gp.setEnabled(false);

    }
    //게임 참가버튼
    public void clickBt_gj(View v){
        tx_gt.setVisibility(View.INVISIBLE);
        tx_gp.setVisibility(View.INVISIBLE);
        tx_gn.setVisibility(View.VISIBLE);
    }
    //게임 시작버튼
    public void clickBt_gst(View v){


        //게임참가버튼을 눌렀을 경우
        if(tx_gn.getVisibility()==View.VISIBLE){
            String gameNum=tx_gn.getText().toString();
            if(gameNum==null||gameNum.equals("")){
                return;
            }
            gameTask=new GameTask("http://70.12.114.134/bowling/joinGame.do");
            gameTask.execute(gameNum.trim());
        }else if(tx_gt.getVisibility()==View.VISIBLE){
            String gameTitle=tx_gt.getText().toString();
            String gameMaster=tx_gp.getText().toString();
            if(gameTitle==null||gameTitle.equals("")){
                return;
            }
            gameOpenTask=new GameOpenTask("http://70.12.114.134/bowling/openGame.do");
            gameOpenTask.execute(gameTitle,gameMaster);


        }

        lay_game.setVisibility(View.INVISIBLE);
        lay_input.setVisibility(View.VISIBLE);

    }

    //회원가입창 취소버튼
    public void clickBt_reg_cancel(View v){
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
    //회원가입 버튼
    public void clickBt_register(View v){
        String id = input_id.getText().toString();
        String pwd = input_pwd.getText().toString();
        String name =input_name.getText().toString();
        String tel= input_tel.getText().toString();
        //more safe to code
        if(id == null || pwd == null || id.equals("") || pwd.equals("")|| name == null || tel == null || name.equals("") || tel.equals("")){
            return;
        }
        registerTask=new RegisterTask("http://70.12.114.134/bowling/registerMember.do");
        registerTask.execute(id.trim(),pwd.trim(),name.trim(),tel.trim());

    }

    //하단의 볼링장 찾기 버튼
    public void clickBt_map(View v){
        bt_map.setSelected(true);
       bt_game.setSelected(false);
       bt_dash.setSelected(false);
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
            myId=id;
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
            //Toast.makeText(MainActivity.this, "" + s, Toast.LENGTH_SHORT).show();
            if(s.trim().equals("1")){

                Toast.makeText(MapsActivity.this, "success", Toast.LENGTH_SHORT).show();
                first.setVisibility(View.INVISIBLE);
                second.setVisibility(View.VISIBLE);
                register_page.setVisibility(View.INVISIBLE);

            }else if(s.trim().equals("2")){
                myId="";
                Toast.makeText(MapsActivity.this, "fail", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MapsActivity.this, ""+s, Toast.LENGTH_SHORT).show();
            }
        }
    }
    // 회원가입 처리부분
    class RegisterTask extends AsyncTask<String, String, String>{

        String url;
        //constructor
        public RegisterTask(){

        }
        public RegisterTask(String url){
            this.url = url;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... strings) {
            String id = strings[0];
            String pwd = strings[1];
            String name = null;
            try {
                name = java.net.URLEncoder.encode(new String(strings[2].getBytes("UTF-8")));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String tel=strings[3];
            Log.d("------------1",name);

            //To JSP
            url += "?id="+id+"&pwd="+pwd+"&name="+name+"&tel="+tel;

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
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Accept","*/*");
                    Log.d("------------1-1",name);

                    if(con.getResponseCode()!=HttpURLConnection.HTTP_OK){
                        Log.d("getResponseCode","ERROR");

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
                Log.d("------------2",e.getMessage());

                return e.getMessage();

            }finally {
                con.disconnect();
            }
            return sb.toString();
            // return "";
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("------------3",s);
            //Toast.makeText(MainActivity.this, "" + s, Toast.LENGTH_SHORT).show();
            if(s.trim().equals("1")){
                first.setVisibility(View.VISIBLE);
                second.setVisibility(View.INVISIBLE);
                register_page.setVisibility(View.INVISIBLE);
                Toast.makeText(MapsActivity.this, "회원가입에 성공하였습니다", Toast.LENGTH_SHORT).show();
            }else if(s.trim().equals("2")){
                Toast.makeText(MapsActivity.this, "이미 사용중인 아이디 입니다", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MapsActivity.this, ""+s, Toast.LENGTH_SHORT).show();
            }
        }
    }
    //게임 참가 처리부분
    class GameTask extends AsyncTask<String, String, String>{

        String url;
        //constructor
        public GameTask(){

        }
        public GameTask(String url){
            this.url = url;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... strings) {
            String gameNum = strings[0];


            //To JSP
            url += "?game_num="+gameNum;

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
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Accept","*/*");


                    if(con.getResponseCode()!=HttpURLConnection.HTTP_OK){
                        Log.d("getResponseCode","ERROR");

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

            //Toast.makeText(MainActivity.this, "" + s, Toast.LENGTH_SHORT).show();
            if(s.trim().equals("2")){
                Toast.makeText(MapsActivity.this, "게임번호를 다시 확인해주세요", Toast.LENGTH_SHORT).show();

            }else{
                String temp[]=s.split("/");
                 g_num=temp[1];
                 g_title=temp[2];
                 g_master=temp[3];
                 g_date=temp[4];
                //Toast.makeText(MapsActivity.this, ""+g_num+" "+g_title+" "+g_master+" "+g_date, Toast.LENGTH_SHORT).show();
                tv_sum.setText("방번호:"+g_num+"/"+g_title+"/"+g_master);
            }
        }
    }
    //게임 개설부분
    class GameOpenTask extends AsyncTask<String, String, String>{

        String url;
        //constructor
        public GameOpenTask(){

        }
        public GameOpenTask(String url){
            this.url = url;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... strings) {
            String gameTitle = null;
            String gameMaster= strings[1];
            try {
                gameTitle = java.net.URLEncoder.encode(new String(strings[0].getBytes("UTF-8")));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            //To JSP
            url += "?title="+gameTitle+"&game_master="+gameMaster;

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
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Accept","*/*");


                    if(con.getResponseCode()!=HttpURLConnection.HTTP_OK){
                        Log.d("getResponseCode","ERROR");

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

            //Toast.makeText(MainActivity.this, "" + s, Toast.LENGTH_SHORT).show();
            if(s.trim().equals("2")){
                Toast.makeText(MapsActivity.this, "게임제목을 한글기준 15자이내로 설정해주세요", Toast.LENGTH_SHORT).show();

            }else{
                String temp[]=s.split("/");
                g_num=temp[1];
                g_title=temp[2];
                g_master=temp[3];
                g_date=temp[4].substring(0,temp[4].lastIndexOf(" ")+1);
                tv_sum.setText("방번호:"+g_num+"/"+g_title+"/"+g_master);

                //Toast.makeText(MapsActivity.this, ""+g_num+" "+g_title+" "+g_master+" "+g_date, Toast.LENGTH_SHORT).show();
            }
        }
    }
    //점수 입력 부분
    public void init(){

        for(int i = 0;i< 21;i++){
            frameScore[i] = findViewById(frameIDScoresPlayer1[i]);
            frameScore[i].setInputType(InputType.TYPE_NULL);
            frameScore[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedText = (EditText) v;
                }
            });
        }
        for(int i=0;i<10;i++){
            frameTotalScorePlayer1[i] = findViewById(frameIDTotalPlayer1[i]);
        }
    }

    public void calculateScore(View v){
        int spareCount = 0;
        int strikeCount = 0;
        int sum=0;
        frameScorePlayer1 = new int[21];
        for(int i=0;i<21;i++){
            String temp = frameScore[i].getText().toString();
            if(temp == null){
                frameScorePlayer1[i] = 0;
            }else if(temp.equals("/")){
                frameScorePlayer1[i] = 10 - frameScorePlayer1[i-1];
                countSpare++;
            }else if(temp.equals("X")){
                frameScorePlayer1[i] = 10;
                countStrike++;
            }else{
                frameScorePlayer1[i] = Integer.parseInt(temp);
            }
        }
        frameTotalPlayer1 = new int[10];
        //0-9 frame
        for(int i=0;i<9;i++){
            int num1 = frameScorePlayer1[2*i];
            int num2 = frameScorePlayer1[2*i+1];

            if(frameScore[2*i].getText().toString().equals("X") || frameScore[2*i+1].getText().toString().equals("X")){
                sum+=10+frameScorePlayer1[2*i+2]+frameScorePlayer1[2*i+3];
                if(frameScore[2*i+2].getText().toString().equals("X") || frameScore[2*i+3].getText().toString().equals("X")){
                    if(frameScore[2*i+4].getText().toString().equals("X") || frameScore[2*i+5].getText().toString().equals("X")){
                        sum+=10;
                    }
                    sum+=frameScorePlayer1[2*i+4];
                }
            }else if(frameScore[2*i+1].getText().toString().equals("/")){
                sum+=10+frameScorePlayer1[2*i+2];
            }else{
                sum+=num1+num2;
            }
            frameTotalPlayer1[i] = sum;
            frameTotalScorePlayer1[i].setText(""+sum);
        }
        //final frame
        int num1 = frameScorePlayer1[18];
        int num2 = frameScorePlayer1[19];
        int num3 = frameScorePlayer1[20];
        if(frameScore[18].getText().toString().equals("X")){
            sum+=20+num3;
        }else{
            if(frameScore[19].getText().toString().equals("/")){
                sum+=num1+num2+num3;
            }
            else{
                sum+=num1+num2;
            }
        }
        frameTotalPlayer1[9] = sum;
        frameTotalScorePlayer1[9].setText(""+sum);

    }

    public void inputScore(View v){
        Button button = (Button)v;
        if(selectedText == null){
            Toast.makeText(getApplicationContext(),"nothing",Toast.LENGTH_SHORT).show();
        }else{
            selectedText.setText(((Button)v).getText().toString());
        }
        //Log.d("text",""+button.getText().toString());
        //Log.d("View",selectedText.getText()+"");
    }

    //스코어 db에 입력
    class ScoreInputTask extends AsyncTask<Void,String, String>{

        String url;
        //constructor
        public ScoreInputTask(){

        }
        public ScoreInputTask(String url){
            this.url = url;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(Void ... voids) {



            //To JSP
            url += "?id="+myId+"&game_num="+g_num+
                    "&set1="+frameTotalPlayer1[0]+"&set2="+frameTotalPlayer1[1]+
                    "&set3="+frameTotalPlayer1[2]+"&set4="+frameTotalPlayer1[3]+
                    "&set5="+frameTotalPlayer1[4]+"&set6="+frameTotalPlayer1[5]+
                    "&set7="+frameTotalPlayer1[6] +"&set8="+frameTotalPlayer1[7]+
                    "&set9="+frameTotalPlayer1[8]+"&set10="+frameTotalPlayer1[9]+
                    "&strike="+countStrike+"&spare="+countSpare;

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
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Accept","*/*");


                    if(con.getResponseCode()!=HttpURLConnection.HTTP_OK){
                        Log.d("getResponseCode","ERROR");

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
            //Toast.makeText(MapsActivity.this, ""+url, Toast.LENGTH_SHORT).show();

            //Toast.makeText(MainActivity.this, "" + s, Toast.LENGTH_SHORT).show();
            if(s.trim().equals("2")){
                Toast.makeText(MapsActivity.this, "스코어 입력에 실패하였습니다", Toast.LENGTH_SHORT).show();

            }else{

                Toast.makeText(MapsActivity.this, "스코어 입력 성공", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
