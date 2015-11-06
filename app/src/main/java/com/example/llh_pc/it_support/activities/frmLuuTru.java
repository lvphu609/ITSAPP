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
import java.util.concurrent.ExecutorService;

public class frmLuuTru extends AppCompatActivity implements InnoFunctionListener{
    String t;
    public static final String url_loadPost = Def.API_BASE_LINK + Def.API_LoadPost + Def.API_FORMAT_JSON;
    public static final String url_get= Def.API_BASE_LINK + Def.API_DeletePost + Def.API_FORMAT_JSON;
    private ListView lstPost;
    private LoadPostAdapter adapter;
    private String token,account_id;

    public static ArrayList<LuuTruModel> postDetails;

    private ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_luu_tru);
        initFlags();
        initControl();
        setEventForControl();
        getData();
        setData();
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
        }
        catch (Exception ex){
            Log.e("error" + "Luu Tru", ex.getMessage());
           ex.printStackTrace();
        }
        //lstPost.setOnItemLongClickListener(new eventDelete(this, postDetails ));
      }
    @Override
    public void onBackPressed() {
        //final Intent intent = new Intent(this, frmDK_DN.class);
        AlertDialog.Builder builder = new AlertDialog.Builder(frmLuuTru.this);
        new AlertDialog.Builder(frmLuuTru.this.getParent())
                .setMessage("Thoát app?")
                .setCancelable(false)
                .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Perform Your Task Here--When No is pressed
                        dialog.cancel();
                    }
                }).show();
    }
//    @Override
//    public void setData() {
//
//
//        }catch (Exception ex){
//            Log.e("error" + "Luu Tru", ex.getMessage());
//            ex.printStackTrace();
//        }
//    }

    @Override
    public void setData() {
        adapter = new LoadPostAdapter(frmLuuTru.this, R.layout.activity_load_post_adapter, postDetails);
        lstPost.setAdapter(adapter);
    }
}
