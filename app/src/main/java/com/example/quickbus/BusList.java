package com.example.quickbus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BusList extends AppCompatActivity {

    RecyclerView rViewBus;
    ArrayList<Bus> listBus;
    DatabaseReference bdQuickBus;
    MyAdapter adapter;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(BusList.this,MainActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_camiones);
        rViewBus = findViewById(R.id.rViewBuses);
        bdQuickBus = FirebaseDatabase.getInstance().getReference("bus");
        listBus = new ArrayList<>();
        rViewBus.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(this,listBus);
        rViewBus.setAdapter(adapter);
        bdQuickBus.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Bus bus = dataSnapshot.getValue(Bus.class);
                    listBus.add(bus);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
