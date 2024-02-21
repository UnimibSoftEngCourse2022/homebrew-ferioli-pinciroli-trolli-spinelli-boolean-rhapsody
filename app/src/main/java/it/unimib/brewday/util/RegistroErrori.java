package it.unimib.brewday.util;

import java.util.HashMap;
import java.util.Map;

import it.unimib.brewday.R;

public class RegistroErrori {

    private Map<String, Integer> mappaErrori;
    private static RegistroErrori instance;
    private RegistroErrori(){
        mappaErrori = new HashMap<>();
        mappaErrori.put(RICETTA_FETCH_ERROR, R.string.ricetta_fetch_failure);
        mappaErrori.put(RICETTA_CREATION_ERROR, R.string.ricetta_creation_failure);
        mappaErrori.put(RICETTA_UPDATE_ERROR, R.string.ricetta_update_failure);
        mappaErrori.put(RICETTA_DELETION_ERROR, R.string.ricetta_deletion_failure);
        mappaErrori.put(INGREDIENTI_FETCH_ERROR, R.string.ingredienti_fetch_failure);
        mappaErrori.put(INGREDIENTI_UPDATE_ERROR, R.string.ingredienti_update_failure);
        mappaErrori.put(BIRRE_FETCH_ERROR, R.string.birre_fetch_failure);
        mappaErrori.put(BIRRE_CREATION_ERROR, R.string.birre_creation_failure);
        mappaErrori.put(BIRRE_UPDATE_ERROR, R.string.birre_update_failure);
        mappaErrori.put(BIRRE_DELETE_ERROR, R.string.birre_deletion_failure);
        mappaErrori.put(ATTREZZI_FETCH_ERROR, R.string.attrezzi_fetch_failure);
        mappaErrori.put(ATTREZZI_CREATION_ERROR, R.string.attrezzi_creation_failure);
        mappaErrori.put(ATTREZZI_UPDATE_ERROR, R.string.attrezzi_update_failure);
        mappaErrori.put(ATTREZZI_DELETE_ERROR, R.string.attrezzi_deletion_failure);
        mappaErrori.put(ATTREZZO_TIPOLOGIA_MANCANTE, R.string.attrezzo_tipologia_mancante);
        mappaErrori.put(ATTREZZO_IN_USO, R.string.attrezzo_in_uso);
        mappaErrori.put(NOTA_CREATION_ERROR, R.string.nota_creation_failure);
        mappaErrori.put(NOTA_FETCH_ERROR, R.string.nota_fetch_failure);
    }

    public static RegistroErrori getInstance() {
        if (instance == null) {
            instance = new RegistroErrori();
        }
        return instance;
    }

    public int getErrore(String stringaErrore){
        if(mappaErrori.containsKey(stringaErrore)){
            return mappaErrori.get(stringaErrore);
        }
        return 0;
    }

    public static final String RICETTA_FETCH_ERROR = "ricetta_fetch_failure";
    public static final String RICETTA_CREATION_ERROR = "ricetta_creation_failure";
    public static final String RICETTA_UPDATE_ERROR = "ricetta_update_failure";
    public static final String RICETTA_DELETION_ERROR = "ricetta_deletion_failure";
    public static final String INGREDIENTI_FETCH_ERROR = "ingredienti_fetch_failure";
    public static final String INGREDIENTI_UPDATE_ERROR = "ingredienti_update_failure";
    public static final String BIRRE_FETCH_ERROR = "birre_fetch_failure";
    public static final String BIRRE_CREATION_ERROR = "birre_creation_failure";
    public static final String BIRRE_UPDATE_ERROR = "birre_update_failure";
    public static final String BIRRE_DELETE_ERROR = "birre_deletion_failure";
    public static final String ATTREZZI_FETCH_ERROR = "attrezzi_fetch_failure";
    public static final String ATTREZZI_CREATION_ERROR = "attrezzi_creation_failure";
    public static final String ATTREZZI_UPDATE_ERROR = "attrezzi_update_failure";
    public static final String ATTREZZI_DELETE_ERROR = "attrezzi_deletion_failure";
    public static final String ATTREZZO_TIPOLOGIA_MANCANTE = "attrezzo_tipologia_mancante";
    public static final String ATTREZZO_IN_USO = "attrezzo_in_uso";
    public static final String NOTA_CREATION_ERROR = "nota_creation_failure";
    public static final String NOTA_FETCH_ERROR = "nota_fetch_failure";



}
