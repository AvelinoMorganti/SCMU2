package com.br;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class InicializaServico extends Activity implements OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// obtema a refer�ncia do bot�o criado.
		Button soma = (Button) findViewById(R.id.botao);

		// Seta um texto
		TextView text = (TextView) findViewById(R.id.idTexto);
		text.setText("Iniciando um Servi�o");

		// Listener criado para o bot�o soma
		soma.setOnClickListener(InicializaServico.this);
		//startService(new Intent("GPS_SERVICE"));
		//startService(new Intent("INICIAR_SERVICO"));

	}

	public void onClick(View v) {

		// Inicia o servi�o a partir de INICIAR_SERVICO definido no
		// androidManifest.xml
		startService(new Intent("INICIAR_SERVICO"));

	}

}