package com.example.llh_pc.it_support.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.datas.AccountDAL;
import com.example.llh_pc.it_support.gcm.QuickstartPreferences;
import com.example.llh_pc.it_support.gcm.RegistrationIntentService;
import com.example.llh_pc.it_support.utils.Events.eventLogin;
import com.example.llh_pc.it_support.utils.Events.eventStayLgoin;
import com.example.llh_pc.it_support.utils.Interfaces.InnoFunctionListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import org.apache.http.conn.params.ConnManagerParamBean;

import java.util.ArrayList;

public class frmDangNhap extends AppCompatActivity implements InnoFunctionListener,CompoundButton.OnFocusChangeListener {


    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "frmDangNhap";
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private Intent intent;
    public static EditText edtUserName, edtPass;
    frmDangKy frmDK = new frmDangKy();
    AccountDAL accdal;
    private Button btnLogin;
    private ArrayList<View> views = new ArrayList<>();
    private CheckBox cbSave;
    private int E, P = 0;
    private static String tokenGCM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_dang_nhap);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setTextColor(getResources().getColor(R.color.actionbar_text));
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());
        StrictMode.enableDefaults();

        accdal = new AccountDAL(getBaseContext());


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        /*-------Button fogot password---------*/
        final Intent intent1 = new Intent(this, frmQuenMK.class);
        final TextView txtQuenMK = (TextView) findViewById(R.id.txtQuenMatKhau);
        txtQuenMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent1);
            }
        });
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        cbSave = (CheckBox) findViewById(R.id.cbLuuTK);
        cbSave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editor.putInt("check", 2);
                    editor.commit();
                }
            }
        });
        editor.putInt("check", 1);
        editor.commit();

        final TextView tvEmail = (TextView) findViewById(R.id.txtEmail);
        edtUserName = (EditText) findViewById(R.id.edtTenDangNhap);
        edtUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().isEmpty()) {
                    E = 0;
                    btnLogin.setEnabled(false);
                    btnLogin.setBackgroundColor(getResources().getColor(R.color.mauxam));
                    tvEmail.setText("Vui lòng nhập email.");
                } else {
                    E = 1;
                    if (E + P == 2) {
                        btnLogin.setEnabled(true);
                        btnLogin.setBackgroundColor(getResources().getColor(R.color.mauxanh));
                        btnLogin.invalidate();
                        tvEmail.setText("");
                    }
                    tvEmail.setText("");
                }
            }
        });
        final TextView tvMatKhau = (TextView) findViewById(R.id.txtMatKhau);
        edtPass = (EditText) findViewById(R.id.edtMatKhau);
        edtPass.setTypeface(Typeface.DEFAULT);
        edtPass.setTransformationMethod(new PasswordTransformationMethod());
        edtPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().isEmpty()) {
                    P = 0;
                    btnLogin.setEnabled(false);
                    btnLogin.setBackgroundColor(getResources().getColor(R.color.mauxam));
                    tvMatKhau.setText("Vui lòng nhập mật khẩu.");
                } else {
                    P = 1;
                    if (E + P == 2) {
                        btnLogin.setEnabled(true);
                        btnLogin.setBackgroundColor(getResources().getColor(R.color.mauxanh));
                        btnLogin.invalidate();
                        tvMatKhau.setText("");
                    }
                    tvMatKhau.setText("");
                }
            }
        });

        edtUserName.setOnFocusChangeListener(this);
        edtPass.setOnFocusChangeListener(this);
        initFlags();
        initControl();
        setEventForControl();
        getData();
        setData();
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*phần quên mật khẩu*/
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            boolean value = extras.getBoolean("checkboxFW");
            edtUserName.setText(extras.getString("EmailFW"));
            cbSave.setChecked(true);
            frmDK.dangkythanhcong = false;
        }
        /*-----------------*/

       /* SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(this);
        String name = sharedPreference.getString("tokenGCM", "YourName");
        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }*/

        if (frmDK.dangkythanhcong == true) {
            edtUserName.setText(accdal.applyEmail.toString());
            if(edtUserName.getText().toString().length()>0) {
                E=1;
            }
            if(edtPass.getText().toString().length()>0)
            {
                P =1;
            }
            cbSave.setChecked(true);

        } else {
            frmDK.dangkythanhcong = false;
        }

        if (frmDK.dangkythanhcong == true ) {
            if(E+P ==2) {
                btnLogin.setEnabled(true);
                btnLogin.setBackgroundColor(getResources().getColor(R.color.mauxanh));
            }

            btnLogin.invalidate();


        }

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String t = intent.getExtras().getString("reg_token");
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("tokenGCM", t);
                editor.commit();
            }
        };

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        final Intent intent = new Intent(this, frmDK_DN.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), frmDK_DN.class);
        startActivityForResult(myIntent, 0);
        return true;
    }


    @Override
    public void initFlags() {

    }

    @Override
    public void initControl() {
        try {
            views.add((View) edtUserName);
            views.add((View) edtPass);
            views.add((View) cbSave);
        } catch (Exception ex) {
            System.out.print(ex.toString());
        }
    }

    @Override
    public void setEventForControl() {
        btnLogin.setOnClickListener(new eventLogin(frmDangNhap.this, views));
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
}
