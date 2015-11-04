package com.example.llh_pc.it_support.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.models.LuuTruModel;
import com.example.llh_pc.it_support.utils.Location.GPSTracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by ZBOOK 15 on 11/3/2015.
 */
public class ChiDuong  extends AppCompatActivity{



    private GoogleMap googleMap;
    private String token, account_id;

    Double x;
    LuuTruModel m;
    Double y;
    String lang, lng,id1;
    String lat,log;
    private GPSTracker gpsTracker;
    MarkerOptions marker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        gpsTracker = new GPSTracker(ChiDuong.this);
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

        SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(ChiDuong.this);
        token = sharedPreference.getString("token", "YourName");
        account_id = sharedPreference.getString("id", "YourName");


        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                new LatLng(x, y)).zoom(15).build();
        marker = new MarkerOptions().position(new LatLng(x, y)).title("It me ");

        googleMap.addMarker(marker);

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
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
}
