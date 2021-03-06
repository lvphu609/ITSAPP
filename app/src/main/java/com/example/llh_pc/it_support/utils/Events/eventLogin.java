package com.example.llh_pc.it_support.utils.Events;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.activities.frmTabHost;
import com.example.llh_pc.it_support.models.Account;
import com.example.llh_pc.it_support.models.JsonParses.AccountParse;
import com.example.llh_pc.it_support.models.JsonParses.LoginParse;
import com.example.llh_pc.it_support.models.JsonParses.abc;
import com.example.llh_pc.it_support.restclients.RequestMethod;
import com.example.llh_pc.it_support.restclients.Response;
import com.example.llh_pc.it_support.restclients.RestClient;
import com.example.llh_pc.it_support.utils.CommonFunction;
import com.example.llh_pc.it_support.utils.Interfaces.Def;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by LLH-PC on 9/11/2015.
 */
public class eventLogin implements View.OnClickListener {
    SharedPreferences sharedpreferences;
    private String url_tokenGCM = Def.API_BASE_LINK + Def.API_tokenGCM + Def.API_FORMAT_JSON;
    private String url_login = Def.API_BASE_LINK + Def.API_LOGIN + Def.API_FORMAT_JSON;
    public static final String url_get_account_info_by_id = Def.API_BASE_LINK + Def.API_GET_ACCOUNT_INFO_BY_ID + Def.API_FORMAT_JSON;
    public static Context context;
    private ArrayList<View> views;
    private String full_name;
    private String avatar;
    private Intent intent;
    private Intent intent_acc;
    ;
    private String ts;
    private String email;
    private String phone, address;
    private ArrayList<String> arr;
    private String user_type;
    private String token;


    public eventLogin(Context current, ArrayList<View> viewArrayList) {
        this.context = current;
        this.views = viewArrayList;
    }

