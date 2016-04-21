package quikr.com.dietpro.Activity.Fragment;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

import quikr.com.dietpro.Activity.MainActivity;
import quikr.com.dietpro.Activity.Utils.AppController;
import quikr.com.dietpro.Activity.Utils.Constants;
import quikr.com.dietpro.R;

/**
 * Created by Rakshith
 */
public class RegisterMyAddressFragment extends Fragment implements View.OnClickListener,RadioGroup.OnCheckedChangeListener{

    TextInputLayout             tilName;
    TextInputLayout             tilAddress;
    TextInputLayout             tilLandMark;
    TextInputLayout             tilPincode;
    TextInputLayout             tilCity;
    TextInputLayout             tilState;
    TextInputLayout             tilPhoneNo;
    TextInputLayout             tilEmailId;

    EditText                    etName;
    EditText                    etAddress;
    EditText                    etLandMark;
    EditText                    etPincode;
    EditText                    etCity;
    EditText                    etState;
    EditText                    etPhoneNo;
    EditText                    etEmailId;

    RadioGroup                  rgAddressType;
    RadioButton                 rbHomeAddress;
    RadioButton                 rbGymAddress;

    TextView                    tvSaveAddress;

    String                      userId;
    String                      mName;
    String                      mAddress;
    String                      mLandMark;
    String                      mPincode;
    String                      mCity;
    String                      mState;
    String                      mPhoneNo;
    String                      mEmailId;

