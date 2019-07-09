package com.adivaa.rachmanlazuardi.adivaaskincareclinic.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adivaa.rachmanlazuardi.adivaaskincareclinic.Model.PembelianProdukModel;
import com.adivaa.rachmanlazuardi.adivaaskincareclinic.R;

import java.util.ArrayList;

public class PembelianProdukAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private ArrayList<PembelianProdukModel> pembelianProdukList;

    public PembelianProdukAdapter(Context context, ArrayList pembelianProdukList){
        this.context = context;
        this.pembelianProdukList = pembelianProdukList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View viewItem = inflater.inflate(R.layout.item_pembelian_produk_fragment, viewGroup, false);
        viewHolder = new PembelianProdukViewHolder(viewItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final PembelianProdukViewHolder pembelianProdukViewHolder = (PembelianProdukViewHolder) viewHolder;
        PembelianProdukModel currentPembelianProduk = pembelianProdukList.get(i);

        pembelianProdukViewHolder.tanggal.setText(currentPembelianProduk.getTanggal());
        pembelianProdukViewHolder.produk.setText(currentPembelianProduk.getProduk());
        pembelianProdukViewHolder.jumlah.setText(currentPembelianProduk.getJumlah());
    }

    @Override
    public int getItemCount() {
        System.out.println(pembelianProdukList.size());
        return pembelianProdukList.size();
    }

    public class PembelianProdukViewHolder extends RecyclerView.ViewHolder{
        public TextView tanggal;
        public TextView produk;
        public TextView jumlah;

        public PembelianProdukViewHolder(@NonNull View itemView) {
            super(itemView);

            tanggal = itemView.findViewById(R.id.tglPembelian);
            produk = itemView.findViewById(R.id.namaProduk);
            jumlah = itemView.findViewById(R.id.jumlahProduk);
        }
    }
}
