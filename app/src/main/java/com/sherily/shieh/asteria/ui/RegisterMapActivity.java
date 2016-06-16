package com.sherily.shieh.asteria.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.sherily.shieh.asteria.R;
import com.sherily.shieh.asteria.baidumaputils.GeoCoderHelper;
import com.sherily.shieh.asteria.baidumaputils.LocationHelper;
import com.sherily.shieh.asteria.baidumaputils.LocationListener;
import com.sherily.shieh.asteria.baidumaputils.MyLocation;
import com.sherily.shieh.asteria.baidumaputils.PoiSearchHelper;
import com.sherily.shieh.asteria.engine.SharePrefHelper;
import com.sherily.shieh.asteria.event.LocationEvent;
import com.sherily.shieh.asteria.model.AddressModel;
import com.sherily.shieh.asteria.ui.adapter.DividerItemDecoration;
import com.sherily.shieh.asteria.ui.adapter.RegisterAddressRecyclerviewAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.http.PUT;

public class RegisterMapActivity extends BaseActivity {


    private static final String TAG = "RegisterMapActivity" ;
    private static final int REQUEST_PERMISSION_LOCATION = 1;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.topPanel)
    RelativeLayout topPanel;

    @Bind(R.id.mapView)
    TextureMapView mapView;
    @Bind(R.id.center_pointer)
    Space centerPointer;
    @Bind(R.id.location)
    ImageView location;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;


    //百度地图对象
    private BaiduMap mBaiduMap;

    private MyLocation myLocation;

    private GeoCoderHelper geoCoderHelper;
    private PoiSearchHelper poiSearchHelper;
    private double latitude;
    private double longitude;
//    private String[] adr1;
//    private String[] adr2;
    private BDLocation mlocation;
    private ArrayList<PoiInfo> list;
    private ArrayList<ReverseGeoCodeResult> list2;
    private LatLng mSelfLocation;

    private RegisterAddressRecyclerviewAdapter adapter;
    private com.sherily.shieh.asteria.baidumap.LocationHelper mLocationHelper;

    private List<AddressModel> mapAddressDataList = new ArrayList<>();
    private String selectedLatitude;
    private String selectedLongitude;
    private Boolean isFirstShow = true;




    private BaiduMap.OnMapStatusChangeListener mOnMapStatusChangeListener = new BaiduMap.OnMapStatusChangeListener() {
        @Override
        public void onMapStatusChangeStart(MapStatus mapStatus) {

        }

        @Override
        public void onMapStatusChange(MapStatus mapStatus) {

        }

        @Override
        public void onMapStatusChangeFinish(MapStatus mapStatus) {
            LatLng latLng = mapStatus.target;//地图中心点
            geoCoderHelper.setLatLng(latLng);
            geoCoderHelper.reverseGeoCode();
            //poiSearchHelper.setLatLng(latLng);
            //poiSearchHelper.searchNearby();

        }
    };


    /*
    * 添加当前位置图标
    */
    private void addLocationMarker(LatLng latLng) {
//        if (latLng == null)
//            return;
        BitmapDescriptor descriptor = BitmapDescriptorFactory.fromResource(R.mipmap.map_location_self);
        OverlayOptions option = new MarkerOptions()
                .position(latLng)
                .icon(descriptor);
        mBaiduMap.addOverlay(option);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_map);
        ButterKnife.bind(this);

        Log.d("oncreate","oncreate");

        list = new ArrayList<PoiInfo>();
        list2 = new ArrayList<ReverseGeoCodeResult>();
        geoCoderHelper = new GeoCoderHelper();
//        poiSearchHelper = new PoiSearchHelper();


        //请求必要的权限：位置信息，读取电话状态（获取deviceId必须）,存储空间（下载更新文件，保存图片必须）
        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_LOCATION);
            return;
        }

        //setData();
        //地图相关初始化
        mapView.showZoomControls(false);
        mBaiduMap = mapView.getMap();
        //隐藏baiduMap logo
        View logo = mapView.getChildAt(1);
        logo.setVisibility(View.INVISIBLE);
        //设置地图缩放级别16 类型普通地图
        final MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(16.0f);
        mBaiduMap.setMapStatus(msu);
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

