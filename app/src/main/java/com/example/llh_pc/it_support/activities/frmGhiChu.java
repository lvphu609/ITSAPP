package com.example.llh_pc.it_support.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_ghi_chu);
        final EditText editGhiChu = (EditText)findViewById(R.id.edtGhiChu);
        Button btnOK = (Button)findViewById(R.id.btnCreatePost);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(frmGhiChu.this);
                String type_id = sharedPreference.getString("type_id", "YourName");
                String token = sharedPreference.getString("token", "YourName");
                gpsTracker = new GPSTracker(frmGhiChu.this);
                String x = String.valueOf(gpsTracker.getLongitude());
                String y = String.valueOf(gpsTracker.getLatitude());
                String edtGhiChu = editGhiChu.getText().toString();
                RestClient restClient = new RestClient(url_get_my_notifications);
                if (Float.valueOf(x) != 0.0 || Float.valueOf(y) != 0.0) {

                    //--------------------server---------------\\

                    restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
                    restClient.addHeader("token", token);
                    restClient.addParam("type_id", type_id);
                    restClient.addParam("location_lat", y);
                    restClient.addParam("location_lng", x);
                    try {
                    restClient.execute(RequestMethod.POST);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS) {
                            String jsonObject = restClient.getResponse();
                            Gson gson = new Gson();
                            PostParse getListPostJson = gson.fromJson(jsonObject, PostParse.class);
                            if (getListPostJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS)) {
                                Intent intent = new Intent(frmGhiChu.this, frmLuuTru.class);
                                frmGhiChu.this.startActivity(intent);
                            }
                        }

                    }
                else {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                   startActivity(intent);
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_frm_ghi_chu, menu);
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
}
