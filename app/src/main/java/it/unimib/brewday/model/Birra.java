package it.unimib.brewday.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
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

    Nota notaGenerale;
    List<NotaDegustazione> listaDiNote;

    public Birra(int litriProdotti, long idRicetta) {
        this.idRicetta = idRicetta;
        this.litriProdotti = litriProdotti;
        terminata = false;
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

    public Nota getNotaGenerale() {
        return notaGenerale;
    }

    public void setNotaGenerale(Nota notaGenerale) {
        this.notaGenerale = notaGenerale;
    }

    public List<NotaDegustazione> getListaDiNote() {
        return listaDiNote;
    }

    public void setListaDiNote(List<NotaDegustazione> listaDiNote) {
        this.listaDiNote = listaDiNote;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {

    }


}
