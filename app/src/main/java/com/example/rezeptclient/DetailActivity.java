package com.example.rezeptclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView txt_name = (TextView) findViewById(R.id.txt_name);
        TextView txt_components = (TextView) findViewById(R.id.txt_components);

        String name = getIntent().getStringExtra("name");
        String components = getIntent().getStringExtra("components");

        txt_name.setText(name);
        txt_components.setText(components);
    }
}