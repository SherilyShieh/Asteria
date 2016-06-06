package com.sherily.shieh.asteria.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.MapView;
import com.sherily.shieh.asteria.R;

import butterknife.Bind;
import butterknife.ButterKnife;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

}
