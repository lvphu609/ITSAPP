package com.example.llh_pc.it_support.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.adapters.GetListPostFinishAdapter;
import com.example.llh_pc.it_support.adapters.LoadPostAdapter;

import com.example.llh_pc.it_support.models.JsonParses.LuuTruParse;
import com.example.llh_pc.it_support.models.LuuTruModel;
import com.example.llh_pc.it_support.restclients.RequestMethod;
import com.example.llh_pc.it_support.restclients.Response;
import com.example.llh_pc.it_support.restclients.RestClient;
import com.example.llh_pc.it_support.utils.Events.eventDetailPost;
import com.example.llh_pc.it_support.utils.Interfaces.Def;
import com.example.llh_pc.it_support.utils.Interfaces.InnoFunctionListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class frmHoanThanh extends AppCompatActivity implements InnoFunctionListener {
    public static final String url_loadPost = Def.API_BASE_LINK + Def.API_LoadPost + Def.API_FORMAT_JSON;
    private ListView lstPost;
    private String token;
    private ArrayList<LuuTruModel> postDetails;
    private GetListPostFinishAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_hoan_thanh);
        initFlags();
        initControl();
        setEventForControl();
        getData();
        setData();
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
        lstPost = (ListView) findViewById(R.id.lsPost);
    }

    @Override
    public void initControl() {

    }

    @Override
    public void setEventForControl() {

    }

    @Override
    public void getData(String... params) {
        SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(frmHoanThanh.this);
        token = sharedPreference.getString("token", "YourName");
        String account_id = sharedPreference.getString("id", "YourName");
        String page = "1";
        try
        {
            RestClient restClient = new RestClient(url_loadPost);
            restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
            restClient.addHeader("token", token);
            restClient.addParam("page", page);
            restClient.addParam("account_id", account_id);
            restClient.addParam("status","2");
            restClient.execute(RequestMethod.POST);
            if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS && restClient.getResponse() != null)
            {
                String jsonObject = restClient.getResponse();
                LuuTruParse luuTruParse = new Gson().fromJson(jsonObject, LuuTruParse.class);
                if(luuTruParse.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS)){
                    postDetails = luuTruParse.getResults();
                }
            }
        }catch (Exception ex){
            Log.e("error" + "Luu Tru", ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void setData() {
        if(postDetails != null) {
            adapter = new GetListPostFinishAdapter(frmHoanThanh.this, R.layout.activity_get_list_post_finish_adapter, postDetails);
            lstPost.setAdapter(adapter);
            lstPost.setOnItemClickListener(new eventDetailPost(this, postDetails));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
        setData();
    }
}
