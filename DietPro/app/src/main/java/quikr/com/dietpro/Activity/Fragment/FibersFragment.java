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

import quikr.com.dietpro.Activity.Adapter.RecyclerDetailAdapter;
import quikr.com.dietpro.Activity.MainActivity;
import quikr.com.dietpro.Activity.Model.DetailModel;
import quikr.com.dietpro.Activity.Utils.Constants;
import quikr.com.dietpro.Activity.Utils.NetworkVolleyRequest;
import quikr.com.dietpro.Activity.Utils.RecyclerItemDecorator;
import quikr.com.dietpro.R;

/**
 * Created by Rakshith
 */
public class FibersFragment extends Fragment {

    RecyclerView lvListView;
    //    DetailAdapter           detailAdapter;
    ArrayList<DetailModel> detailModelList;
    LinearLayoutManager linearLayoutManager;
    String                  categoryId;

    String                  accessToken;
    TextView                tvNoData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.common_list_view , container , false);

        accessToken = Constants.getSharedPrefrence(getActivity() , Constants.DIET_PRO_ACCESS_TOKEN_KEY);
        tvNoData    = (TextView) view.findViewById(R.id.commonListtvNoData);

        detailModelList = new ArrayList<DetailModel>();
        lvListView = (RecyclerView) view.findViewById(R.id.commonListlvListView);

        Bundle  bundle = getArguments();
        if(bundle != null) {
            categoryId = bundle.getString("categoryId");
            categoryDetailApi();
        }

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void categoryDetailApi() {
        try {
            MainActivity.showLoader(getActivity());
            Map<String, String> headerParam = new HashMap<>();
            headerParam.put(Constants.DIET_PRO_AUTHORIZATION, accessToken);

            Map<String, String> categoryParam = new HashMap<>();
            categoryParam.put(Constants.DIET_PRO_CATEGORY_ID, /*categoryId*/"1");
            categoryParam.put(Constants.DIET_PRO_ACTION, Constants.DIET_PRO_GET_CATEGORY);

            String url = "http://www.ndezigners.com/diet_pro/api/getDmCategory/" + 3;
            NetworkVolleyRequest vitaminReq = new NetworkVolleyRequest(NetworkVolleyRequest.RequestMethod.GET, url, String.class, headerParam, new HashMap<>(), new NetworkVolleyRequest.Callback() {
//            NetworkVolleyRequest vitaminReq = new NetworkVolleyRequest(NetworkVolleyRequest.RequestMethod.GET, Constants.DIET_PRO_BASE_URL, String.class, headerParam, categoryParam, new NetworkVolleyRequest.Callback() {
                @Override
                public void onSuccess(Object response) {
                    Log.d("Rakshith", "response from get category " + response);
                    try {
                        MainActivity.hideLoader();
                        JSONObject jsonObject = new JSONObject(response.toString());
                        JSONObject jsonList = jsonObject.getJSONObject("data");

                        int row             = jsonList.getInt("rows");
                        if(row != 0) {
                            tvNoData.setVisibility(View.GONE);
                            JSONArray jsonArray = jsonList.getJSONArray("list");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String vitaminId = jsonObject1.getString("id");
                                String vitaminName = jsonObject1.getString("name");
                                String vitaminCategory = jsonObject1.getString("category");
                                String vitaminContent = jsonObject1.getString("content");
                                String vitaminQty = jsonObject1.getString("qty");
                                String vitaminUnits = jsonObject1.getString("units");
                                String vitaminPrice = jsonObject1.getString("price");
                                String vitaminQtyRs = jsonObject1.getString("qty_rs");
                                String vitaminUnitsRs = jsonObject1.getString("units_rs");
                                String vitaminDiscription = jsonObject1.getString("discription");
                                String vitaminItemImage = jsonObject1.getString("item_image");

                                detailModelList.add(new DetailModel(vitaminId, vitaminName, vitaminCategory, vitaminContent, vitaminQty, vitaminUnits, vitaminPrice,
                                        vitaminQtyRs, vitaminUnitsRs, vitaminDiscription, vitaminItemImage));
                            }

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                            RecyclerDetailAdapter recyclerAdapter = new RecyclerDetailAdapter(FibersFragment.this, detailModelList);
                            lvListView.setLayoutManager(linearLayoutManager);
                            lvListView.addItemDecoration(new RecyclerItemDecorator(10));
                            lvListView.setAdapter(recyclerAdapter);
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

}
