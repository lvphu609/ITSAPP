package com.example.llh_pc.it_support.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.utils.Events.eventLogin;
import com.example.llh_pc.it_support.utils.Events.eventStayLgoin;
import com.example.llh_pc.it_support.utils.Interfaces.InnoFunctionListener;

import java.util.ArrayList;

public class frmDangNhap extends AppCompatActivity implements InnoFunctionListener{

    private Intent intent;
    private EditText edtUserName,edtPass;
    private Button btnLogin;
    private ArrayList<View> views = new ArrayList<>();
    private CheckBox cbSave;
    private int E,P  =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_dang_nhap);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setTextColor(getResources().getColor(R.color.actionbar_text));

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        /*-------Button fogot password---------*/
        final Intent intent1 = new Intent(this, frmQuenMK.class);
              final TextView txtQuenMK = (TextView)findViewById(R.id.txtQuenMatKhau);
        txtQuenMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent1);
            }
        });

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        cbSave = (CheckBox)findViewById(R.id.cbLuuTK);
        cbSave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editor.putInt("check", 2);
                    editor.commit();
                }
            }
        });
        editor.putInt("check", 1);
        editor.commit();

        final TextView tvEmail = (TextView)findViewById(R.id.txtEmail);
        edtUserName = (EditText) findViewById(R.id.edtTenDangNhap);
        edtUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().isEmpty() )
                {
                    E = 0;
                    btnLogin.setEnabled(false);
                    btnLogin.setBackgroundColor(getResources().getColor(R.color.mauxam));
                    tvEmail.setText("Vui lòng nhập email.");
                }
                else
                {
                    E = 1;
                    if(E+P == 2)
                    {
                        btnLogin.setEnabled(true);
                        btnLogin.setBackgroundColor(getResources().getColor(R.color.mauxanh));
                        btnLogin.invalidate();
                        tvEmail.setText("");
                    }
                }
            }
        });

        final TextView tvMatKhau = (TextView)findViewById(R.id.txtMatKhau);
        edtPass = (EditText) findViewById(R.id.edtMatKhau);
        edtPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty() )
                {
                    P = 0;
                    btnLogin.setEnabled(false);
                    btnLogin.setBackgroundColor(getResources().getColor(R.color.mauxam));
                    tvMatKhau.setText("Vui lòng nhập mật khẩu.");
                }
                else
                {
                    P =1;
                    if(E+P == 2)
                    {
                        btnLogin.setEnabled(true);
                        btnLogin.setBackgroundColor(getResources().getColor(R.color.mauxanh));
                        btnLogin.invalidate();
                        tvMatKhau.setText("");
                    }
                    tvMatKhau.setText("");
                }
            }
        });

        initFlags();
        initControl();
        setEventForControl();
        getData();
        setData();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



    @Override
    public void onBackPressed() {
        final Intent intent = new Intent(this, frmDK_DN.class);
        startActivity(intent);
    }


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_frm_dang_nhap, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), frmDK_DN.class);
        startActivityForResult(myIntent, 0);

        return true;
    }


    @Override
    public void initFlags() {

    }

    @Override
    public void initControl() {
        try {
            views.add((View)edtUserName);
            views.add((View)edtPass);
            views.add((View)cbSave);
        }catch (Exception ex)
        {
            System.out.print(ex.toString());
        }
    }

    @Override
    public void setEventForControl() {
        btnLogin.setOnClickListener(new eventLogin(frmDangNhap.this, views));
    }

    @Override
    public void getData(String... params) {

    }

    @Override
    public void setData() {

    }
}
