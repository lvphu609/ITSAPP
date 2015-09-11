package com.example.llh_pc.it_support.datas;

import android.content.Context;
import android.util.Log;

import com.example.llh_pc.it_support.models.JsonParses.LoginParse;
import com.example.llh_pc.it_support.utils.CommonFunction;
import com.google.gson.Gson;
import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.models.Account;


import com.example.llh_pc.it_support.models.Result;
import com.example.llh_pc.it_support.models.ResultStatus;
import com.example.llh_pc.it_support.restclients.RequestMethod;
import com.example.llh_pc.it_support.restclients.Response;
import com.example.llh_pc.it_support.restclients.RestClient;
import com.example.llh_pc.it_support.utils.Interfaces.Def;
import com.example.llh_pc.it_support.utils.SharePreference;

/**
 * Created by Khanh Vo on 9/7/2015.
 * Email: khanhvo@innoria.com || idkhanhvo272@gmail.com
 * Phone number: 093 28 11 291
 */


public class AccountDAL {

    private Context context;
    private String android_id = Def.STRING_EMPTY;
    //URL API
    private String url_login = Def.API_BASE_LINK + Def.API_LOGIN + Def.API_FORMAT_JSON;
    private boolean is_network = false;
    private SharePreference share_preference;
    public AccountDAL(Context current){
        this.context = current;
        is_network = CommonFunction.isNetworkOnline(context);
        share_preference = new SharePreference(context);
    }

    /**
     * get login from server
     * @param account
     * @return
     */
    public Result<String> getLogin(Account account, boolean remember){
        try{
            if(!is_network){
                return new Result<String>(ResultStatus.FALSE,context.getResources().getString(R.string.msg_can_not_connect_to_network));
            }
            RestClient restClient = new RestClient(url_login);

            restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
            restClient.addParam(Account.USERNAME, account.getUsername());
            restClient.addParam(Account.PASSWORD, account.getPassword());
            restClient.execute(RequestMethod.POST);

            //if response success
            if(restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS){
                String jsonObject = restClient.getResponse();
                Gson gson = new Gson();
                LoginParse getLoginJson = gson.fromJson(jsonObject, LoginParse.class);

                //if result from response success
                if(getLoginJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS)){

                    //save values into sharePreference
                    share_preference.setUserId(getLoginJson.getResults().getAccount_id());
                    share_preference.setToken(getLoginJson.getResults().getAccess_token());

                    return new Result<String>(ResultStatus.TRUE, getLoginJson.getResults().getAccess_token());
                }
                else{
                    return new Result<String>(ResultStatus.FALSE, null, getLoginJson.getMessage());
                }
            }
            else{
                return new Result<String>(ResultStatus.FALSE,context.getResources().getString(R.string.msg_can_not_connect_to_network));
            }
        }
        catch(Exception e){
            Log.e(Def.ERROR, e.getMessage());
            return new Result<String>(ResultStatus.FALSE, e.getMessage());
        }
    }//--end getLogin

}
