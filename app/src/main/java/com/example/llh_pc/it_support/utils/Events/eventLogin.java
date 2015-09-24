package com.example.llh_pc.it_support.utils.Events;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.activities.frmLuuTru;
import com.example.llh_pc.it_support.activities.frmTabHost;
import com.example.llh_pc.it_support.models.Account;
import com.example.llh_pc.it_support.models.JsonParses.LoginParse;
import com.example.llh_pc.it_support.models.JsonParses.PostParse;
import com.example.llh_pc.it_support.models.Post;
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
    private String url_login = Def.API_BASE_LINK + Def.API_LOGIN + Def.API_FORMAT_JSON;
    private Context context;
    private ArrayList<View> views;

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



            CheckBox cbSave = (CheckBox)views.get(2);
            String U = edtUserName.getText().toString();
            String P = edtPassword.getText().toString();
            RestClient restClient = new RestClient(url_login);
            restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
            restClient.addParam(Account.EMAIL, U);
            restClient.addParam(Account.PASSWORD, CommonFunction.md5(P));
            restClient.execute(RequestMethod.POST);
            if(!emailValidator(edtUserName.getText().toString()))
            {
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.popup_validation, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder( context);
                alertDialogBuilder.setView(promptsView);
                final TextView textView = (TextView) promptsView.findViewById(R.id.tvValidation);
                textView.setText("Email không hợp lệ ");
                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {


                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            }else if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS) {
                String jsonObject = restClient.getResponse();
                Gson gson = new Gson();
                LoginParse getLoginJson = gson.fromJson(jsonObject, LoginParse.class);
                //if result from response success
                if (getLoginJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS)) {
                    //save values into sharePreference
                        String t = getLoginJson.getResults().getAccess_token();
                        String id = getLoginJson.getResults().getAccount_id();
                        editor.putString("token", t);
                        editor.putString("id", id);
                        editor.commit();
                        Intent intent = new Intent(context, frmTabHost.class);
                        context.startActivity(intent);
                }
                else {
                    LayoutInflater li = LayoutInflater.from(context);
                    View promptsView = li.inflate(R.layout.popup_validation, null);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder( context);
                    alertDialogBuilder.setView(promptsView);
                    final TextView textView = (TextView) promptsView.findViewById(R.id.tvValidation);
                    textView.setText("Email hoặc mật khẩu không hợp lệ. Vui lòng nhập lại.");
                    // set dialog message
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {


                                        }
                                    });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // show it
                    alertDialog.show();
                    editor.putInt("check", 1);
                }
            }

        } catch (Exception ex) {

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
    private class Asysnc extends AsyncTask<String, Void, ArrayList<LoginParse>> {
        @Override
        protected ArrayList<LoginParse> doInBackground(String... params) {

            return null;
        }
    }
}

