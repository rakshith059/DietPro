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
public class RegisterFragment extends Fragment implements View.OnClickListener{
    TextInputLayout             tilName;
    TextInputLayout             tilMobile;
    TextInputLayout             tilEmail;
    TextInputLayout             tilPassword;

    EditText                    etName;
    EditText                    etMobile;
    EditText                    etEmail;
    EditText                    etPassword;

    TextView                    tvSignUp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register , container , false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tilName         = (TextInputLayout) getView().findViewById(R.id.fragmentRegistertilName);
        tilMobile       = (TextInputLayout) getView().findViewById(R.id.fragmentRegistertilMobile);
        tilEmail        = (TextInputLayout) getView().findViewById(R.id.fragmentRegistertilEmail);
        tilPassword     = (TextInputLayout) getView().findViewById(R.id.fragmentRegistertilPassword);

        etName          = (EditText) getView().findViewById(R.id.fragmentRegisteretName);
        etMobile        = (EditText) getView().findViewById(R.id.fragmentRegisteretMobile);
        etEmail         = (EditText) getView().findViewById(R.id.fragmentRegisteretEmail);
        etPassword      = (EditText) getView().findViewById(R.id.fragmentRegisteretPassword);

        tvSignUp        = (TextView) getView().findViewById(R.id.fragmentRegistertvSignUp);

        tvSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.fragmentRegistertvSignUp:
                validationRegister();
                break;
            default:
                break;
        }
    }


    private void validationRegister() {
        String mName        = etName.getText().toString();
        String mMobile      = etMobile.getText().toString();
        String mEmail       = etEmail.getText().toString();
        String mPassword    = etPassword.getText().toString();

        if(!TextUtils.isEmpty(mName))
        {
            tilName.setErrorEnabled(false);
        }
        else
        {
            tilName.setError(getResources().getString(R.string.register_name_error));
        }
        if(!TextUtils.isEmpty(mMobile) && mMobile.length() == 10)
        {
            tilMobile.setErrorEnabled(false);
        }
        else
        {
            tilMobile.setError(getResources().getString(R.string.register_mobile_error));
        }
        if(!TextUtils.isEmpty(mEmail) && mEmail.matches(Constants.emailPattern))
        {
            tilEmail.setErrorEnabled(false);
        }
        else
        {
            tilEmail.setError(getResources().getString(R.string.register_email_error));
        }
        if(!TextUtils.isEmpty(mPassword))
        {
            tilPassword.setErrorEnabled(false);
        }
        else
        {
            tilPassword.setError(getResources().getString(R.string.register_password_error));
        }

        if (!TextUtils.isEmpty(mName) && (!TextUtils.isEmpty(mMobile) && mMobile.length() == 10) && (!TextUtils.isEmpty(mEmail) && mEmail.matches(Constants.emailPattern)) && !TextUtils.isEmpty(mPassword))
        {
            registerApi(mName , mMobile , mPassword , mEmail);
        }
    }

    private void registerApi(final String mName, final String mMobile, final String mPassword, final String mEmail) {
        {
            MainActivity.showLoader(getActivity());
            StringRequest sr = new StringRequest(Request.Method.POST,Constants.DIET_PRO_REGISTER_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("Rakshith", "response from register " + response);
                            try {
                                {
                                    JSONObject jsonObject   = new JSONObject(response);

                                    String      error        = jsonObject.getString("error");
                                    if(!TextUtils.isEmpty(error) && error.equalsIgnoreCase("Invalid username / password")) {
                                        Toast.makeText(getActivity() , error , Toast.LENGTH_SHORT).show();
                                        MainActivity.hideLoader();
                                    }
                                    else {
                                        JSONObject jsonData = jsonObject.getJSONObject("data");

                                        String accessToken = jsonData.getString("access_token");

                                        JSONObject  jsonObject1 = jsonData.getJSONObject("user");
                                        String      userId      = jsonObject1.getString("user_id");

                                        Constants.setSharedPrefrence(getActivity(), Constants.DIET_PRO_ACCESS_TOKEN_KEY, accessToken);
                                        Constants.setSharedPrefrence(getActivity(), Constants.DIET_PRO_USER_ID, userId);

                                        ((MainActivity) getActivity()).replaceFragment(new ViewPagerFragment(), null, null);
//                                        Intent intent = new Intent(getActivity() , MainActivity.class);
//                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
//                                        startActivity(intent);
                                        MainActivity.hideLoader();
                                    }

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
                    params.put(Constants.DIET_PRO_USER_NAME, mName);
                    params.put(Constants.DIET_PRO_USER_EMAIL, mEmail);
                    params.put(Constants.DIET_PRO_USER_MOBILE, mMobile);
                    params.put(Constants.DIET_PRO_USER_PASSWORD, mPassword);

                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(sr);
        }
    }
}
