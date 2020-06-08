package com.example.atilaversionbeta.Fragments.Eventos;

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

import com.example.atilaversionbeta.Adaptadores.AdapterEvento;
import com.example.atilaversionbeta.BaseDatos.AtilaBD;
import com.example.atilaversionbeta.Datos.ConexionSQLiteHelperEvento;
import com.example.atilaversionbeta.Entidades.Evento;
import com.example.atilaversionbeta.Interfaces.MainActivity;
import com.example.atilaversionbeta.Interfaces.iComunicaFragments;
import com.example.atilaversionbeta.R;

import java.util.ArrayList;

public class EventosFragment extends Fragment {
    String municipioBuscado = MainActivity.municipioBuscado;
    ConexionSQLiteHelperEvento admin;
    AdapterEvento adapterEvento;
    RecyclerView recyclerEvento;
    ArrayList<Evento> listaEventos;
    ///
    Activity actividad;
    iComunicaFragments interfaceComunicaFragments;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.eventos_fragment,container,false);
        try{
            admin = new ConexionSQLiteHelperEvento(getContext(),"eventos",null,1);
            updateBD();
            if(municipioBuscado.equals("Valledupar")){
                eventosValledupar();
            }else if(municipioBuscado.equals("Manaure")){

            }
            recyclerEvento = view.findViewById(R.id.recyclerEventos);
            consultarListaEventos();
        }catch (Exception e){

        }

        return view;
    }
    private void consultarListaEventos(){
        SQLiteDatabase db = admin.getReadableDatabase();
        listaEventos = new ArrayList<>();
        Evento evento= null;
        String[] args = new String[] {municipioBuscado};
        Cursor cursor = db.rawQuery(" SELECT * FROM eventos WHERE municipio=? ", args);
        while(cursor.moveToNext()){
            evento = new Evento();
            evento.setCodigo(cursor.getInt(0));
            evento.setMunicipio(cursor.getString(1));
            evento.setNombre(cursor.getString(2));
            evento.setInfo(cursor.getString(3));
            evento.setFoto(cursor.getInt(4));
            evento.setImagenDetalle(cursor.getInt(5));
            evento.setDescripcion(cursor.getString(6));
            listaEventos.add(evento);
        }
        recyclerEvento.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterEvento = new AdapterEvento(getContext(), listaEventos);
        recyclerEvento.setAdapter(adapterEvento);

        adapterEvento.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Seleccionó: "+ listaEventos.get(recyclerEvento.getChildAdapterPosition(view)).getNombre(), Toast.LENGTH_SHORT).show();
                interfaceComunicaFragments.enviarEvento(listaEventos.get(recyclerEvento.getChildAdapterPosition(view)));
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
        SQLiteDatabase db = admin.getWritableDatabase();
        db.delete(AtilaBD.TABLA_EVENTO,"",null);
    }

    //VALLEDUPAR
    public void eventosValledupar(){
        guardarFestival();
    }

    public void guardarFestival(){
        SQLiteDatabase db = admin.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_EVENTO,0);
        values.put(AtilaBD.MUNICIPIO_EVENTO,"Valledupar");
        values.put(AtilaBD.NOMBRE_EVENTO,"Festival Vallenato 2021");
        values.put(AtilaBD.INFO_EVENTO,"Este festival vallenato se va a realizar el año 2021 por la cuarentena");
        values.put(AtilaBD.FOTO_EVENTO, R.drawable.senderismo);
        values.put(AtilaBD.IMG_DETALLE_EVENTO, R.drawable.senderdescrip);
        values.put(AtilaBD.DESCRIPCION_EVENTO, "Este espacio es reservado para la informacion interna del evento");

        long ID =  db.insert(AtilaBD.TABLA_EVENTO, null, values);
        Toast.makeText(getContext(),"AA:"+ID,Toast.LENGTH_LONG);
    }
}
