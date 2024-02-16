package it.unimib.brewday.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = Ricetta.class,
                parentColumns = "id",
                childColumns = "idRicetta",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        )
})
public class Birra {

    @PrimaryKey(autoGenerate = true) private long id;
    private boolean terminata;
    private String dataTerminazione;
    private int litriProdotti;
    @NonNull private long idRicetta;

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
}
