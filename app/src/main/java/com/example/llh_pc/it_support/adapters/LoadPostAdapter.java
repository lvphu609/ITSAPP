package com.example.llh_pc.it_support.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.activities.frmLuuTru;
import com.example.llh_pc.it_support.models.JsonParses.LuuTruParse;
import com.example.llh_pc.it_support.models.LuuTruModel;
import com.example.llh_pc.it_support.utils.Images.ImageLoader;
import java.util.List;
import java.util.Locale;

public class LoadPostAdapter extends ArrayAdapter<LuuTruModel> {

    private ImageLoader imgLoader;
    Context context;
    int layoutResourceId;
    private List<LuuTruModel> data = null;

    public LoadPostAdapter(Context context, int resource, List<LuuTruModel> objects) {
        super(context, resource, objects);
        this.layoutResourceId = resource;
        this.context = context;
        this.data = objects;
        imgLoader = new ImageLoader(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PostHolder holder = null;
        LuuTruModel rowItem = getItem(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(convertView == null)
        {
            convertView = inflater.inflate(R.layout.activity_load_post_adapter, parent, false);
            holder = new PostHolder();
            holder.tvName = (TextView)convertView.findViewById(R.id.tvName);
            holder.tvlocation_name = (TextView)convertView.findViewById(R.id.tvlocation_name);
            convertView.setTag(holder);
        }
        else
        {
            holder = (PostHolder) convertView.getTag();
        }

        if(rowItem.getStatus().equals("1"))
        {
            final ImageView icon_post = (ImageView)convertView.findViewById(R.id.imgIcon);
            imgLoader.DisplayImage(data.get(position).getPostType().avatar, icon_post);
            holder.tvName.setText(rowItem.getPostType().getName());
            holder.tvlocation_name.setText(rowItem.getLocation_name());

        }else
        {
            LinearLayout linearLayout=(LinearLayout) convertView.findViewById(R.id.llbtnCancelFinish);
            linearLayout.removeAllViews();
            final ImageView icon_post = (ImageView)convertView.findViewById(R.id.imgIcon);
            imgLoader.DisplayImage(data.get(position).getPostType().avatar, icon_post);
            holder.tvName.setText(rowItem.getPostType().getName());
            holder.tvlocation_name.setText(rowItem.getLocation_name());
        }
        return convertView;
    }
    static class PostHolder
    {
        TextView tvName,tvlocation_name;
    }

    public List<LuuTruModel> getData() {
        return data;
    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        data.clear();
        if (charText.length() == 0) {
            data.addAll(frmLuuTru.postDetails);
        } else {

        }
        notifyDataSetChanged();
    }
}
