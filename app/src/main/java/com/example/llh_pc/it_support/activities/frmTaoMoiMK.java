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
import com.example.llh_pc.it_support.utils.Events.eventLogin;
import com.example.llh_pc.it_support.utils.Events.eventSetPass;
import com.example.llh_pc.it_support.utils.Interfaces.InnoFunctionListener;

import java.util.ArrayList;

public class frmTaoMoiMK  extends AppCompatActivity implements InnoFunctionListener {

    private String mail;
    private ArrayList<View> views = new ArrayList<>();
    private Button btnSetPass;
    private EditText edtCode,edtPass;
    private EditText edtAPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_tao_moi_mk);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("mail");
            this.mail = value;
        }

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_frm_tao_moi_mk, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void initFlags() {
        btnSetPass.setOnClickListener(new eventSetPass(frmTaoMoiMK.this,views,mail));
    }

    @Override
    public void initControl() {
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
        btnSetPass.setOnClickListener(new eventSetPass(frmTaoMoiMK.this, views, mail));
    }

    @Override
    public void getData(String... params) {

    }

    @Override
    public void setData() {

    }
}
