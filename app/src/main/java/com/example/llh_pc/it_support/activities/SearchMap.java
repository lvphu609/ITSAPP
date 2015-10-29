package com.example.llh_pc.it_support.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.adapters.LoadPostAdapter;
import com.example.llh_pc.it_support.models.JsonParses.LuuTruParse;
import com.example.llh_pc.it_support.models.JsonParses.PostDetailParse;
import com.example.llh_pc.it_support.models.JsonParses.SearchMapModel;
import com.example.llh_pc.it_support.models.JsonParses.SearchParse;
import com.example.llh_pc.it_support.models.LuuTruModel;
import com.example.llh_pc.it_support.models.MapModel;
import com.example.llh_pc.it_support.models.UserPostDetail;
import com.example.llh_pc.it_support.restclients.RequestMethod;
import com.example.llh_pc.it_support.restclients.Response;
import com.example.llh_pc.it_support.restclients.RestClient;
import com.example.llh_pc.it_support.utils.Events.eventDetailPost;
import com.example.llh_pc.it_support.utils.Interfaces.Def;
import com.example.llh_pc.it_support.utils.Interfaces.InnoFunctionListener;
import com.example.llh_pc.it_support.utils.Location.GPSTracker;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class SearchMap extends AppCompatActivity {
    private UserPostDetail uD;
    Marker markerT;
    String idPost;
    Double x;
    LuuTruModel m;
    Double y;
    ListView list;
    EditText editsearch;
    private LoadPostAdapter adapter;
    public static final String url_search = Def.API_BASE_LINK + Def.API_Search + Def.API_FORMAT_JSON;
    public static final String url_get = Def.API_BASE_LINK + Def.API_Loadpostdetail + Def.API_FORMAT_JSON;
    private GPSTracker gpsTracker;
    static final LatLng TutorialsPoint = new LatLng(21, 57);
    private GoogleMap googleMap;
    private String token, account_id;
    String t;
    MarkerOptions marker;
    private String query;
    public static  ArrayList<LuuTruModel> postDetails;
    private ArrayList<String> listid = new ArrayList<>();

    private HashMap<Marker,String> hash_posts = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gpsTracker = new GPSTracker(SearchMap.this);
        y = Double.valueOf(gpsTracker.getLongitude());
        x = Double.valueOf(gpsTracker.getLatitude());
        String lat = String.valueOf(x);
        String log = String.valueOf(y);
        setContentView(R.layout.activity_search_map);
        try {
            // Loading map
            initilizeMap();

        } catch (Exception e) {
            e.printStackTrace();
        }

        SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(SearchMap.this);
        token = sharedPreference.getString("token", "YourName");
        account_id = sharedPreference.getString("id", "YourName");

        try {
            RestClient restClient = new RestClient(url_search);
            restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
            restClient.addHeader("token", token);
            restClient.addParam("account_id", account_id);
            restClient.addParam("location_lat", lat);
            restClient.addParam("location_lng", log);
            restClient.addParam("query", query);
            restClient.execute(RequestMethod.POST);
            if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS &&
                    restClient.getResponse() != null) {
                String jsonObject = restClient.getResponse();
                SearchMapModel searchParse = new Gson().fromJson(jsonObject, SearchMapModel.class);

                if (searchParse.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS)) {
                    postDetails = searchParse.getResults();
                    for (int i = 0; i < postDetails.size(); i++) {
                         m = postDetails.get(i);
                       idPost = m.id;
                        listid.add(idPost);
                        String lang = m.getLocation_lat();
                        String lng = m.getLocation_lng();

                        Double latpost = Double.valueOf(lang);
                        Double logpost = Double.valueOf(lng);



                        marker = new MarkerOptions().position(new LatLng(latpost, logpost)).title(m.name).snippet(m.location_name);

                        //googleMap.addMarker(marker);
                        markerT = googleMap.addMarker(marker);
                        hash_posts.put(markerT,idPost);

                        //


//
                    }

                }

            }
        } catch (Exception ex) {
            t = ex.toString();
        }

        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                new LatLng(x, y)).zoom(15).build();
        marker = new MarkerOptions().position(new LatLng(x, y)).title("It me ");

        googleMap.addMarker(marker);

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//seach




        editsearch = (EditText) findViewById(R.id.inputSearch);
        editsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchString = editsearch.getText().toString();
                searchAPI(searchString);


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //marker1= googleMap.addMarker(marker);
                String id = hash_posts.get(marker);
                Toast.makeText(SearchMap.this, "Id post : " + id, Toast.LENGTH_SHORT).show();
                click(id);
                return false;
            }
        });

    }


    /**
     * function to load map. If map is not created it will create it for you
     */


    public void setEventForControl() {



    }
    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        initilizeMap();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_map, menu);
        return true;
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

    public  void click (String id)
    {

            try {
                SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(eventDetailPost.context);
                String token = sharedPreference.getString("token", "token");
                RestClient restClient = new RestClient(url_get);
                restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
                restClient.addHeader("token", token);
                restClient.addParam("id", id);
                restClient.execute(RequestMethod.POST);
                if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS) {
                    String jsonObject = restClient.getResponse();
                    Gson gson = new Gson();
                    PostDetailParse getLoginJson = gson.fromJson(jsonObject, PostDetailParse.class);
                    if (getLoginJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS)) {
                        uD = getLoginJson.getResults();
                        String loaibaohong = uD.post_type.getName();
                        String diachi = uD.location_name;
                        String ghichu = uD.content;
                        String hoten = uD.normal_account.full_name;
                        String dienthoai = uD.normal_account.phone_number;
                        String diachinha = uD.normal_account.getAddress();
                        Intent intent = new Intent(eventDetailPost.context, frmChiTietPost.class);
                        intent.putExtra("loaibaohong", loaibaohong);
                        intent.putExtra("diachi", diachi);
                        intent.putExtra("ghichu", ghichu);
                        intent.putExtra("hoten", hoten);
                        intent.putExtra("dienthoai", dienthoai);
                        intent.putExtra("diachinha", diachinha);
                        eventDetailPost.context.startActivity(intent);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

    }
    public void searchAPI(String query){
        try {
            gpsTracker = new GPSTracker(SearchMap.this);
            String x = String.valueOf(gpsTracker.getLatitude());
            String y = String.valueOf(gpsTracker.getLongitude());
            RestClient restClient = new RestClient(url_search);
            restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
            restClient.addHeader("token", token);
//            restClient.addParam("page","");
            restClient.addParam("account_id", account_id);
            restClient.addParam("location_lat",x);
            restClient.addParam("location_lng",y);
            restClient.addParam("query",query);
            restClient.execute(RequestMethod.POST);
            if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS &&
                    restClient.getResponse() != null)
            {
                String jsonObject = restClient.getResponse();
                SearchMapModel searchParse = new Gson().fromJson(jsonObject, SearchMapModel.class);

                if(searchParse.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS)){
                    searchParse.getResults();
                    googleMap.clear();
                    postDetails = searchParse.getResults();

                    for (int i = 0 ; i <postDetails.size(); i++) {
                         LuuTruModel m = postDetails.get(i);
                        idPost = m.id;
                        String lang = m.getLocation_lat();
                        String lng = m.getLocation_lng();

                        Double latpost = Double.valueOf(lang);
                        Double logpost = Double.valueOf(lng);



                        marker = new MarkerOptions().position(new LatLng(latpost, logpost)).title(m.name).snippet(m.location_name);



                        googleMap.addMarker(marker);
                        markerT = googleMap.addMarker(marker);
                        hash_posts.put(markerT, idPost);

                    }
                }
            }
        }catch (Exception ex){
            t = ex.toString();
        }}


//    @Override
//    public boolean onMarkerClick(Marker marker) {
//        click(idPost);
//        return false;
//    }
}
