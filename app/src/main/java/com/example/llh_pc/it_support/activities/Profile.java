package com.example.llh_pc.it_support.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.adapters.adapterGridView;
import com.example.llh_pc.it_support.datas.AccountDAL;
import com.example.llh_pc.it_support.models.Account;
import com.example.llh_pc.it_support.models.GetAccount;
import com.example.llh_pc.it_support.models.JsonParses.AccountParse;
import com.example.llh_pc.it_support.models.JsonParses.ChangePassParse;
import com.example.llh_pc.it_support.models.JsonParses.LoginParse;
import com.example.llh_pc.it_support.models.Result;
import com.example.llh_pc.it_support.models.ResultStatus;
import com.example.llh_pc.it_support.restclients.RequestMethod;
import com.example.llh_pc.it_support.restclients.Response;
import com.example.llh_pc.it_support.restclients.RestClient;
import com.example.llh_pc.it_support.utils.Images.ImageLoader;
import com.example.llh_pc.it_support.utils.Interfaces.Def;
import com.example.llh_pc.it_support.utils.Interfaces.InnoFunctionListener;
import com.google.gson.Gson;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile  extends AppCompatActivity implements InnoFunctionListener, CompoundButton.OnCheckedChangeListener,View.OnClickListener,TextWatcher,OnFocusChangeListener {
    ImageButton bntImage;
    public final static int REQUEST_CAMERA = 1;
    public final static int SELECT_FILE = 2;
    public static final String url_get_account_info_by_id = Def.API_BASE_LINK + Def.API_GET_ACCOUNT_INFO_BY_ID + Def.API_FORMAT_JSON;
    public static final String url_editprofile = Def.API_BASE_LINK + Def.API_CREATE + Def.API_FORMAT_JSON;
    public static final String url_change_password = Def.API_BASE_LINK + Def.API_ChangePassword + Def.API_FORMAT_JSON;
    private ArrayList<String> arrayListAccount = new ArrayList<String>();
    private ArrayList<String> arr = new ArrayList<String>();
    LinearLayout ll;
    String showString;
    String link;
    frmDangKy frmDK = new frmDangKy();
    AccountDAL accdal;
    String email, full_name, avatar, phone, address, acctye, checkedbox, temp, arrayList;
    TextView user, provier, chuyenmon, resultText,rateText,eemail;
    TextView validationname,validationphone,validationadess,validationphone1,valadationname1,validationadress1;
    RatingBar rateBar;
    Bitmap avatar1;
    char[] array,array1,array2;
    ImageButton setavatar;
    EditText  efullname, eadress, ephone, password, passcu, passmoi, cfpassmoi;
    Button pc, laptop, mayin, scan, mayfax, photocopy, editProfile, OK, changePass,okprofile;
    ImageLoader imageload;
    Bitmap thumbnail, bm;
    ArrayList<Integer> list = new ArrayList<>();
    ArrayList<String> listString = new ArrayList<>();
    TextView linkclick;
    View[] v;
    int x=1,y=1,z=1;
    public CheckBox cbpc, cbLaptop, cbphoto, cbmayin, cbmayfax, cbscan;
    int pccheck = 0;
    String result,result1;
    private AlertDialog helpDialog;
    private CircleImageView c;
    GridView gridView;
    private  Button[] buttonValues;
//    public Profile(Context context, Button[] buttonValues) {
//        this.context = context;
//        this.buttonValues = buttonValues;
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setValueForGridView();
//        bntImage = (ImageButton) findViewById(R.id.bntImage);
//        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) bntImage.getLayoutParams();
//        params.height = 300;
//        params.width = 300;
//        bntImage.setLayoutParams(params);
//        bntImage.setScaleType(ImageView.ScaleType.FIT_XY);
        photocopy = (Button) findViewById(R.id.photocopy);
        rateBar =(RatingBar) findViewById(R.id.ratingBar);
        rateText = (TextView) findViewById(R.id.rateText);
        validationname = (TextView) findViewById(R.id.valaditionname);
        valadationname1 = (TextView) findViewById(R.id.valaditionname1);

        validationphone=(TextView) findViewById(R.id.vadalitionphone);
        validationphone1=(TextView) findViewById(R.id.vadalitionphone1);
        validationadess=(TextView) findViewById(R.id.validationaddress);
        validationadress1=(TextView) findViewById(R.id.validationaddress1);
        ll = (LinearLayout) findViewById(R.id.ll);

        linkclick = (TextView) findViewById(R.id.link);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());
        StrictMode.enableDefaults();
//change












        accdal = new AccountDAL(getBaseContext());
        imageload = new ImageLoader(getBaseContext());
        c = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.profile_image);
        c.setOnClickListener(this);

        SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(Profile.this);
        String name = sharedPreference.getString("token", "YourName");
        String id = sharedPreference.getString("id", "");

        Context context1;

        getAccount(name, id);
        setProfile();
        checkAcctype();
        showListType();
//        gridView();
        editProfile();

        linkclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp();
            }
        });

        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_profile, menu);
