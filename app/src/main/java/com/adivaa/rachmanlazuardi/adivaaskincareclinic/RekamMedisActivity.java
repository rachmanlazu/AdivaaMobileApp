package com.adivaa.rachmanlazuardi.adivaaskincareclinic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class RekamMedisActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekam_medis);

        //back button tittle bar
        assert getSupportActionBar() !=null; //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //judul header
        setTitle("Rekam Medis");
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
