package com.adivaa.rachmanlazuardi.adivaaskincareclinic;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class NavHeaderFragment extends Fragment {
    private TextView nama, noMember;

    private String namaPasien;
    private String noMemberPasien;
    private String token;

    private IResult mResultCallback = null;
    private VolleyService mVolleyService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nav_header, container, false);

        //token get
        SharedPreferences sharedpref = this.getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);
        token = sharedpref.getString("token", "defaultValue");

        nama = view.findViewById(R.id.namaPasien);
        noMember = view.findViewById(R.id.noMember);

        namaPasien = nama.getText().toString();
        noMemberPasien = noMember.getText().toString();

        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback, getContext());

        getData();

        return view;
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
                }



            }

            @Override
            public void notifyError(String requestType, VolleyError error) {

            }
        };
    }
}
