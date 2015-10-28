package com.example.llh_pc.it_support.activities;

import android.content.SharedPreferences;
import android.graphics.PorterDuff;
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
import android.widget.SearchView;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.adapters.ChildPostAdapter;
import com.example.llh_pc.it_support.adapters.LoadPostAdapter;
import com.example.llh_pc.it_support.models.JsonParses.SearchParse;
import com.example.llh_pc.it_support.models.LuuTruModel;
import com.example.llh_pc.it_support.restclients.RequestMethod;
import com.example.llh_pc.it_support.restclients.Response;
import com.example.llh_pc.it_support.restclients.RestClient;
import com.example.llh_pc.it_support.utils.Events.eventDelete;
import com.example.llh_pc.it_support.utils.Events.eventDetailPost;
import com.example.llh_pc.it_support.utils.Interfaces.Def;
import com.example.llh_pc.it_support.utils.Interfaces.InnoFunctionListener;
import com.example.llh_pc.it_support.utils.Location.GPSTracker;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class Search extends AppCompatActivity implements SearchView.OnQueryTextListener,InnoFunctionListener{
     ListView list;
     private LoadPostAdapter adapter;
    //Search List
    private ListView mSearchNFilterLv;
    EditText editsearch;
    private EditText mSearchEdt;
    private int page =1;
    private String query;
    String t;
    public static final String url_search= Def.API_BASE_LINK + Def.API_Search + Def.API_FORMAT_JSON;
    public ArrayList<LuuTruModel> searchPost;
    private ChildPostAdapter valueAdapter;
    private String token,account_id;
    private String lat="10.7632";
    private String lng = "106.675";
    private TextWatcher mSearchTw;
   private ArrayList<LuuTruModel> postDetails;
    public String name;
    private GPSTracker gpsTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(Search.this);
        token = sharedPreference.getString("token", "YourName");
        account_id = sharedPreference.getString("id", "YourName");
        list = (ListView) findViewById(R.id.list_view);

        try
        {
            RestClient restClient = new RestClient(url_search);
            restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
            restClient.addHeader("token", token);
            restClient.addParam("page","1");
            restClient.addParam("account_id", account_id);
            restClient.addParam("location_lat",lat);
            restClient.addParam("location_lng",lng);
            restClient.addParam("query",query);
            restClient.execute(RequestMethod.POST);
            if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS &&
                    restClient.getResponse() != null)
            {
                String jsonObject = restClient.getResponse();
                SearchParse searchParse = new Gson().fromJson(jsonObject, SearchParse.class);

                if(searchParse.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS)){
                    searchParse.getResults();
                    postDetails = new ArrayList<LuuTruModel>(Arrays.<LuuTruModel>asList(searchParse.getResults()));

                }
            }
        }catch (Exception ex){
            t = ex.toString();
        }

        adapter = new LoadPostAdapter(Search.this, R.layout.list_items, postDetails);
        list.setAdapter(adapter);
        editsearch = (EditText) findViewById(R.id.inputSearch);
        editsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchString=editsearch.getText().toString();
                adapter.getFilter().filter(searchString);
                searchAPI(searchString);
                adapter = new LoadPostAdapter(Search.this, R.layout.list_items, postDetails);
                list.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        setEventForControl();
    }

    @Override
    public void initFlags() {

    }

    @Override
    public void initControl() {

    }

    @Override
    public void setEventForControl() {

       list.setOnItemClickListener(new eventDetailPost(Search.this,postDetails));

    }

    @Override
    public void getData(String... params) {

    }

    @Override
    public void setData() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

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


    @Override
    public boolean onQueryTextSubmit(String query) {
    return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {


        return false;
    }

    public void searchAPI(String query){
        try {
        gpsTracker = new GPSTracker(Search.this);
        String x = String.valueOf(gpsTracker.getLatitude());
        String y = String.valueOf(gpsTracker.getLongitude());
        RestClient restClient = new RestClient(url_search);
        restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
        restClient.addHeader("token", token);
        restClient.addParam("page","1");
        restClient.addParam("account_id", account_id);
        restClient.addParam("location_lat",x);
        restClient.addParam("location_lng",y);
        restClient.addParam("query",query);
        restClient.execute(RequestMethod.POST);
        if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS &&
                restClient.getResponse() != null)
        {
            String jsonObject = restClient.getResponse();
            SearchParse searchParse1 = new Gson().fromJson(jsonObject, SearchParse.class);

            if(searchParse1.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS)){
                searchParse1.getResults();
                postDetails = new ArrayList<LuuTruModel>(Arrays.<LuuTruModel>asList(searchParse1.getResults()));

            }
        }
    }catch (Exception ex){
        t = ex.toString();
    }}
}
