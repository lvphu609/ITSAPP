package com.example.llh_pc.it_support.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.llh_pc.it_support.R;

public class MainActivity extends AppCompatActivity {

    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(this);
        int check = sharedPreference.getInt("check", 1);
        if(check == 2) {
            Thread t = new Thread() {
                public void run() {
                    try {
                        sleep(2000);
                        finish();
                        Intent cv = new Intent(MainActivity.this, frmBaoHu.class);

                        startActivity(cv);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            t.start();
        }else {
            Thread t = new Thread() {
                public void run() {
                    try {

                        sleep(2000);
                        finish();
                        Intent cv = new Intent(MainActivity.this, frmDK_DN.class);

                        startActivity(cv);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            t.start();
        }


        /*intent = new Intent(this, frmDK_DN.class);
        Button btnDangNhap = (Button)findViewById(R.id.btnLogin);
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
