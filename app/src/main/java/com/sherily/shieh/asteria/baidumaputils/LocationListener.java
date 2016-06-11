package com.sherily.shieh.asteria.baidumaputils;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.model.LatLng;
import com.sherily.shieh.asteria.engine.SharePrefHelper;

import de.greenrobot.event.EventBus;


/**
 * Created by Administrator on 2015/6/25.
 * 百度定位监听
 */

public class LocationListener implements BDLocationListener {
    private final String LOG_TAG = "CLASS:LocationListener";

    //默认的一个定位点
    private final String DEFAULT_LOCATION_LAT_STRING = "30.2596305457264";
    private final String DEFAULT_LOCATION_LNG_STRING =  "120.218782540252";

    private final String LOCATION_LAT_NAME = "location_lat_value";
    private final String LOCATION_LNG_NAME = "location_lng_value";
    private LatLng locationCoord;

    Context context;

    private String cityCode;

    private String city;


    public String getCityCode() {
        return cityCode;
    }

    public String getCity() {
        return city;
    }

    /**
     *
     * @return
     */
    public LatLng getLocationCoord() {
        return locationCoord;
    }

    public LocationListener(Context ctx) {

        context = ctx;
        String latSting = SharePrefHelper.getInstance(context).getString(LOCATION_LAT_NAME, DEFAULT_LOCATION_LAT_STRING);
        String lngString = SharePrefHelper.getInstance(context).getString(LOCATION_LNG_NAME, DEFAULT_LOCATION_LNG_STRING);
        locationCoord = new LatLng(Double.parseDouble(latSting), Double.parseDouble(lngString));
    }

    /**
     *
     * @param bdLocation 定位信息
     */
    @Override
    public void onReceiveLocation(BDLocation bdLocation) {

        //
        locationCoord = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
        cityCode = bdLocation.getCityCode();
        city = bdLocation.getCity();

        //保存最新的位置信息
        SharePrefHelper.getInstance(context).putString(LOCATION_LAT_NAME, Double.toString(bdLocation.getLatitude()));
        SharePrefHelper.getInstance(context).putString(LOCATION_LNG_NAME, Double.toString(bdLocation.getLongitude()));

        EventBus.getDefault().post(bdLocation);

//        StringBuffer stringBuffer = new StringBuffer(512);
//        stringBuffer.append("定位信息已更新:\nTime：");
//        stringBuffer.append(bdLocation.getTime());
//        stringBuffer.append("\n城市：");
//        stringBuffer.append(bdLocation.getCity());
//        stringBuffer.append("\nLocationType:");
//        stringBuffer.append(bdLocation.getLocType());
//        stringBuffer.append("\nLatitude:");
//        stringBuffer.append(bdLocation.getLatitude());
//        stringBuffer.append(" ;Lontitude:");
//        stringBuffer.append(bdLocation.getLongitude());
//        stringBuffer.append("\nRadius:");
//        stringBuffer.append(bdLocation.getRadius());
//        if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {
//            //GPS定位类型
//            stringBuffer.append("\nSpeed:");
//            stringBuffer.append(bdLocation.getSpeed());
//            stringBuffer.append("\nSatelliteNumber:");
//            stringBuffer.append(bdLocation.getSatelliteNumber());
//            stringBuffer.append("\nDirection:");
//            stringBuffer.append(bdLocation.getDirection());
//            stringBuffer.append("\nAddress:");
//            stringBuffer.append(bdLocation.getAddrStr());
//        } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
//            stringBuffer.append("\nAddress:");
//            stringBuffer.append(bdLocation.getAddrStr());
//            //运营商信息
//            stringBuffer.append("\nOperationers:");
//            stringBuffer.append(bdLocation.getOperators());
//        }
//
//        Log.i(LOG_TAG, stringBuffer.toString());
    }
}