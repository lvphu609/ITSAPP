package com.example.llh_pc.it_support.activities;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.datas.AccountDAL;
import com.example.llh_pc.it_support.fragments.DateTimePicker;
import com.example.llh_pc.it_support.models.Account;
import com.example.llh_pc.it_support.models.JsonParses.LoginParse;
import com.example.llh_pc.it_support.models.Result;
import com.example.llh_pc.it_support.models.ResultStatus;
import com.example.llh_pc.it_support.restclients.RequestMethod;
import com.example.llh_pc.it_support.restclients.Response;
import com.example.llh_pc.it_support.restclients.RestClient;
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
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class frmDangKy extends AppCompatActivity implements InnoFunctionListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private boolean is_network = false;
    boolean flagcheckon = false;
    protected View gameView;
    ScrollView scrollView;
    TextView errorname, errorname1;
    AccountDAL accdal;
    frmDangNhap frmDN;
    TextView txtDate;
    private Context context;
    boolean emailTontai = false;
    //avatar
    int i;
    char[] array;
    Bitmap thumbnail, bm;
    public static String temp, accType, checkedbox;
    ArrayList<Integer> list = new ArrayList<>();
    private Button btDate, dangkyok;
    boolean checkErrorLineEmail = false;
    boolean fullnameflag = false;
    boolean emailflag = false;
    boolean passwordflag = false;
    boolean confirmpasswordflag = false;
    boolean phoneflag = false;
    boolean addressflag = false;
    boolean selectedImageflag = false;
    boolean checkAccountType = false;
    //checkboxboolen
    boolean checkPCInvisible = false;
    boolean checkLaptopInvisible = false;
    boolean checkMayinInvisible = false;
    boolean checkMayPhotoInvisible = false;
    boolean checkScanInvisible = false;
    boolean checkfaxInvisible = false;
    boolean inVaild = false;
    boolean checkpasswordtrue = false;
    public static boolean dangkythanhcong = false;
    public ArrayList<View> listEditText = new ArrayList<>();
    Canvas canvas;
    //API
//    private Context context;
//    private String url_login = Def.API_BASE_LINK + Def.API_LOGIN + Def.API_FORMAT_JSON;
//    private boolean is_network = false;
//    private SharePreference share_preference;

    private String url_checkemail = Def.API_BASE_LINK + Def.API_CHECKEMAIL + Def.API_FORMAT_JSON;
    //Camera
    public final static int REQUEST_CAMERA = 1;
    public final static int SELECT_FILE = 2;
    Calendar cal;
    public Uri mImageUri;
    ImageButton bntImage;
    Bitmap originImage;
    CheckBox prefCheckBox, provider, user, checkPC, Mayin, scan, fax, Laptop, photocopy;
    CheckBox mayin, mayfax, pc, Laptop1, scan1, photo;
    TextView prefEditText, cbPC, cbLaptop, cbPhoto, cbScan, cbFax, cbMayin, chuyenmon, errorline1, errorline2, errorpass1, errorpass2, errorcfpassword, errorphone1, errorphone2, erroraddress,erroraddress2, errorcfpassword2;
    public static EditText Ifullname, Iemail, Ipassword, Iconfirmpassword, Iphone, Idia_chi;
    ArrayList<DateTimePicker> arrDate = new ArrayList<DateTimePicker>();
    ArrayAdapter<DateTimePicker> adapter = null;
    String arr[] = {
            "Giới tính",
            "Nam",
            "Nữ"};
    ArrayList<Integer> arrayListCheck = new ArrayList<Integer>();
    private CircleImageView c;
    String result, result1;
    char[] array1, array2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_dang_ky);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());
        StrictMode.enableDefaults();

        accdal = new AccountDAL(getBaseContext());
        frmDN = new frmDangNhap();
        //ImageButton
//        bntImage = (ImageButton) findViewById(R.id.bntImage);
        //jvcjknkxcv
        c = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.profile_image);
        c.setOnClickListener(this);

        //jkbvjkjkcbvmkl




//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) bntImage.getLayoutParams();
//        params.height = 300;
//        params.width = 300;
//        bntImage.setLayoutParams(params);
//        bntImage.setScaleType(ImageView.ScaleType.FIT_XY);
        //Pick Date & Gender
//        Spinner spin = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//        spin.setAdapter(adapter);
//        getFormWidgets();
//        getDefautInfor();
//        addEventFormWidgets();
        //Avatar
//        bntImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getBaseContext(), "Chup hinh nao", Toast.LENGTH_SHORT).show();
////                loadImageFromCamera();
////                grabImage();
////                getResizedBitmap(originImage, 20, 20);
////                selectImage();
//
//            }
//        });
        //actionBar
