package quikr.com.dietpro.Activity.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import quikr.com.dietpro.Activity.Adapter.RecyclerMyAddressAdapter;
import quikr.com.dietpro.Activity.MainActivity;
import quikr.com.dietpro.Activity.Model.MyAddressModel;
import quikr.com.dietpro.Activity.Utils.Constants;
import quikr.com.dietpro.Activity.Utils.NetworkVolleyRequest;
import quikr.com.dietpro.Activity.Utils.RecyclerItemDecorator;
import quikr.com.dietpro.R;

/**
 * Created by Rakshith
 */
public class MyAddressFragment extends Fragment implements View.OnClickListener{
    TextView            tvAddAddress;
    RecyclerView        rvAddressList;
    TextView            tvNoData;
    String              userId;
    String              accessToken;

    ArrayList<MyAddressModel> myAddressList = new ArrayList<MyAddressModel>();

    Bundle bundle = new Bundle();
    String from;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_address, container, false);
        accessToken = Constants.getSharedPrefrence(getActivity() , Constants.DIET_PRO_ACCESS_TOKEN_KEY);
        userId = Constants.getSharedPrefrence(getActivity(), Constants.DIET_PRO_USER_ID);
        bundle = getArguments();
        from = bundle.getString("from");
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tvAddAddress    = (TextView) getView().findViewById(R.id.fragmentMyAddresstvAddAddress);
        rvAddressList   = (RecyclerView) getView().findViewById(R.id.commonListlvListView);
        tvNoData        = (TextView) getView().findViewById(R.id.commonListtvNoData);

        getMyAddressApi();

        tvAddAddress.setOnClickListener(this);
    }

    private void getMyAddressApi() {
        try {
            myAddressList.clear();
            MainActivity.showLoader(getActivity());
            Map<String, String> headerParam = new HashMap<>();
            headerParam.put(Constants.DIET_PRO_AUTHORIZATION, accessToken);

            String url = "http://www.ndezigners.com/diet_pro/api/getDmCategory/" + userId;
            NetworkVolleyRequest vitaminReq = new NetworkVolleyRequest(NetworkVolleyRequest.RequestMethod.GET, Constants.DIET_PRO_FETCH_ADDRESS + userId, String.class, headerParam, new HashMap<>(), new NetworkVolleyRequest.Callback() {
                @Override
                public void onSuccess(Object response) {
                    Log.d("Rakshith", "response from get category " + response);
                    try {
                        MainActivity.hideLoader();
                        JSONObject jsonObject = new JSONObject(response.toString());
                        JSONArray  jsonArray = jsonObject.getJSONArray("data");
                        int row             = jsonArray.length();
                        if(row != 0) {
                            tvNoData.setVisibility(View.GONE);
                            for (int i = 0; i < row; i++) {
                                JSONObject jsonObject1  = jsonArray.getJSONObject(i);
                                String addressId        = jsonObject1.getString("addr_id");
                                String userId           = jsonObject1.getString("user_id");
                                String addressTitle     = jsonObject1.getString("addr_title");
                                String addressDetail    = jsonObject1.getString("addr_details");
                                String addressLandMark  = jsonObject1.getString("addr_landmark");
                                String addressPincode   = jsonObject1.getString("addr_pincode");
                                String addressCity      = jsonObject1.getString("addr_city");
                                String addressState     = jsonObject1.getString("addr_state");
                                String addressPhone     = jsonObject1.getString("addr_phone");
                                String addressEmail     = jsonObject1.getString("addr_email");
                                String addressType      = jsonObject1.getString("addr_typ");
                                String addressIsDefault = jsonObject1.getString("addr_isDefault");
                                String addressCrtDt     = jsonObject1.getString("addr_crtdt");

                                Constants.setSharedPrefrence(getActivity() , Constants.DIET_PRO_ADDRESS_ID , addressId);
                                Constants.setSharedPrefrence(getActivity() , Constants.DIET_PRO_ADDRESS_TITLE , addressTitle);
                                Constants.setSharedPrefrence(getActivity() , Constants.DIET_PRO_ADDRESS_PHONE , addressPhone);
                                Constants.setSharedPrefrence(getActivity() , Constants.DIET_PRO_ADDRESS_DETAIL , addressDetail);

                                myAddressList.add(new MyAddressModel(addressId , userId , addressTitle , addressDetail , addressLandMark , addressPincode , addressCity,
                                        addressState , addressPhone , addressEmail , addressType , addressIsDefault , addressCrtDt));
                            }

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                            RecyclerMyAddressAdapter recyclerAdapter = new RecyclerMyAddressAdapter(MyAddressFragment.this, myAddressList , from);
                            rvAddressList.setLayoutManager(linearLayoutManager);
                            rvAddressList.addItemDecoration(new RecyclerItemDecorator(10));
                            rvAddressList.setAdapter(recyclerAdapter);
                        }
                        else
                        {
                            tvNoData.setText(getResources().getString(R.string.no_data));
                            tvNoData.setVisibility(View.VISIBLE);
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
            vitaminReq.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.fragmentMyAddresstvAddAddress:
                Bundle bundle = new Bundle();
                bundle.putString("from", Constants.DIET_PRO_FROM_MY_ADDRESS_FRAGMENT);
                MainActivity.replaceFragment(new RegisterMyAddressFragment() , "myAddressScreen" , bundle);
                break;
            default:break;
        }
    }
}
