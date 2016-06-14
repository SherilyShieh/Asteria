package com.sherily.shieh.asteria.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.sherily.shieh.asteria.R;
import com.sherily.shieh.asteria.baidumaputils.GeoCoderHelper;
import com.sherily.shieh.asteria.model.AddressModel;
import com.sherily.shieh.asteria.ui.RegisterActivity;
import com.sherily.shieh.asteria.ui.RegisterMapActivity;

import java.util.ArrayList;
import java.util.Collection;

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
    private ArrayList<PoiInfo> list;
    private ArrayList<ReverseGeoCodeResult> list2;
    private LatLng latLng;
    private AddressModel addressModel;
    private ReverseGeoCodeResult reverseGeoCodeResult;
    private GeoCoderHelper geoCoderHelper;
   // private SharedPreferences dataPref;

    public RegisterAddressRecyclerviewAdapter(Context context, ArrayList<ReverseGeoCodeResult> list2, ArrayList<PoiInfo> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.list2 = list2;
        this.list = list;
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
            addressModel = null;
                ((Item1ViewHolder) holder).location1.setText(list2.get(holder.getAdapterPosition()).getAddress());
                ((Item1ViewHolder) holder).location2.setText(list2.get(holder.getAdapterPosition()).getAddressDetail().street);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    SharedPreferences.Editor editor = dataPref.edit();
//                    editor.putString("location", address1[position]);
//                    editor.commit();
//                    Log.d("logggg", latLng.latitude+"::"+latLng.longitude+"");
//                    Log.d("logggg", list2.get(position).getLocation().latitude+"::"+list2.get(position).getLocation().longitude
//                    +"::");
//                    Log.d("logggg","bussine:"+list2.get(position).getBusinessCircle());
//                    Intent intent = new Intent(context, RegisterMapActivity.class);
//                    intent.putExtra("address", list2.get(position));
//                    context.startActivity(intent);
                    Log.d("logggg","bussine:"+list2.get(holder.getAdapterPosition()).getAddressDetail().province
                            +"::"+list2.get(holder.getAdapterPosition()).getAddressDetail().city
                            +"::"+list2.get(holder.getAdapterPosition()).getAddressDetail().district
                            +"::"+list2.get(holder.getAdapterPosition()).getAddressDetail().street
                            +"::"+list2.get(holder.getAdapterPosition()).getAddressDetail().streetNumber);
                    addressModel = holeAddressModel(latLng,list2.get(holder.getAdapterPosition()));
                    Log.d("logggg","bussine:"+addressModel.getName()+"::"+addressModel.getAdress()+"::"+addressModel.getCity()
                            +"::"+addressModel.getProvince()+"::"+addressModel.getDistrict()+"::"+addressModel.getStreet()+"::"+addressModel.getStreetNum());
                }
            });

        } else if (holder instanceof Item2ViewHolder) {
            addressModel = null;
                ((Item2ViewHolder) holder).location1.setText(list.get(holder.getAdapterPosition()).name);
                ((Item2ViewHolder) holder).location2.setText(list.get(holder.getAdapterPosition()).address);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(context, RegisterActivity.class);
//                    intent.putExtra("address", list.get(position).address);
//                    context.startActivity(intent);
//                    ((RegisterMapActivity)context).finish();
                    Log.d("logggg","bussine:"+list.get(holder.getAdapterPosition()).name+"::"+list.get(holder.getAdapterPosition()).address+"::"+list.get(holder.getAdapterPosition()).city);
//                    addressModel = holeAddressModel(list.get(position).location,null);
//                    Log.d("logggg","bussine1:"+addressModel.getName()+"::"+addressModel.getAdress()+"::"+addressModel.getCity()
//                    +"::"+addressModel.getProvince()+"::"+addressModel.getDistrict()+"::"+addressModel.getStreet()+"::"+addressModel.getStreetNum());
                }
            });

        }
    }

    public AddressModel  holeAddressModel(LatLng latLng,ReverseGeoCodeResult result) {
        addressModel = new AddressModel(latLng,result);
        if (result != null){
            addressModel.setModel();

        }else {
            reverseGeoCod(addressModel.getLatLng());
            result = reverseGeoCodeResult;
            addressModel.setResult(result);
            addressModel.setModel();
        }

        return addressModel;
    }



    public void reverseGeoCod(LatLng latLng) {
        geoCoderHelper = new GeoCoderHelper();
        geoCoderHelper.setLatLng(latLng);
        geoCoderHelper.setListener(new GeoCoderHelper.onReverseGeoCodeResultListener() {
            @Override
            public void result(Object obj, LatLng latLng) {
                setReverseGeoCodeResult((ReverseGeoCodeResult)obj);
            }
        });
        geoCoderHelper.reverseGeoCode();
    }

    public void setReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
        this.reverseGeoCodeResult = reverseGeoCodeResult;
    }

    /**
     * 刷新数据
     */

    public void setData(ReverseGeoCodeResult reverseGeoCodeResult, LatLng latLng) {
        this.latLng = latLng;
        list.clear();
        list2.clear();
        list2.add(reverseGeoCodeResult);
        list.add(null);
        list.addAll(reverseGeoCodeResult.getPoiList());
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
       // Log.i(TAG, "getItemCount: "+list.size());
        return list.size();
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
