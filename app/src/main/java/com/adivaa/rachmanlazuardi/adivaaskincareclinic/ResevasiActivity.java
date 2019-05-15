package com.adivaa.rachmanlazuardi.adivaaskincareclinic;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ResevasiActivity extends AppCompatActivity {
    private Button btnBantuan;
    private String token;

    private TextView noAntrianPasien, jamDatang, noAntrianSaatIni, namaPasien;

    private Bundle bundle;
    private AntrianModel model;

    private IResult mResultCallback = null;
    private VolleyService mVolleyService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservasi);

        bundle = getIntent().getExtras();
        model = bundle.getParcelable("AntrianModel");

        noAntrianPasien = findViewById(R.id.antrianCust);
        jamDatang = findViewById(R.id.jam);
        noAntrianSaatIni = findViewById(R.id.noAntrianSaatIni);
        namaPasien = findViewById(R.id.namaPasienAntrian);

        noAntrianPasien.setText(model.getNoAntrianPasien().toString());
        jamDatang.setText(model.getJamDatang().toString());
        noAntrianSaatIni.setText(model.getNoAntrianSaatIni().toString());

        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback, getApplicationContext());


        //token get
        SharedPreferences sharedpref = this.getSharedPreferences("token", Context.MODE_PRIVATE);
        token = sharedpref.getString("token", "defaultValue");
        Log.d("Reservasi", "token reservasi " +token);
        //

        getData();


        //back button tittle bar
        assert getSupportActionBar() !=null; //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //judul header
        setTitle("Reservasi Antrian");

        btnBantuan = findViewById(R.id.buttonBantuan);

        btnBantuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWhatsApp();
            }
        });
    }

    public void getData(){
        HashMap headers = new HashMap();
        headers.put("Authorization", "Bearer " + token);

        String URI = "http://10.0.2.2:8000/api/pasien/getDetail";
        mVolleyService.getDataHeadersVolley("GETPROFILE", URI, headers);
    }

    void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) throws JSONException {
                System.out.println(response);

                JSONObject data = response.getJSONObject("data");

                String NamaPasien = data.getString("nama");

                namaPasien.setText(NamaPasien);
            }

            @Override
            public void notifyError(String requestType, VolleyError error) {

            }
        };
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
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Admin saya mau menanyakan antrian saya");
            sendIntent.putExtra("jid", smsNumber + "@s.whatsapp.net");
            sendIntent.setPackage("com.whatsapp");
            startActivity(sendIntent);
        } catch (Exception e) {
            Toast.makeText(this, "Error\n" + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    //back button tittle bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent moveIntent = new Intent(ResevasiActivity.this, MainActivity.class);
                moveIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(moveIntent);
                return true;

                default:
                    return super.onOptionsItemSelected(item);
        }
    }
}
