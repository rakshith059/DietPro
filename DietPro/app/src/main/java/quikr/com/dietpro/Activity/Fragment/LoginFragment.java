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
public class LoginFragment extends Fragment implements View.OnClickListener{

    TextInputLayout         tilName;
    TextInputLayout         tilPassword;
    EditText                etName;
    EditText                etPassword;

    String                  mUserName;
    String                  mPassword;

    TextView                tvLogin;
    TextView                tvSignUp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_screen , container , false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tilName         = (TextInputLayout) getView().findViewById(R.id.fragmentLoginScreentilName);
        tilPassword     = (TextInputLayout) getView().findViewById(R.id.fragmentLoginScreentilPassword);
        etName          = (EditText) getView().findViewById(R.id.fragmentLoginScreenetName);
        etPassword      = (EditText) getView().findViewById(R.id.fragmentLoginScreenetPassword);
        tvLogin         = (TextView) getView().findViewById(R.id.fragmentLoginScreentvSignIn);
        tvSignUp        = (TextView) getView().findViewById(R.id.fragmentLoginScreentvSignUp);

        tvLogin.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
    }

    private void validation() {
        mUserName       = /*"test_user@appnings.com";*/etName.getText().toString();
        mPassword       = /*"admin";*/etPassword.getText().toString();

        if(!TextUtils.isEmpty(mUserName))
        {
            tilName.setErrorEnabled(false);
        }
        else
        {
            tilName.setError(getResources().getString(R.string.name_error));
        }
        if(!TextUtils.isEmpty(mPassword))
        {
            tilPassword.setErrorEnabled(false);
        }
        else
        {
            tilPassword.setError(getResources().getString(R.string.password_error));
        }

        if(!TextUtils.isEmpty(mUserName) && !TextUtils.isEmpty(mPassword))
        {
//            loginApi();
            login();
            ((MainActivity)getActivity()).hideKeyBoard();
        }
    }

    private void login() {
        MainActivity.showLoader(getActivity());
        StringRequest sr = new StringRequest(Request.Method.POST,Constants.DIET_PRO_LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Rakshith" , "response from login " + response);
                        try {
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
//                                Intent intent = new Intent(getActivity() , MainActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
//                                startActivity(intent);
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
                params.put(Constants.DIET_PRO_USER_EMAIL, mUserName);
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

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.fragmentLoginScreentvSignIn:
                validation();
                break;
            case R.id.fragmentLoginScreentvSignUp:
                MainActivity.replaceFragment(new RegisterFragment() , "registerFragmentScreen" , null);
                break;
            default:
                break;
        }
    }
}
