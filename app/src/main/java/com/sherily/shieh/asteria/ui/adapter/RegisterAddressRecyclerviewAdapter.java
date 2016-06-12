package com.sherily.shieh.asteria.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.sherily.shieh.asteria.R;
import com.sherily.shieh.asteria.ui.RegisterMapActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by XIEJIALI on 2016/6/7.
 */
public class RegisterAddressRecyclerviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    private String[] address1;
    private String[] address2;
    private LayoutInflater inflater;
    private Context context;
   // private SharedPreferences dataPref;

    private onDataSetChangeListener onDataSetChangeListener;

    public RegisterAddressRecyclerviewAdapter(Context context, String[] address1, String[] address2) {
        this.context = context;
        this.address1 = address1;
        this.address2 = address2;
        inflater = LayoutInflater.from(context);
        //dataPref = context.getSharedPreferences("address_selected" , context.MODE_PRIVATE);
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof Item1ViewHolder) {
            ((Item1ViewHolder) holder).location1.setText(address1[position]);
            ((Item1ViewHolder) holder).location2.setText(address2[position]);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    SharedPreferences.Editor editor = dataPref.edit();
//                    editor.putString("location", address1[position]);
//                    editor.commit();
                    Intent intent = new Intent(context, RegisterMapActivity.class);
                    intent.putExtra("address", address1[position]);
                    context.startActivity(intent);
                }
            });
        } else if (holder instanceof Item2ViewHolder) {
            ((Item2ViewHolder) holder).location1.setText(address1[position]);
            ((Item2ViewHolder) holder).location2.setText(address2[position]);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, RegisterMapActivity.class);
                    intent.putExtra("address", address1[position]);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return address1.length;
    }

    /**
     * Item点击事件监听器
     */
    public interface OnItemClickListener {
        public void onClick(View parent, int position);
    }


    /**
     * 数据更新监听器
     */
    public interface onDataSetChangeListener{

        void onDataSetChange(boolean isEmpty);
    }
    //item1 的ViewHolder
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
