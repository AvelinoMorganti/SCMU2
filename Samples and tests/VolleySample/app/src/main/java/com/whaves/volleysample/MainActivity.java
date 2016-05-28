package com.whaves.volleysample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    private RequestQueue rq;
    private Map<String, String> params;
    private EditText username;
    private EditText password;
    private String url;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.etEmail);
        password = (EditText) findViewById(R.id.etPassword);

        rq = Volley.newRequestQueue(MainActivity.this);
    }


    // CALLS VOLLEY
    public void callByJsonObjectRequest(View view) {
        Log.i("Script", "ENTREI: callByJsonObjectRequest() em MainActivity");
        params = new HashMap<String, String>();
        params.put("username", "avelino");
        params.put("password", "123");
        params.put("Login", "Login");
        params.put("hidden", "");

        CustomJsonObjectRequest request = new CustomJsonObjectRequest(Method.POST,
                "http://httpbin.org/post",
                params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Script", "SUCCESS: " + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        request.setTag("tag");
        rq.add(request);
    }


    public void callByJsonArrayRequest(View view) {
        params = new HashMap<String, String>();
        params.put("username", "avelino");
        params.put("password", "123");
        params.put("Login", "Login");
        params.put("hidden", "");

        CustomJsonArrayRequest request = new CustomJsonArrayRequest(Method.POST,
                "http://httpbin.org/post",
                params,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("Script", "SUCCESS: " + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        request.setTag("tag");
        rq.add(request);
    }


    public void callByStringRequest(View view) {
        //Sem envio de parâmetros
        StringRequest request = new StringRequest(Method.GET,
                "http://httpbin.org/get?site=code&network=tutsplus",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Script", "SUCCESS: " + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                params = new HashMap<String, String>();
                params.put("username", "avelino");
                params.put("password", "123");
                params.put("Login", "Login");
                params.put("hidden", "");

                return (params);
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<String, String>();
                header.put("apiKey", "Essa e minha API KEY: sdvkjbsdjvkbskdv");

                return (header);
            }

            @Override
            public Priority getPriority() {
                return (Priority.IMMEDIATE);
            }
        };

        request.setTag("tag");
        rq.add(request);


        /*String url = "http://httpbin.org/get?site=code&network=tutsplus";

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            response = response.getJSONObject("args");
                            String site = response.getString("site"),
                                    network = response.getString("network");
                            System.out.println("Site: "+site+"\nNetwork: "+network);
                            Log.i("Script", "JSON: " + response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        Volley.newRequestQueue(this).add(jsonRequest);*/


        params = new HashMap<String, String>();
        params.put("username", "avelino");
        params.put("password", "123");
        params.put("Login", "Login");
        params.put("hidden", "asd");

        CustomStringRequest requestP = new CustomStringRequest(Method.GET,
                "http://www.whaves.com/scmu/login",
                //"http://httpbin.org/post",
                params,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Script", "SUCCESS_LOGIN_WHAVES: " + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Script", "ERROR_LOGIN_WHAVES: " + error.getMessage());

                        Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            public Priority getPriority() {
                return (Priority.IMMEDIATE);
            }
        };

        requestP.setTag("tags");
        rq.add(requestP);


    }


    @Override
    public void onStop() {
        super.onStop();
        rq.cancelAll("tag");
    }
}
