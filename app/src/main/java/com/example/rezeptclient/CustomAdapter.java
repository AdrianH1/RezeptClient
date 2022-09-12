package com.example.rezeptclient;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();
    private Context context;

    public CustomAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //return list.get(pos).getId();
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.recipelist, null);
        }

        //Handle TextView and display string from your list
        TextView tvContact= (TextView)view.findViewById(R.id.tvContact);
        tvContact.setText(list.get(position));

        //Handle buttons and add onClickListeners
        ImageView btn_delete= (ImageView)view.findViewById(R.id.btn_delete);

        btn_delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Thread t = new Thread() {
                    @Override
                    public void run() {
                        try {
                            int recipeDBId = MainActivity.recipeList[position].getId();
                            URL url = new URL("http://10.0.2.2:8080/recipie/" + recipeDBId);
                            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
                            httpCon.setUseCaches(false);
                            httpCon.setRequestMethod("DELETE");
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
                list.remove(position);
                notifyDataSetChanged();
            }
        });

        return view;
    }
}
