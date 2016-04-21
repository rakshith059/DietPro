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
import java.util.List;
import java.util.Map;

import quikr.com.dietpro.Activity.Adapter.RecyclerPersonalTrainerAdapter;
import quikr.com.dietpro.Activity.MainActivity;
import quikr.com.dietpro.Activity.Model.TrainerModel;
import quikr.com.dietpro.Activity.Utils.Constants;
import quikr.com.dietpro.Activity.Utils.NetworkVolleyRequest;
import quikr.com.dietpro.Activity.Utils.RecyclerItemDecorator;
import quikr.com.dietpro.R;

/**
 * Created by Rakshith
 */
public class PersonalTrainerFragment extends Fragment {
    RecyclerView            lvTrainerList;
    List<TrainerModel>      trainerModels = new ArrayList<TrainerModel>();
    String                  accessToken;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.common_list_view , container , false);
        accessToken = Constants.getSharedPrefrence(getActivity() , Constants.DIET_PRO_ACCESS_TOKEN_KEY);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lvTrainerList = (RecyclerView) getView().findViewById(R.id.commonListlvListView);

        getPersonalTrainerApi();
    }

    private void getPersonalTrainerApi() {
        try {
            trainerModels.clear();
            MainActivity.showLoader(getActivity());
            Map<String, String> headerParam = new HashMap<>();
            headerParam.put(Constants.DIET_PRO_AUTHORIZATION, accessToken);

//            Map<String, String> categoryParam = new HashMap<>();
//            categoryParam.put(Constants.DIET_PRO_ACTION, Constants.DIET_PRO_GET_PERSONAL_TRAINERS);

            NetworkVolleyRequest categoryReq = new NetworkVolleyRequest(NetworkVolleyRequest.RequestMethod.GET, Constants.DIET_PRO_BASE_URL+Constants.DIET_PRO_GET_PERSONAL_TRAINERS , String.class, headerParam, new HashMap<>(), new NetworkVolleyRequest.Callback() {
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
                            JSONObject jsonObject1  = jsonArray.getJSONObject(i);
                            String trainerId        = jsonObject1.getString("id");
                            String trainerName      = jsonObject1.getString("name");
                            String trainerLocation  = jsonObject1.getString("location");
                            String trainerLat       = jsonObject1.getString("tr_lat");
                            String trainerLong      = jsonObject1.getString("tr_lng");
                            String trainerContact   = jsonObject1.getString("contact");
                            String trainerDetail    = jsonObject1.getString("details");
                            String trainerImage     = jsonObject1.getString("image");
                            String trrainerCrtDate  = jsonObject1.getString("crt_dt");

                            trainerModels.add(new TrainerModel(trainerId , trainerName , trainerLocation , trainerLat , trainerLong , trainerContact , trainerDetail , trainerImage ,trrainerCrtDate));
                        }

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                        lvTrainerList.setLayoutManager(linearLayoutManager);
                        RecyclerPersonalTrainerAdapter recyclerAdapter = new RecyclerPersonalTrainerAdapter(PersonalTrainerFragment.this , trainerModels);
                        lvTrainerList.addItemDecoration(new RecyclerItemDecorator(10));
                        lvTrainerList.setAdapter(recyclerAdapter);


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
