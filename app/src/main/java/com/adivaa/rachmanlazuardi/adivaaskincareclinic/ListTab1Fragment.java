package com.adivaa.rachmanlazuardi.adivaaskincareclinic;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ListTab1Fragment extends Fragment {

    private RecyclerView recyclerView;

    private TextView tanggal, perawatan;

    private String token;

    private IResult mResultCallback = null;
    private VolleyService mVolleyService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_tab1_fragment, container, false);

        recyclerView = view.findViewById(R.id.list_rekam_medis);

        //token get
        SharedPreferences sharedpref = this.getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);
        token = sharedpref.getString("token", "defaultValue");
        Log.d("Produk", "token produk " +token);
        //

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback, getContext());

        getData();

        return view;
    }

    public void getData(){
        HashMap headers = new HashMap();
        headers.put("Authorization", "Bearer " + token);

        String URI = "http://10.0.2.2:8000/api/pasien/getRekamMedis";
        mVolleyService.getDataHeadersVolley("GETREKAMMEDIS", URI, headers);
    }

    void initVolleyCallback(){
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) throws JSONException {
                System.out.println(response);

                switch (requestType){
                    case "GETREKAMMEDIS":
                        ArrayList<RekamMedisModel> rekamMedisList = new ArrayList<>();
                        JSONArray data = response.getJSONArray("data");
                        for (int i =0; i<data.length(); i++){
                            JSONObject rekamMedis = data.getJSONObject(i);

                            String Tanggal = rekamMedis.getString("tanggal");
                            String Perawatan = rekamMedis.getString("nama_perawatan");

                            RekamMedisModel rekamMedisModel = new RekamMedisModel();

                            rekamMedisModel.setTanggal(Tanggal);
                            rekamMedisModel.setPerawatan(Perawatan);

                            rekamMedisList.add(rekamMedisModel);
                        }

                        RekamMedisAdapter rekamMedisAdapter = new RekamMedisAdapter(getContext(),rekamMedisList);
                        recyclerView.setAdapter(rekamMedisAdapter);
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
