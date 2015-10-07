package com.example.llh_pc.it_support.utils.Events;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.activities.frmLuuTru;
import com.example.llh_pc.it_support.adapters.LoadPostAdapter;
import com.example.llh_pc.it_support.models.JsonParses.PostDetailParse;
import com.example.llh_pc.it_support.models.JsonParses.ViewPostParse;
import com.example.llh_pc.it_support.models.LuuTruModel;
import com.example.llh_pc.it_support.models.Post;
import com.example.llh_pc.it_support.models.PostDetail;
import com.example.llh_pc.it_support.models.UserPostDetail;
import com.example.llh_pc.it_support.restclients.RequestMethod;
import com.example.llh_pc.it_support.restclients.Response;
import com.example.llh_pc.it_support.restclients.RestClient;
import com.example.llh_pc.it_support.utils.Interfaces.Def;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LLH-PC on 10/4/2015.
 */
public class eventDelete implements AdapterView.OnItemLongClickListener {
    public static final String url_get = Def.API_BASE_LINK + Def.API_DeletePost + Def.API_FORMAT_JSON;
    private Context context;
    private ArrayList<LuuTruModel> arrayListPost;
    private UserPostDetail uD;
    private ArrayList<View> views;
    public static final String url_loadPost = Def.API_BASE_LINK + Def.API_LoadPost + Def.API_FORMAT_JSON;
    private LoadPostAdapter loadPostAdapter;
    private String t;
    private int current_position = 0;

    public eventDelete(Context current, ArrayList<LuuTruModel> list, LoadPostAdapter loadPostAdapter) {
        this.context = current;
        this.arrayListPost = list;
        this.loadPostAdapter = loadPostAdapter;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            current_position = position;
            new AsyncTask<String, Integer, Boolean>(){
                @Override
                protected Boolean doInBackground(String... params) {
                    SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
                    String token = sharedPreference.getString("token", "token");
                    String account_id = sharedPreference.getString("id", "account");
                    RestClient restClient = new RestClient(url_get);
                    restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
                    restClient.addHeader("token", token);
                    restClient.addParam("account_id", account_id);
                    restClient.addParam("id", params[0]);
                    try {
                        restClient.execute(RequestMethod.POST);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS) {
                        String jsonObject = restClient.getResponse();
                        Gson gson = new Gson();
                        PostDetailParse getLoginJson = gson.fromJson(jsonObject, PostDetailParse.class);
                        if (getLoginJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS)) {
                            return true;
                        }
                        else{
                            return false;
                        }
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Boolean valueFromInProgress) {
                    super.onPostExecute(valueFromInProgress);
                    if(loadPostAdapter != null && valueFromInProgress){
                        loadPostAdapter.remove(loadPostAdapter.getData().get(current_position));
                        loadPostAdapter.notifyDataSetChanged();
                        Toast.makeText(context, "This post has been deleted.",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(context, "This post can not delete.",Toast.LENGTH_SHORT).show();
                    }
                }
            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, loadPostAdapter.getData().get(position).getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