//        return true;
//    }
    public void onBackPressed() {
        final Intent intent = new Intent(this, Profile1.class);
        startActivity(intent);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), Profile1.class);
        startActivityForResult(myIntent, 0);


        return true;
    }

    private void populateText(LinearLayout ll, View[] views, Context mContext) {
        Display display = getWindowManager().getDefaultDisplay();
        ll.removeAllViews();
        int maxWidth = display.getWidth() - 20;

        LinearLayout.LayoutParams params;
        LinearLayout newLL = new LinearLayout(mContext);
        newLL.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        newLL.setGravity(Gravity.LEFT);
        newLL.setOrientation(LinearLayout.HORIZONTAL);

        int widthSoFar = 0;

        for (int i = 0; i < views.length; i++) {
            LinearLayout LL = new LinearLayout(mContext);
            LL.setOrientation(LinearLayout.HORIZONTAL);
            LL.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
            LL.setLayoutParams(new ListView.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            views[i].measure(0, 0);
            params = new LinearLayout.LayoutParams(views[i].getMeasuredWidth(),
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            LL.addView(views[i], params);
            LL.measure(0, 0);
            widthSoFar += views[i].getMeasuredWidth();// YOU MAY NEED TO ADD THE MARGINS
            if (widthSoFar >= maxWidth) {
                ll.addView(newLL);

                newLL = new LinearLayout(mContext);
                newLL.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                newLL.setOrientation(LinearLayout.HORIZONTAL);
                newLL.setGravity(Gravity.LEFT);
                params = new LinearLayout.LayoutParams(LL
                        .getMeasuredWidth(), LL.getMeasuredHeight());
                newLL.addView(LL, params);
                widthSoFar = LL.getMeasuredWidth();
            } else {
                newLL.addView(LL);
            }
        }
        ll.addView(newLL);
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
        efullname = (EditText) findViewById(R.id.full_name);
        eadress = (EditText) findViewById(R.id.dia_chi);
        ephone = (EditText) findViewById(R.id.phone);
        password = (EditText) findViewById(R.id.password);
//        setavatar = (ImageButton) findViewById(R.id.profile_image);
        imageload.DisplayImage(avatar, c);

        eemail.setText(email);
        efullname.setText(full_name);
        eadress.setText(address);
        ephone.setText(phone);
//        password.setText("********");





    }

    public void editProfile() {
        OK = (Button) findViewById(R.id.OKProfile);
        changePass = (Button) findViewById(R.id.changepass);
//        editProfile = (Button) findViewById(R.id.editProfile);
        final EditText editpassword = (EditText) findViewById(R.id.password);
//        editProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {

//                changeAcctype();


//                editProfile.setEnabled(false);
//                editProfile.setVisibility(View.INVISIBLE);
//                OK.setEnabled(true);
                OK.setVisibility(View.VISIBLE);
                changePass.setEnabled(true);
                changePass.setVisibility(View.VISIBLE);
                editpassword.setVisibility(View.GONE);
                efullname.setEnabled(true);
                eadress.setEnabled(true);
                ephone.setEnabled(true);

        ephone.setOnFocusChangeListener(this);
        efullname.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == true) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


                } else {
                    if (efullname.getText().toString().length() > 0) {

                    }


                }
            }

        });
        efullname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                x = 1;

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                for (int i = s.length(); i > 0; i--) {

                    if (s.subSequence(i - 1, i).toString().equals("\n"))
                        s.replace(i - 1, i, "");

                }
                String txt = efullname.getText().toString();
                if (txt != null && txt.length() > 0) {
                    String first = txt.substring(0, 1);
                    if (!(first == first.toUpperCase())) {
                        String result = toUpperCaseFirst(efullname.getText().toString());
                        efullname.setText(result);
                        efullname.setSelection(efullname.getText().length());
                    }
                }

                if (s.toString().matches("")) {

                    validationname.setVisibility(View.VISIBLE);
                    valadationname1.setVisibility(View.GONE);
                    OK.setEnabled(false);
                    OK.setBackgroundColor(getResources().getColor(R.color.mauxam));
                    x = 0;
                } else {

                    validationname.setVisibility(View.GONE);
                    valadationname1.setVisibility(View.GONE);
                    checkname(s.toString());
                }
            }
        });
        eadress.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == true) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


                } else {

                    if (eadress.getText().toString().length() > 0) {

                    }
                }
            }
        });
        eadress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                y=1;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                for(int i = s.length(); i > 0; i--){

                    if(s.subSequence(i-1, i).toString().equals("\n"))
                        s.replace(i-1, i, "");

                }
                String txt = eadress.getText().toString();
                if(txt != null && txt.length()>0){
                    String first = txt.substring(0,1);
                    if(!(first == first.toUpperCase())){
                        String result = toUpperCaseFirst(eadress.getText().toString());
                        eadress.setText(result);
                        eadress.setSelection(eadress.getText().length());
                    }
                }
                if(s.toString().isEmpty()){
                    validationadess.setVisibility(View.VISIBLE);
                    validationadress1.setVisibility(View.GONE);
                    OK.setEnabled(false);
                    OK.setBackgroundColor(getResources().getColor(R.color.mauxam));
                    y=0;
                }
                else
                {
                    validationadess.setVisibility(View.GONE);
                    validationadress1.setVisibility(View.GONE);
                    checkdiachi(s.toString());
                }
            }
        });
        ephone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                if (ephone.getText().toString().length() >= 10) {
                    z = 1;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ephone.getText().toString().length() <= 9) {
                    validationphone1.setVisibility(View.VISIBLE);
                    OK.setEnabled(false);
                    OK.setBackgroundColor(getResources().getColor(R.color.mauxam));
                    z = 0;
                    setOKenable();
                } else {
                    validationphone1.setVisibility(View.GONE);
                    z = 1;
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

                for (int i = s.length(); i > 0; i--) {

                    if (s.subSequence(i - 1, i).toString().equals("\n"))
                        s.replace(i - 1, i, "");

                }

                if (ephone.getText().toString().length() <= 9) {
                    validationphone1.setVisibility(View.VISIBLE);
                    OK.setEnabled(false);
                    OK.setBackgroundColor(getResources().getColor(R.color.mauxam));
                    z = 0;
                    setOKenable();
                } else if (s.toString().isEmpty()) {
                    validationphone.setVisibility(View.VISIBLE);
                    validationphone1.setVisibility(View.GONE);
                    OK.setEnabled(false);
                    OK.setBackgroundColor(getResources().getColor(R.color.mauxam));
                    z = 0;
                } else {
                    validationphone.setVisibility(View.GONE);
                    if (s.toString().length() > 9) {
                        z = 1;
                        setOKenable();
                    }

                }
            }
        });

