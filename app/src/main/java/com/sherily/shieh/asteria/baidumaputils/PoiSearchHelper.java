package com.sherily.shieh.asteria.baidumaputils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.sherily.shieh.asteria.ui.RegisterMapActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public class PoiSearchHelper {
    private PoiSearch poiSearch;
    private ArrayList list;
    private LatLng latLng;

//    private Handler mhandler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            List<PoiInfo>datas = (List<PoiInfo>) msg.obj;
//            if (listener!= null)
//            {
//                listener.result(datas);
//            }
//        }
//    } ;


    /**
     * 初始化
     */
    public PoiSearchHelper() {
        poiSearch = PoiSearch.newInstance();
        poiSearch.setOnGetPoiSearchResultListener(poiSearchResultListener);
        list = new ArrayList();

    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    /**
     * 发起检索请求
     */
    public void searchNearby() {
        PoiNearbySearchOption poiNearbySearchOption = new PoiNearbySearchOption();
        poiNearbySearchOption.location(latLng)
        .keyword("写字楼")
        .radius(1000)
        .pageCapacity(10);
        poiSearch.searchNearby(poiNearbySearchOption);

    }

    public void clear() {
        if(poiSearch != null) {
            poiSearch.destroy();
        }
    }


    private OnGetPoiSearchResultListener poiSearchResultListener = new OnGetPoiSearchResultListener() {
        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            if (poiResult == null || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
                // 没有找到检索结果
                Log.d("log","没有找到检索结果");
                return;
            }
            if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {// 检索结果正常返回

//                  Message message = new Message();
//                message.obj = poiResult.getAllPoi();
//                  mhandler.sendMessage(message) ;
                listener.result(poiResult.getAllPoi());



            }



        }

        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

        }
    };

    OnPoiResultListener listener;

    public void setListener(OnPoiResultListener listener) {
        this.listener = listener;
    }

    public interface OnPoiResultListener{
        void result(List<PoiInfo> list);
    }
}
