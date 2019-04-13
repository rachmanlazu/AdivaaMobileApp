package com.adivaa.rachmanlazuardi.adivaaskincareclinic;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AmbilAntrianActivity extends AppCompatActivity {

    private Button btnAntrian, alertButton;
    private TextView alertTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambil_antrian);

        //back button tittle bar
        assert getSupportActionBar() !=null; //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                builder.setMessage("Anda harus menunggu sekitar 90 menit dalam 1 antrian");

                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent moveIntent = new Intent(AmbilAntrianActivity.this, ResevasiActivity.class);
                        startActivity(moveIntent);
                    }
                });

                builder.show();
            }
        });

        //btnAntrian = findViewById(R.id.buttonAntrian);

        //btnAntrian.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
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
