package com.example.llh_pc.it_support.utils.Events;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.activities.frmLuuTru;
import com.example.llh_pc.it_support.adapters.LoadPostAdapter;
import com.example.llh_pc.it_support.models.JsonParses.PostDetailParse;
import com.example.llh_pc.it_support.models.JsonParses.ViewPostParse;
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
    private ArrayList<PostDetail> arrayListPost;
    private UserPostDetail uD;
    private ArrayList<View> views;
    public static final String url_loadPost = Def.API_BASE_LINK + Def.API_LoadPost + Def.API_FORMAT_JSON;
    private ArrayList<Object> array_post;
    private List<PostDetail> listdetail;
    private String t;

    public eventDelete(Context current, ArrayList<PostDetail> list, ArrayList<View> viewArrayList) {
        this.context = current;
        this.arrayListPost = list;
        this.views = viewArrayList;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        ListView lstPost =(ListView)views.get(0);
        try {
            PostDetail pd = arrayListPost.get(position);
            String idpost = pd.getPost_type_id();
            SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
            String token = sharedPreference.getString("token", "token");
            String account_id = sharedPreference.getString("id", "account");
            RestClient restClient = new RestClient(url_get);
            restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
            restClient.addHeader("token", token);
            restClient.addParam("account_id", account_id);
            restClient.addParam("id", idpost);
            restClient.execute(RequestMethod.POST);
            if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS) {
                String jsonObject = restClient.getResponse();
                Gson gson = new Gson();
                PostDetailParse getLoginJson = gson.fromJson(jsonObject, PostDetailParse.class);
                if (getLoginJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS)) {
                    try
                    {
                        RestClient restClient1 = new RestClient(url_loadPost);
                        restClient1.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
                        restClient1.addHeader("token", token);
                        restClient1.addParam("page", "1");
                        restClient1.addParam("account_id", account_id);
                        restClient1.execute(RequestMethod.POST);
                        if (restClient1.getResponseCode() == Def.RESPONSE_CODE_SUCCESS)
                        {
                            String jsonObject1 = restClient.getResponse();
                            Gson gson1 = new Gson();
                            ViewPostParse getListPostJson = gson1.fromJson(jsonObject1, ViewPostParse.class);
                            if(getListPostJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS))
                            {
                                LoadPostAdapter adapter = new LoadPostAdapter(context, R.layout.activity_load_post_adapter,listdetail);
                                ((BaseAdapter) lstPost.getAdapter()).notifyDataSetChanged();
                                Intent intent = new Intent(context, frmLuuTru.class);
                                context.startActivity(intent);
                            }
                        }
                    }catch (Exception ex){
                        t = ex.toString();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;

    }
}
