package com.example.llh_pc.it_support.utils.Events;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.activities.frmBaoHu;
import com.example.llh_pc.it_support.activities.frmChildPost;
import com.example.llh_pc.it_support.activities.frmGhiChu;
import com.example.llh_pc.it_support.activities.frmGhiChuKhac;
import com.example.llh_pc.it_support.adapters.PostAdapter;
import com.example.llh_pc.it_support.models.IDOther;
import com.example.llh_pc.it_support.models.JsonParses.OtherParse;
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
    public static final String url_create_type_post_other= Def.API_BASE_LINK + Def.API_create_type_post_other + Def.API_FORMAT_JSON;
    private Context context;
    private ArrayList<Post> arrayListPost;
    private ArrayList<IDOther> ID = new ArrayList<>();
    public eventListPostTitle(Context current,ArrayList<Post> list)
    {
        this.context = current;
        this.arrayListPost = list;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
        final String token = sharedPreference.getString("token", null);
        Post p = arrayListPost.get(position);
        //get id post//
        String idPost = p.getId();
        String namePost = p.getName();
        if(namePost.equals("Khác"))
        {
            LayoutInflater li = LayoutInflater.from(context);
            View promptsView = li.inflate(R.layout.popup_other, null);
            android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);
            alertDialogBuilder.setView(promptsView);
            final TextView textView = (TextView) promptsView.findViewById(R.id.tvValidation);
            // set dialog message
            alertDialogBuilder.setView(promptsView);
            // set dialog message
            alertDialogBuilder.setCancelable(false);
            final android.support.v7.app.AlertDialog show = alertDialogBuilder.show();
            final Button okpopup= (Button) promptsView.findViewById(R.id.okpopup);
            okpopup.setTextColor(Color.parseColor("#ffffff"));
            final EditText edtOther= (EditText)promptsView.findViewById(R.id.edtOther);
            edtOther.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.toString().isEmpty()) {
                        okpopup.setEnabled(false);
                        okpopup.setBackgroundColor(Color.parseColor("#666666"));
                    } else {
                        okpopup.setEnabled(true);
                        okpopup.setBackgroundColor(Color.parseColor("#05afef"));
                    }
                }
            });

            okpopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String str_other = edtOther.getText().toString();
                        SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
                        final String token = sharedPreference.getString("token", null);
                        new CreatePost().execute(str_other, token);
                        show.dismiss();
                    }catch (Exception ex)
                    {
                        Log.e(ex.toString(),"Lỗi");
                    }
                }
            });
            Button huypopup= (Button)promptsView.findViewById(R.id.huypopup);
            huypopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show.dismiss();
                }
            });
        }else {
            //-----------//
            Intent intent = new Intent(context, frmChildPost.class);
            intent.putExtra("NamePost", namePost);
            intent.putExtra("ID", idPost);
            context.startActivity(intent);
        }
     }
    public class CreatePost extends AsyncTask<String,Void,String>
    {
        private ProgressDialog progressDialog;
        private IDOther id;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(context, "IT Support", "Loading...");
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String other = params[0];
                String token = params[1];
                RestClient restClient = new RestClient(url_create_type_post_other);
                restClient.addBasicAuthentication(Def.API_USERNAME_VALUE, Def.API_PASSWORD_VALUE);
                restClient.addHeader("token", token);
                restClient.addParam("name", other);
                restClient.execute(RequestMethod.POST);
                if(restClient.getResponseCode() == Def.RESPONSE_CODE_SUCCESS)
                {
                    String jsonObject = restClient.getResponse();
                    Gson gson = new Gson();
                    OtherParse getListPostJson = gson.fromJson(jsonObject, OtherParse.class);
                    if(getListPostJson.getStatus().equalsIgnoreCase(Response.STATUS_SUCCESS))
                    {
                        id = getListPostJson.getResults();
                        String temp = id.getId();
                        return temp;
                    }

                }
                else
                {
                    return "2";
                }
            }catch (Exception ex)
            {
                Log.e(ex.toString(),"Lỗi");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(!s.isEmpty())
            {
                Intent intent = new Intent(context, frmGhiChuKhac.class);
                progressDialog.dismiss();
                intent.putExtra("Other",s);
                context.startActivity(intent);
            }else
            {
                progressDialog.dismiss();
                Intent intent = new Intent(context, frmGhiChu.class);
                context.startActivity(intent);
            }
        }
    }
}
