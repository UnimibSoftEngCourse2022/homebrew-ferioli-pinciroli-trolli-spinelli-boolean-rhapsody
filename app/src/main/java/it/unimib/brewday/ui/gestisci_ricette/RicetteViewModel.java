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
import it.unimib.brewday.repository.IngredientiRepository;
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

    private  final IngredientiRepository ingredientiRepository;

    public RicetteViewModel(RicetteRepository ricetteRepository, IngredientiRepository ingredientiRepository){
        this.ricetteRepository = ricetteRepository;
        this.ingredientiRepository = ingredientiRepository;
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
        ricetteRepository.readAllRicette(ricetteRisultato::postValue);
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
        ricetteRepository.readIngredientiRicetta(idRicetta, ingredientiRicetteRisultato::postValue);
    }

    public void getDifferenzaIngredienti(long idRicetta, int litriBirraScelti){

        ricetteRepository.readIngredientiRicetta(idRicetta, risultatoIngredientiRicetta -> {
            if(risultatoIngredientiRicetta.isSuccessful()){
                List<IngredienteRicetta> listaIngredientiRicetta = ((Risultato.ListaIngredientiDellaRicettaSuccesso) risultatoIngredientiRicetta).getListaIngrediente();
                setDosaggioDaIngredienteRicetta(litriBirraScelti , listaIngredientiRicetta);
                ingredientiRicettaPerLitriRisultato.postValue(new Risultato.ListaIngredientiDellaRicettaSuccesso(listaIngredientiRicetta));

                ingredientiRepository.readAllIngredienti(risultatoIngredienti -> {
                    if(risultatoIngredienti.isSuccessful()){
                        List<Ingrediente> listaIngredientiDisponibili = ((Risultato.ListaIngredientiSuccesso) risultatoIngredienti).getData();
                        List<Integer> listaDifferenzaIngredienti = new ArrayList<>();
                        calcolaDifferenzaIngredienti(listaIngredientiDisponibili,listaIngredientiRicetta ,listaDifferenzaIngredienti );
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
    public LiveData<Risultato> getDifferenzaIngredientiRisultato(){
        return  differenzaIngredientiRisultato;
    }
    public LiveData<Risultato> getIngredientiRicettaPerLitriRisultato(){
        return  ingredientiRicettaPerLitriRisultato;
    }
    private void setDosaggioDaIngredienteRicetta(int litriBirraScelti, List<IngredienteRicetta> listaIngredientiRicetta ){
        for (IngredienteRicetta ingredienteRicetta : listaIngredientiRicetta) {
            if (ingredienteRicetta.getTipoIngrediente().equals(TipoIngrediente.ACQUA)) {
                ingredienteRicetta.setDosaggioIngrediente(round(ingredienteRicetta.getDosaggioIngrediente() * litriBirraScelti, 1));
            } else {
                ingredienteRicetta.setDosaggioIngrediente(Math.round(ingredienteRicetta.getDosaggioIngrediente() * litriBirraScelti));
            }
        }
    }

    private void calcolaDifferenzaIngredienti(List<Ingrediente> listaIngredientiDisponibili, List<IngredienteRicetta> listaIngredientiBirra, List<Integer> listaDifferenzaIngredienti){
      //  possiedeIngredienti = true;
        for(int i=0; i < listaIngredientiBirra.size(); i++){
            int differenza =  listaIngredientiDisponibili.get(i).getQuantitaPosseduta() - ((int) Math.round(listaIngredientiBirra.get(i).getDosaggioIngrediente()));
           /* if (differenza < 0){
                possiedeIngredienti = false;
            }*/
            listaDifferenzaIngredienti.add(differenza);
        }
    }

    private static double round(double n, int decimals) {
        return Math.floor(n * Math.pow(10, decimals)) / Math.pow(10, decimals);
    }
}
