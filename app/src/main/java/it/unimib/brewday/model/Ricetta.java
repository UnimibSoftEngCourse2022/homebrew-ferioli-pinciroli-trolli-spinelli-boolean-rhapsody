package it.unimib.brewday.model;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Ricetta implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String nome;
    private int litriDiRiferimento;


    public Ricetta(String nome, int litriDiRiferimento){
        this.nome = nome;
        this.litriDiRiferimento = litriDiRiferimento;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getLitriDiRiferimento() {
        return litriDiRiferimento;
    }

    public void setLitriDiRiferimento(int litriDiRiferimento) {
        this.litriDiRiferimento = litriDiRiferimento;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.nome);
        dest.writeInt(this.litriDiRiferimento);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readLong();
        this.nome = source.readString();
        this.litriDiRiferimento = source.readInt();
    }

    protected Ricetta(Parcel in) {
        this.id = in.readLong();
        this.nome = in.readString();
        this.litriDiRiferimento = in.readInt();
    }

    public static final Parcelable.Creator<Ricetta> CREATOR = new Parcelable.Creator<Ricetta>() {
        @Override
        public Ricetta createFromParcel(Parcel source) {
            return new Ricetta(source);
        }

        @Override
        public Ricetta[] newArray(int size) {
            return new Ricetta[size];
        }
    };
}