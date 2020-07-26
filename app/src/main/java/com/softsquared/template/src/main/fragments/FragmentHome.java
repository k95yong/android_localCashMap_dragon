package com.softsquared.template.src.main.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.LocationServices;
import com.softsquared.template.R;
import com.softsquared.template.src.BaseFragment;
import com.softsquared.template.src.main.MainService;
import com.softsquared.template.src.main.PreferenceManager;
import com.softsquared.template.src.main.activities.CategoryActivity;
import com.softsquared.template.src.main.activities.MainNavigationActivity;
import com.softsquared.template.src.main.activities.PlaceInfoActivity;
import com.softsquared.template.src.main.activities.SearchedListActivity;
import com.softsquared.template.src.main.adapters.CatListRecyclerAdapter;
import com.softsquared.template.src.main.dialogs.PopDialog;
import com.softsquared.template.src.main.interfaces.MainActivityView;
import com.softsquared.template.src.main.interfaces.OnBackPressedListener;
import com.softsquared.template.src.main.models.CategorySearchResponse;
import com.softsquared.template.src.main.models.EventContentResponse;
import com.softsquared.template.src.main.models.EventResponse;
import com.softsquared.template.src.main.models.NoticeContentResponse;
import com.softsquared.template.src.main.models.NoticeResponse;
import com.softsquared.template.src.main.models.SearchResult;
import com.softsquared.template.src.main.models.StoreSearchResponse;

import net.daum.mf.map.api.MapCircle;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static android.view.View.INVISIBLE;
import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.kakao.util.maps.helper.Utility.getPackageInfo;

public class FragmentHome extends BaseFragment implements MainActivityView, MapView.MapViewEventListener, OnBackPressedListener {
    final int RADIUS_CAT = 500;
    final int RADIUS_STORE = 3000;
    long backKeyPressedTime;

    public FragmentHome() {
    }

    MapCircle mapCircle;
    Context mContext;
    ImageButton mIbtn_search, btnCurrentPositionInfo, mIbtn_zoom_in, mIbtn_zoom_out, mIbtn_more_category;
    EditText mEt_search_key;
    static TextView txtCurrentPositionInfo;
    LocationListener mLocationListener;
    LinearLayout mLl_show_list, mLl_research;
    boolean firstFlag, research_flag;
    int searchFlag;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int PERMISSIONS_REQUEST_ACCESS_CALL_PHONE = 2;
    MapPOIItem current_location = new MapPOIItem();
    ArrayList<MapPOIItem> cur_markers = new ArrayList<>();
    public double mLongitude;
    public double mLatitude;
    public double altitude;
    public float accuracy;
    public String provider;
    public LocationManager lm;
    public MapView mapView;
    public static ViewGroup mapViewContainer;
    public ArrayList<SearchResult> mCatList = new ArrayList<>();
    public ArrayList<SearchResult> mCatList2 = new ArrayList<>();
    public ArrayList<SearchResult> mStoreList = new ArrayList<>();
    public ArrayList<SearchResult> mStoreList2 = new ArrayList<>();
    public String cur_cat_name = "", cur_store_name = "";
    MainNavigationActivity mainNavigationActivity;
    PopDialog popDialog;
    RecyclerView mRv_cat_list;