//        ActionBar actionBar = getActionBar();
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dangkyok = (Button) findViewById(R.id.dangkyok);
        dangkyok.setTextColor(getResources().getColor(R.color.actionbar_text));
        //Edittext

        Ifullname = (EditText) findViewById(R.id.full_name);
        Iemail = (EditText) findViewById(R.id.email);
        Ipassword = (EditText) findViewById(R.id.password);
        Iconfirmpassword = (EditText) findViewById(R.id.confirmPassword);
        Iphone = (EditText) findViewById(R.id.phone);
        Idia_chi = (EditText) findViewById(R.id.dia_chi);
        //ErrorLine
        errorline1 = (TextView) findViewById(R.id.erroEmail1);
        errorline2 = (TextView) findViewById(R.id.erroEmail2);
        errorcfpassword = (TextView) findViewById(R.id.cfpassword);
        erroraddress = (TextView) findViewById(R.id.erroraddress);
        erroraddress2 = (TextView) findViewById(R.id.validationaddress1);
        //cb
        cbPC = (TextView) findViewById(R.id.cbPC);
        cbLaptop = (TextView) findViewById(R.id.cbLaptop);
        cbMayin = (TextView) findViewById(R.id.cbMayin);
        cbPhoto = (TextView) findViewById(R.id.cbPhoto);
        cbFax = (TextView) findViewById(R.id.cbFax);
        cbScan = (TextView) findViewById(R.id.cbScan);
        chuyenmon = (TextView) findViewById(R.id.chuyenmon);
        errorname = (TextView) findViewById(R.id.errorname);
        errorname1 = (TextView) findViewById(R.id.errorname1);
        errorpass1 = (TextView) findViewById(R.id.errorpass1);
        errorpass2 = (TextView) findViewById(R.id.errorpass2);
        errorphone1 = (TextView) findViewById(R.id.errorphone1);
        errorphone2 = (TextView) findViewById(R.id.errorphone2);
        errorcfpassword2 = (TextView) findViewById(R.id.cfpassword1);

        //set value


        //upcase

        Ipassword.setTypeface(Typeface.DEFAULT);
        Ipassword.setTransformationMethod(new PasswordTransformationMethod());
        Iconfirmpassword.setTypeface(Typeface.DEFAULT);
        Iconfirmpassword.setTransformationMethod(new PasswordTransformationMethod());
        listEditText.add((View) Ifullname);
        listEditText.add((View) Iemail);
        listEditText.add((View) Ipassword);
        listEditText.add((View) Iconfirmpassword);
        listEditText.add((View) Iphone);
        listEditText.add((View) Idia_chi);
        //Sumit Đăng ký
        dangkyok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getcheckEmail(Iemail.getText().toString());
                fieldNull();
                if (inVaild == false || emailTontai == true) {
                    checkErrorLineEmail = false;
                    ErrorLine();

                }
                if (inVaild == false || emailTontai == true) {
                    Iemail.requestFocus();
                } else if (checkpasswordtrue == false) {
                    Ipassword.requestFocus();
                } else if (confirmpasswordflag == false) {
                    Iconfirmpassword.requestFocus();
                } else if (phoneflag == false) {
                    Iphone.requestFocus();
                } else if (addressflag == false) {
                    Idia_chi.requestFocus();
                }


                boolean allInformationtrue = false;
                try {

                    accdal.getSignUp(Iemail.getText().toString(), md5(Ipassword.getText().toString()), md5(Iconfirmpassword.getText().toString()), Ifullname.getText().toString(), Iphone.getText().toString(), Idia_chi.getText().toString(), temp, checkedbox);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                if (inVaild == true && emailTontai == false && checkpasswordtrue == true && confirmpasswordflag == true && phoneflag == true) {

                    popupthanhcong();


                }


            }
        });

//        AccountDAL abc = new AccountDAL(frmDangKy.this, listEditText);


        Ifullname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if(Ifullname.getText().toString().length()>0) {
//                    result = capitalizeFirstLetter(Ifullname.getText().toString());
//
//                }
//
//
//                    if (array[i] == Character.toUpperCase(array[i])) {
//                        Ifullname.setText(result);
//                    }

            }

            @Override
            public void afterTextChanged(Editable s) {


                if (s.toString().matches("")) {

                    errorname.setVisibility(View.VISIBLE);
                    errorname1.setVisibility(View.GONE);
                    fullnameflag = false;
                    setDongyEnble();

                } else {

                    if (s.toString().matches("")) {
                        errorname.setVisibility(View.VISIBLE);
                        errorname1.setVisibility(View.GONE);
                    } else {
                        errorname.setVisibility(View.GONE);
                        errorname1.setVisibility(View.GONE);
                        checkname(s.toString());
                    }


                }


            }
        });
        Ifullname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == true) {

                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);



                } else {

                    if (Ifullname.getText().toString().equals("")) {
                        errorname.setVisibility(View.VISIBLE);
                    } else {
                        errorname.setVisibility(View.GONE);
                        Ifullname.setText(result);
                    }


                }
            }

        });

        Iemail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus == true) {

                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    if (Iemail.getText().toString().matches("")) {
                        errorline1.setVisibility(View.VISIBLE);
                        errorline2.setVisibility(View.GONE);
                    } else if (inVaild == false && Iemail.getText().toString().length() > 0) {
                        errorline2.setVisibility(View.VISIBLE);
                        errorline1.setVisibility(View.GONE);
                    } else {
                        errorline1.setVisibility(View.GONE);
                        errorline2.setVisibility(View.GONE);
                    }

                } else {
                    if (Iemail.getText().toString().matches("")) {
                        errorline1.setVisibility(View.VISIBLE);
                        errorline2.setVisibility(View.GONE);
                    } else if (inVaild == false && Iemail.getText().toString().length() > 0) {
                        errorline2.setVisibility(View.VISIBLE);
                        errorline1.setVisibility(View.GONE);
                    } else {
                        errorline1.setVisibility(View.GONE);
                        errorline2.setVisibility(View.GONE);
                    }

                }
            }
        });
        Iemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (inVaild == false && Iemail.getText().toString().length() > 0) {
                    errorline2.setVisibility(View.VISIBLE);
                    errorline1.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().matches("")) {
                    errorline1.setVisibility(View.VISIBLE);
                    errorline2.setVisibility(View.GONE);
                    validate(s.toString());
                    checkErrorLineEmail = false;
                    emailflag = false;
                    setDongyEnble();


                }
