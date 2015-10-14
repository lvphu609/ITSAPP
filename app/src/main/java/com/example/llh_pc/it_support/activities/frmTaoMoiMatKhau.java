package com.example.llh_pc.it_support.activities;

import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.utils.Events.eventSetPass;
import com.example.llh_pc.it_support.utils.Interfaces.InnoFunctionListener;

import java.util.ArrayList;

public class frmTaoMoiMatKhau extends AppCompatActivity implements InnoFunctionListener {

    private ArrayList<View> views = new ArrayList<>();
    private EditText edtCode,edtPass,edtAPass;
    private Button btnSetPass;
    private String mail;
    private int A,B,C = 0;
    private TextView tvMaXN, tvMatKhau, tvNhapMK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_tao_moi_mat_khau);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back);
        actionBar.setDisplayHomeAsUpEnabled(false);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("mail");
            this.mail = value;
        }
        initFlags();

        initControl();

        setEventForControl();

        getData();

        setData();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnSetPass.setTextColor(getResources().getColor(R.color.actionbar_text));

        edtCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    A = 0;
                    tvMaXN.setText("Vui lòng nhập mã xác thực.");
                    btnSetPass.setEnabled(false);
                    btnSetPass.setBackgroundColor(getResources().getColor(R.color.mauxam));
                } else {
                    A = 1;
                    if (A + B + C == 3) {
                        tvMaXN.setText("");
                        btnSetPass.setEnabled(true);
                        btnSetPass.setBackgroundColor(getResources().getColor(R.color.mauxanh));
                    }
                    tvMaXN.setText("");
                }
            }
        });

        edtPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    tvMatKhau.setText("Vui lòng nhập mật khẩu mới.");
                    B = 0;
                    btnSetPass.setEnabled(false);
                    btnSetPass.setBackgroundColor(getResources().getColor(R.color.mauxam));

                } else {
                    B = 1;
                    if (A + B + C == 3) {
                        tvMatKhau.setText("");
                        btnSetPass.setEnabled(true);
                        btnSetPass.setBackgroundColor(getResources().getColor(R.color.mauxanh));
                    }
                    tvMatKhau.setText("");
                }
            }
        });
        edtAPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    C = 0;
                    btnSetPass.setEnabled(false);
                    btnSetPass.setBackgroundColor(getResources().getColor(R.color.mauxam));
                    tvNhapMK.setText("Vui lòng nhập lại mật khẩu.");
                } else {
                    C = 1;
                    if (A + B + C == 3) {
                        btnSetPass.setEnabled(true);
                        btnSetPass.setBackgroundColor(getResources().getColor(R.color.mauxanh));
                    }
                    tvNhapMK.setText("");
                }
            }
        });
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_frm_tao_moi_mat_khau, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initFlags() {

    }

    @Override
    public void initControl() {
        tvMaXN = (TextView)findViewById(R.id.txtMaXT);
        tvMatKhau = (TextView)findViewById(R.id.txtMatKhau);
        tvNhapMK = (TextView)findViewById(R.id.txtNhapMK);
        edtCode = (EditText)findViewById(R.id.edtCode);
        edtPass = (EditText)findViewById(R.id.edtPassword);
        edtAPass = (EditText)findViewById(R.id.edtAPass);
        views.add((View)edtCode);
        views.add((View)edtPass);
        views.add((View)edtAPass);
        btnSetPass = (Button)findViewById(R.id.btnSetPass);
    }

    @Override
    public void setEventForControl() {
        btnSetPass.setOnClickListener(new eventSetPass(frmTaoMoiMatKhau.this, views,mail));
    }

    @Override
    public void getData(String... params) {

    }

    @Override
    public void setData() {

    }
}
