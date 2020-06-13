package com.example.atilaversionbeta.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atilaversionbeta.Entidades.Informacion;
import com.example.atilaversionbeta.R;

import java.util.ArrayList;

public class AdapterInformacion extends RecyclerView.Adapter<AdapterInformacion.ViewHolder> implements View.OnClickListener {

    LayoutInflater inflater;
    ArrayList<Informacion> model;
    private View.OnClickListener listener;

    public AdapterInformacion(Context context, ArrayList<Informacion> model){
        this.inflater = LayoutInflater.from(context);
        this.model = model;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.lista_informacion, parent, false);
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

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nombres;
        ImageView imagen;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombres = itemView.findViewById(R.id.nombresI);
            imagen = itemView.findViewById(R.id.imagen_informacion1);
        }
    }
}
