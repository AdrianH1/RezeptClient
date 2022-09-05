package com.example.rezeptclient;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;



public class RecipeService extends Service {

    private boolean runService = true;

    public void setRunService(boolean runService) {
        this.runService = runService;
    }

    public RecipeService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new RecipeBinder(this);
    }

    public void getRecipes(RecipeInterface recipeInterface) {
        Thread t = new Thread() {
            @Override
            public void run() {
                while(runService)
                {
                    try {
                        URL url = new URL("http://10.0.2.2:8080/recipie");
                        URLConnection urlconnection = url.openConnection();
                        urlconnection.connect();
                        InputStreamReader reader = new InputStreamReader((InputStream)urlconnection.getContent());

                        Gson gson = new Gson();
                        Recipe[] data = gson.fromJson(reader, Recipe[].class);
                        recipeInterface.getResult(data);
                        sleep(30000);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        t.start();
    }
}