    String                      mAddressType = Constants.DIET_PRO_ADDRESS_TYPE_HOME;
    String                      accessToken;
    String                      from;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_my_address, container, false);
        accessToken = Constants.getSharedPrefrence(getActivity() , Constants.DIET_PRO_ACCESS_TOKEN_KEY);

        Bundle bundle = getArguments();
        if(bundle != null) {
            from = bundle.getString("from");
        }
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tilName             = (TextInputLayout) getView().findViewById(R.id.fragmentMyAddresstilName);
        tilAddress          = (TextInputLayout) getView().findViewById(R.id.fragmentMyAddresstilAddress);
        tilLandMark         = (TextInputLayout) getView().findViewById(R.id.fragmentMyAddresstilLandMark);
        tilPincode          = (TextInputLayout) getView().findViewById(R.id.fragmentMyAddresstilPincode);
        tilCity             = (TextInputLayout) getView().findViewById(R.id.fragmentMyAddresstilCity);
        tilState            = (TextInputLayout) getView().findViewById(R.id.fragmentMyAddresstilState);
        tilPhoneNo          = (TextInputLayout) getView().findViewById(R.id.fragmentMyAddresstilPhoneNo);
        tilEmailId          = (TextInputLayout) getView().findViewById(R.id.fragmentMyAddresstilEmail);

        etName             = (EditText) getView().findViewById(R.id.fragmentMyAddressetName);
        etAddress          = (EditText) getView().findViewById(R.id.fragmentMyAddressetAddress);
        etLandMark         = (EditText) getView().findViewById(R.id.fragmentMyAddressetLandMark);
        etPincode          = (EditText) getView().findViewById(R.id.fragmentMyAddressetPincode);
        etCity             = (EditText) getView().findViewById(R.id.fragmentMyAddressetCity);
        etState            = (EditText) getView().findViewById(R.id.fragmentMyAddressetState);
        etPhoneNo          = (EditText) getView().findViewById(R.id.fragmentMyAddressetPhoneNo);
        etEmailId          = (EditText) getView().findViewById(R.id.fragmentMyAddressetEmail);

        rgAddressType      = (RadioGroup) getView().findViewById(R.id.fragmentMyAddressrgAddressType);
        rbHomeAddress      = (RadioButton) getView().findViewById(R.id.fragmentMyAddressrbHomeAddress);
        rbGymAddress       = (RadioButton) getView().findViewById(R.id.fragmentMyAddressrbGymAddress);

        tvSaveAddress       = (TextView) getView().findViewById(R.id.fragmentMyAddresstvSaveAddress);
        tvSaveAddress.setOnClickListener(this);

        rgAddressType.setOnCheckedChangeListener(this);

        userId = Constants.getSharedPrefrence(getActivity(), Constants.DIET_PRO_USER_ID);
    }

    private void addAddressValidation() {
        mName       = etName.getText().toString();
        mAddress    = etAddress.getText().toString();
        mLandMark   = etLandMark.getText().toString();
        mPincode    = etPincode.getText().toString();
        mCity       = etCity.getText().toString();
        mState      = etState.getText().toString();
        mPhoneNo    = etPhoneNo.getText().toString();
        mEmailId    = etEmailId.getText().toString();

        if(!TextUtils.isEmpty(mName))
        {
            tilName.setErrorEnabled(false);
        }
        else
        {
            tilName.setError(getResources().getString(R.string.register_name_error));
        }
        if(!TextUtils.isEmpty(mAddress))
        {
            tilAddress.setErrorEnabled(false);
        }
        else
        {
            tilAddress.setError(getResources().getString(R.string.address_error));
        }
        if(!TextUtils.isEmpty(mLandMark))
        {
            tilLandMark.setErrorEnabled(false);
        }
        else
        {
            tilLandMark.setError(getResources().getString(R.string.landmark_error));
        }
        if(!TextUtils.isEmpty(mPincode))
        {
            tilPincode.setErrorEnabled(false);
        }
        else
        {
            tilPincode.setError(getResources().getString(R.string.pincode_error));
        }
        if(!TextUtils.isEmpty(mCity))
        {
            tilCity.setErrorEnabled(false);
        }
        else
        {
            tilCity.setError(getResources().getString(R.string.city_error));
        }
        if(!TextUtils.isEmpty(mState))
        {
            tilState.setErrorEnabled(false);
        }
        else
        {
            tilState.setError(getResources().getString(R.string.state_error));
        }
        if(!TextUtils.isEmpty(mPhoneNo))
        {
            tilPhoneNo.setErrorEnabled(false);
        }
        else
        {
            tilPhoneNo.setError(getResources().getString(R.string.phone_no_error));
        }
        if(!TextUtils.isEmpty(mEmailId))
        {
            tilEmailId.setErrorEnabled(false);
        }
        else
        {
            tilEmailId.setError(getResources().getString(R.string.email_error));
        }
        if(!TextUtils.isEmpty(mName) && !TextUtils.isEmpty(mAddress) && !TextUtils.isEmpty(mLandMark) && !TextUtils.isEmpty(mPincode) && !TextUtils.isEmpty(mCity) && !TextUtils.isEmpty(mState) && !TextUtils.isEmpty(mPhoneNo) && !TextUtils.isEmpty(mEmailId))
        {
            AddAddressApi();
        }
    }

    private void AddAddressApi() {
        MainActivity.showLoader(getActivity());
        StringRequest sr = new StringRequest(Request.Method.POST, Constants.DIET_PRO_BASE_URL+Constants.DIET_PRO_ADD_TO_ADDRESS ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Rakshith", "response from add address " + response);
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
                                if (!TextUtils.isEmpty(from) && from.equalsIgnoreCase(Constants.DIET_PRO_FROM_CHECKOUT_FRAGMENT)) {
                                    ((MainActivity) getActivity()).replaceFragment(new CheckoutFragment(), null, null);
                                }
//                                else if (!TextUtils.isEmpty(from) && from.equalsIgnoreCase(Constants.DIET_PRO_FROM_MY_ADDRESS_FRAGMENT))
                                else
                                {
                                    ((MainActivity) getActivity()).replaceFragment(new DietMateFragment(), null, null);
                                }
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
                params.put(Constants.DIET_PRO_ADDRESS_TITLE, mName);
                params.put(Constants.DIET_PRO_ADDRESS_DETAIL, mAddress);
                params.put(Constants.DIET_PRO_ADDRESS_LANDMARK, mLandMark);
                params.put(Constants.DIET_PRO_ADDRESS_PINCODE, mPincode);
                params.put(Constants.DIET_PRO_ADDRESS_CITY, mCity);
                params.put(Constants.DIET_PRO_ADDRESS_STATE, mState);
                params.put(Constants.DIET_PRO_ADDRESS_PHONE, mPhoneNo);
                params.put(Constants.DIET_PRO_ADDRESS_EMAIL, mEmailId);
                params.put(Constants.DIET_PRO_ADDRESS_TYPE, mAddressType);

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

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.fragmentMyAddresstvSaveAddress:
                addAddressValidation();
                break;
            default:
                break;
        }
    }
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId)
        {
            case R.id.fragmentMyAddressrbHomeAddress:
                mAddressType = Constants.DIET_PRO_ADDRESS_TYPE_HOME;
                break;
            case R.id.fragmentMyAddressrbGymAddress:
                mAddressType = Constants.DIET_PRO_ADDRESS_TYPE_GYM;
                break;
            default:
                break;
        }
    }

}
