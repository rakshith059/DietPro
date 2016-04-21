package quikr.com.dietpro.Activity.Adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import quikr.com.dietpro.Activity.Model.CartModel;
import quikr.com.dietpro.Activity.Utils.AppController;
import quikr.com.dietpro.Activity.Utils.Constants;
import quikr.com.dietpro.R;

/**
 * Created by Rakshith
 */
public class RecyclerMyCartAdapter extends RecyclerView.Adapter<MyCartHolder> implements View.OnClickListener{
    Fragment                    fragment;
    ArrayList<CartModel>        cartModelList;
    int                         width;

    String                      cardId;
    int                         position;
    String                      accessToken;

    String                      prodName;
    String                      prodImage;
    String                      assetUrl;


    public RecyclerMyCartAdapter(Fragment activity, ArrayList<CartModel> cartModelList) {
        this.fragment           = activity;
        this.cartModelList      = cartModelList;

        DisplayMetrics  displayMetrics = fragment.getActivity().getResources().getDisplayMetrics();
        width = displayMetrics.widthPixels;
    }

    @Override
    public MyCartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(fragment.getActivity()).inflate(R.layout.my_cart_row, null);
        accessToken = Constants.getSharedPrefrence(fragment.getActivity() , Constants.DIET_PRO_ACCESS_TOKEN_KEY);
        assetUrl                = Constants.getSharedPrefrence(fragment.getActivity(), Constants.DIET_PRO_ASSET_URL);
        return new MyCartHolder(view);
    }

    @Override
    public void onBindViewHolder(MyCartHolder holder, int position) {
        CartModel cartModel = cartModelList.get(position);

        String  prodDetail    = cartModel.getProdDetails();
        String  prodQty     = cartModel.getProdQty();
        String  prodPrice   = cartModel.getProdPrice();

        cardId              = cartModel.getCartId();

        holder.llMainContainer.setMinimumWidth(width);

        if(!TextUtils.isEmpty(prodDetail))
        {
            try {
                JSONObject jsonObject = new JSONObject(prodDetail);
                prodName = jsonObject.getString("name");
                prodImage= jsonObject.getString("item_image");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        AppController.getInstance().getImageLoader().get(assetUrl+prodImage, ImageLoader.getImageListener(holder.ivProdImage, R.drawable.diet_pro_image1, R.drawable.diet_pro_image1));
        holder.tvProdName.setText(prodName);
        holder.tvProdQty.setText(prodQty + " gm");
        holder.tvProdPrice.setText(fragment.getActivity().getResources().getString(R.string.ruppe_symbol) + " " + prodPrice);

        holder.ivRemoveIcon.setOnClickListener(this);
        holder.ivRemoveIcon.setTag(R.string.position , position);
    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    @Override
    public void onClick(View v) {
        int pos = (int) v.getTag(R.string.position);
        switch (v.getId())
        {
            case R.id.myCartRowivRemovePic:
//                Toast.makeText(fragment.getActivity() , fragment.getActivity().getResources().getString(R.string.item_removed) , Toast.LENGTH_SHORT).show();
                removeProdApi(pos);
                break;
            default:
                break;
        }
    }

    private void removeProdApi(final int pos) {
        MainActivity.showLoader(fragment.getActivity());
        StringRequest sr = new StringRequest(Request.Method.POST, Constants.DIET_PRO_REMOVE_CART_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Rakshith", "response from remove prod " + response);
                        try {
                            JSONObject jsonObject   = new JSONObject(response);

                            String      error        = jsonObject.getString("error");
                            String      message      = jsonObject.getString("message");
                            if(!TextUtils.isEmpty(error) && error.equalsIgnoreCase("Invalid cart id")) {
                                Toast.makeText(fragment.getActivity() , error , Toast.LENGTH_SHORT).show();
                                MainActivity.hideLoader();
                            }
                            else {
                                Toast.makeText(fragment.getActivity() , message , Toast.LENGTH_SHORT).show();
                                cartModelList.remove(pos);
//                                notifyDataSetChanged();
                                MainActivity.replaceFragment(new DietProMyCartFragment() , null , null);
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
                    Toast.makeText(fragment.getActivity().getApplicationContext(),errorMessage , Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(fragment.getActivity().getApplicationContext(), fragment.getResources().getString(R.string.exception) , Toast.LENGTH_LONG).show();
                }
                MainActivity.hideLoader();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Constants.DIET_PRO_REMOVE_CART_ID, cartModelList.get(pos).getCartId());

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Constants.DIET_PRO_AUTHORIZATION, accessToken);
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(sr);
    }
}

class MyCartHolder extends RecyclerView.ViewHolder
{
    LinearLayout    llMainContainer;
    ImageView       ivProdImage;
    ImageView       ivRemoveIcon;
    TextView        tvProdName;
    TextView        tvProdQty;
    TextView        tvProdPrice;

    public MyCartHolder(View itemView) {
        super(itemView);

        llMainContainer = (LinearLayout) itemView.findViewById(R.id.myCartRowllMainContainer);
        ivProdImage     = (ImageView) itemView.findViewById(R.id.myCartRowivProdImage);
        ivRemoveIcon    = (ImageView) itemView.findViewById(R.id.myCartRowivRemovePic);
        tvProdName      = (TextView) itemView.findViewById(R.id.myCartRowtvProdName);
        tvProdQty       = (TextView) itemView.findViewById(R.id.myCartRowtvProdQty);
        tvProdPrice     = (TextView) itemView.findViewById(R.id.myCartRowtvProdPrice);
    }
}
