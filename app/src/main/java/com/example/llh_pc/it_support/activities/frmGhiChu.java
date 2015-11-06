package com.example.llh_pc.it_support.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.models.JsonParses.PostParse;
import com.example.llh_pc.it_support.restclients.RequestMethod;
import com.example.llh_pc.it_support.restclients.Response;
import com.example.llh_pc.it_support.restclients.RestClient;
import com.example.llh_pc.it_support.utils.Interfaces.Def;
import com.example.llh_pc.it_support.utils.Location.GPSTracker;
import com.google.gson.Gson;

public class frmGhiChu extends AppCompatActivity {
    public static final String url_get_my_notifications = Def.API_BASE_LINK + Def.API_CREATESubPost + Def.API_FORMAT_JSON;
    private GPSTracker gpsTracker;
    private boolean isGPS;
    private ProgressDialog progressDialog;
    private EditText editGhiChu;
    private String str_GhiChu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gpsTracker = new GPSTracker(frmGhiChu.this);
        setContentView(R.layout.activity_frm_ghi_chu);
        /*final EditText editGhiChu = (EditText)findViewById(R.id.edtGhiChu);*/
        Button btnOK = (Button) findViewById(R.id.btnCreatePost);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editGhiChu = (EditText) findViewById(R.id.edtGhiChu);
                SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(frmGhiChu.this);
                String token = sharedPreference.getString("token", "YourName");
                String type_id = sharedPreference.getString("type_id", "YourName");
                String content = editGhiChu.getText().toString();
                final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    buildAlertMessageNoGps();
                } else {
                    String Longitude = String.valueOf(gpsTracker.getLongitude());
                    String Latitude = String.valueOf(gpsTracker.getLatitude());
                    new NoteAsyncTack().execute(token, type_id, content, Longitude, Latitude);
                }
            }
        });
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void buildAlertMessageNoGps() {
        LayoutInflater li = LayoutInflater.from(frmGhiChu.this);
        View promptsView = li.inflate(R.layout.popup_note, null);
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsView);
        final TextView textView = (TextView) promptsView.findViewById(R.id.tvValidation);
        // set dialog message
        alertDialogBuilder.setView(promptsView);
        // set dialog message
        alertDialogBuilder.setCancelable(false);
        final android.support.v7.app.AlertDialog show = alertDialogBuilder.show();
        Button okpopup = (Button) promptsView.findViewById(R.id.okpopup);
        okpopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                Toast.makeText(frmGhiChu.this, "Mở GPS.", Toast.LENGTH_LONG).show();
                show.dismiss();
            }
        });
        Button huypopup = (Button) promptsView.findViewById(R.id.huypopup);
        huypopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show.dismiss();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private class NoteAsyncTack extends AsyncTask<String, Void, String> {
        private String jsonObject;

        /*function
                * 1: GPS
                * 2: Call api
                * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(frmGhiChu.this, "IT Support", "Loading...");
        }
        public boolean isNetworkAvailable() {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                Boolean checkIntert = isNetworkAvailable();
                if(checkIntert) {
                    String token = params[0];
                    String type_id = params[1];
                    String content = params[2];
                    String Longitude = params[3];
                    String Latitude = params[4];
                    RestClient restClient = new RestClient(url_get_my_notifications);
                    if (Float.valueOf(Longitude) != 0.0 || Float.valueOf(Latitude) != 0.0) {
                        //--------------------server---------------\\
                        restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
                        restClient.addHeader("token", token);
                        restClient.addParam("type_id", type_id);
                        restClient.addParam("location_lat",Latitude );
                        restClient.addParam("location_lng",Longitude);
                        restClient.addParam("content", content);
                        restClient.execute(RequestMethod.POST);
                        if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS) {
                            jsonObject = restClient.getResponse();
                            Gson gson = new Gson();
                            PostParse getListPostJson = gson.fromJson(jsonObject, PostParse.class);
                            if (getListPostJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS)) {
                                return "1";

                            } else {
                                return "2";
                            }
                        }
                    }
                }

                else
                {
                    return "2";
                }

            } catch (Exception ex) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals("1")) {
                progressDialog.dismiss();
                Intent intent = new Intent(frmGhiChu.this, frmTabHost.class);
                startActivity(intent);
                frmTabHost.x = 3;
            } else {
                progressDialog.dismiss();
                Toast.makeText(frmGhiChu.this, "không có kết nối internet.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(frmGhiChu.this, frmDK_DN.class);
                startActivity(intent);
            }
        }
    }
}
