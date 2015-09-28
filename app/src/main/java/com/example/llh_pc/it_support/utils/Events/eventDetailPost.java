package com.example.llh_pc.it_support.utils.Events;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.example.llh_pc.it_support.models.Post;
import com.example.llh_pc.it_support.utils.Interfaces.Def;

import java.util.ArrayList;

/**
 * Created by LLH-PC on 9/27/2015.
 */
public class eventDetailPost implements AdapterView.OnItemClickListener {

    public static final String url_get_my_notifications = Def.API_BASE_LINK + Def.API_PostTile + Def.API_FORMAT_JSON;
    private Context context;
    private ArrayList<Post> arrayListPost;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
