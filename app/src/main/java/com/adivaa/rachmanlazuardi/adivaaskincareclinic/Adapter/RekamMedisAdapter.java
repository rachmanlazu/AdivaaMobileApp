package com.adivaa.rachmanlazuardi.adivaaskincareclinic.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adivaa.rachmanlazuardi.adivaaskincareclinic.Model.RekamMedisModel;
import com.adivaa.rachmanlazuardi.adivaaskincareclinic.R;

import java.util.ArrayList;

public class RekamMedisAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private ArrayList<RekamMedisModel> rekamMedisList;

    public RekamMedisAdapter(Context context, ArrayList rekamMedisList){
        this.context = context;
        this.rekamMedisList = rekamMedisList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View viewItem = inflater.inflate(R.layout.item_rekam_medis_fragment, viewGroup, false);
        viewHolder = new RekamMedisViewHolder(viewItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final RekamMedisViewHolder rekamMedisViewHolder = (RekamMedisViewHolder) viewHolder;
        RekamMedisModel currentRekamMedis = rekamMedisList.get(i);

        rekamMedisViewHolder.tanggal.setText(currentRekamMedis.getTanggal());
        rekamMedisViewHolder.perawatan.setText(currentRekamMedis.getPerawatan());
    }

    @Override
    public int getItemCount() {
        return rekamMedisList.size();
    }

    public class RekamMedisViewHolder extends RecyclerView.ViewHolder{
        public TextView tanggal;
        public TextView perawatan;

        public RekamMedisViewHolder(@NonNull View itemView) {
            super(itemView);

            tanggal = itemView.findViewById(R.id.tglPeriksa);
            perawatan = itemView.findViewById(R.id.jenisPerawatan);
        }
    }
}
