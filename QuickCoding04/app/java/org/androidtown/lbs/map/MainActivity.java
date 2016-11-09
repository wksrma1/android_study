package org.androidtown.lbs.map;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;


/**
 * 현재 위치의 지도를 보여주는 방법에 대해 알 수 있습니다.
 *
 * Google Play Services 라이브러리를 링크하여 사용합니다.
 * 구글맵 v2를 사용하기 위한 여러 가지 권한이 있어야 합니다.
 * 매니페스트 파일 안에 있는 키 값을 PC에 맞는 것으로 새로 발급받아서 넣어야 합니다.
 *
 * @author Mike
 */
public class MainActivity extends ActionBarActivity {

    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // XML 레이아웃에 정의한 지도 객체 참조
        map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

        // 위치 확인하여 위치 표시 시작
        startLocationService();
    }
    public void onResume(){
        super.onResume();
        map.setMyLocationEnabled(true);
    }
    //엑티비티가 화면에 보일때 내 위치 표시 활성화
    public void onPause(){
        super.onPause();
        map.setMyLocationEnabled(false);
    }
    //엑티비티가 중지될떄 내 위치 비활성화

    /**
     * 현재 위치 확인을 위해 정의한 메소드
     */
    private void startLocationService() {
        // LocationManager은 시스템 서비스로 제공되므로 getSystemService()를 이용하여 위치 관리자 객체 참조
        //Context.LOCATION_SERVICE는 위치관리자를 위한 서비스의 이름이다
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // gps 리스너 객체 생성
        GPSListener gpsListener = new GPSListener();
        long minTime = 10000;
        //최소시간 설정 10분으로 하려면 600000으로 설정한다
        float minDistance = 0;
        //최소거리 설정

        // GPS 기반 위치 요청
        manager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                minTime,
                minDistance,
                gpsListener);

        // 네트워크 기반 위치 요청
        manager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                minTime,
                minDistance,
                gpsListener);

        Toast.makeText(getApplicationContext(), "위치 확인 시작함. 로그를 확인하세요.", Toast.LENGTH_SHORT).show();
    }

    /**
     * 리스너 정의
     */
    private class GPSListener implements LocationListener {
        /**
         * 위치 정보가 확인되었을 때 호출되는 메소드
         */

        //made by yoo ji eun
        private LocationManagement locationManagement = new LocationManagement();

        public void onLocationChanged(Location location) {
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();

            String msg = "Latitude : "+ latitude + "\nLongitude:"+ longitude;
            Log.i("GPSLocationService", msg);

            //위도와 경도 좌표 확인
            Toast.makeText(getApplicationContext(), msg,Toast.LENGTH_SHORT).show();
            // 현재 위치의 지도를 보여주기 위해 정의한 메소드 호출
            showCurrentLocation(latitude, longitude);


            //링크드리스트에 위도 적도 추가
            locationManagement.AddLocationList(latitude, longitude);


        }



        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

    }

    /**
     * 현재 위치의 지도를 보여주기 위해 정의한 메소드
     *
     * @param latitude
     * @param longitude
     */
    private void showCurrentLocation(Double latitude, Double longitude) {
        // showCurrentLocation()을 이용해 들어온 위도 경도의 값으로 LatLng객체를 만들면 지도 위에 표시 될 수 있는 새로운 포인트로 바뀌게 됩니다.
        LatLng curPoint = new LatLng(latitude, longitude);

        //마커를 찍기위해 마커객체를 생성한다
        //위치는 현재위치
       // Marker melbourne = map.addMarker(new MarkerOptions()
        //        .position(curPoint));

        //동그라미 모양으로 찍기 위해서 Circle로 객체 생성한다
        Circle circle = map.addCircle(new CircleOptions()
                .center(new LatLng(latitude, longitude))
                .radius(3)
                .strokeColor(Color.RED)
                .fillColor(Color.BLUE)
                );


        //17의 값은 축척의 정도를 나타내는 것으로 17로 했을시 건물의 위치까지 볼 수 있다.
        //19 또는 21이 최대 축척 값이다
        //animateCamera는 LatLng 객체를 중심으로 지도를 보여준다
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 17));


        // 지도 유형 설정. 지형도인 경우에는 GoogleMap.MAP_TYPE_TERRAIN, 위성 지도인 경우에는 GoogleMap.MAP_TYPE_SATELLITE
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}