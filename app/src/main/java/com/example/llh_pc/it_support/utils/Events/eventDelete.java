package com.example.llh_pc.it_support.utils.Events;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.adapters.LoadPostAdapter;
import com.example.llh_pc.it_support.models.JsonParses.PostDetailParse;
import com.example.llh_pc.it_support.models.LuuTruModel;
import com.example.llh_pc.it_support.models.UserPostDetail;
import com.example.llh_pc.it_support.restclients.RequestMethod;
import com.example.llh_pc.it_support.restclients.Response;
import com.example.llh_pc.it_support.restclients.RestClient;
import com.example.llh_pc.it_support.utils.Interfaces.Def;
import com.google.gson.Gson;
import java.util.ArrayList;
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
    private ProgressDialog progressDialog;

    public eventDelete(Context current, ArrayList<LuuTruModel> list) {
        this.context = current;
        this.arrayListPost = list;
        //this.loadPostAdapter = load;
    }
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        try
        {
            progressDialog = ProgressDialog.show(context, "IT Support", "Loading...");
            /*LuuTruModel lt = arrayListPost.get(position);
            String idpost = lt.getId();
            new DeletePost().execute(idpost);*/

        }catch (Exception ex)
        {
            Log.e(ex.toString(), "Lỗi");
        }
        return true;
    }

    private class DeletePost extends AsyncTask<String,Void,Boolean>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressDialog = ProgressDialog.show(context, "IT Support", "Loading...");
        }

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
                String token = sharedPreference.getString("token", "token");
                String account_id = sharedPreference.getString("id", "account");
                String idpost = params[0];
                RestClient restClient = new RestClient(url_get);
                restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
                restClient.addHeader("token", token);
                restClient.addParam("account_id", account_id);
                restClient.addParam("id", idpost);
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
            }catch (Exception ex)
            {
                Log.e(ex.toString(),"Lỗi");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean)
            {
                Toast.makeText(context, "Xóa thành công.", Toast.LENGTH_LONG).show();
            }else
            {
                Toast.makeText(context, "Xóa thất bại.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
