package com.example.llh_pc.it_support.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.llh_pc.it_support.models.Account;
import com.example.llh_pc.it_support.models.JsonParses.LoginParse;
import com.example.llh_pc.it_support.restclients.RequestMethod;
import com.example.llh_pc.it_support.restclients.Response;
import com.example.llh_pc.it_support.restclients.RestClient;
import com.example.llh_pc.it_support.utils.CommonFunction;
import com.example.llh_pc.it_support.utils.Interfaces.Def;
import com.google.gson.Gson;
import com.newrelic.agent.android.NewRelic;
import com.example.llh_pc.it_support.R;

public class MainActivity extends AppCompatActivity {
    //private GCMClientManager pushClientManager;
    String PROJECT_NUMBER = "<YOUR PROJECT NUMBER HERE>";
    private Intent intent;
    private String url_login = Def.API_BASE_LINK + Def.API_checkToken + Def.API_FORMAT_JSON;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(this);
        final String token = sharedPreference.getString("token", null);
        int check = sharedPreference.getInt("check", 1);
        int staylogin = sharedPreference.getInt("staylogin", 0);
        if(staylogin == 1)
        {
            if(check == 2) {
                Thread t = new Thread() {
                    public void run() {
                        try {
                            boolean check = checkToken(token);
                            if(check) {
                                sleep(2000);
                                finish();
                                Intent cv = new Intent(MainActivity.this, frmTabHost.class);
                                //Intent cv = new Intent(MainActivity.this, Main2Activity.class);
                                startActivity(cv);
                            }else
                            {
                                sleep(2000);
                                finish();
                                Intent cv = new Intent(MainActivity.this, frmDK_DN.class);
                                //Intent cv = new Intent(MainActivity.this, Main2Activity.class);
                                startActivity(cv);
                            }
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
    private boolean checkToken(String token)
    {
        try
        {
            RestClient restClient = new RestClient(url_login);
            restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
            restClient.addHeader("token", token);
            restClient.execute(RequestMethod.POST);
            if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS)
            {
                String jsonObject = restClient.getResponse();
                Gson gson = new Gson();
                LoginParse getLoginJson = gson.fromJson(jsonObject, LoginParse.class);
                if (getLoginJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS)) {
                    String t = "sussecc";
                    return true;
                }
            }
        }catch (Exception ex)
        {

        }
        return false;
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