//
                else {

                    inVaild = false;
                    emailflag = true;
                    setDongyEnble();
                    validate(s.toString());
                    errorline1.setVisibility(View.GONE);

                }

            }
        });
        Ipassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    if (Ipassword.getText().toString().matches("")) {
                        errorpass1.setVisibility(View.VISIBLE);
                        errorpass2.setVisibility(View.GONE);
                    } else if (Ipassword.getText().toString().length() <= 5 && Ipassword.getText().toString().length() > 0) {
                        errorpass2.setVisibility(View.VISIBLE);
                        errorpass1.setVisibility(View.GONE);
                    } else {
                        errorpass2.setVisibility(View.GONE);
                        errorpass1.setVisibility(View.GONE);
                    }
                } else {
                    if (Ipassword.getText().toString().matches("")) {
                        errorpass1.setVisibility(View.VISIBLE);
                        errorpass2.setVisibility(View.GONE);
                    } else if (Ipassword.getText().toString().length() <= 5 && Ipassword.getText().toString().length() > 0) {
                        errorpass2.setVisibility(View.VISIBLE);
                        errorpass1.setVisibility(View.GONE);
                    } else {
                        errorpass2.setVisibility(View.GONE);
                        errorpass1.setVisibility(View.GONE);
                    }
                }

            }
        });
        Ipassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().matches("")) {
                    errorpass1.setVisibility(View.VISIBLE);
                    errorpass2.setVisibility(View.GONE);
                    passwordflag = false;
                    setDongyEnble();
                    checkpasswordtrue = false;
                } else if (s.toString() != null && s.toString().length() <= 5) {
                    checkpasswordtrue = false;
                    errorpass2.setVisibility(View.VISIBLE);
                    errorpass1.setVisibility(View.GONE);
                } else {
                    checkpasswordtrue = true;
                    passwordflag = true;
                    setDongyEnble();
                    errorpass1.setVisibility(View.GONE);
                    errorpass2.setVisibility(View.GONE);
                }
            }
        });
        Iconfirmpassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == true) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    if (Iconfirmpassword.getText().toString().matches("")) {
                        errorcfpassword2.setVisibility(View.VISIBLE)
                        ;
                        errorcfpassword.setVisibility(View.GONE);
                    } else if (Iconfirmpassword.getText().toString().matches(Ipassword.getText().toString())) {
                        errorcfpassword.setVisibility(View.GONE);
                        errorcfpassword2.setVisibility(View.GONE);
                    } else if (Iconfirmpassword.getText().toString().length() > 0) {
                        errorcfpassword.setVisibility(View.VISIBLE);
                        errorcfpassword2.setVisibility(View.GONE);
                    }
                } else {

                    if (Iconfirmpassword.getText().toString().matches("")) {
                        errorcfpassword2.setVisibility(View.VISIBLE);
                    } else if (Iconfirmpassword.getText().toString().matches(Ipassword.getText().toString())) {
                        errorcfpassword.setVisibility(View.GONE);
                        errorcfpassword2.setVisibility(View.GONE);
                    } else if (Iconfirmpassword.getText().toString().length() > 0) {
                        errorcfpassword.setVisibility(View.VISIBLE);
                        errorcfpassword2.setVisibility(View.GONE);
                    }
                }
            }
        });
        Iconfirmpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Iconfirmpassword.getText().toString().matches("")) {
                    errorcfpassword2.setVisibility(View.VISIBLE)
                    ;
                    errorcfpassword.setVisibility(View.GONE);
                } else if (Iconfirmpassword.getText().toString().matches(Ipassword.getText().toString())) {
                    errorcfpassword.setVisibility(View.GONE);
                    errorcfpassword2.setVisibility(View.GONE);
                } else if (Iconfirmpassword.getText().toString().length() > 0) {
                    errorcfpassword.setVisibility(View.VISIBLE);
                    errorcfpassword2.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().matches("")) {

                    confirmpasswordflag = false;
                    errorcfpassword2.setVisibility(View.VISIBLE);
                    setDongyEnble();

                } else if (s.toString().matches(Ipassword.getText().toString())) {
                    errorcfpassword.setVisibility(View.GONE);
                    confirmpasswordflag = true;
                    setDongyEnble();
                } else {
                    confirmpasswordflag = false;
                    setDongyEnble();
                    errorcfpassword2.setVisibility(View.GONE);

                }
            }
        });
        Iphone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == true) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

                    if (Iphone.getText().toString().matches("")) {
                        errorphone1.setVisibility(View.VISIBLE);
                        errorphone2.setVisibility(View.GONE);
                    } else if ((Iphone.getText().toString().length() >= 10)) {
                        errorphone2.setVisibility(View.GONE);
                        errorphone1.setVisibility(View.GONE);
                    } else if (Iphone.getText().toString().length() > 0 && Iphone.getText().toString().length() < 10) {
                        errorphone2.setVisibility(View.VISIBLE);
                        errorphone1.setVisibility(View.GONE);
                    }
                } else {
                    if (Iphone.getText().toString().matches("")) {
                        errorphone1.setVisibility(View.VISIBLE);
                        errorphone2.setVisibility(View.GONE);
                    } else if ((Iphone.getText().toString().length() >= 10)) {
                        errorphone2.setVisibility(View.GONE);
                        errorphone1.setVisibility(View.GONE);
                    } else if (Iphone.getText().toString().length() > 0 && Iphone.getText().toString().length() < 10) {
                        errorphone2.setVisibility(View.VISIBLE);
                        errorphone1.setVisibility(View.GONE);
                    }

                }
            }
        });
        Iphone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Iphone.getText().toString().matches("")) {
                    errorphone1.setVisibility(View.VISIBLE);
                    errorphone2.setVisibility(View.GONE);
                } else if ((Iphone.getText().toString().length() >= 10)) {
                    errorphone2.setVisibility(View.GONE);
                    errorphone1.setVisibility(View.GONE);
                } else if (Iphone.getText().toString().length() > 0 && Iphone.getText().toString().length() < 10) {
                    errorphone2.setVisibility(View.VISIBLE);
                    errorphone1.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().matches("")) {
                    phoneflag = false;

                    errorphone2.setVisibility(View.GONE);
                    setDongyEnble();
                } else if (Iphone.getText().toString().length() <= 9) {
                    phoneflag = false;

                    errorphone1.setVisibility(View.GONE);
                    setDongyEnble();
                } else {
                    phoneflag = true;
                    setDongyEnble();
                    errorphone1.setVisibility(View.GONE);
                    errorphone2.setVisibility(View.GONE);
                }

            }
        });
        Idia_chi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == true) {

                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    if (Idia_chi.getText().toString().matches("")) {
                        addressflag = false;
                        setDongyEnble();
                        erroraddress.setVisibility(View.VISIBLE);

                    } else {
                        erroraddress.setVisibility(View.GONE);
                    }
                } else {

                    if (Idia_chi.getText().toString().matches("")) {
                        addressflag = false;
                        setDongyEnble();
                        erroraddress.setVisibility(View.VISIBLE);

                    } else {
                        Idia_chi.setText(result1);
                        erroraddress.setVisibility(View.GONE);
                        erroraddress.setVisibility(View.GONE);
                    }

                }
            }
        });
        Idia_chi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Idia_chi.getText().toString().matches("")) {
                    erroraddress.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().matches("")) {
                    addressflag = false;
                    setDongyEnble();
                    erroraddress.setVisibility(View.VISIBLE);
                    erroraddress2.setVisibility(View.GONE);

                } else {
                    erroraddress.setVisibility(View.GONE);
                    erroraddress2.setVisibility(View.GONE);
                    checkdiachi(s.toString());
                }
            }
        });


        //CheckBox
        prefCheckBox = (CheckBox) findViewById(R.id.user);
