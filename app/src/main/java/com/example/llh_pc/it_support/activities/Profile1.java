package com.example.llh_pc.it_support.activities;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.models.JsonParses.AccountParse;
import com.example.llh_pc.it_support.restclients.RequestMethod;
import com.example.llh_pc.it_support.restclients.Response;
import com.example.llh_pc.it_support.restclients.RestClient;
import com.example.llh_pc.it_support.utils.Images.ImageLoader;
import com.example.llh_pc.it_support.utils.Interfaces.Def;
import com.google.gson.Gson;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile1 extends ActionBarActivity implements View.OnClickListener {
    public static final String url_get_account_info_by_id = Def.API_BASE_LINK + Def.API_GET_ACCOUNT_INFO_BY_ID + Def.API_FORMAT_JSON;
    private ArrayList<String> arr = new ArrayList<String>();
    String email, full_name, avatar, phone, address, acctye, checkedbox, temp, arrayList;
    TextView eemail, efullname, eadress, ephone, password, passcu, passmoi, cfpassmoi;
    private CircleImageView c;
    ImageLoader imageload;
    TextView user, provier, chuyenmon, resultText,rateText;
    TextView pc, laptop, mayin, scan, mayfax, photocopy, OK, changePass;
    Button editProfile;
    RatingBar rateBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile1);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());
        StrictMode.enableDefaults();
        imageload = new ImageLoader(getBaseContext());
        rateText = (TextView) findViewById(R.id.rateText);
        c = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.profile_image);
        c.setOnClickListener(this);
        SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(Profile1.this);
        String name = sharedPreference.getString("token", "YourName");
        String id = sharedPreference.getString("id", "");
        getAccount(name, id);
        checkAcctype();
        setProfile();
        editProfile();

        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void onBackPressed() {
        final Intent intent = new Intent(this, frmTabHost.class);
        startActivity(intent);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), frmTabHost.class);
        startActivityForResult(myIntent, 0);


        return true;
    }
    public void getAccount(String name, String id) {

        try {


            RestClient restClient = new RestClient(url_get_account_info_by_id);
            restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
            restClient.addParam("id", id);
            restClient.addHeader("token", name);
            restClient.execute(RequestMethod.POST);
            if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS) {
                String jsonObject = restClient.getResponse();
                Gson gson = new Gson();
                AccountParse getAccountJson = gson.fromJson(jsonObject, AccountParse.class);
                if (getAccountJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS)) {
                    email = getAccountJson.getDKresults().getEmail().toString();
                    full_name = getAccountJson.getDKresults().getFull_name().toString();
                    phone = getAccountJson.getDKresults().getPhone_number().toString();
                    address = getAccountJson.getDKresults().getAddress().toString();
                    arr = getAccountJson.getDKresults().getAccount_type();
                    avatar = getAccountJson.getDKresults().getAvatar().toString();
//                    checkboxAcctype();

                }
            }


        } catch (Exception ex) {
        }

    }
    public void setProfile() {
        eemail = (TextView) findViewById(R.id.email);
        efullname = (TextView) findViewById(R.id.full_name);
        eadress = (TextView) findViewById(R.id.dia_chi);
        ephone = (TextView) findViewById(R.id.phone);
        password = (TextView) findViewById(R.id.password);
        imageload.DisplayImage(avatar, c);

        eemail.setText(email);
        efullname.setText(full_name);
        eadress.setText(address);
        ephone.setText(phone);

    }
    public void checkAcctype() {
        user = (TextView) findViewById(R.id.enableUser);
        provier = (TextView) findViewById(R.id.enableProvider);
        pc = (TextView) findViewById(R.id.bntPC);
        laptop = (TextView) findViewById(R.id.bntLaptpo);
        scan = (TextView) findViewById(R.id.bntMayScan);
        photocopy = (TextView) findViewById(R.id.photocopy);
        mayfax = (TextView) findViewById(R.id.bntMayfax);
        mayin = (TextView) findViewById(R.id.bntMayin);
        chuyenmon = (TextView) findViewById(R.id.viewchuyenmon);
        rateBar =(RatingBar) findViewById(R.id.ratingBar);

        for (int i = 0; i < arr.size(); i++) {

            if (arr.get(i).equals("1")) {
                user.setVisibility(View.VISIBLE);
                provier.setVisibility(View.GONE);
                chuyenmon.setVisibility(View.INVISIBLE);
                rateBar.setVisibility(View.GONE);
                rateText.setVisibility(View.GONE);
            } else {
                user.setVisibility(View.GONE);
                provier.setVisibility(View.VISIBLE);
                chuyenmon.setVisibility(View.VISIBLE);
                rateBar.setVisibility(View.VISIBLE);
                rateText.setVisibility(View.VISIBLE);
                if (arr.get(i).equals("3")) {

                    pc.setVisibility(View.VISIBLE);
                } else if (arr.get(i).equals("4")) {
                    laptop.setVisibility(View.VISIBLE);
                } else if (arr.get(i).equals("5")) {
                    mayin.setVisibility(View.VISIBLE);
                } else if (arr.get(i).equals("6")) {
                    photocopy.setVisibility(View.VISIBLE);
                } else if (arr.get(i).equals("7")) {
                    scan.setVisibility(View.VISIBLE);
                } else if (arr.get(i).equals("8")) {
                    mayfax.setVisibility(View.VISIBLE);
                } else {
                    pc.setVisibility(View.GONE);
                    laptop.setVisibility(View.GONE);
                    mayin.setVisibility(View.GONE);
                    photocopy.setVisibility(View.GONE);
                    scan.setVisibility(View.GONE);
                    mayfax.setVisibility(View.GONE);
                }
            }


        }
    }
    public void editProfile() {

        editProfile = (Button) findViewById(R.id.editProfile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile1 = new Intent(Profile1.this, Profile.class);
                startActivity(profile1);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
    public  String toUpperCaseFirst(String s) {
        if (s != null && s.length() > 0) {
            String first = s.substring(0, 1).toUpperCase();
            if (s.length() == 1) {
                return first;
            } else {
                String result = first + s.substring(1);
                return result;
            }



        }else return "";

    }
}
