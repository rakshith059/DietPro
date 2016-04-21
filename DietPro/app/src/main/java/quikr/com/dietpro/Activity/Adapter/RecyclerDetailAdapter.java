package quikr.com.dietpro.Activity.Adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import quikr.com.dietpro.Activity.Fragment.DietProMyCartFragment;
import quikr.com.dietpro.Activity.MainActivity;
import quikr.com.dietpro.Activity.Model.DetailModel;
import quikr.com.dietpro.Activity.Utils.AppController;
import quikr.com.dietpro.Activity.Utils.Constants;
import quikr.com.dietpro.R;

/**
 * Created by Rakshith
 */
public class RecyclerDetailAdapter extends RecyclerView.Adapter<RecyclerDetailAdapter.RecyclerViewHolder> implements View.OnClickListener{

    Fragment                mContext;
    ArrayList<DetailModel>  detailModels;

    int         detailRowImage;
    String      vitaminId;
    String      vitaminName;
    String      vitaminCategory;
    String      vitaminContent;
    String      vitaminQty;
    String      vitaminUnits;
    String      vitaminPrice;
    String      vitaminQtyRs;
    String      vitaminUnitsRs;
    String      vitaminDiscription;
    String      vitaminItemImage;
    String      userId;

    String      accessToken;
    int         totalPrice;
    int         selectedItemPos = 0;
    String      assetUrl;

    public RecyclerDetailAdapter(Fragment fragment, ArrayList<DetailModel> detailModels)
    {
        this.mContext           = fragment;
        this.detailModels       = detailModels;
        assetUrl                = Constants.getSharedPrefrence(fragment.getActivity() , Constants.DIET_PRO_ASSET_URL);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_row , parent , false);
        accessToken = Constants.getSharedPrefrence(mContext.getActivity() , Constants.DIET_PRO_ACCESS_TOKEN_KEY);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        final DetailModel detailModel = detailModels.get(position);

        userId = Constants.getSharedPrefrence(mContext.getActivity(), Constants.DIET_PRO_USER_ID);

        vitaminId           = detailModel.getVitaminId();
        vitaminName         = detailModel.getVitaminName();
        vitaminCategory     = detailModel.getVitaminCategory();
        vitaminContent      = detailModel.getVitaminContent();
        vitaminQty          = detailModel.getVitaminQty();
        vitaminUnits        = detailModel.getVitaminUnits();
        vitaminPrice        = detailModel.getVitaminPrice();
        vitaminQtyRs        = detailModel.getVitaminQtyRs();
        vitaminUnitsRs      = detailModel.getVitaminUnitsRs();
        vitaminDiscription  = detailModel.getVitaminDiscription();
        vitaminItemImage    = detailModel.getVitaminItemImage();

        AppController.getInstance().getImageLoader().get(assetUrl+detailModel.getVitaminItemImage(), ImageLoader.getImageListener(holder.ivRowImage, R.drawable.diet_pro_image1, R.drawable.diet_pro_image1));

        holder.tvRowTitle.setText(detailModel.getVitaminName());
        holder.tvRowQuantityPerGram.setText(detailModel.getVitaminQty()+ " " + mContext.getActivity().getResources().getString(R.string.carb_per_gram));
        holder.tvRowPricePerGram.setText(mContext.getActivity().getResources().getString(R.string.ruppe_symbol) + " " + detailModel.getVitaminPrice());
        totalPrice = selectedItemPos * Integer.valueOf(detailModel.getVitaminPrice());

        ArrayList<String> list = new ArrayList<String>();
        list.add("select quantity");
        list.add("100");
        list.add("200");
        list.add("300");
        list.add("400");
        list.add("500");
        list.add("600");
        list.add("700");
        list.add("800");
        list.add("900");
        list.add("1000");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mContext.getActivity() , android.R.layout.simple_spinner_dropdown_item , list);
        holder.spRowTotalQuantity.setAdapter(arrayAdapter);
        holder.spRowTotalQuantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    selectedItemPos = position;
                    totalPrice = selectedItemPos * Integer.valueOf(detailModel.getVitaminPrice());

                    Log.d("Rakshith", selectedItemPos + " totalPrice " + totalPrice);
                    mContext.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            holder.tvRowPricePerGram.setText(mContext.getActivity().getResources().getString(R.string.ruppe_symbol) + " " + totalPrice);
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        holder.ivRowCartIcon.setOnClickListener(this);
        holder.ivRowCartIcon.setTag(position);

    }

    @Override
    public int getItemCount() {
        return detailModels.size();
    }

    @Override
    public void onClick(View v) {
        int pos = (int) v.getTag();
        switch (v.getId())
        {
            case R.id.detailRowivRowCartImage:
                if(!TextUtils.isEmpty(userId)) {
                    if(selectedItemPos != 0) {
                        addToCartApi(pos);
                    }
                    else
                    {
                        Toast.makeText(mContext.getActivity(), mContext.getResources().getString(R.string.please_select_quantity), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                break;
        }
    }

    private void addToCartApi(final int pos) {
        MainActivity.showLoader(mContext.getActivity());
        StringRequest sr = new StringRequest(Request.Method.POST, Constants.DIET_PRO_ADD_CART_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Rakshith" , "response from addCart " + response);
                        try {
                            JSONObject jsonObject   = new JSONObject(response);

                            String      error        = jsonObject.getString("error");
                            if(!TextUtils.isEmpty(error)) {
                                Toast.makeText(mContext.getActivity(), error, Toast.LENGTH_SHORT).show();
                                MainActivity.hideLoader();
                            }
                            else {
                                String message = jsonObject.getString("message");
                                Toast.makeText(mContext.getActivity(), message, Toast.LENGTH_SHORT).show();
                                ((MainActivity)mContext.getActivity()).replaceFragment(new DietProMyCartFragment(), "productScreen", null);
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
                    Toast.makeText(mContext.getActivity().getApplicationContext(),errorMessage , Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(mContext.getActivity().getApplicationContext(), mContext.getResources().getString(R.string.exception) , Toast.LENGTH_LONG).show();
                }
                MainActivity.hideLoader();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Constants.DIET_PRO_USER_ID, userId);
                params.put(Constants.DIET_PRO_PROD_ID, detailModels.get(pos).getVitaminId());
                params.put(Constants.DIET_PRO_PROD_TYPE, "DM");
                params.put(Constants.DIET_PRO_PROD_QTY, String.valueOf((selectedItemPos * 100)));
                params.put(Constants.DIET_PRO_PROD_PRICE, String.valueOf(totalPrice));

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

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView ivRowImage;
        TextView tvRowTitle;
        TextView        tvRowQuantityPerGram;
        TextView        tvRowPricePerGram;
        ImageView       ivRowCartIcon;
        Spinner spRowTotalQuantity;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ivRowImage = (ImageView) itemView.findViewById(R.id.detailRowivRowImage);
            tvRowTitle = (TextView) itemView.findViewById(R.id.detailRowtvRowTitle);
            tvRowQuantityPerGram = (TextView) itemView.findViewById(R.id.detailRowtvRowQuantityPerGram);
            tvRowPricePerGram = (TextView) itemView.findViewById(R.id.detailRowtvRowPrice);
            spRowTotalQuantity = (Spinner) itemView.findViewById(R.id.detailRowspSpinner);
            ivRowCartIcon    = (ImageView) itemView.findViewById(R.id.detailRowivRowCartImage);
        }
    }
}