//        prefEditText = (TextView) findViewById(R.id.prefEditText);
        provider = (CheckBox) findViewById(R.id.provider);
        provider.setOnCheckedChangeListener(listener);
        user = (CheckBox) findViewById(R.id.user);
        user.setOnCheckedChangeListener(listener);

        //Popup
        checkPC = (CheckBox) findViewById(R.id.PC);
        Laptop = (CheckBox) findViewById(R.id.Laptop);
        scan = (CheckBox) findViewById(R.id.scan);
        fax = (CheckBox) findViewById(R.id.fax);
        photocopy = (CheckBox) findViewById(R.id.photocopy);
        Mayin = (CheckBox) findViewById(R.id.Mayin);

        loadPref();

    }

    //
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_frm_dang_ky, menu);
//        return true;
//    }
    @Override
    public void onBackPressed() {
        final Intent intent = new Intent(this, frmDK_DN.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent myIntent = new Intent(getApplicationContext(), frmDK_DN.class);
        startActivityForResult(myIntent, 0);

        return true;


        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
        //CheckBoxDiaup
//        Intent intent = new Intent();
//        intent.setClass(frmDangKy.this, setPreferenceActivity.class);
//        startActivityForResult(intent, 0);

//        return super.onOptionsItemSelected(item);

    }

    public void showDatePickerDialog() {
        final DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                txtDate.setText((dayOfMonth) + "/" + (monthOfYear + 1) + "/" + year);
                cal.set(year, monthOfYear, dayOfMonth);

            }
        };
        String s = txtDate.getText() + "";
        String strArrtmp[] = s.split("/");
        int ngay = Integer.parseInt(strArrtmp[0]);
        int thang = Integer.parseInt(strArrtmp[1]) - 1;
        int nam = Integer.parseInt(strArrtmp[2]);
        DatePickerDialog pic = new DatePickerDialog(
                frmDangKy.this,
                callback, nam, thang, ngay);
        pic.setTitle("");
        pic.show();
    }

