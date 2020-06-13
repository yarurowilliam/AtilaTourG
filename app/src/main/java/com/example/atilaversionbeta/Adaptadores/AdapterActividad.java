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
import com.example.atilaversionbeta.R;

import java.util.ArrayList;

public class AdapterActividad extends RecyclerView.Adapter<AdapterActividad.ViewHolder> implements View.OnClickListener  {

    LayoutInflater inflater;
    ArrayList<Actividad> model;
    private View.OnClickListener listener;

    public AdapterActividad(Context context, ArrayList<Actividad> model){
        this.inflater = LayoutInflater.from(context);
        this.model = model;
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.lista_actividad, parent, false);
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

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombres, descripcion, lugares;
        ImageView imagen;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            nombres = itemView.findViewById(R.id.nombresA);
            descripcion = itemView.findViewById(R.id.descripcionA);
            imagen = itemView.findViewById(R.id.imagen_actividad);

        }

    }
}
