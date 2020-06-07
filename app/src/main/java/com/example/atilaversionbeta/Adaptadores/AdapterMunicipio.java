package com.example.atilaversionbeta.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atilaversionbeta.Entidades.Municipio;
import com.example.atilaversionbeta.R;

import java.util.ArrayList;

public class AdapterMunicipio extends RecyclerView.Adapter<AdapterMunicipio.ViewHolder> implements View.OnClickListener {

    LayoutInflater inflater;
    ArrayList<Municipio> model;

    private View.OnClickListener listener;

    public AdapterMunicipio(Context context, ArrayList<Municipio> model){
        this.inflater = LayoutInflater.from(context);
        this.model = model;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.lista_municipio, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    public void setOnclickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String nombres = model.get(position).getNombre();
        String descripcion = model.get(position).getDecripcion();
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

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombres, descripcion;
        ImageView imagen;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            nombres = itemView.findViewById(R.id.nombres);
            descripcion = itemView.findViewById(R.id.descripcion);
            imagen = itemView.findViewById(R.id.imagen_municipio);
        }

    }

}
