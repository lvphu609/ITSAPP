package com.example.llh_pc.it_support.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.example.llh_pc.it_support.R;
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
        tvPhone.setText(extras.getString("dienthoai"));
        tvaddress.setText(extras.getString("diachinha"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_frm_chi_tiet_post, menu);
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
}
