package com.adivaa.rachmanlazuardi.adivaaskincareclinic;

import android.os.Parcel;
import android.os.Parcelable;

public class AntrianModel implements Parcelable {
    private Integer noAntrianPasien;
    private String jamDatang;
    private Integer noAntrianSaatIni;

    public AntrianModel(){
        this.noAntrianPasien = 0;
        this.jamDatang = "";
        this.noAntrianSaatIni = 0;
    }

    public void setNoAntrianPasien(Integer noAntrianPasien) {
        this.noAntrianPasien = noAntrianPasien;
    }

    public Integer getNoAntrianPasien() {
        return noAntrianPasien;
    }

    public void setJamDatang(String jamDatang) {
        this.jamDatang = jamDatang;
    }

    public String getJamDatang() {
        return jamDatang;
    }

    public void setNoAntrianSaatIni(Integer noAntrianSaatIni) {
        this.noAntrianSaatIni = noAntrianSaatIni;
    }

    public Integer getNoAntrianSaatIni() {
        return noAntrianSaatIni;
    }

    protected AntrianModel(Parcel in){
        noAntrianPasien = in.readInt();
        jamDatang = in.readString();
        noAntrianSaatIni = in.readInt();
    }

    public static final Creator<AntrianModel> CREATOR = new Creator<AntrianModel>() {
        @Override
        public AntrianModel createFromParcel(Parcel in) {
            return new AntrianModel(in);
        }

        @Override
        public AntrianModel[] newArray(int size) {
            return new AntrianModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(noAntrianPasien);
        dest.writeString(jamDatang);
        dest.writeInt(noAntrianSaatIni);
    }
}
