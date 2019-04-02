package com.adivaa.rachmanlazuardi.adivaaskincareclinic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AmbilAntrianActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnAntrian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambil_antrian);

        btnAntrian = findViewById(R.id.buttonAntrian);

        btnAntrian.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonAntrian:
                Intent moveIntent = new Intent(AmbilAntrianActivity.this, ResevasiActivity.class);
                startActivity(moveIntent);
                break;
        }
    }
}
