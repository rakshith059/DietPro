package quikr.com.dietpro.Activity.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import quikr.com.dietpro.Activity.MainActivity;
import quikr.com.dietpro.Activity.Utils.AppController;
import quikr.com.dietpro.Activity.Utils.Constants;
import quikr.com.dietpro.R;

/**
 * Created by Rakshith
 */
public class CheckoutFragment extends Fragment implements View.OnClickListener , RadioGroup.OnCheckedChangeListener{
    String              totalPrice;
    TextView            tvTotalPrice;
    TextView            tvPlaceOrder;

    RadioGroup          rgFlavour;
    RadioButton         rbFlavourPlain;
    RadioButton         rbFlavourLime;
    RadioButton         rbFlavourGinger;
    RadioButton         rbFlavourGarlic;

    String              userId;
    ArrayList<String>   cartIdList;
    String              addressId;
    String              addressName;
    String              addressPhone;
    String              addressDetail;

    String              accessToken;
    String              orderFlavour = Constants.DIET_PRO_PLAIN;

    RadioGroup          rgPaymentMode;
    RadioButton         rbCod;
    RadioButton         rbOnlinePayment;
    String              paymentMode;

    Bundle   bundle = new Bundle();

    LinearLayout        llAddressLayout;
    TextView            tvName;
    TextView            tvPhoneNo;
    TextView            tvAddress;
    TextView            tvChangeAddress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkout, container, false);

        tvPlaceOrder    = (TextView) view.findViewById(R.id.fragmentCheckouttvPlaceOrder);
        tvTotalPrice    = (TextView) view.findViewById(R.id.fragmentCheckouttvTotalPrice);

        accessToken     = Constants.getSharedPrefrence(getActivity(), Constants.DIET_PRO_ACCESS_TOKEN_KEY);
        userId          = Constants.getSharedPrefrence(getActivity() , Constants.DIET_PRO_USER_ID);
        addressId       = Constants.getSharedPrefrence(getActivity() , Constants.DIET_PRO_ADDRESS_ID);
        addressName       = Constants.getSharedPrefrence(getActivity() , Constants.DIET_PRO_ADDRESS_TITLE);
        addressPhone       = Constants.getSharedPrefrence(getActivity() , Constants.DIET_PRO_ADDRESS_PHONE);
        addressDetail       = Constants.getSharedPrefrence(getActivity() , Constants.DIET_PRO_ADDRESS_DETAIL);

        Bundle bundle = getArguments();
        if(bundle != null) {
            cartIdList      = bundle.getStringArrayList("cartIdList");
            totalPrice = bundle.getString("totalPrice");
            tvTotalPrice.setText(getResources().getString(R.string.ruppe_symbol) + " " + totalPrice);
        }

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rgFlavour           = (RadioGroup) getView().findViewById(R.id.fragmentCheckoutrgFlavour);
        rbFlavourPlain      = (RadioButton) getView().findViewById(R.id.fragmentCheckoutrbFlavourPlain);
        rbFlavourLime       = (RadioButton) getView().findViewById(R.id.fragmentCheckoutrbFlavourLime);
        rbFlavourGinger     = (RadioButton) getView().findViewById(R.id.fragmentCheckoutrbFlavourGinger);
        rbFlavourGarlic     = (RadioButton) getView().findViewById(R.id.fragmentCheckoutrbFlavourGarlic);

        rgPaymentMode       = (RadioGroup) getView().findViewById(R.id.fragmentCheckoutrgPaymentMode);
        rbCod               = (RadioButton) getView().findViewById(R.id.fragmentCheckoutrbCod);
        rbOnlinePayment     = (RadioButton) getView().findViewById(R.id.fragmentCheckoutrbonlinePayment);

        llAddressLayout = (LinearLayout) getView().findViewById(R.id.myAddressRowllAddressContainer);
        tvName      = (TextView) getView().findViewById(R.id.myAddressRowtvName);
        tvPhoneNo   = (TextView) getView().findViewById(R.id.myAddressRowtvPhone);
        tvAddress   = (TextView) getView().findViewById(R.id.myAddressRowtvAddress);

        tvChangeAddress = (TextView) getView().findViewById(R.id.fragmentCheckouttvChangeAddress);

        if(!TextUtils.isEmpty(addressName)) {
            tvName.setText(addressName);
        }
        if(!TextUtils.isEmpty(addressPhone)) {
            tvPhoneNo.setText(addressPhone);
        }
        if(!TextUtils.isEmpty(addressDetail)) {
            tvAddress.setText(addressDetail);
        }

        rbFlavourPlain.setChecked(true);
        rgFlavour.setOnCheckedChangeListener(this);
        rbCod.setChecked(true);
        rgPaymentMode.setOnCheckedChangeListener(this);
        tvPlaceOrder.setOnClickListener(this);
        tvChangeAddress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.fragmentCheckouttvPlaceOrder:
                if(!TextUtils.isEmpty(addressId)) {
                    PlaceOrderApi();
                }
                else
                {
                    Bundle bundle = new Bundle();
                    bundle.putString("from" , Constants.DIET_PRO_FROM_CHECKOUT_FRAGMENT);
                    MainActivity.replaceFragment(new RegisterMyAddressFragment() , "checkoutScreen" , bundle);
                    Toast.makeText(getActivity() , getResources().getString(R.string.please_add_address) , Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.fragmentCheckouttvChangeAddress:
                Bundle bundle = new Bundle();
                bundle.putString("from" , Constants.DIET_PRO_FROM_CHECKOUT_FRAGMENT);
                MainActivity.replaceFragment(new MyAddressFragment() , "checkoutScreen" , bundle);
                break;
            default:
                break;
        }
    }

    private void PlaceOrderApi() {
        MainActivity.showLoader(getActivity());
        StringRequest sr = new StringRequest(Request.Method.POST, Constants.DIET_PRO_PLACE_ORDER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Rakshith", "response from place order " + response);
                        try {
                            JSONObject jsonObject   = new JSONObject(response);
                            JSONObject jsonObject1  = jsonObject.getJSONObject("data");

                            String      orderId     = jsonObject1.getString("order_id");
                            if(!TextUtils.isEmpty(orderId))
                            {
                                Toast.makeText(getActivity() , "Your order has placed succesfully.. Your order-id is " + orderId , Toast.LENGTH_SHORT).show();
                            }

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
                try {
                    String errorResponse = String.valueOf(error.networkResponse.data);
                    if(!TextUtils.isEmpty(errorResponse)) {
                        JSONObject jsonObject = new JSONObject(errorResponse);
                        String errorMessage = jsonObject.getString("error");
                        Log.d("Rakshith", "error status code " + error.networkResponse.statusCode + " error message " + errorMessage);
                        Toast.makeText(getActivity().getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                MainActivity.hideLoader();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Constants.DIET_PRO_USER_ID, userId);
                params.put(Constants.DIET_PRO_ADDRESS_ID, addressId);
                params.put(Constants.DIET_PRO_ORDER_PAYMENT_OPTION, Constants.DIET_PRO_COD);
                params.put(Constants.DIET_PRO_ORDER_CART_AMOUNT, totalPrice);
                params.put(Constants.DIET_PRO_ORDER_DELIVERY_AMOUNT, "0");
                params.put(Constants.DIET_PRO_ORDER_NET_AMOUNT, totalPrice);
                params.put(Constants.DIET_PRO_ORDER_FLAVOUR, orderFlavour);
                params.put(Constants.DIET_PRO_ORDER_NOTES, "Notes");

                params.put(Constants.DIET_PRO_CART_ID, "364");
                params.put(Constants.DIET_PRO_CART_ID, "366");
//                if(cartIdList.size() > 0) {
//                    for (int i =0 ; i < cartIdList.size() ; i++) {
//                        params.put(Constants.DIET_PRO_CART_ID, cartIdList.get(0));
//                        params.put(Constants.DIET_PRO_CART_ID, cartIdList.get(1));
//                    }
//                }

                Log.d("Rakshith", "params from place order " +  params);

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

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId)
        {
            case R.id.fragmentCheckoutrbFlavourPlain:
                orderFlavour = Constants.DIET_PRO_PLAIN;
                break;
            case R.id.fragmentCheckoutrbFlavourLime:
                orderFlavour = Constants.DIET_PRO_LIME;
                break;
            case R.id.fragmentCheckoutrbFlavourGinger:
                orderFlavour = Constants.DIET_PRO_GINGER;
                break;
            case R.id.fragmentCheckoutrbFlavourGarlic:
                orderFlavour = Constants.DIET_PRO_GARLIC;
                break;

            case R.id.fragmentCheckoutrbCod:
                paymentMode = Constants.DIET_PRO_COD;
                break;
            default:
                break;
        }
    }
}