    // acitivyt result code
    private int mIbtn_more_category_request = 1000;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        Log.e("keyHash",getKeyHash(mContext));
        mainNavigationActivity = (MainNavigationActivity) getActivity();
        mainNavigationActivity.setOnBackPressedListener(this);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        searchFlag = -1;
        research_flag = false;
        firstFlag = true;
        getLocationPermission();
        if (firstFlag) {
            showProgressDialog();
        }
        mLocationListener = new LocationListener() {

            public void onLocationChanged(Location location) {
                getLocation();
                mLongitude = location.getLongitude();
                mLatitude = location.getLatitude();
                altitude = location.getAltitude();
                accuracy = location.getAccuracy();
                provider = location.getProvider();
                getCompleteAddressString(mContext, mLatitude, mLongitude);

                if (firstFlag == true) {
                    setDaumMapCurrentLocation(mLatitude, mLongitude);
                    firstFlag = false;
                }
                //lm.removeUpdates(mLocationListener);  //  미수신할때는 반드시 자원해체를 해주어야 한다.
            }

            public void onProviderDisabled(String provider) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }
        };

        mIbtn_search = rootView.findViewById(R.id.ibtn_search);
        mIbtn_zoom_in = rootView.findViewById(R.id.ibtn_zoom_in);
        mIbtn_zoom_out = rootView.findViewById(R.id.ibtn_zoom_out);
        mIbtn_more_category = rootView.findViewById(R.id.ibtn_more_category);
        mEt_search_key = rootView.findViewById(R.id.et_search_key);
        mLl_show_list = rootView.findViewById(R.id.ll_show_list);
        if((mCatList == null || mCatList.size() == 0) && (mStoreList==null || mStoreList.size() == 0)){
            mLl_show_list.setVisibility(INVISIBLE);
        }
        mLl_research = rootView.findViewById(R.id.ll_research);
        mLl_research.setVisibility(INVISIBLE);
        mRv_cat_list = rootView.findViewById(R.id.rv_cat_list);
        mapViewContainer = rootView.findViewById(R.id.map_view);
        txtCurrentPositionInfo = rootView.findViewById(R.id.tv_cur_location);
        txtCurrentPositionInfo.setSelected(true);
        btnCurrentPositionInfo = rootView.findViewById(R.id.ibtn_cur_location);

        //set location
        lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        //initSet
        LocationServices.getFusedLocationProviderClient(mContext);

        if (PreferenceManager.getBoolean(mContext, "pop_up_flag")) {
            popDialog = new PopDialog(mContext);
            popDialog.show();
        }

        mLl_research.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double screen_lat = mapView.getMapCenterPoint().getMapPointGeoCoord().latitude;
                double screen_lon = mapView.getMapCenterPoint().getMapPointGeoCoord().longitude;
                if (searchFlag == 1) {
                    getStoreSearch(screen_lat, screen_lon, cur_store_name);
                } else {
                    getCategorySearch(screen_lat, screen_lon, cur_cat_name);
                }
            }
        });
        mLl_show_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SearchedListActivity.class);

                intent.putExtra("flag", searchFlag);
                intent.putExtra("cat", "검색 결과");

                if (mCatList != null && searchFlag == 0) {
                    intent.putExtra("searched_list", mCatList);
                    intent.putExtra("searched_list2", mCatList2);
                } else if (mStoreList != null && searchFlag == 1) {
                    intent.putExtra("searched_list", mStoreList);
                    intent.putExtra("searched_list2", mStoreList2);
                }
                startActivity(intent);
            }
        });

        btnCurrentPositionInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
                getCompleteAddressString(mContext, mLatitude, mLongitude);
                mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(mLatitude, mLongitude), true);
            }
        });

        mIbtn_zoom_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapView.zoomIn(true);
            }
        });

        mIbtn_zoom_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapView.zoomOut(true);
            }
        });

        mIbtn_more_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CategoryActivity.class);
                startActivityForResult(intent, mIbtn_more_category_request);
            }
        });

        mEt_search_key.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && keyCode == KeyEvent.KEYCODE_ENTER) {
                    searchFlag = 1;
                    Intent intent = new Intent(mContext, SearchedListActivity.class);
                    String store_name = mEt_search_key.getText().toString();
                    cur_store_name = store_name;
                    //mLl_show_list.setVisibility(View.VISIBLE);
                    mapView.removePOIItems(cur_markers.toArray(new MapPOIItem[cur_markers.size()]));
                    cur_markers = new ArrayList<>();
                    getStoreSearch(mLatitude, mLongitude, cur_store_name);
                    return true;
                }
                return false;
            }
        });
        mIbtn_search.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchFlag = 1;
                Intent intent = new Intent(mContext, SearchedListActivity.class);
                String store_name = mEt_search_key.getText().toString();
                cur_store_name = store_name;
                //mLl_show_list.setVisibility(View.VISIBLE);
                mapView.removePOIItems(cur_markers.toArray(new MapPOIItem[cur_markers.size()]));
                cur_markers = new ArrayList<>();
                getStoreSearch(mLatitude, mLongitude, cur_store_name);
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        mRv_cat_list.setLayoutManager(manager);
        mRv_cat_list.setAdapter(new CatListRecyclerAdapter(mContext, this));
        return rootView;
    }

    public void sendCategoryName(String text) {
        research_flag = true;
        cur_cat_name = text;
        double screen_lat = mapView.getMapCenterPoint().getMapPointGeoCoord().latitude;
        double screen_lon = mapView.getMapCenterPoint().getMapPointGeoCoord().longitude;
        getCategorySearch(screen_lat, screen_lon, text);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (PlaceInfoActivity.mapViewContainer != null) {
            PlaceInfoActivity.mapViewContainer.removeAllViews();
        }

        if (mapView == null) {
            mapView = new MapView(mContext);
            mapViewContainer.addView(mapView);
            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(mLatitude, mLongitude), true);
            addExistMarker();
        } else {
            if (mapViewContainer.getChildCount() == 0) {
                mapView = new MapView(mContext);
                mapViewContainer.addView(mapView);
                addExistMarker();
            }
        }
        mapView.setMapViewEventListener(this);
        mapView.setPOIItemEventListener(piel);
        setDaumMapCurrentMarker_my_location(mLatitude, mLongitude, "현재위치");
        getCompleteAddressString(mContext, mLatitude, mLongitude);
        getLocation();
        getCompleteAddressString(mContext, mLatitude, mLongitude);
    }

    public void addExistMarker() {
        for (int i = 0; i < cur_markers.size(); i++) {
            mapView.addPOIItem(cur_markers.get(i));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == mIbtn_more_category_request) {
                String cur_cat = data.getStringExtra("cat");
                cur_cat_name = cur_cat;
                getCategorySearch(mLatitude, mLongitude, cur_cat);
                if (mCatList != null) {
                    searchFlag = 0;
                    mapView.removePOIItems(cur_markers.toArray(new MapPOIItem[cur_markers.size()]));
                    cur_markers = new ArrayList<>();
                    mLl_show_list.setVisibility(View.VISIBLE);
                    research_flag = true;
                    makeCatMarker(mCatList);
                }
            }
        }
    }

    public void getLocation() {
        try {
            //getLocationPermission();
            //txtCurrentPositionInfo.setText("수신중..");
            // GPS 제공자의 정보가 바뀌면 콜백하도록 리스너 등록하기~!!!
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, // 등록할 위치제공자
                    1000, // 통지사이의 최소 시간간격 (miliSecond)
                    5, // 통지사이의 최소 변경거리 (m)
                    mLocationListener);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자
                    1000, // 통지 시간 간격
                    5, // 통지 거리 간격
                    mLocationListener);
            //txtCurrentPositionInfo.setText("위치정보 미수신중");
            //lm.removeUpdates(mLocationListener);  //  미수신할때는 반드시 자원해체를 해주어야 한다.
        } catch (SecurityException ex) {
            Log.e("SecurityException : ", ex + "");
        }
    }

    public void getCompleteAddressString(Context context, double LATITUDE, double LONGITUDE) {

        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(LATITUDE, LONGITUDE);
        MapReverseGeoCoder.ReverseGeoCodingResultListener reverseGeoCodingResultListener = new MapReverseGeoCoder.ReverseGeoCodingResultListener() {
            @Override
            public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder mapReverseGeoCoder, String s) {
                txtCurrentPositionInfo.setText(s);
            }

            @Override
            public void onReverseGeoCoderFailedToFindAddress(MapReverseGeoCoder mapReverseGeoCoder) {
            }
        };
        MapReverseGeoCoder reverseGeoCoder = new MapReverseGeoCoder("82967c534ccf6d551c69d7a9a2d5ecea", mapPoint, reverseGeoCodingResultListener, (Activity) context);
        reverseGeoCoder.startFindingAddress();

        return;
    }

    public void setDaumMapCurrentLocation(double latitude, double longitude) {
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true);
        mapView.setZoomLevel(4, true);
        mapView.zoomIn(true);
        setDaumMapCurrentMarker_my_location(mLatitude, mLongitude, "현재 위치");
    }

    public void setDaumMapCurrentMarker_my_location(double lat, double lon, String name) {
        mapView.removePOIItem(current_location);
        current_location = new MapPOIItem();
        current_location.setItemName(name);
        current_location.setTag(1);
        current_location.setMapPoint(MapPoint.mapPointWithGeoCoord(lat, lon));
        current_location.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        current_location.setCustomImageResourceId(R.drawable.my_loc_marker);
        current_location.setCustomImageAutoscale(false);
        current_location.setCustomImageAnchor(0.5f, 1.0f);
        mapView.addPOIItem(current_location);
    }

    public void setDaumMapCurrentMarker(double lat, double lon, String name) {
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName(name);
        marker.setTag(0);
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(lat, lon));
        marker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        marker.setCustomImageResourceId(R.drawable.marker_2);
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.CustomImage);
        marker.setCustomSelectedImageResourceId(R.drawable.marker_3);
        mapView.addPOIItem(marker);
        cur_markers.add(marker);
    }

    @Override
    public void validateSuccess(String text) {
    }

    @Override
    public void validateFailure(String message) {
    }

    @Override
    public void getStoreSearchResult(StoreSearchResponse res) {
        Toast.makeText(getActivity(), res.getMessage() + "", Toast.LENGTH_LONG).show();
        searchFlag = 1;
        research_flag = true;
        if (res.getCode() == 100) {
            mLl_show_list.setVisibility(View.VISIBLE);
            ArrayList<SearchResult> result = res.getStoreSearchResult();
            ArrayList<SearchResult> result2 = res.getStoreSearchResult2();
            mStoreList = new ArrayList<>();
            mStoreList = result;
            mStoreList2 = new ArrayList<>();
            mStoreList2 = result2;
            mapView.removePOIItems(cur_markers.toArray(new MapPOIItem[cur_markers.size()]));
            cur_markers = new ArrayList<>();

            for (int i = 0; i < result.size(); i++) {
                double cur_lat = result.get(i).getLatitude();
                double cur_lon = result.get(i).getLongitude();
                String cur_name = result.get(i).getMutual_name();
                setDaumMapCurrentMarker(cur_lat, cur_lon, cur_name);
            }
        } else if (res.getCode() == 205) {
            mStoreList.clear();
            mStoreList = new ArrayList<>();
            mapView.removePOIItems(cur_markers.toArray(new MapPOIItem[cur_markers.size()]));
        }
    }

    @Override
    public void getNotice(NoticeResponse res) {
    }

    @Override
    public void getCategorySearchResult(CategorySearchResponse res) {
        Toast.makeText(getActivity(), res.getMessage() + "", Toast.LENGTH_LONG).show();
        research_flag = true;
        searchFlag = 0;
        if (res.getCode() == 100) {
            ArrayList<SearchResult> results = res.getResults();
            ArrayList<SearchResult> results2 = res.getResults2();
            mCatList.clear();
            mCatList2.clear();
            mCatList = results;
            mCatList2 = results2;
            mapView.removePOIItems(cur_markers.toArray(new MapPOIItem[cur_markers.size()]));
            cur_markers = new ArrayList<>();
            mLl_show_list.setVisibility(View.VISIBLE);
            for (int i = 0; i < results.size(); i++) {
                double cur_lat = results.get(i).getLatitude();
                double cur_lon = results.get(i).getLongitude();
                String cur_name = results.get(i).getMutual_name();
                setDaumMapCurrentMarker(cur_lat, cur_lon, cur_name);
            }
        } else if (res.getCode() == 205) {
            mCatList.clear();
            mCatList2.clear();
            mCatList = new ArrayList<>();
            mapView.removePOIItems(cur_markers.toArray(new MapPOIItem[cur_markers.size()]));
        }
    }

    @Override
    public void getEvent(EventResponse res) {
    }

    @Override
    public void getNoticeContent(NoticeContentResponse res) {
    }

    @Override
    public void getEventContent(EventContentResponse res) {
    }

    public void makeCatMarker(ArrayList<SearchResult> list) {
        for (int i = 0; i < list.size(); i++) {
            double cur_lat = list.get(i).getLatitude();
            double cur_lon = list.get(i).getLongitude();
            String cur_name = list.get(i).getMutual_name();
            setDaumMapCurrentMarker(cur_lat, cur_lon, cur_name);
        }
    }


    public void getStoreSearch(double lat, double lon, String name) {
        makeCircle(lat, lon, RADIUS_STORE);
        mLl_research.setVisibility(View.INVISIBLE);
        MainService mainService = new MainService(this);
        mainService.getStoreSearch(lat, lon, name);
    }

    public void getCategorySearch(double lat, double lon, String category) {
        makeCircle(lat, lon, RADIUS_CAT);
        mLl_research.setVisibility(View.INVISIBLE);
        MainService mainService = new MainService(this);
        mainService.getCategorySearch(lat, lon, category);
    }

    @Override
    public void onMapViewInitialized(MapView mapView) {
        Log.e("Map상태", "onMapViewInitialized");
        //hideProgressDialog();
    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {
        if (research_flag) {
            mLl_research.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {
    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {
    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {
    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {
    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {
    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {
    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {
        Log.e("Map상태", "onMapViewMoveFinished");
        hideProgressDialog();
        getLocation();
    }

    MapView.POIItemEventListener piel = new MapView.POIItemEventListener() {
        @Override
        public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
        }

        @Override
        public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
            MapPoint mapPoint = mapPOIItem.getMapPoint();
            double cur_sel_lat = mapPoint.getMapPointGeoCoord().latitude;
            double cur_sel_lon = mapPoint.getMapPointGeoCoord().longitude;
            String cur_name = mapPOIItem.getItemName();
            if (searchFlag == 1) {
                for (int i = 0; i < mStoreList.size(); i++) {
                    SearchResult cur = mStoreList.get(i);
                    if (cur.getMutual_name() == cur_name) {
                        Intent intent = new Intent(getActivity(), PlaceInfoActivity.class);
                        intent.putExtra("title", cur.getMutual_name());
                        intent.putExtra("address", cur.getAddress());
                        intent.putExtra("tel", cur.getPhone_number());
                        intent.putExtra("lat", cur.getLatitude());
                        intent.putExtra("lon", cur.getLongitude());
                        mapViewContainer.removeAllViews();
                        startActivity(intent);
                        break;
                    }
                }
            } else {
                for (int i = 0; i < mCatList.size(); i++) {
                    SearchResult cur = mCatList.get(i);
                    if (cur.getMutual_name() == cur_name) {
                        Intent intent = new Intent(getActivity(), PlaceInfoActivity.class);
                        intent.putExtra("title", cur.getMutual_name());
                        intent.putExtra("address", cur.getAddress());
                        intent.putExtra("tel", cur.getPhone_number());
                        intent.putExtra("lat", cur.getLatitude());
                        intent.putExtra("lon", cur.getLongitude());
                        mapViewContainer.removeAllViews();
                        startActivity(intent);
                        break;
                    }
                }
            }
        }

        @Override
        public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {
        }

        @Override
        public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {
        }
    };

    void makeCircle(double lat, double lon, int radius) {
        mapView.removeAllCircles();
        mapCircle = new MapCircle(MapPoint.mapPointWithGeoCoord(lat, lon), // center
                radius, // radius
                Color.TRANSPARENT,
                Color.parseColor("#50c4c2ff"));
        mapView.addCircle(mapCircle);
    }

    @Override
    public void onBackPressed() {
        Log.e("onBack", "눌림");
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showCustomToast("한번 더 누르면 종료됩니다.");
            return;
        } else {
            getActivity().finish();
        }
    }

    public void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        }
    }
    public static String getKeyHash(final Context context) {
        PackageInfo packageInfo = getPackageInfo(context, PackageManager.GET_SIGNATURES);
        if (packageInfo == null)
            return null;

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                return android.util.Base64.encodeToString(md.digest(), android.util.Base64.NO_WRAP);
            } catch (NoSuchAlgorithmException e) {
                Log.w(TAG, "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
        return null;
    }
}
