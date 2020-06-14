package com.example.atilaversionbeta.Fragments;

import android.content.Intent;
import android.media.Image;
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

import com.example.atilaversionbeta.InicioAPP.SeleccionarMunicipio;
import com.example.atilaversionbeta.InicioAPP.SplashActivity;
import com.example.atilaversionbeta.MainActivity;
import com.example.atilaversionbeta.R;

public class MainFragment extends Fragment {
    TextView textoBuscado,textoDescripcion;
    ImageView imageBuscado;
    Button botonBuscado;
    String municipioBuscado = MainActivity.municipioBuscado;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment,container,false);
        textoBuscado = view.findViewById(R.id.municipioBuscado);
        imageBuscado = (ImageView) view.findViewById(R.id.imagenMunicipio);
        textoDescripcion = view.findViewById(R.id.textoMunicipio);
        botonBuscado = (Button) view.findViewById(R.id.botonMunicipios);
        if(municipioBuscado.equals("Valledupar")){
            textoBuscado.setText("¡BIENVENIDO AL MUNICIPIO DE VALLEDUPAR!");
            imageBuscado.setImageResource(R.drawable.inicioapp);
            textoDescripcion.setText("¡Gracias por visitar Valledupar, ahora puede consultar toda la información referente a este municipio y guardar en las que estas interesado!");
            verMunicipios();
        }else if(municipioBuscado.equals("Manaure")){
            textoBuscado.setText("¡BIENVENIDO AL MUNICIPIO DE MANAURE!");
            imageBuscado.setImageResource(R.drawable.manaurecity);
            textoDescripcion.setText("¡Gracias por visitar Manaure, ahora puede consultar toda la información referente a este municipio y guardar en las que estas interesado!");
            verMunicipios();
        }

        return view;
    }

    public void verMunicipios(){
        botonBuscado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SeleccionarMunicipio.class); //ELEGIMOS LA ACTIVITY QUE QUEREMOS EJECUTAR
                startActivity(intent);
            }
        });
    }
}
