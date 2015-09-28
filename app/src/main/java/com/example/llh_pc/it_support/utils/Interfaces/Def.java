package com.example.llh_pc.it_support.utils.Interfaces;

/**
 * Created by Khanh Vo on 9/7/2015.
 * Email: khanhvo@innoria.com || idkhanhvo272@gmail.com
 * Phone number: 093 28 11 291
 */
public interface Def {
    //Format date
    public static final String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";
    public static final String NEW_DATE_FORMAT = "hh:mm:ss dd/MM/yyyy";

    //Api url
    public static final String API_BASE_LINK = "http://demo.innoria.com/itsupport/api/";
    public static final String API_FORMAT_JSON = "";
    public static final String API_LOGIN = "accounts/login/";
    public static final String API_GET_ACCOUNT_INFO_BY_ID ="accounts/get_account_info_by_id";
    public static final String API_CHECKEMAIL ="accounts/check_email_exist";

    //Forgot Pass
    public static final String API_ForgotPass = "accounts/forgot_password";
    //reset pass
    public static final String API_ResetPass = "accounts/reset_password";
    //postlist
    public static final String API_PostTile = "config/get_list_type_post/";
    //postchild
    public static final String API_SubPost = "config/get_sub_post_types/";
    //logout
    public static final String API_Logout = "accounts/logout/";
    //create account
    public static final String API_CREATE = "accounts/create/";
    //create post
    public static final String API_CREATESubPost = "posts/create/";
    //load post
    public static final String API_LoadPost = "/posts/get_my_posts/";





    public static final int RESPONSE_CODE_SUCCESS = 200;
    public static final String REPONSE_CHECKEMAIL="true";
    public static final String STATUS = "status";
    public static final String MESSAGE = "message";
    public static final String RESULTS = "results";
    public static final String VALIDATION = "validation";

    //Authen Api
    public static final String API_USERNAME = "username";
    public static final String API_USERNAME_VALUE = "admin@itsupport.com";
    public static final String API_PASSWORD = "password";
    public static final String API_PASSWORD_VALUE = "admin1234";

    public static final String STRING_EMPTY = "";
    public static final int NUMBER_ZERO = 0;
    public static final int PERCENT_FOR_EVERY_ITEM_SIGN_UP = 6;
    public static final int CONDITION_SHOW_BUTTON_NEXT = 70;
    public static final int MIN_LENGHT_USERNAME = 5;
    public static final int MIN_LENGHT_PASSWORD = 6;
    public static final String ERROR = "error:";

    //Share reference
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String REMEMBER = "remember";
    public static final String TOKEN = "token";
    public static final String USER_ID = "user_id";
    public static final String PAGE = "page";
    public static final String ROW_PER_PAGE = "row_per_page";
    public static final String CREATED_AT = "created_at";
    public static final String DATE_TIME = "date_time";
    public static final String USER_TYPE = "user_type";

    //Navigate to View
    public static final String HOME = "home";
    public static final String SIGN_IN = "sign_in";
    public static final String SIGN_UP = "sign_up";
    public static final String FORGOT_PASSWORD = "forgot_password";

    //IMAGE
    public static int RESULT_LOAD_IMG = 1;
    public static int RESULT_CAPTURE = 0;
    public static int RESULT_CROP_IMAGE = 2;
    public static String DATA = "data";
    public static final int AVATAR_WIDTH = 100;
    public static final int AVATAR_HEIGHT = 120;

    //Fragment for activity
    public static final String TAG_POST_TYPE = "post_type";
    public static final String TAG_POST_TYPE_DETAIL = "post_type_detail";
    public static final String TAG_MAP = "map";
    public static final String TAG_LIST = "list";

    //Tab
    public static final int TAB_POST = 0;
    public static final int TAB_SEARCH = 1;
    public static final int TAB_NOTIFICATION = 2;
    public static final int TAB_ACCOUNT = 3;
    //public static final int TAB_ACCOUNT_PROFILE = 0;
    public static final int TAB_ACCOUNT_ACTIVITY = 0;
    public static final int TAB_ACCOUNT_COMPLETE = 1;


    //Duration
    public static final int DURATION_TOAST = 2000;
    public static final String INDEXOF_SEPARATE_DATE = "-";
    public static final int NUMBER_LIST_LOAD_MORE = 0;
    public static final String GPS = "gps";

    //Search
    public static final String QUERY = "query";
    public static final String NORMAL_ACCOUNT = "1";
    public static final String PROVIDER_ACCOUNT = "2";
    public static final int POST_DETAIL_FROM_SEARCH = 1;
    public static final int POST_LIST_FROM_SEARCH = 1;

    //Post
    public static final int NOT_PICK = 0;
    public static final int PICKED = 1;
    public static final int COMPLETED = 2;

    //Directory
    public static final double LOCATION_LAT_LNG_DEFAULT = 0.0;
    public static final int PROVIDER_ACCOUNT_LATLNG = 1;
    public static final int NORMAL_ACCOUNT_LATLNG = 2;
    public static final String POSITION_KEY = "position_key";
    public static final String POSITIONS = "positions";

    //Upload Image
    public static final String ACTION_CROP_IMAGE = "com.android.camera.action.CROP";

    //Notification

    public static final String NUMBER_NOTIFICATION = "number-notification";
    public static final String KEY_NUMBER_NOTIFICATION = "number_notification";
    public static final String POST_NOTIFICATION = "post_notification";
    public static final String KEY_POST_NOTIFICATION = "key_post_notification";
    public static final String KEY_NOTIFICATION = "key_notification";
    public static final String NOTIFICATION = "notification";
    public static final int POST_DETAIL_NOTIFICATION = 2;
    public static final String NOTIFICATION_UPDATE_LOCATION = "update_location";
    public static final String KEY_NOTIFICATION_UPDATE_LOCATION = "KEY_update_location";
    public static final String KEY_POST_ID = "key_post_id";
    public static final String KEY_POST_STATUS = "key_post_status";


}
