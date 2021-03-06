package com.example.llh_pc.it_support.utils.Events;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.activities.frmDK_DN;
import com.example.llh_pc.it_support.activities.frmDangNhap;
import com.example.llh_pc.it_support.models.Account;
import com.example.llh_pc.it_support.models.JsonParses.LoginParse;
import com.example.llh_pc.it_support.restclients.RequestMethod;
import com.example.llh_pc.it_support.restclients.Response;
import com.example.llh_pc.it_support.restclients.RestClient;
import com.example.llh_pc.it_support.utils.CommonFunction;
import com.example.llh_pc.it_support.utils.Interfaces.Def;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by LLH-PC on 9/14/2015.
 */
public class eventSetPass implements View.OnClickListener {
    private Context context;
    private ArrayList<View> views;
    private String mail;
    private String url_resetpass = Def.API_BASE_LINK + Def.API_ResetPass + Def.API_FORMAT_JSON;

    public eventSetPass(Context current, ArrayList<View> viewArrayList, String mail) {
        this.context = current;
        this.views = viewArrayList;
        this.mail = mail;
    }

    @Override
    public void onClick(View v) {
        try {
            EditText edtCode = (EditText) views.get(0);
            String code = edtCode.getText().toString();
            EditText edtPass = (EditText) views.get(1);
            String pass = edtPass.getText().toString();
            EditText edtAPass = (EditText) views.get(2);
            String apass = edtAPass.getText().toString();
            RestClient restClient = new RestClient(url_resetpass);
            restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
            restClient.addParam(Account.CODE_RESET_PASSWORD, code);
            restClient.addParam(Account.PASSWORD, CommonFunction.md5(pass));
            restClient.addParam(Account.CONFIRM_PASSWORD, CommonFunction.md5(apass));
            restClient.addParam(Account.EMAIL, mail);
            restClient.execute(RequestMethod.POST);
            if (pass.length() < 6) {
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
 /*==>*/        } else if (!pass.equals(apass)) {
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
                tv.setText("Xác nhận mật khẩu không trùng khớp với mật khẩu đã đăng ký.");
                okpopup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        show.dismiss();
                    }
                });
            } else if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS) {
                   String jsonObject = restClient.getResponse();
                Gson gson = new Gson();
                LoginParse getLoginJson = gson.fromJson(jsonObject, LoginParse.class);
                //if result from response success
                if (getLoginJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS)) {
                    //save values into sharePreference
                    Intent intent = new Intent(context, frmDangNhap.class);
                    intent.putExtra("checkboxFW",true);
                    intent.putExtra("EmailFW",mail);
                    Toast.makeText(context, "Cập nhật thành công.", Toast.LENGTH_LONG).show();
                    //Toast.makeText(context, "", Toast.LENGTH_LONG).show();
                    context.startActivity(intent);
                } else {
                    LayoutInflater li = LayoutInflater.from(context)
                            ;
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
                    tv.setText("Mã xác thực không hợp lệ.");
                    okpopup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            show.dismiss();
                        }
                    });
                }
            }
        } catch (Exception ex) {

        }
    }
}
