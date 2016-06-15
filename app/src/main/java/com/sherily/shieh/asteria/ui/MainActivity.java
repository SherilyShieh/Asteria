package com.sherily.shieh.asteria.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sherily.shieh.asteria.R;
import com.sherily.shieh.asteria.engine.SharePrefHelper;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.topPanel)
    RelativeLayout topPanel;
    @Bind(R.id.register_adr_text)
    TextView registerAdrText;
    @Bind(R.id.register_address)
    TextView registerAddress;
    @Bind(R.id.register_edit)
    ImageView registerEdit;
    @Bind(R.id.register_adr)
    RelativeLayout registerAdr;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        String poiAddress = SharePrefHelper.getInstance(this).getString("Selected_poiInfoAddress","");
//        Boolean isPoiAddress = SharePrefHelper.getInstance(this).getBoolean("isPoiAddress",false);
        address = SharePrefHelper.getInstance(this).getString("Selected_Distrct","")
                +SharePrefHelper.getInstance(this).getString("Selected_Street","");
        if (!TextUtils.isEmpty(poiAddress)) {
            registerAddress.setText(poiAddress);
        } else {
            registerAddress.setText(address);
        }



    }


    @OnClick(R.id.register_adr)
    public void modifyRegisterAddress() {
        loadActivity(RegisterActivity.class);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }

}
