package com.example.llh_pc.it_support.activities;

import android.app.DownloadManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.adapters.ChildPostAdapter;
import com.example.llh_pc.it_support.models.LuuTruModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Search extends AppCompatActivity implements SearchView.OnQueryTextListener{
    EditText inputSearch;
    SearchView searchView;
    MenuItem searchMenuItem;
    LayoutInflater inflater;
    frmLuuTru childPost;
    //Search List
    private ListView mSearchNFilterLv;

    private EditText mSearchEdt;

    public ArrayList<LuuTruModel> searchPost;
    private ChildPostAdapter valueAdapter;

    private TextWatcher mSearchTw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        childPost = new frmLuuTru();
        inputSearch = (EditText) findViewById(R.id.inputSearch);
        inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
    return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {


        return false;
    }

}
