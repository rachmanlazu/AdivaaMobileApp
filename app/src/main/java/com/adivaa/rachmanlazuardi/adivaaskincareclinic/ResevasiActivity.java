package com.adivaa.rachmanlazuardi.adivaaskincareclinic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class ResevasiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservasi);

        assert getSupportActionBar() !=null; //null check
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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
