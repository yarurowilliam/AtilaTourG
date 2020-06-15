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
import com.example.atilaversionbeta.Entidades.Add.MiActividad;
import com.example.atilaversionbeta.R;

import java.util.ArrayList;

public class AdapterMiActividad extends RecyclerView.Adapter<AdapterMiActividad.ViewHolder> implements View.OnClickListener  {

    LayoutInflater inflater;
    ArrayList<MiActividad> model;
    private View.OnClickListener listener;

    public AdapterMiActividad(Context context, ArrayList<MiActividad> model){
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
        View view = inflater.inflate(R.layout.lista_miactividad, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String nombres = model.get(position).getNombre();
        int imageid = model.get(position).getFoto();
        holder.nombres.setText(nombres);
        holder.imagen.setImageResource(imageid);
    }

    @Override
    public int getItemCount() {
        return model.size();
    }
    public void setOnclickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombres;
        ImageView imagen;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombres = itemView.findViewById(R.id.nombresMiA);
            imagen = itemView.findViewById(R.id.imagen_Miactividad);
        }
    }
}
