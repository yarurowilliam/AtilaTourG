package com.example.atilaversionbeta.Fragments;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.municipios_fragment,container,false);
        //txtnombre = view.findViewById(R.id.txtnombre);

        recyclerMunicipio = view.findViewById(R.id.recyclerView);
        listaMunicipios = new ArrayList<>();
        cargarLista();
        mostrarData();
        return view;
    }
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
               //String nombre = listaMunicipios.get(recyclerMunicipio.getChildAdapterPosition(view)).getNombre();
                //txtnombre.setText(nombre);
               Toast.makeText(getContext(), "Seleccionó: "+ listaMunicipios.get(recyclerMunicipio.getChildAdapterPosition(view)).getNombre(), Toast.LENGTH_SHORT).show();
                //enviar mediante la interface el objeto seleccionado al detalle
                //se envia el objeto completo
                //se utiliza la interface como puente para enviar el objeto seleccionado
                interfaceComunicaFragments.enviarMunicipio(listaMunicipios.get(recyclerMunicipio.getChildAdapterPosition(view)));
                //luego en el mainactivity se hace la implementacion de la interface para implementar el metodo enviarpersona
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //esto es necesario para establecer la comunicacion entre la lista y el detalle
        //si el contexto que le esta llegando es una instancia de un activity:
        if(context instanceof Activity){
        //voy a decirle a mi actividad que sea igual a dicho contesto. castin correspondiente:
            this.actividad= (Activity) context;
            ////que la interface icomunicafragments sea igual ese contexto de la actividad:
            interfaceComunicaFragments= (iComunicaFragments) this.actividad;
            //esto es necesario para establecer la comunicacion entre la lista y el detalle
        }

       /* if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
      // mListener = null;
    }

    /*
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
