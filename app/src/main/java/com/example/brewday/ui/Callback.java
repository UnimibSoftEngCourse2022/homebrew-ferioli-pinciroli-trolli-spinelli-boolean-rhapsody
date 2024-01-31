package com.example.brewday.ui;

import com.example.brewday.model.Result;

public interface Callback {

    void onSuccess(Result result);
    void onFailure(Exception e);
}
