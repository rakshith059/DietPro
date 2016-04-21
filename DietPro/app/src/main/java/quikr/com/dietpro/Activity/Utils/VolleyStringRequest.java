package quikr.com.dietpro.Activity.Utils;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

/**
 * Created by Rakshith
 */
public class VolleyStringRequest extends StringRequest{
    private Map<String, String> headers;
    private String body;
    private String contentType;
    private static final int MAX_RETRY = 2; //Total Attempts is 3.
    private static final int INITIAL_TIMEOUT = 7500; //7.5secs.
    private static final float BACK_OFF_MULTIPLIER = 1.0F;
    protected Priority mPriority= Request.Priority.NORMAL;

    public VolleyStringRequest(int method, String url, String contentType, String body, Map<String, String> headers, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.headers = headers;
        this.body = body;
        this.contentType = contentType;
        setRetryPolicy(new DefaultRetryPolicy(INITIAL_TIMEOUT, MAX_RETRY, BACK_OFF_MULTIPLIER));
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers == null ? super.getHeaders() : headers;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        return body.getBytes();
    }

    public String getBodyContentType() {
        return contentType == null ? super.getBodyContentType() : contentType;
    }

    public void setPriority(Priority priority){
        mPriority = priority;
    }

    @Override
    public Priority getPriority() {
        return mPriority;
    }
}
