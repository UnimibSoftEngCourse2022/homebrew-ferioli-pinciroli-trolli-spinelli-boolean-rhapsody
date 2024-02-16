package it.unimib.brewday.model;

import android.os.Parcel;
import android.os.Parcelable;

public class BirraConRicetta extends Birra implements Parcelable {

    private String nomeRicetta;

    public BirraConRicetta(int litriProdotti, long idRicetta, String nomeRicetta) {
        super(litriProdotti, idRicetta);
        this.nomeRicetta = nomeRicetta;
    }

    public String getNomeRicetta() {
        return nomeRicetta;
    }

    public void setNomeRicetta(String nomeRicetta) {
        this.nomeRicetta = nomeRicetta;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.nomeRicetta);
    }

    public void readFromParcel(Parcel source) {
        this.nomeRicetta = source.readString();
    }

    protected BirraConRicetta(Parcel in) {
        super(in);
        this.nomeRicetta = in.readString();
    }

    public static final Creator<BirraConRicetta> CREATOR = new Creator<BirraConRicetta>() {
        @Override
        public BirraConRicetta createFromParcel(Parcel source) {
            return new BirraConRicetta(source);
        }

        @Override
        public BirraConRicetta[] newArray(int size) {
            return new BirraConRicetta[size];
        }
    };
}
