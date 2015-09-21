package com.example.llh_pc.it_support.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_dang_nhap);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = sharedPreferences.edit();


        /*-------Button fogot password---------*/
        final Intent intent1 = new Intent(this, frmQuenMK.class);
        Button btn = (Button)findViewById(R.id.btnLink);
        btn.setOnClickListener(new View.OnClickListener() {
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
                if(isChecked) {
                    editor.putInt("check", 2);
                    editor.commit();
                }
            }
        });

        editor.putInt("check", 1);
        editor.commit();

        initFlags();

        initControl();

        setEventForControl();

        getData();

        setData();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_frm_dang_nhap, menu);
        return true;
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
        try {
            edtUserName = (EditText) findViewById(R.id.edtTenDangNhap);
            edtPass = (EditText) findViewById(R.id.edtMatKhau);
            btnLogin = (Button) findViewById(R.id.btnLogin);
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
