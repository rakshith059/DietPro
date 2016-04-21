package quikr.com.dietpro.Activity.Fragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import quikr.com.dietpro.Activity.Adapter.RecyclerDietMateAdapter;
import quikr.com.dietpro.Activity.MainActivity;
import quikr.com.dietpro.Activity.Model.DietModel;
import quikr.com.dietpro.Activity.Utils.Constants;
import quikr.com.dietpro.Activity.Utils.NetworkVolleyRequest;
import quikr.com.dietpro.Activity.Utils.RecyclerItemDecorator;
import quikr.com.dietpro.R;

/**
 * Created by Rakshith
 */
public class DietMateFragment extends Fragment implements View.OnClickListener{

    ArrayList<DietModel> dietModels = new ArrayList<DietModel>();
//    DietMateAdapter dietMateAdapter;
    RecyclerView     lvDietList;
    TextView        tvDeliciousRecipes;

    String          accessToken;
    TextView        tvNoData;

    public DietMateFragment()
    {  }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diet_mate , container , false);

        accessToken = Constants.getSharedPrefrence(getActivity() , Constants.DIET_PRO_ACCESS_TOKEN_KEY);

        tvNoData    = (TextView) view.findViewById(R.id.commonListtvNoData);
        lvDietList = (RecyclerView) view.findViewById(R.id.commonListlvListView);
        getCategoryApi();

        return view;
    }

    private void getCategoryApi() {
        try {
            MainActivity.showLoader(getActivity());
            dietModels.clear();
            dietModels.add(new DietModel("diet_pro_image1", "0", "Delicious Recipes", "Delicious Recipes"));
            MainActivity.showLoader(getActivity());
            Map<String, String> headerParam = new HashMap<>();
            headerParam.put(Constants.DIET_PRO_AUTHORIZATION, accessToken);

            Map<String, String> categoryParam = new HashMap<>();
            categoryParam.put(Constants.DIET_PRO_ACTION, Constants.DIET_PRO_GET_CATEGORIES);

            NetworkVolleyRequest categoryReq = new NetworkVolleyRequest(NetworkVolleyRequest.RequestMethod.GET, Constants.DIET_PRO_BASE_URL + Constants.DIET_PRO_GET_CATEGORIES, String.class, headerParam, new HashMap<>(), new NetworkVolleyRequest.Callback() {
                @Override
                public void onSuccess(Object response) {
                    Log.d("Rakshith", "response from get category " + response);
                    try {
                        MainActivity.hideLoader();
                        JSONObject jsonObject = new JSONObject(response.toString());
                        JSONObject jsonList = jsonObject.getJSONObject("data");
                        JSONArray  jsonArray = jsonList.getJSONArray("list");

                        String assetUrl     = jsonObject.getString("asset_url");

                        if(!TextUtils.isEmpty(assetUrl))
                        {
                            Constants.setSharedPrefrence(getActivity() , Constants.DIET_PRO_ASSET_URL , assetUrl);
                        }

                        int listSize        = jsonArray.length();
                        if(listSize != 0) {
                            tvNoData.setVisibility(View.GONE);
                            for (int i = 0; i < listSize; i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String categoryId = jsonObject1.getString("id");
                                String categoryTitle = jsonObject1.getString("title");
                                String categoryImage = jsonObject1.getString("category_image");
                                String categoryContent = jsonObject1.getString("content");

                                dietModels.add(new DietModel(categoryImage, categoryId, categoryTitle, categoryContent));
                            }

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                            lvDietList.setLayoutManager(linearLayoutManager);
                            RecyclerDietMateAdapter recyclerAdapter = new RecyclerDietMateAdapter(DietMateFragment.this, dietModels);
                            lvDietList.addItemDecoration(new RecyclerItemDecorator(10));
                            lvDietList.setAdapter(recyclerAdapter);
                        }
                        else
                        {
                            tvNoData.setVisibility(View.VISIBLE);
                            tvNoData.setText(getResources().getString(R.string.no_data));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(int errorCode, String errorMessage) {
                    Log.d("Rakshith", "error from get category " + errorMessage);
                    MainActivity.hideLoader();
                }
            }, NetworkVolleyRequest.ContentType.JSON);
            categoryReq.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
//            case R.id.fragmentDietMatetvDeliciousRecipes:
//                MainActivity.replaceFragment(new DeliciousRecipesFragment() , "deliciousRecipesFragment" , new Bundle());
//                showStatusBarNotification();
//                break;
            default:
                break;
        }
    }

    private void showStatusBarNotification() {
        PendingIntent pendingIntent = preparePendingIntent();
        Notification notification = createNotification(pendingIntent);
        displayNotification(notification);
    }

    private void displayNotification(Notification notification) {
        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1 , notification);
    }

    private Notification createNotification(PendingIntent pendingIntent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity());
        long[] vibrationPattern = {0, 200};

        Notification notification = builder.setSmallIcon(R.drawable.side_nav_bar)
                .setContentTitle("testing status bar")
                .setAutoCancel(true)
                .setVibrate(vibrationPattern)
                .setContentIntent(pendingIntent)
                .setContentInfo("click on this notification to open the new fragment").build();
        return notification;
    }

    private PendingIntent preparePendingIntent() {
        Intent intent = new Intent(getActivity() , MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity() , 0 , intent , PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }
}
