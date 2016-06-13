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
        Intent intent = getIntent();
        address.setText(intent.getStringExtra("address"));

    }


    private void onNext() {
        startActivity(new Intent(this, RegisterMapActivity.class));
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }

    private void onPre() {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
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
        onPre();
        finish();
    }
}
