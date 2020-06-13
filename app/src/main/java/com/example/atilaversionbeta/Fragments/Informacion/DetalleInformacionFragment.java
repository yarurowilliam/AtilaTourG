package com.example.atilaversionbeta.Fragments.Informacion;

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

import com.example.atilaversionbeta.Entidades.Informacion;
import com.example.atilaversionbeta.R;

public class DetalleInformacionFragment extends Fragment {
    TextView nombre,descripcion;
    ImageView imagen;
    Button btnLink;
    String link;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detalle_informacion_fragment,container,false);
        nombre = view.findViewById(R.id.nombreInformacionI);
        imagen = view.findViewById(R.id.imagen_detalleidI);
        descripcion = view.findViewById(R.id.descripcion_detalleI);
        btnLink = view.findViewById(R.id.btnLinkInfo);

        Bundle objetoInformacion = getArguments();
        Informacion informacion = null;

        if(objetoInformacion !=null){
            informacion = (Informacion) objetoInformacion.getSerializable("objeto");
            imagen.setImageResource(informacion.getImagenDetalle());
            nombre.setText(informacion.getNombre());
            descripcion.setText(informacion.getDescripcion());
            link = informacion.getLink();
        }

        btnLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(link);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });

        return view;
    }
}
