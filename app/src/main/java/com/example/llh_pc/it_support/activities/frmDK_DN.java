package com.example.llh_pc.it_support.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.llh_pc.it_support.R;

import com.example.llh_pc.it_support.utils.Interfaces.InnoFunctionListener;

public class frmDK_DN extends AppCompatActivity implements InnoFunctionListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_dk__dn);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null){
            String checkTOKEN = bundle.getString("checkTOKEN","0");
            if(checkTOKEN.equals("1"))
            {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(frmDK_DN.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("check", 1);
                editor.putInt("staylogin", 0);
                editor.commit();
                Validation();
            }
        }

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
    }

    public void Validation(){
        final android.app.AlertDialog.Builder helpBuilder = new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View checkboxLayout = inflater.inflate(R.layout.popup_validation, null);
        helpBuilder.setView(checkboxLayout);
        // set dialog message
        helpBuilder.setCancelable(false);
        final android.app.AlertDialog show = helpBuilder.show();
        Button okpopup= (Button) checkboxLayout.findViewById(R.id.okpopup);
        TextView tv = (TextView)checkboxLayout.findViewById(R.id.tvValidation);
        tv.setText("Đã có người đăng nhập tài khoản của bạn từ thiết bị khác.");
        okpopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //final Intent intent = new Intent(this, frmDK_DN.class);
        AlertDialog.Builder builder = new AlertDialog.Builder(frmDK_DN.this);
        new AlertDialog.Builder(frmDK_DN.this)
                .setMessage("Thoát app?")
                .setCancelable(false)
                .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // Perform Your Task Here--When No is pressed
                        dialog.cancel();
                    }
                }).show();
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
