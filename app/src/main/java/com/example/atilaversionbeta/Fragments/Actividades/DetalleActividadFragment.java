package com.example.atilaversionbeta.Fragments.Actividades;

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
import com.example.atilaversionbeta.R;

public class DetalleActividadFragment extends Fragment {
    TextView nombre,descripcion;
    ImageView imagen;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detalle_actividad_fragment,container,false);
        nombre = view.findViewById(R.id.nombreActividad);
        imagen = view.findViewById(R.id.imagen_detalleidA);
        descripcion = view.findViewById(R.id.descripcion_detalleA);
        //Crear bundle para recibir el objeto enviado por parametro.
        Bundle objetoActividad = getArguments();
        Actividad actividad = null;;        //validacion para verificar si existen argumentos para mostrar

        if(objetoActividad !=null){
            actividad = (Actividad) objetoActividad.getSerializable("objeto");
            imagen.setImageResource(actividad.getImagenDetalle());
            nombre.setText(actividad.getNombre());
            descripcion.setText(actividad.getDescripcion());
        }
        return view;
    }

}
