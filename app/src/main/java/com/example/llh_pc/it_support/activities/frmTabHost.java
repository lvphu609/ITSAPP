package com.example.llh_pc.it_support.activities;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import com.example.llh_pc.it_support.activities.TabBaoHu;
import com.example.llh_pc.it_support.R;
import com.example.llh_pc.it_support.activities.frmBaoHu;
import com.example.llh_pc.it_support.activities.frmLuuTru;
import com.example.llh_pc.it_support.activities.frmThongBao;
import com.example.llh_pc.it_support.activities.frmTimKiem;

import org.w3c.dom.Text;

public class frmTabHost  extends TabActivity {
    TextView tv;
    private FragmentTabHost mTabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_tab_host);
        final TabHost tab=(TabHost) findViewById(android.R.id.tabhost);
        tab.setup();

        Resources ressources = getResources();
        TabHost tabHost = getTabHost();

        Intent intentAndroid = new Intent().setClass(this, frmBaoHu.class);
        TabHost.TabSpec tabSpecAndroid = tabHost
                .newTabSpec("Android")
                .setIndicator("",ressources.getDrawable(R.drawable.ic_baohong))
                .setContent(intentAndroid);


        // Apple tab
        Intent intentApple = new Intent().setClass(this, frmThongBao.class);
        TabHost.TabSpec tabSpecApple = tabHost
                .newTabSpec("Apple")
                .setIndicator("", ressources.getDrawable(R.drawable.ic_thongbao))
                .setContent(intentApple);

        // Windows tab
        Intent intentWindows = new Intent().setClass(this, frmTimKiem.class);
        TabHost.TabSpec tabSpecWindows = tabHost
                .newTabSpec("Windows")
                .setIndicator("", ressources.getDrawable(R.drawable.ic_timkiem))
                .setContent(intentWindows);

        // Blackberry tab
        Intent intentBerry = new Intent().setClass(this, frmLuuTru.class);
        TabHost.TabSpec tabSpecBerry = tabHost
                .newTabSpec("Berry")
                .setIndicator("", ressources.getDrawable(R.drawable.ic_luutru))
                .setContent(intentBerry);

        //Profile tab



        // add all tabs
        tabHost.addTab(tabSpecAndroid);
        tabHost.addTab(tabSpecApple);
        tabHost.addTab(tabSpecWindows);
        tabHost.addTab(tabSpecBerry);




        //set Windows tab as default (zero based)
        tabHost.setCurrentTab(0);
        tab.setOnTabChangedListener(new
                                            TabHost.OnTabChangeListener() {
                                                public void onTabChanged(String arg0) {
                                                    for (int i = 0; i < tab.getTabWidget().getChildCount(); i++) {
                                                        tab.getTabWidget().getChildAt(i).setBackgroundColor(0x00FF00); //unselected
                                                         tv = (TextView) tab.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
                                                        tv.setTextColor(getResources().getColor(R.color.actionbar_text));
                                                    }

                                                    if (tab.getTabWidget().getChildCount() == 0)
                                                        tab.getTabWidget().getChildAt(tab.getCurrentTab()).setBackgroundColor(getResources().getColor(R.color.mauxanh)); //1st tab selected

                                                    else
                                                        tab.getTabWidget().getChildAt(tab.getCurrentTab()).setBackgroundColor(getResources().getColor(R.color.mauxanh)); //2nd tab selected

                                                }
                                            });
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

}
