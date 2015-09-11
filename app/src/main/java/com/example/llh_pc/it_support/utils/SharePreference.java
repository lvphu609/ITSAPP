package com.example.llh_pc.it_support.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.llh_pc.it_support.utils.Interfaces.Def;

/**
 * Created by Khanh Vo on 9/7/2015.
 * Email: khanhvo@innoria.com || idkhanhvo272@gmail.com
 * Phone number: 093 28 11 291
 */
public class SharePreference {

    private SharedPreferences preferences;

    public SharePreference(Context mContext) {
        preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public SharePreference(Context mContext, String name) {
        preferences = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public void setRemember(int remember) {
        preferences.edit().putInt(Def.REMEMBER, remember).commit();
    }

    public int getRemember() {
        return preferences.getInt(Def.REMEMBER, 0);
    }

    public void setUser(String mUser) {
        preferences.edit().putString(Def.USERNAME, mUser).commit();

    }

    public String getUser() {
        return preferences.getString(Def.USERNAME, Def.STRING_EMPTY);
    }

    public void setPASS(String mPASS) {
        preferences.edit().putString(Def.PASSWORD, mPASS).commit();

    }

    public String getPASS() {
        return preferences.getString(Def.PASSWORD, Def.STRING_EMPTY);
    }


    public void setToken(String mToken) {
        preferences.edit().putString(Def.TOKEN, mToken).commit();
    }

    public String getToken() {
        return preferences.getString(Def.TOKEN, Def.STRING_EMPTY);
    }

    public void setUserId(String userId) {
        preferences.edit().putString(Def.USER_ID, userId).commit();
    }

    public String getUserId() {
        return preferences.getString(Def.USER_ID, Def.STRING_EMPTY);
    }

    public void setUserType(String userType) {
        preferences.edit().putString(Def.USER_TYPE, userType).commit();
    }

    public String getUserType() {
        return preferences.getString(Def.USER_TYPE, Def.STRING_EMPTY);
    }

    public void setDestroy(String userType) {
        preferences.edit().putString("destroy", userType).commit();
    }

    public String getDestroy() {
        return preferences.getString("destroy", Def.STRING_EMPTY);
    }
}
