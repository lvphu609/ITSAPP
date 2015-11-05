package com.example.llh_pc.it_support.utils.Events;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.internal.widget.DecorToolbar;
import android.view.View;
import android.widget.AdapterView;

import com.example.llh_pc.it_support.activities.DetailPickPost;
import com.example.llh_pc.it_support.activities.Search;
import com.example.llh_pc.it_support.activities.SearchMap;
import com.example.llh_pc.it_support.activities.frmChiTietPost;
import com.example.llh_pc.it_support.models.JsonParses.NotificationParse;
import com.example.llh_pc.it_support.models.JsonParses.PostDetailParse;
import com.example.llh_pc.it_support.models.LuuTruModel;
import com.example.llh_pc.it_support.models.NotificationDetail;
import com.example.llh_pc.it_support.models.PostDetail;
import com.example.llh_pc.it_support.models.UserPostDetail;
import com.example.llh_pc.it_support.restclients.RequestMethod;
import com.example.llh_pc.it_support.restclients.Response;
import com.example.llh_pc.it_support.restclients.RestClient;
import com.example.llh_pc.it_support.utils.Interfaces.Def;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by LLH-PC on 9/27/2015.
 */
public class eventDetailPost implements AdapterView.OnItemClickListener{

    public static final String url_get = Def.API_BASE_LINK + Def.API_Loadpostdetail + Def.API_FORMAT_JSON;
    public static  Context context;
    private ArrayList<LuuTruModel> arrayListPost;
    private NotificationDetail uD;

    public eventDetailPost(Context current, ArrayList<LuuTruModel> list) {
        this.context = current;
        this.arrayListPost = list;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LuuTruModel p = arrayListPost.get(position);
        String idPost = p.id;
        try {
            SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
            String token = sharedPreference.getString("token", "token");
            RestClient restClient = new RestClient(url_get);
            restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
            restClient.addHeader("token", token);
            restClient.addParam("id", idPost);
            restClient.execute(RequestMethod.POST);
            if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS) {
                String jsonObject = restClient.getResponse();
                Gson gson = new Gson();
                NotificationParse getLoginJson = gson.fromJson(jsonObject, NotificationParse.class);
                if (getLoginJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS)) {
                    uD = getLoginJson.getResults();
                    /*infortion post*/
                    String name = uD.getPost_type().getName();
                    String Location_name = uD.getLocation_name();
                    String content = uD.getContent();
                    /*--------------*/
                    /*infortion user*/
                    String full_name= uD.getNormal_account().getFull_name();
                    String phone_number = uD.getNormal_account().getPhone_number();
                    String address = uD.getNormal_account().getAddress();
                    /*--------------*/
                    Intent intent = new Intent(context, frmChiTietPost .class);
                    intent.putExtra("loaibaohong", name);
                    intent.putExtra("diachi", Location_name);
                    intent.putExtra("ghichu", content);
                    intent.putExtra("hoten", full_name);
                    intent.putExtra("dienthoai", phone_number);
                    intent.putExtra("diachinha", address);

                    /*infortion provider*/
                    String status = uD.getStatus();
                    if(status.equals("1"))
                    {
                        String picked_at = uD.getPicked_at();
                        String full_name_p = uD.getProvider_account().getFull_name();
                        String phone_number_p = uD.getProvider_account().getPhone_number();
                        intent.putExtra("updated_at", picked_at);
                        intent.putExtra("full_name", full_name_p);
                        intent.putExtra("phone_number", phone_number_p);
                    }
                        /*rating*/
                    /*---------------*/
                    context.startActivity(intent);






                    //String ID_PickPost = uD.getId();
                    //String ID_PickPost = uD.post_type.getId();
                    /*String loaibaohong = uD.post_type.getName();
                    String diachi = uD.location_name;
                    String ghichu = uD.content;
                    String hoten = uD.normal_account.full_name;
                    String dienthoai = uD.normal_account.phone_number;
                    String diachinha = uD.normal_account.getAddress();
                    *//*------------------------------------------------*//*
                    String updated_at = uD.updated_at;
                    String full_name = uD.provider_account.getFull_name();
                    String phone_number = uD.provider_account.getPhone_number();
                    Intent intent = new Intent(context, frmChiTietPost .class);
                    intent.putExtra("loaibaohong", loaibaohong);
                    intent.putExtra("diachi", diachi);
                    intent.putExtra("ghichu", ghichu);
                    intent.putExtra("hoten", hoten);
                    intent.putExtra("dienthoai", dienthoai);
                    intent.putExtra("diachinha", diachinha);
                    intent.putExtra("IDPostPost", ID_PickPost);
                    intent.putExtra("updated_at", updated_at);
                    //intent.putExtra("full_name", full_name);
                    intent.putExtra("phone_number", phone_number);
                    context.startActivity(intent);*/
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


