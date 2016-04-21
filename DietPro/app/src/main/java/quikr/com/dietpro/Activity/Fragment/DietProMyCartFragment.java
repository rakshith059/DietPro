package quikr.com.dietpro.Activity.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import quikr.com.dietpro.Activity.Adapter.RecyclerMyCartAdapter;
import quikr.com.dietpro.Activity.MainActivity;
import quikr.com.dietpro.Activity.Model.CartModel;
import quikr.com.dietpro.Activity.Utils.Constants;
import quikr.com.dietpro.Activity.Utils.NetworkVolleyRequest;
import quikr.com.dietpro.Activity.Utils.RecyclerItemDecorator;
import quikr.com.dietpro.Activity.Utils.Utils;
import quikr.com.dietpro.R;

/**
 * Created by Rakshith
 */
public class DietProMyCartFragment extends Fragment implements View.OnClickListener{
    String          accessToken;
    String          userId;

    ArrayList<CartModel>    cartModelList;
    RecyclerView            lvListView;

    TextView                tvNoData;
    TextView                tvTotalPrice;
    TextView                tvCheckout;

    int                     totalPrice = 0;
    String                  cartId;
    int                     listSize;

    ArrayList<String>       cartIdList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_cart, container, false);
        cartModelList   = new ArrayList<CartModel>();
        accessToken = Constants.getSharedPrefrence(getActivity() , Constants.DIET_PRO_ACCESS_TOKEN_KEY);
        userId      = Constants.getSharedPrefrence(getActivity() , Constants.DIET_PRO_USER_ID);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tvNoData        = (TextView) getView().findViewById(R.id.commonListtvNoData);
        lvListView      = (RecyclerView) getView().findViewById(R.id.commonListlvListView);
        tvTotalPrice    = (TextView) getView().findViewById(R.id.fragmentMyCarttvTotalPrice);
        tvCheckout      = (TextView) getView().findViewById(R.id.fragmentMyCarttvCheckout);

        if(Utils.isNetworkAvailable(getActivity())) {
            getMyCartApi();
        }
        else
        {
            Toast.makeText(getActivity() , getResources().getString(R.string.no_data) , Toast.LENGTH_SHORT).show();
        }

        tvCheckout.setOnClickListener(this);
    }

    private void getMyCartApi() {
        try {
            totalPrice = 0;
            MainActivity.showLoader(getActivity());
            Map<String, String> headerParam = new HashMap<>();
            headerParam.put(Constants.DIET_PRO_AUTHORIZATION, accessToken);

            String url = "http://www.ndezigners.com/diet_pro/api/listCartItems/" + userId;

            NetworkVolleyRequest myCartReq = new NetworkVolleyRequest(NetworkVolleyRequest.RequestMethod.GET, url, String.class, headerParam, new HashMap<>(), new NetworkVolleyRequest.Callback() {
                @Override
                public void onSuccess(Object response) {
                    try {
                        tvNoData.setVisibility(View.GONE);
                        MainActivity.hideLoader();
                        JSONObject jsonObject       = new JSONObject(response.toString());
                        JSONObject jsonData         = jsonObject.getJSONObject("data");
                        JSONArray  jsonCartArray    = jsonData.getJSONArray("cart_items");

                        listSize                = jsonCartArray.length();
                        Constants.setSharedPrefrence(getActivity() , Constants.DIET_PRO_CART_COUNT , String.valueOf(listSize));

                        Intent intent = new Intent(Constants.CART_COUNT_FILTER);
                        intent.putExtra(Constants.DIET_PRO_CART_COUNT , String.valueOf(listSize));
                        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);

                        if(listSize != 0) {
                            for (int i = 0; i < listSize; i++) {
                                JSONObject jsonObject1 = jsonCartArray.getJSONObject(i);
                                cartId = jsonObject1.getString("cart_id");
                                String userId = jsonObject1.getString("user_id");
                                String prodId = jsonObject1.getString("prod_id");
                                String prodType = jsonObject1.getString("prod_type");
                                String prodQty = jsonObject1.getString("prod_qty");
                                String prodPrice = jsonObject1.getString("prod_price");
                                String prodDetails = jsonObject1.getString("prod_details");
                                String cartStatus = jsonObject1.getString("cart_status");
                                String cartCrtdt = jsonObject1.getString("cart_crtdt");

                                cartIdList.add(cartId);

                                totalPrice  = totalPrice + Integer.valueOf(prodPrice);
                                cartModelList.add(new CartModel(cartId, userId, prodId, prodType, prodQty, prodPrice, prodDetails, cartStatus, cartCrtdt));
                            }

                            tvTotalPrice.setText(getResources().getString(R.string.ruppe_symbol) + " " + String.valueOf(totalPrice));

                            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                            RecyclerMyCartAdapter recyclerMyCartAdapter = new RecyclerMyCartAdapter(DietProMyCartFragment.this, cartModelList);
                            lvListView.setLayoutManager(layoutManager);
                            lvListView.addItemDecoration(new RecyclerItemDecorator(10));
                            lvListView.setAdapter(recyclerMyCartAdapter);
                        }
                        else
                        {
                            tvNoData.setVisibility(View.VISIBLE);
                            tvNoData.setText(getResources().getString(R.string.no_data));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        MainActivity.hideLoader();
                    }

                }

                @Override
                public void onError(int errorCode, String errorMessage) {
                    MainActivity.hideLoader();
                }
            } , NetworkVolleyRequest.ContentType.JSON);
            myCartReq.execute();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.fragmentMyCarttvCheckout:
                if(listSize != 0) {
                    Bundle bundle = new Bundle();
                    bundle.putString("totalPrice", String.valueOf(totalPrice));
                    bundle.putStringArrayList("cartIdList", cartIdList);
                    MainActivity.replaceFragment(new CheckoutFragment(), "myCartScreen", bundle);
                }
                else
                {
                    Toast.makeText(getActivity() , getResources().getString(R.string.start_shopping) , Toast.LENGTH_SHORT).show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            MainActivity.replaceFragment(new ViewPagerFragment() , null , null);
                        }
                    }, 1000);
                }
                break;
            default:
                break;
        }
    }
}
