package com.sherily.shieh.asteria.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
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
import com.sherily.shieh.asteria.event.LocationEvent;
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
    private void addLocationMarker() {

        if (mSelfLocation == null)
            return;
        BitmapDescriptor descriptor = BitmapDescriptorFactory.fromResource(R.mipmap.map_location_self);
        OverlayOptions option = new MarkerOptions()
                .position(mSelfLocation)
                .icon(descriptor);
        mBaiduMap.addOverlay(option);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_map);
        ButterKnife.bind(this);

        Log.d("oncreate","oncreate");

        list = new ArrayList<PoiInfo>();
        list2 = new ArrayList<ReverseGeoCodeResult>();
        geoCoderHelper = new GeoCoderHelper();
        poiSearchHelper = new PoiSearchHelper();



        //setData();
        //地图相关初始化
        mapView.showZoomControls(false);
        mBaiduMap = mapView.getMap();
        //隐藏baiduMap logo
        View logo = mapView.getChildAt(1);
        logo.setVisibility(View.INVISIBLE);
        //设置地图缩放级别16 类型普通地图
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(16.0f);
        mBaiduMap.setMapStatus(msu);
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
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
        mLocationHelper = com.sherily.shieh.asteria.baidumap.LocationHelper.sharedInstance(this);
        mLocationHelper.start();
        mLocationHelper.addOnLocatedListener(new com.sherily.shieh.asteria.baidumap.LocationHelper.OnLocatedListener() {
            @Override
            public void onLocated(BDLocation bdLocation) {
                LatLng ll = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                MapStatus mapStatus = new MapStatus.Builder()
                        .target(ll)
                        .zoom(15)
                        .build();
                MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(mapStatus);
                mBaiduMap.animateMapStatus(u);
                //mSelfLocation = ll;
                BitmapDescriptor descriptor = BitmapDescriptorFactory.fromResource(R.mipmap.map_location_self);
                OverlayOptions option = new MarkerOptions()
                        .position(ll)
                        .icon(descriptor);
                mBaiduMap.addOverlay(option);
            }
        });
        //addLocationMarker();
       // MoveToSelf(mlocation);

       // mBaiduMap.setOnMarkerClickListener(mOnMarkerClickListener);
       // mBaiduMap.setOnMapClickListener(mOnMapClickListener);




        recyclerView.setHasFixedSize(true);
        adapter = new RegisterAddressRecyclerviewAdapter(RegisterMapActivity.this,list2,list);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        //recyclerView.addItemDecoration(new RecyclerviewDecoration(this,LinearLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        geoCoderHelper.setListener(new GeoCoderHelper.onReverseGeoCodeResultListener() {
            @Override
            public void result(Object obj,LatLng latLng) {
                adapter.setData((ReverseGeoCodeResult) obj,latLng);
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

    }

    private void setData() {
        //UI测试数据，实际存放定位地址数据
//        adr1 = new String[] {"花果山","水帘洞","高老庄","御花园"};
//        adr2 = new String[] {"1001","1002","1003","1004"};
//        adr1 = (String[]) myLocation.getArrayList1().toArray();
//        adr2 = (String[]) myLocation.getArrayList2().toArray();


    }



    private void MoveToSelf(BDLocation location) {
        LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
        if (mBaiduMap != null) {
            MapStatus mapStatus = new MapStatus.Builder()
                    .target(ll)
                    .zoom(15)
                    .build();
            MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(mapStatus);
            mBaiduMap.animateMapStatus(u);
        } /*else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    MoveToSelf(mlocation);
                }
            }, 1000);
        }*/
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(BDLocation location) {
        Log.d(TAG, "onEventMainThread: "+location);
        if(location == null) {
            return;
        }
        if (location.getLatitude() > 0.0001f) {
            mlocation = location;
        }
        MoveToSelf(mlocation);

    }
    @OnClick(R.id.location)
    public void location() {
        //MoveToSelf(mlocation);
        //myLocation.MoveToSelf();
        mLocationHelper.start();
    }


    @Override
    protected void onStart() {
        super.onStart();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        mLocationHelper.start();

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
       // myLocation.stop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //myLocation.stop();
        LocationHelper.sharedInstance(this).stop();
        geoCoderHelper.clear();
        mapView.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void onPre() {
//        startActivity(new Intent(this, RegisterMapActivity.class));
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
