package com.example.llh_pc.it_support.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.datas.AccountDAL;
import com.example.llh_pc.it_support.fragments.DateTimePicker;

import org.apache.commons.io.output.ByteArrayOutputStream;

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

public class frmDangKy extends ActionBarActivity {

    AccountDAL accdal;
    TextView txtDate;
    //avatar
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
    boolean inVaild =false;

    public ArrayList<View> listEditText = new ArrayList<>();
    //API
//    private Context context;
//    private String url_login = Def.API_BASE_LINK + Def.API_LOGIN + Def.API_FORMAT_JSON;
//    private boolean is_network = false;
//    private SharePreference share_preference;


    //Camera
    public final static int REQUEST_CAMERA = 1;
    public final static int SELECT_FILE = 2;
    Calendar cal;
    public Uri mImageUri;
    ImageButton bntImage;
    Bitmap originImage;
    CheckBox prefCheckBox, provider, user, checkPC, Mayin, scan, fax, Laptop, photocopy;
    TextView prefEditText, cbPC, cbLaptop, cbPhoto, cbScan, cbFax, cbMayin, chuyenmon, errorline1, errorline2;
    public static EditText Ifullname, Iemail, Ipassword, Iconfirmpassword, Iphone, Idia_chi;
    ArrayList<DateTimePicker> arrDate = new ArrayList<DateTimePicker>();
    ArrayAdapter<DateTimePicker> adapter = null;
    String arr[] = {
            "Giới tính",
            "Nam",
            "Nữ"};
    ArrayList<Integer> arrayListCheck = new ArrayList<Integer>();


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
        final Intent DN = new Intent(this, frmDangNhap.class);
        accdal = new AccountDAL(getBaseContext());
        //ImageButton
        bntImage = (ImageButton) findViewById(R.id.bntImage);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) bntImage.getLayoutParams();
        params.height = 300;
        params.width = 300;
        bntImage.setLayoutParams(params);
        bntImage.setScaleType(ImageView.ScaleType.FIT_XY);
        //Pick Date & Gender
//        Spinner spin = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//        spin.setAdapter(adapter);
//        getFormWidgets();
//        getDefautInfor();
//        addEventFormWidgets();
        //Avatar
        bntImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Chup hinh nao", Toast.LENGTH_SHORT).show();
//                loadImageFromCamera();
//                grabImage();
//                getResizedBitmap(originImage, 20, 20);
                selectImage();

            }
        });


        dangkyok = (Button) findViewById(R.id.dangkyok);
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

        //cb
        cbPC = (TextView) findViewById(R.id.cbPC);
        cbLaptop = (TextView) findViewById(R.id.cbLaptop);
        cbMayin = (TextView) findViewById(R.id.cbMayin);
        cbPhoto = (TextView) findViewById(R.id.cbPhoto);
        cbFax = (TextView) findViewById(R.id.cbFax);
        cbScan = (TextView) findViewById(R.id.cbScan);
        chuyenmon = (TextView) findViewById(R.id.chuyenmon);
        //set value


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
                if (inVaild ==false)
                {   checkErrorLineEmail =false;
                    ErrorLine();

                }

                boolean allInformationtrue = false;
                try {

                    accdal.getSignUp(Iemail.getText().toString(), md5(Ipassword.getText().toString()), md5(Iconfirmpassword.getText().toString()), Ifullname.getText().toString(), Iphone.getText().toString(), Idia_chi.getText().toString(), temp, checkedbox);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                if (inVaild ==true) {
                    startActivity(DN);
                    Toast.makeText(getBaseContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
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

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().matches("")) {
                    fullnameflag = false;
                    setDongyEnble();

                } else {
                    fullnameflag = true;
                    setDongyEnble();
                }

            }
        });
        Iemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().matches(""))
                {

                    validate(s.toString());
                    checkErrorLineEmail=false;
                    emailflag = false;
                    setDongyEnble();


                }
//
                else {

                    inVaild =false;
                    emailflag = true;
                    setDongyEnble();
                    validate(s.toString());
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
                    passwordflag = false;
                    setDongyEnble();
                } else {
                    passwordflag = true;
                    setDongyEnble();
                }
            }
        });
        Iconfirmpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().matches("")) {
                    confirmpasswordflag = false;

                    setDongyEnble();
                } else if (s.toString().matches(Ipassword.getText().toString())) {

                    confirmpasswordflag = true;
                    setDongyEnble();
                } else {
                    confirmpasswordflag = false;
                    setDongyEnble();
                }
            }
        });
        Iphone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().matches("")) {
                    phoneflag = false;
                    setDongyEnble();
                } else {
                    phoneflag = true;
                    setDongyEnble();
                }
            }
        });
        Idia_chi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().matches("")) {
                    addressflag = false;
                    setDongyEnble();

                } else {
                    addressflag = true;
                    setDongyEnble();

                }
            }
        });


        //CheckBox
        prefCheckBox = (CheckBox) findViewById(R.id.user);
        prefEditText = (TextView) findViewById(R.id.prefEditText);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_frm_dang_ky, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        //CheckBoxDiaup
//        Intent intent = new Intent();
//        intent.setClass(frmDangKy.this, setPreferenceActivity.class);
//        startActivityForResult(intent, 0);

        return super.onOptionsItemSelected(item);

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

        String my_edittext_preference = mySharedPreferences.getString("edittext_preference", "");
        prefEditText.setText(my_edittext_preference);

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
                bntImage.setImageBitmap(thumbnail);
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
                bntImage.setImageBitmap(bm);
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
                        checkedbox = "[1]";
                        checkAccountType = true;
                        setDongyEnble();
                        break;
                    case R.id.provider:
                        provider.setChecked(true);
                        user.setChecked(false);
                        checkAccountType = true;
                        setDongyEnble();
                        showPopUp();

                }


