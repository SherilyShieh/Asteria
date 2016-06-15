package com.sherily.shieh.asteria.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.platform.comapi.map.A;
import com.sherily.shieh.asteria.AsteriaApplication;
import com.sherily.shieh.asteria.R;
import com.sherily.shieh.asteria.baidumaputils.GeoCoderHelper;
import com.sherily.shieh.asteria.engine.SharePrefHelper;
import com.sherily.shieh.asteria.model.AddressModel;
import com.sherily.shieh.asteria.ui.RegisterActivity;
import com.sherily.shieh.asteria.ui.RegisterMapActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by XIEJIALI on 2016/6/7.
 */
public class RegisterAddressRecyclerviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "RegisterAddressRecyclerviewAdapter";

//    private String[] address1;
//    private String[] address2;
    private LayoutInflater inflater;
    private Context context;

//    private ArrayList<PoiInfo> list;
//    private ArrayList<ReverseGeoCodeResult> list2;
    private LatLng latLng;
//    private AddressModel addressModel;
//    private ReverseGeoCodeResult reverseGeoCodeResult;
//    private GeoCoderHelper geoCoderHelper;
   // private SharedPreferences dataPref;
    private List<AddressModel> mapAddressDataList;

    public RegisterAddressRecyclerviewAdapter(Context context, List<AddressModel> mapAddressDataList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
//        this.list2 = list2;
//        this.list = list;
        this.mapAddressDataList = mapAddressDataList;
    }




    //建立枚举 2个item 类型
    public enum ITEM_TYPE {
        ITEM1,
        ITEM2
    }

    /**
     * 第一条Item加载第一种布局，第二条Item加载第二种布局，
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return position == 0 ? ITEM_TYPE.ITEM1.ordinal() : ITEM_TYPE.ITEM2.ordinal();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM1.ordinal()) {
            return new Item1ViewHolder(inflater.inflate(R.layout.register_item_1, parent, false));
        } else {
            return new Item2ViewHolder(inflater.inflate(R.layout.register_item_2, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof Item1ViewHolder) {
                ((Item1ViewHolder) holder).location1.setText(mapAddressDataList.get(holder.getAdapterPosition()).resultAdress);
                ((Item1ViewHolder) holder).location2.setText(mapAddressDataList.get(holder.getAdapterPosition()).street);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    keepData(holder.getAdapterPosition());
//                    AddressModel addressModel = mapAddressDataList.get(holder.getAdapterPosition());
//                    String resultAdress = addressModel.resultAdress;
//                    String latitude =  Double.toString(addressModel.latLng.latitude);
//                    String longitude = Double.toString(addressModel.latLng.longitude);
//                    String poiInfoName = addressModel.poiName;
//                    String poiInfoAddress = addressModel.poiAddress;
//                    String province = addressModel.province;
//                    String city = addressModel.city;
//                    String distrct = addressModel.district;
//                    String street = addressModel.street;
//                    String streetNum = addressModel.streetNum;
//                    SharePrefHelper.getInstance(context).putString("Selected_ResultAddress", resultAdress);
//                    SharePrefHelper.getInstance(context).putString("Selected_Latitude", latitude);
//                    SharePrefHelper.getInstance(context).putString("Selected_Longitude", longitude);
//                    SharePrefHelper.getInstance(context).putString("Selected_poiInfoName", poiInfoName);
//                    SharePrefHelper.getInstance(context).putString("Selected_poiInfoAddress", poiInfoAddress);
//                    SharePrefHelper.getInstance(context).putString("Selected_Province", province);
//                    SharePrefHelper.getInstance(context).putString("Selected_City", city);
//                    SharePrefHelper.getInstance(context).putString("Selected_Distrct", distrct);
//                    SharePrefHelper.getInstance(context).putString("Selected_Street", street);
//                    SharePrefHelper.getInstance(context).putString("Selected_streetNum", streetNum);
////                    SharePrefHelper.getInstance(context).putBoolean("isPoiAddress", false);
//                    Intent intent = new Intent(context, RegisterActivity.class);
//                    context.startActivity(intent);
//                    ((RegisterMapActivity)context).finish();
                }
            });

        } else if (holder instanceof Item2ViewHolder) {
                ((Item2ViewHolder) holder).location1.setText(mapAddressDataList.get(holder.getAdapterPosition()).poiName);
                ((Item2ViewHolder) holder).location2.setText(mapAddressDataList.get(holder.getAdapterPosition()).poiAddress);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    keepData(holder.getAdapterPosition());
//                    AddressModel addressModel = mapAddressDataList.get(holder.getAdapterPosition());
//                    String resultAdress = addressModel.resultAdress;
//                    String latitude =  Double.toString(addressModel.latLng.latitude);
//                    String longitude = Double.toString(addressModel.latLng.longitude);
//                    String poiInfoName = addressModel.poiName;
//                    String poiInfoAddress = addressModel.poiAddress;
//                    String province = addressModel.province;
//                    String city = addressModel.city;
//                    String distrct = addressModel.district;
//                    String street = addressModel.street;
//                    String streetNum = addressModel.streetNum;
//                    SharePrefHelper.getInstance(context).putString("Selected_ResultAddress", resultAdress);
//                    SharePrefHelper.getInstance(context).putString("Selected_Latitude", latitude);
//                    SharePrefHelper.getInstance(context).putString("Selected_Longitude", longitude);
//                    SharePrefHelper.getInstance(context).putString("Selected_poiInfoName", poiInfoName);
//                    SharePrefHelper.getInstance(context).putString("Selected_poiInfoAddress", poiInfoAddress);
//                    SharePrefHelper.getInstance(context).putString("Selected_Province", province);
//                    SharePrefHelper.getInstance(context).putString("Selected_City", city);
//                    SharePrefHelper.getInstance(context).putString("Selected_Distrct", distrct);
//                    SharePrefHelper.getInstance(context).putString("Selected_Street", street);
//                    SharePrefHelper.getInstance(context).putString("Selected_streetNum", streetNum);
////                    SharePrefHelper.getInstance(context).putBoolean("isPoiAddress", true);
//                    Intent intent = new Intent(context, RegisterActivity.class);
//                    context.startActivity(intent);
//                    ((RegisterMapActivity)context).finish();
//                    geoCoderHelper = new GeoCoderHelper();
//                    geoCoderHelper.setLatLng(addressModel.latLng);
//                    geoCoderHelper.setListener(new GeoCoderHelper.onReverseGeoCodeResultListener() {
//                        @Override
//                        public void result(Object obj, LatLng latLng) {
//                            ReverseGeoCodeResult result = (ReverseGeoCodeResult)obj;
//                            ReverseGeoCodeResult.AddressComponent addressComponent = ((ReverseGeoCodeResult) obj).getAddressDetail();
//                            String resultAdress = result.getAddress();
//                            String latitude =  Double.toString(latLng.latitude);
//                            String longitude = Double.toString(latLng.longitude);
//                            String province = addressComponent.province;
//                            String city = addressComponent.city;
//                            String distrct = addressComponent.district;
//                            String street = addressComponent.street;
//                            String streetNum = addressComponent.streetNumber;
//                            SharePrefHelper.getInstance(context).putString("Selected_ResultAddress", resultAdress);
//                            SharePrefHelper.getInstance(context).putString("Selected_Latitude", latitude);
//                            SharePrefHelper.getInstance(context).putString("Selected_Longitude", longitude);
//                            SharePrefHelper.getInstance(context).putString("Selected_Province", province);
//                            SharePrefHelper.getInstance(context).putString("Selected_City", city);
//                            SharePrefHelper.getInstance(context).putString("Selected_Distrct", distrct);
//                            SharePrefHelper.getInstance(context).putString("Selected_Street", street);
//                            SharePrefHelper.getInstance(context).putString("Selected_streetNum", streetNum);
//                            Intent intent = new Intent(context, RegisterActivity.class);
//                            context.startActivity(intent);
////                    geoCoderHelper.clear();
//                            ((RegisterMapActivity)context).finish();
//                        }
//                    });
//                    geoCoderHelper.reverseGeoCode();

                }
            });

        }
    }

    /**
     * 点击Recyclerview Item后持久化保存该Item对应的adressmodel的数据
     * @param postion
     */
    private void keepData(int postion) {
        AddressModel addressModel = mapAddressDataList.get(postion);
        String resultAdress = addressModel.resultAdress;
        String latitude =  Double.toString(addressModel.latLng.latitude);
        String longitude = Double.toString(addressModel.latLng.longitude);
        String poiInfoName = addressModel.poiName;
        String poiInfoAddress = addressModel.poiAddress;
        String province = addressModel.province;
        String city = addressModel.city;
        String distrct = addressModel.district;
        String street = addressModel.street;
        String streetNum = addressModel.streetNum;
        SharePrefHelper.getInstance(context).putString("Selected_ResultAddress", resultAdress);
        SharePrefHelper.getInstance(context).putString("Selected_Latitude", latitude);
        SharePrefHelper.getInstance(context).putString("Selected_Longitude", longitude);
        SharePrefHelper.getInstance(context).putString("Selected_poiInfoName", poiInfoName);
        SharePrefHelper.getInstance(context).putString("Selected_poiInfoAddress", poiInfoAddress);
        SharePrefHelper.getInstance(context).putString("Selected_Province", province);
        SharePrefHelper.getInstance(context).putString("Selected_City", city);
        SharePrefHelper.getInstance(context).putString("Selected_Distrct", distrct);
        SharePrefHelper.getInstance(context).putString("Selected_Street", street);
        SharePrefHelper.getInstance(context).putString("Selected_streetNum", streetNum);
//                    SharePrefHelper.getInstance(context).putBoolean("isPoiAddress", false);
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
        ((RegisterMapActivity)context).finish();
    }
