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

public class ItemProdukActivity extends AppCompatActivity {
    private Button btnAlertPesan;

    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_produk);

        //token get
        SharedPreferences sharedpref = this.getSharedPreferences("token", Context.MODE_PRIVATE);
        token = sharedpref.getString("token", "defaultValue");
        Log.d("Item Produk", "token produk " +token);
        //

        //judul header
        setTitle("Produk");

        //back button tittle bar
        assert getSupportActionBar() !=null; //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnAlertPesan = findViewById(R.id.buttonPesan);

        //pop-up window
        btnAlertPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ItemProdukActivity.this);

                builder.setCancelable(true);
                builder.setTitle("Apakah anda ingin membeli produk tersebut?");
                builder.setMessage("Jika Ya anda akan dihubungi oleh admin untuk informasi pemesanan selanjutnya");

                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.show();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
