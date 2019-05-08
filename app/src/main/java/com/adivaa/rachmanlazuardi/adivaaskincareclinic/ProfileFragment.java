package com.adivaa.rachmanlazuardi.adivaaskincareclinic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ProfileFragment extends Fragment {
    //private Button btnEdit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //btnEdit = view.findViewById(R.id.buttonProfile);

//        btnEdit.setOnClickListener(new View.OnClickListener(){
////            @Override
////            public void onClick(View v) {
////                Intent i = new Intent(getActivity(), );
////                startActivity(i);
////            }
////        });

        return view;
    }
}
