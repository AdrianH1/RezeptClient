package com.example.rezeptclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private RecipeService service;
    public CustomAdapter customAdapter;
    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_add = (Button) findViewById(R.id.btn_add);
        ListView lst_recipes = (ListView) findViewById(R.id.lst_recipes);

        list = new ArrayList<String>();
        customAdapter = new CustomAdapter(list, this);
        lst_recipes.setAdapter(customAdapter);

        Intent intent = new Intent(this, RecipeService.class);
        bindService(intent, service_connection, Context.BIND_AUTO_CREATE);
        startService(intent);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(MainActivity.this, AddRecipeActivity.class);
                //startActivity(intent);
            }
        });

        lst_recipes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("test");
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        service.setRunService(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (service != null) {
            service.setRunService(true);
            startService();
        }
    }

    public ServiceConnection service_connection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder binder) {
            service = ((RecipeBinder) binder).getService();
            startService();
        }
        public void onServiceDisconnected(ComponentName className) {
            service = null;
        }
    };

    public void startService() {
        RecipeInterface recipeInterface = new RecipeInterface() {
            @Override
            public void getResult(Recipe[] data) {
                list.clear();
                for (Recipe r : data) {
                    list.add(r.getName());
                }
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        customAdapter.notifyDataSetChanged();
                    }
                });
            }
        };

        service.getRecipes(recipeInterface);
    }
}