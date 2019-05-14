package com.adivaa.rachmanlazuardi.adivaaskincareclinic;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ProdukAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<ProdukModel> produkList;
    private OnItemClickListener mListener;

    public ProdukAdapter(Context context, ArrayList produkList){
        this.context = context;
        this.produkList = produkList;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {this.mListener = listener;}

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View viewItem = inflater.inflate(R.layout.activity_item_produk, viewGroup, false);
        viewHolder = new ProdukViewHolder(viewItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            final ProdukViewHolder produkViewHolder = (ProdukViewHolder) viewHolder;
            ProdukModel currentProduk = produkList.get(i);

            produkViewHolder.namaProduk.setText(currentProduk.getNamaProduk());
            produkViewHolder.hargaProduk.setText(currentProduk.getHargaProduk());


    }

    @Override
    public int getItemCount() {
        return produkList.size();
    }

//    public interface OnItemClickListener{
//        void onItemClick(int position);
//    }

    //public void setOnItemClickListener(OnItemClickListener listener) {this.mListener = listener; }

    public class ProdukViewHolder extends RecyclerView.ViewHolder{
        public TextView namaProduk;
        public TextView hargaProduk;
        public Button pesan;

        public ProdukViewHolder(@NonNull View itemView) {
            super(itemView);

            namaProduk = itemView.findViewById(R.id.namaProduk);
            hargaProduk = itemView.findViewById(R.id.hargaProduk);
            pesan = itemView.findViewById(R.id.buttonPesan);

            pesan.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    if (mListener != null){
                        int position = getAdapterPosition();
                        mListener.onItemClick(position);
                    }
                }
            });
        }
    }
}
