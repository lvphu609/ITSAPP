package com.example.llh_pc.it_support.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.llh_pc.it_support.R;

/**
 * Created by ZBOOK 15 on 10/5/2015.
 */
public class TabBaoHu extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.activity_frm_luu_tru, container, false);

        return V;
    }
}