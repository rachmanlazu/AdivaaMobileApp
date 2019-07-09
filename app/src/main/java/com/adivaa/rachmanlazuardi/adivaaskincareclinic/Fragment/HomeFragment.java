package com.adivaa.rachmanlazuardi.adivaaskincareclinic.Fragment;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.adivaa.rachmanlazuardi.adivaaskincareclinic.AmbilAntrianActivity;
import com.adivaa.rachmanlazuardi.adivaaskincareclinic.Model.AntrianModel;
import com.adivaa.rachmanlazuardi.adivaaskincareclinic.IResult;
import com.adivaa.rachmanlazuardi.adivaaskincareclinic.ProdukActivity;
import com.adivaa.rachmanlazuardi.adivaaskincareclinic.R;
import com.adivaa.rachmanlazuardi.adivaaskincareclinic.ResevasiActivity;
import com.adivaa.rachmanlazuardi.adivaaskincareclinic.RiwayatPasienActivity;
import com.adivaa.rachmanlazuardi.adivaaskincareclinic.Service.VolleyService;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private CardView reserve, rekam, konsul, product;
    //private String noWhatsapp;

    private IResult mResultCallback = null;
    private VolleyService mVolleyService;

    private String token;

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

        //token get
        SharedPreferences sharedpref = this.getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);
        token = sharedpref.getString("token", "defaultValue");

        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback, getContext());


        return view;
    }

    public void getData(){
        HashMap headers = new HashMap();
        headers.put("Authorization", "Bearer " + token);

        String URI = "http://10.0.2.2:8000/api/pasien/getAntrian";
        mVolleyService.getDataHeadersVolley("GETANTRIAN", URI, headers);
    }

//    public void getWhatsAppNumber(){
//        HashMap headers = new HashMap();
//        headers.put("Authorization", "Bearer " + token);
//
//        String URI = "http://10.0.2.2:8000/api/getWhatsappNumber";
//        mVolleyService.getDataHeadersVolley("GETWANO", URI, headers);
//    }

    void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) throws JSONException {
                System.out.println(response);

                JSONObject data = response.getJSONObject("data");
                //JSONObject WaNumber = response.getJSONObject("WaNumber");

                Integer NoAntrianPasien = data.getInt("nomor_antrian");
                String Jam = data.getString("jam");
                Integer NoAntrianSaatIni = data.getInt("nomor_antrian_saat_ini");

                //noWhatsapp = WaNumber.getString("no_whatsapp");

                if (NoAntrianPasien==0){
                    Intent moveIntent = new Intent(getActivity(), AmbilAntrianActivity.class);
                    startActivity(moveIntent);
                }
                else {
                    AntrianModel antrianModel = new AntrianModel();

                    antrianModel.setNoAntrianPasien(NoAntrianPasien);
                    antrianModel.setJamDatang(Jam);
                    antrianModel.setNoAntrianSaatIni(NoAntrianSaatIni);

                    Intent moveIntent = new Intent(getActivity(), ResevasiActivity.class);
                    moveIntent.putExtra("AntrianModel", antrianModel);
                    startActivity(moveIntent);
                }


            }


            @Override
            public void notifyError(String requestType, VolleyError error) {

            }
        };
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reservasi:

                getData();


                break;
        }

        switch (v.getId()) {
            case R.id.rekamMedis:
                Intent moveIntent = new Intent(getActivity(), RiwayatPasienActivity.class);
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
        String smsNumber = "6287870817600";
        //getWhatsAppNumber();

        try {
            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Dokter saya mau konsultasi");
            sendIntent.putExtra("jid", smsNumber + "@s.whatsapp.net");
            //sendIntent.putExtra("jid", noWhatsapp + "@s.whatsapp.net");
            sendIntent.setPackage("com.whatsapp");
            startActivity(sendIntent);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Error\n" + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}


