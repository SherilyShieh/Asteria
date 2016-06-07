package com.sherily.shieh.asteria.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Space;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.Marker;
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

    private BaiduMap mBaiduMap;

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
        mBaiduMap.setOnMarkerClickListener(mOnMarkerClickListener);
        mBaiduMap.setOnMapClickListener(mOnMapClickListener);
        mBaiduMap.setOnMapStatusChangeListener(mOnMapStatusChangeListener);

        //隐藏baiduMap logo
        View logo = mapView.getChildAt(1);
        logo.setVisibility(View.INVISIBLE);
    }

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