//    public AddressModel  holeAddressModel(LatLng latLng,ReverseGeoCodeResult result) {
//        addressModel = new AddressModel(latLng,result);
//        if (result != null){
//            addressModel.setModel();
//
//        }else {
//            reverseGeoCod(addressModel.getLatLng());
//            result = reverseGeoCodeResult;
//            addressModel.setResult(result);
//            addressModel.setModel();
//        }
//
//        return addressModel;
//    }



//    public void reverseGeoCod(LatLng latLng) {
//        geoCoderHelper = new GeoCoderHelper();
//        geoCoderHelper.setLatLng(latLng);
//        geoCoderHelper.setListener(new GeoCoderHelper.onReverseGeoCodeResultListener() {
//            @Override
//            public void result(Object obj, LatLng latLng) {
//                setReverseGeoCodeResult((ReverseGeoCodeResult)obj);
//            }
//        });
//        geoCoderHelper.reverseGeoCode();
//    }
//
//    public void setReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
//        this.reverseGeoCodeResult = reverseGeoCodeResult;
//    }

    /**
     * 刷新数据
     */

    public void setData(ReverseGeoCodeResult reverseGeoCodeResult, LatLng latLng) {
        this.latLng = latLng;
        mapAddressDataList.clear();
        String resultAddress = reverseGeoCodeResult.getAddress();
        ReverseGeoCodeResult.AddressComponent addressComponent = reverseGeoCodeResult.getAddressDetail();
        String province = addressComponent.province;
        String city = addressComponent.city;
        String district = addressComponent.district;
        String street = addressComponent.street;
        String streetNum = addressComponent.streetNumber;
        mapAddressDataList.add(new AddressModel(latLng,null,null,resultAddress,province,city,district,street,streetNum));
        List<PoiInfo> poiInfoList = reverseGeoCodeResult.getPoiList();
        if (poiInfoList !=null){
            for (PoiInfo poiInfo : poiInfoList){
                mapAddressDataList.add(new AddressModel(poiInfo.location,poiInfo.name,poiInfo.address,null,null,null,null,null,null));
            }

        }
//        list.clear();
//        list2.clear();
//        list2.add(reverseGeoCodeResult);
//        list.add(null);
//        list.addAll(reverseGeoCodeResult.getPoiList());
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
       // Log.i(TAG, "getItemCount: "+list.size());
       //return list.size();
        return mapAddressDataList.size();
    }

    public boolean isItemNotEmpty() {
        return mapAddressDataList.size()>0;
    }


    /**
     * Item点击事件监听器
     */
    public interface OnItemClickListener {
        public void onClick(View parent, int position);
    }



    public static class Item1ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.location1)
        TextView location1;
        @Bind(R.id.location2)
        TextView location2;


        public Item1ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    //item2 的ViewHolder
    public static class Item2ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.location1)
        TextView location1;
        @Bind(R.id.location2)
        TextView location2;
        public Item2ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
