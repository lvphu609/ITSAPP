package com.example.llh_pc.it_support.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.models.Account;
import com.example.llh_pc.it_support.models.JsonParses.LoginParse;
import com.example.llh_pc.it_support.models.JsonParses.abc;
import com.example.llh_pc.it_support.restclients.RequestMethod;
import com.example.llh_pc.it_support.restclients.Response;
import com.example.llh_pc.it_support.restclients.RestClient;
import com.example.llh_pc.it_support.utils.Events.eventForgotPass;
import com.example.llh_pc.it_support.utils.Events.eventLogin;
import com.example.llh_pc.it_support.utils.Interfaces.Def;
import com.example.llh_pc.it_support.utils.Interfaces.InnoFunctionListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class frmQuenMK extends AppCompatActivity implements InnoFunctionListener,CompoundButton.OnFocusChangeListener {

    private Button btnSend;
    private ArrayList<View> views = new ArrayList<>();
    private EditText edtMail;
    private TextView tvMatKhau;
    private static String email;
    private String url_forgotpass = Def.API_BASE_LINK + Def.API_ForgotPass + Def.API_FORMAT_JSON;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_quen_mk);

        edtMail = (EditText)findViewById(R.id.edtMail);
        views.add((View) edtMail);
        btnSend = (Button)findViewById(R.id.btnSendMail);
        tvMatKhau = (TextView)findViewById(R.id.txtEmail);
        btnSend.setTextColor(getResources().getColor(R.color.actionbar_text));
        edtMail = (EditText)findViewById(R.id.edtMail);
        edtMail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    btnSend.setEnabled(false);
                    btnSend.setBackgroundColor(getResources().getColor(R.color.mauxam));
                    tvMatKhau.setText("Không được để trống trường này.");
                } else {
                    tvMatKhau.setText("");
                    btnSend.setEnabled(true);
                    btnSend.setBackgroundColor(getResources().getColor(R.color.mauxanh));
                    btnSend.invalidate();
                }
            }
        });
        /*sda*/

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back);
        actionBar.setDisplayHomeAsUpEnabled(false);
        edtMail.setOnFocusChangeListener(this);
        initFlags();
        initControl();
        setEventForControl();
        getData();
        setData();
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

    @Override
    public void initFlags() {

    }

    @Override
    public void initControl() {

    }

    @Override
    public void setEventForControl() {
        //btnSend.setOnClickListener(new eventForgotPass(frmQuenMK.this,views));
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edtMail.getText().toString();
                new SendMail().execute(email);
            }
        });
    }

    @Override
    public void getData(String... params) {

    }

    @Override
    public void setData() {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    private class SendMail extends AsyncTask<String, Void,String>
    {
        /*return
        * 1: success
        * 2: email don't register
        * 3: email don't format*/

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(frmQuenMK.this, "IT Support", "Loading...");
        }

        @Override
        protected String doInBackground(String... params) {
            if(!emailValidator(email)) {
                return "3";
            }else
            {
                try
                {
                    RestClient restClient = new RestClient(url_forgotpass);
                    restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
                    restClient.addParam(Account.EMAIL, email);
                    restClient.execute(RequestMethod.POST);
                    if(restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS);
                    {
                        String jsonObject = restClient.getResponse();
                        Gson gson = new Gson();
                        //LoginParse getLoginJson = gson.fromJson(jsonObject, LoginParse.class);
                        abc  getLoginJson =gson.fromJson(jsonObject, abc.class);
                        if (getLoginJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS)) {
                            return "1";
                        }
                        else
                        {
                            return "2";
                        }
                    }

                }catch (Exception ex)
                {
                    Log.e(ex.toString(),"Errors");
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if(s.equals("1"))
            {
                Intent intent = new Intent(frmQuenMK.this, frmTaoMoiMatKhau.class);
                intent.putExtra("mail", email);
                progressDialog.dismiss();
                startActivity(intent);
            }
            if(s.equals("2"))
            {
                String t2 = "Email không hợp lệ hoặc chưa đăng ký.";
                progressDialog.dismiss();
                popupValidation(t2);
            }
            if(s.equals("3"))
            {
                String t3 = " Email không hợp lệ hoặc chưa đăng ký.";
                progressDialog.dismiss();
                popupValidation(t3);
            }
            super.onPostExecute(s);
        }
    }

    public void popupValidation(String info)
    {
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.popup_validation, null);
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsView);
        final TextView textView = (TextView) promptsView.findViewById(R.id.tvValidation);
        // set dialog message
        alertDialogBuilder.setView(promptsView);
        // set dialog message
        alertDialogBuilder.setCancelable(false);
        final android.support.v7.app.AlertDialog show = alertDialogBuilder.show();
        Button okpopup= (Button) promptsView.findViewById(R.id.okpopup);
        TextView tv = (TextView)promptsView.findViewById(R.id.tvValidation);
        tv.setText(info);
        okpopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show.dismiss();
            }
        });
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
