package com.example.atilaversionbeta.Fragments.Actividades;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.atilaversionbeta.Datos.ConexionSQLiteHelperMisActividades;
import com.example.atilaversionbeta.Entidades.Actividad;
import com.example.atilaversionbeta.Entidades.Add.MiActividad;
import com.example.atilaversionbeta.R;

public class DetalleMiActividadFragment extends Fragment {
    TextView nombre,descripcion,lugares;
    ImageView imagen;
    Button btnLink,btnMaps;
    String link,maps;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detalle_miactividad_fragment,container,false);
        nombre = view.findViewById(R.id.nombreMiActividad);
        imagen = view.findViewById(R.id.imagen_detalleidMA);
        descripcion = view.findViewById(R.id.descripcion_detalleMA);
        lugares = view.findViewById(R.id.lugares_detallesMA);
        btnLink = view.findViewById(R.id.btnLinkMA);
        btnMaps = view.findViewById(R.id.btnMapsMi);


        Bundle objetoActividad = getArguments();
        MiActividad actividad = null;

        if(objetoActividad !=null){
            actividad = (MiActividad) objetoActividad.getSerializable("objeto");
            imagen.setImageResource(actividad.getImagenDetalle());
          //  nombre.setText("pepe");
            nombre.setText(actividad.getNombre());
            descripcion.setText(actividad.getDescripcion());
            lugares.setText(actividad.getLugares());
            link = actividad.getUrlInfo();
            maps = actividad.getUrlMaps();
        }

        btnLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(link);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });

        btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(maps);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        return view;
    }

}
