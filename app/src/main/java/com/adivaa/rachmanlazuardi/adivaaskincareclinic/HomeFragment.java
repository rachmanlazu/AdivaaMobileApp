package com.adivaa.rachmanlazuardi.adivaaskincareclinic;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private CardView reserve, rekam, konsul, product;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false);

        reserve = view.findViewById(R.id.reservasi);
        rekam = view.findViewById(R.id.rekamMedis);
        konsul = view.findViewById(R.id.konsultasi);
        product = view.findViewById(R.id.produk);


        reserve.setOnClickListener(this);
        rekam.setOnClickListener(this);
        konsul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWhatsApp();
            }
        });
        product.setOnClickListener(this);


        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reservasi:
                Intent moveIntent = new Intent(getActivity(), AmbilAntrianActivity.class);
                startActivity(moveIntent);
                break;
        }

        switch (v.getId()) {
            case R.id.rekamMedis:
                Intent moveIntent = new Intent(getActivity(), RekamMedisActivity.class);
                startActivity(moveIntent);
                break;
        }

        switch (v.getId()) {
            case R.id.produk:
                Intent moveIntent = new Intent(getActivity(), ProdukActivity.class);
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
            Toast.makeText(getActivity(), "Error\n" + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}


