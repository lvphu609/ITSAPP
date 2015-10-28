package com.example.llh_pc.it_support.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class frmGhiChuKhac extends AppCompatActivity {

    public static final String url_get_my_notifications = Def.API_BASE_LINK + Def.API_CREATESubPost + Def.API_FORMAT_JSON;
    private GPSTracker gpsTracker;
    private boolean isGPS;
    private ProgressDialog progressDialog;
    private EditText editGhiChu;
    private String str_GhiChu;
    private String other;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gpsTracker = new GPSTracker(frmGhiChuKhac.this);
        setContentView(R.layout.activity_frm_ghi_chu_khac);
        Bundle bundle = getIntent().getExtras();
        other = bundle.getString("Other");
        Button btnOK = (Button) findViewById(R.id.btnCreatePostOther);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editGhiChu = (EditText) findViewById(R.id.edtGhiChuOther);
                SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(frmGhiChuKhac.this);
                String token = sharedPreference.getString("token", "YourName");
                String content = editGhiChu.getText().toString();
                final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    buildAlertMessageNoGps();
                } else {

                    String Longitude = String.valueOf(gpsTracker.getLongitude());
                    String Latitude = String.valueOf(gpsTracker.getLatitude());;
                    new NoteAsyncTack().execute(token, other, content, Longitude, Latitude);
                }
            }
        });
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    private void checkGPS()
    {
        double set;
        do {
            set = gpsTracker.getLatitude();
        }while (set != 0.0);
    }
    private void buildAlertMessageNoGps() {
        LayoutInflater li = LayoutInflater.from(frmGhiChuKhac.this);
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
                Toast.makeText(frmGhiChuKhac.this, "Mở GPS.", Toast.LENGTH_LONG).show();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_frm_ghi_chu_khac, menu);
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
    private class NoteAsyncTack extends AsyncTask<String, Void, String> {
        private String jsonObject;
        private ProgressDialog progressDialog;

        /*function
                * 1: GPS
                * 2: Call api*/
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(frmGhiChuKhac.this, "IT Support", "Loading...");
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String token = params[0];
                String other = params[1];
                String content = params[2];
                String Longitude = params[3];
                String Latitude = params[4];
                RestClient restClient = new RestClient(url_get_my_notifications);
                if (Float.valueOf(Longitude) != 0.0 || Float.valueOf(Latitude) != 0.0) {
                    //--------------------server---------------\\
                    restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
                    restClient.addHeader("token", token);
                    restClient.addParam("type_id", other);
                    restClient.addParam("location_lat",Latitude);
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
            } catch (Exception ex) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals("1")) {
                Toast.makeText(frmGhiChuKhac.this, "Báo hỏng thành công.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(frmGhiChuKhac.this, frmTabHost.class);
                startActivity(intent);
                frmTabHost.x = 3;
            } else {
                Intent intent = new Intent(frmGhiChuKhac.this, frmDK_DN.class);
                startActivity(intent);
            }
        }
    }
}