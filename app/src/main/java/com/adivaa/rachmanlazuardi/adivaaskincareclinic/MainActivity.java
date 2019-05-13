package com.adivaa.rachmanlazuardi.adivaaskincareclinic;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView nama, noMember;
    private DrawerLayout drawer;
    private long backPressedTime;
    private Toast backToast;
    ActionBarDrawerToggle toggle;

    private String token;

    private IResult mResultCallback = null;
    private VolleyService mVolleyService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //token get
        SharedPreferences sharedpref = this.getSharedPreferences("token", Context.MODE_PRIVATE);
        token = sharedpref.getString("token", "defaultValue");
        Log.d("Home", "token home " +token);
        //

        //judul header
        setTitle("Home");

        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback, getApplicationContext());


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.nav_open,R.string.nav_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        View headers = navigationView.getHeaderView(0);
        nama = headers.findViewById(R.id.namaPasien);
        noMember = headers.findViewById(R.id.noMember);

        getData();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    public void getData(){
        HashMap headers = new HashMap();
        headers.put("Authorization", "Bearer " + token);

        String URI = "http://10.0.2.2:8000/api/pasien/getDetail";
        mVolleyService.getDataHeadersVolley("GETPROFILE", URI, headers);
    }

    void initVolleyCallback(){
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) throws JSONException {
                System.out.println(response);

                switch (requestType){
                    case "GETPROFILE":
                        JSONObject data = response.getJSONObject("data");

                        String Nama = data.getString("nama");
                        String NoMember = data.getString("no_member");

                        nama.setText(Nama);
                        noMember.setText(NoMember);
                        break;
                }



            }

            @Override
            public void notifyError(String requestType, VolleyError error) {

            }
        };
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Tekan kembali untuk keluar", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();

//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                setTitle("Home");
                break;

            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
                setTitle("Profile");
                break;

            case R.id.nav_logout:
                //token get
                SharedPreferences sharedpref = this.getSharedPreferences("token", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpref.edit();
                editor.clear();
                editor.commit();

                Intent moveIntent = new Intent(getApplicationContext(), LoginActivity.class);
                moveIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(moveIntent);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
