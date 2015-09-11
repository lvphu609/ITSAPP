package com.example.llh_pc.it_support.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Log;

import com.example.llh_pc.it_support.utils.Interfaces.Def;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Khanh Vo on 9/7/2015.
 * Email: khanhvo@innoria.com || idkhanhvo272@gmail.com
 * Phone number: 093 28 11 291
 */
public class CommonFunction {


    /**
     * convert from bitmap to byte array
     * @param bitmap
     * @return
     */
    public static byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }//--end getBytesFromBitmap

    /**
     * Bitmap to Base64
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap){
        String output = Def.STRING_EMPTY;
        try{
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            output = Base64.encodeToString(byteArray, Base64.DEFAULT);
        }
        catch (Exception e){
            Log.e(Def.ERROR, e.getMessage());
        }

        return output;
    }//--end bitmapToBase64

    /**
     * String to md5
     * @param input
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static StringBuffer md5(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(input.getBytes());

        byte byteData[] = md.digest();

        StringBuffer hexString = new StringBuffer();
        for (int i=0;i<byteData.length;i++) {
            String hex= Integer.toHexString(0xff & byteData[i]);
            if(hex.length()==1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString;
    }//--end md5

    /**
     * convert string url to drawable
     * @param url
     * @return
     * @throws java.net.MalformedURLException
     * @throws java.io.IOException
     */
    public static Bitmap drawableFromUrl(String url) throws java.net.MalformedURLException, java.io.IOException {
        Bitmap x;

        HttpURLConnection connection = (HttpURLConnection)new URL(url) .openConnection();
        connection.setRequestProperty("User-agent","Mozilla/4.0");

        connection.connect();
        InputStream input = connection.getInputStream();

        x = BitmapFactory.decodeStream(input);

        return x;
    }//--end drawableFromUrl

    /**
     * check network
     * @param context
     * @return
     */
    public static boolean isNetworkOnline(Context context) {
        boolean status=false;
        try{
            ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState()== NetworkInfo.State.CONNECTED) {
                status= true;
            }else {
                netInfo = cm.getNetworkInfo(1);
                if(netInfo!=null && netInfo.getState()== NetworkInfo.State.CONNECTED)
                    status= true;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return status;

    }//end isNetworkOnline

    /**
     * format date - time
     * @param input
     * @return
     */
    public static String formatDate(String input){
        SimpleDateFormat formatDate = new SimpleDateFormat(Def.DATE_FORMAT);
        Date date = null;
        try {
            date = formatDate.parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        formatDate.applyPattern(Def.NEW_DATE_FORMAT);
        return formatDate.format(date);
    }//--end formatDate

    /**
     * Convert String to Int
     * @param input
     * @return
     */
    public static int stringToInt(String input){
        try{
            return Integer.parseInt(input);
        }
        catch(Exception e){
            return 0;
        }
    }//--end stringToInt

    /**
     * Convert String to Double
     * @param input
     * @return
     */
    public static double stringToDouble(String input){
        try{
            return Double.valueOf(input);
        }
        catch (Exception e){
            return 0.0;
        }
    }//--end stringToDouble

    /**
     * String upperCase with first character
     * @param input
     * @return
     */
    public static String upperCaseFirstString(String input){
        if(input != null && input.length() > 1){
            return input.substring(0,1).toUpperCase() + input.substring(1);
        }
        return input;
    }//--end upperCaseFirstString

    /**
     * String upperCase with full text
     * @param input
     * @return
     */
    public static String upperCaseFullText(String input){
        if(input.equalsIgnoreCase(Def.STRING_EMPTY)){
            return Def.STRING_EMPTY;
        }
        String output = Def.STRING_EMPTY;
        String[]split_name = input.split(" ");
        for(int i=0; i<split_name.length; ++i){
            split_name[i] = upperCaseFirstString(split_name[i]);
            if(!split_name[i].equalsIgnoreCase(Def.STRING_EMPTY)){
                output += " " + split_name[i];
            }

        }
        return output.trim();
    }//--end upperCaseFullText

    /**
     * reset the sharePreference
     * @param context
     */
    public static void resetSharePreference(Context context){
        SharePreference preference = new SharePreference(context);
        preference.setToken(Def.STRING_EMPTY);
        preference.setUserId(Def.STRING_EMPTY);
        preference.setUserType(Def.STRING_EMPTY);
        preference.setRemember(0);
        preference.setPASS(Def.STRING_EMPTY);
    }//--end resetPreference
}
