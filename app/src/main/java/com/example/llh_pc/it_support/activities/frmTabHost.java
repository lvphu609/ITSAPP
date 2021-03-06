package com.example.llh_pc.it_support.activities;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v4.app.FragmentTabHost;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.llh_pc.it_support.R;


import com.example.llh_pc.it_support.fragments.TabHostHoatDong;
import com.example.llh_pc.it_support.fragments.tabHostTimKiem;
import com.example.llh_pc.it_support.models.JsonParses.AccountParse;
import com.example.llh_pc.it_support.models.JsonParses.LoginParse;
import com.example.llh_pc.it_support.models.JsonParses.PostParse;
import com.example.llh_pc.it_support.models.JsonParses.abc;
import com.example.llh_pc.it_support.restclients.RequestMethod;
import com.example.llh_pc.it_support.restclients.Response;
import com.example.llh_pc.it_support.restclients.RestClient;
import com.example.llh_pc.it_support.utils.Images.ImageLoader;
import com.example.llh_pc.it_support.utils.Interfaces.Def;
import com.example.llh_pc.it_support.utils.Location.GPSTracker;
import com.google.gson.Gson;

import java.util.ArrayList;

public class frmTabHost extends TabActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{
    private DrawerLayout drawerLayout;
    private NavigationView navDrawer;
    private ArrayList<String> arr = new ArrayList<String>();
    private ActionBarDrawerToggle drawerToggle;
    private ActionBarDrawerToggle mDrawerToggle;
    private int selectedItem;
    private String url_logout = Def.API_BASE_LINK + Def.API_Logout + Def.API_FORMAT_JSON;
    public static final String url_gcm = Def.API_BASE_LINK + Def.API_update_gcm + Def.API_FORMAT_JSON;
    private int check;
    private Toolbar toolbar;
    private ImageLoader imageload = new ImageLoader(frmTabHost.this);
    private TextView tvName;
    private String fullname, avatar;
    TextView tv;
    private FragmentTabHost mTabHost;
    FragmentTransaction fragTrac;
    public static int x;
    private SharedPreferences sharedPreference;
    public static final String url_get_account_info_by_id = Def.API_BASE_LINK + Def.API_GET_ACCOUNT_INFO_BY_ID + Def.API_FORMAT_JSON;
    private String full_name;
    private String ts;
    private String id;
    private String token;
    TabHost tabHost;

