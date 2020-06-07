package com.example.atilaversionbeta.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atilaversionbeta.Entidades.Actividad;
import com.example.atilaversionbeta.Entidades.Evento;
import com.example.atilaversionbeta.R;

import java.util.ArrayList;

public class AdapterEvento  extends RecyclerView.Adapter<AdapterEvento.ViewHolder> implements View.OnClickListener {
    LayoutInflater inflater;
    ArrayList<Evento> model;
    private View.OnClickListener listener;

    public AdapterEvento(Context context, ArrayList<Evento> model){
        this.inflater = LayoutInflater.from(context);
        this.model = model;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.lista_evento, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String nombres = model.get(position).getNombre();
        String descripcion = model.get(position).getInfo();
        int imageid = model.get(position).getFoto();
        holder.nombres.setText(nombres);
        holder.descripcion.setText(descripcion);
        holder.imagen.setImageResource(imageid);
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public void setOnclickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombres, descripcion;
        ImageView imagen;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombres = itemView.findViewById(R.id.nombresE);
            descripcion = itemView.findViewById(R.id.descripcionE);
            imagen = itemView.findViewById(R.id.imagen_evento);
        }
    }
}
