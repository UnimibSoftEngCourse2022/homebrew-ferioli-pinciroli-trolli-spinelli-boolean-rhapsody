package it.unimib.brewday.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import it.unimib.brewday.model.AttrezzoBirra;
import it.unimib.brewday.model.Birra;
import it.unimib.brewday.model.BirraConRicetta;

@Dao
public interface BirraDao {

    @Insert
    long insertBirra(Birra birra);

    @Query("SELECT B.id, B.litriProdotti, B.idRicetta, B.terminata, B.dataTerminazione, B.notaGenerale, " +
            "R.nome AS nomeRicetta, AVG(ND.recensione) AS mediaRecensione " +
            "FROM birra AS B " +
            "JOIN Ricetta AS R ON B.idRicetta = R.id " +
            "LEFT JOIN notadegustazione AS ND ON B.id = ND.idBirra " +
            "GROUP BY B.id " +
            "ORDER BY B.terminata ASC, B.dataTerminazione DESC ")
    List<BirraConRicetta> getAllBirre();




    @Update
    int updateBirra(Birra birra);

    @Insert
    long[] insertBirraAttrezzi(List<AttrezzoBirra> listaAttrezziBirra);

    @Query("DELETE FROM AttrezzoBirra WHERE idBirra = :idBirra")
    int deleteBirraAttrezzi(long idBirra);
}
