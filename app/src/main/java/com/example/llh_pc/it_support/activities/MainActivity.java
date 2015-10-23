package com.example.llh_pc.it_support.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.llh_pc.it_support.models.Account;
import com.example.llh_pc.it_support.models.JsonParses.LoginParse;
import com.example.llh_pc.it_support.models.JsonParses.abc;
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
    private String checkToken = Def.API_BASE_LINK + Def.API_checkToken + Def.API_FORMAT_JSON;
    private String url_login = Def.API_BASE_LINK + Def.API_LOGIN + Def.API_FORMAT_JSON;

    private String tokenlogin;
    private String t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(this);
        final String token = sharedPreference.getString("token", null);
        final String email = sharedPreference.getString("email", null);
        final String pass = sharedPreference.getString("password", null);
        int check = sharedPreference.getInt("check", 1);
        int staylogin = sharedPreference.getInt("staylogin", 0);
        if(staylogin == 1)
        {
            if(check == 2) {
                Thread t = new Thread() {
                    public void run() {
                        try {
                            boolean checkLogin = checkToken(token);
                            if(checkLogin) {
                                sleep(2000);
                                finish();
                                Intent cv = new Intent(MainActivity.this, frmTabHost.class);
                                startActivity(cv);
                            }else
                            {
                                LoginTemp(email,pass );
                                sleep(2000);
                                finish();
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
            RestClient restClient = new RestClient(checkToken);
            restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
            restClient.addHeader("token", token);
            restClient.execute(RequestMethod.POST);
            if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS)
            {
                String jsonObject = restClient.getResponse();
                Gson gson = new Gson();
                abc getLoginJson = gson.fromJson(jsonObject, abc.class);
                if (getLoginJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS)) {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }catch (Exception ex)
        {

        }
        return false;
    }
    private void LoginTemp(String U, String P)
    {
        try {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            RestClient restClient = new RestClient("http://demo.innoria.com/itsupport/api/accounts/login");
            restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
            restClient.addParam(Account.EMAIL, U);
            restClient.addParam(Account.PASSWORD, CommonFunction.md5(P));
            restClient.execute(RequestMethod.POST);
            if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS)
            {
                String jsonObject = restClient.getResponse();
                Gson gson = new Gson();
                LoginParse getLoginJson = gson.fromJson(jsonObject, LoginParse.class);
                if (getLoginJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS))
                {
                    String token = getLoginJson.getResults().getAccess_token();
                    String id = getLoginJson.getResults().getAccount_id();

                    String account_type = getLoginJson.getResults().getAccount_type();
                    tokenlogin = getLoginJson.getResults().getAccess_token();
                    /*--------------------information account------------------*/
                    //getAccount(id, tokenlogin);
                    /*--------------------------------------------------------*/
                    editor.putString("token", token);
                    editor.putString("id", id);
                    /*editor.putString("avatar",avatar);
                    editor.putString("fullname",full_name);*/
                    editor.commit();
                   /* Toast.makeText(context, "Đăng nhập thành công.", Toast.LENGTH_LONG).show();*/
                    /*LoginGCM();*/
                    intent = new Intent(this, frmTabHost.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        }catch (Exception ex)
        {
            t = ex.toString();
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
