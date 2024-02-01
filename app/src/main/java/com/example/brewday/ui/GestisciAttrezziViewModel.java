package com.example.brewday.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.brewday.model.Risultato;

public class GestisciAttrezziViewModel extends ViewModel {

    public MutableLiveData<Risultato> allAttrezzi;
    public MutableLiveData<Risultato> createAttrezzoResult;
    public MutableLiveData<Risultato> updateAttrezzoResult;
    public MutableLiveData<Risultato> deleteAttrezzoResult;
}