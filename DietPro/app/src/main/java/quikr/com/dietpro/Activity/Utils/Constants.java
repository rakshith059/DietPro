package quikr.com.dietpro.Activity.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

/**
 * Created by Rakshith
 */
public class Constants {
    public final static String DIET_PRO_BASE_URL                    = "http://www.ndezigners.com/diet_pro/api/";

    public final static String DIET_PRO_LOGIN_URL                   = DIET_PRO_BASE_URL + "userLogin";
    public final static String DIET_PRO_ADD_CART_URL                = DIET_PRO_BASE_URL + "addToCart";
    public final static String DIET_PRO_REGISTER_URL                = DIET_PRO_BASE_URL + "signUp";
    public final static String DIET_PRO_REMOVE_CART_URL             = DIET_PRO_BASE_URL + "removeCartItem";
    public final static String DIET_PRO_ADD_TO_ADDRESS              = "addUserAddress";
    public final static String DIET_PRO_FETCH_ADDRESS               = DIET_PRO_BASE_URL + "fetchAddresses/";
    public final static String DIET_PRO_PLACE_ORDER                 = DIET_PRO_BASE_URL + "placeOrder";

    public final static String DIET_PRO_DELETE_ADDRESS              = DIET_PRO_BASE_URL + "deleteUserAddress/";

    public final static String DIET_PRO_MY_PROFILE                  = DIET_PRO_BASE_URL + "getUserProfile/";
    public final static String DIET_PRO_MY_ORDERS                   = DIET_PRO_BASE_URL + "myOrders/";
    public final static String DIET_PRO_CONTACT_US                  = DIET_PRO_BASE_URL + "contactUs";

    public final static String DIET_PRO_ACTION                      = "action";
    public final static String DIET_PRO_AUTHORIZATION               = "Authorization";

    public final static String DIET_PRO_ACCESS_TOKEN_KEY            = "accessToken";
//    public final static String DIET_PRO_ACCESS_TOKEN                = "accessToken";// = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InRlc3RfdXNlckBhcHBuaW5ncy5jb20iLCJuYW1lIjoidGVzdF91c2VyIiwiaWQiOiI1In0.NSPLpTLb7LJ3Z52L8UtNTKfv-Het9zO7YshToCcNP8k";
    public final static String DIET_PRO_ASSET_URL                   = "assetUrl";


    public final static String DIET_PRO_USER_LOGIN                  = "userLogin";
    public final static String DIET_PRO_GET_CATEGORY                = "getDmCategory";
    public final static String DIET_PRO_GET_CATEGORIES              = "getDmCategories";
    public final static String DIET_PRO_GET_DELICIOUS_RECEPIES      = "getDeliciousRecepies";
    public final static String DIET_PRO_GET_DELICIOUS_RECEPIE_ITEM  = "getDeliciousRecepieItem";
    public final static String DIET_PRO_GET_KNOW_YOUR_DIET          = "getKnowYourDiet";
    public final static String DIET_PRO_GET_KNOW_YOUR_DIET_INNER    = "getKnowYourDietInner";
    public final static String DIET_PRO_GET_PERSONAL_TRAINERS       = "getPersonalTrainers";

    public final static String DIET_PRO_USER_EMAIL                  = "user_email";
    public final static String DIET_PRO_USER_PASSWORD               = "user_password";
    public final static String DIET_PRO_CATEGORY_ID                 = "category_id";
    public final static String DIET_PRO_ITEM_ID                     = "item_id";
    public final static String DIET_PRO_USER_ID                     = "user_id";
    public final static String DIET_PRO_ITEM_NAME                   = "item_name";
    public final static String DIET_PRO_ITEM_QUANTITY               = "item_quantity";
    public final static String DIET_PRO_ITEM_TOTAL_PRICE            = "item_total_price";
    public final static String DIET_PRO_ITEM_BASE_PRICE             = "item_base_price";
    public final static String DIET_PRO_SESSION_ID                  = "session_id";
    public final static String DIET_PRO_ITEM_TYPE                   = "item_type";
    public final static String DIET_PRO_CART_ID                     = "cart_id[]";
    public final static String DIET_PRO_REMOVE_CART_ID              = "cart_id";
    public final static String DIET_PRO_PROD_ID                     = "prod_id";
    public final static String DIET_PRO_PROD_TYPE                   = "prod_type";
    public final static String DIET_PRO_PROD_QTY                    = "prod_qty";
    public final static String DIET_PRO_PROD_PRICE                  = "prod_price";

    public final static String DIET_PRO_USER_NAME                   = "user_name";
    public final static String DIET_PRO_USER_MOBILE                 = "user_mobile";

    public final static String DIET_PRO_ADDRESS_TITLE               = "addr_title";
    public final static String DIET_PRO_ADDRESS_DETAIL              = "addr_details";
    public final static String DIET_PRO_ADDRESS_LANDMARK            = "addr_landmark";
    public final static String DIET_PRO_ADDRESS_PINCODE             = "addr_pincode";
    public final static String DIET_PRO_ADDRESS_CITY                = "addr_city";
    public final static String DIET_PRO_ADDRESS_STATE               = "addr_state";
    public final static String DIET_PRO_ADDRESS_PHONE               = "addr_phone";
    public final static String DIET_PRO_ADDRESS_EMAIL               = "addr_email";
    public final static String DIET_PRO_ADDRESS_TYPE                = "addr_typ";

    public final static String DIET_PRO_ADDRESS_TYPE_HOME           = "HME";
    public final static String DIET_PRO_ADDRESS_TYPE_GYM            = "GYM";

    public final static String DIET_PRO_ADDRESS_ID                  = "addr_id";
    public final static String DIET_PRO_ORDER_PAYMENT_OPTION        = "ord_pymt_opt";
    public final static String DIET_PRO_ORDER_CART_AMOUNT           = "ord_cart_amt";
    public final static String DIET_PRO_ORDER_DELIVERY_AMOUNT       = "ord_dlvy_amt";
    public final static String DIET_PRO_ORDER_NET_AMOUNT            = "ord_net_amt";
    public final static String DIET_PRO_ORDER_FLAVOUR               = "ord_flavour";
    public final static String DIET_PRO_ORDER_NOTES                 = "ord_notes";

    public final static String DIET_PRO_COD                         = "COD";
    public final static String DIET_PRO_PLAIN                       = "PLAIN";
    public final static String DIET_PRO_LIME                        = "LIME";
    public final static String DIET_PRO_GINGER                      = "GINGER";
    public final static String DIET_PRO_GARLIC                      = "GARLIC";
    public final static String DIET_PRO_FROM_MY_ADDRESS_FRAGMENT    = "myAddressFragment";
    public final static String DIET_PRO_FROM_CHECKOUT_FRAGMENT      = "checkoutFragment";

    public final static String DIET_PRO_USER_PHONE                  = "user_phone";
    public final static String DIET_PRO_USER_MESSAGE                = "user_message";
    public final static String DIET_PRO_CART_COUNT                  = "cart_count";
    public final static String CART_COUNT_FILTER                    = "com.dietpro.Activity.MyCart";

    public static boolean isNetworkAvailable(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null;
    }

    public static void setSharedPrefrence(Context context , String key , String value)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key , value);
        editor.apply();
    }

    public static String getSharedPrefrence(Context context , String key)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String value = sharedPreferences.getString(key , "");
        return value;
    }

    public static String emailPattern = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{1,25}" +
            ")+";

}
