package com.example.llh_pc.it_support.gcm;

import com.example.llh_pc.it_support.utils.Interfaces.Def;
import com.example.llh_pc.it_support.models.Post;

import java.io.File;

/**
 * Created by Khanh Vo on 9/15/2015.
 * Email: khanhvo@innoria.com || idkhanhvo272@gmail.com
 * Phone number: 093 28 11 291
 */
public class Flags {

    public static String token = Def.STRING_EMPTY;
    public static boolean tab_click = false;
    public static boolean is_active = true;
    public static boolean is_finish = false;//when user active, it mean not finish
    public static boolean is_expire = false;//false it mean not expire

    public static String take_picture_name = Def.STRING_EMPTY;
    public static File image_from_camera;

    /**
     * Screen SearchMap & SearchList
     * list = 0; map = 1
     */
    public static int tab_list = 0;

    public static boolean date_picker_click = true;
    public static boolean date_picker_click_edit = false;
    public static boolean text_view_click = true;

    public static boolean is_post_detail = false;
    public static boolean is_search_list = false;

    public static boolean back_profile_edit = false;

    public static String account_id = "0";
    public static String account_type = "0";
    public static boolean show_check_box = false;

    public static boolean post_detail_to_activity = false;
    public static boolean post_list_from_activity = false;
    public static int post_from_search = 0;

    //show tab at account with defaul: tab activity
    public static int set_main_tab = 0;
    public static int tab_account = Def.TAB_ACCOUNT_ACTIVITY;
    public static int tab_notification = Def.TAB_NOTIFICATION;

    public static final String KEY_INSTANCE_STATE_TYPE_POST = "key_instance_state_type_post";
    public static final int INSTANCE_STATE_TYPE_POST = 1;

    public static String reg_token = Def.STRING_EMPTY;

    //notification for user view position provider
    public static double user_view_location_lat_provider = Def.LOCATION_LAT_LNG_DEFAULT;
    public static double user_view_location_lng_provider = Def.LOCATION_LAT_LNG_DEFAULT;
    public static Post post_user_view_position_provider = new Post();

    public static int number_notification = 0;
    public static String post_id_notification = "0";
    public static boolean on_destroy = false;
    public static int number_notify_out_app = 0;

    public static String post_id_directory = Def.STRING_EMPTY;

    /**
     * list post picked
     */
    public static boolean post_picked = false;

    public static boolean token_status = true;


    /*Sms & call log*/
    /**
     * to check status sms & call log were sent
     */
    public static boolean push_message_call_log = true;

}