//    private void addEventFormWidgets() {
//        btDate.setOnClickListener(new myButtonEvent());
//    }
//
//    private class myButtonEvent implements View.OnClickListener {
//
//        @Override
//        public void onClick(View v) {
//            switch (v.getId()) {
//                case R.id.buttonDate:
//                    showDatePickerDialog();
//                    break;
//            }
//        }
//    }
//
//    public void getFormWidgets() {
//        txtDate = (TextView) findViewById(R.id.txtDate);
//        btDate = (Button) findViewById(R.id.buttonDate);
//    }
//
//    public void getDefautInfor() {
//        cal = Calendar.getInstance();
//        SimpleDateFormat dtf = null;
//        dtf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//        String strDate = dtf.format(cal.getTime());
//        txtDate.setText(strDate);
//
//
//    }

//    void loadImageFromCamera() {
//        Intent takePicture = new Intent("android.media.action.IMAGE_CAPTURE");
//        File photo = null;
//        try {
//            // place where to store camera taken picture
//            photo = this.createTemporaryFile("picture", ".jpg");
//            photo.delete();
//        } catch (Exception e) {
//
//        }
//        mImageUri = Uri.fromFile(photo);
//        takePicture.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
//        startActivityForResult(takePicture, 0);
//    }
//
//    private File createTemporaryFile(String part, String ext) throws Exception {
//        File tempDir = Environment.getExternalStorageDirectory();
//        tempDir = new File(tempDir.getAbsolutePath() + "/.temp/");
//        if (!tempDir.exists()) {
//            tempDir.mkdir();
//        }
//        return File.createTempFile(part, ext, tempDir);
//    }
//
//    public void grabImage() {
//        this.getContentResolver().notifyChange(mImageUri, null);
//        ContentResolver cr = this.getContentResolver();
//        try {
//            originImage = android.provider.MediaStore.Images.Media.getBitmap(cr, mImageUri);
//        } catch (Exception e) {
//
//        }
//    }

//    public static Bitmap getResizedBitmap(Bitmap image, int newHeight, int newWidth) {
//        int width = image.getWidth();
//        int height = image.getHeight();
//        float scaleWidth = ((float) newWidth) / width;
//        float scaleHeight = ((float) newHeight) / height;
//        // create a matrix for the manipulation
//        Matrix matrix = new Matrix();
//        // resize the bit map
//        matrix.postScale(scaleWidth, scaleHeight);
//        // recreate the new Bitmap
//        Bitmap resizedBitmap = Bitmap.createBitmap(image, 0, 0, width, height,
//                matrix, false);
//        return resizedBitmap;
//    }

    private void loadPref() {
        SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        boolean my_checkbox_preference = mySharedPreferences.getBoolean("checkbox_preference", false);
        prefCheckBox.setChecked(my_checkbox_preference);

//        String my_edittext_preference = mySharedPreferences.getString("edittext_preference", "");
//        prefEditText.setText(my_edittext_preference);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {

                thumbnail = (Bitmap) data.getExtras().get("data");
                Uri mImageUri2 = data.getData();

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
//                Intent intent = new Intent("com.android.camera.action.CROP");
//                intent .setDataAndType(mImageUri2, "image/*");
//                intent.putExtra("outputX",thumbnail.getWidth());
//                intent.putExtra("outputY", thumbnail.getHeight());
//                intent.putExtra("aspectX", 1);
//                intent.putExtra("aspectY", 1);
//                intent.putExtra("scale", true);
//                intent.putExtra("return-data", false);
//                startActivityForResult(intent,REQUEST_CAMERA);
                c.setImageBitmap(thumbnail);

                TextView chonanh = (TextView) findViewById(R.id.chonanh);
                chonanh.setVisibility(View.GONE);
//                bntImage.setImageBitmap(thumbnail);
                selectedImageflag = true;
                setDongyEnble();
                BitMapToString(thumbnail);


            } else if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};
                Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                        null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();
                String selectedImagePath = cursor.getString(column_index);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                AssetFileDescriptor fileDescriptor = null;
                BitmapFactory.decodeFile(selectedImagePath, options);

                final int REQUIRED_SIZE = 200;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                        && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                bm = BitmapFactory.decodeFile(selectedImagePath, options);
