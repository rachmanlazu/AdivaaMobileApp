package com.adivaa.rachmanlazuardi.adivaaskincareclinic.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.adivaa.rachmanlazuardi.adivaaskincareclinic.IResult;
import com.adivaa.rachmanlazuardi.adivaaskincareclinic.R;
import com.adivaa.rachmanlazuardi.adivaaskincareclinic.Service.VolleyService;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ProfileFragment extends Fragment {
    private Button btnEdit;
    private EditText nama, noTelp, alergi, alamat;
    private TextView noMember;

    private String namaPasien;
    private String noTelpPasien;
    private String alergiPasien;
    private String alamatPasien;
    private String noMemberPasien;
    private String token;

    private IResult mResultCallback = null;
    private VolleyService mVolleyService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //token get
        SharedPreferences sharedpref = this.getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);
        token = sharedpref.getString("token", "defaultValue");

        btnEdit = view.findViewById(R.id.buttonProfile);

        nama = view.findViewById(R.id.namaPasien);
        noTelp = view.findViewById(R.id.noTelp);
        alergi = view.findViewById(R.id.alergi);
        alamat = view.findViewById(R.id.alamat);
        noMember = view.findViewById(R.id.noMember);

        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback, getContext());

        getData();

        btnEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(getActivity(), );
//                startActivity(i);
                namaPasien = nama.getText().toString();
                noTelpPasien = noTelp.getText().toString();
                alergiPasien = alergi.getText().toString();
                alamatPasien = alamat.getText().toString();
                noMemberPasien = noMember.getText().toString();

                HashMap params = new HashMap<String, String>();
                params.put("nama", namaPasien);
                params.put("no_telp", noTelpPasien);
                params.put("alergi", alergiPasien);
                params.put("no_member", noMemberPasien);
                params.put("tempat_tinggal", alamatPasien);

                HashMap headers = new HashMap();
                headers.put("Authorization", "Bearer " + token);
                headers.put("X-HTTP-Method-Override","PATCH");
                String URI = "http://10.0.2.2:8000/api/pasien/setDetail";

                mVolleyService.postDataHeadersVolley("PUTPROFILE", URI, params, headers);
            }
        });


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
                        String NoTelp = data.getString("no_telp");
                        String Alergi = data.getString("alergi");
                        String Alamat = data.getString("tempat_tinggal");

                        nama.setText(Nama);
                        noMember.setText(NoMember);
                        noTelp.setText(NoTelp);
                        alergi.setText(Alergi);
                        alamat.setText(Alamat);
                        break;

                    case "PUTPROFILE":
                        break;
                }



            }

            @Override
            public void notifyError(String requestType, VolleyError error) {

            }
        };
    }

}
