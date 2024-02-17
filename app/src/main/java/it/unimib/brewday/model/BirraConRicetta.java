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
        dest.writeLong(this.getId());
        dest.writeByte(this.isTerminata() ? (byte) 1 : (byte) 0);
        dest.writeString(this.getDataTerminazione());
        dest.writeInt(this.getLitriProdotti());
        dest.writeLong(this.getIdRicetta());
        dest.writeString(this.getNotaGenerale());
    }

    public void readFromParcel(Parcel source) {
        super.readFromParcel(source);
        this.nomeRicetta = source.readString();
        this.setId(source.readLong());
        this.setTerminata(source.readByte() != 0);
        this.setDataTerminazione(source.readString());
        this.setLitriProdotti(source.readInt());
        this.setIdRicetta(source.readLong());
        this.setNotaGenerale(source.readString());
    }

    protected BirraConRicetta(Parcel in) {
        super(in);
        this.nomeRicetta = in.readString();
        this.setId(in.readLong());
        this.setTerminata(in.readByte() != 0);
        this.setDataTerminazione(in.readString());
        this.setLitriProdotti(in.readInt());
        this.setIdRicetta(in.readLong());
        this.setNotaGenerale(in.readString());
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
