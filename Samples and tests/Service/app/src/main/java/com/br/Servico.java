package com.br;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class Servico extends Service {

	public static final String CATEGORIA = "servico";

	@Override
	public void onStart(Intent intent, int startId) {

		incremento(0);
		stopSelf();
	}

	private void incremento(int result) {

		for (int i = 0; i < 100; i++) {

			try {
				Thread.sleep(1000);
				result++;
				Log.i(CATEGORIA, "incremento  " + result);
			} catch (InterruptedException e) {

				Log.i(CATEGORIA, "erro " + e);
			}
		}

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
