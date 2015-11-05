package com.example.llh_pc.it_support.activities;

import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
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

public class frmLuuTru extends AppCompatActivity implements InnoFunctionListener,View.OnClickListener{
    String t;
    public static final String url_loadPost = Def.API_BASE_LINK + Def.API_LoadPost + Def.API_FORMAT_JSON;
    public static final String url_get= Def.API_BASE_LINK + Def.API_DeletePost + Def.API_FORMAT_JSON;
    private ListView lstPost;
    private LoadPostAdapter adapter;
    private String token,account_id;

    public static ArrayList<LuuTruModel> postDetails;

    private ProgressDialog progress;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_luu_tru);
        initFlags();
        initControl();
        setEventForControl();
        getData();
    }


    @Override
    public void initFlags() {

    }

    @Override
    public void initControl() {
        lstPost = (ListView)findViewById(R.id.lsPost);
    }

    @Override
    public void setEventForControl() {

    }

    @Override
    public void getData(String... params) {
        GetList();
        new getPostList().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        lstPost.setOnItemClickListener(new eventDetailPost(this, postDetails));
        lstPost.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    ProgressDialog.show(view.getRootView().getContext(),  "", "Please Wait....");
                    String t = "sdfasafsdfsfsfd";
                }catch (Exception ex)
                {
                    Log.e(ex.toString(),"Lỗi");
                }
                return true;
            }
        });
        //lstPost.setOnItemLongClickListener(new eventDelete(this, postDetails));
       /* lstPost.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String t = "fdsasdafsa";
            }
        });*/
        //lstPost.setOnItemLongClickListener(new eventDelete(this, postDetails ));
      }

    @Override
    public void setData() {

    }

    @Override
    public void onClick(View v) {

    }

    private class getPostList extends AsyncTask<String, Integer, ArrayList<LuuTruModel>>{
        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(frmLuuTru.this.getParent(), Def.STRING_EMPTY, "Loading...",true);
            progress.getWindow().setGravity(Gravity.CENTER_VERTICAL);
        }

        @Override
        protected ArrayList<LuuTruModel> doInBackground(String... params) {

            return postDetails;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(ArrayList<LuuTruModel> luuTruModels) {
            super.onPostExecute(luuTruModels);
            if(luuTruModels != null){
                adapter = new LoadPostAdapter(frmLuuTru.this, R.layout.activity_load_post_adapter, postDetails);
                lstPost.setAdapter(adapter);
            }
            progress.dismiss();
        }
        
    };
     public ArrayList<LuuTruModel> GetList()
     {
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
         return postDetails;
     }
}