//                if( checkPCInvisible ==true|| checkLaptopInvisible ==true||checkMayinInvisible ==true||checkMayPhotoInvisible ==true||checkfaxInvisible==true||checkScanInvisible ==true)
//                {
//
//                    provider.setChecked(true);
//                }
//                else if (checkPCInvisible ==false)
//                {
//                    provider.setChecked(false);
//                }

            } else {
                checkAccountType = false;
                setDongyEnble();

            }
            if (checkAccountType == false) {
                cbPC.setVisibility(View.GONE);
                cbLaptop.setVisibility(View.GONE);
                cbMayin.setVisibility(View.GONE);
                cbFax.setVisibility(View.GONE);
                cbScan.setVisibility(View.GONE);
                cbPhoto.setVisibility(View.GONE);
                chuyenmon.setVisibility(View.INVISIBLE);
                list.clear();
            }
        }
    };

    private void showPopUp() {

        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        helpBuilder.setTitle("Chuyên môn");
        helpBuilder.setMessage("Chuyên môn sửa chữa");

        LayoutInflater inflater = getLayoutInflater();
        final View checkboxLayout = inflater.inflate(R.layout.popuplayout, null);
        helpBuilder.setView(checkboxLayout);

        helpBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        CheckBox pc = (CheckBox) ((AlertDialog) dialog).findViewById(R.id.PC);
                        CheckBox Laptop = (CheckBox) ((AlertDialog) dialog).findViewById(R.id.Laptop);
                        CheckBox photo = (CheckBox) ((AlertDialog) dialog).findViewById(R.id.photocopy);
                        CheckBox mayin = (CheckBox) ((AlertDialog) dialog).findViewById(R.id.Mayin);
                        CheckBox mayfax = (CheckBox) ((AlertDialog) dialog).findViewById(R.id.fax);
                        CheckBox scan = (CheckBox) ((AlertDialog) dialog).findViewById(R.id.scan);


                        if (pc.isChecked()) {
                            if (!list.contains(3)) {
                                list.add(3);
                                cbPC.setVisibility(View.VISIBLE);
                                checkPCInvisible = true;

                            }
                        } else if (list.contains(3)) {
                            checkPCInvisible = false;
                            cbPC.setVisibility(View.GONE);
//                                 for (int i = 0 ; i <= list.size();i++)
//                                 {
//                                     if(list.contains(3))
//                                     {
//                                         list.remove(i);
//                                     }
//                                 }

                        }
                        if (Laptop.isChecked()) {
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
                        if (scan.isChecked()) {
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
                            chuyenmon.setVisibility(View.INVISIBLE);
                        }
                    }
                });

        // Remember, create doesn't show the dialog
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
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
        if (fullnameflag == true && emailflag == true && passwordflag == true && confirmpasswordflag == true && phoneflag == true && addressflag == true && selectedImageflag == true && checkAccountType == true) {
            dangkyok.setEnabled(true);
        } else {
            dangkyok.setEnabled(false);
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

    //call API
//    public Result<String> getSignUp (){
//        try{
//
//            if(!is_network){
//                return new Result<String>(ResultStatus.FALSE,context.getResources().getString(R.string.msg_can_not_connect_to_network));
//            }
//            RestClient restClient = new RestClient(url_login);
//            String P = Ipassword.getText().toString();
//            String CP= Iconfirmpassword.getText().toString();
//
//            restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
//            restClient.addParam(Account.EMAIL, Iemail.getText().toString());
//            restClient.addParam(Account.PASSWORD, md5(P));
//            restClient.addParam(Account.CONFIRM_PASSWORD,md5(CP));
//            restClient.addParam(Account.FULL_NAME,Ifullname.getText().toString());
//            restClient.addParam(Account.PHONE_NUMBER,Iphone.getText().toString());
//            restClient.addParam(Account.ADDRESS,Idia_chi.getText().toString());
//            restClient.execute(RequestMethod.POST);
//
//            //if response success
//            if(restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS){
//                String jsonObject = restClient.getResponse();
//                Gson gson = new Gson();
//                LoginParse getLoginJson = gson.fromJson(jsonObject, LoginParse.class);
//
//                //if result from response success
//                if(getLoginJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS)){
//
//                    //save values into sharePreference
//                    share_preference.setUserId(getLoginJson.getResults().getAccount_id());
//                    share_preference.setToken(getLoginJson.getResults().getAccess_token());
//
//                    return new Result<String>(ResultStatus.TRUE, getLoginJson.getResults().getAccess_token());
//                }
//                else{
//                    return new Result<String>(ResultStatus.FALSE, null, getLoginJson.getMessage());
//                }
//            }
//            else{
//                return new Result<String>(ResultStatus.FALSE,context.getResources().getString(R.string.msg_can_not_connect_to_network));
//            }
//        }
//        catch(Exception e){
//            Log.e(Def.ERROR, e.getMessage());
//            return new Result<String>(ResultStatus.FALSE, e.getMessage());
//        }
//    }
    public void ErrorLine() {
        if (checkErrorLineEmail == false) {
            errorline2.setVisibility(View.VISIBLE);

        } else if (checkErrorLineEmail ==true) {
            errorline2.setVisibility(View.GONE);
        }

    }
    public  boolean validate(String hex) {


        String emailPattern = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
                + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\."
                + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+";
        Pattern p = Pattern.compile(emailPattern);
        Matcher m = p.matcher(hex);
        if (m.matches()) {
            inVaild = true;
            checkErrorLineEmail = true;
            ErrorLine();
        }
        return inVaild;
    }
}