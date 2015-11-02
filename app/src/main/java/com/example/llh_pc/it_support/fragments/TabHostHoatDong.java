package com.example.llh_pc.it_support.fragments;

import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.activities.frmBaoHu;
import com.example.llh_pc.it_support.activities.frmHoanThanh;
import com.example.llh_pc.it_support.activities.frmLuuTru;
import com.example.llh_pc.it_support.activities.frmTabHost;
import com.example.llh_pc.it_support.activities.frmThongBao;

/**
 * Created by ZBOOK 15 on 10/8/2015.
 */
public class TabHostHoatDong extends TabActivity {
    public static TabHost tab;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_content);
        tab = (TabHost) findViewById(android.R.id.tabhost);
        tab.setup();
        TabHost tabHost = getTabHost();
        Resources ressources = getResources();

        Intent intentAndroid = new Intent().setClass(this,frmLuuTru.class);
        TabHost.TabSpec tabSpecAndroid = tabHost
                .newTabSpec("Android")
                .setIndicator("Hoạt Động", ressources.getDrawable(R.color.actionbar_text))
                .setContent(intentAndroid);

        // Apple tab
        Intent intentApple = new Intent().setClass(this, frmHoanThanh.class);
        TabHost.TabSpec tabSpecApple = tabHost
                .newTabSpec("Apple")
                .setIndicator("Hoàn Thành", ressources.getDrawable(R.drawable.ic_thongbao))
                .setContent(intentApple);

        tabHost.addTab(tabSpecAndroid);
        tabHost.addTab(tabSpecApple);

        tabHost.setCurrentTab(0);
        tab.getTabWidget().getChildAt(tab.getCurrentTab()).setBackgroundColor(getResources().getColor(R.color.mauxanh));

        tab.setOnTabChangedListener(new
                                            TabHost.OnTabChangeListener() {
                                                public void onTabChanged(String arg0) {
                                                    for (int i = 0; i < tab.getTabWidget().getChildCount(); i++) {
                                                        tab.getTabWidget().getChildAt(i).setBackgroundColor(0x00FF00); //unselected
                                                        tv = (TextView) tab.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
                                                        tv.setTextColor(getResources().getColor(R.color.actionbar_text));
                                                    }
                                                    tab.getTabWidget().getChildAt(tab.getCurrentTab()).setBackgroundColor(getResources().getColor(R.color.mauxanh));


                                                }
                                            });
    }

}