//        // 开启定位图层
//        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setOnMapStatusChangeListener(mOnMapStatusChangeListener);

        //定位初始化
        //注意: 实例化定位服务 LocationClient类必须在主线程中声明 并注册定位监听接口
//        myLocation = MyLocation.getMyLocation(mBaiduMap, getApplicationContext(),latitude, longitude);
//        myLocation.setLocationOption();
//        myLocation.start();
//        LocationHelper.sharedInstance(this).setOnLocationChangedListener(new LocationListener.OnLocationChangedListener() {
//            @Override
//            public void onChanged(BDLocation bdLocation) {
//                onEventMainThread(bdLocation);
//            }
//        });
//        LocationHelper.sharedInstance(this).start();
//        selectedLatitude = SharePrefHelper.getInstance(this).getString("Selected_Latitude","");
//        selectedLongitude = SharePrefHelper.getInstance(this).getString("Selected_Longitude","");
//        if (!TextUtils.isEmpty(selectedLatitude) && !TextUtils.isEmpty(selectedLongitude)) {
//
//            mSelfLocation = new LatLng(Double.parseDouble(selectedLatitude), Double.parseDouble(selectedLongitude));
////            MapStatus mapStatus = new MapStatus.Builder()
////                    .target(mSelfLocation)
////                    .zoom(15)
////                    .build();
////            MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(mapStatus);
////            mBaiduMap.animateMapStatus(u);
//            moveTolocation(mSelfLocation);
//            addLocationMarker(mSelfLocation);
//        } else {
            mLocationHelper = com.sherily.shieh.asteria.baidumap.LocationHelper.sharedInstance(this);

            mLocationHelper.addOnLocatedListener(new com.sherily.shieh.asteria.baidumap.LocationHelper.OnLocatedListener() {
                @Override
                public void onLocated(BDLocation bdLocation) {
//                MyLocationData locData = new MyLocationData.Builder()
//                        .accuracy(bdLocation.getRadius())
//                        .direction(100)// 此处设置开发者获取到的方向信息，顺时针0-360
//                        .latitude(bdLocation.getLatitude())
//                        .longitude(bdLocation.getLongitude())
//                        .accuracy(bdLocation.getRadius())
//                        .direction(bdLocation.getDirection())
//                        .build();
//                mBaiduMap.setMyLocationData(locData);
//                BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
//                        .fromResource(R.mipmap.map_location_self);
//                MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, mCurrentMarker);
//                mBaiduMap.setMyLocationConfigeration(config);

                    LatLng ll = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
//                    MapStatus mapStatus = new MapStatus.Builder()
//                            .target(ll)
//                            .zoom(15)
//                            .build();
//                    MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(mapStatus);
//                    mBaiduMap.animateMapStatus(u);
                    moveTolocation(ll);
                    // mSelfLocation = ll;
//                BitmapDescriptor descriptor = BitmapDescriptorFactory.fromResource(R.mipmap.map_location_self);
//                OverlayOptions option = new MarkerOptions()
//                        .position(ll)
//                        .icon(descriptor);
//                mBaiduMap.addOverlay(option);

                    addLocationMarker(ll);
                }
            });
//        }

       // MoveToSelf(mlocation);

       // mBaiduMap.setOnMarkerClickListener(mOnMarkerClickListener);
       // mBaiduMap.setOnMapClickListener(mOnMapClickListener);




