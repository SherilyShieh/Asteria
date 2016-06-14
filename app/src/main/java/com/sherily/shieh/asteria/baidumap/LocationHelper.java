package com.sherily.shieh.asteria.baidumap;

import android.content.Context;
import android.location.LocationManager;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;

import java.util.HashSet;

import static com.baidu.location.LocationClientOption.LocationMode.Hight_Accuracy;

/**
 * 定位
 *
 * @author zhudongjie .
 */
public class LocationHelper {

    private static final String TAG = "LocationHelper";

    public static final String COOR_TYPE_BD09 = "bd09";

    public static final String COOR_TYPE_BD09LL = "bd09ll";

    public static final String COOR_TYPE_GCJ02 = "gcj02";

    private Context mContext;

    private LocationClient mLocationClient;

    private HashSet<OnLocatedListener> mOnLocatedListeners;

    private LocationClientOption mOption;

    private double mLastLatitude = 0.0d;

    private double mLastLongitude = 0.0d;

    /**
     * 定位监听
     */
    public interface OnLocatedListener {

        /**
         * 定位成功后回调
         */
        void onLocated(BDLocation bdLocation);
    }

    private BDLocationListener mLocationListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
                Log.d(TAG, "onReceiveLocation: " + (bdLocation == null ? null :
                        "[" + bdLocation.getLatitude() + "," + bdLocation.getLongitude() + "]"));

            if (bdLocation != null) {
                mLocationClient.stop();
                mLastLatitude = bdLocation.getLatitude();
                mLastLongitude = bdLocation.getLongitude();
                for (OnLocatedListener onLocatedListener : mOnLocatedListeners) {
                    onLocatedListener.onLocated(bdLocation);
                }
            }
        }
    };

    private static LocationHelper locationHelper = null;
    public static LocationHelper sharedInstance(Context context) {
        if (locationHelper == null) {
            locationHelper = new LocationHelper(context);
        }
        return locationHelper;
    }

    private LocationHelper(Context context) {
        mContext = context.getApplicationContext();
        mLocationClient = new LocationClient(mContext);
        mLocationClient.registerLocationListener(mLocationListener);
        // 设置定位的相关配置
        mOption = new LocationClientOption();
        // mOption.setOpenGps(true);// 打开gps
        mOption.setCoorType(COOR_TYPE_BD09LL); // 设置坐标类型
        mOption.setScanSpan(1000);
        mOption.setLocationMode(Hight_Accuracy);
        mOption.disableCache(false);//禁止启用缓存定位
        mOption.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        mOption.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        mOption.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        mLocationClient.setLocOption(mOption);
    }

//    public LocationHelper setCoorType(String coorType) {
//        mOption.setCoorType(coorType);
//        return this;
//    }
//
//    public LocationHelper setLocationMode(LocationClientOption.LocationMode locationMode) {
//        mOption.setLocationMode(locationMode);
//        return this;
//    }

    public double getLastLatitude() {
        return mLastLatitude;
    }

    public double getLastLongitude() {
        return mLastLongitude;
    }

    public LatLng getLastLocation() {
        return new LatLng(mLastLatitude, mLastLongitude);
    }

    public double getDistance(LatLng desc) {
        return DistanceUtil.getDistance(getLastLocation(), desc);
    }

    /**
     * GPS定位是否可用
     */
    public boolean isGpsLocationEnable() {
        LocationManager locationManager = (LocationManager) mContext.
                getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * 网络定位是否可用
     */
    public boolean isNetworkLocationEnable() {
        LocationManager locationManager = (LocationManager) mContext.
                getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    /**
     * 开始定位
     */
    public void start() {
        if (!mLocationClient.isStarted()) {
            mLocationClient.start();
        }
    }

    /**
     * 取消定位
     */
    public void cancel() {
        if (mLocationClient != null && mLocationClient.isStarted()) {
            mLocationClient.stop();
        }
    }

    /**
     * 添加一个定位监听
     *
     * @param onLocatedListener 要添加的定位监听对象
     */
    public void addOnLocatedListener(OnLocatedListener onLocatedListener) {
        if (mOnLocatedListeners == null) {
            mOnLocatedListeners = new HashSet<>();
        }
        mOnLocatedListeners.add(onLocatedListener);
    }

    /**
     * 删除指定的定位监听
     *
     * @param onLocatedListener 要删除的定位监听
     */
    public void removeOnLocatedListener(OnLocatedListener onLocatedListener) {
        if (mOnLocatedListeners != null) {
            mOnLocatedListeners.remove(onLocatedListener);
        }
    }

    /**
     * 删除所有的定位监听
     */
    public void clearOnLocatedListener() {
        if (mOnLocatedListeners != null) {
            mOnLocatedListeners.clear();
        }
    }
}
