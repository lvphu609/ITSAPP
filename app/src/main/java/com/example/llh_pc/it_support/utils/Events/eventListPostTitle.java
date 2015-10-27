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
                    String str_other = edtOther.getText().toString();
                    new CreatePost().execute(str_other,token);

                    show.dismiss();
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
                if(true)
                {
                    return "1";
                }
                else
                {
                    return "2";
                }
            }catch (Exception ex)
            {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equals("1"))
            {
                Intent intent = new Intent(context, frmGhiChuKhac.class);
                context.startActivity(intent);
            }else
            {
                Intent intent = new Intent(context, frmBaoHu.class);
                context.startActivity(intent);
            }
        }
    }
}
