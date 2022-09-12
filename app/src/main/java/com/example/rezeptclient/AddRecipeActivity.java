package com.example.rezeptclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class AddRecipeActivity extends AppCompatActivity {

    String name;
    String components;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        Button btn_save = (Button) findViewById(R.id.btn_save);
        EditText edt_name = (EditText) findViewById(R.id.edt_name);
        EditText edt_components = (EditText) findViewById(R.id.edt_components);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = edt_name.getText().toString();
                components = edt_components.getText().toString();
                addRecipe();
                finish();
            }
        });
    }

    public void addRecipe() {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {

                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("name", name);
                    jsonObject.addProperty("components", components);

                    URL url = new URL("http://10.0.2.2:8080/recipie");
                    HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
                    httpCon.setUseCaches(false);
                    httpCon.setRequestMethod("POST");



                    String test = String.valueOf(jsonObject);

                    OutputStreamWriter wr = new OutputStreamWriter(httpCon.getOutputStream());
                    wr.write(String.valueOf(jsonObject)); // data is the post data to send
                    wr.flush();

                    int responseCode = httpCon.getResponseCode();
                    httpCon.connect();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        t.start();
    }
}