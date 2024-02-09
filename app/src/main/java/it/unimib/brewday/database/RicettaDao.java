package it.unimib.brewday.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Upsert;

import java.util.List;

import it.unimib.brewday.model.IngredienteDellaRicetta;
import it.unimib.brewday.model.Ricetta;
import it.unimib.brewday.model.RicettaIngrediente;

@Dao
public interface RicettaDao {

    @Query(
            "SELECT ri.tipoIngrediente, ri.dosaggioIngrediente " +
                    "FROM ricetta r JOIN RicettaIngrediente ri ON r.id = ri.idRicetta " +
                    "JOIN ingrediente i ON ri.tipoIngrediente = i.tipo " +
                    "WHERE r.id = :idRicetta"
    )
    List<IngredienteDellaRicetta> getIngredientiDellaRicetta(int idRicetta);

    @Insert
    long insertRicetta(Ricetta ricetta);

    @Upsert
    long upsertRicettaIngrediente(RicettaIngrediente ricettaIngrediente);
}
