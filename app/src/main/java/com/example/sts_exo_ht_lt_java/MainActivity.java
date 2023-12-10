package com.example.sts_exo_ht_lt_java;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ListView lvMat;
    private Model model = new Model();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvMat = findViewById(R.id.listMatView);
        ModelAdapter adapter = new ModelAdapter(this);
        adapter.setModel(model);
        lvMat.setAdapter(adapter);
    }
}