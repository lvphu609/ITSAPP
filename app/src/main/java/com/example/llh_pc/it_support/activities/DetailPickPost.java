package com.example.llh_pc.it_support.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.models.JsonParses.PostParse;
import com.example.llh_pc.it_support.models.JsonParses.abc;
import com.example.llh_pc.it_support.restclients.RequestMethod;
import com.example.llh_pc.it_support.restclients.Response;
import com.example.llh_pc.it_support.restclients.RestClient;
import com.example.llh_pc.it_support.utils.Interfaces.Def;
import com.example.llh_pc.it_support.utils.Location.GPSTracker;
import com.google.gson.Gson;

public class DetailPickPost extends AppCompatActivity {
    public static final String url_PickPost = Def.API_BASE_LINK + Def.API_PickPost + Def.API_FORMAT_JSON;
    TextView tvType,tvLocation_Name,tvNote,tvName,tvPhone,tvaddress;
    private ProgressDialog progressDialog;
    private String ID_PickPost;
    private GPSTracker gpsTracker;
    private String Longitude,Latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pick_post);
        gpsTracker = new GPSTracker(DetailPickPost.this);
        tvType = (TextView)findViewById(R.id.tvTypePost);
        tvLocation_Name = (TextView)findViewById(R.id.tvLocation);
        tvNote = (TextView)findViewById(R.id.tvNote);
        tvName = (TextView)findViewById(R.id.tvName);
        tvPhone = (TextView)findViewById(R.id.tvPhone_number);
        tvaddress = (TextView)findViewById(R.id.tvaddress);

        Bundle extras = getIntent().getExtras();
        tvType.setText(extras.getString("loaibaohong"));
        tvLocation_Name.setText(extras.getString("diachi"));
        tvNote.setText(extras.getString("ghichu"));
        tvName.setText(extras.getString("hoten"));
        tvName.setTextColor(getResources().getColor(R.color.mauxanh));
        tvPhone.setText(extras.getString("dienthoai"));
        tvPhone.setTextColor(getResources().getColor(R.color.mauxanh));
        tvaddress.setText(extras.getString("diachinha"));
        ID_PickPost = extras.getString("IDPostPost");
        Longitude = String.valueOf(gpsTracker.getLongitude());
        Latitude = String.valueOf(gpsTracker.getLatitude());
        Button btnAccept = (Button)findViewById(R.id.btnAccept);
        btnAccept.setTextColor(getResources().getColor(R.color.actionbar_text));
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AccpetAsyncTack().execute(ID_PickPost,Longitude,Latitude);
            }
        });
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_pick_post, menu);
        return true;
    }
*/
   public void onBackPressed() {
       final Intent intent = new Intent(this, frmTabHost.class);
       startActivity(intent);
   }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Intent myIntent = new Intent(getApplicationContext(), frmTabHost.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

    private class AccpetAsyncTack extends AsyncTask<String,Void,Boolean>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(DetailPickPost.this, "IT Support", "Loading...");
        }

        @Override
        protected Boolean doInBackground(String... params) {
            try
            {
                String id = params[0];
                String Longitude = params[1];
                String Latitude = params[2];
                SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(DetailPickPost.this);
                String token = sharedPreference.getString("token", "YourName");
                RestClient restClient = new RestClient(url_PickPost);
                restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
                restClient.addHeader("token", token);
                restClient.addParam("id", id);
                restClient.addParam("location_lat",Longitude);
                restClient.addParam("location_lng",Latitude);
                restClient.execute(RequestMethod.POST);
                if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS) {
                    String jsonObject = restClient.getResponse();
                    Gson gson = new Gson();
                    abc getListPostJson = gson.fromJson(jsonObject, abc.class);
                    if(getListPostJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS))
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
            }catch (Exception ex)
            {
                Log.e(ex.toString(),"Lỗi");
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean)
            {
                Toast.makeText(DetailPickPost.this, "Chấp nhận thành công.", Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(getApplicationContext(), frmTabHost.class);
                myIntent.putExtra("status",1);
                frmTabHost.x = 3;
                startActivityForResult(myIntent, 0);
                finish();
                progressDialog.dismiss();
            }
            else
            {
                Toast.makeText(DetailPickPost.this, "Bài post này đã có người pick.", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }
    }
}
