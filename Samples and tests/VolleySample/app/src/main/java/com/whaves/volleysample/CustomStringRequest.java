package com.whaves.volleysample;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Request.Priority;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

public class CustomStringRequest extends Request<String> {
    private Response.Listener<String> response;
    private Map<String, String> params;


    public CustomStringRequest(
            int method, String url,
            Map<String, String> params,
            Response.Listener<String> response,
            Response.ErrorListener listener) {

        super(method, url, listener);

        this.params = params;
        this.response = response;
        // TODO Auto-generated constructor stub
    }
    public CustomStringRequest(String url, Map<String, String> params, Response.Listener<String> response, Response.ErrorListener listener) {
        super(Method.GET, url, listener);
        this.params = params;
        this.response = response;
        // TODO Auto-generated constructor stub
    }

    public Map<String, String> getParams() throws AuthFailureError {
        return params;
    }

    public Map<String, String> getHeaders() throws AuthFailureError{
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("apiKey", "Essa e minha API KEY: json array");

        return(header);
    }

    public Priority getPriority(){
        return(Priority.NORMAL);
    }



    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String js = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Log.i("Script","NETWORK: " + js);
            return(Response.success(new String(js), HttpHeaderParser.parseCacheHeaders(response)));


        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void deliverResponse(String response) {
        this.response.onResponse(response);
    }

}
