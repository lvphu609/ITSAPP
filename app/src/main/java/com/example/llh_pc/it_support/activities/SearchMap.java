package com.example.llh_pc.it_support.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.adapters.LoadPostAdapter;
import com.example.llh_pc.it_support.models.JsonParses.PostDetailParse;
import com.example.llh_pc.it_support.models.JsonParses.SearchMapModel;
import com.example.llh_pc.it_support.models.LuuTruModel;
import com.example.llh_pc.it_support.models.ModelLatLng;
import com.example.llh_pc.it_support.models.UserPostDetail;
import com.example.llh_pc.it_support.restclients.RequestMethod;
import com.example.llh_pc.it_support.restclients.Response;
import com.example.llh_pc.it_support.restclients.RestClient;
import com.example.llh_pc.it_support.utils.Events.eventDetailPickPost;
import com.example.llh_pc.it_support.utils.Events.eventDetailPost;
import com.example.llh_pc.it_support.utils.Interfaces.Def;
import com.example.llh_pc.it_support.utils.Location.GPSTracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SearchMap extends AppCompatActivity {
    private UserPostDetail uD;
    Marker markerT;
    String idPost;
    Double x;
    LuuTruModel m;
    Double y;
    ListView list;
    String page1 = "1";
    String lang, lng,id1;
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
    String lat,log;
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
         lat = String.valueOf(x);
         log = String.valueOf(y);
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

//        try {
//            RestClient restClient = new RestClient(url_search);
//            restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
//            restClient.addHeader("token", token);
//            restClient.addParam("page", "1");
//            restClient.addParam("account_id", account_id);
//            restClient.addParam("location_lat", lat);
//            restClient.addParam("location_lng", log);
//            restClient.addParam("query", query);
//            restClient.execute(RequestMethod.POST);
//            if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS &&
//                    restClient.getResponse() != null) {
//                String jsonObject = restClient.getResponse();
//                SearchMapModel searchParse = new Gson().fromJson(jsonObject, SearchMapModel.class);
//
//                if (searchParse.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS)) {
//                    postDetails = searchParse.getResults();
//                    for (int i = 0; i < postDetails.size(); i++) {
//                         m = postDetails.get(i);
//                       idPost = m.id;
//                        listid.add(idPost);
//                         lang = m.getLocation_lat();
//                         lng = m.getLocation_lng();
//
//                        Double latpost = Double.valueOf(lang);
//                        Double logpost = Double.valueOf(lng);
//
//
//
//                        marker = new MarkerOptions().position(new LatLng(latpost, logpost)).title(m.name).snippet(m.location_name);
//
//                        //googleMap.addMarker(marker);
//                        markerT = googleMap.addMarker(marker);
//                        hash_posts.put(markerT,idPost);
//
//                        //
////
//                    }
//
//                }
//
//            }
//        } catch (Exception ex) {
//            t = ex.toString();
//        }

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

                new GCMAsyncTask().execute(searchString,lang,lng);

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(editsearch.getText().toString().isEmpty())
                {
                    markerT = googleMap.addMarker(marker);
                    hash_posts.put(markerT,idPost);
                }

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
        new MapSearch().execute(page1, lat, log, token, account_id);
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //marker1= googleMap.addMarker(marker);

                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        String id = hash_posts.get(marker);
//                        click(id);
                       new clickMarker().execute(id);

                        return false;
                    }
                });

                return false;
            }
        });

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

    private class GCMAsyncTask extends AsyncTask<String, Void, ArrayList<LuuTruModel>> {
        ArrayList<LuuTruModel> list = new ArrayList<>();
        ModelLatLng k = new ModelLatLng();
        @Override
        protected ArrayList<LuuTruModel> doInBackground(String... params) {
            String query1 = params[0];
            String x1 = params[1];
            String y1 = params[2];

            try {

                RestClient restClient = new RestClient(url_search);
                restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
                restClient.addHeader("token", token);
//            restClient.addParam("page","1");
                restClient.addParam("account_id", account_id);
                restClient.addParam("location_lat",x1);
                restClient.addParam("location_lng",y1);
                restClient.addParam("query",query1);
                restClient.execute(RequestMethod.POST);
                if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS &&
                        restClient.getResponse() != null)
                {
                    String jsonObject = restClient.getResponse();
                    SearchMapModel searchParse = new Gson().fromJson(jsonObject, SearchMapModel.class);

                    if(searchParse.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS)) {
                        searchParse.getResults();

//
                        postDetails = searchParse.getResults();

                        for (int i = 0; i < postDetails.size(); i++) {
                            LuuTruModel m = postDetails.get(i);
                            idPost = m.id;
                            String lang = m.getLocation_lat();
                            String lng = m.getLocation_lng();

                            Double latpost = Double.valueOf(lang);
                            Double logpost = Double.valueOf(lng);


                            /* marker = new MarkerOptions().position(new LatLng(latpost, logpost)).title(m.name).snippet(m.location_name);

                            googleMap.addMarker(marker);
                            markerT = googleMap.addMarker(marker);
                            hash_posts.put(markerT, idPost);*/
                            list.add(m);


                        }
                    }}
            }catch (Exception ex){
                t = ex.toString();
            }
            return  list;
            /*return false;*/
        }

        @Override
           protected void onPostExecute(ArrayList<LuuTruModel> doubles) {
        super.onPostExecute(doubles);
        googleMap.clear();

        for (int i = 0 ; i< list.size();i++) {

            Double a = Double.valueOf(list.get(i).getLocation_lat());
            Double b = Double.valueOf(list.get(i).getLocation_lng());
            String locname = String.valueOf(list.get(i).getLocation_name());
            String name = String.valueOf(list.get(i).getName());
            id1 = String.valueOf(list.get(i).getId());

            marker = new MarkerOptions().position(new LatLng( a,b)).title(name).snippet(locname);

            markerT = googleMap.addMarker(marker);

            hash_posts.put(markerT, id1);

        }



    }
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
//    public void searchAPI(String query){
//        try {
//            gpsTracker = new GPSTracker(SearchMap.this);
//            String x = String.valueOf(gpsTracker.getLatitude());
//            String y = String.valueOf(gpsTracker.getLongitude());
//            RestClient restClient = new RestClient(url_search);
//            restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
//            restClient.addHeader("token", token);
////            restClient.addParam("page","1");
//            restClient.addParam("account_id", account_id);
//            restClient.addParam("location_lat",x);
//            restClient.addParam("location_lng",y);
//            restClient.addParam("query",query);
//            restClient.execute(RequestMethod.POST);
//            if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS &&
//                    restClient.getResponse() != null)
//            {
//                String jsonObject = restClient.getResponse();
//                SearchMapModel searchParse = new Gson().fromJson(jsonObject, SearchMapModel.class);
//
//                if(searchParse.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS)){
//                    searchParse.getResults();
//                    googleMap.clear();
//                    postDetails = searchParse.getResults();
//
//                    for (int i = 0 ; i <postDetails.size(); i++) {
//                         LuuTruModel m = postDetails.get(i);
//                        idPost = m.id;
//                        String lang = m.getLocation_lat();
//                        String lng = m.getLocation_lng();
//
//                        Double latpost = Double.valueOf(lang);
//                        Double logpost = Double.valueOf(lng);
//
//
//
//                        marker = new MarkerOptions().position(new LatLng(latpost, logpost)).title(m.name).snippet(m.location_name);
//
//
//
//                        googleMap.addMarker(marker);
//                        markerT = googleMap.addMarker(marker);
//                        hash_posts.put(markerT, idPost);
//
//                    }
//                }
//            }
//        }catch (Exception ex){
//            t = ex.toString();
//        }}

    private class clickMarker extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String id = params[0];

            try {
                SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(eventDetailPickPost.context);
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
                        Intent intent = new Intent(eventDetailPickPost.context, DetailPickPost.class);
                        intent.putExtra("loaibaohong", loaibaohong);
                        intent.putExtra("diachi", diachi);
                        intent.putExtra("ghichu", ghichu);
                        intent.putExtra("hoten", hoten);
                        intent.putExtra("dienthoai", dienthoai);
                        intent.putExtra("diachinha", diachinha);
                        eventDetailPickPost.context.startActivity(intent);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }
    private class MapSearch extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String page = params[0];
            String x1 = params[1];
            String y1 = params[2];
            String token = params[3];
            String accountid = params[4];

            try {
                RestClient restClient = new RestClient(url_search);
                restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
                restClient.addHeader("token", token);
                restClient.addParam("page", page);
                restClient.addParam("account_id", accountid);
                restClient.addParam("location_lat", x1);
                restClient.addParam("location_lng", y1);
                restClient.addParam("query", query);
                restClient.execute(RequestMethod.POST);
                if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS &&
                        restClient.getResponse() != null) {
                    String jsonObject = restClient.getResponse();
                    SearchMapModel searchParse = new Gson().fromJson(jsonObject, SearchMapModel.class);

                    if (searchParse.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS)) {
                        postDetails = searchParse.getResults();
//                        for (int i = 0; i < postDetails.size(); i++) {
//                            m = postDetails.get(i);
//                            idPost = m.id;
//                            listid.add(idPost);
//                            lang = m.getLocation_lat();
//                            lng = m.getLocation_lng();

                            //
//
//                        }

                    }

                }
            } catch (Exception ex) {
                t = ex.toString();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            for (int i = 0; i < postDetails.size(); i++) {
                m = postDetails.get(i);
                idPost = m.id;
                listid.add(idPost);
                lang = m.getLocation_lat();
                lng = m.getLocation_lng();
                Double latpost = Double.valueOf(lang);
                Double logpost = Double.valueOf(lng);
                marker = new MarkerOptions().position(new LatLng(latpost, logpost)).title(m.name).snippet(m.location_name);
                //googleMap.addMarker(marker);
                markerT = googleMap.addMarker(marker);
                hash_posts.put(markerT, idPost);
            }
        }
    }
}
