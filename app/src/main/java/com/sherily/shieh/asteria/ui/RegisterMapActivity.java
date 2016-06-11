package com.sherily.shieh.asteria.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
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
import com.sherily.shieh.asteria.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.http.PUT;

public class RegisterMapActivity extends BaseActivity {

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
    //是否首次定位
    private  boolean isFirstLoc = true;
   //定位SDK的核心类
    private LocationClient mLocClient;
    //定位图层显示模式 (普通-跟随-罗盘)
    private MyLocationConfiguration.LocationMode mCurrentMode;

    private double latitude;
    private double longitude;
//    //定位图标描述
//    private BitmapDescriptor mCurrentMarker = null;
//
    //定位SDK监听函数
    public MyLocationListenner locListener = new MyLocationListenner();

    private BDLocation mlocation;

    /**
     * 定位SDK监听器 需添加locSDK jar和so文件
     */
    public class MyLocationListenner implements BDLocationListener {


        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapview 销毁后不在处理新接收的位置
            if (location == null || mBaiduMap == null) {
                return;
            }
            StringBuilder sb = new StringBuilder(256);
            sb.append("time:");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append(location.getLocationDescribe());
            }
            Log.e("log", sb.toString());


            //MyLocationData.Builder定位数据建造器
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    .direction(100)
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude())
                    .accuracy(location.getRadius())
                    .direction(location.getDirection())
                    .build();
            //设置定位数据
            mBaiduMap.setMyLocationData(locData);
            mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
            //获取经纬度
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            //Toast.makeText(getApplicationContext(), String.valueOf(latitude), Toast.LENGTH_SHORT).show();
            //第一次定位的时候，那地图中心点显示为定位到的位置
            if (isFirstLoc) {
                isFirstLoc = false;
                //地理坐标基本数据结构
                LatLng loc = new LatLng(location.getLatitude(),location.getLongitude());
                //MapStatusUpdate描述地图将要发生的变化
                //MapStatusUpdateFactory生成地图将要反生的变化
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(loc);
                mBaiduMap.animateMapStatus(msu);
//                Toast.makeText(getApplicationContext(), location.getAddrStr(),
//                        Toast.LENGTH_SHORT).show();
            }
        }



    }



    /**
     * 覆盖物监听
     */
    private BaiduMap.OnMarkerClickListener mOnMarkerClickListener = new BaiduMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            return true;
        }
    };
    /**
     *
     */
    private BaiduMap.OnMapClickListener mOnMapClickListener = new BaiduMap.OnMapClickListener() {
        @Override
        public void onMapClick(LatLng latLng) {

        }

        @Override
        public boolean onMapPoiClick(MapPoi mapPoi) {
            return false;
        }
    };

    private BaiduMap.OnMapStatusChangeListener mOnMapStatusChangeListener = new BaiduMap.OnMapStatusChangeListener() {
        @Override
        public void onMapStatusChangeStart(MapStatus mapStatus) {

        }

        @Override
        public void onMapStatusChange(MapStatus mapStatus) {

        }

        @Override
        public void onMapStatusChangeFinish(MapStatus mapStatus) {


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_map);
        ButterKnife.bind(this);
        //地图相关初始化
        mapView.showZoomControls(false);
        mBaiduMap = mapView.getMap();
        //设置地图缩放级别16 类型普通地图
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(16.0f);
        mBaiduMap.setMapStatus(msu);
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        //定位初始化
        //注意: 实例化定位服务 LocationClient类必须在主线程中声明 并注册定位监听接口
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(locListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);              //打开GPS
        option.setCoorType("bd09ll");        //设置坐标类型
        option.setScanSpan(1000);            //设置发起定位请求的间隔时间为1000ms
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy); // 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.disableCache(false);//禁止启用缓存定位
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        mLocClient.setLocOption(option);     //设置定位参数
        mLocClient.start();                  //调用此方法开始定位


        mBaiduMap.setOnMarkerClickListener(mOnMarkerClickListener);
        mBaiduMap.setOnMapClickListener(mOnMapClickListener);
        mBaiduMap.setOnMapStatusChangeListener(mOnMapStatusChangeListener);

        //隐藏baiduMap logo
        View logo = mapView.getChildAt(1);
        logo.setVisibility(View.INVISIBLE);

    }
    private void MoveToSelf() {
        LatLng ll = new LatLng(latitude, longitude);
        if (ll != null && mBaiduMap != null) {
            MapStatus mapStatus = new MapStatus.Builder()
                    .target(ll)
                    .zoom(15)
                    .build();
            MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(mapStatus);
            mBaiduMap.animateMapStatus(u);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    MoveToSelf();
                }
            }, 1000);
        }
    }

//    /**
//     * 定位并添加标注
//     */
//    private void addMyLocation() {
//        //更新
//        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
//                mCurrentMode, true, mCurrentMarker));
//        mBaiduMap.clear();
//        //定义Maker坐标点
//        LatLng point = new LatLng(latitude, longitude);
//        //构建Marker图标
//        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.map_location_self);
//        //构建MarkerOption，用于在地图上添加Marker
//        OverlayOptions option = new MarkerOptions()
//                .position(point)
//                .icon(bitmap);
//        //在地图上添加Marker，并显示
//        mBaiduMap.addOverlay(option);
//    }

    @OnClick(R.id.location)
    public void location() {

        //addMyLocation();
        MoveToSelf();
//        showAddress(mlocation);
    }

//    private void showAddress(BDLocation location) {
//        Toast.makeText(RegisterMapActivity.this, "我的位置："+location.getAddrStr()
//                , Toast.LENGTH_SHORT).show();
//    }

    @Override
    protected void onStart() {
        super.onStart();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mLocClient != null){
            mLocClient.stop();
        }
        mapView.onDestroy();

    }

    private void onPre() {
        startActivity(new Intent(this, RegisterMapActivity.class));
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }

    @OnClick(R.id.back)
    public void back() {
        onPre();
        this.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onPre();
        this.finish();
    }
}