//                pc.setEnabled(true);
//                laptop.setEnabled(true);
//                photocopy.setEnabled(true);
//                scan.setEnabled(true);
//                mayfax.setEnabled(true);
//                mayin.setEnabled(true);

                c.setEnabled(true);
                c.setClickable(true);
                c.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectImage();
                    }
                });
//                checkEditAcctype();
                changePass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showInputDialog();

//                    }
//                });


            }
        });
        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OK.getResources().getColor(R.color.actionbar_text);
                    c.setEnabled(false);
                    c.setClickable(false);
//                bntImage.setEnabled(false);
//                bntImage.setClickable(false);
//                editProfile.setEnabled(true);
//                editProfile.setVisibility(View.VISIBLE);
                OK.setEnabled(false);
                OK.setVisibility(View.INVISIBLE);
                changePass.setEnabled(false);
                changePass.setVisibility(View.INVISIBLE);
                editpassword.setVisibility(View.VISIBLE);
                efullname.setEnabled(false);
                eadress.setEnabled(false);
                ephone.setEnabled(false);
//                pc.setEnabled(false);
//                laptop.setEnabled(false);
//                mayin.setEnabled(false);
//                photocopy.setEnabled(false);
//                scan.setEnabled(false);
//                mayfax.setEnabled(false);
//                pc.setTextColor(getResources().getColor(R.color.mauxam));
//                laptop.setTextColor(getResources().getColor(R.color.mauxam));
//                photocopy.setTextColor(getResources().getColor(R.color.mauxam));
//                scan.setTextColor(getResources().getColor(R.color.mauxam));
//                mayfax.setTextColor(getResources().getColor(R.color.mauxam));
//                mayin.setTextColor(getResources().getColor(R.color.mauxam));
                SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(Profile.this);
                String token = sharedPreference.getString("token", "YourName");
                String id = sharedPreference.getString("id", "");
                ChangeEditProfile(id, token, efullname.getText().toString(), ephone.getText().toString(), eadress.getText().toString(), temp, checkedbox);

                Intent profile1 = new Intent(Profile.this, Profile1.class);
                startActivity(profile1);

                Toast.makeText(getBaseContext(), "Đổi thông tin tài khoản thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public void checkAcctype() {
        user = (TextView) findViewById(R.id.enableUser);
        provier = (TextView) findViewById(R.id.enableProvider);
//        pc = (Button) findViewById(R.id.bntPC);
//        laptop = (Button) findViewById(R.id.bntLaptpo);
//        scan = (Button) findViewById(R.id.bntMayScan);
//        photocopy = (Button) findViewById(R.id.photocopy);
//        mayfax = (Button) findViewById(R.id.bntMayfax);
//        mayin = (Button) findViewById(R.id.bntMayin);
        chuyenmon = (TextView) findViewById(R.id.viewchuyenmon);

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
                    link = "PC";
                    listString.add(link);
                    showListType();
//                    pc.setVisibility(View.VISIBLE);
                } else if (arr.get(i).equals("4")) {
                    link = "Laptop";
                    listString.add(link);
                    showListType();
//                    laptop.setVisibility(View.VISIBLE);
                } else if (arr.get(i).equals("5")) {
                    link = "Máy in";
                    listString.add(link);
                    showListType();

//                    mayin.setVisibility(View.VISIBLE);
                } else if (arr.get(i).equals("6")) {
//                    photocopy.setVisibility(View.VISIBLE);
                    link = "Máy Photocopy";
                    listString.add(link);
                    showListType();
                } else if (arr.get(i).equals("7")) {
//                    scan.setVisibility(View.VISIBLE);
                    link = "Máy Scan";
                    listString.add(link);
                    showListType();
                } else if (arr.get(i).equals("8")) {
//                    mayfax.setVisibility(View.VISIBLE);
                    link = " Máy Fax";
                    listString.add(link);
                    showListType();
                } else {
//                    pc.setVisibility(View.GONE);
//                    laptop.setVisibility(View.GONE);
//                    mayin.setVisibility(View.GONE);
//                    photocopy.setVisibility(View.GONE);
//                    scan.setVisibility(View.GONE);
//                    mayfax.setVisibility(View.GONE);
                    link = null;

                }
            }


        }
    }

    public void checkboxAcctype() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor saveAcc = sharedPreferences.edit();

        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).equals("3")) {
                saveAcc.putInt("save", 3);
                saveAcc.commit();
            }
            if (arr.get(i).equals("4")) {
                saveAcc.putInt("save", 4);
                saveAcc.commit();
            }
            if (arr.get(i).equals("5")) {
                saveAcc.putInt("save", 5);
                saveAcc.commit();
            }
            if (arr.get(i).equals("6")) {
                saveAcc.putInt("save", 6);
                saveAcc.commit();
            }
            if (arr.get(i).equals("7")) {
                saveAcc.putInt("save", 7);
                saveAcc.commit();
            }
            if (arr.get(i).equals("8")) {
                saveAcc.putInt("save", 8);
                saveAcc.commit();
            }
        }

    }

    public void checkEditAcctype() {
        user = (TextView) findViewById(R.id.enableUser);
        provier = (TextView) findViewById(R.id.enableProvider);
        pc = (Button) findViewById(R.id.bntPC);
        laptop = (Button) findViewById(R.id.bntLaptpo);
        scan = (Button) findViewById(R.id.bntMayScan);
        photocopy = (Button) findViewById(R.id.photocopy);
        mayfax = (Button) findViewById(R.id.bntMayfax);
        mayin = (Button) findViewById(R.id.bntMayin);
        chuyenmon = (TextView) findViewById(R.id.viewchuyenmon);

        for (int i = 0; i < arr.size(); i++) {


            if (arr.get(i).equals("1")) {
                user.setVisibility(View.VISIBLE);
                provier.setVisibility(View.GONE);
                chuyenmon.setVisibility(View.INVISIBLE);
            } else {
                user.setVisibility(View.GONE);
                provier.setVisibility(View.VISIBLE);
                chuyenmon.setVisibility(View.VISIBLE);
                if (arr.get(i).equals("3")) {

                    pc.setEnabled(true);
                    pc.setTextColor(getResources().getColor(R.color.mauxanh));
                } else if (arr.get(i).equals("4")) {

                    laptop.setEnabled(true);
                    laptop.setTextColor(getResources().getColor(R.color.mauxanh));
                } else if (arr.get(i).equals("5")) {

                    mayin.setEnabled(true);
                    mayin.setTextColor(getResources().getColor(R.color.mauxanh));
                } else if (arr.get(i).equals("6")) {

                    photocopy.setEnabled(true);
                    photocopy.setTextColor(getResources().getColor(R.color.mauxanh));
                } else if (arr.get(i).equals("7")) {

                    scan.setEnabled(true);
                    scan.setTextColor(getResources().getColor(R.color.mauxanh));
                } else if (arr.get(i).equals("8")) {

                    mayfax.setEnabled(true);
                    mayfax.setTextColor(getResources().getColor(R.color.mauxanh));
                } else {
                }
            }


        }
    }

    protected void showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(Profile.this);
        View promptView = layoutInflater.inflate(R.layout.changepass, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Profile.this);

        alertDialogBuilder.setView(promptView);

        final Button okdialog = (Button) promptView.findViewById(R.id.diaglogok);
        Button huy = (Button) promptView.findViewById(R.id.dialoghuy);
        passcu = (EditText) promptView.findViewById(R.id.passcu);
        passmoi = (EditText) promptView.findViewById(R.id.passmoi);
        cfpassmoi = (EditText) promptView.findViewById(R.id.cfpassmoi);
        final TextView Tpassmoi1,Tpassmoi2,Tpasscu1,Tpasscu2,Tcfpassmoi1,Tcfpassmoi2,Tpassmoi3;
        Tpasscu1 = (TextView) promptView.findViewById(R.id.errorpass1);
        Tpasscu2 = (TextView) promptView.findViewById(R.id.errorpass2);
        Tpassmoi1 = (TextView) promptView.findViewById(R.id.errorpassmoi);
        Tpassmoi2 = (TextView) promptView.findViewById(R.id.errorpassmoi2);
        Tcfpassmoi1 = (TextView) promptView.findViewById(R.id.errorcfpassmoi);
        Tcfpassmoi2 = (TextView) promptView.findViewById(R.id.errorcfpassmoi2);
        Tpassmoi3 = (TextView) promptView.findViewById(R.id.errorpassmoi3);

        passmoi.setTypeface(Typeface.DEFAULT);
        passmoi.setTransformationMethod(new PasswordTransformationMethod());
        passcu.setTypeface(Typeface.DEFAULT);
        passcu.setTransformationMethod(new PasswordTransformationMethod());
        cfpassmoi.setTypeface(Typeface.DEFAULT);
        cfpassmoi.setTransformationMethod(new PasswordTransformationMethod());
        passcu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (passcu.getText().toString().length() > 5 && passmoi.getText().toString().length() > 5 && cfpassmoi.getText().toString().length() > 5 && cfpassmoi.getText().toString().matches(passmoi.getText().toString())
                        ) {
                    Tpasscu1.setVisibility(View.GONE);
                    Tpasscu2.setVisibility(View.GONE);
                    okdialog.setEnabled(true);
                    okdialog.setBackgroundColor(getResources().getColor(R.color.mauxanh));

                } else if (passcu.getText().toString().matches("")) {
                    Tpasscu1.setVisibility(View.VISIBLE);
                    Tpasscu2.setVisibility(View.GONE);
                    okdialog.setEnabled(false);
                    okdialog.setBackgroundColor(getResources().getColor(R.color.mauxam));
                } else if (passcu.getText().toString().length() < 6) {
                    Tpasscu1.setVisibility(View.GONE);
                    Tpasscu2.setVisibility(View.VISIBLE);
                    okdialog.setEnabled(false);
                    okdialog.setBackgroundColor(getResources().getColor(R.color.mauxam));
                } else if (passcu.getText().toString().length() > 5) {
                    Tpasscu1.setVisibility(View.GONE);
                    Tpasscu2.setVisibility(View.GONE);
                }

            }


            @Override
            public void afterTextChanged(Editable s) {
                if (passcu.getText().toString().length() > 5 && passmoi.getText().toString().length() > 5 && cfpassmoi.getText().toString().length() > 5 && cfpassmoi.getText().toString().matches(passmoi.getText().toString())
                        ) {
                    okdialog.setEnabled(true);
                    okdialog.setBackgroundColor(getResources().getColor(R.color.mauxanh));

                } else {
                    okdialog.setEnabled(false);
                    okdialog.setBackgroundColor(getResources().getColor(R.color.mauxam));
                }
            }
        });
        passmoi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(passcu.getText().toString().length()>5 && passmoi.getText().toString().length()>5 &&cfpassmoi.getText().toString().length()>5 &&cfpassmoi.getText().toString().matches(passmoi.getText().toString())
                        )
                {
                    Tpassmoi1.setVisibility(View.GONE);
                    Tpassmoi2.setVisibility(View.GONE);
                    Tpassmoi3.setVisibility(View.GONE);
                    okdialog.setEnabled(true);
                    okdialog.setBackgroundColor(getResources().getColor(R.color.mauxanh));

                }
                else if (passmoi.getText().toString().length()<6 && passmoi.getText().toString().length()>0)
                {
                    Tpassmoi2.setVisibility(View.VISIBLE);
                    Tpassmoi1.setVisibility(View.GONE);
                    Tpassmoi3.setVisibility(View.GONE);
                    okdialog.setEnabled(false);
                    okdialog.setBackgroundColor(getResources().getColor(R.color.mauxam));
                }
                else if (passmoi.getText().toString().matches(""))
                {
                    Tpassmoi2.setVisibility(View.GONE);
                    Tpassmoi1.setVisibility(View.VISIBLE);
                    Tpassmoi3.setVisibility(View.GONE);
                    okdialog.setEnabled(false);
                    okdialog.setBackgroundColor(getResources().getColor(R.color.mauxam));
                }
                else if (passmoi.getText().toString().length()>5)
                {
                    Tpassmoi1.setVisibility(View.GONE);
                    Tpassmoi2.setVisibility(View.GONE);
                    Tpassmoi3.setVisibility(View.GONE);
                }

                 if (passmoi.getText().toString().matches(passcu.getText().toString()))
                {
                    Tpassmoi1.setVisibility(View.GONE);
                    Tpassmoi2.setVisibility(View.GONE);
                    Tpassmoi3.setVisibility(View.VISIBLE);
                    okdialog.setEnabled(false);
                    okdialog.setBackgroundColor(getResources().getColor(R.color.mauxam));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(passcu.getText().toString().length()>5 && passmoi.getText().toString().length()>5 &&cfpassmoi.getText().toString().length()>5 &&cfpassmoi.getText().toString().matches(passmoi.getText().toString())
                      && passmoi.getText().toString().equalsIgnoreCase(passcu.getText().toString()))
                {
                    okdialog.setEnabled(true);
                    okdialog.setBackgroundColor(getResources().getColor(R.color.mauxanh));

                }


                else if (passmoi.getText().toString().matches(cfpassmoi.getText().toString()))
                {
                    okdialog.setEnabled(true);
                    okdialog.setBackgroundColor(getResources().getColor(R.color.mauxanh));
                }
                else
                {
                    okdialog.setEnabled(false);
                    okdialog.setBackgroundColor(getResources().getColor(R.color.mauxam));
                }
                if (passmoi.getText().toString().matches(passcu.getText().toString()))
                {
                    okdialog.setEnabled(false);
                    okdialog.setBackgroundColor(getResources().getColor(R.color.mauxam));
                }
            }
        });


        cfpassmoi.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    if (cfpassmoi.getText().toString().length()>0 && cfpassmoi.getText().toString().length()<6)
                { Tcfpassmoi1.setVisibility(View.VISIBLE);
                    Tcfpassmoi2.setVisibility(View.GONE);}
                    else  if (cfpassmoi.getText().toString().length()>5 && cfpassmoi.getText().toString().matches(passmoi.getText().toString())
                           )
                {
                    Tcfpassmoi1.setVisibility(View.GONE);
                    Tcfpassmoi2.setVisibility(View.GONE);
                }
                    else
                {
                    Tcfpassmoi1.setVisibility(View.GONE);
                    Tcfpassmoi2.setVisibility(View.VISIBLE);
                }

                }
                else
                {
                    if (cfpassmoi.getText().toString().length()>5 && cfpassmoi.getText().toString().matches(passmoi.getText().toString())
                            )
                    {
                        Tcfpassmoi1.setVisibility(View.GONE);
                        Tcfpassmoi2.setVisibility(View.GONE);
                    }
                    else
                    {
                        Tcfpassmoi1.setVisibility(View.GONE);
                        Tcfpassmoi2.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        cfpassmoi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(passcu.getText().toString().length()>5 && passmoi.getText().toString().length()>5 &&cfpassmoi.getText().toString().length()>5 &&cfpassmoi.getText().toString().matches(passmoi.getText().toString())
                       )
                {
                    Tcfpassmoi1.setVisibility(View.GONE);
                    Tcfpassmoi2.setVisibility(View.GONE);
                    okdialog.setEnabled(true);
                    okdialog.setBackgroundColor(getResources().getColor(R.color.mauxanh));

                }
                else if (cfpassmoi.getText().toString().length() <6 && cfpassmoi.getText().toString().length()>0)
                {
                    Tcfpassmoi2.setVisibility(View.VISIBLE);
                    Tcfpassmoi1.setVisibility(View.GONE);
                    okdialog.setEnabled(false);
                    okdialog.setBackgroundColor(getResources().getColor(R.color.mauxam));
                }
                else  if (cfpassmoi.getText().toString().matches(""))
                {
                    Tcfpassmoi1.setVisibility(View.VISIBLE);
                    Tcfpassmoi2.setVisibility(View.GONE);
                    okdialog.setEnabled(false);
                    okdialog.setBackgroundColor(getResources().getColor(R.color.mauxam));
                }
                else if (cfpassmoi.getText().toString().length()>5 && cfpassmoi.getText().toString().matches(passmoi.getText().toString()))
                {
                    Tcfpassmoi1.setVisibility(View.GONE);
                    Tcfpassmoi2.setVisibility(View.GONE);
                }
                if (passmoi.getText().toString().matches(passcu.getText().toString()))
                {
                    okdialog.setEnabled(false);
                    okdialog.setBackgroundColor(getResources().getColor(R.color.mauxam));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(passcu.getText().toString().length()>5 && passmoi.getText().toString().length()>5 &&cfpassmoi.getText().toString().length()>5 &&cfpassmoi.getText().toString().matches(passmoi.getText().toString())
                       )
                {
                    Tcfpassmoi1.setVisibility(View.GONE);
                    Tcfpassmoi2.setVisibility(View.GONE);
                    okdialog.setEnabled(true);
                    okdialog.setBackgroundColor(getResources().getColor(R.color.mauxanh));

                }
                else
                {
                    Tcfpassmoi1.setVisibility(View.GONE);
                    Tcfpassmoi2.setVisibility(View.VISIBLE);
                    okdialog.setEnabled(false);
                    okdialog.setBackgroundColor(getResources().getColor(R.color.mauxam));
                }
                if (passmoi.getText().toString().matches(passcu.getText().toString()))
                {
                    okdialog.setEnabled(false);
                    okdialog.setBackgroundColor(getResources().getColor(R.color.mauxam));
                }
            }
        });


        // setup a dialog window
        final AlertDialog show = alertDialogBuilder.show();
        alertDialogBuilder.setCancelable(false);
                okdialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(Profile.this);
                        String token = sharedPreference.getString("token", "YourName");
                        String id2 = sharedPreference.getString("id", "");
                        changePassword(id2, token, passcu.getText().toString(), passmoi.getText().toString(), cfpassmoi.getText().toString());
                        String status = sharedPreference.getString("status", "");

                        if (status.equals("success")) {
                            Toast.makeText(getBaseContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();

                            show.cancel();
                        } else {
                            Toast.makeText(getBaseContext(), "Vui lòng thử lại", Toast.LENGTH_SHORT).show();

                            show.cancel();
                        }

                    }
                });


        alertDialogBuilder.setCancelable(false);
               huy.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                                show.cancel();
                            }
                        });


        // create an alert dialog
    }

    public void changePassword(String id, String token, String passcu, String passmoi, String cfpassmoi) {
        try {


            RestClient restClient = new RestClient(url_change_password);
            restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
            restClient.addParam("id", id);
            restClient.addHeader("token", token);
            restClient.addParam(Account.OLD_PASSWORD, md5(passcu));
            restClient.addParam(Account.NEW_PASSWORD, md5(passmoi));
            restClient.addParam(Account.CONFIRM_PASSWORD, md5(cfpassmoi));
            restClient.execute(RequestMethod.POST);
            if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS) {


                String jsonObject = restClient.getResponse();
                Gson gson = new Gson();
                ChangePassParse getAccountJson = gson.fromJson(jsonObject, ChangePassParse.class);

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Profile.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("status", getAccountJson.getStatus());
                editor.commit();
                if (getAccountJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS)) {

                }
            }

        } catch (Exception ex) {
        }
    }

    public static String md5(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(input.getBytes());

        byte byteData[] = md.digest();

        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            String hex = Integer.toHexString(0xff & byteData[i]);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public void changeAcctype() {
        user = (TextView) findViewById(R.id.enableUser);
        provier = (TextView) findViewById(R.id.enableProvider);
        pc = (Button) findViewById(R.id.bntPC);
        laptop = (Button) findViewById(R.id.bntLaptpo);
        scan = (Button) findViewById(R.id.bntMayScan);
        photocopy = (Button) findViewById(R.id.photocopy);
        mayfax = (Button) findViewById(R.id.bntMayfax);
        mayin = (Button) findViewById(R.id.bntMayin);
        chuyenmon = (TextView) findViewById(R.id.viewchuyenmon);


        user.setVisibility(View.GONE);
        provier.setVisibility(View.VISIBLE);
        chuyenmon.setVisibility(View.VISIBLE);
        {
            pc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showPopUp();

                }
            });
        }
        {
            laptop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopUp();
                }
            });
        }
        {
            mayin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopUp();
                }
            });
        }
        {
            photocopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopUp();
                }
            });
        }
        {
            scan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopUp();
                }
            });
        }
        {
            mayfax.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopUp();
                }
            });
        }
    }

    public void showPopUp() {

        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
//        helpBuilder.setTitle("Chuyên môn");
        LayoutInflater inflater = getLayoutInflater();
        final View checkboxLayout = inflater.inflate(R.layout.populayoutprofile, null);
        helpBuilder.setView(checkboxLayout);

        cbpc = (CheckBox) checkboxLayout.findViewById(R.id.PC);
        cbLaptop = (CheckBox) checkboxLayout.findViewById(R.id.Laptop);
        cbphoto = (CheckBox) checkboxLayout.findViewById(R.id.photocopy);
        cbmayin = (CheckBox) checkboxLayout.findViewById(R.id.Mayin);
        cbmayfax = (CheckBox) checkboxLayout.findViewById(R.id.fax);
        cbscan = (CheckBox) checkboxLayout.findViewById(R.id.scan);
        okprofile = (Button) checkboxLayout.findViewById(R.id.okpopup);
        /**
         * set event
         */
        cbpc.setOnCheckedChangeListener(this);
        cbLaptop.setOnCheckedChangeListener(this);
        cbphoto.setOnCheckedChangeListener(this);
        cbmayin.setOnCheckedChangeListener(this);
        cbmayfax.setOnCheckedChangeListener(this);
        cbscan.setOnCheckedChangeListener(this);
        helpBuilder.setCancelable(false);
        helpDialog = helpBuilder.create();
        helpDialog.show();
        okprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user = (TextView) findViewById(R.id.enableUser);
                provier = (TextView) findViewById(R.id.enableProvider);
//                pc = (Button) findViewById(R.id.bntPC);
//                laptop = (Button) findViewById(R.id.bntLaptpo);
//                scan = (Button) findViewById(R.id.bntMayScan);
//                photocopy = (Button) findViewById(R.id.photocopy);
//                mayfax = (Button) findViewById(R.id.bntMayfax);
//                mayin = (Button) findViewById(R.id.bntMayin);
                chuyenmon = (TextView) findViewById(R.id.viewchuyenmon);
                list.clear();


                if (cbpc.isChecked()) {

                    list.remove((Integer) 3);
                    if (!list.contains(3) || !arrayList.equals("3")) {
                        list.remove((Integer) 3);
                        list.add(3);
//                        pc.setVisibility(View.VISIBLE);
//                        pc.setEnabled(true);
//                        pc.setTextColor(getResources().getColor(R.color.mauxanh));

//                                    Profile.this.putBooleanInPreferences(isChecked, "isChecked");

                    }


                } else

                {

                    list.remove((Integer) 3);
//                    pc.setVisibility(View.GONE);

                }

                if (cbLaptop.isChecked()) {

                    list.remove((Integer) 4);
                    if (!list.contains(4) || !arrayList.equals("4")) {
                        list.remove((Integer) 4);
                        list.add(4);
//                        laptop.setVisibility(View.VISIBLE);
//                        laptop.setEnabled(true);
//                        laptop.setTextColor(getResources().getColor(R.color.mauxanh));

//                                    Profile.this.putBooleanInPreferences(isChecked, "isChecked");
                    }
                } else

                {
                    list.remove((Integer) 4);
//                    laptop.setVisibility(View.GONE);

                }

                if (cbmayin.isChecked())

                {
                    list.remove((Integer) 5);
                    if (!list.contains(5) || !arrayList.equals("5")) {
                        list.remove((Integer) 5);
                        list.add(5);
//                        mayin.setVisibility(View.VISIBLE);
//                        mayin.setEnabled(true);
//                        mayin.setTextColor(getResources().getColor(R.color.mauxanh));
//                                    Profile.this.putBooleanInPreferences(isChecked, "isChecked");

                    }
                } else

                {
                    list.remove((Integer) 5);
//                    mayin.setVisibility(View.GONE);

                }

                if (cbphoto.isChecked())

                {
                    list.remove((Integer) 6);
                    if (!list.contains(6) || !arrayList.equals("6")) {
                        list.remove((Integer) 6);
                        list.add(6);
//                        photocopy.setVisibility(View.VISIBLE);
//                        photocopy.setEnabled(true);
//                        photocopy.setTextColor(getResources().getColor(R.color.mauxanh));
//                                    Profile.this.putBooleanInPreferences(isChecked, "isChecked");


                    }
                } else

                {

                    list.remove((Integer) 6);
//                    photocopy.setVisibility(View.GONE);

                }

                if (cbscan.isChecked())

                {
                    list.remove((Integer) 7);
                    if (!list.contains(7) || !arrayList.equals("7")) {
                        list.remove((Integer) 7);
                        list.add(7);
//                        scan.setVisibility(View.VISIBLE);
//                        scan.setEnabled(true);
//                        scan.setTextColor(getResources().getColor(R.color.mauxanh));

                    }
                } else

                {
                    list.remove((Integer) 7);
//                    scan.setVisibility(View.GONE);

                }

                if (cbmayfax.isChecked())

                {
                    list.remove((Integer) 8);
                    if (!list.contains(8) || !arrayList.equals("8")) {
                        list.remove((Integer) 8);
                        list.add(8);
//                        mayfax.setVisibility(View.VISIBLE);
//                        mayfax.setEnabled(true);
//                        mayfax.setTextColor(getResources().getColor(R.color.mauxanh));
//                                    Profile.this.putBooleanInPreferences(isChecked, "isChecked");

                    }
                } else

                {

                    list.remove((Integer) 8);
//                    mayfax.setVisibility(View.GONE);


                }
                if (pccheck > 0) {
                    pccheck = 0;
                }


                // checkedbox.joi = String.join(",", list);
                checkedbox = TextUtils.join(",", list);

                checkedbox = "[" + checkedbox;
                checkedbox = checkedbox + "]";
                SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(Profile.this);
                String token = sharedPreference.getString("token", "YourName");

                String id = sharedPreference.getString("id", "");
                ChangeEditProfile(id, token, efullname.getText().toString(), ephone.getText().toString(), eadress.getText().toString(), temp, checkedbox);
                listString.clear();
                getAccount(token,id);
                checkAcctype();
                helpDialog.dismiss();
            }
        });

        // Remember, create doesn't show the dialog

