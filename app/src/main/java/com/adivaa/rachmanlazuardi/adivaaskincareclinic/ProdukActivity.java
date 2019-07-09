package com.adivaa.rachmanlazuardi.adivaaskincareclinic;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.adivaa.rachmanlazuardi.adivaaskincareclinic.Adapter.ProdukAdapter;
import com.adivaa.rachmanlazuardi.adivaaskincareclinic.Model.ProdukModel;
import com.adivaa.rachmanlazuardi.adivaaskincareclinic.Service.VolleyService;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ProdukActivity extends AppCompatActivity implements ProdukAdapter.OnItemClickListener {
//    RecyclerView listProduk;

    private RecyclerView recyclerView;

    private TextView namaProduk, hargaProduk;

    private String token;

    private IResult mResultCallback = null;
    private VolleyService mVolleyService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk);

        recyclerView =  findViewById(R.id.list_produk);

        //token get
        SharedPreferences sharedpref = this.getSharedPreferences("token", Context.MODE_PRIVATE);
        token = sharedpref.getString("token", "defaultValue");
        Log.d("Produk", "token produk " +token);
        //

        //back button tittle bar
        assert getSupportActionBar() !=null; //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //judul header
        setTitle("Produk");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback, getApplicationContext());

        getData();
    }

    public void getData(){
        HashMap headers = new HashMap();
        headers.put("Authorization", "Bearer " + token);

        String URI = "http://10.0.2.2:8000/api/pasien/getProduk/";
        mVolleyService.getDataHeadersVolley("GETPRODUK", URI, headers);
    }



    void initVolleyCallback(){
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) throws JSONException {
                System.out.println(response);

                switch (requestType){
                    case "GETPRODUK":
                        ArrayList<ProdukModel> produkList = new ArrayList<>();
                        JSONArray data = response.getJSONArray("data");
                        for (int i =0; i<data.length(); i++){
                            JSONObject produk = data.getJSONObject(i);

                            String NamaProduk = produk.getString("nama_produk");
                            String Harga = produk.getString("harga_produk");

                            ProdukModel produkModel = new ProdukModel();

                            produkModel.setNamaProduk(NamaProduk);
                            produkModel.setHargaProduk(Harga);

                            produkList.add(produkModel);
                        }

                        ProdukAdapter produkAdapter = new ProdukAdapter(getApplicationContext(),produkList);
                        recyclerView.setAdapter(produkAdapter);
                        produkAdapter.setOnItemClickListener(ProdukActivity.this);

                }



            }

            @Override
            public void notifyError(String requestType, VolleyError error) {

            }
        };
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

    @Override
    public void onItemClick(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

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
}
