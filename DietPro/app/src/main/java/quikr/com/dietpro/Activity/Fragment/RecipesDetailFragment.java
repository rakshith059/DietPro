package quikr.com.dietpro.Activity.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import quikr.com.dietpro.Activity.MainActivity;
import quikr.com.dietpro.Activity.Model.DeliciousRecipesModel;
import quikr.com.dietpro.Activity.Utils.AppController;
import quikr.com.dietpro.Activity.Utils.Constants;
import quikr.com.dietpro.Activity.Utils.NetworkVolleyRequest;
import quikr.com.dietpro.R;

/**
 * Created by Rakshith
 */
public class RecipesDetailFragment extends Fragment implements View.OnClickListener{

    TextView                tvReciepeTitle;
    ImageView               ivReciepeImage;
    TextView                tvReciepeContent1;
    TextView                tvReciepeContent2;
    TextView                tvReciepeContent3;
    TextView                tvReciepeContent4;
    TextView                tvReciepeDescription;
    TextView                tvReciepePrice;

    ImageView               ivAddToCart;

    DeliciousRecipesModel   recipesModel;

    String                  deliciousId;
    String                  accessToken;

    String                  assetUrl;
    String                  userId;
    String                  itemId;
    String                  itemPrice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipes_detail, container, false);

        accessToken = Constants.getSharedPrefrence(getActivity() , Constants.DIET_PRO_ACCESS_TOKEN_KEY);
        assetUrl            = Constants.getSharedPrefrence(getActivity() , Constants.DIET_PRO_ASSET_URL);
        userId = Constants.getSharedPrefrence(getActivity(), Constants.DIET_PRO_USER_ID);

        Bundle bundle = getArguments();
        deliciousId = bundle.getString("dRecipesId");

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tvReciepeTitle          = (TextView) getView().findViewById(R.id.fragmentRecipesDetailtvTitle);
        ivReciepeImage          = (ImageView) getView().findViewById(R.id.fragmentRecipesDetailivRecipeImage);
        tvReciepeContent1       = (TextView) getView().findViewById(R.id.fragmentRecipesDetailtvContent1);
        tvReciepeContent2       = (TextView) getView().findViewById(R.id.fragmentRecipesDetailtvContent2);
        tvReciepeContent3       = (TextView) getView().findViewById(R.id.fragmentRecipesDetailtvContent3);
        tvReciepeContent4       = (TextView) getView().findViewById(R.id.fragmentRecipesDetailtvContent4);
        tvReciepeDescription    = (TextView) getView().findViewById(R.id.fragmentRecipesDetailtvDescription);
        tvReciepePrice          = (TextView) getView().findViewById(R.id.fragmentRecipesDetailtvPrice);

        ivAddToCart             = (ImageView) getView().findViewById(R.id.fragmentRecipesDetailivAddToCart);
        ivAddToCart.setOnClickListener(this);

        getDeliciousDetail();
    }

    private void getDeliciousDetail() {
        MainActivity.showLoader(getActivity());
        Map<String, String> headerParam = new HashMap<>();
        headerParam.put(Constants.DIET_PRO_AUTHORIZATION, accessToken);

        Map<String, String> deliciousDetailParam = new HashMap<>();
        deliciousDetailParam.put(Constants.DIET_PRO_ACTION, Constants.DIET_PRO_GET_DELICIOUS_RECEPIE_ITEM);
        deliciousDetailParam.put(Constants.DIET_PRO_ITEM_ID, deliciousId);

        String url = "http://www.ndezigners.com/diet_pro/api/getDeliciousRecepieItem/" + deliciousId;
        NetworkVolleyRequest deliciousRecepiesReq = new NetworkVolleyRequest(NetworkVolleyRequest.RequestMethod.GET, url, String.class, headerParam, new HashMap<>(), new NetworkVolleyRequest.Callback() {
//        NetworkVolleyRequest deliciousRecepiesReq = new NetworkVolleyRequest(NetworkVolleyRequest.RequestMethod.GET, Constants.DIET_PRO_BASE_URL, String.class, headerParam, deliciousDetailParam, new NetworkVolleyRequest.Callback() {
            @Override
            public void onSuccess(Object response) {
                try {
                    Log.d("Rakshith" , "response from deliciousRecepie " + response);
                    MainActivity.hideLoader();
                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONObject jsonData = jsonObject.getJSONObject("data");
                    JSONObject jsonList = jsonData.getJSONObject("list");

                    itemId               = jsonList.getString("id");
                    String itemName             = jsonList.getString("name");
                    String itemAlt              = jsonList.getString("alt");
                    String itemDescription      = jsonList.getString("discription");
                    String itemImage            = jsonList.getString("recipi_image");
                    itemPrice            = jsonList.getString("price");

                    JSONArray jsonContentArray  = jsonList.getJSONArray("content");
                    for(int i=0 ; i < jsonContentArray.length() ; i++)
                    {
                        JSONObject  jsonContentObj  = jsonContentArray.getJSONObject(i);
                        String contentId            = jsonContentObj.getString("id");
                        String contentFoodItemId    = jsonContentObj.getString("food_item_id");
                        String contentDesc          = jsonContentObj.getString("content");
                        String contentContain       = jsonContentObj.getString("contain");

                        if(i == 0)
                        {
                            tvReciepeContent1.setText(contentDesc + ":" + contentContain + "g");
                        }
                        else if(i == 1)
                        {
                            tvReciepeContent2.setText(contentDesc + ":" + contentContain + "g");
                        }
                        else if(i == 2)
                        {
                            tvReciepeContent3.setText(contentDesc + ":" + contentContain + "g");
                        }
                        else if(i == 3)
                        {
                            tvReciepeContent4.setText(contentDesc + ":" + contentContain + "g");
                        }
                    }

                    AppController.getInstance().getImageLoader().get(assetUrl + itemImage, ImageLoader.getImageListener(ivReciepeImage, R.drawable.diet_pro_image1, R.drawable.diet_pro_image1));

                    tvReciepeTitle.setText(itemName);
                    tvReciepeDescription.setText(itemDescription);
//                    tvReciepeDescription.setText("<p>Carbohydrates are found in almost all living things and play a critical role in the proper functioning of the immune system, fertilization, blood clotting, and human development. A deficiency of carbohydrates can lead to impaired functioning of all these systems, however, in the Western world, deficiency is rare. Excessive consumption of carbohydrates, especially refined carbohydrates like sugar or corn syrup, can lead to obesity, type II diabetes, and cancer. Unhealthy high carbohydrate foods include dried fruit, cereals, crackers, cakes, flours, jams, preserves, bread products, and potato products. Healthy high carbohydrate foods include vegetables, legumes (beans), whole grains, fruits, nuts, and yogurt.<strong>Below is a list of foods highest in carbohydrates, for more see the extended list of???<a href=\\\"http://www.healthaliciousness.com/articles/foods-highest-in-carbohydrates.php#carb-rich-foods-to-avoid\\\">carb rich foods to avoid</a>. For more healthy high carbohydrate choices see the list of???<a href=\\\"http://www.healthaliciousness.com/articles/foods-highest-in-carbohydrates.php#healthy-high-carb-foods\\\">healthy high carb foods</a>.</strong></p>");
                    tvReciepePrice.setText(getResources().getString(R.string.ruppe_symbol) + " " + itemPrice);
                } catch (JSONException e) {
                    e.printStackTrace();
                    MainActivity.hideLoader();
                }
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                MainActivity.hideLoader();
            }
        }, NetworkVolleyRequest.ContentType.JSON);
        deliciousRecepiesReq.execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.fragmentRecipesDetailivAddToCart:
                addToCartApi();
                break;
            default:
                break;
        }
    }

    private void addToCartApi() {
        MainActivity.showLoader(getActivity());
        StringRequest sr = new StringRequest(Request.Method.POST, Constants.DIET_PRO_ADD_CART_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Rakshith" , "response from addCart " + response);
                        try {
                            JSONObject jsonObject   = new JSONObject(response);

                            String      error        = jsonObject.getString("error");
                            if(!TextUtils.isEmpty(error)) {
                                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                MainActivity.hideLoader();
                            }
                            else {
                                String message = jsonObject.getString("message");
                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                ((MainActivity)getActivity()).replaceFragment(new DietProMyCartFragment(), "productScreen", null);
                                MainActivity.hideLoader();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            MainActivity.hideLoader();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorResponse = String.valueOf(error.networkResponse.data);
                try {
                    JSONObject jsonObject = new JSONObject(errorResponse);
                    String errorMessage = jsonObject.getString("error");
                    Log.d("Rakshith" , "error status code " + error.networkResponse.statusCode + " error message " + errorMessage);
                    Toast.makeText(getActivity().getApplicationContext(),errorMessage , Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.exception) , Toast.LENGTH_LONG).show();
                }
                MainActivity.hideLoader();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Constants.DIET_PRO_USER_ID, userId);
                params.put(Constants.DIET_PRO_PROD_ID, itemId);
                params.put(Constants.DIET_PRO_PROD_TYPE, "DR");
                params.put(Constants.DIET_PRO_PROD_QTY, String.valueOf((1)));
                params.put(Constants.DIET_PRO_PROD_PRICE, String.valueOf(itemPrice));

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                header.put(Constants.DIET_PRO_AUTHORIZATION, accessToken);
                header.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
                return header;
            }
        };
        AppController.getInstance().addToRequestQueue(sr);
    }
}