//                Intent intent = new Intent("com.android.camera.action.CROP");
//                intent .setDataAndType(selectedImageUri, "image/*");
//                intent.putExtra("crop", "true");
//                intent.putExtra("outputX",bm.getWidth());
//                intent.putExtra("outputY", bm.getHeight());
//                intent.putExtra("aspectX", 1);
//                intent.putExtra("aspectY", 1);
//                intent.putExtra("scale", true);
//                intent.putExtra("return-data", false);
//                startActivityForResult(intent, 2);
                c.setImageBitmap(bm);
                TextView chonanh = (TextView) findViewById(R.id.chonanh);
                chonanh.setVisibility(View.GONE);
                //bntImage.setImageBitmap(bm);
                selectedImageflag = true;
                setDongyEnble();
                BitMapToString(bm);
            }
        }

    }

    private CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {

                switch (buttonView.getId()) {
                    case R.id.user:
                        user.setChecked(true);
                        provider.setChecked(false);
                        user.setButtonDrawable(R.drawable.checked);
                        provider.setButtonDrawable(R.drawable.check_white);
                        checkedbox = "[1]";
                        checkAccountType = true;
                        setDongyEnble();
                        break;
                    case R.id.provider:
                        provider.setChecked(true);
                        user.setChecked(false);
                        provider.setButtonDrawable(R.drawable.checked);
                        user.setButtonDrawable(R.drawable.check_white);
                        checkAccountType = true;
                        setDongyEnble();
                        showPopUp();

                }

            } else {
                checkAccountType = false;
                setDongyEnble();
                user.setButtonDrawable(R.drawable.check_white);
                provider.setButtonDrawable(R.drawable.check_white);
            }

            if (checkAccountType == false) {

                checkfaxInvisible = false;
                checkPCInvisible = false;
                checkLaptopInvisible = false;
                checkMayinInvisible = false;
                checkMayPhotoInvisible = false;
                checkScanInvisible = false;
                cbPC.setVisibility(View.GONE);
                cbLaptop.setVisibility(View.GONE);
                cbMayin.setVisibility(View.GONE);
                cbFax.setVisibility(View.GONE);
                cbScan.setVisibility(View.GONE);
                cbPhoto.setVisibility(View.GONE);
                chuyenmon.setVisibility(View.GONE);
                list.clear();
            }
        }
    };

    public void showPopUp() {

        final AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
//        helpBuilder.setTitle("Chuyên môn");
        helpBuilder.setMessage("Chọn chuyên môn sửa chữa");
        LayoutInflater inflater = getLayoutInflater();
        final View checkboxLayout = inflater.inflate(R.layout.popuplayout, null);
        helpBuilder.setView(checkboxLayout);
        pc = (CheckBox) checkboxLayout.findViewById(R.id.PC);
        Laptop1 = (CheckBox) checkboxLayout.findViewById(R.id.Laptop);
        photo = (CheckBox) checkboxLayout.findViewById(R.id.photocopy);
        mayin = (CheckBox) checkboxLayout.findViewById(R.id.Mayin);
        mayfax = (CheckBox) checkboxLayout.findViewById(R.id.fax);
        scan1 = (CheckBox) checkboxLayout.findViewById(R.id.scan);
        Button okpopup = (Button) checkboxLayout.findViewById(R.id.okpopup);
        pc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    pc.setButtonDrawable(R.drawable.checked);
                } else {
                    pc.setButtonDrawable(R.drawable.check_white);
                }
            }

        });
        Laptop1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Laptop1.setButtonDrawable(R.drawable.checked);
                } else {
                    Laptop1.setButtonDrawable(R.drawable.check_white);
                }
            }
        });
        photo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    photo.setButtonDrawable(R.drawable.checked);
                } else {
                    photo.setButtonDrawable(R.drawable.check_white);
                }
            }
        });
        mayin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mayin.setButtonDrawable(R.drawable.checked);
                } else {
                    mayin.setButtonDrawable(R.drawable.check_white);
                }
            }
        });
        mayfax.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mayfax.setButtonDrawable(R.drawable.checked);
                } else {
                    mayfax.setButtonDrawable(R.drawable.check_white);
                }
            }
        });
        scan1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    scan1.setButtonDrawable(R.drawable.checked);
                } else {
                    scan1.setButtonDrawable(R.drawable.check_white);
                }
            }
        });
        final AlertDialog show = helpBuilder.show();
        helpBuilder.setCancelable(false);

