package com.example.llh_pc.it_support.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.models.JsonParses.PostDetailParse;
import com.example.llh_pc.it_support.utils.Interfaces.InnoFunctionListener;

public class frmChiTietPost extends AppCompatActivity {

    TextView tvType,tvLocation_Name,tvNote,tvName,tvPhone,tvaddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_chi_tiet_post);

        tvType = (TextView)findViewById(R.id.tvTypePost);
        tvLocation_Name = (TextView)findViewById(R.id.tvLocation);
        tvNote = (TextView)findViewById(R.id.tvNote);
        tvName = (TextView)findViewById(R.id.tvName);
        tvPhone = (TextView)findViewById(R.id.tvPhone_number);
        tvaddress = (TextView)findViewById(R.id.tvaddress);

        Bundle extras = getIntent().getExtras();
        tvType.setText(extras.getString("loaibaohong"));
        tvLocation_Name.setText(extras.getString("diachi"));
        tvNote.setText(extras.getString("ghichu"));
        tvName.setText(extras.getString("hoten"));
        tvName.setTextColor(getResources().getColor(R.color.mauxanh));
        tvPhone.setText(extras.getString("dienthoai"));
        tvPhone.setTextColor(getResources().getColor(R.color.mauxanh));
        tvaddress.setText(extras.getString("diachinha"));
        LinearLayout one = (LinearLayout) findViewById(R.id.lnProvider);
        one.setVisibility(View.INVISIBLE);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), frmTabHost.class);
        frmTabHost.x = 3;
        startActivityForResult(myIntent, 0);
        return true;
    }
}
