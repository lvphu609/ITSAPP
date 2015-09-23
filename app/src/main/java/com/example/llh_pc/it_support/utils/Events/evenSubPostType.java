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
    public static final String url_get_my_notifications = Def.API_BASE_LINK + Def.API_CREATESubPost + Def.API_FORMAT_JSON;
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
        gpsTracker = new GPSTracker(context);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        STT = position;
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.popup_create_post, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);

            // set dialog message
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Post p = arrayListPost.get(STT);
                                    //get Lang_Long\\
                                    SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
                                    String token = sharedPreference.getString("token", "YourName");
                                    String idPost = p.getId();
                                    String x = String.valueOf(gpsTracker.getLongitude());
                                    String y = String.valueOf(gpsTracker.getLatitude());
                                    t = userInput.getText().toString();
                                    if(Float.valueOf(x) != 0.0 || Float.valueOf(y) != 0.0 ) {
                                        try {
                                            //--------------------server---------------\\
                                            RestClient restClient = new RestClient(url_get_my_notifications);
                                            restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
                                            restClient.addHeader("token", token);
                                            restClient.addParam("type_id", idPost);
                                            restClient.addParam("location_lat", y);
                                            restClient.addParam("location_lng", x);
                                            restClient.execute(RequestMethod.POST);
                                            if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS) {
                                                String jsonObject = restClient.getResponse();
                                                Gson gson = new Gson();
                                                PostParse getListPostJson = gson.fromJson(jsonObject, PostParse.class);
                                                if (getListPostJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS)) {
                                                    Intent intent = new Intent(context, frmLuuTru.class);
                                                    context.startActivity(intent);
                                                }
                                            }
                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                            Log.e("error", ex.getMessage());

                                        }
                                    }else
                                    {
                                        Toast.makeText(context, "Bạn chưa mở GPS", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();

    }
}
