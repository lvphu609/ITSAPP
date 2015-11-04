package com.example.llh_pc.it_support.adapters;

import android.app.Activity;
import android.content.Context;
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
import com.example.llh_pc.it_support.models.ListNotificationModel;
import com.example.llh_pc.it_support.models.Post;
import com.example.llh_pc.it_support.utils.Images.ImageLoader;

import java.util.List;

public class ListNotificationAdapter extends ArrayAdapter<ListNotificationModel> {
    private ImageLoader imgLoader;
    Context context;
    int layoutResourceId;
    List<ListNotificationModel> data = null;

    public ListNotificationAdapter(Context context, int resource, List<ListNotificationModel> objects) {
        super(context, resource, objects);
        this.layoutResourceId = resource;
        this.context = context;
        this.data = objects;
        imgLoader = new ImageLoader(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PostHolder holder = null;
        ListNotificationModel rowItem = getItem(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(convertView == null)
        {
            convertView = inflater.inflate(R.layout.activity_list_notification_adapter, parent, false);
            holder = new PostHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            holder.tvlocation_name = (TextView) convertView.findViewById(R.id.tvlocation_name);
            convertView.setTag(holder);
        }
        else
        {
            holder = (PostHolder) convertView.getTag();
        }
        final ImageView icon_post = (ImageView)convertView.findViewById(R.id.imgIcon);
        imgLoader.DisplayImage(data.get(position).getAvatar(), icon_post);
        holder.tvName.setText(rowItem.getTitle());
        holder.tvlocation_name.setText(rowItem.getCreated_at());
        return convertView;
    }

    static class PostHolder
    {
        TextView tvName,tvlocation_name;
    }
}
