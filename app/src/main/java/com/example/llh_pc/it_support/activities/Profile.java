package com.example.llh_pc.it_support.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.datas.AccountDAL;
import com.example.llh_pc.it_support.models.GetAccount;
import com.example.llh_pc.it_support.models.JsonParses.AccountParse;
import com.example.llh_pc.it_support.restclients.RequestMethod;
import com.example.llh_pc.it_support.restclients.Response;
import com.example.llh_pc.it_support.restclients.RestClient;
import com.example.llh_pc.it_support.utils.Images.ImageLoader;
import com.example.llh_pc.it_support.utils.Interfaces.Def;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Profile extends Activity {
 ImageButton bntImage;
    public static final String url_get_account_info_by_id = Def.API_BASE_LINK + Def.API_GET_ACCOUNT_INFO_BY_ID + Def.API_FORMAT_JSON;
    private ArrayList<GetAccount> arrayListAccount;
    private ArrayList<String> arr = new ArrayList<String>();
    LinearLayout ll;
    frmDangKy frmDK = new frmDangKy();
    AccountDAL accdal;
    String email,full_name,avatar,phone,address,acctye;
    TextView user,provier,chuyenmon,resultText;
    Bitmap avatar1;
    ImageButton setavatar;
    EditText eemail, efullname,  eadress,ephone,password,passcu,passmoi,cfpassmoi;
    Button pc,laptop,mayin,scan,mayfax,photocopy,editProfile,OK,changePass;
    ImageLoader imageload;
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
        imageload =new ImageLoader(getBaseContext());







        SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(Profile.this);
        String name = sharedPreference.getString("token", "YourName");
        String id = sharedPreference.getString("id","");

        Context context1;

       getAccount(name,id);
        setProfile();
        checkAcctype();
        editProfile();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
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

        return super.onOptionsItemSelected(item);
    }
    private void populateText(LinearLayout ll, View[] views , Context mContext) {
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

        for (int i = 0 ; i < views.length ; i++ ){
            LinearLayout LL = new LinearLayout(mContext);
            LL.setOrientation(LinearLayout.HORIZONTAL);
            LL.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM);
            LL.setLayoutParams(new ListView.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            //my old code
            //TV = new TextView(mContext);
            //TV.setText(textArray[i]);
            //TV.setTextSize(size);  <<<< SET TEXT SIZE
            //TV.measure(0, 0);
            views[i].measure(0,0);
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
    public void getAccount(String name,String id) {

        try
        {



            RestClient restClient = new RestClient(url_get_account_info_by_id);
            restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
            restClient.addParam("id",id);
            restClient.addHeader("token",name);
            restClient.execute(RequestMethod.POST);
            if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS)
            {
                String jsonObject = restClient.getResponse();
                Gson gson = new Gson();
                AccountParse getAccountJson = gson.fromJson(jsonObject, AccountParse.class);
                if(getAccountJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS))
                {
//
                     email = getAccountJson.getDKresults().getEmail().toString();
                     full_name = getAccountJson.getDKresults().getFull_name().toString();
                     phone = getAccountJson.getDKresults().getPhone_number().toString();
                     address =getAccountJson.getDKresults().getAddress().toString();
//                     acctye = getAccountJson.getDKresults().getAccount_type().toString();
                    arr = getAccountJson.getDKresults().getAccount_type();
                     avatar= getAccountJson.getDKresults().getAvatar().toString();
//                     avatar1 = StringToBitMap(avatar);
//                    arrayListAccount = getAccountJson.getResults();
//                    for(GetAccount str: arrayListAccount){
//                        String email = str.getEmail();
//                        String ho_ten = str.getFull_name();
//                    }
                }
            }



        }catch (Exception ex){}

    }
    public void setProfile()
    {
        eemail = (EditText) findViewById(R.id.email);
        efullname = (EditText) findViewById(R.id.full_name);
        eadress = (EditText) findViewById(R.id.dia_chi);
        ephone = (EditText) findViewById(R.id.phone);
        password = (EditText) findViewById(R.id.password);
        setavatar = (ImageButton) findViewById (R.id.bntImage);
        imageload.DisplayImage(avatar,setavatar);

        eemail.setText(email);
        efullname.setText(full_name);
        eadress.setText(address);
        ephone.setText(phone);
        password.setText("********");



    }
    public void editProfile()
    {
        OK = (Button) findViewById(R.id.OKProfile);
        changePass = (Button) findViewById(R.id.changepass);
        editProfile = (Button) findViewById(R.id.editProfile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile.setEnabled(false);
                editProfile.setVisibility(View.INVISIBLE);
                OK.setEnabled(true);
                OK.setVisibility(View.VISIBLE);
                changePass.setEnabled(true);
                changePass.setVisibility(View.VISIBLE);

                efullname.setEnabled(true);
                eadress.setEnabled(true);
                ephone.setEnabled(true);
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
                editProfile.setEnabled(true);
                editProfile.setVisibility(View.VISIBLE);
                OK.setEnabled(false);
                OK.setVisibility(View.INVISIBLE);
                changePass.setEnabled(false);
                changePass.setVisibility(View.INVISIBLE);
                efullname.setEnabled(false);
                eadress.setEnabled(false);
                ephone.setEnabled(false);

//                accdal.getSignUp(eemail.getText().toString(), password.getText().toString(), frmDK.Iconfirmpassword.getText().toString(), efullname.getText().toString(), ephone.getText().toString(), eadress.getText().toString(), frmDK.temp, frmDK.checkedbox);
            }
        });
    }
    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte= Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }
    public void  checkAcctype()
    {
        user = (TextView) findViewById(R.id.enableUser);
        provier = (TextView) findViewById(R.id.enableProvider);
        pc =  (Button)  findViewById(R.id.bntPC);
        laptop = (Button) findViewById(R.id.bntLaptpo);
        scan = (Button) findViewById(R.id.bntMayScan);
        photocopy = (Button) findViewById(R.id.photocopy);
        mayfax = (Button) findViewById(R.id.bntMayfax);
        mayin = (Button) findViewById(R.id.bntMayin);
        chuyenmon = (TextView) findViewById(R.id.viewchuyenmon);

        for (int i = 0 ; i < arr.size();i++)
        {


                if (arr.get(i).equals("1")) {
                    user.setVisibility(View.VISIBLE);
                    chuyenmon.setVisibility(View.INVISIBLE);
                } else {
                    user.setVisibility(View.GONE);
                    provier.setVisibility(View.VISIBLE);
                    chuyenmon.setVisibility(View.VISIBLE);
                    if(arr.get(i).equals("3"))
                    {pc.setVisibility(View.VISIBLE);}
                    else if(arr.get(i).equals("4"))
                    {laptop.setVisibility(View.VISIBLE);}
                    else if(arr.get(i).equals("5"))
                    {mayin.setVisibility(View.VISIBLE);}
                    else if(arr.get(i).equals("6"))
                    {photocopy.setVisibility(View.VISIBLE);}
                    else if(arr.get(i).equals("7"))
                    {scan.setVisibility(View.VISIBLE);}
                    else if(arr.get(i).equals("8"))
                    {mayfax.setVisibility(View.VISIBLE);}
                    else {}
                }


        }
//        if(acctye.toString().equals("[1]"))
//        {
//            user.setVisibility(View.VISIBLE);
//            chuyenmon.setVisibility(View.INVISIBLE);
//        }
//        else
//        {
//            user.setVisibility(View.GONE);
//            provier.setVisibility(View.VISIBLE);
//            chuyenmon.setVisibility(View.VISIBLE);
//            if(acctye.toString().equals("[3]"))
//            {
//
//                pc.setVisibility(View.VISIBLE);
//            }
//            if(acctye.toString().equals("[3,4]"))
//            {
//                pc.setVisibility(View.VISIBLE);
//                laptop.setVisibility(View.VISIBLE);
//            }
//            if(acctye.toString().equals("[3,4,5]"))
//            {
//                pc.setVisibility(View.VISIBLE);
//                laptop.setVisibility(View.VISIBLE);
//                mayin.setVisibility(View.VISIBLE);
//            }
//            if(acctye.toString().equals("[3,4,5,6]"))
//            {
//                pc.setVisibility(View.VISIBLE);
//                laptop.setVisibility(View.VISIBLE);
//                mayin.setVisibility(View.VISIBLE);
//                photocopy.setVisibility(View.VISIBLE);
//            }
//            if(acctye.toString().equals("[3,4,5,6,7]"))
//            {
//                pc.setVisibility(View.VISIBLE);
//                laptop.setVisibility(View.VISIBLE);
//                mayin.setVisibility(View.VISIBLE);
//                photocopy.setVisibility(View.VISIBLE);
//                scan.setVisibility(View.VISIBLE);
//            }
//            if(acctye.toString().equals("[3,4,5,6,7,8]"))
//            {
//                pc.setVisibility(View.VISIBLE);
//                laptop.setVisibility(View.VISIBLE);
//                mayin.setVisibility(View.VISIBLE);
//                photocopy.setVisibility(View.VISIBLE);
//                scan.setVisibility(View.VISIBLE);
//                mayfax.setVisibility(View.VISIBLE);
//            }
//            if(acctye.toString().equals("[4]"))
//            {laptop.setVisibility(View.VISIBLE);}
//            if(acctye.toString().equals("[4,5]"))
//            {laptop.setVisibility(View.VISIBLE);
//                mayin.setVisibility(View.VISIBLE);}
//            if(acctye.toString().equals("[4,5,6]"))
//            {laptop.setVisibility(View.VISIBLE);
//                mayin.setVisibility(View.VISIBLE);
//                photocopy.setVisibility(View.VISIBLE);}
//            if(acctye.toString().equals("[4,5,6,7]"))
//            {       laptop.setVisibility(View.VISIBLE);
//                mayin.setVisibility(View.VISIBLE);
//                photocopy.setVisibility(View.VISIBLE);
//                scan.setVisibility(View.VISIBLE);}
//            if(acctye.toString().equals("[4,5,6,7,8]"))
//             {    laptop.setVisibility(View.VISIBLE);
//                 mayin.setVisibility(View.VISIBLE);
//                 photocopy.setVisibility(View.VISIBLE);
//                 scan.setVisibility(View.VISIBLE);
//                 mayfax.setVisibility(View.VISIBLE);}
//            if(acctye.toString().equals("[5]"))
//            {  mayin.setVisibility(View.VISIBLE);}
//            if(acctye.toString().equals("[5,6]"))
//            { mayin.setVisibility(View.VISIBLE);
//                photocopy.setVisibility(View.VISIBLE);}
//            if(acctye.toString().equals("[5,6,7]"))
//            {    mayin.setVisibility(View.VISIBLE);
//                photocopy.setVisibility(View.VISIBLE);
//                scan.setVisibility(View.VISIBLE);}
//            if(acctye.toString().equals("[5,6,7,8]"))
//            {     mayin.setVisibility(View.VISIBLE);
//                photocopy.setVisibility(View.VISIBLE);
//                scan.setVisibility(View.VISIBLE);
//                mayfax.setVisibility(View.VISIBLE);}}
//            if(acctye.toString().equals("[6]"))
//            {photocopy.setVisibility(View.VISIBLE);}
//            if(acctye.toString().equals("[6,7]"))
//            { photocopy.setVisibility(View.VISIBLE);
//                scan.setVisibility(View.VISIBLE);}
//            if(acctye.toString().equals("[6,7,8]"))
//            { photocopy.setVisibility(View.VISIBLE);
//                scan.setVisibility(View.VISIBLE);
//                mayfax.setVisibility(View.VISIBLE);}
//            if(acctye.toString().equals("[7]"))
//            {scan.setVisibility(View.VISIBLE);}
//            if(acctye.toString().equals("[7,8]"))
//            {scan.setVisibility(View.VISIBLE);
//                mayfax.setVisibility(View.VISIBLE);}
//            if(acctye.toString().equals("[8]"))
//            {mayfax.setVisibility(View.VISIBLE);}

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
       if(passmoi.getText().toString() == "123456789")
       {
            alertDialogBuilder.setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
//                        resultText.setText("Hello, " + editText.getText());
                        }
                    });


       }
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
}
