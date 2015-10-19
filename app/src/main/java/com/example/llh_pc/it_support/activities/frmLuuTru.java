package com.example.llh_pc.it_support.activities;

import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.adapters.LoadPostAdapter;
import com.example.llh_pc.it_support.models.JsonParses.LuuTruParse;
import com.example.llh_pc.it_support.models.JsonParses.PostDetailParse;
import com.example.llh_pc.it_support.models.JsonParses.ViewPostParse;
import com.example.llh_pc.it_support.models.LuuTruModel;
import com.example.llh_pc.it_support.models.Post;
import com.example.llh_pc.it_support.models.PostDetail;
import com.example.llh_pc.it_support.restclients.RequestMethod;
import com.example.llh_pc.it_support.restclients.Response;
import com.example.llh_pc.it_support.restclients.RestClient;
import com.example.llh_pc.it_support.utils.Events.eventDelete;
import com.example.llh_pc.it_support.utils.Events.eventDetailPost;
import com.example.llh_pc.it_support.utils.Interfaces.Def;
import com.example.llh_pc.it_support.utils.Interfaces.InnoFunctionListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class frmLuuTru extends AppCompatActivity implements InnoFunctionListener {
    String t;
    public static final String url_loadPost = Def.API_BASE_LINK + Def.API_LoadPost + Def.API_FORMAT_JSON;
    public static final String url_get= Def.API_BASE_LINK + Def.API_DeletePost + Def.API_FORMAT_JSON;
    private ListView lstPost;
    public static LoadPostAdapter adapter;
    private String token,account_id;

    public static ArrayList<LuuTruModel> postDetails;

    private ArrayList<View> views = new ArrayList<>();

    TextView tv;
    private TabHost mTabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_luu_tru);
        SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(frmLuuTru.this);
        token = sharedPreference.getString("token", "YourName");
        account_id = sharedPreference.getString("id", "YourName");
        String page = "1";
        try
        {
            RestClient restClient = new RestClient(url_loadPost);
            restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
            restClient.addHeader("token", token);
            restClient.addParam("page", page);
            restClient.addParam("account_id", account_id);
            restClient.execute(RequestMethod.POST);
            if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS &&
                    restClient.getResponse() != null)
            {
                String jsonObject = restClient.getResponse();
                LuuTruParse luuTruParse = new Gson().fromJson(jsonObject, LuuTruParse.class);

                if(luuTruParse.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS)){
                    luuTruParse.getResults();
                    postDetails = new ArrayList<LuuTruModel>(Arrays.<LuuTruModel>asList(luuTruParse.getResults()));
                }
            }
        }catch (Exception ex){
            t = ex.toString();
        }
        adapter = new LoadPostAdapter(frmLuuTru.this, R.layout.activity_load_post_adapter, postDetails);
        initFlags();
        initControl();
        setEventForControl();
        getData();
//        Tab
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_frm_luu_tru, menu);
        return true;
    }*/

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
        lstPost = (ListView)findViewById(R.id.lsPost);
        lstPost.setAdapter(adapter);


    }

    @Override
    public void setEventForControl() {
        lstPost.setOnItemClickListener(new eventDetailPost(frmLuuTru.this, postDetails));
        lstPost.setOnItemLongClickListener(new eventDelete(frmLuuTru.this, postDetails, adapter));
    }

    @Override
    public void getData(String... params) {

    }

    @Override
    public void setData() {

    }

}
