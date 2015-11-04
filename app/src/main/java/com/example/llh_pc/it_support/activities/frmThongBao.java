package com.example.llh_pc.it_support.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.adapters.ListNotificationAdapter;
import com.example.llh_pc.it_support.models.JsonParses.ListNotificationParse;
import com.example.llh_pc.it_support.models.ListNotificationModel;
import com.example.llh_pc.it_support.restclients.RequestMethod;
import com.example.llh_pc.it_support.restclients.Response;
import com.example.llh_pc.it_support.restclients.RestClient;
import com.example.llh_pc.it_support.utils.Interfaces.Def;
import com.example.llh_pc.it_support.utils.Interfaces.InnoFunctionListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class frmThongBao extends ActionBarActivity implements InnoFunctionListener {
    public static final String url_get_my_notifications = Def.API_BASE_LINK + Def.API_get_my_notifications + Def.API_FORMAT_JSON;
    private String token;
    private String account_id;
    private String page;
    private ArrayList<ListNotificationModel> listNotifi;
    private ListView lstPost;
    private ListNotificationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_thong_bao);
        try {
            SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(this);
            token = sharedPreference.getString("token", null);
            account_id = sharedPreference.getString("id", null);
            page ="1";
            RestClient restClient = new RestClient(url_get_my_notifications);
            restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
            restClient.addHeader("token", token);
            restClient.addParam("account_id", account_id);
            restClient.addParam("page", page);
            restClient.execute(RequestMethod.POST);
            if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS)
            {
                String jsonObject = restClient.getResponse();
                Gson gson = new Gson();
                ListNotificationParse listNotificationParse = gson.fromJson(jsonObject, ListNotificationParse.class);
                if(listNotificationParse.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS))
                {
                    listNotifi = new ArrayList<ListNotificationModel>(Arrays.asList(listNotificationParse.getResults()));
                    adapter = new ListNotificationAdapter(frmThongBao.this, R.layout.activity_post_adapter, listNotifi);
                }
            }
        }catch (Exception ex) {
            Log.e(ex.toString(),"Lỗi");
        }
        initFlags();
        initControl();
        setEventForControl();
        getData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_frm_thong_bao, menu);
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
    public void onBackPressed() {
        //final Intent intent = new Intent(this, frmDK_DN.class);
        AlertDialog.Builder builder = new AlertDialog.Builder(frmThongBao.this);
        new AlertDialog.Builder(frmThongBao.this)
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
        lstPost = (ListView) findViewById(R.id.lsPost);
        lstPost.setAdapter(adapter);
    }

    @Override
    public void setData() {

    }
}
