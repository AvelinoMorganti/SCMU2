package com.whaves.scmuiot;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.CookieStore;


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

        setContentView(com.whaves.scmuiot.R.layout.login);

        buttonSettings = (Button) findViewById(com.whaves.scmuiot.R.id.buttonLogin);
        buttonSettings.setOnClickListener(this);

        editLogin = (EditText) findViewById(com.whaves.scmuiot.R.id.editLogin);
        editPassword = (EditText) findViewById(com.whaves.scmuiot.R.id.editPassword);
        textErrorMsg = (TextView) findViewById(com.whaves.scmuiot.R.id.textErrorMsg);
        textUsername = (TextView) findViewById(com.whaves.scmuiot.R.id.textUsername);
        textPassword = (TextView) findViewById(com.whaves.scmuiot.R.id.textPassword);

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

