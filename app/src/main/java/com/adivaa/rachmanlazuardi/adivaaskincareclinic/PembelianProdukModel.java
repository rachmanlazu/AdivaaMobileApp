package com.adivaa.rachmanlazuardi.adivaaskincareclinic;

import android.os.Parcel;
import android.os.Parcelable;

public class PembelianProdukModel implements Parcelable {
    private String tanggal;
    private String produk;
    private String jumlah;

    public PembelianProdukModel(){
        this.tanggal = "";
        this.produk = "";
        this.jumlah = "";
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setProduk(String produk) {
        this.produk = produk;
    }

    public String getProduk() {
        return produk;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getJumlah() {
        return jumlah;
    }

    protected PembelianProdukModel(Parcel in){
        tanggal = in.readString();
        produk = in.readString();
        jumlah = in.readString();
    }

    public static final Creator<PembelianProdukModel> CREATOR = new Creator<PembelianProdukModel>(){
        @Override
        public PembelianProdukModel createFromParcel(Parcel in) {
            return new PembelianProdukModel(in);
        }

        @Override
        public PembelianProdukModel[] newArray(int size) {
            return new PembelianProdukModel[size];
        }
    };

    @Override
    public int describeContents(){ return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tanggal);
        dest.writeString(produk);
        dest.writeString(jumlah);
    }
}
