package com.example.llh_pc.it_support.activities;

import android.content.Context;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.utils.Events.eventForgotPass;
import com.example.llh_pc.it_support.utils.Events.eventLogin;
import com.example.llh_pc.it_support.utils.Interfaces.InnoFunctionListener;

import java.util.ArrayList;

public class frmQuenMK extends AppCompatActivity implements InnoFunctionListener {

    private Button btnSend;
    private ArrayList<View> views = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_quen_mk);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);

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
        getMenuInflater().inflate(R.menu.menu_frm_quen_mk, menu);
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
        EditText edtMail = (EditText)findViewById(R.id.edtMail);
        views.add((View)edtMail);
        btnSend = (Button)findViewById(R.id.btnSendMail);
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
