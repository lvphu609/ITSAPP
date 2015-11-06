package com.example.llh_pc.it_support.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.sax.Element;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.models.JsonParses.PostDetailParse;
import com.example.llh_pc.it_support.models.LuuTruModel;
import com.example.llh_pc.it_support.models.UserPostDetail;
import com.example.llh_pc.it_support.restclients.RequestMethod;
import com.example.llh_pc.it_support.restclients.Response;
import com.example.llh_pc.it_support.restclients.RestClient;
import com.example.llh_pc.it_support.utils.Events.eventDetailPickPost;
import com.example.llh_pc.it_support.utils.Interfaces.Def;
import com.example.llh_pc.it_support.utils.Location.GPSTracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by ZBOOK 15 on 11/3/2015.
 */
public class ChiDuong  extends AppCompatActivity{
    private UserPostDetail uD;
    public static final String url_get = Def.API_BASE_LINK + Def.API_Loadpostdetail + Def.API_FORMAT_JSON;
    public static String idPostCD;
    Double latpost,logpost;
    String ID_PickPost,name1,locname;
    private GoogleMap googleMap;
    private String token, account_id;
    private HashMap<Marker,String> hash_posts = new HashMap<>();
    private HashMap<eventDetailPickPost,String> hash_posts1 = new HashMap<>();

   public static int K ;
    Marker markerT;
    Double x;
    LuuTruModel m;
    Double y;
    String lang, lng,id1;
    String lat,log;
    private GPSTracker gpsTracker;
    MarkerOptions marker,markerB;
    GMapV2Direction md;
    PolylineOptions lineOption;
    PolylineOptions polylines;
    Polyline line;
    private final ArrayList<LatLng> lstLatLng = new ArrayList<LatLng>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        md = new GMapV2Direction();
        gpsTracker = new GPSTracker(ChiDuong.this);
        y = Double.valueOf(gpsTracker.getLongitude());
        x = Double.valueOf(gpsTracker.getLatitude());
        lat = String.valueOf(x);
        log = String.valueOf(y);


       //



        setContentView(R.layout.activity_search_map);
        try {
            // Loading map
            initilizeMap();

        } catch (Exception e) {
            e.printStackTrace();
        }

        SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(ChiDuong.this);
        token = sharedPreference.getString("token", "YourName");
        account_id = sharedPreference.getString("id", "YourName");
        if (K == 1) {
            new MarkerTarget().execute(SearchMap.id2);
        }
        if(K ==2) {
            new MarkerTarget().execute(eventDetailPickPost.ID_PickPost);
        }


        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void onBackPressed() {
        final Intent myIntent = new Intent(ChiDuong.this, frmTabHost.class);
        frmTabHost.x = 2;
        startActivityForResult(myIntent, 2);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), frmTabHost.class);
        startActivityForResult(myIntent, 2);


        return true;
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
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }

    private class MarkerTarget extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String id = params[0];
            try {
                String url = "http://maps.googleapis.com/maps/api/directions/xml?";




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
                         ID_PickPost = uD.getId();
                         name1 = uD.getPost_type().getName();
                         locname= uD.getLocation_name();
                        lang = uD.getLocation_lat();
                        lng = uD.getLocation_lng();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null ;
        }

        private ArrayList<LatLng> decodePolylines(final String encodedPoints) { int index = 0; int lat = 0, lng = 0;
            while (index < encodedPoints.length())
        { int b, shift = 0, result = 0; do { b = encodedPoints.charAt(index++) - 63; result |= (b & 0x1f) << shift; shift += 5; }
        while (b >= 0x20); int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1)); lat += dlat; shift = 0; result = 0;
            do { b = encodedPoints.charAt(index++) - 63; result |= (b & 0x1f) << shift; shift += 5; } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng; lstLatLng.add(new LatLng((double)lat/1E5, (double)lng/1E5)); }
            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            lstLatLng.add(new LatLng(x, y));

            CameraPosition cameraPosition = new CameraPosition.Builder().target(
                    new LatLng(x, y)).zoom(15).build();
            marker = new MarkerOptions().position(lstLatLng.get(0)).title("It me ").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
//            googleMap.addMarker(marker);
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));





//            idPostCD =SearchMap.id2;

             latpost = Double.valueOf(lang);
             logpost = Double.valueOf(lng);
            lstLatLng.add(new LatLng(latpost, logpost));
            markerB = new MarkerOptions().position(lstLatLng.get(lstLatLng.size()-1)).title(name1).snippet(locname);



//            googleMap.addMarker(markerB);
            markerT = googleMap.addMarker(markerB);
            hash_posts.put(markerT, idPostCD);

             polylines = new PolylineOptions(); polylines.color(Color.BLUE);

            for(final LatLng latLng : lstLatLng) { polylines.add(latLng); }
//            googleMap.addPolyline(polylines);

            check();
//            md.setParams(lstLatLng.get(0),lstLatLng.get(lstLatLng.size()-1),1);


        }

    }


 public  void check ()
 {
     String urlTopass = makeURL(x,
             y,latpost,
             logpost);
     new connectAsyncTask(urlTopass).execute();


 }
    private class connectAsyncTask extends AsyncTask<Void, Void, String> {
        private ProgressDialog progressDialog;
        String url;

        connectAsyncTask(String urlPass) {
            url = urlPass;
        }
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = new ProgressDialog(ChiDuong.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            JSONParser jParser = new JSONParser();
            String json = jParser.getJSONFromUrl(url);
            return json;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.hide();
            if (result != null) {
                drawPath(result);
            }
        }
    }

    public String makeURL(double sourcelat, double sourcelog, double destlat,
                          double destlog) {
        StringBuilder urlString = new StringBuilder();
        urlString.append("http://maps.googleapis.com/maps/api/directions/json");
        urlString.append("?origin=");// from
        urlString.append(Double.toString(sourcelat));
        urlString.append(",");
        urlString.append(Double.toString(sourcelog));
        urlString.append("&destination=");// to
        urlString.append(Double.toString(destlat));
        urlString.append(",");
        urlString.append(Double.toString(destlog));
        urlString.append("&sensor=false&mode=driving&alternatives=true");
        return urlString.toString();
    }

    public class JSONParser {

        InputStream is = null;
        JSONObject jObj = null;
        String json = "";

        // constructor
        public JSONParser() {
        }

        public String getJSONFromUrl(String url) {

            // Making HTTP request
            try {
                // defaultHttpClient
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }

                json = sb.toString();
                is.close();
            } catch (Exception e) {
                Log.e("Buffer Error", "Error converting result " + e.toString());
            }
            return json;

        }
    }

    public void drawPath(String result) {
        if (line != null) {
            googleMap.clear();
        }
        googleMap.addMarker(marker);

        googleMap.addMarker(markerB);

        try {
            // Tranform the string into a json object
            final JSONObject json = new JSONObject(result);
            JSONArray routeArray = json.getJSONArray("routes");
            JSONObject routes = routeArray.getJSONObject(0);
            JSONObject overviewPolylines = routes
                    .getJSONObject("overview_polyline");
            String encodedString = overviewPolylines.getString("points");
            List<LatLng> list = decodePoly(encodedString);

            for (int z = 0; z < list.size() - 1; z++) {
                LatLng src = list.get(z);
                LatLng dest = list.get(z + 1);
                line = googleMap.addPolyline(new PolylineOptions()
                        .add(new LatLng(src.latitude, src.longitude),
                                new LatLng(dest.latitude, dest.longitude))
                        .width(5).color(Color.BLUE).geodesic(true));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }


}
