package com.sherily.shieh.asteria.baidumaputils;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/12.
 */
public class MyLocation {
    //百度地图对象
    private BaiduMap mBaiduMap;
    private Context context;
    private double latitude;
    private double longitude;


    private MyLocation(BaiduMap mBaiduMap, Context context, double latitude, double longitude) {
        this.mBaiduMap = mBaiduMap;
        this.context = context;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    private static MyLocation myLocation = null;

    public static MyLocation getMyLocation(BaiduMap mBaiduMap, Context context,  double latitude, double longitude) {
        if (null == myLocation) {
            myLocation = new MyLocation(mBaiduMap, context,latitude,longitude);
        }
        return myLocation;
    }

    //是否首次定位
    private  boolean isFirstLoc = true;
    //定位SDK的核心类
    private LocationClient mLocClient;
    //定位图层显示模式 (普通-跟随-罗盘)
    private MyLocationConfiguration.LocationMode mCurrentMode;



    //定位SDK监听函数
    public MyLocationListenner locListener = new MyLocationListenner();

    public void setLocationOption() {
        //定位初始化
        //注意: 实例化定位服务 LocationClient类必须在主线程中声明 并注册定位监听接口
        mLocClient = new LocationClient(context);
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

    }

    /**
     * 启动定位功能
     */
    public void start() {
        if (!mLocClient.isStarted()) {
            mLocClient.registerLocationListener(locListener);
            mLocClient.start();
        }
    }

    /**
     * 关闭定位功能
     */
    public void stop() {
            mLocClient.unRegisterLocationListener(locListener);
            mLocClient.stop();


    }
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
            //MyLocationData.Builder定位数据建造器
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    .direction(location.getDirection())// 此处设置开发者获取到的方向信息，顺时针0-360
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
            //第一次定位的时候，那地图中心点显示为定位到的位置
            if (isFirstLoc) {
                isFirstLoc = false;
                //地理坐标基本数据结构
                LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
                //MapStatusUpdate描述地图将要发生的变化
                //MapStatusUpdateFactory生成地图将要反生的变化
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(loc);
                mBaiduMap.animateMapStatus(msu);// animationMapStatus()方法把定位到的点移动到地图中心
            }
            //Log.d("location",location.getAddrStr()+"::"+location.getLocationDescribe()+"::"+location.getAddress());
            stop();
        }
    }

    public void MoveToSelf() {
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


}