//
//        helpDialog = helpBuilder.create();
//        helpDialog.show();
        /*if (pccheck==true) {
            helpDialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(true);
        } else {

        }*/
    }


    public void ChangeEditProfile(String id, String token, String full_name, String phone, String address, String avatar, String accountType) {

        try {


            RestClient restClient = new RestClient(url_editprofile);
            restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
            restClient.addParam("id", id);
            restClient.addHeader("token", token);
            restClient.addParam(Account.FULL_NAME, full_name);
            restClient.addParam(Account.PHONE_NUMBER, phone);
            restClient.addParam(Account.ADDRESS, address);
            restClient.addParam(Account.ACCOUNT_TYPE, accountType);
            restClient.addParam(Account.AVATAR, avatar);
            restClient.execute(RequestMethod.POST);
            if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS) {
                String jsonObject = restClient.getResponse();
                Gson gson = new Gson();
                AccountParse getAccountJson = gson.fromJson(jsonObject, AccountParse.class);
                if (getAccountJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS)) {


                }
            }


        } catch (Exception ex) {
        }
    }

    private void selectImage() {

        final CharSequence[] items = {"Chụp ảnh", "Chọn từ thư viện", "Hủy"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
        builder.setTitle("Tùy chọn");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Chụp ảnh")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);

                } else if (items[item].equals("Chọn từ thư viện")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);

                } else if (items[item].equals("Hủy")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.PNG, 90, bytes);
                File destination = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".jpg");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                c.setImageBitmap(thumbnail);

