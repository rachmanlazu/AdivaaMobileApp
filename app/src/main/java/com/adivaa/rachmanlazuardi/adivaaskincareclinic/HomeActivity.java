package com.adivaa.rachmanlazuardi.adivaaskincareclinic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView reserve, rekam, konsul, product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        reserve = findViewById(R.id.reservasi);
        rekam = findViewById(R.id.rekamMedis);
        konsul = findViewById(R.id.konsultasi);
        product = findViewById(R.id.produk);

        reserve.setOnClickListener(this);
        rekam.setOnClickListener(this);
        konsul.setOnClickListener(this);
        product.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reservasi:
                Intent moveIntent = new Intent(HomeActivity.this, AmbilAntrianActivity.class);
                startActivity(moveIntent);
                break;
        }

        switch (v.getId()){
            case R.id.rekamMedis:
                Intent moveIntent = new Intent(HomeActivity.this, RekamMedisActivity.class);
                startActivity(moveIntent);
                break;
        }
    }
}
