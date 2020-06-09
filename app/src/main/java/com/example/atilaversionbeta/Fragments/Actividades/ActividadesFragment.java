package com.example.atilaversionbeta.Fragments.Actividades;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import com.example.atilaversionbeta.Adaptadores.AdapterActividad;
import com.example.atilaversionbeta.BaseDatos.AtilaBD;
import com.example.atilaversionbeta.Datos.ConexionSQLiteHelperActividad;
import com.example.atilaversionbeta.Entidades.Actividad;
import com.example.atilaversionbeta.Interfaces.MainActivity;
import com.example.atilaversionbeta.Interfaces.iComunicaFragments;
import com.example.atilaversionbeta.R;

import java.util.ArrayList;

public class ActividadesFragment extends Fragment{

    String municipioBuscado = MainActivity.municipioBuscado;
    ConexionSQLiteHelperActividad admin;
    AdapterActividad adapterActividad;
    RecyclerView recyclerActividad;
    ArrayList<Actividad> listaActividades;

    EditText txtnombre;

    Activity actividad;
    iComunicaFragments interfaceComunicaFragments;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.actividades_fragment,container,false);
        try{
            admin = new ConexionSQLiteHelperActividad(getContext(),"actividades",null,1);
            updateBD();
            if(municipioBuscado.equals("Valledupar")){
                actividadesValledupar();
            }else if(municipioBuscado.equals("Manaure")){
                actividadesManaure();
            }
            recyclerActividad = view.findViewById(R.id.recyclerActivida);
            consultarListaActividades();
        }catch (Exception e){

        }

        return view;
    }


    private void consultarListaActividades(){
        SQLiteDatabase db = admin.getReadableDatabase();
        listaActividades = new ArrayList<>();
        Actividad actividad= null;
        String[] args = new String[] {municipioBuscado};
        Cursor cursor = db.rawQuery(" SELECT * FROM actividades WHERE municipio=? ", args);
        while(cursor.moveToNext()){
            actividad = new Actividad();
            actividad.setCodigo(cursor.getInt(0));
            actividad.setMunicipio(cursor.getString(1));
            actividad.setNombre(cursor.getString(2));
            actividad.setInfo(cursor.getString(3));
            actividad.setFoto(cursor.getInt(4));
            actividad.setImagenDetalle(cursor.getInt(5));
            actividad.setDescripcion(cursor.getString(6));
            listaActividades.add(actividad);
        }
        recyclerActividad.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterActividad = new AdapterActividad(getContext(), listaActividades);
        recyclerActividad.setAdapter(adapterActividad);

        adapterActividad.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Seleccionó: "+ listaActividades.get(recyclerActividad.getChildAdapterPosition(view)).getNombre(), Toast.LENGTH_SHORT).show();
                interfaceComunicaFragments.enviarActividad(listaActividades.get(recyclerActividad.getChildAdapterPosition(view)));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        updateBD();
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
            db.delete(AtilaBD.TABLA_ACTIVIDAD,"",null);
        }catch (Exception e){

        }
    }

    //GUARDANDO LAS ACTIVIDADES POR MUNICIPIO

    //VALLEDUPAR
    public void actividadesValledupar(){
        guardarCiclomonta();
    }

    public void guardarCiclomonta(){
        SQLiteDatabase db = admin.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_ACTIVIDAD,0);
        values.put(AtilaBD.MUNICIPIO_ACTIVIDAD,"Valledupar");
        values.put(AtilaBD.NOMBRE_ACTIVIDAD,"Cilomontañismo");
        values.put(AtilaBD.INFO_ACTIVIDAD,"Este espacio es reservado para la informacion de la actividad");
        values.put(AtilaBD.FOTO_ACTIVIDAD, R.drawable.ciclomontain);
        values.put(AtilaBD.IMG_DETALLE_ACTIVIDAD, R.drawable.mountain);
        values.put(AtilaBD.DESCRIPCION_ACTIVIDAD, "Este espacio es reservado para la informacion interna de la actividad");

        long ID =  db.insert(AtilaBD.TABLA_ACTIVIDAD, null, values);
        Toast.makeText(getContext(),"AA:"+ID,Toast.LENGTH_LONG);
    }

    //MANAURE
    public void actividadesManaure(){
        guardarParapente();
    }


    public void guardarParapente(){
        SQLiteDatabase db = admin.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_ACTIVIDAD,1);
        values.put(AtilaBD.MUNICIPIO_ACTIVIDAD,"Manaure");
        values.put(AtilaBD.NOMBRE_ACTIVIDAD,"Parapente");
        values.put(AtilaBD.INFO_ACTIVIDAD,"Este espacio es reservado para la informacion de la actividad");
        values.put(AtilaBD.FOTO_ACTIVIDAD, R.drawable.parapente);
        values.put(AtilaBD.IMG_DETALLE_ACTIVIDAD, R.drawable.parapente);
        values.put(AtilaBD.DESCRIPCION_ACTIVIDAD, "Este espacio es reservado para la informacion interna de la actividad");
        db.insert(AtilaBD.TABLA_ACTIVIDAD, null, values);
    }

}
