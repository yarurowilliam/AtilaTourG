package com.example.atilaversionbeta.Fragments.Actividades;

import android.app.Activity;
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

import com.example.atilaversionbeta.Adaptadores.AdapterActividad;
import com.example.atilaversionbeta.Adaptadores.AdapterMiActividad;
import com.example.atilaversionbeta.BaseDatos.AtilaBD;
import com.example.atilaversionbeta.Datos.ConexionSQLiteHelperActividad;
import com.example.atilaversionbeta.Entidades.Actividad;
import com.example.atilaversionbeta.Entidades.Add.MiActividad;
import com.example.atilaversionbeta.Interfaces.iComunicaFragments;
import com.example.atilaversionbeta.MainActivity;
import com.example.atilaversionbeta.R;

import java.util.ArrayList;

public class MisActividadesFragment extends Fragment {
    //String municipioBuscado = MainActivity.municipioBuscado;
    private ConexionSQLiteHelperActividad admin;
    AdapterMiActividad adapterActividad;
    RecyclerView recyclerActividad;
    ArrayList<MiActividad> listaActividades;

    Activity actividad;
    iComunicaFragments interfaceComunicaFragments;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.misactividades_fragment,container,false);
        try{
            admin = new ConexionSQLiteHelperActividad(getContext(),"misactividades",null,1);
            recyclerActividad = view.findViewById(R.id.recyclerMiActivida);
            recyclerActividad.setLayoutManager(new LinearLayoutManager(getContext()));
            consultarListaActividades();
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

    private void consultarListaActividades(){
        try{
            SQLiteDatabase db = admin.getReadableDatabase();
            listaActividades = new ArrayList<>();
            MiActividad miActividad= null;
            Cursor cursor = db.rawQuery("select * From "+ AtilaBD.TABLA_MIACTIVIDAD,null);
            while(cursor.moveToNext()){
                miActividad = new MiActividad();
                miActividad.setCodigo(cursor.getInt(0));
                miActividad.setMunicipio(cursor.getString(1));
                miActividad.setNombre(cursor.getString(2));
                miActividad.setFoto(cursor.getInt(3));
                miActividad.setImagenDetalle(cursor.getInt(4));
                miActividad.setDescripcion(cursor.getString(5));
                miActividad.setUrlInfo(cursor.getString(6));
                miActividad.setLugares(cursor.getString(7));
                miActividad.setUrlMaps(cursor.getString(8));
                listaActividades.add(miActividad);
            }

            adapterActividad = new AdapterMiActividad(getContext(), listaActividades);
            recyclerActividad.setAdapter(adapterActividad);

            adapterActividad.setOnclickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "Seleccionó: "+ listaActividades.get(recyclerActividad.getChildAdapterPosition(view)).getNombre(), Toast.LENGTH_SHORT).show();
                    interfaceComunicaFragments.enviarMiActivadad(listaActividades.get(recyclerActividad.getChildAdapterPosition(view)));
                }
            });
        }catch (Exception io){

        }

    }



}
