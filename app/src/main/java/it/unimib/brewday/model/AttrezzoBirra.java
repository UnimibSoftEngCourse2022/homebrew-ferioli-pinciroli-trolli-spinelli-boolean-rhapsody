package it.unimib.brewday.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(
        primaryKeys = {"idBirra", "idAttrezzo"},
        foreignKeys = {
                @ForeignKey(
                        entity = Birra.class,
                        parentColumns = "id",
                        childColumns = "idBirra",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Attrezzo.class,
                        parentColumns = "id",
                        childColumns = "idAttrezzo",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.RESTRICT
                )
        }
)
public class AttrezzoBirra {

    private long idBirra;
    private long idAttrezzo;

    public AttrezzoBirra(long idBirra, long idAttrezzo) {
        this.idBirra = idBirra;
        this.idAttrezzo = idAttrezzo;
    }

    public long getIdBirra() {
        return idBirra;
    }

    public void setIdBirra(long idBirra) {
        this.idBirra = idBirra;
    }

    public long getIdAttrezzo() {
        return idAttrezzo;
    }

    public void setIdAttrezzo(long idAttrezzo) {
        this.idAttrezzo = idAttrezzo;
    }
}
