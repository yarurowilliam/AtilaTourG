package com.example.atilaversionbeta.Fragments.Sitios;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.atilaversionbeta.Entidades.Sitio;
import com.example.atilaversionbeta.R;

public class DetalleSitioFragment extends Fragment {
    TextView nombre,descripcion;
    ImageView imagen;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detalle_sitio_fragment,container,false);
        nombre = view.findViewById(R.id.nombreSitio);
        imagen = view.findViewById(R.id.imagen_detalleidS);
        descripcion = view.findViewById(R.id.descripcion_detalleS);
        Bundle objetoSitio = getArguments();
        Sitio sitio = null;;

        if(objetoSitio !=null){
            sitio = (Sitio) objetoSitio.getSerializable("objeto");
            imagen.setImageResource(sitio.getImagenDetalle());
            nombre.setText(sitio.getNombre());
            descripcion.setText(sitio.getDescripcion());
        }
        return view;
    }
}
