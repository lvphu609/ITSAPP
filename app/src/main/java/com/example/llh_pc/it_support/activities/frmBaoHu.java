package com.example.llh_pc.it_support.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.adapters.PostAdapter;
import com.example.llh_pc.it_support.models.JsonParses.LoginParse;
import com.example.llh_pc.it_support.models.JsonParses.PostParse;
import com.example.llh_pc.it_support.models.Post;
import com.example.llh_pc.it_support.restclients.RequestMethod;
import com.example.llh_pc.it_support.restclients.Response;
import com.example.llh_pc.it_support.restclients.RestClient;
import com.example.llh_pc.it_support.utils.Events.eventListPostTitle;
import com.example.llh_pc.it_support.utils.Interfaces.Def;
import com.example.llh_pc.it_support.utils.Interfaces.InnoFunctionListener;
import com.google.gson.Gson;

import java.util.ArrayList;

import static android.content.DialogInterface.*;

public class frmBaoHu extends AppCompatActivity implements InnoFunctionListener {
    public static final String url_get_my_notifications = Def.API_BASE_LINK + Def.API_PostTile + Def.API_FORMAT_JSON;
    //progfile
    private boolean is_network = false;
    private ArrayList<Post> array_post = new ArrayList<>();
    private PostAdapter adapter;
    private ListView lstPost;
    private String url_logout = Def.API_BASE_LINK + Def.API_Logout + Def.API_FORMAT_JSON;
    String[] menu;
    DrawerLayout dLayout;
    ListView dList;
    ArrayAdapter<String> adapter1;
    private OnClickListener dialogClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_bao_hu);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(frmBaoHu.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("staylogin",1);
        editor.commit();

<<<<<<< HEAD
        String text;
        menu = new String[]{"Thông tin tài khoản","Android","Windows","Linux","Raspberry Pi","WordPress","Videos","Đăng xuất"};
=======
        menu = new String[]{"NAME","Android","Windows","Linux","Raspberry Pi","WordPress","Videos","Đăng xuất"};
>>>>>>> 5a40d655cf08f5623b3360701b466fa799c3fe16
        dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        dList = (ListView) findViewById(R.id.left_drawer);
        adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,menu);
        dList.setAdapter(adapter1);
        dList.setSelector(android.R.color.holo_blue_dark);
        dList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
                if (position == 7) {
                    try {
                        SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(frmBaoHu.this);
                        String token = sharedPreference.getString("token", "token");
                        RestClient restClient = new RestClient(url_logout);
                        restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
                        restClient.addHeader("token", token);
                        restClient.execute(RequestMethod.POST);
                        if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS) {
                            String jsonObject = restClient.getResponse();
                            Gson gson = new Gson();
                            LoginParse getLoginJson = gson.fromJson(jsonObject, LoginParse.class);
                            //if result from response success
                            if (getLoginJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS)) {
                                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(frmBaoHu.this);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putInt("check", 1);
                                editor.putString("token", "Hai");
                                editor.putInt("staylogin", 0);
                                editor.commit();
                                Intent intent = new Intent(frmBaoHu.this, frmDK_DN.class);
                                startActivity(intent);
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }                if (position == 0) {
                    Intent profile = new Intent(frmBaoHu.this, Profile.class);
                    startActivity(profile);
                }
            }
        });
        try
        {
            RestClient restClient = new RestClient(url_get_my_notifications);
            restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
            restClient.execute(RequestMethod.GET);
            if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS)
            {
                String jsonObject = restClient.getResponse();
                Gson gson = new Gson();
                PostParse getListPostJson = gson.fromJson(jsonObject, PostParse.class);
                if(getListPostJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS))
                {
                    array_post = getListPostJson.getResults();
                    adapter = new PostAdapter(frmBaoHu.this, R.layout.activity_post_adapter, array_post);
                }
            }
        }catch (Exception ex){}
        initFlags();
        initControl();
        setEventForControl();
        getData();
    }

    @Override
    public void onBackPressed() {
        //final Intent intent = new Intent(this, frmDK_DN.class);
        AlertDialog.Builder builder = new AlertDialog.Builder(frmBaoHu.this);
        new AlertDialog.Builder(frmBaoHu.this)
                .setMessage("Thoát app?")
                .setCancelable(false)
                .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Intent a = new Intent(Intent.ACTION_MAIN);
                        a.addCategory(Intent.CATEGORY_HOME);
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(a);

                        /*Intent intent=new Intent(frmBaoHu.this,MainActivity.class);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();*/


                        /*Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();*/




                        /*Intent a = new Intent(Intent.ACTION_MAIN);
                        a.addCategory(Intent.CATEGORY_HOME);
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(a);*/
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // Perform Your Task Here--When No is pressed
                        dialog.cancel();
                    }
                }).show();
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
        lstPost.setOnItemClickListener(new eventListPostTitle(frmBaoHu.this, array_post));
    }

    @Override
    public void getData(String... params) {

    }

    @Override
    public void setData() {

    }
}
