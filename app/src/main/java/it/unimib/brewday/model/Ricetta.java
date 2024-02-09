package it.unimib.brewday.model;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Ricetta {

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
}