package com.example.atilaversionbeta.Fragments.Eventos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.atilaversionbeta.Entidades.Actividad;
import com.example.atilaversionbeta.Entidades.Evento;
import com.example.atilaversionbeta.R;

public class DetalleEventoFragment extends Fragment {
    TextView nombre,descripcion;
    ImageView imagen;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detalle_evento_fragment,container,false);
        nombre = view.findViewById(R.id.nombreEvento);
        imagen = view.findViewById(R.id.imagen_detalleidE);
        descripcion = view.findViewById(R.id.descripcion_detalleE);
        Bundle objetoEvento = getArguments();
        Evento evento = null;
        if(objetoEvento !=null){
            evento = (Evento) objetoEvento.getSerializable("objeto");
            imagen.setImageResource(evento.getImagenDetalle());
            nombre.setText(evento.getNombre());
            descripcion.setText(evento.getDescripcion());
        }
        return view;
    }
}
