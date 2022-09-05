package com.example.rezeptclient;

import android.os.Binder;

public class RecipeBinder extends Binder {
    private RecipeService service;

    public RecipeBinder(RecipeService service) {
        this.service = service;
    }
    RecipeService getService() {
        return service;
    }

}
