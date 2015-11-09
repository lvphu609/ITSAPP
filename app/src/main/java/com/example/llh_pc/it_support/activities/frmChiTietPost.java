package com.example.llh_pc.it_support.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.fragments.TabHostHoatDong;
import com.example.llh_pc.it_support.models.JsonParses.NotificationParse;
import com.example.llh_pc.it_support.models.JsonParses.PostDetailParse;
import com.example.llh_pc.it_support.models.NotificationDetail;
import com.example.llh_pc.it_support.restclients.Response;
import com.example.llh_pc.it_support.utils.Interfaces.InnoFunctionListener;
import com.google.gson.Gson;

public class frmChiTietPost extends AppCompatActivity {

    TextView tvType, tvLocation_Name,tvNote,tvName,tvPhone,tvaddress, tvThoiGian,tvHoTen,tvSoDienThoai,tvCreated_at,tvPicked_at;
    EditText edtRating;
    private NotificationDetail uD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_chi_tiet_post);
        try {
            Bundle extras = getIntent().getExtras();
            String jsonObject = extras.getString("JsonObject");
            Gson gson = new Gson();
            NotificationParse getLoginJson = gson.fromJson(jsonObject, NotificationParse.class);
            if (getLoginJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS)) {
                uD = getLoginJson.getResults();

                /*if(uD.getStatus().equals("0"))
                {

                }else
                {
                    // this is post pick
                    if(rowItem.getStatus().equals("1"))
                    {     // ID current pick post ?
                        if(rowItem.getPicked_by().equals(account_id)) // account provider
                        {
                            // Set processing
                            if(rowItem.getProcessing().equals("0"))
                            {

                            }else
                            {

                            }
                        }
                        else // Account user
                        {
                            if(.getProcessing().equals("0")) {

                            }else
                            {

                            }

                        }
                    }
                }
*/





                String name = uD.getPost_type().getName();
                String Location_name = uD.getLocation_name();
                String content = uD.getContent();
                String created_at = uD.getCreated_at();
                String picked_at = uD.getPicked_at();
                String processing = uD.getProcessing();
                String completed_at = uD.getCompleted_at();


                tvType = (TextView) findViewById(R.id.tvTypePost);
                tvLocation_Name = (TextView) findViewById(R.id.tvLocation);
                tvNote = (TextView) findViewById(R.id.tvNote);
                tvName = (TextView) findViewById(R.id.tvName);
                tvPhone = (TextView) findViewById(R.id.tvPhone_number);
                tvaddress = (TextView) findViewById(R.id.tvaddress);
                tvCreated_at = (TextView)findViewById(R.id.tvCreated_at);
                tvPicked_at = (TextView)findViewById(R.id.tvPicked_at);
                /*info provider pick post*/
                tvHoTen = (TextView) findViewById(R.id.tvHoTen);
                tvSoDienThoai = (TextView)findViewById(R.id.tvSoDienThoai);

            }
        }catch (Exception ex)
        {
            Log.e(ex.toString(),"Lỗi");
        }
        /*tvType = (TextView) findViewById(R.id.tvTypePost);
        tvLocation_Name = (TextView) findViewById(R.id.tvLocation);
        tvNote = (TextView) findViewById(R.id.tvNote);
        tvName = (TextView) findViewById(R.id.tvName);
        tvPhone = (TextView) findViewById(R.id.tvPhone_number);
        tvaddress = (TextView) findViewById(R.id.tvaddress);
        tvCreated_at = (TextView)findViewById(R.id.tvCreated_at);
        tvPicked_at = (TextView)findViewById(R.id.tvPicked_at);

        *//*info provider pick post*//*
        //tvThoiGian = (TextView) findViewById(R.id.tvThoiGian);
        tvHoTen = (TextView) findViewById(R.id.tvHoTen);
        tvSoDienThoai = (TextView)findViewById(R.id.tvSoDienThoai);

        *//*rating*//*
        *//*------------*//*

        Bundle extras = getIntent().getExtras();
        tvType.setText(extras.getString("loaibaohong"));
        tvLocation_Name.setText(extras.getString("diachi"));
        tvNote.setText(extras.getString("ghichu"));
        tvName.setText(extras.getString("hoten"));
        tvName.setTextColor(getResources().getColor(R.color.mauxanh));
        tvPhone.setText(extras.getString("dienthoai"));
        tvPhone.setTextColor(getResources().getColor(R.color.mauxanh));
        tvaddress.setText(extras.getString("diachinha"));
        String updated_at = extras.getString("updated_at");
        if(updated_at == null)
        {
            LinearLayout one = (LinearLayout) findViewById(R.id.lnProvider);
            one.setVisibility(View.INVISIBLE);
        }
        else
        {
            tvHoTen.setText(extras.getString("full_name"));
            tvSoDienThoai.setText(extras.getString("phone_number"));
        }*/
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back);
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
    public void onBackPressed() {
        TabHostHoatDong tabHostHoatDong = new TabHostHoatDong();

        Intent myIntent = new Intent(getApplicationContext(), frmTabHost.class);
        frmTabHost.x = 3;
        startActivityForResult(myIntent, 2);

    }
}
