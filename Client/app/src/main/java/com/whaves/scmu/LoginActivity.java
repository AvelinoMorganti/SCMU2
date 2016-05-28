package com.whaves.scmu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpCookie;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.net.Uri.parse;


public class LoginActivity extends Activity implements View.OnClickListener {

    private Button buttonSettings;
    private TextView textErrorMsg;
    private TextView textUsername;
    private TextView textPassword;
    private EditText editLogin;
    private EditText editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.login);

        buttonSettings = (Button) findViewById(R.id.buttonLogin);
        buttonSettings.setOnClickListener(this);

        editLogin = (EditText) findViewById(R.id.editLogin);
        editPassword = (EditText) findViewById(R.id.editPassword);
        textErrorMsg = (TextView) findViewById(R.id.textErrorMsg);
        textUsername = (TextView) findViewById(R.id.textUsername);
        textPassword = (TextView) findViewById(R.id.textPassword);

        Log.e("LoginActivity", "Login foi criado");

        editLogin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    editLogin.setHint("");
                else
                    editLogin.setHint("example@whaves.com");
            }
        });
        editPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    editPassword.setHint("");
                else
                    editPassword.setHint("••••••••••");
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == buttonSettings) {
            String username = editLogin.getText().toString().trim();
            String password = editPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(getApplicationContext(),
                        "Username or password is empty", Toast.LENGTH_SHORT).show();

            } else {

                try {
                    Request w = new Request();
                    CookieStore cookieStore = w.authPost(username, password);

                    if (cookieStore == null) {
                        Log.wtf("Cookie null?", "NULL");
                        Toast.makeText(getApplicationContext(),
                                "Username or password is incorrect", Toast.LENGTH_SHORT).show();
                    } else {
                        textErrorMsg.setText("");
                        textErrorMsg.setTextColor(Color.parseColor("#FFFFFFFF"));

                        Intent itPainel = new Intent(this, PainelActivity.class);
                        CookieStoreImpl ck = Utils.createCookieStoreImpl(cookieStore);

                        itPainel.putExtra("COOKIESTORE", ck);

               /*CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.setAcceptCookie(true);
                cookieManager.getCookie()*/

                        startActivity(itPainel);
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            "Network error. Please, try again.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //Quando o botao: "voltar", for pressionado, volta a Painel.
    @Override
    public void onBackPressed() {
    }

    public void showMessage(String msg, String button) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
        dialog.setMessage(msg);
        dialog.setNeutralButton(button, null);
        dialog.show();
    }
}