//                bntImage.setImageBitmap(thumbnail);
                BitMapToString(thumbnail);


            } else if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};
                Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                        null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();
                String selectedImagePath = cursor.getString(column_index);
                ;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(selectedImagePath, options);
                final int REQUIRED_SIZE = 200;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                        && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                bm = BitmapFactory.decodeFile(selectedImagePath, options);
                Bitmap bitmap = bm;
                ExifInterface exif = null;
                try {
                    exif = new ExifInterface(selectedImagePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                Matrix m = new Matrix();

                if ((orientation == 3)) {
                    m.postRotate(180);
                    m.postScale((float) bm.getWidth(), (float) bm.getHeight());
                    bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
                } else if (orientation == 6) {
                    m.postRotate(90);
                    bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
                } else if (orientation == 8) {
                    m.postRotate(270);
                    bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
                }
                c.setImageBitmap(bitmap);

//                bntImage.setImageBitmap(bm);
                BitMapToString(bitmap);

            }
        }
    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream ByteStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, ByteStream);
        byte[] b = ByteStream.toByteArray();
        temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    @Override
    public void initFlags() {

    }

    @Override
    public void initControl() {

    }

    @Override
    public void setEventForControl() {

    }

    @Override
    public void getData(String... params) {

    }

    @Override
    public void setData() {

    }

    public void checkBoxEnable()
    {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if(isChecked){
            ++pccheck;
            buttonView.setButtonDrawable(R.drawable.checked);
        }
        else{
            --pccheck;
            buttonView.setButtonDrawable(R.drawable.check_white);
        }

        if (helpDialog != null && pccheck>0) {
            okprofile.setEnabled(true);
            okprofile.setBackgroundColor(getResources().getColor(R.color.mauxanh));
        } else {
            okprofile.setEnabled(false);
            okprofile.setBackgroundColor(getResources().getColor(R.color.mauxam));
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {


    }

    public void setOKenable()
    {
        if(x==1&&y==1&&z==1) {
            OK.setEnabled(true);
            OK.setBackgroundColor(getResources().getColor(R.color.mauxanh));
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }
//    public String capitalizeFirstLetter(String s){
//        array = s.toCharArray();
//        // Uppercase first letter.
//        if(array[0] == Character.toLowerCase(array[0]))
//        {
//            array[0] = Character.toUpperCase(array[0]);
//        }
//        else
//        {
//        }
//
//        // Uppercase all letters that follow a whitespace character.
//        for (int i = 1; i < array.length; i++) {
//            if (Character.isWhitespace(array[i - 1])) {
//
//                array[i] = Character.toUpperCase(array[i]);
//
//            }
//
//        }
//        // Result.
//        return new String(array);
//    }

    @Override
    protected void onResume() {


        super.onResume();
    }
    public String checkname(String s)
        { array1 = s.toCharArray();
        for (int i = 0; i < array1.length; i++) {
            if (Character.isWhitespace(array1[0])) {
                valadationname1.setVisibility(View.VISIBLE);
                validationname.setVisibility(View.GONE);
                OK.setEnabled(false);
                OK.setBackgroundColor(getResources().getColor(R.color.mauxam));
                x = 0;
            }
            else {      valadationname1.setVisibility(View.GONE);

                x = 1;
                setOKenable();}
        }
        return new String (array1);
    }

    public String checkdiachi(String s)
    { array2 = s.toCharArray();
        for (int i = 0; i < array2.length; i++) {
            if (Character.isWhitespace(array2[0])) {
                validationadress1.setVisibility(View.VISIBLE);
                validationadess.setVisibility(View.GONE);
                OK.setEnabled(false);
                OK.setBackgroundColor(getResources().getColor(R.color.mauxam));
                y=0;
            }
            else {      validationadress1.setVisibility(View.GONE);
                if(s.toString().length()>0) {

                }
                y=1;
                setOKenable();}
        }
        return new String (array2);
    }

    private void setValueForGridView(){
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


    public void showListType()
    {

            showString = TextUtils.join(",",listString);
            linkclick.setText(showString);

    }
}
