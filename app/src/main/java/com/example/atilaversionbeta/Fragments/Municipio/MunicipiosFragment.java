package com.example.atilaversionbeta.Fragments.Municipio;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atilaversionbeta.Adaptadores.AdapterMunicipio;
import com.example.atilaversionbeta.Entidades.Municipio;
import com.example.atilaversionbeta.Interfaces.iComunicaFragments;
import com.example.atilaversionbeta.R;

import java.util.ArrayList;

public class MunicipiosFragment extends Fragment {

    //private OnFragmentInteractionListener mListener;


    AdapterMunicipio adapterMunicipio;
    RecyclerView recyclerMunicipio;
    ArrayList<Municipio> listaMunicipios;

    EditText txtnombre;

    //Crear referencias para poder realizar la comunicacion entre el fragment detalle
    Activity actividad;
    iComunicaFragments interfaceComunicaFragments;

  /*  @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ñ,container,false);
        //txtnombre = view.findViewById(R.id.txtnombre);

        recyclerMunicipio = view.findViewById(R.id.recyclerView);
        listaMunicipios = new ArrayList<>();
        cargarLista();
        mostrarData();
        return view;
    }*/
    public void cargarLista(){
        listaMunicipios.add(new Municipio(1003,getString(R.string.valle_nombre),getString(R.string.valle_descripcionCorta),"EFFE",R.drawable.vallecityicon,R.drawable.valleduparinfo,getString(R.string.valle_descripcionLarga)));
        listaMunicipios.add(new Municipio(1004,getString(R.string.mana_nombre),getString(R.string.mana_descripcionCorta),"EFFE",R.drawable.manaureicon,R.drawable.manaureinfo,getString(R.string.mana_descripcionLarga)));
        listaMunicipios.add(new Municipio(1005,getString(R.string.pueblo_nombre),getString(R.string.pueblo_descripcionCorta),"EFFE",R.drawable.puebloicon,R.drawable.pueblobelloinfo,getString(R.string.pueblo_descripcionLarga)));
    }

    private void mostrarData(){
        recyclerMunicipio.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterMunicipio = new AdapterMunicipio(getContext(), listaMunicipios);
        recyclerMunicipio.setAdapter(adapterMunicipio);

        adapterMunicipio.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Toast.makeText(getContext(), "Seleccionó: "+ listaMunicipios.get(recyclerMunicipio.getChildAdapterPosition(view)).getNombre(), Toast.LENGTH_SHORT).show();
               interfaceComunicaFragments.enviarMunicipio(listaMunicipios.get(recyclerMunicipio.getChildAdapterPosition(view)));
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof Activity){
            this.actividad= (Activity) context;
            interfaceComunicaFragments= (iComunicaFragments) this.actividad;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
