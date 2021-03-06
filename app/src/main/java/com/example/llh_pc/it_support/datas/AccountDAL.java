package com.example.llh_pc.it_support.datas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.activities.frmDK_DN;
import com.example.llh_pc.it_support.activities.frmDangKy;
import com.example.llh_pc.it_support.models.Account;
import com.example.llh_pc.it_support.models.JsonParses.LoginParse;
import com.example.llh_pc.it_support.models.Result;
import com.example.llh_pc.it_support.models.ResultStatus;
import com.example.llh_pc.it_support.restclients.RequestMethod;
import com.example.llh_pc.it_support.restclients.Response;
import com.example.llh_pc.it_support.restclients.RestClient;
import com.example.llh_pc.it_support.utils.CommonFunction;
import com.example.llh_pc.it_support.utils.Interfaces.Def;
import com.example.llh_pc.it_support.utils.SharePreference;
import com.google.gson.Gson;

/**
 * Created by Khanh Vo on 9/7/2015.
 * Email: khanhvo@innoria.com || idkhanhvo272@gmail.com
 * Phone number: 093 28 11 291
 */


public class AccountDAL {

    frmDangKy frmDK = new frmDangKy();
    private Context context;
    public static boolean emailTontai =false;
    public static String applyEmail;
    private String android_id = Def.STRING_EMPTY;
    //URL API
    private String url_dangky = Def.API_BASE_LINK + Def.API_CREATE + Def.API_FORMAT_JSON;
    private  String url_get_account_info_by_id = Def.API_BASE_LINK+Def.API_GET_ACCOUNT_INFO_BY_ID +Def.API_FORMAT_JSON;
    private boolean is_network = false;
    private SharePreference share_preference;
    public AccountDAL(Context current){
        this.context = current;
        is_network = CommonFunction.isNetworkOnline(context);
        share_preference = new SharePreference(context);

    }


    /**
     * get login from server
//     * @param account
     * @return
/    */
//    public Result<String> getLogin(Account account, boolean remember){
//        try{
//            if(!is_network){
//                return new Result<String>(ResultStatus.FALSE,context.getResources().getString(R.string.msg_can_not_connect_to_network));
//            }
//            RestClient restClient = new RestClient(url_login);
//
//            restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
//            restClient.addParam(Account.USERNAME, account.getUsername());
//            restClient.addParam(Account.PASSWORD, account.getPassword());
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
//    }//--end getLogin
    public Result<String> getSignUp (String email, String pass,String cfpass,String fullname,String phone,String address,String image,String accountType){
        try{

            if(!is_network){
                return new Result<String>(ResultStatus.FALSE,context.getResources().getString(R.string.msg_can_not_connect_to_network));
            }
            RestClient restClient = new RestClient(url_dangky);


            restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
            restClient.addParam(Account.EMAIL, email);
            restClient.addParam(Account.PASSWORD, pass);
            restClient.addParam(Account.CONFIRM_PASSWORD,cfpass);
            restClient.addParam(Account.FULL_NAME,fullname);
            restClient.addParam(Account.PHONE_NUMBER,phone);
            restClient.addParam(Account.ADDRESS,address);
            restClient.addParam(Account.AVATAR, image);
            restClient.addParam(Account.ACCOUNT_TYPE, accountType);


//            restClient.addParam(Account.JOB_TYPE,jobType);
//            restClient.addParam(Account.CONFIRM_PASSWORD,frmDK.md5(CP));
//            restClient.addParam(Account.FULL_NAME, frmDK.getIfullname().getText().toString());
//            restClient.addParam(Account.PHONE_NUMBER,frmDK.getIphone().getText().toString());
//            restClient.addParam(Account.ADDRESS,frmDK.getIdia_chi().getText().toString());
            restClient.execute(RequestMethod.POST);

            //if response success
            if(restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS){
                String jsonObject = restClient.getResponse();
                Gson gson = new Gson();
                LoginParse getLoginJson = gson.fromJson(jsonObject, LoginParse.class);

                //if result from response success
                if(getLoginJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS)){
                    emailTontai =false;
                   applyEmail = email;
                    Intent intent = new Intent(context, frmDK_DN.class);
                    return new Result<String>(ResultStatus.FALSE, null, getLoginJson.getMessage());
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
            emailTontai= true;
            Log.e(Def.ERROR, e.getMessage());
            return new Result<String>(ResultStatus.FALSE, e.getMessage());
        }
    }
}
