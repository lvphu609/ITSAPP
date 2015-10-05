package com.example.llh_pc.it_support.activities;

import android.content.Context;
import android.graphics.Color;
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
import com.example.llh_pc.it_support.utils.Events.eventForgotPass;
import com.example.llh_pc.it_support.utils.Events.eventLogin;
import com.example.llh_pc.it_support.utils.Interfaces.InnoFunctionListener;

import java.util.ArrayList;

public class frmQuenMK extends AppCompatActivity implements InnoFunctionListener {

    private Button btnSend;
    private ArrayList<View> views = new ArrayList<>();
    private EditText edtMail;
    private TextView tvMatKhau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_quen_mk);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back);
        actionBar.setDisplayHomeAsUpEnabled(false);
        initFlags();
        initControl();
        setEventForControl();
        getData();
        setData();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnSend.setTextColor(getResources().getColor(R.color.actionbar_text));
        edtMail = (EditText)findViewById(R.id.edtMail);
        edtMail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().isEmpty())
                {
                    btnSend.setEnabled(false);
                    btnSend.setBackgroundColor(getResources().getColor(R.color.mauxam));
                    tvMatKhau.setText("Vui lòng nhập email.");
                }
                else
                {
                    tvMatKhau.setText("");
                    btnSend.setEnabled(true);
                    btnSend.setBackgroundColor(getResources().getColor(R.color.mauxanh));
                    btnSend.invalidate();
                }
            }
        });
    }
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
        edtMail = (EditText)findViewById(R.id.edtMail);
        views.add((View)edtMail);
        btnSend = (Button)findViewById(R.id.btnSendMail);
        tvMatKhau = (TextView)findViewById(R.id.txtEmail);
    }

    @Override
    public void setEventForControl() {
        btnSend.setOnClickListener(new eventForgotPass(frmQuenMK.this,views));
    }

    @Override
    public void getData(String... params) {

    }

    @Override
    public void setData() {

    }
}
