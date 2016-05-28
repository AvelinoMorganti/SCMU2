package com.whaves.scmu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.painel);


        Intent itLogin = new Intent(this, LoginActivity.class);
        startActivity(itLogin);
    }

    public void showMessage(String msg, String button) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setMessage(msg);
        dialog.setNeutralButton(button, null);
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        //Intent it = new Intent(this, PainelActivity.class);
        //startActivity(it);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        showMessage("onRestart", "onRestart");
        Intent it = new Intent(this, LoginActivity.class);
        startActivity(it);
    }

    @Override
    public void onPause() {
        super.onPause();
        //showMessage("onPause", "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        //showMessage("onResume", "onResume");
    }

    @Override
    public void onStart() {
        super.onStart();
        //showMessage("onStart", "onStart");
    }

    /*@Override
    public void onStop() {
        super.onStop();
        //showMessage("onStop", "onStop");
    }*/
}