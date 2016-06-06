package com.sherily.shieh.asteria.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sherily.shieh.asteria.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

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

        back.setOnClickListener(this);
        registerAdrLayout.setOnClickListener(this);
        confirmButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.register_adr_layout:
                Intent intent2 = new Intent(RegisterActivity.this, RegisterMapActivity.class);
                startActivity(intent2);
            case R.id.confirm_button:
                Intent intent1 = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent1);
                finish();
                break;
        }
    }
}
