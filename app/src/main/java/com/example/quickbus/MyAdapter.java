package com.example.quickbus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    Context context;
    ArrayList<Bus> BusList;

    public MyAdapter(Context context,ArrayList<Bus> BusList) {
        this.context = context;
        this.BusList = BusList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.camiones,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        Bus bus = BusList.get(position);
        holder.ruta.setText(bus.getRuta());
        holder.num_micro.setText(bus.getRuta());
        holder.tiempo_est.setText(bus.getTiempo_est());
        holder.sig_parada.setText(bus.getSig_parada());
        holder.estado.setText(bus.getEstado());
    }

    @Override
    public int getItemCount() {
        return BusList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView ruta,num_micro,tiempo_est,sig_parada,estado;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ruta = itemView.findViewById(R.id.txtRuta);
            num_micro = itemView.findViewById(R.id.txtNumMicro);
            tiempo_est = itemView.findViewById(R.id.txtte);
            sig_parada = itemView.findViewById(R.id.txtParada);
            estado = itemView.findViewById(R.id.txtEstado);
        }
    }
}