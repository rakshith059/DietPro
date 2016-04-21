package quikr.com.dietpro.Activity.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
public class ContactUsFragment extends Fragment implements View.OnClickListener{

    TextInputLayout         tilName;
    TextInputLayout         tilEmail;
    TextInputLayout         tilPhoneNo;
    TextInputLayout         tilDescription;

    EditText                etName;
    EditText                etEmail;
    EditText                etPhoneNo;
    EditText                etDescription;

    TextView                tvSend;

    String                  mName;
    String                  mEmail;
    String                  mPhoneNo;
    String                  mDescription;

    String                  mUserID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_us , container , false);
        mUserID = Constants.getSharedPrefrence(getActivity() , Constants.DIET_PRO_USER_ID);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tilName             = (TextInputLayout) getView().findViewById(R.id.fragmentContactUstilName);
        tilEmail            = (TextInputLayout) getView().findViewById(R.id.fragmentContactUstilEmail);
        tilPhoneNo          = (TextInputLayout) getView().findViewById(R.id.fragmentContactUstilPhoneNo);
        tilDescription      = (TextInputLayout) getView().findViewById(R.id.fragmentContactUstilDescription);

        etName              = (EditText) getView().findViewById(R.id.fragmentContactUsetName);
        etEmail             = (EditText) getView().findViewById(R.id.fragmentContactUsetEmail);
        etPhoneNo           = (EditText) getView().findViewById(R.id.fragmentContactUsetPhoneNo);
        etDescription       = (EditText) getView().findViewById(R.id.fragmentContactUsetDescription);

        tvSend              = (TextView) getView().findViewById(R.id.fragmentContactUstvSend);
        tvSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.fragmentContactUstvSend:
                ContactUsValidation();
                break;
            default:
                break;
        }
    }

    private void ContactUsValidation() {
        mName               = etName.getText().toString();
        mEmail              = etEmail.getText().toString();
        mPhoneNo            = etPhoneNo.getText().toString();
        mDescription        = etDescription.getText().toString();

        if(TextUtils.isEmpty(mName))
        {
            tilName.setError(getResources().getString(R.string.name_error));
        }
        else
        {
            tilName.setErrorEnabled(false);
        }
        if(TextUtils.isEmpty(mEmail))
        {
            tilEmail.setError(getResources().getString(R.string.email_error));
        }
        else
        {
            tilEmail.setErrorEnabled(false);
        }
        if(TextUtils.isEmpty(mPhoneNo))
        {
            tilPhoneNo.setError(getResources().getString(R.string.phone_no_error));
        }
        else
        {
            tilPhoneNo.setErrorEnabled(false);
        }
        if(TextUtils.isEmpty(mDescription))
        {
            tilDescription.setError(getResources().getString(R.string.description_error));
        }
        else
        {
            tilDescription.setErrorEnabled(false);
        }

        if(!TextUtils.isEmpty(mName) && !TextUtils.isEmpty(mEmail) && !TextUtils.isEmpty(mPhoneNo) && !TextUtils.isEmpty(mDescription) && !TextUtils.isEmpty(mUserID))
        {
            ContactUsApi();
        }
    }

    private void ContactUsApi() {
        try {
            mName    = etName.getText().toString();

            MainActivity.showLoader(getActivity());
            StringRequest sr = new StringRequest(Request.Method.POST, Constants.DIET_PRO_CONTACT_US,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("Rakshith", "response from login " + response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                String error = jsonObject.getString("error");
                                if (!TextUtils.isEmpty(error)) {
                                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                    MainActivity.hideLoader();
                                } else {
                                    String message = jsonObject.getString("message");
                                    Toast.makeText(getActivity() , message , Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getActivity() , MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
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
                        JSONObject jsonObject = new JSONObject(errorResponse);
                        String errorMessage = jsonObject.getString("error");
                        Log.d("Rakshith", "error status code " + error.networkResponse.statusCode + " error message " + errorMessage);
                        Toast.makeText(getActivity().getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.exception), Toast.LENGTH_LONG).show();
                    }
                    MainActivity.hideLoader();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(Constants.DIET_PRO_USER_NAME, mName);
                    params.put(Constants.DIET_PRO_USER_EMAIL, mEmail);
                    params.put(Constants.DIET_PRO_USER_PHONE, mPhoneNo);
                    params.put(Constants.DIET_PRO_USER_MESSAGE, mDescription);
                    params.put(Constants.DIET_PRO_USER_ID, mUserID);

                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(Constants.DIET_PRO_AUTHORIZATION , Constants.getSharedPrefrence(getActivity() , Constants.DIET_PRO_ACCESS_TOKEN_KEY));
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(sr);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
