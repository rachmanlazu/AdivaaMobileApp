package com.adivaa.rachmanlazuardi.adivaaskincareclinic;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.adivaa.rachmanlazuardi.adivaaskincareclinic.Adapter.SectionPageAdapter;
import com.adivaa.rachmanlazuardi.adivaaskincareclinic.Fragment.PembelianProdukFragment;
import com.adivaa.rachmanlazuardi.adivaaskincareclinic.Fragment.RekamMedisFragment;

public class RiwayatPasienActivity extends AppCompatActivity {
    private static final String TAG = "RiwayatPasienActivity";

    private SectionPageAdapter mSectionPageAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_pasien);
        Log.d(TAG, "onCreate: Starting.");

        //token get
        SharedPreferences sharedpref = this.getSharedPreferences("token", Context.MODE_PRIVATE);
        String token = sharedpref.getString("token", "defaultValue");
        Log.d("Riwayat Pasien", "token riwayat pasien " +token);
        //

        mSectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        //back button tittle bar
        assert getSupportActionBar() !=null; //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //judul header
        setTitle("Riwayat Pasien");
    }

    private void setupViewPager(ViewPager viewPager){
        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new RekamMedisFragment(), "Rekam Medis");
        adapter.addFragment(new PembelianProdukFragment(), "Pembelian Produk");
        viewPager.setAdapter(adapter);
    }

    //back button tittle bar
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
