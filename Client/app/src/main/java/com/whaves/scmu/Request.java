package com.whaves.scmu;

import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Request {

    private CookieStore cookieStore;
    private String mainUrl = "http://whaves.com/scmu2/";

    public Request() {};

    public Request(BasicCookieStore cookieStore) {
        this.cookieStore = cookieStore;
    }

    public Request(CookieStoreImpl data) {
        this.cookieStore = (CookieStore) Utils.createApacheCookieStore((List<CookiesImpl>) data.getData());
    }


    public String getStateJSON() {
        try {
            HttpClient client = getHttpClient();

            /********************* GET REQUEST COM COOKIES *****************************/
            HttpContext localContext = new BasicHttpContext();
            localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
            HttpPost request_with_cookies = new HttpPost(mainUrl+"getState");
            HttpResponse response_with_cookies = client.execute(request_with_cookies, localContext);

            String responseAsString = EntityUtils.toString(response_with_cookies.getEntity());

            Log.w("getStateJSON()", responseAsString);

            return responseAsString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getStateJSON(CookieStore cookie) {
        try {
            HttpClient client = getHttpClient();

            /********************* GET REQUEST COM COOKIES *****************************/
            HttpContext localContext = new BasicHttpContext();
            localContext.setAttribute(ClientContext.COOKIE_STORE, cookie);
            HttpPost request_with_cookies = new HttpPost(mainUrl+"getState");
            HttpResponse response_with_cookies = client.execute(request_with_cookies, localContext);

            String responseAsString = EntityUtils.toString(response_with_cookies.getEntity());

            Log.w("getStateJSON(cookie)", responseAsString);

            return responseAsString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getStateJSON(String username, String password) {
        try {
            HttpClient client = getHttpClient();
            //HttpPost request = new HttpPost("http://whaves.com/scmu/login?username=" + username
              //      + "&password=" + password);
            HttpPost request = new HttpPost(mainUrl+"login");

            //Parâmetros
            List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
            urlParameters.add(new BasicNameValuePair("username", username));
            urlParameters.add(new BasicNameValuePair("password", password));
            request.setEntity(new UrlEncodedFormEntity(urlParameters, HTTP.UTF_8));

            HttpResponse response = client.execute(request);

            cookieStore = ((DefaultHttpClient) client).getCookieStore();

            List<Cookie> listCookies = cookieStore.getCookies();
            int i = 0;
            for (Cookie l : listCookies) {
                Log.e("COOKIE " + (i + 1), l.getName() + " { " + l.getValue() + " }\n");
                l.isExpired(new Date());
                i++;
            }
            Log.w("COOKIE", cookieStore.toString());

            /********************* GET REQUEST COM COOKIES *****************************/
            HttpContext localContext = new BasicHttpContext();
            localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
            HttpPost request_with_cookies = new HttpPost(mainUrl+"getState");
            HttpResponse response_with_cookies = client.execute(request_with_cookies, localContext);

            String responseAsString = EntityUtils.toString(response_with_cookies.getEntity());

            Log.w("getStateJSON(cookie)", responseAsString);

            return responseAsString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setStateJSON(String json) {
        try {
            HttpClient client = getHttpClient();
            /********************* POST COM COOKIES *****************************/
            HttpContext localContext = new BasicHttpContext();
            localContext.setAttribute(ClientContext.COOKIE_STORE, this.cookieStore);
            HttpPost request_with_cookies = new HttpPost(mainUrl+"setState");

            //Com parâmetros...
            List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
            urlParameters.add(new BasicNameValuePair("json", json));
            request_with_cookies.setEntity(new UrlEncodedFormEntity(urlParameters, HTTP.UTF_8));
            HttpResponse response_with_cookies = client.execute(request_with_cookies, localContext);

            String responseAsString = EntityUtils.toString(response_with_cookies.getEntity());
            Log.w("setStateJSON(c,json)", responseAsString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setStateJSON(CookieStore cookie, String json) {
        try {
            HttpClient client = getHttpClient();
            /********************* POST COM COOKIES *****************************/
            HttpContext localContext = new BasicHttpContext();
            localContext.setAttribute(ClientContext.COOKIE_STORE, cookie);
            HttpPost request_with_cookies = new HttpPost(mainUrl+"setState");

            //Com parâmetros...
            List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
            urlParameters.add(new BasicNameValuePair("json", json));
            request_with_cookies.setEntity(new UrlEncodedFormEntity(urlParameters, HTTP.UTF_8));
            HttpResponse response_with_cookies = client.execute(request_with_cookies, localContext);

            String responseAsString = EntityUtils.toString(response_with_cookies.getEntity());
            Log.w("setStateJSON(c,json)", responseAsString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setStateJSON(String username, String password, String json) {
        try {

            HttpClient client = getHttpClient();
            //HttpPost request = new HttpPost("http://whaves.com/scmu/login?username=" + username
            //      + "&password=" + password);
            HttpPost request = new HttpPost("http://whaves.com/scmu/login");

            //Parâmetros
            List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
            urlParameters.add(new BasicNameValuePair("username", username));
            urlParameters.add(new BasicNameValuePair("password", password));
            request.setEntity(new UrlEncodedFormEntity(urlParameters, HTTP.UTF_8));

            HttpResponse response = client.execute(request);

            cookieStore = ((DefaultHttpClient) client).getCookieStore();

            List<Cookie> listCookies = cookieStore.getCookies();
            int i = 0;
            for (Cookie l : listCookies) {
                Log.e("COOKIE " + (i + 1), l.getName() + " { " + l.getValue() + " }\n");
                l.isExpired(new Date());
                i++;
            }
            Log.w("COOKIE", cookieStore.toString());

            /********************* POST COM COOKIES *****************************/
            HttpContext localContext = new BasicHttpContext();
            localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
            HttpPost request_with_cookies = new HttpPost("http://whaves.com/scmu/setState");

            //Com parâmetros...
            urlParameters = null;
            urlParameters = new ArrayList<NameValuePair>();
            urlParameters.add(new BasicNameValuePair("json", json));
            request_with_cookies.setEntity(new UrlEncodedFormEntity(urlParameters, HTTP.UTF_8));
            HttpResponse response_with_cookies = client.execute(request_with_cookies, localContext);

            String responseAsString = EntityUtils.toString(response_with_cookies.getEntity());
            Log.w("setStateJSON(c,json)", responseAsString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public CookieStore authPost(String username, String password) {
        try {
            HttpClient client = getHttpClient();
            HttpPost request = new HttpPost(mainUrl+"login?username=" + username
                    + "&password=" + password);

            //*************************************************
            //Com parâmetros...
            /*
            HttpPost request = new HttpPost("http://whaves.com/scmu/login");
            List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
            urlParameters.add(new BasicNameValuePair("username", username));
            urlParameters.add(new BasicNameValuePair("password", password));
            request.setEntity(new UrlEncodedFormEntity(urlParameters, HTTP.UTF_8));
            //*/
            //*************************************************

            HttpResponse response = client.execute(request);
            
            cookieStore = ((DefaultHttpClient) client).getCookieStore();

            /******* PRINT COOKIES ************/
            List<Cookie> listCookies = cookieStore.getCookies();
            int i = 0;
            /*for (Cookie l : listCookies) {
                Log.e("COOKIE "+(i+1),l.getName() + " { " + l.getValue() + " }\n");
                l.isExpired(new Date());

/*                l.getComment(); //String comment;
                l.getCommentURL();
                l.getDomain();
                l.getExpiryDate();
                l.getPath();
                l.getPorts();//int
                l.getVersion();//int
                l.isPersistent();//boolean
                l.isSecure();//boolean
                i++;
            }*/
            for (Cookie l : listCookies) {
                Log.e("COOKIE " + (i + 1), l.getName() + " { " + l.getValue() + " }\n");
                l.isExpired(new Date());

/*                l.getComment(); //String comment;
                l.getCommentURL();
                l.getDomain();
                l.getExpiryDate();
                l.getPath();
                l.getPorts();//int
                l.getVersion();//int
                l.isPersistent();//boolean
                l.isSecure();//boolean*/
                i++;
            }


            /**********************************/
            //Log.w("COOKIE", cookieStore.toString());
            //String responseAsString = EntityUtils.toString(response.getEntity());
            //Log.e("authPost(user, pass)", responseAsString);

            return cookieStore;
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return null;
    }

    private HttpClient getHttpClient() {
        HttpClient mHttpClient = new DefaultHttpClient();
        final HttpParams params = mHttpClient.getParams();
        HttpConnectionParams.setConnectionTimeout(params, 5000);
        HttpConnectionParams.setSoTimeout(params, 5000);
        ConnManagerParams.setTimeout(params, 5000);

        return mHttpClient;
    }

    public CookieStore getCookieStore(){
        return cookieStore;
    }

    public void setScheduleJSON(CookieStore cookie, String json) {
        try {
            HttpClient client = getHttpClient();
            /********************* POST COM COOKIES *****************************/
            HttpContext localContext = new BasicHttpContext();
            localContext.setAttribute(ClientContext.COOKIE_STORE, cookie);
            HttpPost request_with_cookies = new HttpPost(mainUrl+"setSchedule");

            //Com parâmetros...
            List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
            urlParameters.add(new BasicNameValuePair("json", json));
            request_with_cookies.setEntity(new UrlEncodedFormEntity(urlParameters, HTTP.UTF_8));
            HttpResponse response_with_cookies = client.execute(request_with_cookies, localContext);

            String responseAsString = EntityUtils.toString(response_with_cookies.getEntity());
            Log.w("setScheduleJSON(c,json)", responseAsString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

