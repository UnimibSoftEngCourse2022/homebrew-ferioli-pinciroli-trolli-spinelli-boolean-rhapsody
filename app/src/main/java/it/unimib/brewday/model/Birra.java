package it.unimib.brewday.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = Ricetta.class,
                parentColumns = "id",
                childColumns = "idRicetta",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        )
})
public class Birra implements Parcelable {

    @PrimaryKey(autoGenerate = true) private long id;
    private boolean terminata;
    private String dataTerminazione;
    private int litriProdotti;
    @NonNull private long idRicetta;
    private String notaGenerale;


    public Birra(int litriProdotti, long idRicetta) {
        this.idRicetta = idRicetta;
        this.litriProdotti = litriProdotti;
        terminata = false;
        notaGenerale = "";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isTerminata() {
        return terminata;
    }

    public void setTerminata(boolean terminata) {
        this.terminata = terminata;
    }

    public int getLitriProdotti() {
        return litriProdotti;
    }

    public void setLitriProdotti(int litriProdotti) {
        this.litriProdotti = litriProdotti;
    }

    public long getIdRicetta() {
        return idRicetta;
    }

    public void setIdRicetta(long idRicetta) {
        this.idRicetta = idRicetta;
    }

    public String getDataTerminazione() {
        return dataTerminazione;
    }

    public void setDataTerminazione(String dataTerminazione) {
        this.dataTerminazione = dataTerminazione;
    }

    public String getNotaGenerale() {
        return notaGenerale;
    }

    public void setNotaGenerale(String notaGenerale) {
        this.notaGenerale = notaGenerale;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeByte(this.terminata ? (byte) 1 : (byte) 0);
        dest.writeString(this.dataTerminazione);
        dest.writeInt(this.litriProdotti);
        dest.writeLong(this.idRicetta);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readLong();
        this.terminata = source.readByte() != 0;
        this.dataTerminazione = source.readString();
        this.litriProdotti = source.readInt();
        this.idRicetta = source.readLong();
    }

    protected Birra(Parcel in) {
        this.id = in.readLong();
        this.terminata = in.readByte() != 0;
        this.dataTerminazione = in.readString();
        this.litriProdotti = in.readInt();
        this.idRicetta = in.readLong();
    }

    public static final Creator<Birra> CREATOR = new Creator<Birra>() {
        @Override
        public Birra createFromParcel(Parcel source) {
            return new Birra(source);
        }

        @Override
        public Birra[] newArray(int size) {
            return new Birra[size];
        }
    };
}
