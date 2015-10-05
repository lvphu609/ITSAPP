package com.example.llh_pc.it_support.adapters;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.llh_pc.it_support.R;

public class ItemMenuLeftApdapter {

    private  Context context;
    private  String[] itemname;
    private  Integer[] imgid;
    private CharSequence[] result;

    public ItemMenuLeftApdapter(Context current,String[] prgmNameList, Integer[] prgmImages)
    {
        this.context = current;
        this.itemname = prgmNameList;
        this.imgid = prgmImages;

    }
}
