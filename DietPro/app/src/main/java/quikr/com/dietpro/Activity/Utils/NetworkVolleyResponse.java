package quikr.com.dietpro.Activity.Utils;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by Rakshith
 */
public class NetworkVolleyResponse<T> implements Response.Listener<T> , Response.ErrorListener{

    private NetworkVolleyRequest.Callback<T> mCallBack;

    public NetworkVolleyResponse(NetworkVolleyRequest.Callback<T> callback)
    {
        this.mCallBack = callback;
    }

    @Override
    public void onResponse(T t) {
        if(mCallBack != null)
            mCallBack.onSuccess(t);
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        if(mCallBack != null)
        {
            if(volleyError != null && volleyError.networkResponse != null && volleyError.networkResponse.data != null)
            {
                mCallBack.onError(volleyError.networkResponse.statusCode , String.valueOf(volleyError.networkResponse.data));
            }
            else
            {
                mCallBack.onError(0 , null);
            }
        }
    }
}
