package com.sherily.shieh.asteria.baidumaputils;

import android.content.Context;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;

/**
 * Created by Administrator on 2015/6/25.
 * 定位帮助类
 */
public class LocationHelper {

    private static LocationHelper locationHelper = null;

    public static LocationHelper sharedInstance(Context context) {
        if (null == locationHelper) {
            locationHelper = new LocationHelper(context);
        }

        return locationHelper;
    }

    //坐标系
    public enum CoordType {
        GCJ02,
        BD09ll,
        BD09,
    }

    ;

    //默认的地图坐标系
    private CoordType mCoord = CoordType.BD09ll;

    //默认的定位模式
    private LocationClientOption.LocationMode mLocationMode = LocationClientOption.LocationMode.Hight_Accuracy;

    //默认的定位频率
    private int mLocationSpan = 5000;

    private LocationClient mLocationClient;

    private LocationListener mLocationListener;

    private LocationClientOption mLocationClientOption;

    private LocationHelper(Context context) {
        mLocationClient = new LocationClient(context);
        mLocationListener = new LocationListener(context);
        mLocationClientOption = new LocationClientOption();
    }

    //获取最新的定位位置
    public LatLng getLocationCoord() {
        return mLocationListener.getLocationCoord();
    }

    /**
     * 获取最近一次定位的城市的城市代号
     * @return 城市号
     * @deprecated 该方法获取的杭州是179不是3301,不知道怎么转换
     */
    @Deprecated
    public String getCurrentCityCode() {
        return mLocationListener.getCityCode();
    }

    public String getCurrentCity(){
        return mLocationListener.getCity();
    }

    /**
     * 启动定位功能
     */
    public void start() {
        if (!mLocationClient.isStarted()) {
            mLocationClient.registerLocationListener(mLocationListener);
            mLocationClient.start();
        }
    }

    /**
     * 关闭定位功能
     */
    public void stop() {
        if (mLocationClient.isStarted()) {
            mLocationClient.stop();
            mLocationClient.unRegisterLocationListener(mLocationListener);
        }
    }

    /**
     * 加载设置
     */
    public LocationHelper loadOptions() {

        //设置坐标系
        switch (mCoord) {
            case GCJ02:
                mLocationClientOption.setCoorType("gcj02");
                break;
            case BD09ll:
                mLocationClientOption.setCoorType("bd09ll");
                break;
            case BD09:
                mLocationClientOption.setCoorType("bd09");
                break;
        }
        //设置定位模式
        mLocationClientOption.setLocationMode(mLocationMode);
        //设置定位频率
        mLocationClientOption.setScanSpan(mLocationSpan);
        //设置显示地址
        mLocationClientOption.setIsNeedAddress(true);
        mLocationClientOption.setAddrType("all");
        mLocationClientOption.disableCache(false);//禁止启用缓存定位
        mLocationClientOption.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        mLocationClientOption.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        mLocationClientOption.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        mLocationClient.setLocOption(mLocationClientOption);
        return this;
    }

    /**
     * 设置坐标系
     * gcj02: 返回国测局经纬度坐标系
     * bd0911: 返回百度经纬度坐标系
     * bd09: 返回百度墨卡托坐标系
     *
     * @param coord
     * @return
     */
    public LocationHelper setCoord(CoordType coord) {
        mCoord = coord;
        return this;
    }

    /**
     * 设置定位模式
     * 定位模式 分为高精度定位模式 低功耗定位模式 仅设备定位模式
     * 高精度定位模式：这种定位模式下，会同时使用网络定位和GPS定位，优先返回最高精度的定位结果
     * 低功耗定位模式：这种定位模式下，不会使用GPS，只会使用网络定位（Wi-Fi和基站定位）
     * 仅用设备定位模式：这种定位模式下，不需要连接网络，只使用GPS进行定位，这种模式下不支持室内环境的定位
     *
     * @param mode
     * @return
     */
    public LocationHelper setLocationMode(LocationClientOption.LocationMode mode) {
        mLocationMode = mode;
        return this;
    }

    /**
     * 设置定位频率, 能够设置的最小间隔为1秒
     *
     * @param span
     * @return
     */
    public LocationHelper setLocationSpan(int span) {
        mLocationSpan = span < 1000 ? 1000 : span;
        return this;
    }

}
