package com.example.llh_pc.it_support.activities;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.adapters.LoadPostAdapter;
import com.example.llh_pc.it_support.models.JsonParses.ViewPostParse;
import com.example.llh_pc.it_support.models.Post;
import com.example.llh_pc.it_support.models.PostDetail;
import com.example.llh_pc.it_support.restclients.RequestMethod;
import com.example.llh_pc.it_support.restclients.Response;
import com.example.llh_pc.it_support.restclients.RestClient;
import com.example.llh_pc.it_support.utils.Events.eventDetailPost;
import com.example.llh_pc.it_support.utils.Interfaces.Def;
import com.example.llh_pc.it_support.utils.Interfaces.InnoFunctionListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class frmLuuTru extends AppCompatActivity implements InnoFunctionListener {
    String t;
    public static final String url_loadPost = Def.API_BASE_LINK + Def.API_LoadPost + Def.API_FORMAT_JSON;
    private ListView lstPost;
    private ArrayList<Object> array_post = new ArrayList<>();
    private LoadPostAdapter adapter;
    private ArrayList<Post> list = new ArrayList<>();
    private ArrayList<PostDetail> listdetail = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_luu_tru);

        SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(frmLuuTru.this);
        String token = sharedPreference.getString("token", "YourName");
        String account_id = sharedPreference.getString("id", "YourName");
        String page = "1";
        try
        {
            RestClient restClient = new RestClient(url_loadPost);
            restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
            restClient.addHeader("token", token);
            restClient.addParam("page",page);
            restClient.addParam("account_id", account_id);
            restClient.execute(RequestMethod.POST);
            if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS)
            {

                String jsonObject = restClient.getResponse();
                Gson gson = new Gson();
                ViewPostParse getListPostJson = gson.fromJson(jsonObject, ViewPostParse.class);
                if(getListPostJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS))
                {
                    array_post = getListPostJson.getResults();
                    int count = array_post.size();
                    JSONArray array = new JSONArray(array_post);
                    for (int i=0; i< count; i++) {
                        JSONObject jObj = array.getJSONObject(i);
                        String id = jObj.getString("id");
                        String location_name = jObj.getString("location_name");
                        String t = jObj.getString("post_type");
                        Post p = gson.fromJson(t, Post.class);
                        PostDetail pd = gson.fromJson(t, PostDetail.class);
                        pd.setLocation_name(location_name);
                        pd.setPost_type_id(id);
                        listdetail.add(pd);
                    }
                    adapter = new LoadPostAdapter(frmLuuTru.this,R.layout.activity_load_post_adapter,listdetail);
                }
            }
        }catch (Exception ex){
             t = ex.toString();
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        initFlags();
        initControl();
        setEventForControl();
        getData();
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
        lstPost.setOnItemClickListener(new eventDetailPost(frmLuuTru.this,listdetail ));
    }

    @Override
    public void getData(String... params) {

    }

    @Override
    public void setData() {

    }
}
