package com.adivaa.rachmanlazuardi.adivaaskincareclinic.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adivaa.rachmanlazuardi.adivaaskincareclinic.IResult;
import com.adivaa.rachmanlazuardi.adivaaskincareclinic.Adapter.PembelianProdukAdapter;
import com.adivaa.rachmanlazuardi.adivaaskincareclinic.Model.PembelianProdukModel;
import com.adivaa.rachmanlazuardi.adivaaskincareclinic.R;
import com.adivaa.rachmanlazuardi.adivaaskincareclinic.Service.VolleyService;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PembelianProdukFragment extends Fragment {
    private static final String TAG = "PembelianProdukFragment";

    private RecyclerView recyclerView;

    private TextView tanggal, produk, jumlah;

    private String token;

    private IResult mResultCallback = null;
    private VolleyService mVolleyService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pembelian_produk_fragment,container,false);

        recyclerView = view.findViewById(R.id.list_pembelian_produk);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback, getContext());

        getData();

        return view;
    }

    public void getData(){
        //token get
        SharedPreferences sharedpref = this.getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);
        token = sharedpref.getString("token", "defaultValue");
        Log.d("Produk", "token1 " +token);
        //

        HashMap headers = new HashMap();
        headers.put("Authorization", "Bearer " + token);

        String URI = "http://10.0.2.2:8000/api/pasien/getRiwayatPembelian";
        mVolleyService.getDataHeadersVolley("GETPEMBELIANPRODUK", URI, headers);
    }

    void initVolleyCallback(){
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) throws JSONException {
                System.out.println(response);

                switch (requestType){
                    case "GETPEMBELIANPRODUK":
                        ArrayList<PembelianProdukModel> pembelianProdukList = new ArrayList<>();
                        JSONArray data = response.getJSONArray("data");
                        for (int i =0; i<data.length(); i++){
                            JSONObject pembelianProduk = data.getJSONObject(i);

                            String Tanggal = pembelianProduk.getString("tanggal");
                            String Produk = pembelianProduk.getString("nama_produk");
                            String Jumlah = pembelianProduk.getString("jumlah");

                            PembelianProdukModel pembelianProdukModel = new PembelianProdukModel();

                            pembelianProdukModel.setTanggal(Tanggal);
                            pembelianProdukModel.setProduk(Produk);
                            pembelianProdukModel.setJumlah(Jumlah);

                            pembelianProdukList.add(pembelianProdukModel);
                        }

                        PembelianProdukAdapter pembelianProdukAdapter = new PembelianProdukAdapter(getContext(),pembelianProdukList);
                        recyclerView.setAdapter(pembelianProdukAdapter);
                }

            }

            @Override
            public void notifyError(String requestType, VolleyError error) {

            }
        };
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case android.R.id.home:
//                finish();
//                return true;
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

//    @Override
//    public void onItemClick(int position) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        builder.setCancelable(true);
//        builder.setTitle("Apakah anda ingin membeli produk tersebut?");
//        builder.setMessage("Jika Ya anda akan dihubungi oleh admin untuk informasi pemesanan selanjutnya");
//
//        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.cancel();
//            }
//        });
//
//        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.cancel();
//            }
//        });
//
//        builder.show();
//    }
}
