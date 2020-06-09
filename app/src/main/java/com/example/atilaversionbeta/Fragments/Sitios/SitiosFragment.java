package com.example.atilaversionbeta.Fragments.Sitios;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import com.example.atilaversionbeta.Adaptadores.AdapterSitio;
import com.example.atilaversionbeta.BaseDatos.AtilaBD;
import com.example.atilaversionbeta.Datos.ConexionSQLiteHelperSitio;
import com.example.atilaversionbeta.Entidades.Sitio;
import com.example.atilaversionbeta.Interfaces.MainActivity;
import com.example.atilaversionbeta.Interfaces.iComunicaFragments;
import com.example.atilaversionbeta.R;

import java.util.ArrayList;

public class SitiosFragment extends Fragment {

    ImageButton btnRestaurante,btnHotel;
    String municipioBuscado = MainActivity.municipioBuscado;
    String tipoBusqueda;
    ConexionSQLiteHelperSitio admin;
    AdapterSitio adapterSitio;
    RecyclerView recyclerSitio;
    ArrayList<Sitio> listaSitios;

    Activity actividad;
    iComunicaFragments interfaceComunicaFragments;
    private Object ImageButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sitios_fragment,container,false);
        btnRestaurante = (ImageButton) view.findViewById(R.id.botonRestaurante);
        btnHotel = (ImageButton) view.findViewById(R.id.botonHotel);
        try{
            admin = new ConexionSQLiteHelperSitio(getContext(),"sitios",null,1);
            recyclerSitio = view.findViewById(R.id.recyclerSitios);
            btnRestaurante.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tipoBusqueda = "Restaurante";
                    Toast.makeText(getContext(), "RESTAURANTES EN VALLEDUPAR HPTA", Toast.LENGTH_SHORT).show();
                    consultarLista();
                }
            });
            btnHotel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tipoBusqueda = "Hotel";
                    Toast.makeText(getContext(), "HOTELES EN VALLEDUPAR HPTA", Toast.LENGTH_SHORT).show();
                    consultarLista();
                }
            });


        }catch (Exception e){

        }

        return view;
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

    private void updateBD(){
        try{
            SQLiteDatabase db = admin.getWritableDatabase();
            db.delete(AtilaBD.TABLA_SITIO,"",null);
        }catch (Exception e){

        }
    }


    ///CONSULTANDO POR SITIO RESTAURANTE
    private void consultarLista(){
        SQLiteDatabase db = admin.getReadableDatabase();
        listaSitios = new ArrayList<>();
        Sitio sitio= null;
        String[] args = new String[] {municipioBuscado,tipoBusqueda};
        Cursor cursor = db.rawQuery(" SELECT * FROM sitios WHERE municipio=? AND tipo=? ", args);
        while(cursor.moveToNext()){
            sitio = new Sitio();
            sitio.setCodigo(cursor.getInt(0));
            sitio.setTipo(cursor.getString(1));
            sitio.setMunicipio(cursor.getString(2));
            sitio.setNombre(cursor.getString(3));
            sitio.setInfo(cursor.getString(4));
            sitio.setFoto(cursor.getInt(5));
            sitio.setImagenDetalle(cursor.getInt(6));
            sitio.setDescripcion(cursor.getString(7));
            listaSitios.add(sitio);
        }
        recyclerSitio.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterSitio = new AdapterSitio(getContext(), listaSitios);
        recyclerSitio.setAdapter(adapterSitio);

        adapterSitio.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Seleccionó: "+ listaSitios.get(recyclerSitio.getChildAdapterPosition(view)).getNombre(), Toast.LENGTH_SHORT).show();
                interfaceComunicaFragments.enviarSitio(listaSitios.get(recyclerSitio.getChildAdapterPosition(view)));
            }
        });
    }
}
