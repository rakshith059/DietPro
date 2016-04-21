package quikr.com.dietpro.Activity.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import quikr.com.dietpro.Activity.Adapter.DeliciousRecipesAdapter;
import quikr.com.dietpro.Activity.MainActivity;
import quikr.com.dietpro.Activity.Model.DeliciousRecipesModel;
import quikr.com.dietpro.Activity.Utils.Constants;
import quikr.com.dietpro.Activity.Utils.NetworkVolleyRequest;
import quikr.com.dietpro.R;

/**
 * Created by Rakshith
 */
public class DeliciousRecipesFragment extends Fragment {

    DeliciousRecipesAdapter recipesAdapter;
    ArrayList<DeliciousRecipesModel> recipesModelList;
    GridView        gvGridView;

    String          accessToken;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delicious_recipes , container , false);
        gvGridView  = (GridView) view.findViewById(R.id.commonGridgvGridView);

        accessToken = Constants.getSharedPrefrence(getActivity() , Constants.DIET_PRO_ACCESS_TOKEN_KEY);

        recipesModelList = new ArrayList<DeliciousRecipesModel>();
//        recipesModelList.add(new DeliciousRecipesModel(R.drawable.diet_pro_image3 , "Grilled Eggplant pannir", dRecipesName, dRecipesAlt, dRecipesDescription, dRecipesPrice));
//        recipesModelList.add(new DeliciousRecipesModel(R.drawable.diet_pro_image2 , "Grilled Eggplant pannir", dRecipesName, dRecipesAlt, dRecipesDescription, dRecipesPrice));
//        recipesModelList.add(new DeliciousRecipesModel(R.drawable.diet_pro_image1 , "Grilled Eggplant pannir", dRecipesName, dRecipesAlt, dRecipesDescription, dRecipesPrice));
//        recipesModelList.add(new DeliciousRecipesModel(R.drawable.diet_pro_image2 , "Grilled Eggplant pannir", dRecipesName, dRecipesAlt, dRecipesDescription, dRecipesPrice));
//        recipesModelList.add(new DeliciousRecipesModel(R.drawable.diet_pro_image3 , "Grilled Eggplant pannir", dRecipesName, dRecipesAlt, dRecipesDescription, dRecipesPrice));
//        recipesModelList.add(new DeliciousRecipesModel(R.drawable.diet_pro_image2 , "Grilled Eggplant pannir", dRecipesName, dRecipesAlt, dRecipesDescription, dRecipesPrice));
//        recipesModelList.add(new DeliciousRecipesModel(R.drawable.diet_pro_image1 , "Grilled Eggplant pannir", dRecipesName, dRecipesAlt, dRecipesDescription, dRecipesPrice));
//        recipesModelList.add(new DeliciousRecipesModel(R.drawable.diet_pro_image2 , "Grilled Eggplant pannir", dRecipesName, dRecipesAlt, dRecipesDescription, dRecipesPrice));
//
//        recipesAdapter = new DeliciousRecipesAdapter(getActivity() , recipesModelList);
//        gvGridView.setAdapter(recipesAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getDeliciousRecepiesApi();
    }

    private void getDeliciousRecepiesApi() {
        MainActivity.showLoader(getActivity());

        Map<String , String> headerParam = new HashMap<>();
        headerParam.put(Constants.DIET_PRO_AUTHORIZATION, accessToken);

//        Map<String, String> deliciousParam = new HashMap<>();
//        deliciousParam.put(Constants.DIET_PRO_ACTION, Constants.DIET_PRO_GET_DELICIOUS_RECEPIES);

        NetworkVolleyRequest deliciousRecepiesReq = new NetworkVolleyRequest(NetworkVolleyRequest.RequestMethod.GET, Constants.DIET_PRO_BASE_URL+Constants.DIET_PRO_GET_DELICIOUS_RECEPIES, String.class, headerParam, new HashMap<>(), new NetworkVolleyRequest.Callback() {
            @Override
            public void onSuccess(Object response) {
                try {
                    MainActivity.hideLoader();
                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONObject jsonList = jsonObject.getJSONObject("data");
                    JSONArray jsonArray = jsonList.getJSONArray("list");

                    for (int i=0 ; i <jsonArray.length() ; i++)
                    {
                        JSONObject jsonObject1          = jsonArray.getJSONObject(i);
                        String dRecipesId               = jsonObject1.getString("id");
                        String dRecipesName             = jsonObject1.getString("name");
                        String dRecipesAlt              = jsonObject1.getString("alt");
                        String dRecipesDescription      = jsonObject1.getString("discription");
                        String dRecipesImage            = jsonObject1.getString("recipi_image");
                        String dRecipesPrice            = jsonObject1.getString("price");

                        recipesModelList.add(new DeliciousRecipesModel(dRecipesImage , dRecipesId , dRecipesName , dRecipesAlt , dRecipesDescription , dRecipesPrice));
                    }

                    recipesAdapter = new DeliciousRecipesAdapter(DeliciousRecipesFragment.this , recipesModelList);
                    gvGridView.setAdapter(recipesAdapter);

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
        deliciousRecepiesReq.execute();
    }
}
