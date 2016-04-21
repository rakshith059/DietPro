package quikr.com.dietpro.Activity.Utils;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rakshith
 */
public class NetworkVolleyRequest<T> {

    private Request<T> request;

    protected int                               method;
    protected String                            url;
    protected Type                              type;
    protected Map<String , String>              header;
    protected Map<String , ? extends Object>    params;
    protected Callback<T>                       callBack;
    protected String                            contentType;
    private byte[]                              multipartBody;

    public NetworkVolleyRequest(int method , String url , Type type , Map<String , String> header , Map<String, ? extends Object> params, Callback<T> callBack , String contentType)
    {
        this.method         = method;
        this.url            = url;
        this.type           = type;
        this.header         = header;
        this.params         = params;
        this.callBack       = callBack;
        this.contentType    = contentType;
    }

    private String getUrl(int method, String url, Map<String, ?> params) {
        String appendedURL = "";
        switch (method)
        {
            case RequestMethod.GET:
                appendedURL = finalUrl(url , params , false);
                break;
            case RequestMethod.POST:
                appendedURL = finalUrl(url , params , true);
                break;
            default:
                break;
        }
        Log.d("Rakshith" , "appendedUrl " + appendedURL);
        return appendedURL;
    }

    private String finalUrl(String url, Map<String, ?> params, boolean isPost) {
        final StringBuilder basicUrl = new StringBuilder(url);
        boolean isFirst = basicUrl.indexOf("?") < 0;

        if(!isPost)
        {
            for(String key : params.keySet())
            {
                if(!(params.get(key) instanceof String))
                {
                    throw new IllegalArgumentException("Only key value pair is allowed in GET method");
                }
                String value = (String) params.get(key);
                if (params.get(key) != null)
                {
                    try
                    {
                        basicUrl.append((isFirst?"?" : "&")).append(key + "=" + URLEncoder.encode(value , "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    if (isFirst)
                        isFirst = false;
                }
            }

//            basicUrl.append((isFirst?"?":"&") + "appVersion=" + AppController.APP_VERSION );
        }
        Log.d("Rakshith" , "basic url " + basicUrl.toString());
        return basicUrl.toString();
    }

    public interface Callback<T>
    {
        void onSuccess(T response);
        void onError(int errorCode , String errorMessage);
    }

    public interface RequestMethod
    {
        int GET = 0;
        int POST = 1;
        int PUT = 2;
        int DELETE= 3;
    }

    public interface ContentType {
        String JSON = String.format("application/json; charset=%s", new Object[]{"utf-8"});
        String MULTIPART_FORMDATA = "multipart/form-data; boundary=-------------------------acebdf13572468" ;
        String TEXT_PLAIN = "text/plain" ;
        String X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
    }

    public void execute()
    {
        createRequest();
        AppController.getInstance().addToRequestQueue(request);
    }

    protected  boolean requestCreated = false;
    private void createRequest() {
        requestCreated = true;
        String body = null;

        if(method == RequestMethod.POST)
        {
            contentType = contentType == null ? ContentType.JSON :contentType;
            body = getBody(contentType , params);
        }
        url = getUrl(method , url , params);

        if(header == null)
        {
            header = new HashMap<>();
        }

        NetworkVolleyResponse<T> networkResponse = new NetworkVolleyResponse<>(callBack);

        if(type instanceof Class && type == String.class)
        {
            request = (Request<T>) new VolleyStringRequest(method , url , contentType , body , header , (Response.Listener<String>)networkResponse , networkResponse);
        }
//        else if(type instanceof Class && type == MultipartRequest.class)
//        {
//            request = (Request<T>) new MultipartRequest(Constants.UPLOAD_PIC_URL, null, contentType , multipartBody, (Response.Listener<String>)networkResponse);
//        }
        request.setTag(this);
//        PriorityAwareRequest.class.cast(request).setPriority(priority);
    }

    Request<T> getRequest(){
        return request;
    }

    protected Request.Priority priority = Request.Priority.NORMAL;
    public interface PriorityAwareRequest {
        Request.Priority getPriority();
        void setPriority(Request.Priority priority);
    }

    private String createMultiPartPostBody(Map<String, ? extends Object> params) {
        StringBuilder sbPost = new StringBuilder();
        if (params != null) {
            for (String key : params.keySet()) {
                if (params.get(key) != null && params.get(key) instanceof String) {
                    sbPost.append("\r\n" + "--" + "-------------------------acebdf13572468" + "\r\n");
                    sbPost.append("Content-Disposition: form-data; name=\"" + key + "\"" + "\r\n\r\n");
                    sbPost.append(params.get(key).toString());
                }
            }
        }
        return sbPost.toString();
    }

    private String getBody(String contentType, Map<String, ? extends Object> params) {
        if(contentType.equalsIgnoreCase(ContentType.JSON))
        {
            if(params.containsKey("body"))
            {
                return String.valueOf(params.get("body"));
            }
            return String.valueOf(params);
        }
        else if (contentType.equals(ContentType.MULTIPART_FORMDATA)) {
            return createPostBody(params);
        }
        else if(contentType.equalsIgnoreCase(ContentType.TEXT_PLAIN))
        {
            if(params.containsKey("body"))
            {
                return String.valueOf(params.get("body"));
            }
            return null;
        }
        else if(contentType.equalsIgnoreCase(ContentType.X_WWW_FORM_URLENCODED))
        {
            return createPostBody(params);
        }
        return null;
    }

    private String createPostBody(Map<String, ? extends Object> params) {
        Map<String,String> param = new HashMap<String, String>();
        param.put("Content-Type","application/x-www-form-urlencoded");
        return param.toString();
    }
}
