package com.example.llh_pc.it_support.utils.Events;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.activities.frmDK_DN;
import com.example.llh_pc.it_support.activities.frmTaoMoiMK;
import com.example.llh_pc.it_support.activities.frmTaoMoiMatKhau;
import com.example.llh_pc.it_support.models.Account;
import com.example.llh_pc.it_support.models.JsonParses.LoginParse;
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
 * Created by LLH-PC on 9/13/2015.
 */
public class eventForgotPass implements View.OnClickListener {
    private String url_forgotpass = Def.API_BASE_LINK + Def.API_ForgotPass + Def.API_FORMAT_JSON;
    private Context context;
    private ArrayList<View> views;

    public eventForgotPass(Context current, ArrayList<View> viewArrayList)
    {
        this.context = current;
        this.views = viewArrayList;
    }
    @Override
    public void onClick(View v) {
        try {

            EditText edtMai = (EditText) views.get(0);
            String mail = edtMai.getText().toString();
            RestClient restClient = new RestClient(url_forgotpass);
            restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
            restClient.addParam(Account.EMAIL, mail);
            restClient.execute(RequestMethod.POST);

            if(!emailValidator(edtMai.getText().toString()))
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
                    Intent intent = new Intent(context, frmTaoMoiMatKhau.class);
                    intent.putExtra("mail",mail);
                    context.startActivity(intent);
                }else
                {
                    //save values into sharePreference
                    LayoutInflater li = LayoutInflater.from(context);
                    View promptsView = li.inflate(R.layout.popup_validation, null);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder( context);
                    alertDialogBuilder.setView(promptsView);
                    final TextView textView = (TextView) promptsView.findViewById(R.id.tvValidation);
                    textView.setText("Email không hợp lệ hoặc chưa đăng ký.");
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
                }
            }
        }catch (Exception ex)
        {

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
}
