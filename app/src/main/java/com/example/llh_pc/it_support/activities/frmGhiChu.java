package com.example.llh_pc.it_support.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_ghi_chu);
        final EditText editGhiChu = (EditText)findViewById(R.id.edtGhiChu);
        Button btnOK = (Button)findViewById(R.id.btnCreatePost);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //function check gps\\
                final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
                if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                    buildAlertMessageNoGps();
                } else
                {
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
                                Toast.makeText(frmGhiChu.this, "Báo hỏng thành công.", Toast.LENGTH_LONG).show();
                                frmGhiChu.this.startActivity(intent);
                            }
                        }

                    }
                }
            }
        });
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    private void buildAlertMessageNoGps() {
        LayoutInflater li = LayoutInflater.from(frmGhiChu.this);
        View promptsView = li.inflate(R.layout.popup_validation, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(frmGhiChu.this);
        alertDialogBuilder.setView(promptsView);
        final TextView textView = (TextView) promptsView.findViewById(R.id.tvValidation);
        textView.setText("IT Support cần xác định vị trí của bạn. Bạn có muốn bật GPS");
        // set dialog message
        alertDialogBuilder
                .setTitle("IT Support")
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                Toast.makeText(frmGhiChu.this, "Mở GPS.", Toast.LENGTH_LONG).show();
                            }
                        });
        alertDialogBuilder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();






        /*LayoutInflater li = LayoutInflater.from(frmGhiChu.this);
        View promptsView = li.inflate(R.layout.popup_validation, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("?")
                .setCancelable(false)
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        Toast.makeText(frmGhiChu.this, "Mở GPS.", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Toast.makeText(frmGhiChu.this, "Không mở GPS", Toast.LENGTH_LONG).show();
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
 */   }

}
