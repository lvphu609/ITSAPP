package com.example.llh_pc.it_support.utils.Events;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.example.llh_pc.it_support.activities.Profile;
import com.example.llh_pc.it_support.activities.frmChildPost;
import com.example.llh_pc.it_support.models.GetAccount;
import com.example.llh_pc.it_support.models.JsonParses.AccountParse;
import com.example.llh_pc.it_support.models.JsonParses.PostParse;
import com.example.llh_pc.it_support.models.Post;
import com.example.llh_pc.it_support.restclients.RequestMethod;
import com.example.llh_pc.it_support.restclients.Response;
import com.example.llh_pc.it_support.restclients.RestClient;
import com.example.llh_pc.it_support.utils.Interfaces.Def;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by ZBOOK 15 on 9/22/2015.
 */
public class eventGetAccount {
    public static final String url_get_account_info_by_id = Def.API_BASE_LINK + Def.API_GET_ACCOUNT_INFO_BY_ID + Def.API_FORMAT_JSON;
    private Context context;
    private ArrayList<GetAccount> arrayListAccount;
    public eventGetAccount (Context current,ArrayList<GetAccount> listAccount) {
            this.context =current;
            this.arrayListAccount = listAccount;

    }

    public void getAccount() {

        try
        {
            RestClient restClient = new RestClient(url_get_account_info_by_id);
            restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
            restClient.execute(RequestMethod.GET);
            if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS)
            {
                String jsonObject = restClient.getResponse();
                Gson gson = new Gson();
                AccountParse getListPostJson = gson.fromJson(jsonObject, AccountParse.class);
                if(getListPostJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS))
                {
//                    arrayListAccount = getListPostJson.getResults();
                    for(GetAccount str: arrayListAccount){
                        String t = str.getEmail();
                    }
                }
            }



        }catch (Exception ex){}

    }
}
