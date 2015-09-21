package com.example.llh_pc.it_support.utils.Events;

import android.content.Context;
import android.view.View;
import android.widget.CompoundButton;

/**
 * Created by LLH-PC on 9/17/2015.
 */
public class eventStayLgoin implements CompoundButton.OnCheckedChangeListener {

    private Context context;
    public eventStayLgoin(Context current)
    {
        this.context = current;
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked)
        {
            int a = 1+4;
            a++;
        }
    }
}
