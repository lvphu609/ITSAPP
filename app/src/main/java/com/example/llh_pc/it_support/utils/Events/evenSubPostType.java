package com.example.llh_pc.it_support.utils.Events;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.activities.frmGhiChu;
import com.example.llh_pc.it_support.activities.frmLuuTru;
import com.example.llh_pc.it_support.models.JsonParses.PostParse;
import com.example.llh_pc.it_support.models.Post;
import com.example.llh_pc.it_support.restclients.RequestMethod;
import com.example.llh_pc.it_support.restclients.Response;
import com.example.llh_pc.it_support.restclients.RestClient;
import com.example.llh_pc.it_support.utils.Interfaces.Def;
import com.example.llh_pc.it_support.utils.Location.GPSTracker;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by LLH-PC on 9/22/2015.
 */
public class evenSubPostType implements AdapterView.OnItemClickListener {

    private Context context;
    private ArrayList<Post> arrayListPost;
    LocationManager locationManager ;
    String provider;
    private GPSTracker gpsTracker;
    private String t;
    private int STT;
    public evenSubPostType(Context current, ArrayList<Post> list)
    {
        this.context = current;
        this.arrayListPost = list;
        //gpsTracker = new GPSTracker(context);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Post p = arrayListPost.get(position);
        String idPost = p.getId();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("type_id",idPost );
        editor.commit();
        Intent intent = new Intent(context, frmGhiChu.class);
        context.startActivity(intent);
    }
}
