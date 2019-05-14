package com.adivaa.rachmanlazuardi.adivaaskincareclinic;

import android.os.Parcel;
import android.os.Parcelable;

public class ProdukModel implements Parcelable {
    private String namaProduk;
    private String hargaProduk;

    public ProdukModel() {
        this.namaProduk = "";
        this.hargaProduk = "";
    }

    public void setNamaProduk(String namaProduk) {this.namaProduk = namaProduk;}

    public String getNamaProduk() {return namaProduk;}

    public void setHargaProduk(String hargaProduk) {this.hargaProduk = hargaProduk;}

    public String getHargaProduk() {return hargaProduk;}

    protected ProdukModel(Parcel in) {
        namaProduk = in.readString();
        hargaProduk = in.readString();
    }

    public static final Creator<ProdukModel> CREATOR = new Creator<ProdukModel>() {
        @Override
        public ProdukModel createFromParcel(Parcel in) {
            return new ProdukModel(in);
        }

        @Override
        public ProdukModel[] newArray(int size) {
            return new ProdukModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(namaProduk);
        dest.writeString(hargaProduk);
    }

}

