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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atilaversionbeta.Adaptadores.AdapterSitio;
import com.example.atilaversionbeta.BaseDatos.AtilaBD;
import com.example.atilaversionbeta.Datos.ConexionSQLiteHelperEvento;
import com.example.atilaversionbeta.Datos.ConexionSQLiteHelperSitio;
import com.example.atilaversionbeta.Entidades.Sitio;
import com.example.atilaversionbeta.Interfaces.MainActivity;
import com.example.atilaversionbeta.Interfaces.iComunicaFragments;
import com.example.atilaversionbeta.R;

import java.util.ArrayList;

public class SitiosFragment extends Fragment {

    String municipioBuscado = MainActivity.municipioBuscado;
    ConexionSQLiteHelperSitio admin;
    AdapterSitio adapterSitio;
    RecyclerView recyclerSitio;
    ArrayList<Sitio> listaSitios;

    Activity actividad;
    iComunicaFragments interfaceComunicaFragments;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sitios_fragment,container,false);
        try{
            admin = new ConexionSQLiteHelperSitio(getContext(),"eventos",null,1);
            updateBD();
            if(municipioBuscado.equals("Valledupar")){
                sitiosValledupar();
            }else if(municipioBuscado.equals("Manaure")){

            }
            recyclerSitio = view.findViewById(R.id.recyclerSitios);
            consultarListaSitio();
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
        SQLiteDatabase db = admin.getWritableDatabase();
        db.delete(AtilaBD.TABLA_SITIO,"",null);
    }


    ///CONSULTANDO POR SITIO
    private void consultarListaSitio(){
        SQLiteDatabase db = admin.getReadableDatabase();
        listaSitios = new ArrayList<>();
        Sitio sitio= null;
        String[] args = new String[] {municipioBuscado};
        Cursor cursor = db.rawQuery(" SELECT * FROM sitios WHERE municipio=? ", args);
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


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    //GUARDANDO LOS SITIOS POR MUNICIPIO Y TIPO

    //VALLEDUPAR
    public void sitiosValledupar(){
        guardarRestauranteMontaCarga();
    }

    public void guardarRestauranteMontaCarga(){
        SQLiteDatabase db = admin.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_SITIO,0);
        values.put(AtilaBD.TIPO_SITIO,"Restaurante");
        values.put(AtilaBD.MUNICIPIO_SITIO,"Valledupar");
        values.put(AtilaBD.NOMBRE_SITIO,"Montacarga del sur");
        values.put(AtilaBD.INFO_SITIO,"Este espacio es reservado para la informacion del restaurante");
        values.put(AtilaBD.FOTO_SITIO, R.drawable.ciclomontain);
        values.put(AtilaBD.IMG_DETALLE_SITIO, R.drawable.mountain);
        values.put(AtilaBD.DESCRIPCION_SITIO, "Este espacio es reservado para la informacion interna del restaurante");

        long ID =  db.insert(AtilaBD.TABLA_SITIO, null, values);
        Toast.makeText(getContext(),"AA:"+ID,Toast.LENGTH_LONG);
    }


}