//           setPositiveButton("",
//                   new DialogInterface.OnClickListener() {
//
//                       public void onClick(DialogInterface dialog, int which) {
        okpopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (pc.isChecked()) {
                    if (!list.contains(3)) {
                        list.add(3);
                        cbPC.setVisibility(View.VISIBLE);
                        checkPCInvisible = true;

                    }
                } else if (list.contains(3)) {
                    checkPCInvisible = false;
                    cbPC.setVisibility(View.GONE);


                }
                if (Laptop1.isChecked()) {
                    if (!list.contains(4)) {
                        list.add(4);
                        cbLaptop.setVisibility(View.VISIBLE);
                        checkLaptopInvisible = true;

                    }
                } else if (list.contains(4)) {
                    checkLaptopInvisible = false;
                    cbLaptop.setVisibility(View.GONE);


                }
                if (mayin.isChecked()) {
                    if (!list.contains(5)) {
                        list.add(5);
                        checkMayinInvisible = true;
                        cbMayin.setVisibility(View.VISIBLE);

                    }
                } else if (list.contains(5)) {
                    checkMayinInvisible = false;
                    cbMayin.setVisibility(View.GONE);


                }
                if (photo.isChecked()) {
                    if (!list.contains(6)) {
                        list.add(6);
                        checkMayPhotoInvisible = true;
                        cbPhoto.setVisibility(View.VISIBLE);

                    }
                } else if (list.contains(6)) {
                    checkMayPhotoInvisible = false;
                    cbPhoto.setVisibility(View.GONE);


                }
                if (scan1.isChecked()) {
                    if (!list.contains(7)) {
                        list.add(7);
                        checkScanInvisible = true;
                        cbScan.setVisibility(View.VISIBLE);

                    }
                } else if (list.contains(7)) {
                    checkScanInvisible = false;
                    cbScan.setVisibility(View.GONE);


                }
                if (mayfax.isChecked()) {
                    if (!list.contains(8)) {
                        list.add(8);
                        checkfaxInvisible = true;
                        cbFax.setVisibility(View.VISIBLE);

                    }
                } else if (list.contains(8)) {
                    checkfaxInvisible = false;
                    cbFax.setVisibility(View.GONE);


                }
                // checkedbox.joi = String.join(",", list);
                checkedbox = TextUtils.join(",", list);
                checkedbox = "[" + checkedbox;
                checkedbox = checkedbox + "]";
                if (checkPCInvisible == true || checkLaptopInvisible == true || checkMayinInvisible == true || checkMayPhotoInvisible == true || checkfaxInvisible == true || checkScanInvisible == true) {
                    chuyenmon.setVisibility(View.VISIBLE);
                } else if (checkPCInvisible == false && checkLaptopInvisible == false && checkMayinInvisible == false && checkMayPhotoInvisible == false && checkfaxInvisible == false && checkScanInvisible == false) {
                    chuyenmon.setVisibility(View.GONE);
                    provider.setChecked(false);
                }
                show.dismiss();
            }
        });


        // Remember, create doesn't show the dialog
//        AlertDialog helpDialog = helpBuilder.create();
//        helpDialog.show();

    }

    //    private CompoundButton.OnCheckedChangeListener checkpopup = new CompoundButton.OnCheckedChangeListener() {
