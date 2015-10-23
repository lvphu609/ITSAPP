package com.example.llh_pc.it_support.activities;

import android.graphics.Typeface;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.utils.Events.eventSetPass;
import com.example.llh_pc.it_support.utils.Interfaces.InnoFunctionListener;

import java.util.ArrayList;

public class frmTaoMoiMatKhau extends AppCompatActivity implements InnoFunctionListener,CompoundButton.OnFocusChangeListener{

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
        /*ABC*/
        tvMaXN = (TextView) findViewById(R.id.txtMaXT);
        tvMatKhau = (TextView) findViewById(R.id.txtMatKhau);
        tvNhapMK = (TextView) findViewById(R.id.txtNhapMK);
        edtCode = (EditText) findViewById(R.id.edtCode);
        edtPass = (EditText) findViewById(R.id.edtPassword);
        edtPass.setTypeface(Typeface.DEFAULT);
        edtPass.setTransformationMethod(new PasswordTransformationMethod());
        edtAPass = (EditText) findViewById(R.id.edtAPass);
        edtAPass.setTypeface(Typeface.DEFAULT);
        edtAPass.setTransformationMethod(new PasswordTransformationMethod());
        views.add((View) edtCode);
        views.add((View) edtPass);
        views.add((View) edtAPass);
        btnSetPass = (Button) findViewById(R.id.btnSetPass);
        /*fds*/


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
                    tvMaXN.setText("Không được để trống trường này.");
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
                    tvMatKhau.setText("Không được để trống trường này.");
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
                    tvNhapMK.setText("Không được để trống trường này.");
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
        edtCode.setOnFocusChangeListener(this);
        edtPass.setOnFocusChangeListener(this);
        edtAPass.setOnFocusChangeListener(this);
        initFlags();

        initControl();

        setEventForControl();

        getData();

        setData();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }
}
