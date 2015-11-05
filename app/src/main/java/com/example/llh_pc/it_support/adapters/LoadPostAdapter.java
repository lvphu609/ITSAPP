package com.example.llh_pc.it_support.adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.example.llh_pc.it_support.models.JsonParses.abc;
import com.example.llh_pc.it_support.models.LuuTruModel;
import com.example.llh_pc.it_support.restclients.RequestMethod;
import com.example.llh_pc.it_support.restclients.Response;
import com.example.llh_pc.it_support.restclients.RestClient;
import com.example.llh_pc.it_support.utils.Images.ImageLoader;
import com.example.llh_pc.it_support.utils.Interfaces.Def;
import com.google.gson.Gson;

import java.util.List;
import java.util.Locale;

public class LoadPostAdapter extends ArrayAdapter<LuuTruModel> {

    public static final String url_destroy = Def.API_BASE_LINK + Def.API_destroy + Def.API_FORMAT_JSON;
    private final String token;
    private ImageLoader imgLoader;
    Context context;
    int layoutResourceId;
    private List<LuuTruModel> data = null;
    private View v;
    public LoadPostAdapter(Context context, int resource, List<LuuTruModel> objects) {
        super(context, resource, objects);
        this.layoutResourceId = resource;
        this.context = context;
        this.data = objects;
        imgLoader = new ImageLoader(context);
        SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
        token = sharedPreference.getString("token", "YourName");

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            v = convertView;
            PostHolder holder = null;
            final LuuTruModel rowItem = getItem(position);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.activity_load_post_adapter, parent, false);
                holder = new PostHolder();
                holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
                holder.tvlocation_name = (TextView) convertView.findViewById(R.id.tvlocation_name);
                convertView.setTag(holder);
            } else {
                holder = (PostHolder) convertView.getTag();
            }

            if (rowItem.getStatus().equals("1")) {
                final ImageView icon_post = (ImageView) convertView.findViewById(R.id.imgIcon);
                imgLoader.DisplayImage(data.get(position).getPostType().avatar, icon_post);
                holder.tvName.setText(rowItem.getPostType().getName());
                holder.tvlocation_name.setText(rowItem.getLocation_name());


            } else {
                LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.llbtnCancelFinish);
                linearLayout.removeAllViews();
                final ImageView icon_post = (ImageView) convertView.findViewById(R.id.imgIcon);
                imgLoader.DisplayImage(data.get(position).getPostType().avatar, icon_post);
                holder.tvName.setText(rowItem.getPostType().getName());
                holder.tvlocation_name.setText(rowItem.getLocation_name());
            }
            Button btnCancel = (Button) convertView.findViewById(R.id.btnCancel);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = rowItem.post_type.getId();
                    new CancelAsyncTask().execute(token,id);
                    String t = "sfdsfdsfdsfsfsfdsfd" + id;
                }
            });
        }catch (Exception ex)
        {
            Log.e(ex.toString(),"Lỗi");
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

    private class CancelAsyncTask extends AsyncTask<String,Void,Boolean>
    {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            String id = params[0];
            String token = params[1];
            try {
                RestClient restClient = new RestClient(url_destroy);
                restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
                restClient.addHeader("token", token);
                restClient.addParam("id", id);
                restClient.execute(RequestMethod.POST);
                if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS && restClient.getResponse() != null)
                {
                    String jsonObject = restClient.getResponse();
                    abc luuTruParse = new Gson().fromJson(jsonObject, abc.class);
                    if(luuTruParse.getStatus().equalsIgnoreCase(Response.STATUS_FALSE)){
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }

            }catch (Exception ex)
            {
                Log.e(ex.toString(),"Lỗi");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean s) {
            super.onPostExecute(s);
            if(s)
            {
                LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.llbtnCancelFinish);
                linearLayout.removeAllViews();
            }else
            {

            }
        }
    }
}
