package com.example.llh_pc.it_support.utils.Events;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

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

            //if response success
            if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS) {
                String jsonObject = restClient.getResponse();
                Gson gson = new Gson();
                LoginParse getLoginJson = gson.fromJson(jsonObject, LoginParse.class);
                //if result from response success
                if (getLoginJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS)) {
                    //save values into sharePreference
                    Intent intent = new Intent(context, frmTaoMoiMatKhau.class);
                    intent.putExtra("mail",mail);
                    context.startActivity(intent);
                }else
                {

                }
            }
        }catch (Exception ex)
        {

        }
    }
}
