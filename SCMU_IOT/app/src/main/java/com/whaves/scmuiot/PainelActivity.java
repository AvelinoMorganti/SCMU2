package com.whaves.scmuiot;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.impl.client.BasicCookieStore;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PainelActivity extends Activity implements View.OnClickListener {


    //Components
    private Button buttonSettings;
    private TextView textLum;
    private TextView textGas;
    private Switch switchLamp;
    private Switch switchAlarm;

    //Constants
    int time = 1000 * 5;             //call function every 10 seconds
    int i = 0;
    private Timer myTimer;

    //Request/response
    private Request request;
    private State state;
    CookieStoreImpl data;

    //Dialogs
    AlertDialog alert;
    boolean showAlert;
    boolean showAlertInThisSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.whaves.scmuiot.R.layout.painel);

        textLum = (TextView) findViewById(com.whaves.scmuiot.R.id.textLum);
        textGas = (TextView) findViewById(com.whaves.scmuiot.R.id.textGas);
        switchLamp = (Switch) findViewById(com.whaves.scmuiot.R.id.switchLamp);
        switchAlarm = (Switch) findViewById(com.whaves.scmuiot.R.id.switchAlarm);
        buttonSettings = (Button) findViewById(com.whaves.scmuiot.R.id.buttonSettings);
        buttonSettings.setOnClickListener(this);

        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }

        }, 0, time);

        Intent i = getIntent();
        data = (CookieStoreImpl) i.getSerializableExtra("COOKIESTORE");

        //actualizar();
        showAlert = true;
        showAlertInThisSession = true;


    }

    public void actualizar() {
        BasicCookieStore ck = Utils.createApacheCookieStore((List<CookiesImpl>) data.getData());

        request = new Request(ck);
        String response = request.getStateJSON(ck);

        Gson gson = new Gson();
        State state = gson.fromJson(response, State.class);

        //Atualiza botões
        switchAlarm.setChecked(state.isAlarm());
        switchLamp.setChecked(state.isLamp());

        double lum = state.getLuminosity();
        double gas = state.getHarmfulGases();

        NumberFormat nf = new DecimalFormat("#,###");
        nf.setMaximumFractionDigits(3);
        nf.setMinimumFractionDigits(3);

        //Atualiza sensores
        textLum.setText("Luminosity: " + String.valueOf(nf.format(lum)) + "%"); //lux
        textGas.setText("Air quality: " + String.valueOf(nf.format(gas)) + "% Pollution");//ppm

        if (gas >= 15.0 && gas < 25.00) {
            if (showAlert && showAlertInThisSession) {
                showMessageDialog("Bad air quality. Be careful\n");
            }
        }
        if (gas >= 25.0) {
            if (showAlert && showAlertInThisSession) {
                showMessageDialog("Something is wrong. Very bad air quality\n\nFire?\n");
            }
        }
   }

    @Override
    public void onClick(View v) {
        if (v == buttonSettings) {
            Intent itSettings = new Intent(this, SettingsActivity.class);
            itSettings.putExtra("COOKIESTORE", data);
            startActivity(itSettings);
        }
    }

    public void setLamp(View v) {
        if (((Switch) v).isChecked()) {
            setTypeRequest("lamp", true);
        } else {
            setTypeRequest("lamp", false);
        }
    }

    public void setAlarm(View v) {
        setTypeRequest("alarm", ((Switch) v).isChecked());
    }

    public void setTypeRequest(String type, boolean value) {

        try {
            if (type.equals("lamp")) {

                BasicCookieStore ck = Utils.createApacheCookieStore((List<CookiesImpl>) data.getData());
                request = new Request(ck);

                String response = request.getStateJSON(ck);

                Gson gson = new Gson();
                state = gson.fromJson(response, State.class);
                state.setLamp(value);

                String json = gson.toJson(state);
                request.setStateJSON(ck, json);

                if (value) {
                    showMessage("LAMP ON", "OK");
                } else {
                    showMessage("LAMP OFF", "OK");
                }
            }


            if (type.equals("alarm")) {
                BasicCookieStore ck = Utils.createApacheCookieStore((List<CookiesImpl>) data.getData());
                request = new Request(ck);

                String response = request.getStateJSON(ck);

                Gson gson = new Gson();
                state = gson.fromJson(response, State.class);
                state.setAlarm(value);

                String json = gson.toJson(state);
                request.setStateJSON(ck, json);

                if (value) {
                    showMessage("ALARM ON", "OK");
                } else {
                    showMessage("ALARM OFF", "OK");
                }
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "Network error. Please make sure your Wifi is active and try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private void TimerMethod() {
        //This method is called directly by the timer
        //and runs in the same thread as the timer.
        //We call the method that will work with the UI
        //through the runOnUiThread method.
        this.runOnUiThread(Timer_Tick);

    }


    private Runnable Timer_Tick = new Runnable() {
        //This method runs in the same thread as the UI.
        //Do something to the UI thread here
        public void run() {
            try {
                actualizar();
            } catch (Exception e) {
                //Toast.makeText(getApplicationContext(),
                //      "Network error. Please, try again.", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void showMessageDialog(String msg) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(PainelActivity.this);
        dialog.setMessage(msg);
        dialog.setCancelable(false);

        dialog.setPositiveButton("Hide", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                showAlert = true;
            }
        });
        dialog.setNeutralButton("Call the\nfirefighters", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                showAlert = true;

                showToast("Calling to firefighters...");

                String telefone = "212950093";

                Uri uri = Uri.parse("tel:" + telefone);

                //Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                Intent intent = new Intent(Intent.ACTION_CALL, uri);

                if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(getApplicationContext(),
                            "No permission to call!", Toast.LENGTH_SHORT).show();

/*                    int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.CALL_PHONE);

                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(PainelActivity.this,
                            Manifest.permission.CALL_PHONE)) {

                        // Show an expanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.

                    } else {

                        // No explanation needed, we can request the permission.
                        int NOTHING = 0;

                        ActivityCompat.requestPermissions(PainelActivity.this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                NOTHING);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }
                    return;s*/
                }

                startActivity(intent);
            }
        });

        dialog.setNegativeButton("Don't show\nme anymore", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //showAlertInThisSession = false;
                showAlert = false;
            }
        });


        alert = dialog.create();
        alert.show();

        showAlert = false;
    }


    public void showMessage(final String msg, final String button) {
        runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder dialog = new AlertDialog.Builder(PainelActivity.this);
                dialog.setMessage(msg);
                dialog.setNeutralButton(button, null);
                dialog.show();
            }
        });
    }


    public void showToast(final String msg) {
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showToast(final String msg, final int duration) {
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getApplicationContext(), msg, duration).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}