//        recyclerView.setVisibility(View.GONE);
        recyclerView.setHasFixedSize(true);
        adapter = new RegisterAddressRecyclerviewAdapter(RegisterMapActivity.this,mapAddressDataList);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        //recyclerView.addItemDecoration(new RecyclerviewDecoration(this,LinearLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        geoCoderHelper.setListener(new GeoCoderHelper.onReverseGeoCodeResultListener() {
            @Override
            public void result(Object obj,LatLng latLng) {
                adapter.setData((ReverseGeoCodeResult) obj,latLng);
                if (isFirstShow) {
                    isFirstShow = false;
                    if (adapter.isItemNotEmpty()) {
                        recyclerView.setVisibility(View.VISIBLE);
//                        mBaiduMap.clear();
                        mLocationHelper.start();
                    }
                }


            }
        });


//        poiSearchHelper.setListener(new PoiSearchHelper.OnPoiResultListener() {
//            @Override
//            public void result(List<PoiInfo> datas) {
////                list.clear();
////                Log.d("lllll",datas.size()+"");
////                list.addAll(datas);
////                for (PoiInfo poiInfo : list){
////                    Log.d("llaaaaa：",poiInfo.address);
////                }
////                adapter.notifyDataSetChanged();
//                adapter.setData(datas);
//            }
//        });
        mLocationHelper.start();

    }

//    private void setData() {
//        //UI测试数据，实际存放定位地址数据
////        adr1 = new String[] {"花果山","水帘洞","高老庄","御花园"};
////        adr2 = new String[] {"1001","1002","1003","1004"};
////        adr1 = (String[]) myLocation.getArrayList1().toArray();
////        adr2 = (String[]) myLocation.getArrayList2().toArray();
//
//
//    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            for (int gra : grantResults) {
                if (gra != PackageManager.PERMISSION_GRANTED) {
                    return;
//                    //用户不同意，向用户展示该权限作用
//                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
//                        AlertDialog.Builder dia = new AlertDialog.Builder(this);
//                        dia.setTitle("没有该权限没法定位");
//                               dia .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        finish();
//                                    }
//                                });
//                               dia.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        finish();
//                                    }
//                                });
//                        dia.create().show();
//                        return;
//                    }
//                    finish();
                }
                }
                mapView.showZoomControls(false);
                mBaiduMap.setMyLocationEnabled(true);
                mLocationHelper.start();
            }
        }


    private void moveTolocation(LatLng latLng) {
        MapStatus mapStatus = new MapStatus.Builder()
                .target(latLng)
                .zoom(15)
                .build();
        MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(mapStatus);
        mBaiduMap.animateMapStatus(u);
    }

//    private void MoveToSelf(BDLocation location) {
//        LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
//        if (mBaiduMap != null) {
//            MapStatus mapStatus = new MapStatus.Builder()
//                    .target(ll)
//                    .zoom(15)
//                    .build();
//            MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(mapStatus);
//            mBaiduMap.animateMapStatus(u);
//        } /*else {
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    MoveToSelf(mlocation);
//                }
//            }, 1000);
//        }*/
//    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEventMainThread(BDLocation location) {
//        Log.d(TAG, "onEventMainThread: "+location);
//        if(location == null) {
//            return;
//        }
//        if (location.getLatitude() > 0.0001f) {
//            mlocation = location;
//        }
//        MoveToSelf(mlocation);
//
//    }
    @OnClick(R.id.location)
    public void location() {
        //MoveToSelf(mlocation);
        //myLocation.MoveToSelf();
//        if (!TextUtils.isEmpty(selectedLatitude) && !TextUtils.isEmpty(selectedLongitude)) {
//            moveTolocation(mSelfLocation);
//        } else {
            mLocationHelper.start();
//        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        //mLocationHelper.start();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
//        if (adapter.isItemNotEmpty()) {
//            recyclerView.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 关闭图层定位
        mBaiduMap.setMyLocationEnabled(false);
       // myLocation.stop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //myLocation.stop();
//        LocationHelper.sharedInstance(this).stop();
        mLocationHelper.clearOnLocatedListener();
        geoCoderHelper.clear();
        mapView.onDestroy();
//        EventBus.getDefault().unregister(this);
    }

    private void onPre() {
        loadActivity(RegisterActivity.class);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }

    @OnClick(R.id.back)
    public void back() {
        onPre();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onPre();
    }
}
