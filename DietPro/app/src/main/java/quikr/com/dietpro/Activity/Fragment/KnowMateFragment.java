package quikr.com.dietpro.Activity.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import quikr.com.dietpro.Activity.Adapter.RecyclerKnowMateAdapter;
import quikr.com.dietpro.Activity.MainActivity;
import quikr.com.dietpro.Activity.Model.KnowMateModel;
import quikr.com.dietpro.Activity.Utils.Constants;
import quikr.com.dietpro.Activity.Utils.NetworkVolleyRequest;
import quikr.com.dietpro.Activity.Utils.RecyclerItemDecorator;
import quikr.com.dietpro.R;

/**
 * Created by Rakshith
 */
public class KnowMateFragment extends Fragment {

    ArrayList<KnowMateModel> knowMateModels = new ArrayList<KnowMateModel>();
    RecyclerView lvDietList;
    String          accessToken;

    public KnowMateFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diet_mate , container , false);
        accessToken = Constants.getSharedPrefrence(getActivity() , Constants.DIET_PRO_ACCESS_TOKEN_KEY);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lvDietList = (RecyclerView) getView().findViewById(R.id.commonListlvListView);
        getKnowMateApi();
    }

    private void getKnowMateApi() {
        try {
            knowMateModels.clear();
            MainActivity.showLoader(getActivity());
            Map<String, String> headerParam = new HashMap<>();
            headerParam.put(Constants.DIET_PRO_AUTHORIZATION, accessToken);

//            Map<String, String> categoryParam = new HashMap<>();
//            categoryParam.put(Constants.DIET_PRO_ACTION, Constants.DIET_PRO_GET_KNOW_YOUR_DIET);

            NetworkVolleyRequest categoryReq = new NetworkVolleyRequest(NetworkVolleyRequest.RequestMethod.GET, Constants.DIET_PRO_BASE_URL+Constants.DIET_PRO_GET_KNOW_YOUR_DIET, String.class, headerParam, new HashMap<>(), new NetworkVolleyRequest.Callback() {
                @Override
                public void onSuccess(Object response) {
                    Log.d("Rakshith", "response from get category " + response);
                    try {
                        MainActivity.hideLoader();
                        JSONObject jsonObject = new JSONObject(response.toString());
                        JSONObject jsonList = jsonObject.getJSONObject("data");
                        JSONArray jsonArray = jsonList.getJSONArray("list");

                        for (int i=0 ; i <jsonArray.length() ; i++)
                        {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String knowMateId       = jsonObject1.getString("id");
                            String categoryId       = jsonObject1.getString("category_id");
                            String knowMateName     = jsonObject1.getString("name");
                            String knowMateDetail   = jsonObject1.getString("details");
                            String knowMateCrtDate  = jsonObject1.getString("crt_dt");

                            knowMateModels.add(new KnowMateModel(knowMateId , categoryId , knowMateName , knowMateDetail , knowMateCrtDate));
                        }

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                        lvDietList.setLayoutManager(linearLayoutManager);
                        RecyclerKnowMateAdapter recyclerAdapter = new RecyclerKnowMateAdapter(KnowMateFragment.this , knowMateModels);
                        lvDietList.addItemDecoration(new RecyclerItemDecorator(10));
                        lvDietList.setAdapter(recyclerAdapter);


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

}
