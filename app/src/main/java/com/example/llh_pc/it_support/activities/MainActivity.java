package com.example.llh_pc.it_support.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.newrelic.agent.android.NewRelic;
import com.example.llh_pc.it_support.R;

public class MainActivity extends AppCompatActivity {
    //private GCMClientManager pushClientManager;
    String PROJECT_NUMBER = "<YOUR PROJECT NUMBER HERE>";
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //-----install newselic-------\\
        NewRelic.withApplicationToken(
                "AAfc264dac549cbfb6e921fae2991f2cefa93a378e"
        ).start(this.getApplication());
        //-----------------------------\\
        SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(this);
        String token = sharedPreference.getString("token", null);
        int check = sharedPreference.getInt("check", 1);
        int staylogin = sharedPreference.getInt("staylogin", 0);
        if(staylogin == 1)
        {
            if(check == 2) {
                Thread t = new Thread() {
                    public void run() {
                        try {
                            sleep(2000);
                            finish();
                            Intent cv = new Intent(MainActivity.this, frmTabHost.class);
                            //Intent cv = new Intent(MainActivity.this, Main2Activity.class);
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
                            //Intent cv = new Intent(MainActivity.this, Main2Activity.class);
                            startActivity(cv);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                t.start();
            }
        }
       else {
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
