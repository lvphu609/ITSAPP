package com.example.llh_pc.it_support.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.models.LuuTruModel;
import com.example.llh_pc.it_support.utils.Images.ImageLoader;
import com.example.llh_pc.it_support.utils.Interfaces.Def;

import java.util.List;

class LoadPostFinish extends ArrayAdapter<LuuTruModel> {
    public static final String url_Destroy = Def.API_BASE_LINK + Def.API_destroy + Def.API_FORMAT_JSON;
    public static final String url_complete = Def.API_BASE_LINK + Def.API_complete + Def.API_FORMAT_JSON;
    private final String account_id;
    private ImageLoader imgLoader;
    Context context;
    int layoutResourceId;
    private List<LuuTruModel> data = null;
    private boolean islag;

    public LoadPostFinish(Context context, int resource, List<LuuTruModel> objects) {
        super(context, resource, objects);
        this.layoutResourceId = resource;
        this.context = context;
        this.data = objects;
        imgLoader = new ImageLoader(context);
        SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
        account_id = sharedPreference.getString("id", "YourName");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PostHolder holder = null;
        LuuTruModel rowItem = getItem(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_load_post_finish, parent, false);
            holder = new PostHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            holder.tvlocation_name = (TextView) convertView.findViewById(R.id.tvlocation_name);
            convertView.setTag(holder);
        } else {
            holder = (PostHolder) convertView.getTag();
        }
        final ImageView icon_post = (ImageView) convertView.findViewById(R.id.imgIcon);
        imgLoader.DisplayImage(data.get(position).getPostType().avatar, icon_post);
        holder.tvName.setText(rowItem.getPostType().getName());
        holder.tvlocation_name.setText(rowItem.getLocation_name());

        return convertView;
    }

    static class PostHolder {
        TextView tvName, tvlocation_name;
    }
}
