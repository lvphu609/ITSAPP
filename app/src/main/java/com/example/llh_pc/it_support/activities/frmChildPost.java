package com.example.llh_pc.it_support.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Filter;
import android.widget.ListView;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.adapters.ChildPostAdapter;
import com.example.llh_pc.it_support.adapters.PostAdapter;
import com.example.llh_pc.it_support.models.Account;
import com.example.llh_pc.it_support.models.JsonParses.PostParse;
import com.example.llh_pc.it_support.models.Post;
import com.example.llh_pc.it_support.restclients.RequestMethod;
import com.example.llh_pc.it_support.restclients.Response;
import com.example.llh_pc.it_support.restclients.RestClient;
import com.example.llh_pc.it_support.utils.Events.evenSubPostType;
import com.example.llh_pc.it_support.utils.Interfaces.Def;
import com.example.llh_pc.it_support.utils.Interfaces.InnoFunctionListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class frmChildPost extends AppCompatActivity implements InnoFunctionListener {
    public static final String url_get_my_notifications = Def.API_BASE_LINK + Def.API_SubPost + Def.API_FORMAT_JSON;
    private ListView lstPost;
    public static ArrayList<Post> array_post = new ArrayList<>();

    public static  ChildPostAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_child_post);

        Bundle extras = getIntent().getExtras();

            String value = extras.getString("NamePost");
            String ID = extras.getString("ID");
            getSupportActionBar().setTitle(value);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        try
        {
            SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(this);
            final String token = sharedPreference.getString("token", null);
            RestClient restClient = new RestClient(url_get_my_notifications);
            restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
            restClient.addParam(Post.ID, ID);
            restClient.addHeader("token",token);
            restClient.execute(RequestMethod.POST);
            if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS)
            {
                String jsonObject = restClient.getResponse();
                Gson gson = new Gson();
                PostParse getListPostJson = gson.fromJson(jsonObject, PostParse.class);
                if(getListPostJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS))
                {
                    array_post = getListPostJson.getResults();
                    adapter = new ChildPostAdapter(frmChildPost.this,R.layout.activity_child_post_adapter,array_post);
                }else if(getListPostJson.getStatus().equalsIgnoreCase(Response.STATUS_FALSE))
                {
                    Intent intent = new Intent(this, frmDK_DN.class);
                    intent.putExtra("checkTOKEN","1");
                    startActivity(intent);
                }
            }
        }catch (Exception ex){}
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        initFlags();
        initControl();
        setEventForControl();
        getData();
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
   @Override
   public void onBackPressed() {
       final Intent intent = new Intent(this, frmTabHost.class);
       startActivity(intent);
   }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), frmTabHost.class);
        startActivityForResult(myIntent, 0);
        return true;
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
        lstPost.setOnItemClickListener(new evenSubPostType(frmChildPost.this, array_post));
    }

    @Override
    public void getData(String... params) {
    }

    @Override
    public void setData() {
    }
}
