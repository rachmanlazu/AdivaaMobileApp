package com.adivaa.rachmanlazuardi.adivaaskincareclinic;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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

        //token get
        SharedPreferences sharedpref = this.getSharedPreferences("token", Context.MODE_PRIVATE);
        String token = sharedpref.getString("token", "defaultValue");
        Log.d("Home", "token home" +token);
        //

        reserve.setOnClickListener(this);
        rekam.setOnClickListener(this);
        konsul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWhatsApp();
            }
        });
        product.setOnClickListener(this);

        //judul header
        setTitle("Home");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reservasi:
                Intent moveIntent = new Intent(HomeActivity.this, AmbilAntrianActivity.class);
                startActivity(moveIntent);
                break;
        }

        switch (v.getId()) {
            case R.id.rekamMedis:
                Intent moveIntent = new Intent(HomeActivity.this, RekamMedisActivity.class);
                startActivity(moveIntent);
                break;
        }

        switch (v.getId()) {
            case R.id.produk:
                Intent moveIntent = new Intent(HomeActivity.this, ProdukActivity.class);
                startActivity(moveIntent);
                break;
        }
    }

    /**
     * Open WhatssApp.
     *
     * @param context     current Context, like Activity, App, or Service
     * @param packageName the full package name of the app to open
     * @return true if likely successful, false if unsuccessful
     */
    public static boolean openApp(Context context, String packageName) {
        PackageManager manager = context.getPackageManager();
        try {
            Intent i = manager.getLaunchIntentForPackage(packageName);
            if (i == null) {
                return false;
                //throw new ActivityNotFoundException();
            }
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            context.startActivity(i);
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }

    private void openWhatsApp() {
        String smsNumber = "6289657011491"; //without '+'
        try {
            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Dokter saya mau konsultasi");
            sendIntent.putExtra("jid", smsNumber + "@s.whatsapp.net");
            sendIntent.setPackage("com.whatsapp");
            startActivity(sendIntent);
        } catch (Exception e) {
            Toast.makeText(this, "Error\n" + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
