package com.example.localizadorgps;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ScrollView;
import android.content.BroadcastReceiver;


public class MainActivity extends Activity implements LocationListener
{
    /*
   private int precisaoMinimaExigida=2000;    // Precis�o do servi�o de localiza��o em metros.

   private int intervaloTempoLeituraGPS=5000; // De quanto em quanto tempo (milissegundos) avisar� que mudou de posi��o.
   private int distanciaLeituraGPS=0;         // De quantos em quantos metros avisar� que mudou a posi��o.

   private int intervaloTempoREDE=5000;       // De quanto em quanto tempo (milissegundos) avisar� que mudou de posi��o.
   private int distanciaREDE=0;               // De quantos em quantos metros avisar� que mudou posi��o.
   
   // Define os elementos visuais para exposi��o das informa��es no dispositivo.
   private EditText editTextPosicoes;
   private ScrollView scroller;

   // Define o gerenciador de localiza��o.
   private LocationManager locationManager;
      
   // Filtro para definir o evento de broadcast que o app aguardar�.
   // No caso "android.location.LocationManager.PROVIDERS_CHANGED_ACTION".
   private IntentFilter filter;
   
   // Caixa de di�logo para intera��o com o usu�rio.
   private AlertDialog alert;
   
   // Vari�veis de controle dos provedores de localiza��o habilitados na configura��o do Android.
   private Boolean provedorGPS_Habilitado;
   private Boolean provedorREDE_Habilitado;

   // Cria um Broadcast Receiver para que a MainActivity seja avisada caso o usu�rio mude as configura��es de localiza��o por fora do app
   // (deslizando a tela para baixo e clicando no �cone do GPS, por exemplo).
   // Isso � necess�rio porque durante a execu��o, o usu�rio tem como mudar as configura��es de localiza��o sem usar o pr�prio aplicativo.
   BroadcastReceiver bReceiver = new BroadcastReceiver()
   {      
      @Override
      public void onReceive(Context arg0, Intent arg1)
      {         
         // Chama o m�todo que localiza o usu�rio.
         localizarUsuario();         
      }
   };

   
   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);     
      setContentView(R.layout.activity_main);

      // Registra o receiver para que o app seja avisado quando o usu�rio modificar as configura��es de localiza��o do dispositivo.
      filter = new IntentFilter(android.location.LocationManager.PROVIDERS_CHANGED_ACTION);
      this.registerReceiver(bReceiver, filter);      
      
      // Obt�m o editText para usar como sa�da, na tela do dispositivo.
      editTextPosicoes=(EditText) findViewById(R.id.editTextPosicoes);
      editTextPosicoes.setEnabled(false);           
      
      // Obt�m o scroller, para permitir a rolagem da caixa de texto onde s�o exibidas as posi��es.
      scroller=(ScrollView) findViewById(R.id.scroller);

      // Limpa o editText.
      editTextPosicoes.setText("");
            
      // Chama o m�todo que localiza o usu�rio.
      localizarUsuario();
            
   }

   public void localizarUsuario()
   {

      // Este m�todo efetivamente realiza a localiza��o do usu�rio, configurando um "locationManager".
      // Atrav�s do locationManager o dispositivo utiliza ou o GPS ou a REDE para descobrir a localiza��o
      // do usu�rio. Note que, na configura��o do aparelho, podem estar habilitados ambos os m�todos,
      // algum deles, ou nenhum. Para cada caso, uma mensagem de alerta dever� ser mostrada, 
      // questionando o usu�rio se deseja ligar as ferramentas de localiza��o.
      
      try
      {
                  
         // Obt�m o locationManager.
         locationManager=(LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE);

         // H� provedores de localiza��o diversos.
         // O GPS_PROVIDER usa o hardware de GPS dispositivo para obter localiza��o via sat�lite.
         // O NETWORK_PROVIDER obter� a localiza��o a partir da triangula��o de antenas da rede de telefonia celular.

         // Verifica se os provedores de localiza��o est�o habilitados.
         provedorREDE_Habilitado=locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
         provedorGPS_Habilitado=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

         
         // Caso n�o haja nenhum provedor de localiza��o habilitado...
         if (!provedorREDE_Habilitado && !provedorGPS_Habilitado)
         {
                     
            // N�o h� provedores de localiza��o habilitados.
            // Perguntar ao usu�rio se deseja habilitar.
            
            // Monta a caixa de di�logo.
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
            alertDialogBuilder
                  .setMessage("O servi�o de localiza��o est� desabilitado. Deseja mudar a configura��o?")
                  .setCancelable(false)
                  .setPositiveButton("Configurar",  new DialogInterface.OnClickListener()
                  {
                     public void onClick(DialogInterface dialog, int id)
                     {
                        abreConfiguracaoDoDispositivo();
                     }
                  });
            
            alertDialogBuilder.setNegativeButton("Cancelar",  new DialogInterface.OnClickListener()
            {
               public void onClick(DialogInterface dialog, int id)
               {
                  dialog.cancel();
                  editTextPosicoes.setText("Selecione uma forma de obter a localiza��o\npelo menu Configurar.");
               }
            });
         
            alert = alertDialogBuilder.create();
            
            // Mostra a caixa de di�logo.
            if (!alert.isShowing())
            {
               alert.show();
            }

            
         }
         else
         {
            // Limpa o editText.
            editTextPosicoes.setText("");
            
            // Se servi�o de localiza��o j� foi configurado...
            if (provedorGPS_Habilitado)
            {
               configuraProvedorGPS();
            }
            else
            {
               
               // Caso apenas o servi�o de localiza��o por REDE esteja habilitado, sugere o GPS.
               
               // Monta a caixa de di�logo.
               AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
               alertDialogBuilder
                     .setMessage("Gostaria de habilitar o GPS para uma precis�o maior?")
                     .setCancelable(false)
                     .setPositiveButton("Sim",  new DialogInterface.OnClickListener()
                     {
                        public void onClick(DialogInterface dialog, int id)
                        {
                           abreConfiguracaoDoDispositivo();
                        }
                     });
               
               alertDialogBuilder.setNegativeButton("N�o",  new DialogInterface.OnClickListener()
               {
                  public void onClick(DialogInterface dialog, int id)
                  {
                     dialog.cancel();
                     configuraProvedorREDE();
                  }
               });
            
               alert = alertDialogBuilder.create();
               
               // Mostra a caixa de di�logo.
               if (!alert.isShowing())
               {
                  alert.show();
               }

            }
            
         }
         
      }
      catch (Exception e)
      {
         Log.i("gps", e.getMessage() + " " + e.getLocalizedMessage());
      }
   }

   public void abreConfiguracaoDoDispositivo()
   {
      // Este m�todo abre a tela de congifura��o de localiza��o do dispositivo.
      
      Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
      (MainActivity.this).startActivity(callGPSSettingIntent);     
   }
   
   
   public void configuraProvedorGPS()
   {

      // minTimeGPS � de quanto em quanto tempo (em milissegundos), a informa��o da localiza��o ser� atualizada via GPS.
      // minDistanceGPS � de quantos em quantos metros a informa��o da localiza��o ser� atualizada via GPS.
      long minTimeGPS=intervaloTempoLeituraGPS;
      long minDistanceGPS=distanciaLeituraGPS;

      // Configura o locationManager para chamar o m�todo OnLocationChanged() de acordo com as premissas estabelecidas.
      locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTimeGPS, minDistanceGPS, this);
   }

   public void configuraProvedorREDE()
   {
      
      // minTimeNETWORK � de quanto em quanto tempo (em milissegundos), a informa��o da localiza��o ser� atualizada via triangula��o de antenas.
      // minDistanceNETWORK � de quantos em quantos metros a informa��o da localiza��o ser� atualizada via via triangula��o de antenas.
      long minTimeNETWORK=intervaloTempoREDE;
      long minDistanceNETWORK=distanciaREDE;

      // Configura o locationManager para chamar o m�todo OnLocationChanged() de acordo com as premissas estabelecidas.
      locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTimeNETWORK, minDistanceNETWORK, this);
   }
   
   
   @Override
   public void onLocationChanged(Location location)
   {
      // Este m�todo ser� chamado toda vez que houver uma atualiza��o da localiza��o do usu�rio.
      // A atualiza��o da localiza��o ocorre de acordo com o configurado no locationManager.

      try
      {
         
         // Obt�m a informa��o da precis�o da localiza��o, em metros.
         float precisao=location.getAccuracy();

         // Se a precis�o for menor ou igual � precis�o m�nima exigida (em metros), ent�o mostra a localiza��o na tela.
         // A precis�o m�nima exigida depende da aplica��o.
         if (precisao <= precisaoMinimaExigida)
         {
            // Comp�e o texto de sa�da e acrescenta � caixa de texto.
            String latitude = String.valueOf(location.getLatitude()).substring(0, 10);
            String longitude = String.valueOf(location.getLongitude()).substring(0, 10);
            String novaLinha = location.getProvider() + ": " + latitude + "," + longitude + ", raio:  " + precisao + " m\n" ;
            editTextPosicoes.append( novaLinha );
            
            // Rola o texto da caixa para baixo.
            scroller.smoothScrollTo(0, editTextPosicoes.getBottom());
            
         }
      }
      catch (Exception e)
      {
      }

   }

   @Override
   public void onProviderDisabled(String provider)
   {
   }

   @Override
   public void onProviderEnabled(String provider)
   {     
   }

   @Override
   public void onStatusChanged(String provider, int status, Bundle extras)
   {
   }
        
   @Override
   protected void onDestroy()
   {
      super.onDestroy();
      
      // Desregistra o Broadcast Receiver.
      unregisterReceiver(bReceiver);
   }
   
   @Override
   public boolean onCreateOptionsMenu(Menu menu)
   {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.main, menu);

      return true;
   }
   
   @Override
   public boolean onOptionsItemSelected(MenuItem item)
   {
      // Lida com os itens do menu selecion�veis pelo usu�rio.
      switch (item.getItemId())
      {
         case R.id.action_settings:
            abreConfiguracaoDoDispositivo();
            break;
      }
      
      return super.onOptionsItemSelected(item);
   }

   */
}
