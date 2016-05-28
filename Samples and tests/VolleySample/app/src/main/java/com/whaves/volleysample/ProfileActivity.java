package com.whaves.volleysample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView tv = (TextView) findViewById(R.id.tvAnswer);
        tv.setText("Resposta servidor: " + getIntent().getExtras().getString("answer"));
    }
}