package com.example.llh_pc.it_support.activities;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.datas.AccountDAL;
import com.example.llh_pc.it_support.models.Account;
import com.example.llh_pc.it_support.models.Result;
import com.example.llh_pc.it_support.utils.Interfaces.InnoFunctionListener;

public class frmDangNhap extends AppCompatActivity implements InnoFunctionListener{

    String username;
    String pass;
    private EditText edtUserName,edtPass;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_dang_nhap);

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_frm_dang_nhap, menu);
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

    }

    @Override
    public void initControl() {
        edtUserName = (EditText)findViewById(R.id.edtTenDangNhap);
        edtPass = (EditText)findViewById(R.id.edtMatKhau);
        btnLogin = (Button)findViewById(R.id.btnLogin);
    }

    @Override
    public void setEventForControl() {

    }

    @Override
    public void getData(String... params) {
        Result<String> resultLogin = new AccountDAL(frmDangNhap.this).getLogin(new Account(username, pass), false);
    }

    @Override
    public void setData() {

    }
}
