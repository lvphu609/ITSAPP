package com.example.llh_pc.it_support.utils.Events;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;

import com.example.llh_pc.it_support.activities.ChiDuong;
import com.example.llh_pc.it_support.activities.DetailPickPost;
import com.example.llh_pc.it_support.activities.frmChiTietPost;
import com.example.llh_pc.it_support.models.JsonParses.PostDetailParse;
import com.example.llh_pc.it_support.models.LuuTruModel;
import com.example.llh_pc.it_support.models.UserPostDetail;
import com.example.llh_pc.it_support.restclients.RequestMethod;
import com.example.llh_pc.it_support.restclients.Response;
import com.example.llh_pc.it_support.restclients.RestClient;
import com.example.llh_pc.it_support.utils.Interfaces.Def;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by LLH-PC on 10/29/2015.
 */
public class eventDetailPickPost implements AdapterView.OnItemClickListener {
    public static final String url_get = Def.API_BASE_LINK + Def.API_Loadpostdetail + Def.API_FORMAT_JSON;
    public static Context context;
    private ArrayList<LuuTruModel> arrayListPost;
    private UserPostDetail uD;
    public static String idPost;
    public static String ID_PickPost;
    public eventDetailPickPost(Context current, ArrayList<LuuTruModel> list)
    {

        this.context = current;
        this.arrayListPost = list;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LuuTruModel p = arrayListPost.get(position);
         idPost = p.id;
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
                PostDetailParse getLoginJson = gson.fromJson(jsonObject, PostDetailParse.class);
                if (getLoginJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS)) {
                    uD = getLoginJson.getResults();
                     ID_PickPost = uD.getId();
                    String loaibaohong = uD.post_type.getName();
                    String diachi = uD.location_name;
                    String ghichu = uD.content;
                    String hoten = uD.normal_account.full_name;
                    String dienthoai = uD.normal_account.phone_number;
                    String diachinha = uD.normal_account.getAddress();
                    Intent intent = new Intent(context, DetailPickPost.class);
                    intent.putExtra("loaibaohong", loaibaohong);
                    intent.putExtra("diachi", diachi);
                    intent.putExtra("ghichu", ghichu);
                     intent.putExtra("hoten", hoten);
                    intent.putExtra("dienthoai", dienthoai);
                    intent.putExtra("diachinha", diachinha);
                    intent.putExtra("IDPostPost", ID_PickPost);
                    context.startActivity(intent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
