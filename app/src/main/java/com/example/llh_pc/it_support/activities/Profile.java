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
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.llh_pc.it_support.R;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Profile  extends AppCompatActivity implements InnoFunctionListener {
    ImageButton bntImage;
    public final static int REQUEST_CAMERA = 1;
    public final static int SELECT_FILE = 2;
    public static final String url_get_account_info_by_id = Def.API_BASE_LINK + Def.API_GET_ACCOUNT_INFO_BY_ID + Def.API_FORMAT_JSON;
    public static final String url_editprofile = Def.API_BASE_LINK + Def.API_CREATE + Def.API_FORMAT_JSON;
    public static final String url_change_password = Def.API_BASE_LINK + Def.API_ChangePassword + Def.API_FORMAT_JSON;
    private ArrayList<String> arrayListAccount = new ArrayList<String>();
    private ArrayList<String> arr = new ArrayList<String>();
    LinearLayout ll;
    frmDangKy frmDK = new frmDangKy();
    AccountDAL accdal;
    String email, full_name, avatar, phone, address, acctye,checkedbox,temp,arrayList;
    TextView user, provier, chuyenmon, resultText;
    Bitmap avatar1;
    ImageButton setavatar;
    EditText eemail, efullname, eadress, ephone, password, passcu, passmoi, cfpassmoi;
    Button pc, laptop, mayin, scan, mayfax, photocopy, editProfile, OK, changePass;
    ImageLoader imageload;
    Bitmap thumbnail, bm;
    ArrayList<Integer> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        bntImage = (ImageButton) findViewById(R.id.bntImage);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) bntImage.getLayoutParams();
        params.height = 300;
        params.width = 300;
        bntImage.setLayoutParams(params);
        bntImage.setScaleType(ImageView.ScaleType.FIT_XY);
        photocopy = (Button) findViewById(R.id.photocopy);
//
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());
        StrictMode.enableDefaults();

        accdal = new AccountDAL(getBaseContext());
        imageload = new ImageLoader(getBaseContext());


        SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(Profile.this);
        String name = sharedPreference.getString("token", "YourName");
        String id = sharedPreference.getString("id", "");

        Context context1;

        getAccount(name, id);
        setProfile();
        checkAcctype();
        editProfile();
        changeAcctype();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_profile, menu);
