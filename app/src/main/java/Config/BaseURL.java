package Config;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseURL {
    static final String APP_NAME = "Smart Soch  ";
    public static final String PREFS_NAME = "GroceryLoginPrefs";
    public static final String PREFS_NAME2 = "GroceryLoginPrefs2";
    public static final String IS_LOGIN = "isLogin";
    public static final String KEY_NAME = "user_fullname";
    public static final String KEY_EMAIL = "user_email";
    public static final String TOTAL_AMOUNT = "TOTAL_AMOUNT";
    public static final String WALLET_TOTAL_AMOUNT = "WALLET_TOTAL_AMOUNT";
    public static final String COUPON_TOTAL_AMOUNT = "COUPON_TOTAL_AMOUNT";
    public static final String KEY_ID = "user_id";
    public static final String KEY_MOBILE = "user_phone";
    public static final String KEY_IMAGE = "user_image";
    public static final String KEY_WALLET_Ammount = "wallet_ammount";
    public static final String KEY_REWARDS_POINTS = "rewards_points";
    public static final String KEY_PAYMENT_METHOD = "payment_method";
    public static final String KEY_PINCODE = "pincode";
    public static final String KEY_SOCITY_ID = "Socity_id";
    public static final String KEY_REWARDS = "rewards";
    public static final String KEY_SOCITY_NAME = "socity_name";
    public static final String KEY_HOUSE = "house_no";
    public static final String KEY_DATE = "date";
    public static final String KEY_TIME = "time";

    //Store Selection

    public static final String KEY_STORE_COUNT = "STORE_COUNT";
    public static final String KEY_NOTIFICATION_COUNT = "NOTIFICATION_COUNT";

    //Firebase
    public static final String SHARED_PREF = "ah_firebase";
    public static final String TOPIC_GLOBAL = "global";
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;


    public static final String KEY_PASSWORD = "password";

    //City and Store Id
    public static final String CITY_ID = "CITY_ID";
    public static final String STORE_ID = "STORE_ID";

    //https://www.vijaylaxmi.net/admin/index.php/api/get_products
    //public static String BASE_URL = "https://www.vijaylaxmi.net/";
    public static String BASE_URL = "https://www.vijaylaxmi.net/admin/";

    public static String IMG_SLIDER_URL = BASE_URL + "uploads/sliders/";
    public static String IMG_CATEGORY_URL = BASE_URL + "uploads/category/";
    public static String IMG_PRODUCT_URL = BASE_URL + "uploads/products/";

    public static String IMG_PROFILE_URL = BASE_URL + "uploads/profile/";
    public static String GET_SLIDER_URL = BASE_URL + "index.php/api/get_sliders";
    public static String GET_FEAATURED_SLIDER_URL = BASE_URL + "index.php/api/get_feature_banner";
    public static String GET_BANNER_URL = BASE_URL + "index.php/api/get_banner";

    public static String WALLET_REFRESH = BASE_URL + "index.php/api/wallet?user_id=";
    public static String REWARDS_REFRESH = BASE_URL + "index.php/api/rewards?user_id=";



    public static String PUT_SUGGESTION_URL = BASE_URL + "index.php/api/put_suggestion";
    public static String GET_CATEGORY_URL = BASE_URL + "index.php/api/get_categories";
    public static String GET_ALL_CATEGORY_URL = BASE_URL + "index.php/api/get_all_categories";
    public static String GET_SLIDER_CATEGORY_URL = BASE_URL + "index.php/api/get_sub_cat";
    public static String GET_CATEGORY_ICON_URL = BASE_URL + "index.php/api/icon";
    public static String COUPON_CODE = BASE_URL + "index.php/api/get_coupons";

    //Home PAGE

    public static String GET_MENU_PRODUCTS = BASE_URL + "index.php/api/icon";
    public static String GET_MENU_ICON_PRODUCT_URL = BASE_URL + "index.php/api/get_header_products";
    public static String GET_DEAL_OF_DAY_PRODUCTS = BASE_URL + "index.php/api/deal_product";
    public static String GET_ALL_DEAL_OF_DAY_PRODUCTS = BASE_URL + "index.php/api/get_all_deal_product";
    public static String GET_TOP_SELLING_PRODUCTS = BASE_URL + "index.php/api/top_selling_product";
    public static String GET_NEW_PRODUCTS = BASE_URL + "index.php/api/new_product";
    public static String GET_ALL_TOP_SELLING_PRODUCTS = BASE_URL + "index.php/api/get_all_top_selling_product";


    public static String GET_PRODUCT_URL = BASE_URL + "index.php/api/get_products";

    public static String GET_ATTR_URL = BASE_URL + "index.php/api/get_attribute";

    public static String GET_ABOUT_URL = BASE_URL + "index.php/api/aboutus";

    public static String GET_SUPPORT_URL = BASE_URL + "index.php/api/support";

    public static String GET_TERMS_URL = BASE_URL + "index.php/api/terms";

    public static String GET_TIME_SLOT_URL = BASE_URL + "index.php/api/get_time_slot";

    public static String LOGIN_URL = BASE_URL + "index.php/api/login";

    public static String REGISTER_URL = BASE_URL + "index.php/api/signup";

    public static String GET_SOCITY_URL = BASE_URL + "index.php/api/get_society";

    public static String EDIT_PROFILE_URL = BASE_URL + "index.php/api/update_userdata";
    public static String WALLET_AMOUNT_URL = BASE_URL + "index.php/api/wallet_amount";

    public static String ADD_ORDER_URL = BASE_URL + "index.php/api/send_order";
    public static String Wallet_CHECKOUT = BASE_URL + "index.php/api/wallet_at_checkout";
    public static String GET_ORDER_URL = BASE_URL + "index.php/api/my_orders";

    public static String GET_DELIVERD_ORDER_URL = BASE_URL + "index.php/api/delivered_complete";

    public static String ORDER_DETAIL_URL = BASE_URL + "index.php/api/order_details";

    public static String DELETE_ORDER_URL = BASE_URL + "index.php/api/cancel_order";

    public static String GET_LIMITE_SETTING_URL = BASE_URL + "index.php/api/get_limit_settings";

    public static String ADD_ADDRESS_URL = BASE_URL + "index.php/api/add_address";

    public static String GET_ADDRESS_URL = BASE_URL + "index.php/api/get_address";

    public static String FORGOT_URL = BASE_URL + "index.php/api/forgot_password";

    public static String JSON_RIGISTER_FCM = BASE_URL + "index.php/api/register_fcm";

    public static String CHANGE_PASSWORD_URL = BASE_URL + "index.php/api/change_password";

    public static String DELETE_ADDRESS_URL = BASE_URL + "index.php/api/delete_address";

    public static String EDIT_ADDRESS_URL = BASE_URL + "index.php/api/edit_address";


    // global topic to receive app wide push notifications

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";

    public static final String PUSH_NOTIFICATION = "pushNotification";

    // product details

    public static String PRODUCT_DETAILS = BASE_URL + "index.php/api/get_products_details";
}