//        @Override
//        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//            if (isChecked)
//                {
//                   checkPC.setChecked(true);
//
////                    switch (buttonView.getId())
////                    {
////                        case
////                        R.id.PC:
////
////                            checkPC.setChecked(true);
////                            arrayListCheck.add(0);
////                            break;
////                        case
////                                R.id.Laptop:
////                            Laptop.setChecked(true);
////                            arrayListCheck.add(1);
////                            break;
////                        case
////                                R.id.photocopy:
////                            photocopy.setChecked(true);
////                            arrayListCheck.add(2);
////                            break;
////                        case
////                                R.id.scan:
////                            scan.setChecked(true);
////                            arrayListCheck.add(3);
////                            break;
////                        case
////                                R.id.fax:
////                            fax.setChecked(true);
////                            arrayListCheck.add(4);
////                            break;
////                        case
////                                R.id.Mayin:
////                            Mayin.setChecked(true);
////                            arrayListCheck.add(5);
////
////                    }
//
//
//                }
//        }
//    };
    public void setDongyEnble() {
        if (fullnameflag == true && emailflag == true && passwordflag == true && addressflag == true && selectedImageflag == true && checkAccountType == true) {
            dangkyok.setEnabled(true);
            dangkyok.setBackgroundColor(getResources().getColor(R.color.mauxanh));

        } else {
            dangkyok.setEnabled(false);
            dangkyok.setBackgroundColor(getResources().getColor(R.color.mauxam));
            dangkyok.setTextColor(getResources().getColor(R.color.actionbar_text));
        }

    }

    private void selectImage() {
        final CharSequence[] items = {"Chụp ảnh", "Chọn từ thư viện", "Hủy"};
        AlertDialog.Builder builder = new AlertDialog.Builder(frmDangKy.this);
        builder.setTitle("Tùy chọn");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Chụp ảnh")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                    TextView chonanh = (TextView) findViewById(R.id.chonanh);
                } else if (items[item].equals("Chọn từ thư viện")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                    TextView chonanh = (TextView) findViewById(R.id.chonanh);


                } else if (items[item].equals("Hủy")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    //Convert Image to String
    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream ByteStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, ByteStream);
        byte[] b = ByteStream.toByteArray();
        temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    //Convert String to Image
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

    //pawword to change md5
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
    }//--end md5


    public void ErrorLine() {
        if (checkErrorLineEmail == false) {
            errorline2.setVisibility(View.VISIBLE);

        } else if (checkErrorLineEmail == true || accdal.emailTontai == false) {
            errorline2.setVisibility(View.GONE);
        }

    }

    public boolean validate(String hex) {


        String emailPattern = "([a-zA-Z0-9\\+_\\-]+)(\\.[a-z0-9\\+_\\-]+)*@([a-z0-9\\-]+\\.)+[a-z]{2,6}";
        Pattern p = Pattern.compile(emailPattern);
        Matcher m = p.matcher(hex);
        if (m.matches()) {
            inVaild = true;
            checkErrorLineEmail = true;
            ErrorLine();
        }

        return inVaild;
    }

    public Result<String> getcheckEmail(String email) {

        try {

            RestClient restClient = new RestClient(url_checkemail);
            restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
            restClient.addParam(Account.EMAIL, email);
            restClient.execute(RequestMethod.POST);
            //if response success
            if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS) {
                String jsonObject = restClient.getResponse();
                Gson gson = new Gson();
                LoginParse getLoginJson = gson.fromJson(jsonObject, LoginParse.class);
                //if result from response success
                if (getLoginJson.getStatus().equalsIgnoreCase(Response.STAUS_FALSE)) {
                    emailTontai = false;
                    return new Result<String>(ResultStatus.FALSE, null, getLoginJson.getMessage());
                } else {
                    emailTontai = true;
                    return new Result<String>(ResultStatus.FALSE, null, getLoginJson.getMessage());
                }
            } else {
                return new Result<String>(ResultStatus.FALSE, context.getResources().getString(R.string.msg_can_not_connect_to_network));

            }
        } catch (Exception e) {
            Log.e(Def.ERROR, e.getMessage());
            return new Result<String>(ResultStatus.FALSE, e.getMessage());
        }
    }

    public void fieldNull() {

        if (fullnameflag == false) {
        } else if (emailflag == false) {
        } else if (checkpasswordtrue == false) {
            errorpass2.setVisibility(View.VISIBLE);
        } else if (confirmpasswordflag == false) {
            errorcfpassword.setVisibility(View.VISIBLE);
        } else if (phoneflag == false) {
            errorphone2.setVisibility(View.VISIBLE);
        } else if (addressflag == false) {
        } else if (selectedImageflag == false) {
        } else if (checkAccountType == false) {
        } else {
            return;
        }
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

    @Override
    public void onClick(View v) {
        selectImage();
    }

    public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {

        int targetWidth = 50;
        int targetHeight = 50;
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth),
                        ((float) targetHeight)) / 2),
                Path.Direction.CCW);

        canvas.clipPath(path);
        Bitmap sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap(sourceBitmap,
                new Rect(0, 0, sourceBitmap.getWidth(),
                        sourceBitmap.getHeight()),
                new Rect(0, 0, targetWidth,
                        targetHeight), null);
        return targetBitmap;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    public String capitalizeFirstLetter(String s) {
        array = s.toCharArray();
        // Uppercase first letter.
        if (array[0] == Character.toLowerCase(array[0])) {
            array[0] = Character.toUpperCase(array[0]);
        } else {
        }

        // Uppercase all letters that follow a whitespace character.
        for (int i = 1; i < array.length; i++) {
            if (Character.isWhitespace(array[i - 1])) {

                array[i] = Character.toUpperCase(array[i]);

            }

        }
        // Result.
        return new String(array);
    }

    public void popupthanhcong() {
        final AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View checkboxLayout = inflater.inflate(R.layout.popupdangky, null);

        helpBuilder.setView(checkboxLayout);
        // set dialog message
        helpBuilder
                .setCancelable(false);
        final Intent DN = new Intent(this, frmDangNhap.class);
        final AlertDialog show = helpBuilder.show();
        Button okpopup = (Button) checkboxLayout.findViewById(R.id.okpopup);
        okpopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(DN);
                dangkythanhcong = true;
                show.dismiss();
            }
        });


        // create alert dialog

    }

    public String checkname(String s) {
        array1 = s.toCharArray();
        for (int i = 0; i < array1.length; i++) {
            if (Character.isWhitespace(array1[0])) {
                errorname1.setVisibility(View.VISIBLE);
                errorname.setVisibility(View.GONE);
                fullnameflag = false;
                setDongyEnble();
            } else {
                result = capitalizeFirstLetter(Ifullname.getText().toString());
                fullnameflag = true;
                setDongyEnble();
                errorname.setVisibility(View.GONE);

            }
        }
            return new String(array1);
    }

    public String checkdiachi(String s)
    { array2 = s.toCharArray();
        for (int i = 0; i < array2.length; i++) {
            if (Character.isWhitespace(array2[0])) {
                erroraddress2.setVisibility(View.VISIBLE);
                erroraddress.setVisibility(View.GONE);
                addressflag = false;
                setDongyEnble();
            } else {
                result1 = capitalizeFirstLetter(Idia_chi.getText().toString());
                addressflag = true;
                setDongyEnble();
                erroraddress.setVisibility(View.GONE);
            }
        }
        return new String (array2);
    }

}
