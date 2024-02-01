package com.example.brewday.ui;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.brewday.model.Attrezzo;
import com.example.brewday.model.Risultato;
import com.example.brewday.model.TipoAttrezzo;
import com.example.brewday.repository.AttrezziRepository;
import com.example.brewday.util.ServiceLocator;

public class GestisciAttrezziViewModel extends ViewModel {

    public MutableLiveData<Risultato> allAttrezzi;
    public MutableLiveData<Risultato> createAttrezzoResult;
    public MutableLiveData<Risultato> updateAttrezzoResult;
    public MutableLiveData<Risultato> deleteAttrezzoResult;

    private final AttrezziRepository attrezziRepository;

    public GestisciAttrezziViewModel(Context context) {

        allAttrezzi = new MutableLiveData<>();
        createAttrezzoResult = new MutableLiveData<>();
        updateAttrezzoResult = new MutableLiveData<>();
        deleteAttrezzoResult = new MutableLiveData<>();

        attrezziRepository = ServiceLocator.getInstance().getAttrezziRepository(context);
    }

    public void readAllAttrezzi() {
        attrezziRepository.readAllAttrezzi(result -> {
            allAttrezzi.postValue(result);
        });
    }

    public void createAttrezzo(String nome, TipoAttrezzo tipoAttrezzo, double capacita) {

        Attrezzo attrezzo = new Attrezzo();
        attrezzo.nome = nome;
        attrezzo.tipoAttrezzo = tipoAttrezzo;
        attrezzo.capacita = capacita;

        attrezziRepository.createAttrezzo(attrezzo, result -> {
            createAttrezzoResult.postValue(result);
            readAllAttrezzi();
        });
    }

    public void updateAttrezzo(Attrezzo nuovoAttrezzo) {
        attrezziRepository.updateAttrezzo(nuovoAttrezzo, result -> {
            updateAttrezzoResult.postValue(result);
            readAllAttrezzi();
        });
    }

    public void deleteAttrezzo(Attrezzo daCanellare) {
        attrezziRepository.deleteAttrezzo(daCanellare, result -> {
            deleteAttrezzoResult.postValue(result);
            readAllAttrezzi();
        });
    }
}