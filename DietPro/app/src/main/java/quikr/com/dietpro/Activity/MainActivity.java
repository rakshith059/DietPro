package quikr.com.dietpro.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import quikr.com.dietpro.Activity.Fragment.ContactUsFragment;
import quikr.com.dietpro.Activity.Fragment.DietProMyCartFragment;
import quikr.com.dietpro.Activity.Fragment.LoginFragment;
import quikr.com.dietpro.Activity.Fragment.MyAddressFragment;
import quikr.com.dietpro.Activity.Fragment.MyorderFragement;
import quikr.com.dietpro.Activity.Fragment.ViewPagerFragment;
import quikr.com.dietpro.Activity.Utils.AppController;
import quikr.com.dietpro.Activity.Utils.Constants;
import quikr.com.dietpro.Activity.Utils.NetworkVolleyRequest;
import quikr.com.dietpro.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener , View.OnClickListener {

    FrameLayout                                 flMainContainer;
    static ArrayList<Fragment>                  mFragmentList = new ArrayList<Fragment>();
    static AppCompatActivity                    mContext;
    static Fragment                             mFragment;

    static ProgressDialog                              ploader;

    String                                      accessToken;
    ImageView                                   ivProfilePic;
    TextView                                    tvProfileName;

    TextView                                    tvCartCount;
    String                                      cartCount;

    String userName;
    String userEmail;
    String userMobile;
    String userProfilePic;
    String userVerified;
    String userCrtdt;
    View headerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cartCount   = Constants.getSharedPrefrence(this, Constants.DIET_PRO_CART_COUNT);

        intiActionBar();

        mContext = MainActivity.this;
        accessToken = Constants.getSharedPrefrence(this, Constants.DIET_PRO_ACCESS_TOKEN_KEY);

        LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(cartCountreciver , new IntentFilter(Constants.CART_COUNT_FILTER));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_main);

        ivProfilePic    = (ImageView) headerLayout.findViewById(R.id.navHeaderMainProfilePic);
        tvProfileName   = (TextView) headerLayout.findViewById(R.id.navHeaderMainProfileName);

        if(!TextUtils.isEmpty(accessToken))
        {
            navigationView.getMenu().findItem(R.id.activityMainDrawerLogin).setVisible(false);
            navigationView.getMenu().findItem(R.id.activityMainDrawerLogout).setVisible(true);
            navigationView.getMenu().findItem(R.id.activityMainDrawerHome).setVisible(true);
            navigationView.getMenu().findItem(R.id.activityMainDrawerMyOrders).setVisible(true);
            navigationView.getMenu().findItem(R.id.activityMainDrawerMyAddress).setVisible(true);
            addFragment(new ViewPagerFragment(), null);
            myProfileApi();
        }
        else {
            navigationView.getMenu().findItem(R.id.activityMainDrawerLogin).setVisible(true);
            navigationView.getMenu().findItem(R.id.activityMainDrawerLogout).setVisible(false);
            navigationView.getMenu().findItem(R.id.activityMainDrawerMyAddress).setVisible(false);
            navigationView.getMenu().findItem(R.id.activityMainDrawerHome).setVisible(false);
            navigationView.getMenu().findItem(R.id.activityMainDrawerMyOrders).setVisible(false);
            addFragment(new LoginFragment(), null);
        }

        flMainContainer = (FrameLayout) findViewById(R.id.contentMainflMainContainer);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

    }

    private void myProfileApi() {
        HashMap<String , String> header = new HashMap<>();
        header.put(Constants.DIET_PRO_AUTHORIZATION, accessToken);

        String userId = Constants.getSharedPrefrence(this, Constants.DIET_PRO_USER_ID);

        NetworkVolleyRequest myProfileReq = new NetworkVolleyRequest(NetworkVolleyRequest.RequestMethod.GET, Constants.DIET_PRO_MY_PROFILE + userId, String.class, header, new HashMap<>(), new NetworkVolleyRequest.Callback() {
            @Override
            public void onSuccess(Object response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    Log.d("Rakshith" , "response from myProfile jsonObject " + jsonObject + " jsonObject1 " + jsonObject1);

                    userName         = jsonObject1.getString("user_name");
                    userEmail        = jsonObject1.getString("user_email");
                    userMobile       = jsonObject1.getString("user_mobile");
                    userProfilePic   = jsonObject1.getString("user_profile_pic");
                    userVerified     = jsonObject1.getString("user_verified");
                    userCrtdt        = jsonObject1.getString("user_crtdt");

                    Log.d("Rakshith" , "response from myProfile userName " + userName + " userEmail " + userEmail + " userProfilePic " + userProfilePic);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(!TextUtils.isEmpty(userName)) {
                    tvProfileName.setText(userName);
                }
//                else
//                {
//                    tvProfileName.setVisibility(View.GONE);
//                }
                if(!TextUtils.isEmpty(userProfilePic))
                {
                    String assetUrl                = Constants.getSharedPrefrence(MainActivity.this , Constants.DIET_PRO_ASSET_URL);
                    AppController.getInstance().getImageLoader().get(assetUrl + userProfilePic, ImageLoader.getImageListener(ivProfilePic, R.drawable.diet_pro_image1, R.drawable.diet_pro_image1));
                }
//                else
//                {
//                    ivProfilePic.setVisibility(View.GONE);
//                }
            }

            @Override
            public void onError(int errorCode, String errorMessage) {

            }
        }, NetworkVolleyRequest.ContentType.JSON);
        myProfileReq.execute();
    }

    private void intiActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        View view = LayoutInflater.from(this).inflate(R.layout.custom_action_bar, null);

        ImageView ivMyCart = (ImageView) view.findViewById(R.id.customActionBarivMyCart);
        ivMyCart.setOnClickListener(this);

        tvCartCount = (TextView) view.findViewById(R.id.customActionBartvCartCount);
        if(!TextUtils.isEmpty(cartCount)) {
            tvCartCount.setText(cartCount);
        }
        actionBar.setCustomView(view);
    }

    private BroadcastReceiver cartCountreciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            cartCount = intent.getStringExtra(Constants.DIET_PRO_CART_COUNT);
            if(!TextUtils.isEmpty(cartCount)) {
                tvCartCount.setText(cartCount);
            }
        }
    };

    public static void showLoader(final Activity mActivity)
    {
        if(mActivity != null && !mActivity.isFinishing()) {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (ploader != null && ploader.isShowing()) {
                        ploader.dismiss();
                    }
                    ploader = new ProgressDialog(mActivity);
                    ploader.setMessage(mActivity.getString(R.string.loading));
                    ploader.show();
                    ploader.setCanceledOnTouchOutside(false);
                }
            });
        }
    }

    public static void hideLoader()
    {
        try {
            if (ploader != null && ploader.isShowing())
                ploader.dismiss();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void hideKeyBoard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if(inputMethodManager.isActive()) {
            if (inputMethodManager.isAcceptingText()) {
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    public static void addFragment(Fragment fragment , String mBackStack)
    {
        if(mContext == null)
        {
            return;
        }
        mFragmentList.add(fragment);

        FragmentTransaction fragmentTransaction = mContext.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.contentMainflMainContainer, fragment);

        if(mBackStack != null)
        {
            fragmentTransaction.addToBackStack(mBackStack);
        }
        mFragment = fragment;
        fragmentTransaction.commit();
    }

    public static void replaceFragment(Fragment fragment , String mBackStack , Bundle bundle)
    {
        if(mContext == null)
        {
            return;
        }
        mFragmentList.add(fragment);

        FragmentTransaction fragmentTransaction = mContext.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.contentMainflMainContainer, fragment);

        if(mBackStack != null)
        {
            fragmentTransaction.addToBackStack(mBackStack);
        }
        mFragment = fragment;
        if(bundle != null) {
            mFragment.setArguments(bundle);
        }
        fragmentTransaction.commit();
    }

    public static Fragment getmFragment()
    {
        if(mFragmentList.size() > 0)
        {
            return mFragmentList.get(mFragmentList.size() - 1);
        }
        return null;
    }

    public static void popCurrentFragment()
    {
        if(mFragmentList.size() > 0)
        {
            mFragmentList.remove(mFragmentList.size() - 1);
            if(mFragmentList.size() > 0)
            {
                mFragmentList.remove(mFragmentList.size() - 1);
            }
        }
        mContext.getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.activityMainDrawerHome)
        {
            replaceFragment(new ViewPagerFragment(), null , null);
        }
        else if(id == R.id.activityMainDrawerLogin)
        {
            replaceFragment(new LoginFragment() , "loginScreen" , null);
        }
        else if (id == R.id.activityMainDrawerMyOrders) {
            replaceFragment(new MyorderFragement() , "myOrderScreen" ,new Bundle());
        } else if (id == R.id.activityMainDrawerMyAddress) {
            replaceFragment(new MyAddressFragment() , "myAddressScreen" , new Bundle());
        } else if (id == R.id.activityMainDrawerContactUs) {
            replaceFragment(new ContactUsFragment() , "contactUsScreen" , new Bundle());
        } else if (id == R.id.activityMainDrawerAboutUs) {

        }
        else if(id == R.id.activityMainDrawerLogout)
        {
            logOut();
//            deleteCache();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logOut() {
        Constants.setSharedPrefrence(MainActivity.this , Constants.DIET_PRO_ADDRESS_ID , "");
        Constants.setSharedPrefrence(MainActivity.this , Constants.DIET_PRO_ADDRESS_TITLE , "");
        Constants.setSharedPrefrence(MainActivity.this , Constants.DIET_PRO_ADDRESS_PHONE , "");
        Constants.setSharedPrefrence(MainActivity.this , Constants.DIET_PRO_ADDRESS_DETAIL , "");
        Constants.setSharedPrefrence(MainActivity.this , Constants.DIET_PRO_ASSET_URL , "");
        Constants.setSharedPrefrence(MainActivity.this, Constants.DIET_PRO_CART_COUNT, "");
        Constants.setSharedPrefrence(MainActivity.this, Constants.DIET_PRO_ACCESS_TOKEN_KEY, "");
        Constants.setSharedPrefrence(MainActivity.this, Constants.DIET_PRO_USER_ID, "");
        Intent intent = new Intent(this , MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    public void deleteCache() {
        try {
            File cache = getCacheDir();
            File appDir = new File(cache.getParent());
            if (appDir.exists()) {
                String[] children = appDir.list();
                for (String s : children) {
                    if (!s.equals("lib")) {
                        deleteDir(new File(appDir, s));
                        Log.i("TAG", "**************** File /data/data/APP_PACKAGE/" + s + " DELETED *******************");
                        replaceFragment(new LoginFragment() , null , null);
                    }
                }
            }
        } catch (Exception e) {}
    }

    public static boolean deleteDir(File dir)
    {
        if (dir != null && dir.isDirectory()) {
        String[] children = dir.list();
        for (int i = 0; i < children.length; i++) {
            boolean success = deleteDir(new File(dir, children[i]));
            if (!success) {
                return false;
            }
        }
    }
        return dir.delete();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.customActionBarivMyCart:
                replaceFragment(new DietProMyCartFragment() , "homeScreen" , null);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mContext = null;
    }
}
