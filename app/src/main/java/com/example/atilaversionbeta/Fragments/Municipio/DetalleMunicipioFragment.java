package com.example.atilaversionbeta.Fragments.Municipio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.atilaversionbeta.Entidades.Municipio;
import com.example.atilaversionbeta.R;

public class DetalleMunicipioFragment extends Fragment {
    TextView nombre,descripcion;
    ImageView imagen;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detalle_municipio_fragment,container,false);
        //nombre = view.findViewById(R.id.nombreMunicipio);
        imagen = view.findViewById(R.id.imagen_detalleid);
        //descripcion = view.findViewById(R.id.descripcion_detalle);
        //Crear bundle para recibir el objeto enviado por parametro.
        Bundle objetoMunicipio = getArguments();
        Municipio municipio = null;;        //validacion para verificar si existen argumentos para mostrar

        if(objetoMunicipio !=null){
            municipio = (Municipio) objetoMunicipio.getSerializable("objeto");
            imagen.setImageResource(municipio.getImagenDetalle());
            //nombre.setText(municipio.getNombre());
            //descripcion.setText(municipio.getDescripcion());
        }
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
