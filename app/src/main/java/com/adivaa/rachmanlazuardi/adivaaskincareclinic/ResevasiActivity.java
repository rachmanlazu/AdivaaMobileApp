package com.adivaa.rachmanlazuardi.adivaaskincareclinic;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

public class ResevasiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservasi);

        //token get
        SharedPreferences sharedpref = this.getSharedPreferences("token", Context.MODE_PRIVATE);
        String token = sharedpref.getString("token", "defaultValue");
        Log.d("Home", "token home" +token);
        //

        //back button tittle bar
        assert getSupportActionBar() !=null; //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //judul header
        setTitle("Reservasi Antrian");
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
}
