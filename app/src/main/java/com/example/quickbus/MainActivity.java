package com.example.quickbus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CardView cViewCamiones = findViewById(R.id.cViewCamiones);
        CardView cViewInfo = findViewById(R.id.cViewInfo);
        CardView cViewRuta = findViewById(R.id.cViewRutas);

        cViewCamiones.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BusList.class);
            startActivity(intent);
        });

        cViewRuta.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RoutesMap.class);
            startActivity(intent);
        });
    }
}
