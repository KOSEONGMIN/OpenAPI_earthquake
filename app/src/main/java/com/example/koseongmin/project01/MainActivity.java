package com.example.koseongmin.project01;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    boolean isDrawerOpend;
    TextView textView;
    ArrayList<String> lon;
    ArrayList<String> lat;
    Double latitude = 37.450770;
    Double longitude = 127.128844;
    Intent intent;
    Context con = this;
    String loginId;
    String loginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);

        getSupportActionBar().setTitle("");



        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        Intent earthquakeIntent = getIntent();
        if(earthquakeIntent.getBooleanExtra("isEarthquake", false)){
            vibrator.vibrate(5000);
        }


        checkPermissions();


        if (savedInstanceState == null) {
            //커스텀 타이틀을 사용하기 때문에 ActionBar()에 기본 타이틀을 표시하지 않도록 false로 지정
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            //DrawerLayout 참조
            drawer=(DrawerLayout)findViewById(R.id.main_drawer);

        /* ActionBarDrawerToggle 생성
           - 3, 4번째 매개변수:ActionBarDrawerToggle가 열린 상태, 닫힌 상태를 표현하기 위한 문자열(임의의 문자열 지정)
           - 하지만 화면 출력과 무관함으로 임의로 작성하면 됨(sting.xml)
        */
            toggle = new ActionBarDrawerToggle(this, drawer, R.string.drawer_open, R.string.drawer_close);

            //ActionBar(ToolBar)에서 기본 홈 버튼(<-)을 사용 가능하도록 설정
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //ActionBarDrawerToggle 의 상태를 sync(동기화)
            toggle.syncState();

            //NavigationView 참조
            NavigationView navigationView=(NavigationView)findViewById(R.id.main_drawer_view);

            //navigationView에 이벤트 설정
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int id=item.getItemId();

                   if(id==R.id.nav_join){    //회원가입
                        Intent joinIntent = new Intent(MainActivity.this, JoinActivity.class);
                        startActivity(joinIntent);
                    }else if(id==R.id.nav_login){       //로그인
                        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                       startActivity(loginIntent);
                    }else if(id==R.id.nav_shopping1){   //1번 쇼핑몰
                        Intent shoppingIntent = new Intent(MainActivity.this, ShoppingActivity.class);
                        startActivity(shoppingIntent);
                    }else if(id==R.id.nav_shopping2){   //2번 쇼핑몰
                        Intent shoppingIntent = new Intent(MainActivity.this, ShoppingActivity2.class);
                        startActivity(shoppingIntent);
                    }else if(id==R.id.nav_shopping3){   //3번 쇼핑몰
                        Intent shoppingIntent = new Intent(MainActivity.this, ShoppingActivity3.class);
                        startActivity(shoppingIntent);
                    }else if(id==R.id.kbs){         //kbs 뉴스
                        Intent kbsIntent = new Intent(MainActivity.this, NewsActivity.class);
                        startActivity(kbsIntent);
                    }else if(id==R.id.sbs){         //sbs뉴스
                        Intent sbsIntent = new Intent(MainActivity.this, NewsActivity2.class);
                        startActivity(sbsIntent);
                    }else if(id==R.id.mbc){         //mbc뉴스
                        Intent mbcIntent = new Intent(MainActivity.this, NewsActivity3.class);
                        startActivity(mbcIntent);
                    }else if(id==R.id.jtbc) {          //jtbc 뉴스
                       Intent jtbcIntent = new Intent(MainActivity.this, NewsActivity4.class);
                       startActivity(jtbcIntent);
                   }else if(id==R.id.family){       //가족 리스트
                       Intent familyIntent=new Intent(MainActivity.this,FamilyActivity.class);
                       startActivity(familyIntent);
                    }else if(id==R.id.nav_help){
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(con);
                        alertDialogBuilder.setTitle("도움말");
                        alertDialogBuilder
                                .setMessage("졍이 미니가 만들어쪙 뀨우><")
                                .setCancelable(false)
                                .setNegativeButton("닫기",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(
                                                    DialogInterface dialog, int id) {
                                                // 다이얼로그를 취소한다
                                                dialog.cancel();
                                            }
                                        });

                        // 다이얼로그 생성
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // 다이얼로그 보여주기
                        alertDialog.show();


                    }else if(id==R.id.nav_home){
                    }
                    return false;
                }
            });

        }


    }

    /* ActionBarDrawerToggle 아이콘 이벤트 처리
       - 내부적으로 ActionBarDrawerToogle 아이콘 클릭이 메뉴 이벤트로 처리되기 때문에
         onOptionsItemSelected() 메서드를 정의해야 NavigationDrawer가 열리거나 닫힘
       - onOptionsItemSelected() 메서드를 정의하지 않으면 토글 아이콘은 표시되지만
         열리거나 닫히지 않음
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 이벤트가 toggle에서 발생한 거라면 메뉴 이벤트 로직에서 벗어나게 처리
        if(toggle.onOptionsItemSelected(item)){
            return false;
        }
        return super.onOptionsItemSelected(item);
    }


    public void onButtonClick(View view) {
        findLocationMakeIntent();
        startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();
        findLocationMakeIntent();
    }

    public void onGPSClick(View view) {
        findLocationMakeIntent();
        Toast.makeText(getApplicationContext(),"사용자 위치 재탐색", Toast.LENGTH_LONG).show();
    }

    public void findLocationMakeIntent() {
        findMyLocation();
        intent = new Intent(this, MapActivity.class);
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
    }

    //사용자 권한 확인 메서드
    private void checkPermissions() {
        String[] permissions = {
                Manifest.permission.ACCESS_FINE_LOCATION,//GPS 이용권한
                Manifest.permission.ACCESS_COARSE_LOCATION,//네트워크/Wifi 이용 권한
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.VIBRATE
        };

        //권한을 가지고 있는지 체크
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
              //  Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
            } else {
               // Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                 //   Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
                } else {
                    ActivityCompat.requestPermissions(this, permissions, 1);
                }
            }
        }


    }

    public void findMyLocation() {

        // LocationManager 객체 생성 (LOCATION_SERVICE 사용)
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // GPSListener 객체 생성 (LocationListener 인터페이스 정의 필요)
        GPSListener gpsListener = new GPSListener();
        long minTime = 10000;
        float minDistance = 0;

        try {
            // GPS를 이용한 위치 요청
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime,
                    minDistance,
                    gpsListener);

            // 네트워크를 이용한 위치 요청
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    minTime,
                    minDistance,
                    gpsListener);

            // 위치요청을 한 상태에서 위치추적되는 동안 먼저 최근 위치를 조회해서 set
            Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastLocation != null) {
                latitude = lastLocation.getLatitude();
                longitude = lastLocation.getLongitude();
            }
        } catch(SecurityException ex) {
            ex.printStackTrace();
        }


    }

    public class GPSListener implements LocationListener {

        // LocationManager 에서 위치정보가 변경되면 호출
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();

        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }

}

