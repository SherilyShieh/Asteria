package com.sherily.shieh.asteria.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.common.primitives.Booleans;
import com.sherily.shieh.asteria.R;
import com.sherily.shieh.asteria.engine.SharePrefHelper;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.topPanel)
    RelativeLayout topPanel;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.register_adr)
    EditText registerAdr;
    @Bind(R.id.register_adr_layout)
    LinearLayout registerAdrLayout;
    @Bind(R.id.confirm_button)
    android.widget.Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        String resultAddress = SharePrefHelper.getInstance(this).getString("Selected_ResultAddress", "");
        String poiAddress = SharePrefHelper.getInstance(this).getString("Selected_poiInfoAddress","");
        String streetNum = SharePrefHelper.getInstance(this).getString("Edit_StreetNum", "");
//        Boolean isPoiAddress = SharePrefHelper.getInstance(this).getBoolean("isPoiAddress",false);
        if (!TextUtils.isEmpty(poiAddress)){
            address.setText(poiAddress);
        } else {
           address.setText(resultAddress);
        }
        registerAdr.setText(streetNum);
//        Log.d("RegisterActivity", "onCreate: >>>>>>>>>>>>>>"+address+"   "+streetNum);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        address.setText(SharePrefHelper.getInstance(this).getString("Selected_ResultAddress",""));
//        registerAdr.setText(SharePrefHelper.getInstance(this).getString("Edit_StreetNum",""));
    }

    private void onNext() {
        loadActivity(RegisterMapActivity.class);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }

    private void onPre() {
        loadActivity(MainActivity.class);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("RegisterActivity", "onDestroy: >>>>>>>>>>>>>>");
    }

    @OnClick(R.id.back)
    public void back() {
       onPre();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onPre();
    }
    @OnClick(R.id.register_adr_layout)
    public void modfiyRegister() {
        onNext();
    }
    @OnClick(R.id.confirm_button)
    public void modfiyConfirm() {
        SharePrefHelper.getInstance(this).putString("Edit_StreetNum",registerAdr.getText().toString());
        onPre();
    }
}
