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
import com.adivaa.rachmanlazuardi.adivaaskincareclinic.R;
import com.adivaa.rachmanlazuardi.adivaaskincareclinic.Adapter.RekamMedisAdapter;
import com.adivaa.rachmanlazuardi.adivaaskincareclinic.Model.RekamMedisModel;
import com.adivaa.rachmanlazuardi.adivaaskincareclinic.Service.VolleyService;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class RekamMedisFragment extends Fragment {
    private static final String TAG = "RekamMedisFragment";

    private RecyclerView recyclerView;

    private TextView tanggal, perawatan;

    private String token;

    private IResult mResultCallback = null;
    private VolleyService mVolleyService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rekam_medis_fragment,container,false);

        recyclerView = view.findViewById(R.id.list_rekam_medis);



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
                            String Perawatan = rekamMedis.getString("perawatan");

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
}
