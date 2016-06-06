package com.sherily.shieh.asteria.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Space;

import com.baidu.mapapi.map.TextureMapView;
import com.sherily.shieh.asteria.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RegisterMapActivity extends BaseActivity implements View.OnClickListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_map);
        ButterKnife.bind(this);
        back.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                Intent intent= new Intent(RegisterMapActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }
}
