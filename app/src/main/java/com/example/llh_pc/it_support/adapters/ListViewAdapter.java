package com.example.llh_pc.it_support.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.activities.frmLuuTru;
import com.example.llh_pc.it_support.models.JsonParses.LuuTruParse;
import com.example.llh_pc.it_support.models.LuuTruModel;
import com.example.llh_pc.it_support.models.Post;
import com.example.llh_pc.it_support.models.PostType;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by ZBOOK 15 on 10/11/2015.
 */
public class ListViewAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater inflater;
    private List<LuuTruModel> worldpopulationlist = null;
    private ArrayList<frmLuuTru> arraylist;


    public ListViewAdapter(Context context,
                           List<LuuTruModel> worldpopulationlist) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);

        frmLuuTru.postDetails.addAll(worldpopulationlist);
    }


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder {
        ListView rank;
        TextView country;
        TextView population;
        ImageView flag;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.activity_frm_luu_tru, null);
            // Locate the TextViews in listview_item.xml
            holder.rank = (ListView) view.findViewById(R.id.lsPost);


            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews


        // Set the results into ImageView
//
        // Listen for ListView Item Click
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Send single item click data to SingleItemView Class
                Intent intent = new Intent(mContext,frmLuuTru.class);
                // Pass all data rank


                // Start SingleItemView Class
                mContext.startActivity(intent);
            }
        });

        return view;
    }
    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        worldpopulationlist.clear();
        if (charText.length() == 0) {
            worldpopulationlist.addAll(frmLuuTru.postDetails);
        } else {

        }
        notifyDataSetChanged();
    }
}