    private GPSTracker gpsTracker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_tab_host);
        final TabHost tab = (TabHost) findViewById(android.R.id.tabhost);
        sharedPreference = PreferenceManager.getDefaultSharedPreferences(this);
        /*fullname = sharedPreference.getString("fullname", null);
        avatar = sharedPreference.getString("avatar", null);*/
        id = sharedPreference.getString("id",null);
        token = sharedPreference.getString("token",null);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        getAccount(id, token);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(frmTabHost.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("staylogin", 1);
        editor.commit();
        TextView tvView  = (TextView)findViewById(R.id.tvName);
        tvView.setText(fullname);
        tvView.setOnClickListener(this);
        initNavigation(savedInstanceState);
        //final TabHost tab = (TabHost) findViewById(android.R.id.tabhost);
        tab.setup();
        tabHost = getTabHost();
        Resources ressources = getResources();


        Intent intentAndroid = new Intent().setClass(this, frmBaoHu.class);
        TabHost.TabSpec tabSpecAndroid = tabHost
                .newTabSpec("Android")
                .setIndicator("", ressources.getDrawable(R.drawable.ic_baohong))
                .setContent(intentAndroid);

        // Apple tab
        Intent intentApple = new Intent().setClass(this, frmThongBao.class);
        TabHost.TabSpec tabSpecApple = tabHost
                .newTabSpec("Apple")
                .setIndicator("", ressources.getDrawable(R.drawable.ic_thongbao))
                .setContent(intentApple);
        // Windows tab
        Intent intentWindows = new Intent().setClass(this, tabHostTimKiem.class);
        TabHost.TabSpec tabSpecWindows = tabHost
                .newTabSpec("Windows")
                .setIndicator("", ressources.getDrawable(R.drawable.ic_timkiem))
                .setContent(intentWindows);
        // Blackberry tab
        Intent intentBerry = new Intent().setClass(this, TabHostHoatDong.class);
        TabHost.TabSpec tabSpecBerry = tabHost
                .newTabSpec("Berry")
                .setIndicator("", ressources.getDrawable(R.drawable.ic_luutru))
                .setContent(intentBerry);

        //Profile tab


        // add all tabs
        tabHost.addTab(tabSpecAndroid);
        tabHost.addTab(tabSpecApple);
        tabHost.addTab(tabSpecWindows);
        tabHost.addTab(tabSpecBerry);


        //set Windows tab as default (zero based)
        tabHost.setCurrentTab(0);
        tab.getTabWidget().getChildAt(tab.getCurrentTab()).setBackgroundColor(getResources().getColor(R.color.mauxanh));
        tab.setOnTabChangedListener(new
                                            TabHost.OnTabChangeListener() {
                                                public void onTabChanged(String arg0) {
                                                    for (int i = 0; i < tab.getTabWidget().getChildCount(); i++) {
                                                        tab.getTabWidget().getChildAt(i).setBackgroundColor(0x00FF00); //unselected
                                                        tv = (TextView) tab.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
                                                        tv.setTextColor(getResources().getColor(R.color.actionbar_text));
                                                    }

                                                    if (tab.getTabWidget().getChildCount() == 0)
                                                        tab.getTabWidget().getChildAt(tab.getCurrentTab()).setBackgroundColor(getResources().getColor(R.color.mauxanh)); //1st tab selected

                                                    else
                                                        tab.getTabWidget().getChildAt(tab.getCurrentTab()).setBackgroundColor(getResources().getColor(R.color.mauxanh)); //2nd tab selected

                                                    if (tab.getCurrentTab() == 0) {
                                                        toolbar.setTitle("Báo hỏng");
                                                        tab.getTabWidget().getChildAt(tab.getCurrentTab()).setBackgroundColor(getResources().getColor(R.color.mauxanh)); //1st tab selected
                                                    } else if ((tab.getCurrentTab() == 1)) {
                                                        toolbar.setTitle("Thông báo");
                                                        tab.getTabWidget().getChildAt(tab.getCurrentTab()).setBackgroundColor(getResources().getColor(R.color.mauxanh));
                                                    }//2nd tab selected
                                                    else if ((tab.getCurrentTab() == 2)) {
                                                        toolbar.setTitle("Tìm Kiếm");
                                                        tab.getTabWidget().getChildAt(tab.getCurrentTab()).setBackgroundColor(getResources().getColor(R.color.mauxanh));


                                                            for (int i = 0; i < arr.size(); i++) {

                                                            if (arr.get(i).equals("1")) {
                                                                    showPopUp();

                                                            } else {

                                                            }
                                                        }
                                                    }
                                                    else if ((tab.getCurrentTab() == 3))
                                                    {
                                                    } else if ((tab.getCurrentTab() == 3)) {
                                                        toolbar.setTitle("Lưu Trữ");
                                                        tab.getTabWidget().getChildAt(tab.getCurrentTab()).setBackgroundColor(getResources().getColor(R.color.mauxanh));
                                                    }
                                                }
                                            });
    }

    @Override
    protected void onResume() {

        getTabHost().setCurrentTab(x);
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    private void initNavigation(Bundle savedInstanceState) {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navDrawer = (NavigationView) findViewById(R.id.menu_drawer);
        //Set item listenner
        //navDrawer.setNavigationItemSelectedListener(_context);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.hello_world, R.string.hello_world);
        navDrawer.setNavigationItemSelectedListener(this);
        drawerLayout.setDrawerListener(drawerToggle);
        //drawerToggle.syncState();
        //Add toobar
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("Báo hỏng");
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        //setActionBar(toolbar);
        drawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        selectedItem = savedInstanceState == null ? R.id.nav_item_1 : savedInstanceState.getInt("selectedItem");
        /*set text and image*/
        de.hdodenhof.circleimageview.CircleImageView c = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.profile_image);
        imageload.DisplayImage(avatar, c);
        c.setOnClickListener(this);
    }

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
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        selectedItem = menuItem.getItemId();
        switch (selectedItem) {
            case R.id.nav_item_1:
                toolbar.setTitle("Báo hỏng");
                break;
            case R.id.nav_item_2:
                toolbar.setTitle("Thông báo");
                Toast.makeText(this, "Gift is clicked !", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_item_3:
                toolbar.setTitle("Tìm kiếm");
                Toast.makeText(this, "Delete is clicked !", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_item_4:
                toolbar.setTitle("Luu trữ");
                Toast.makeText(this, "Favorite is clicked !", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_item_5:
                logout();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        try {
            SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(frmTabHost.this);
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
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(frmTabHost.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("check", 1);
                    editor.putString("token", "Hai");
                    editor.putInt("staylogin", 0);
                    editor.commit();
                    Intent intent = new Intent(frmTabHost.this, frmDK_DN.class);
                    startActivity(intent);
                }
                else
                {
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(frmTabHost.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("check", 1);
                    editor.putString("token", "Hai");
                    editor.putInt("staylogin", 0);
                    editor.commit();
                    Intent intent = new Intent(frmTabHost.this, frmDK_DN.class);
                    startActivity(intent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        Intent profile1 = new Intent(frmTabHost.this, Profile1.class);
        startActivity(profile1);
    }

    private void getAccount(String id, String token) {
        try {
            RestClient restClient = new RestClient(url_get_account_info_by_id);
            restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
            restClient.addParam("id", id);
            restClient.addHeader("token", token);
            restClient.execute(RequestMethod.POST);
            if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS) {
                String jsonObject = restClient.getResponse();
                Gson gson = new Gson();
                AccountParse getAccountJson = gson.fromJson(jsonObject, AccountParse.class);
                if (getAccountJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS)) {
                    fullname = getAccountJson.getDKresults().getFull_name().toString();
                    avatar = getAccountJson.getDKresults().getAvatar().toString();
                    arr = getAccountJson.getDKresults().getAccount_type();
                }
            }
        } catch (Exception ex) {
            ts = ex.toString();
        }
    }
    public void showPopUp() {
        final android.app.AlertDialog.Builder helpBuilder = new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View checkboxLayout = inflater.inflate(R.layout.popupuser, null);
        helpBuilder.setView(checkboxLayout);
        Button okpopup = (Button) checkboxLayout.findViewById(R.id.okpopup);
        final android.app.AlertDialog show = helpBuilder.show();
        show.setCancelable(false);
        okpopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabHost.setCurrentTab(0);
                show.dismiss();
            }
        });
        show.show();
    }

    private class GCMAsyncTask extends AsyncTask<String, Void, Boolean>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            String token = params[0];
            String location_lat = params[1];
            String location_lng = params[2];
            try {
                RestClient restClient = new RestClient(url_gcm);
                restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
                restClient.addHeader("token", token);
                restClient.addParam("location_lat", location_lat);
                restClient.addParam("location_lng", location_lng);
                restClient.execute(RequestMethod.POST);
                if(restClient.getResponseCode()== Def.RESPONSE_CODE_SUCCESS)
                {
                    String jsonObject = restClient.getResponse();
                    Gson gson = new Gson();
                    abc getJson = gson.fromJson(jsonObject, abc.class);
                    if(getJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS))
                    {
                        return true;
                    }
                    else {
                        return false;
                    }
                }

            }catch (Exception ex)
            {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean)
            {
                String t ="fdsasdfa";
                String t1 ="fdsasdfadfs";
            }
            else
            {
                return;
            }
        }
    }

}
