package com.example.llh_pc.it_support.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.llh_pc.it_support.R;

import com.example.llh_pc.it_support.utils.Interfaces.InnoFunctionListener;

public class frmDK_DN extends AppCompatActivity implements InnoFunctionListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_dk__dn);
        Button btnDN = (Button)findViewById(R.id.btnDN);
        btnDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(frmDK_DN.this, frmDangNhap.class);
                startActivity(intent1);
            }
        });
        Button btnDK = (Button)findViewById(R.id.btnDK);
        btnDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(frmDK_DN.this, frmDangKy.class);
                startActivity(intent);
            }
        });

        initControl();
        //Profile


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_frm_dk__dn, menu);
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

    }

    @Override
    public void setEventForControl() {

    }

    @Override
    public void getData(String... params) {

    }

    @Override
    public void setData() {

    }
}