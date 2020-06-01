package com.softsquared.template.src.main.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.softsquared.template.R;
import com.softsquared.template.src.BaseActivity;
import com.softsquared.template.src.main.PreferenceManager;
import com.softsquared.template.src.main.fragments.FragmentHome;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;

// 권한 체크하는 부분 수정 필요!!!
public class PlaceInfoActivity extends BaseActivity {
    ImageButton mIbtn_back_to_main, mIbtn_regitst_my_place;
    LinearLayout mLl_call_to_place;
    public static ViewGroup mapViewContainer;
    TextView mTv_place_phone_nunmber, mTv_title, mTv_address;
    Activity thisActivity = (Activity) this;
    MapView mapView;
    String title, address, tel;
    double lat, lon;
    final int MY_PERMISSIONS_REQUEST_CALL = 1;
    Place place;
    ArrayList<Place> bookMarkList;
    int pos_checker;
    Context mContext;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_info);
        Intent intent = getIntent();
        mIbtn_back_to_main = findViewById(R.id.ibtn_back_to_main);
        mLl_call_to_place = findViewById(R.id.ll_call_to_place);
        mTv_title = findViewById(R.id.tv_place_title);
        mTv_address = findViewById(R.id.tv_place_address);
        mTv_place_phone_nunmber = findViewById(R.id.tv_place_phone_number);
        mIbtn_regitst_my_place = findViewById(R.id.ibtn_regitst_my_place);
        title = intent.getExtras().getString("title");
        address = intent.getExtras().getString("address");
        tel = intent.getExtras().getString("tel");
        mTv_title.setText(title);
        mTv_address.setText(address);
        mTv_place_phone_nunmber.setText(tel);
        lat = intent.getExtras().getDouble("lat");
        lon = intent.getExtras().getDouble("lon");
        mapView = new MapView(this);
        place = new Place(title, address, tel, lat, lon);
        mapViewContainer = findViewById(R.id.ll_store_location);
        mapViewContainer.addView(mapView);
        mContext = this;
        setDaumMapCurrentLocation(lat, lon);

        bookMarkList = PreferenceManager.getBookMarkList(getApplicationContext());
        pos_checker = checker(bookMarkList, place);
        if(pos_checker == -1){
            mIbtn_regitst_my_place.setImageResource(R.drawable.ic_heart);
        }else{
            mIbtn_regitst_my_place.setImageResource(R.drawable.heart_registered);
        }

        mIbtn_back_to_main.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mLl_call_to_place.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(thisActivity, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL);
                }
                String tel = "tel:" + mTv_place_phone_nunmber.getText().toString();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(tel));
                // 권한 체크 필요함.
                startActivity(intent);
            }
        });

        mIbtn_regitst_my_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos_checker = checker(bookMarkList, place);
                if(pos_checker == -1){
                    showCustomToast("즐겨찾기에 추가 되었습니다.");
                    mIbtn_regitst_my_place.setImageResource(R.drawable.heart_registered);
                    bookMarkList.add(place);

                    PreferenceManager.saveBookMarkList(getApplicationContext(), bookMarkList);
                }
                else {
                    showCustomToast("즐겨찾기 해제 되었습니다..");
                    mIbtn_regitst_my_place.setImageResource(R.drawable.ic_heart);
                    bookMarkList.remove(pos_checker);

                    PreferenceManager.saveBookMarkList(getApplicationContext(), bookMarkList);
                }
            }
        });
    }

    int checker(ArrayList<Place> bookMarkList, Place cur){
        for(int i=0;i<bookMarkList.size();i++){
            double curLat = bookMarkList.get(i).getLat();
            double curLon = bookMarkList.get(i).getLon();
            if(curLat == cur.getLat() && curLon == cur.getLon()){
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("onDestroy", "Destroyed");
        mapViewContainer.removeAllViews();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("onStop", "onStop");
    }

    public void setDaumMapCurrentLocation(double latitude, double longitude) {
        // 중심점 변경
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true);
        // 줌 레벨 변경
        mapView.setZoomLevel(4, true);
        // 줌 인
        mapView.zoomIn(true);
        setDaumMapCurrentMarker_my_location(latitude, longitude, title);
    }
    public void setDaumMapCurrentMarker_my_location(double lat, double lon, String name) {
        MapPOIItem current_location;
        current_location = new MapPOIItem();
        current_location.setItemName(name);
        current_location.setTag(1);
        current_location.setMapPoint(MapPoint.mapPointWithGeoCoord(lat, lon));
        current_location.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        current_location.setCustomImageResourceId(R.drawable.marker_3);
        current_location.setCustomImageAutoscale(false);
        current_location.setCustomImageAnchor(0.5f, 1.0f);

//        current_location.setCustomImageAutoscale(false);
//        current_location.setCustomImageAnchor(0.5f, 0.5f);
//        current_location.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
//        current_location.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

        mapView.addPOIItem(current_location);
    }
}
