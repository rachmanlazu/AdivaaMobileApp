package com.adivaa.rachmanlazuardi.adivaaskincareclinic.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class RekamMedisModel implements Parcelable {
    private String tanggal;
    private String perawatan;

    public RekamMedisModel(){
        this.tanggal = "";
        this.perawatan = "";
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setPerawatan(String perawatan) {
        this.perawatan = perawatan;
    }

    public String getPerawatan() {
        return perawatan;
    }

    protected RekamMedisModel(Parcel in){
        tanggal = in.readString();
        perawatan = in.readString();
    }

    public static final Creator<RekamMedisModel> CREATOR = new Creator<RekamMedisModel>(){
        @Override
        public RekamMedisModel createFromParcel(Parcel in) {
            return new RekamMedisModel(in);
        }

        @Override
        public RekamMedisModel[] newArray(int size) {
            return new RekamMedisModel[size];
        }
    };

    @Override
    public int describeContents(){ return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tanggal);
        dest.writeString(perawatan);
    }
}
