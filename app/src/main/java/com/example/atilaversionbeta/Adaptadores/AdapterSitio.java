package com.example.atilaversionbeta.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atilaversionbeta.Entidades.Sitio;
import com.example.atilaversionbeta.R;

import java.util.ArrayList;

public class AdapterSitio extends RecyclerView.Adapter<AdapterSitio.ViewHolder> implements View.OnClickListener {

    LayoutInflater inflater;
    ArrayList<Sitio> model;
    private View.OnClickListener listener;


    public AdapterSitio(Context context, ArrayList<Sitio> model){
        this.inflater = LayoutInflater.from(context);
        this.model = model;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.lista_sitio, parent, false);
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

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public void setOnclickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nombres, descripcion;
        ImageView imagen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombres = itemView.findViewById(R.id.nombresS);
            descripcion = itemView.findViewById(R.id.descripcionS);
            imagen = itemView.findViewById(R.id.imagen_sitio);
        }
    }
}
