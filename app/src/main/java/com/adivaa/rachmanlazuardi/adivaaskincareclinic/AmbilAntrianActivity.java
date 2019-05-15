package com.adivaa.rachmanlazuardi.adivaaskincareclinic;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AmbilAntrianActivity extends AppCompatActivity {

    private Button btnAntrian, alertButton;
    private TextView alertTextView, noAntrianSaatIni, jam, noAntrianPasien, jamDatang;

    private String token;

    private IResult mResultCallback = null;
    private VolleyService mVolleyService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambil_antrian);

        //back button tittle bar
        assert getSupportActionBar() != null; //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //token get
        SharedPreferences sharedpref = this.getSharedPreferences("token", Context.MODE_PRIVATE);
        token = sharedpref.getString("token", "defaultValue");
        Log.d("Reservasi", "token ambil antrian " + token);
        //

        noAntrianSaatIni = findViewById(R.id.noAmbilAntrian);
        jam = findViewById(R.id.jamDatang);
        noAntrianPasien = findViewById(R.id.antrianCust);
        jamDatang = findViewById(R.id.jam);

        System.out.println(noAntrianSaatIni);

        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback, getApplicationContext());

        getData();

        //judul header
        setTitle("Reservasi Antrian");

        alertButton = findViewById(R.id.buttonAntrian);

        //pop-up window
        alertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AmbilAntrianActivity.this);

                builder.setCancelable(true);
                builder.setTitle("Apakah anda ingin mengambil nomor antrian?");
                builder.setMessage("Anda harus menunggu sekitar 60 menit dalam 1 antrian");

                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        HashMap params = new HashMap<String, String>();
//                        params.put("nomor_antrian", noAntrianPasien);
//                        params.put("jam", jamDatang);

                        HashMap headers = new HashMap();
                        headers.put("Authorization", "Bearer " + token);
//                        headers.put("X-HTTP-Method-Override","PATCH");
                        String URI = "http://10.0.2.2:8000/api/pasien/ambilAntrian";

                        mVolleyService.postDataHeadersVolley("POSTANTRIAN", URI, params, headers);
                    }
                });

                builder.show();
            }
        });

        //btnAntrian = findViewById(R.id.buttonAntrian);

        //btnAntrian.setOnClickListener(this);
    }

    public void getData() {
        HashMap headers = new HashMap();
        headers.put("Authorization", "Bearer " + token);

        String URI = "http://10.0.2.2:8000/api/pasien/getAntrian";
        //String URI = "http://192.168.43.68:8000/api/pasien/getAntrian";
        mVolleyService.getDataHeadersVolley("GETNOANTRIAN", URI, headers);
    }


    void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) throws JSONException {
                System.out.println(response);

                switch (requestType) {
                    case "GETNOANTRIAN":
                        JSONObject data = response.getJSONObject("data");
                        Log.d("kribo", String.valueOf(data.getInt("nomor_antrian_saat_ini")));
                        Integer NoAntrian = data.getInt("nomor_antrian_saat_ini");
                        String Jam = data.getString("jam");

                        noAntrianSaatIni.setText(NoAntrian.toString());
                        jam.setText(Jam.toString() + ":00");
                        break;

                    case "POSTANTRIAN":

                        JSONObject dataNoAntrian = response.getJSONObject("data");
                        Integer NoAntrianPasien = dataNoAntrian.getInt("nomor_antrian");
                        String JamDatang = dataNoAntrian.getString("jam");
                        Integer NoAntrianSaatIni = dataNoAntrian.getInt("nomor_antrian_saat_ini");

                        AntrianModel antrianModel = new AntrianModel();

                        antrianModel.setNoAntrianPasien(NoAntrianPasien);
                        antrianModel.setJamDatang(JamDatang);
                        antrianModel.setNoAntrianSaatIni(NoAntrianSaatIni);

                        Intent moveIntent = new Intent(AmbilAntrianActivity.this, ResevasiActivity.class);
                        moveIntent.putExtra("AntrianModel", antrianModel);
                        startActivity(moveIntent);

                        break;
                }

            }

            @Override
            public void notifyError(String requestType, VolleyError error) {

            }
        };
    }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            switch (item.getItemId()) {
                case android.R.id.home:
                    finish();
                    return true;

                default:
                    return super.onOptionsItemSelected(item);
            }
        }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.buttonAntrian:
//                Intent moveIntent = new Intent(AmbilAntrianActivity.this, ResevasiActivity.class);
//                startActivity(moveIntent);
//                break;
//        }
//    }
}
