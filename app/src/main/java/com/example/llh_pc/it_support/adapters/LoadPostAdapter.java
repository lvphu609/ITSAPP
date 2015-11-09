package com.example.llh_pc.it_support.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
import android.widget.ListView;
import android.widget.TextView;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.activities.frmHoanThanh;
import com.example.llh_pc.it_support.activities.frmLuuTru;
import com.example.llh_pc.it_support.fragments.TabHostHoatDong;
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
    public static final String url_Destroy = Def.API_BASE_LINK + Def.API_destroy + Def.API_FORMAT_JSON;
    public static final String url_complete = Def.API_BASE_LINK + Def.API_complete + Def.API_FORMAT_JSON;
    public static final String url_processing = Def.API_BASE_LINK + Def.API_processing + Def.API_FORMAT_JSON;
    private final String account_id;
    private ImageLoader imgLoader;
    Context context;
    int layoutResourceId;
    private List<LuuTruModel> data = null;
    private boolean islag;

    public LoadPostAdapter(Context context, int resource, List<LuuTruModel> objects) {
        super(context, resource, objects);
        this.layoutResourceId = resource;
        this.context = context;
        this.data = objects;
        imgLoader = new ImageLoader(context);
        SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
        account_id = sharedPreference.getString("id", "YourName");
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        PostHolder holder = null;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.activity_load_post_adapter, parent, false);
        holder = new PostHolder();
        holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
        holder.tvlocation_name = (TextView) convertView.findViewById(R.id.tvlocation_name);
        holder.tvYeuCau = (TextView) convertView.findViewById(R.id.tvYeuCau);
        holder.btnCancel = (Button) convertView.findViewById(R.id.btnCancel);
        holder.btnFinish = (Button) convertView.findViewById(R.id.btnfinish);
        holder.btnProcessing = (Button) convertView.findViewById(R.id.btnProcessing);
        convertView.setTag(holder);
        LuuTruModel rowItem = data.get(position);
        //status 0: new post
        if(rowItem.getStatus().equals("0"))
        {
            LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.llbtnCancelFinish);
            linearLayout.removeAllViews();
            final ImageView icon_post = (ImageView) convertView.findViewById(R.id.imgIcon);
            imgLoader.DisplayImage(data.get(position).getPostType().avatar, icon_post);
            holder.tvName.setText(rowItem.getPostType().getName());
            holder.tvlocation_name.setText(rowItem.getLocation_name());
        }else
        {
            // this is post pick
          if(rowItem.getStatus().equals("1"))
          {     // ID current pick post ?
              if(rowItem.getPicked_by().equals(account_id)) // account provider
              {
                    // Set processing
                  if(rowItem.getProcessing().equals("0"))
                  {
                      holder.tvYeuCau.setText("");
                      final ImageView icon_post = (ImageView) convertView.findViewById(R.id.imgIcon);
                      imgLoader.DisplayImage(data.get(position).getPostType().avatar, icon_post);
                      holder.tvName.setText(rowItem.getPostType().getName());
                      holder.tvlocation_name.setText(rowItem.getLocation_name());
                      holder.btnCancel.setVisibility(convertView.VISIBLE);
                      holder.btnProcessing.setVisibility(convertView.VISIBLE);
                  }else
                  {
                      holder.tvYeuCau.setText("Đang xử lý");
                      final ImageView icon_post = (ImageView) convertView.findViewById(R.id.imgIcon);
                      imgLoader.DisplayImage(data.get(position).getPostType().avatar, icon_post);
                      holder.tvName.setText(rowItem.getPostType().getName());
                      holder.tvlocation_name.setText(rowItem.getLocation_name());
                      holder.btnCancel.setVisibility(convertView.VISIBLE);
                      holder.btnFinish.setVisibility(convertView.VISIBLE);
                  }
              }
              else // Account user
              {
                  if(rowItem.getProcessing().equals("0")) {
                      holder.tvYeuCau.setText("");
                      final ImageView icon_post = (ImageView) convertView.findViewById(R.id.imgIcon);
                      imgLoader.DisplayImage(data.get(position).getPostType().avatar, icon_post);
                      holder.tvName.setText(rowItem.getPostType().getName());
                      holder.tvlocation_name.setText(rowItem.getLocation_name());
                      holder.btnCancel.setVisibility(convertView.VISIBLE);
                  }else
                  {
                      holder.tvYeuCau.setText("");
                      final ImageView icon_post = (ImageView) convertView.findViewById(R.id.imgIcon);
                      imgLoader.DisplayImage(data.get(position).getPostType().avatar, icon_post);
                      holder.tvName.setText(rowItem.getPostType().getName());
                      holder.tvlocation_name.setText(rowItem.getLocation_name());
                      holder.btnCancel.setVisibility(convertView.VISIBLE);
                  }

              }
          }
        }
        final View finalConvertView = convertView;
        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    LuuTruModel rowItem = getItem(position);
                    String idPost = rowItem.getId();
                    SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
                    String token = sharedPreference.getString("token", "token");
                    RestClient restClient = new RestClient(url_Destroy);
                    restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
                    restClient.addHeader("token", token);
                    restClient.addParam("id", idPost);
                    restClient.execute(RequestMethod.POST);
                    if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS) {
                        String jsonObject = restClient.getResponse();
                        Gson gson = new Gson();
                        abc getLoginJson = gson.fromJson(jsonObject, abc.class);
                        if (getLoginJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS)) {
                            getItem(position).setStatus("-1");
                            notifyDataSetChanged();
                        }
                    }
                } catch (Exception ex) {

                }
            }
        });

        holder.btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    LuuTruModel rowItem = getItem(position);
                    String idPost = rowItem.getId();
                    SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
                    String token = sharedPreference.getString("token", "token");
                    RestClient restClient = new RestClient(url_complete);
                    restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
                    restClient.addHeader("token", token);
                    restClient.addParam("id", idPost);
                    restClient.execute(RequestMethod.POST);
                    if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS) {
                        String jsonObject = restClient.getResponse();
                        Gson gson = new Gson();
                        abc getLoginJson = gson.fromJson(jsonObject, abc.class);
                        if (getLoginJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS)) {
                            TabHostHoatDong.tabHost.setCurrentTab(1);
                        }
                    }
                }catch (Exception ex)
                {

                }
            }
        });
        final View finalConvertView1 = convertView;
        final PostHolder finalHolder = holder;
        holder.btnProcessing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    LuuTruModel rowItem = getItem(position);
                    String idPost = rowItem.getId();
                    SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
                    String token = sharedPreference.getString("token", "token");
                    RestClient restClient = new RestClient(url_processing);
                    restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
                    restClient.addHeader("token", token);
                    restClient.addParam("id", idPost);
                    restClient.execute(RequestMethod.POST);
                    if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS) {
                        String jsonObject = restClient.getResponse();
                        Gson gson = new Gson();
                        abc getLoginJson = gson.fromJson(jsonObject, abc.class);
                        if (getLoginJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS)) {
                            getItem(position).setProcessing("1");
                            finalHolder.btnFinish.setVisibility(finalConvertView1.VISIBLE);
                            finalHolder.btnProcessing.setVisibility(finalConvertView1.GONE);
                            notifyDataSetChanged();
                        }
                    }
                }catch (Exception ex)
                {

                }
            }
        });


        /*PostHolder holder = null;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.activity_load_post_adapter, parent, false);
        holder = new PostHolder();
        holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
        holder.tvlocation_name = (TextView) convertView.findViewById(R.id.tvlocation_name);
        holder.tvYeuCau = (TextView) convertView.findViewById(R.id.tvYeuCau);
        holder.btnCancel = (Button) convertView.findViewById(R.id.btnCancel);
        holder.btnFinish = (Button) convertView.findViewById(R.id.btnfinish);
        holder.btnProcessing = (Button) convertView.findViewById(R.id.btnProcessing);
        convertView.setTag(holder);
        LuuTruModel rowItem = data.get(position);
        if (rowItem.getStatus().equals("1")) {
            if (account_id.equals(rowItem.picked_by)) {
                holder.tvYeuCau.setText("");
                final ImageView icon_post = (ImageView) convertView.findViewById(R.id.imgIcon);
                imgLoader.DisplayImage(data.get(position).getPostType().avatar, icon_post);
                holder.tvName.setText(rowItem.getPostType().getName());
                holder.tvlocation_name.setText(rowItem.getLocation_name());
                holder.btnCancel.setVisibility(convertView.VISIBLE);
            } else {
                if(rowItem.getProcessing().equals("1"))
                {
                    holder.tvYeuCau.setText("");
                    final ImageView icon_post = (ImageView) convertView.findViewById(R.id.imgIcon);
                    imgLoader.DisplayImage(data.get(position).getPostType().avatar, icon_post);
                    holder.tvName.setText(rowItem.getPostType().getName());
                    holder.tvlocation_name.setText(rowItem.getLocation_name());
                    holder.btnCancel.setVisibility(convertView.VISIBLE);
                    holder.btnFinish.setVisibility(convertView.VISIBLE);
                    //holder.btnProcessing.setVisibility(convertView.VISIBLE);
                }
                else {
                    holder.tvYeuCau.setText("");
                    final ImageView icon_post = (ImageView) convertView.findViewById(R.id.imgIcon);
                    imgLoader.DisplayImage(data.get(position).getPostType().avatar, icon_post);
                    holder.tvName.setText(rowItem.getPostType().getName());
                    holder.tvlocation_name.setText(rowItem.getLocation_name());
                    holder.btnCancel.setVisibility(convertView.VISIBLE);
                    holder.btnFinish.setVisibility(convertView.VISIBLE);
                    holder.btnProcessing.setVisibility(convertView.VISIBLE);
                }
            }

        } else {
            LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.llbtnCancelFinish);
            linearLayout.removeAllViews();
            final ImageView icon_post = (ImageView) convertView.findViewById(R.id.imgIcon);
            imgLoader.DisplayImage(data.get(position).getPostType().avatar, icon_post);
            holder.tvName.setText(rowItem.getPostType().getName());
            holder.tvlocation_name.setText(rowItem.getLocation_name());
        }
        final View finalConvertView = convertView;
        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    LuuTruModel rowItem = getItem(position);
                    String idPost = rowItem.getId();
                    SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
                    String token = sharedPreference.getString("token", "token");
                    RestClient restClient = new RestClient(url_Destroy);
                    restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
                    restClient.addHeader("token", token);
                    restClient.addParam("id", idPost);
                    restClient.execute(RequestMethod.POST);
                    if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS) {
                        String jsonObject = restClient.getResponse();
                        Gson gson = new Gson();
                        abc getLoginJson = gson.fromJson(jsonObject, abc.class);
                        if (getLoginJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS)) {
                            getItem(position).setStatus("-1");
                            notifyDataSetChanged();
                        }
                    }
                } catch (Exception ex) {

                }
            }
        });

        holder.btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    LuuTruModel rowItem = getItem(position);
                    String idPost = rowItem.getId();
                    SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
                    String token = sharedPreference.getString("token", "token");
                    RestClient restClient = new RestClient(url_complete);
                    restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
                    restClient.addHeader("token", token);
                    restClient.addParam("id", idPost);
                    restClient.execute(RequestMethod.POST);
                    if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS) {
                        String jsonObject = restClient.getResponse();
                        Gson gson = new Gson();
                        abc getLoginJson = gson.fromJson(jsonObject, abc.class);
                        if (getLoginJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS)) {
                            TabHostHoatDong.tabHost.setCurrentTab(1);
                        }
                    }
                }catch (Exception ex)
                {

                }
            }
        });
        final View finalConvertView1 = convertView;
        final PostHolder finalHolder = holder;
        holder.btnProcessing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    LuuTruModel rowItem = getItem(position);
                    String idPost = rowItem.getId();
                    SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
                    String token = sharedPreference.getString("token", "token");
                    RestClient restClient = new RestClient(url_processing);
                    restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
                    restClient.addHeader("token", token);
                    restClient.addParam("id", idPost);
                    restClient.execute(RequestMethod.POST);
                    if (restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS) {
                        String jsonObject = restClient.getResponse();
                        Gson gson = new Gson();
                        abc getLoginJson = gson.fromJson(jsonObject, abc.class);
                        if (getLoginJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS)) {
                            getItem(position).setProcessing("1");
                            finalHolder.btnFinish.setVisibility(finalConvertView1.VISIBLE);
                            finalHolder.btnProcessing.setVisibility(finalConvertView1.GONE);
                            notifyDataSetChanged();
                        }
                    }
                }catch (Exception ex)
                {

                }
            }
        });*/
    return convertView;
}

static class PostHolder {
    TextView tvName, tvlocation_name, tvYeuCau;
    Button btnCancel, btnFinish, btnProcessing;
}
}
