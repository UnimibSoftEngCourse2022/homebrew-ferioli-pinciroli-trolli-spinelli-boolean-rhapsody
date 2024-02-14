package it.unimib.brewday.ui.gestisci_ricette;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import it.unimib.brewday.model.Ingrediente;
import it.unimib.brewday.model.IngredienteRicetta;
import it.unimib.brewday.model.Ricetta;
import it.unimib.brewday.model.Risultato;
import it.unimib.brewday.model.TipoIngrediente;
import it.unimib.brewday.repository.IngredienteRepository;
import it.unimib.brewday.repository.RicetteRepository;

public class RicetteViewModel extends ViewModel {

    private final MutableLiveData<Risultato> ricetteRisultato;
    private final MutableLiveData<Risultato> ingredientiRicetteRisultato;
    private final MutableLiveData<Risultato> insertRicettaRisultato;
    private final MutableLiveData<Risultato> updateRicettaRisultato;
    private final MutableLiveData<Risultato> deleteRicettaRisultato;

    private final MutableLiveData<Risultato> differenzaIngredientiRisultato;

    private final MutableLiveData<Risultato> ingredientiRicettaPerLitriRisultato;
    private final MutableLiveData<Risultato> updateIngredientiRicettaRisultato;


    private final RicetteRepository ricetteRepository;

    private  final IngredienteRepository ingredienteRepository;

    public RicetteViewModel(RicetteRepository ricetteRepository, IngredienteRepository ingredienteRepository){
        this.ricetteRepository = ricetteRepository;
        this.ingredienteRepository = ingredienteRepository;
        ricetteRisultato = new MutableLiveData<>();
        insertRicettaRisultato = new MutableLiveData<>();
        deleteRicettaRisultato = new MutableLiveData<>();
        updateRicettaRisultato = new MutableLiveData<>();
        updateIngredientiRicettaRisultato = new MutableLiveData<>();
        ingredientiRicetteRisultato = new MutableLiveData<>();
        differenzaIngredientiRisultato = new MutableLiveData<>();
        ingredientiRicettaPerLitriRisultato = new MutableLiveData<>();
    }

    public void getAllRicette() {
        ricetteRepository.getRicette(ricetteRisultato::postValue);
    }

    public void insertRicetta(Ricetta ricetta, List<IngredienteRicetta> listaIngredienti) {
        ricetteRepository.insertRicetta(ricetta, listaIngredienti, insertRicettaRisultato::postValue);
    }

    public void deleteRicetta(Ricetta ricetta) {
        ricetteRepository.deleteRicetta(ricetta, deleteRicettaRisultato::postValue);
    }

    public void updateRicetta(Ricetta ricetta) {
        ricetteRepository.updateRicetta(ricetta, updateRicettaRisultato::postValue);
    }

    public void updateIngredientiRicetta(List<IngredienteRicetta> ingredientiRicetta) {
        ricetteRepository.updateIngredientiRicetta(ingredientiRicetta, updateIngredientiRicettaRisultato::postValue);
    }

    public void getIngredientiRicetta(long idRicetta) {
        ricetteRepository.getIngredientiRicetta(idRicetta, ingredientiRicetteRisultato::postValue);
    }

    public void getDifferenzaIngredienti(long idRicetta, int litriBirraScelti){

            ricetteRepository.getIngredientiRicetta(idRicetta, risultato -> {
                if(risultato.isSuccessful()){
                    List<IngredienteRicetta> listaIngredientiBirra = ((Risultato.ListaIngredientiDellaRicettaSuccesso) risultato).getListaIngrediente();
                    setDosaggioDaIngredienteRicetta(litriBirraScelti , listaIngredientiBirra );
                    ingredientiRicettaPerLitriRisultato.postValue(new Risultato.ListaIngredientiDellaRicettaSuccesso(listaIngredientiBirra));
                    ingredienteRepository.readAllIngredienti(risultato1 -> {
                        if(risultato1.isSuccessful()){
                            List<Ingrediente> listaIngredientiDisponibili = ((Risultato.IngredientiSuccesso) risultato1).getData();
                            List<Integer> listaDifferenzaIngredienti = new ArrayList<>();
                            calcolaDifferenzaIngredienti(listaIngredientiDisponibili,listaIngredientiBirra ,listaDifferenzaIngredienti );
                            differenzaIngredientiRisultato.postValue(new Risultato.ListaDifferenzaIngredientiSuccesso(listaDifferenzaIngredienti));

                    }
                    });
                }

            });

    }

    public LiveData<Risultato> getRicetteRisultato() {
        return ricetteRisultato;
    }

    public LiveData<Risultato> getIngredientiRicetteRisultato() {
        return ingredientiRicetteRisultato;
    }

    public LiveData<Risultato> getInsertRicettaRisultato() {
        return insertRicettaRisultato;
    }

    public LiveData<Risultato> getUpdateRicettaRisultato() {
        return updateRicettaRisultato;
    }

    public LiveData<Risultato> getUpdateIngredientiRicettaRisultato() {
        return updateIngredientiRicettaRisultato;
    }

    public LiveData<Risultato> getDeleteRicettaRisultato() {
        return deleteRicettaRisultato;
    }

    public void setDosaggioDaIngredienteRicetta(int litriBirraScelti, List<IngredienteRicetta> listaIngredientiBirra ){
        for (IngredienteRicetta ingredienteRicetta : listaIngredientiBirra) {
            if (ingredienteRicetta.getTipoIngrediente().equals(TipoIngrediente.ACQUA)) {
                ingredienteRicetta.setDosaggioIngrediente(round(ingredienteRicetta.getDosaggioIngrediente() * litriBirraScelti, 1));
            } else {
                ingredienteRicetta.setDosaggioIngrediente(Math.round(ingredienteRicetta.getDosaggioIngrediente() * litriBirraScelti));
            }
        }
    }

    public void calcolaDifferenzaIngredienti(List<Ingrediente> listaIngredientiDisponibili, List<IngredienteRicetta> listaIngredientiBirra, List<Integer> listaDifferenzaIngredienti){
      //  possiedeIngredienti = true;
        for(int i=0; i < listaIngredientiBirra.size(); i++){
            int differenza =  listaIngredientiDisponibili.get(i).getQuantitaPosseduta() - ((int) Math.round(listaIngredientiBirra.get(i).getDosaggioIngrediente()));
           /* if (differenza < 0){
                possiedeIngredienti = false;
            }*/
            listaDifferenzaIngredienti.add(differenza);
        }
    }

    public static double round(double n, int decimals) {
        return Math.floor(n * Math.pow(10, decimals)) / Math.pow(10, decimals);
    }
}
