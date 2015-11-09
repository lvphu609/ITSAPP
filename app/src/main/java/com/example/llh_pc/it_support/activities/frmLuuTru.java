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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.adapters.LoadPostAdapter;
import com.example.llh_pc.it_support.models.JsonParses.LuuTruParse;
import com.example.llh_pc.it_support.models.JsonParses.NotificationParse;
import com.example.llh_pc.it_support.models.JsonParses.PostDetailParse;
import com.example.llh_pc.it_support.models.JsonParses.ViewPostParse;
import com.example.llh_pc.it_support.models.LuuTruModel;
import com.example.llh_pc.it_support.models.NotificationDetail;
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

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class frmLuuTru extends AppCompatActivity implements InnoFunctionListener {
    String t;
    public static final String url_loadPost = Def.API_BASE_LINK + Def.API_LoadPost + Def.API_FORMAT_JSON;
    public static final String url_postDetail = Def.API_BASE_LINK + Def.API_Loadpostdetail + Def.API_FORMAT_JSON;
    public static final String url_delete = Def.API_BASE_LINK + Def.API_DeletePost + Def.API_FORMAT_JSON;
    private ListView lstPost;
    private LoadPostAdapter adapter;
    private String token, account_id;
    private NotificationDetail uD;
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

        lstPost = (ListView) findViewById(R.id.lsPost);
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
        try {
            RestClient restClient = new RestClient(url_loadPost);
            restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
            restClient.addHeader("token", token);
            restClient.addParam("page", page);
            restClient.addParam("account_id", account_id);
            restClient.execute(RequestMethod.POST);
            if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS && restClient.getResponse() != null) {
                String jsonObject = restClient.getResponse();
                LuuTruParse luuTruParse = new Gson().fromJson(jsonObject, LuuTruParse.class);
                if (luuTruParse.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS)) {
                    postDetails = luuTruParse.getResults();
                }
            }
        } catch (Exception ex) {

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
        if (postDetails != null) {
            adapter = new LoadPostAdapter(frmLuuTru.this, R.layout.activity_load_post_adapter, postDetails);
            lstPost.setAdapter(adapter);
            lstPost.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    LuuTruModel p = postDetails.get(position);
                    String idPost = p.id;
                    new DetailAsyncTask().execute(idPost);
                }
            });
            lstPost.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    LayoutInflater li = LayoutInflater.from(frmLuuTru.this.getParent());
                    View promptsView = li.inflate(R.layout.popup_deletepost, null);
                    android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(frmLuuTru.this.getParent());
                    alertDialogBuilder.setView(promptsView);
                    ImageButton imgDelete = (ImageButton) promptsView.findViewById(R.id.ibDetele);
                    final TextView textView = (TextView) promptsView.findViewById(R.id.tvDelete);
                    // set dialog message
                    alertDialogBuilder.setView(promptsView);
                    // set dialog message
                    alertDialogBuilder.setCancelable(false);
                    final android.support.v7.app.AlertDialog show = alertDialogBuilder.show();
                    imgDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            LuuTruModel p = postDetails.get(position);
                            String idPost = p.id;
                            String status = p.status;
                            if(!status.equals("1")) {
                                new DeteleAsyncTask().execute(idPost);
                            }
                            show.dismiss();
                        }
                    });
                    return true;
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
        setData();
        adapter = new LoadPostAdapter(frmLuuTru.this, R.layout.activity_load_post_adapter, postDetails);

    }

    /*private class DetailAsyncTask extends AsyncTask<String, Void, NotificationDetail> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(frmLuuTru.this.getParent(),"IT Support", "Loading...", true);
            progress.getWindow().setGravity(Gravity.CENTER_VERTICAL);
        }

        @Override
        protected NotificationDetail doInBackground(String... params) {
            String idPost = params[0];
            try {
                SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(frmLuuTru.this);
                String token = sharedPreference.getString("token", "token");
                RestClient restClient = new RestClient(url_postDetail);
                restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
                restClient.addHeader("token", token);
                restClient.addParam("id", idPost);
                restClient.execute(RequestMethod.POST);
                if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS) {
                    String jsonObject = restClient.getResponse();
                    Gson gson = new Gson();
                    NotificationParse getLoginJson = gson.fromJson(jsonObject, NotificationParse.class);
                    if (getLoginJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS)) {
                        uD = getLoginJson.getResults();
                    }
                }
            } catch (Exception ex) {

            }
            return uD;
        }

        @Override
        protected void onPostExecute(NotificationDetail notificationDetail) {
            super.onPostExecute(notificationDetail);
            *//*infortion user*//*
            String name = uD.getPost_type().getName();
            String Location_name = uD.getLocation_name();
            String content = uD.getContent();
            String created_at = uD.getCreated_at();
            String picked_at = uD.getPicked_at();
            String processing = uD.getProcessing();
            String completed_at = uD.getCompleted_at();

            *//*infortion provider*//*
            String full_name = uD.getNormal_account().getFull_name();
            String phone_number = uD.getNormal_account().getPhone_number();
            String address = uD.getNormal_account().getAddress();

            *//*infortion user*//*
            Intent intent = new Intent(frmLuuTru.this, frmChiTietPost.class);
            intent.putExtra("loaibaohong", name);
            intent.putExtra("diachi", Location_name);
            intent.putExtra("ghichu", content);
            intent.putExtra("hoten", full_name);
            intent.putExtra("dienthoai", phone_number);
            intent.putExtra("diachinha", address);
            *//*infortion provider*//*
            String status = uD.getStatus();
            if (status.equals("1")) {
                //String picked_at = uD.getPicked_at();
                String full_name_p = uD.getProvider_account().getFull_name();
                String phone_number_p = uD.getProvider_account().getPhone_number();
                intent.putExtra("updated_at", picked_at);
                intent.putExtra("full_name", full_name_p);
                intent.putExtra("phone_number", phone_number_p);
                if(processing.equals("1"))
                {
                    intent.putExtra("processing", processing);
                }
            }
            if (status.equals("2")) {
                //String picked_at = uD.getPicked_at();
                String full_name_p = uD.getProvider_account().getFull_name();
                String phone_number_p = uD.getProvider_account().getPhone_number();
                intent.putExtra("completed_at", completed_at);
                intent.putExtra("updated_at", picked_at);
                intent.putExtra("full_name", full_name_p);
                intent.putExtra("phone_number", phone_number_p);
                intent.putExtra("created_at", created_at);
            }
            progress.dismiss();
            startActivity(intent);
        }
    }*/

    private class DetailAsyncTask extends AsyncTask<String, Void, String>
    {
        private String jsonObject;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(frmLuuTru.this.getParent(),"IT Support", "Loading...", true);
            progress.getWindow().setGravity(Gravity.CENTER_VERTICAL);
        }

        @Override
        protected String doInBackground(String... params) {
            String idPost = params[0];
            try {
                SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(frmLuuTru.this);
                String token = sharedPreference.getString("token", "token");
                RestClient restClient = new RestClient(url_postDetail);
                restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
                restClient.addHeader("token", token);
                restClient.addParam("id", idPost);
                restClient.execute(RequestMethod.POST);
                if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS) {
                    jsonObject = restClient.getResponse();
                }
                else
                {
                    return "";
                }

            }catch (Exception ex)
            {
                Log.e(ex.toString(),"Lỗi");
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(!s.isEmpty())
            {
                Intent intent = new Intent(frmLuuTru.this, frmChiTietPost.class);
                intent.putExtra("JsonObject",s);
                progress.dismiss();
                startActivity(intent);
            }
            else
            {

            }
        }
    }

    private class DeteleAsyncTask extends AsyncTask<String,Void,Boolean>
    {
        @Override
        protected Boolean doInBackground(String... params) {
            try {
                SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(frmLuuTru.this);
                String token = sharedPreference.getString("token", "token");
                String account_id = sharedPreference.getString("id", "account");
                String idpost = params[0];
                RestClient restClient = new RestClient(url_delete);
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
                getData();
                setData();
                adapter.notifyDataSetChanged();
            }
        }
    }

}
