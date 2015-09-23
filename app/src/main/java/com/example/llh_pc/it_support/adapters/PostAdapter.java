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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.models.Post;
import com.example.llh_pc.it_support.utils.Images.ImageLoader;

import java.util.List;

public class PostAdapter extends ArrayAdapter<Post> {
    private ImageLoader imgLoader;
    Context context;
    int layoutResourceId;
    List<Post> data = null;
    public PostAdapter(Context context, int resource, List<Post> objects) {
        super(context, resource, objects);
        this.layoutResourceId = resource;
        this.context = context;
        this.data = objects;
        imgLoader = new ImageLoader(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PostHolder holder = null;
        Post rowItem = getItem(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(convertView == null)
        {
            convertView = inflater.inflate(R.layout.activity_post_adapter, parent, false);
            holder = new PostHolder();
            holder.tvName = (TextView)convertView.findViewById(R.id.tvName);

            convertView.setTag(holder);
        }
        else
        {
            holder = (PostHolder) convertView.getTag();
        }
        final ImageView icon_post = (ImageView)convertView.findViewById(R.id.imgIcon);
        imgLoader.DisplayImage(data.get(position).getAvatar(), icon_post);
        holder.tvName.setText(rowItem.getName());
        return convertView;
    }
    static class PostHolder
    {
        TextView tvName;
    }
}
