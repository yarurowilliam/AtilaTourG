package com.example.atilaversionbeta.Fragments.Informacion;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atilaversionbeta.Adaptadores.AdapterInformacion;
import com.example.atilaversionbeta.Datos.ConexionSQLiteHelperInformacion;
import com.example.atilaversionbeta.Entidades.Informacion;
import com.example.atilaversionbeta.Interfaces.iComunicaFragments;
import com.example.atilaversionbeta.MainActivity;
import com.example.atilaversionbeta.R;

import java.util.ArrayList;

public class InformacionFragment extends Fragment {

    String municipioBuscado = MainActivity.municipioBuscado;
    ConexionSQLiteHelperInformacion admin;
    AdapterInformacion adapterInformacion;
    RecyclerView recyclerInformacion;
    ArrayList<Informacion> listaInformacion;
    String tipoBusqueda;
    Button btnLeyenda,btnMusica;

    Activity actividad;
    iComunicaFragments interfaceComunicaFragments;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.informacion_fragment,container,false);
        try{
            btnLeyenda = (Button) view.findViewById(R.id.botonLeyendas);
            btnMusica = (Button) view.findViewById(R.id.botonMusica);
            admin = new ConexionSQLiteHelperInformacion(getContext(),"informacion",null,1);
            recyclerInformacion = view.findViewById(R.id.recyclerInformacion);
            btnLeyenda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tipoBusqueda = "Leyenda";
                    Toast.makeText(getContext(), "LEYENDAS EN VALLEDUPAR HPTA", Toast.LENGTH_SHORT).show();
                    consultarInformacion();
                }
            });
            btnMusica.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tipoBusqueda = "Musica";
                    Toast.makeText(getContext(), "MUSICA EN VALLEDUPAR HPTA", Toast.LENGTH_SHORT).show();
                    consultarInformacion();
                }
            });
        }catch (Exception e){

        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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

    private void consultarInformacion(){
        SQLiteDatabase db = admin.getReadableDatabase();
        listaInformacion = new ArrayList<>();
        Informacion informacion= null;
        String[] args = new String[] {municipioBuscado,tipoBusqueda};
        Cursor cursor = db.rawQuery(" SELECT * FROM informacion WHERE municipio=? AND tipo=? ", args);
        while(cursor.moveToNext()){
            informacion = new Informacion();
            informacion.setCodigo(cursor.getInt(0));
            informacion.setTipo(cursor.getString(1));
            informacion.setMunicipio(cursor.getString(2));
            informacion.setNombre(cursor.getString(3));
            informacion.setInfo(cursor.getString(4));
            informacion.setFoto(cursor.getInt(5));
            informacion.setImagenDetalle(cursor.getInt(6));
            informacion.setDescripcion(cursor.getString(7));
            listaInformacion.add(informacion);
        }
        recyclerInformacion.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterInformacion = new AdapterInformacion(getContext(), listaInformacion);
        recyclerInformacion.setAdapter(adapterInformacion);

        adapterInformacion.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Seleccionó: "+ listaInformacion.get(recyclerInformacion.getChildAdapterPosition(view)).getNombre(), Toast.LENGTH_SHORT).show();
                interfaceComunicaFragments.enviarInformacion(listaInformacion.get(recyclerInformacion.getChildAdapterPosition(view)));
            }
        });
    }

}
