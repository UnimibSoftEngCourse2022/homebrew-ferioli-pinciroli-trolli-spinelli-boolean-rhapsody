package it.unimib.brewday.util;

public class RegistroErrori {
    private RegistroErrori(){}

    public static final String RICETTA_FETCH_ERROR = "ricetta_fetch_failed";
    public static final String RICETTA_CREATION_ERROR = "ricetta_creation_failed";
    public static final String RICETTA_UPDATE_ERROR = "ricetta_update_failed";
    public static final String RICETTA_DELETION_ERROR = "ricetta_deletion_failed";
    public static final String INGREDIENTI_FETCH_ERROR = "ingredienti_fetch_failed";
    public static final String INGREDIENTI_UPDATE_ERROR = "ingredienti_update_failed";
    public static final String BIRRE_FETCH_ERROR = "birre_fetch_failed";
    public static final String BIRRE_CREATION_ERROR = "birre_creation_failed";
    public static final String BIRRE_UPDATE_ERROR = "birre_update_failed";
    public static final String BIRRE_DELETE_ERROR = "birre_delete_failed";
    public static final String ATTREZZI_FETCH_ERROR = "Errore nella lettura degli attrezzi";
    public static final String ATTREZZI_CREATION_ERROR = "Errore nell'inserimento degli attrezzi";
    public static final String ATTREZZI_UPDATE_ERROR = "Errore nell'aggiornamento dei dati";
    public static final String ATTREZZI_DELETE_ERROR = "Errore nella cancellazione dei dati";
}
