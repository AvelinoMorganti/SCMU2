package com.whaves.scmu;


import android.Manifest;
import android.app.*;
import android.content.*;
import android.location.*;
import android.view.*;
import android.widget.*;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.app.Activity;
import android.view.View;
import android.widget.ToggleButton;

import com.google.gson.Gson;

import org.apache.http.impl.client.BasicCookieStore;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class SettingsActivity extends Activity implements LocationListener {

    //Request/response
    private Request request;
    private State state;
    CookieStoreImpl data;

    //Constants
    int time = 1000 * 5;             //call function every 5 seconds
    int i = 0;
    private Timer myTimer;


    private String latitude = "";
    private String longitude = "";
    private String altitude;
    private String _precisao;

    private int precisaoMinimaExigida = 30;//2000    // Precisão do serviço de localização em metros.
    private int intervaloTempoLeituraGPS = 1000; // De quanto em quanto tempo (milissegundos) avisará que mudou de posição.
    private int distanciaLeituraGPS = 10;         // De quantos em quantos metros avisará que mudou a posição.
    private int intervaloTempoREDE = 1000;       // De quanto em quanto tempo (milissegundos) avisará que mudou de posição.
    private int distanciaREDE = 10;               // De quantos em quantos metros avisará que mudou posição.

    // Define os elementos visuais para exposição das informações no dispositivo.
    private EditText editTextPosicoes;
    private ScrollView scroller;
    private ToggleButton toggleSMSbt;
    // Define o gerenciador de localização.
    private LocationManager locationManager;

    // Filtro para definir o evento de broadcast que o app aguardará.
    // No caso "android.location.LocationManager.PROVIDERS_CHANGED_ACTION".
    private IntentFilter filter;

    // Caixa de diálogo para interação com o usuário.
    private AlertDialog alert;

    // Variáveis de controle dos provedores de localização habilitados na configuração do Android.
    private Boolean provedorGPS_Habilitado;
    private Boolean provedorREDE_Habilitado;

    // Cria um Broadcast Receiver para que a GPSActivity seja avisada caso o usuário mude as configurações de localização por fora do app
    // (deslizando a tela para baixo e clicando no ícone do GPS, por exemplo).
    // Isso é necessário porque durante a execução, o usuário tem como mudar as configurações de localização sem usar o próprio aplicativo.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        // Registra o receiver para que o app seja avisado quando o usuário modificar as configurações de localização do dispositivo.
        filter = new IntentFilter(android.location.LocationManager.PROVIDERS_CHANGED_ACTION);
        this.registerReceiver(bReceiver, filter);

        // Obtém o editText para usar como saída, na tela do dispositivo.
        editTextPosicoes = (EditText) findViewById(R.id.editTextPosicoes);
        editTextPosicoes.setEnabled(false);

        // Obtém o scroller, para permitir a rolagem da caixa de texto onde são exibidas as posições.
        scroller = (ScrollView) findViewById(R.id.scroller);

        // Limpa o editText.
        editTextPosicoes.setText("");

        Intent i = getIntent();
        data = (CookieStoreImpl) i.getSerializableExtra("COOKIESTORE");


        // get your ToggleButton
        toggleSMSbt = (ToggleButton) findViewById(R.id.toggleSMS);

        localizarUsuario();
        //actualizar();

        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }

        }, 0, time);
    }

    public void actualizar() {
        BasicCookieStore ck = Utils.createApacheCookieStore((List<CookiesImpl>) data.getData());
        request = new Request(ck);

        String response = request.getStateJSON(ck);

        Gson gson = new Gson();
        state = gson.fromJson(response, State.class);

        toggleSMSbt.setChecked(state.isSmsNotifications());
    }

    public void setSMS(View view) {
        if (((ToggleButton) view).isChecked()) {
            try {
                BasicCookieStore ck = Utils.createApacheCookieStore((List<CookiesImpl>) data.getData());
                request = new Request(ck);

                String response = request.getStateJSON(ck);

                Gson gson = new Gson();
                state = gson.fromJson(response, State.class);
                state.setSmsNotifications(true);

                String json = gson.toJson(state);
                request.setStateJSON(ck, json);

                showMessage("Automatic SMS notifications are Active.\nNow, police and firefighters receive\nAutomatic notifications", "OK");
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),
                         "Network error. Please make sure your Wifi is active and try again.", Toast.LENGTH_SHORT).show();
            }
        } else {
            try {
                BasicCookieStore ck = Utils.createApacheCookieStore((List<CookiesImpl>) data.getData());
                request = new Request(ck);

                String response = request.getStateJSON(ck);

                Gson gson = new Gson();
                state = gson.fromJson(response, State.class);
                state.setSmsNotifications(false);

                String json = gson.toJson(state);
                request.setStateJSON(ck, json);

                showMessage("Now, Automatic SMS notifications is Inactive", "OK");
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),
                        "Network error. Please make sure your Wifi is active and try again.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setGPS(View view) {
        /*
        * TODO
        * bug: quando ha dados vazios de latitude e longitude a aplicaçao nao consegue atualizar
        * */
        try {
            if (latitude.isEmpty() ||
                    longitude.isEmpty() ||
                    latitude == null ||
                    longitude == null) {
                Toast.makeText(getApplicationContext(),
                        "GPS data is empty. Please make sure your GPS is active, go to an open spot and try again.", Toast.LENGTH_SHORT).show();
            } else {
                BasicCookieStore ck = Utils.createApacheCookieStore((List<CookiesImpl>) data.getData());
                request = new Request(ck);

                String response = request.getStateJSON(ck);
                //showMessage(response, "OK");

                Gson gson = new Gson();
                state = gson.fromJson(response, State.class);

                double distance = HaversineAlgorithm.HaversineInM(
                        Double.parseDouble(state.getLatitude()),
                        Double.parseDouble(state.getLongitude()),
                        Double.parseDouble(latitude),
                        Double.parseDouble(longitude));

                //showMessage("Distancia é:" + distance, "OKAY");

                state.setLatitude(latitude);
                state.setLongitude(longitude);

                String json = gson.toJson(state);
                request.setStateJSON(ck, json);

                NumberFormat nf = new DecimalFormat("##,######");
                nf.setMaximumFractionDigits(3);
                nf.setMinimumFractionDigits(3);

                showMessage("The position of your home has been updated." +
                        "\nDistance from the last position: "
                        +nf.format(distance) +"m", "OK");

                latitude = "";
                longitude = "";
            }
        } catch (Exception e) {
            // Toast.makeText(getApplicationContext(),
            //       e.getMessage(), Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(),
                    "Network error. Please make sure your Wifi is active and try again.", Toast.LENGTH_SHORT).show();
        }

    }

    public void back(View v) {
        finish();
    }

    BroadcastReceiver bReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            // Chama o método que localiza o usuário.
            localizarUsuario();

        }
    };

    public String getLatitude() {
        return this.latitude;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void localizarUsuario() {

        // Este método efetivamente realiza a localização do usuário, configurando um "locationManager".
        // Através do locationManager o dispositivo utiliza ou o GPS ou a REDE para descobrir a localização
        // do usuário. Note que, na configuração do aparelho, podem estar habilitados ambos os métodos,
        // algum deles, ou nenhum. Para cada caso, uma mensagem de alerta deverá ser mostrada,
        // questionando o usuário se deseja ligar as ferramentas de localização.

        try {

            // Obtém o locationManager.
            locationManager = (LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE);

            // Há provedores de localização diversos.
            // O GPS_PROVIDER usa o hardware de GPS dispositivo para obter localização via satélite.
            // O NETWORK_PROVIDER obterá a localização a partir da triangulação de antenas da rede de telefonia celular.

            // Verifica se os provedores de localização estão habilitados.
            provedorREDE_Habilitado = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            provedorGPS_Habilitado = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);


            // Caso não haja nenhum provedor de localização habilitado...
            if (!provedorREDE_Habilitado && !provedorGPS_Habilitado) {

                // Não há provedores de localização habilitados.
                // Perguntar ao usuário se deseja habilitar.

                // Monta a caixa de diálogo.
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SettingsActivity.this);
                alertDialogBuilder
                        //.setMessage("O serviço de localização está desabilitado. Deseja mudar a configuração?")
                        .setMessage("The location service is disabled. Do you want to change the configuration?")
                        .setCancelable(false)
                        .setPositiveButton("Configure", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                abreConfiguracaoDoDispositivo();
                            }
                        });

                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        //editTextPosicoes.setText("Selecione uma forma de obter a localização\npelo menu Configurar.");
                        editTextPosicoes.setText("Please select a way to get\n the location in Setup menu.");
                    }
                });

                alert = alertDialogBuilder.create();

                // Mostra a caixa de diálogo.
                if (!alert.isShowing()) {
                    alert.show();
                }


            } else {
                // Limpa o editText.
                editTextPosicoes.setText("");

                // Se serviço de localização já foi configurado...
                if (provedorGPS_Habilitado) {
                    configuraProvedorGPS();
                } else {

                    // Caso apenas o serviço de localização por REDE esteja habilitado, sugere o GPS.

                    // Monta a caixa de diálogo.
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SettingsActivity.this);
                    alertDialogBuilder
                            //.setMessage("Gostaria de habilitar o GPS para uma precisão maior?")
                            .setMessage("Would you like to enable GPS for greater accuracy?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    abreConfiguracaoDoDispositivo();
                                }
                            });

                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            configuraProvedorREDE();
                        }
                    });

                    alert = alertDialogBuilder.create();

                    // Mostra a caixa de diálogo.
                    if (!alert.isShowing()) {
                        alert.show();
                    }

                }

            }

        } catch (Exception e) {
            Log.i("gps", e.getMessage() + " " + e.getLocalizedMessage());
        }
    }

    public void abreConfiguracaoDoDispositivo() {
        // Este método abre a tela de congifuração de localização do dispositivo.

        Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        (SettingsActivity.this).startActivity(callGPSSettingIntent);
    }


    public void configuraProvedorGPS() {

        // minTimeGPS é de quanto em quanto tempo (em milissegundos), a informação da localização será atualizada via GPS.
        // minDistanceGPS é de quantos em quantos metros a informação da localização será atualizada via GPS.
        long minTimeGPS = intervaloTempoLeituraGPS;
        long minDistanceGPS = distanciaLeituraGPS;

        // Configura o locationManager para chamar o método OnLocationChanged() de acordo com as premissas estabelecidas.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTimeGPS, minDistanceGPS, this);
    }

    public void configuraProvedorREDE() {

        // minTimeNETWORK é de quanto em quanto tempo (em milissegundos), a informação da localização será atualizada via triangulação de antenas.
        // minDistanceNETWORK é de quantos em quantos metros a informação da localização será atualizada via via triangulação de antenas.
        long minTimeNETWORK = intervaloTempoREDE;
        long minDistanceNETWORK = distanciaREDE;

        // Configura o locationManager para chamar o método OnLocationChanged() de acordo com as premissas estabelecidas.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTimeNETWORK, minDistanceNETWORK, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        // Este método será chamado toda vez que houver uma atualização da localização do usuário.
        // A atualização da localização ocorre de acordo com o configurado no locationManager.

        try {

            // Obtém a informação da precisão da localização, em metros.
            float precisao = location.getAccuracy();
            _precisao = String.valueOf(precisao);

            // Se a precisão for menor ou igual à precisão mínima exigida (em metros), então mostra a localização na tela.
            // A precisão mínima exigida depende da aplicação.
            if (precisao <= precisaoMinimaExigida) {
                // Compõe o texto de saída e acrescenta à caixa de texto.
                latitude = String.valueOf(location.getLatitude()).substring(0, 10);
                longitude = String.valueOf(location.getLongitude()).substring(0, 10);
                String novaLinha = "Latitude: " + latitude + " - Longitude: " + longitude + "\nAccuracy:  " + precisao + " m\n";//- Origem dos dados: "+location.getProvider() + "\n";
                editTextPosicoes.setText(novaLinha);

                // Rola o texto da caixa para baixo.
                scroller.smoothScrollTo(0, editTextPosicoes.getBottom());


            }
        } catch (Exception e) {
        }

    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Desregistra o Broadcast Receiver.
        unregisterReceiver(bReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Lida com os itens do menu selecionáveis pelo usuário.
        switch (item.getItemId()) {
            case R.id.action_settings:
                abreConfiguracaoDoDispositivo();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showMessage(String msg, String button) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(SettingsActivity.this);
        dialog.setMessage(msg);
        dialog.setNeutralButton(button, null);
        dialog.show();
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

}