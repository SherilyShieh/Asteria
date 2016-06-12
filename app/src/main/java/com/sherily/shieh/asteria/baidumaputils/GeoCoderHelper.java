package com.sherily.shieh.asteria.baidumaputils;

import android.util.Log;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

/**
 * Created by Administrator on 2016/6/12.
 */
public class GeoCoderHelper {
    private LatLng latLng;
    private GeoCoder geoCoder;
    private String address;

    public GeoCoderHelper(LatLng latLng) {
        this.latLng = latLng;
        geoCoder = GeoCoder.newInstance();
        geoCoder.setOnGetGeoCodeResultListener(geoCoderResultListener);
    }

    public void reverseGeoCode()
    {
        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
    }

    public String getAddress() {
        return address;
    }

    public void clear() {
        geoCoder.destroy();
    }

    private OnGetGeoCoderResultListener geoCoderResultListener = new OnGetGeoCoderResultListener() {
        @Override
        public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

        }

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {

           if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                address = "找不到图中地址信息";
                return;
            }
            address = reverseGeoCodeResult.getAddress();
            Log.d("log", address);
        }
    };
}