    @Override
    public void onClick(View v) {

        try {
            /*save*//*
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("storedName", "Your");
            editor.commit();
            *//*load*//*
            SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
            String name = sharedPreference.getString("storedName", "YourName");*/
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            EditText edtUserName = (EditText) views.get(0);
            EditText edtPassword = (EditText) views.get(1);
            CheckBox cbSave = (CheckBox) views.get(2);
            String U = edtUserName.getText().toString();
            String P = edtPassword.getText().toString();
            RestClient restClient = new RestClient(url_login);
            restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
            restClient.addParam(Account.EMAIL, U);
            restClient.addParam(Account.PASSWORD, CommonFunction.md5(P));
            restClient.execute(RequestMethod.POST);
            if (!emailValidator(edtUserName.getText().toString())) {

                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.popup_validation, null);
                android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);
                alertDialogBuilder.setView(promptsView);
                final TextView textView = (TextView) promptsView.findViewById(R.id.tvValidation);
                // set dialog message
                alertDialogBuilder.setView(promptsView);
                // set dialog message
                alertDialogBuilder.setCancelable(false);
                final android.support.v7.app.AlertDialog show = alertDialogBuilder.show();
                Button okpopup= (Button) promptsView.findViewById(R.id.okpopup);
                TextView tv = (TextView)promptsView.findViewById(R.id.tvValidation);
                tv.setText("Email hoặc mật khẩu không hợp lệ.");
                okpopup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        show.dismiss();
                    }
                });

                editor.putInt("check", 1);

            } else if (edtPassword.getText().length() < 6) {
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.popup_validation, null);
                android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);
                alertDialogBuilder.setView(promptsView);
                final TextView textView = (TextView) promptsView.findViewById(R.id.tvValidation);
                // set dialog message
                alertDialogBuilder.setView(promptsView);
                // set dialog message
                alertDialogBuilder.setCancelable(false);
                final android.support.v7.app.AlertDialog show = alertDialogBuilder.show();
                Button okpopup= (Button) promptsView.findViewById(R.id.okpopup);
                TextView tv = (TextView)promptsView.findViewById(R.id.tvValidation);
                tv.setText("Mật khẩu phải có ít nhất 6 kí tự.");
                okpopup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        show.dismiss();
                    }
                });
                editor.putInt("check", 1);
            } else if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS) {
                String jsonObject = restClient.getResponse();
                Gson gson = new Gson();
                LoginParse getLoginJson = gson.fromJson(jsonObject, LoginParse.class);
                //if result from response success
                if (getLoginJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS)) {
                    //save values into sharePreference
                    String id = getLoginJson.getResults().getAccount_id();
                    String account_type = getLoginJson.getResults().getAccount_type();
                    token = getLoginJson.getResults().getAccess_token();

                    /*------------------------------------------------------------*/
                    new GCMAsyncTask().execute(token);

                    /*--------------------information account------------------*/
                    getAccount(id, token);
                    /*--------------------------------------------------------*/
                    editor.putString("token", token);
                    editor.putString("id", id);
                    editor.putString("email", email);
                    editor.putString("password",P);
                    /*editor.putString("avatar",avatar);
                    editor.putString("fullname",full_name);*/
                    editor.commit();
                    Toast.makeText(context, "Đăng nhập thành công.", Toast.LENGTH_LONG).show();
                    //LoginGCM();
                    intent = new Intent(context, frmTabHost.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);


                } else {
                    LayoutInflater li = LayoutInflater.from(context);
                    View promptsView = li.inflate(R.layout.popup_validation, null);
                    android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);
                    alertDialogBuilder.setView(promptsView);
                    final TextView textView = (TextView) promptsView.findViewById(R.id.tvValidation);
                    // set dialog message
                    alertDialogBuilder.setView(promptsView);
                    // set dialog message
                    alertDialogBuilder.setCancelable(false);
                    final android.support.v7.app.AlertDialog show = alertDialogBuilder.show();
                    Button okpopup= (Button) promptsView.findViewById(R.id.okpopup);
                    TextView tv = (TextView)promptsView.findViewById(R.id.tvValidation);
                    tv.setText("Email hoặc mật khẩu không hợp lệ.");
                    okpopup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            show.dismiss();
                        }
                    });

                    editor.putInt("check", 1);
                }
            }
            /*888888888888888888888888888888**/
        } catch (Exception ex) {

        }
    }
    private void getAccount(String id, String token) {
        try {
            RestClient restClient = new RestClient(url_get_account_info_by_id);
            restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
            restClient.addParam("id", id);
            restClient.addHeader("token", token);
            restClient.execute(RequestMethod.POST);
            if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS) {
                String jsonObject = restClient.getResponse();
                Gson gson = new Gson();
                AccountParse getAccountJson = gson.fromJson(jsonObject, AccountParse.class);
                if (getAccountJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS)) {
                    full_name = getAccountJson.getDKresults().getFull_name().toString();
                    avatar = getAccountJson.getDKresults().getAvatar().toString();
                    user_type = getAccountJson.getDKresults().getAccount_type().toString();
                    /*info login*/
                    email = getAccountJson.getDKresults().getEmail();


                }
            }
        } catch (Exception ex) {
            ts = ex.toString();
        }
    }

    public static boolean emailValidator(final String mailAddress) {

        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(mailAddress);
        return matcher.matches();

    }

    private class GCMAsyncTask extends AsyncTask<String,Void,Boolean>
    {
        @Override
        protected Boolean doInBackground(String... params) {
            try {
                SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
                String tokenGCM = sharedPreference.getString("tokenGCM", "YourName");
                String token = params[0];
                RestClient restClient = new RestClient(url_tokenGCM);
                restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
                restClient.addHeader("token", token);
                restClient.addParam("reg_id", tokenGCM);
                restClient.execute(RequestMethod.POST);
                if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS) {
                    String jsonObject = restClient.getResponse();
                    Gson gson = new Gson();
                    abc getLoginJson = gson.fromJson(jsonObject, abc.class);
                    //if result from response success
                    if (getLoginJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS))
                    {
                        return true;
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
                return;
            }
        }
    }
}