//        return true;
//    }

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

        return super.onOptionsItemSelected(item);
    }

    private void populateText(LinearLayout ll, View[] views, Context mContext) {
        Display display = getWindowManager().getDefaultDisplay();
        ll.removeAllViews();
        int maxWidth = display.getWidth() - 20;

        LinearLayout.LayoutParams params;
        LinearLayout newLL = new LinearLayout(mContext);
        newLL.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
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
            //my old code
            //TV = new TextView(mContext);
            //TV.setText(textArray[i]);
            //TV.setTextSize(size);  <<<< SET TEXT SIZE
            //TV.measure(0, 0);
            views[i].measure(0, 0);
            params = new LinearLayout.LayoutParams(views[i].getMeasuredWidth(),
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            //params.setMargins(5, 0, 5, 0);  // YOU CAN USE THIS
            //LL.addView(TV, params);
            LL.addView(views[i], params);
            LL.measure(0, 0);
            widthSoFar += views[i].getMeasuredWidth();// YOU MAY NEED TO ADD THE MARGINS
            if (widthSoFar >= maxWidth) {
                ll.addView(newLL);

                newLL = new LinearLayout(mContext);
                newLL.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT,
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
//
                    email = getAccountJson.getDKresults().getEmail().toString();
                    full_name = getAccountJson.getDKresults().getFull_name().toString();
                    phone = getAccountJson.getDKresults().getPhone_number().toString();
                    address = getAccountJson.getDKresults().getAddress().toString();
//                     acctye = getAccountJson.getDKresults().getAccount_type().toString();
                    arr = getAccountJson.getDKresults().getAccount_type();
                    avatar = getAccountJson.getDKresults().getAvatar().toString();
//                     avatar1 = StringToBitMap(avatar);
//                    arrayListAccount = getAccountJson.getResults();
//                    for(GetAccount str: arrayListAccount){
//                        String email = str.getEmail();
//                        String ho_ten = str.getFull_name();
//                    }
                }
            }


        } catch (Exception ex) {
        }

    }

    public void setProfile() {
        eemail = (EditText) findViewById(R.id.email);
        efullname = (EditText) findViewById(R.id.full_name);
        eadress = (EditText) findViewById(R.id.dia_chi);
        ephone = (EditText) findViewById(R.id.phone);
        password = (EditText) findViewById(R.id.password);
        setavatar = (ImageButton) findViewById(R.id.bntImage);
        imageload.DisplayImage(avatar, setavatar);

        eemail.setText(email);
        efullname.setText(full_name);
        eadress.setText(address);
        ephone.setText(phone);
//        password.setText("********");


    }

    public void editProfile() {
        OK = (Button) findViewById(R.id.OKProfile);
        changePass = (Button) findViewById(R.id.changepass);
        editProfile = (Button) findViewById(R.id.editProfile);
        final EditText editpassword = (EditText) findViewById(R.id.password);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editProfile.setEnabled(false);
                editProfile.setVisibility(View.INVISIBLE);
                OK.setEnabled(true);
                OK.setVisibility(View.VISIBLE);
                changePass.setEnabled(true);
                changePass.setVisibility(View.VISIBLE);
                editpassword.setVisibility(View.GONE);
                efullname.setEnabled(true);
                eadress.setEnabled(true);
                ephone.setEnabled(true);
                pc.setEnabled(true);
                laptop.setEnabled(true);
                photocopy.setEnabled(true);
                scan.setEnabled(true);
                mayfax.setEnabled(true);
                mayin.setEnabled(true);
                bntImage.setEnabled(true);
                bntImage.setClickable(true);
                bntImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectImage();
                    }
                });
                checkEditAcctype();
                changePass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showInputDialog();

                    }
                });


            }
        });
        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OK.getResources().getColor(R.color.actionbar_text);

                bntImage.setEnabled(false);
                bntImage.setClickable(false);
                editProfile.setEnabled(true);
                editProfile.setVisibility(View.VISIBLE);
                OK.setEnabled(false);
                OK.setVisibility(View.INVISIBLE);
                changePass.setEnabled(false);
                changePass.setVisibility(View.INVISIBLE);
                editpassword.setVisibility(View.VISIBLE);
                efullname.setEnabled(false);
                eadress.setEnabled(false);
                ephone.setEnabled(false);
                pc.setEnabled(false);
                laptop.setEnabled(false);
                mayin.setEnabled(false);
                photocopy.setEnabled(false);
                scan.setEnabled(false);
                mayfax.setEnabled(false);
                pc.setTextColor(getResources().getColor(R.color.mauxam));
                laptop.setTextColor(getResources().getColor(R.color.mauxam));
                photocopy.setTextColor(getResources().getColor(R.color.mauxam));
                scan.setTextColor(getResources().getColor(R.color.mauxam));
                mayfax.setTextColor(getResources().getColor(R.color.mauxam));
                mayin.setTextColor(getResources().getColor(R.color.mauxam));
                SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(Profile.this);
                String token = sharedPreference.getString("token", "YourName");
                String id = sharedPreference.getString("id", "");
                ChangeEditProfile(id,token,efullname.getText().toString(),ephone.getText().toString(),eadress.getText().toString(),temp,checkedbox);
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
                chuyenmon.setVisibility(View.INVISIBLE);
            } else {
                user.setVisibility(View.GONE);
                provier.setVisibility(View.VISIBLE);
                chuyenmon.setVisibility(View.VISIBLE);
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
                }
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


        passcu = (EditText) promptView.findViewById(R.id.passcu);
        passmoi = (EditText) promptView.findViewById(R.id.passmoi);
        cfpassmoi = (EditText) promptView.findViewById(R.id.cfpassmoi);

        // setup a dialog window

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(Profile.this);
                        String token = sharedPreference.getString("token", "YourName");
                        String id2 = sharedPreference.getString("id", "");
                        String status = sharedPreference.getString("status", "");
                        changePassword(id2, token, passcu.getText().toString(), passmoi.getText().toString(), cfpassmoi.getText().toString());

                        if (status.equals("success")) {
                            Toast.makeText(getBaseContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();

                            dialog.cancel();
                        } else {
                            Toast.makeText(getBaseContext(), "Vui lòng thử lại", Toast.LENGTH_SHORT).show();

                            dialog.cancel();
                        }

                    }
                });


        alertDialogBuilder.setCancelable(false)
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });


        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public void changePassword(String id, String token, String passcu, String passmoi, String cfpassmoi) {
        try {


            RestClient restClient = new RestClient(url_change_password);
            restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
            restClient.addParam("id", id);
            restClient.addParam(Account.OLD_PASSWORD, md5(passcu));
            restClient.addParam(Account.NEW_PASSWORD, md5(passmoi));
            restClient.addParam(Account.CONFIRM_PASSWORD, md5(cfpassmoi));
            restClient.addHeader("token", token);
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

    public   void showPopUp() {

        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
//        helpBuilder.setTitle("Chuyên môn");
        helpBuilder.setMessage("Chọn chuyên môn sửa chữa");

        LayoutInflater inflater = getLayoutInflater();
        final View checkboxLayout = inflater.inflate(R.layout.popuplayout, null);
        helpBuilder.setView(checkboxLayout);

        helpBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        CheckBox cbpc = (CheckBox) ((AlertDialog) dialog).findViewById(R.id.PC);
                        CheckBox cbLaptop = (CheckBox) ((AlertDialog) dialog).findViewById(R.id.Laptop);
                        CheckBox cbphoto = (CheckBox) ((AlertDialog) dialog).findViewById(R.id.photocopy);
                        CheckBox cbmayin = (CheckBox) ((AlertDialog) dialog).findViewById(R.id.Mayin);
                        CheckBox cbmayfax = (CheckBox) ((AlertDialog) dialog).findViewById(R.id.fax);
                        CheckBox cbscan = (CheckBox) ((AlertDialog) dialog).findViewById(R.id.scan);
                        user = (TextView) findViewById(R.id.enableUser);
                        provier = (TextView) findViewById(R.id.enableProvider);
                        pc = (Button) findViewById(R.id.bntPC);
                        laptop = (Button) findViewById(R.id.bntLaptpo);
                        scan = (Button) findViewById(R.id.bntMayScan);
                        photocopy = (Button) findViewById(R.id.photocopy);
                        mayfax = (Button) findViewById(R.id.bntMayfax);
                        mayin = (Button) findViewById(R.id.bntMayin);
                        chuyenmon = (TextView) findViewById(R.id.viewchuyenmon);
//                        boolean isChecked = getBooleanFromPreferences("isChecked");
//                        cbpc.setChecked(isChecked);
//                        cbLaptop.setChecked(isChecked);
//                        cbphoto.setChecked(isChecked);
//                        cbmayfax.setChecked(isChecked);
//                        cbscan.setChecked(isChecked);
//                        cbmayin.setChecked(isChecked);


                        for(int i =0; i< arr.size();i++)
                        {
                           arrayList= arr.get(i);

                        }

                            if (cbpc.isChecked()) {
                                if (!list.contains(3)|| !arrayList.equals("3")) {
                                    list.remove((Integer) 3);
                                    list.add(3);
                                    pc.setVisibility(View.VISIBLE);
                                    pc.setEnabled(true);
                                    pc.setTextColor(getResources().getColor(R.color.mauxanh));
//                                    Profile.this.putBooleanInPreferences(isChecked, "isChecked");

                                }


                            } else  {
                               list.remove((Integer) 3);
                                pc.setVisibility(View.GONE);
//

                            }
                            if (cbLaptop.isChecked()) {
                                if (!list.contains(4)|| !arrayList.equals("4")) {
                                    list.remove((Integer) 4);
                                    list.add(4);
                                    laptop.setVisibility(View.VISIBLE);
                                    laptop.setEnabled(true);
                                    laptop.setTextColor(getResources().getColor(R.color.mauxanh));

//                                    Profile.this.putBooleanInPreferences(isChecked, "isChecked");

                                }


                            } else  {

                                list.remove((Integer) 4);
                                laptop.setVisibility(View.GONE);


                            }
                            if (cbmayin.isChecked()) {
                                if (!list.contains(5)|| !arrayList.equals("5")) {
                                    list.remove((Integer) 5);
                                    list.add(5);
                                    mayin.setVisibility(View.VISIBLE);
                                    mayin.setEnabled(true);
                                    mayin.setTextColor(getResources().getColor(R.color.mauxanh));
//                                    Profile.this.putBooleanInPreferences(isChecked, "isChecked");
                                }
                            } else {

                                list.remove((Integer) 5);
                                mayin.setVisibility(View.GONE);


                            }
                            if (cbphoto.isChecked()) {
                                if (!list.contains(6)|| !arrayList.equals("6")) {
                                    list.remove((Integer) 6);
                                    list.add(6);
                                    photocopy.setVisibility(View.VISIBLE);
                                    photocopy.setEnabled(true);
                                    photocopy.setTextColor(getResources().getColor(R.color.mauxanh));
//                                    Profile.this.putBooleanInPreferences(isChecked, "isChecked");
                                }
                            } else {

                                list.remove((Integer) 6);
                                photocopy.setVisibility(View.GONE);


                            }
                            if (cbscan.isChecked()) {
                                if (!list.contains(7)|| !arrayList.equals("7")) {
                                    list.remove((Integer) 7);
                                    list.add(7);
                                    scan.setVisibility(View.VISIBLE);
                                    scan.setEnabled(true);
                                    scan.setTextColor(getResources().getColor(R.color.mauxanh));
//                                    Profile.this.putBooleanInPreferences(isChecked, "isChecked");
                                }
                            } else  {

                                list.remove((Integer) 7);
                                scan.setVisibility(View.GONE);


                            }
                            if (cbmayfax.isChecked()) {
                                if (!list.contains(8)|| !arrayList.equals("8")) {
                                    list.remove((Integer) 8);
                                    list.add(8);
                                    mayfax.setVisibility(View.VISIBLE);
                                    mayfax.setEnabled(true);
                                    mayfax.setTextColor(getResources().getColor(R.color.mauxanh));
//                                    Profile.this.putBooleanInPreferences(isChecked, "isChecked");
                                }
                            } else  {

                                list.remove((Integer) 8);
                                mayfax.setVisibility(View.GONE);


                            }


                        // checkedbox.joi = String.join(",", list);
                        checkedbox = TextUtils.join(",", list);
                        checkedbox = "[" + checkedbox;
                        checkedbox = checkedbox + "]";

                    }
                });

        // Remember, create doesn't show the dialog
        AlertDialog helpDialog = helpBuilder.create();
    helpDialog.show();

    }
public void  ChangeEditProfile (String id,String token,String full_name,String phone,String address,String avatar,String accountType) {

    try {


        RestClient restClient = new RestClient(url_editprofile);
        restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
        restClient.addParam("id", id);
        restClient.addHeader("token", token);
        restClient.addParam(Account.FULL_NAME, full_name);
        restClient.addParam(Account.PHONE_NUMBER,phone);
        restClient.addParam(Account.ADDRESS,address);
        restClient.addParam(Account.ACCOUNT_TYPE,accountType);
        restClient.addParam(Account.AVATAR,avatar);
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
                bntImage.setImageBitmap(thumbnail);
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
                BitMapToString(bm);

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
}
