package com.example.llh_pc.it_support.fragments;

import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.activities.Search;
import com.example.llh_pc.it_support.activities.SearchMap;
import com.example.llh_pc.it_support.activities.frmHoanThanh;
import com.example.llh_pc.it_support.activities.frmLuuTru;

/**
 * Created by ZBOOK 15 on 10/14/2015.
 */
public class tabHostTimKiem extends TabActivity {
    TabHost tab1;
    TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabsearch);
        tab1 = (TabHost) findViewById(android.R.id.tabhost);
        tab1.setup();
        TabHost tabHost = getTabHost();
        Resources ressources = getResources();

        Intent intentAndroid = new Intent().setClass(this,SearchMap.class);
        TabHost.TabSpec tabSpecAndroid = tabHost
                .newTabSpec("Android")
                .setIndicator("Bản Đồ", ressources.getDrawable(R.color.actionbar_text))
                .setContent(intentAndroid);

        // Apple tab
        Intent intentApple = new Intent().setClass(this, Search.class);
        TabHost.TabSpec tabSpecApple = tabHost
                .newTabSpec("Apple")
                .setIndicator("Danh Sách", ressources.getDrawable(R.drawable.ic_thongbao))
                .setContent(intentApple);

        tabHost.addTab(tabSpecAndroid);
        tabHost.addTab(tabSpecApple);

        tabHost.setCurrentTab(1);
        tab1.getTabWidget().getChildAt(tab1.getCurrentTab()).setBackgroundColor(getResources().getColor(R.color.mauxanh));

        tab1.setOnTabChangedListener(new
                                            TabHost.OnTabChangeListener() {
                                                public void onTabChanged(String arg0) {
                                                    for (int i = 0; i < tab1.getTabWidget().getChildCount(); i++) {
                                                        tab1.getTabWidget().getChildAt(i).setBackgroundColor(0x00FF00); //unselected
                                                        tv1 = (TextView) tab1.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
                                                        tv1.setTextColor(getResources().getColor(R.color.actionbar_text));
                                                    }
                                                    tab1.getTabWidget().getChildAt(tab1.getCurrentTab()).setBackgroundColor(getResources().getColor(R.color.mauxanh));


                                                }
                                            });
    }
}
