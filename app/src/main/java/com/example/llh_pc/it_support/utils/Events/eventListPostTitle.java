package com.example.llh_pc.it_support.utils.Events;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.activities.frmChildPost;
import com.example.llh_pc.it_support.models.JsonParses.PostParse;
import com.example.llh_pc.it_support.models.Post;
import com.example.llh_pc.it_support.restclients.RequestMethod;
import com.example.llh_pc.it_support.restclients.Response;
import com.example.llh_pc.it_support.restclients.RestClient;
import com.example.llh_pc.it_support.utils.Interfaces.Def;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by LLH-PC on 9/16/2015.
 */
public class eventListPostTitle implements AdapterView.OnItemClickListener {
    public static final String url_get_my_notifications = Def.API_BASE_LINK + Def.API_PostTile + Def.API_FORMAT_JSON;
    private Context context;
    private ArrayList<Post> arrayListPost;
    public eventListPostTitle(Context current,ArrayList<Post> list)
    {
        this.context = current;
        this.arrayListPost = list;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Post p = arrayListPost.get(position);
        //get id post//
        String idPost = p.getId();
        String namePost = p.getName();
        //-----------//
        try
        {
            RestClient restClient = new RestClient(url_get_my_notifications);
            restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
            restClient.execute(RequestMethod.GET);
            if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS)
            {
                String jsonObject = restClient.getResponse();
                Gson gson = new Gson();
                PostParse getListPostJson = gson.fromJson(jsonObject, PostParse.class);
                if(getListPostJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS))
                {
                    arrayListPost = getListPostJson.getResults();
                    Intent intent = new Intent(context, frmChildPost.class);
                    intent.putExtra("NamePost",namePost);
                    intent.putExtra("ID", idPost);
                    context.startActivity(intent);
                }
            }



        }catch (Exception ex){}

    }
}
