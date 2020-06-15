package com.example.atilaversionbeta.Fragments.Actividades;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.atilaversionbeta.BaseDatos.AtilaBD;
import com.example.atilaversionbeta.Datos.ConexionSQLiteHelperActividad;
import com.example.atilaversionbeta.Datos.ConexionSQLiteHelperMisActividades;
import com.example.atilaversionbeta.Entidades.Actividad;
import com.example.atilaversionbeta.Entidades.Add.MiActividad;
import com.example.atilaversionbeta.R;

public class DetalleActividadFragment extends Fragment {
    TextView nombre,descripcion,lugares;
    ImageView imagen;
    Button btnLink,btnMaps,btnAdd;
    String link,maps;
    ConexionSQLiteHelperMisActividades actividadSave;

    int codigoM,fotoM,imageM;
    private String municipioM,nombreM,descripcionM,urlInfoM,urlMapsM,lugaresM;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detalle_actividad_fragment,container,false);
        nombre = view.findViewById(R.id.nombreActividad);
        imagen = view.findViewById(R.id.imagen_detalleidA);
        descripcion = view.findViewById(R.id.descripcion_detalleA);
        lugares = view.findViewById(R.id.lugares_detallesA);
        btnLink = view.findViewById(R.id.btnLink);
        btnMaps = view.findViewById(R.id.btnMaps);
        btnAdd = view.findViewById(R.id.btnAddMis);
        actividadSave = new ConexionSQLiteHelperMisActividades(getContext(),"misactividades",null,1);


        Bundle objetoActividad = getArguments();
        Actividad actividad = null;

        if(objetoActividad !=null){
            actividad = (Actividad) objetoActividad.getSerializable("objeto");
            imagen.setImageResource(actividad.getImagenDetalle());
            nombre.setText(actividad.getNombre());
            descripcion.setText(actividad.getDescripcion());
            lugares.setText(actividad.getLugares());
            link = actividad.getUrlInfo();
            maps = actividad.getUrlMaps();


            codigoM = actividad.getCodigo();
            municipioM = actividad.getMunicipio();
            nombreM = actividad.getNombre();
            fotoM = actividad.getFoto();
            imageM = actividad.getImagenDetalle();
            descripcionM = actividad.getDescripcion();
            urlInfoM = actividad.getUrlInfo();
            lugaresM = actividad.getLugares();
            urlMapsM = actividad.getUrlMaps();
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarMisActividades();
            }
        });

        btnLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(link);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });

        btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(maps);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        return view;
    }

    public void guardarMisActividades(){
        try{
            SQLiteDatabase db = actividadSave.getWritableDatabase();
            ContentValues values =  new ContentValues();
            values.put(AtilaBD.CODIGO_ACTIVIDAD,codigoM);
            values.put(AtilaBD.MUNICIPIO_ACTIVIDAD,municipioM);
            values.put(AtilaBD.NOMBRE_ACTIVIDAD,nombreM);
            values.put(AtilaBD.FOTO_ACTIVIDAD, fotoM);
            values.put(AtilaBD.IMG_DETALLE_ACTIVIDAD,imageM );
            values.put(AtilaBD.DESCRIPCION_ACTIVIDAD, descripcionM);
            values.put(AtilaBD.URLINFO_ACTIVIDAD,urlInfoM );
            values.put(AtilaBD.LUGARES_ACTIVIDAD,lugaresM );
            values.put(AtilaBD.URLMAPS_ACTIVIDAD,urlMapsM );


            db.insert(AtilaBD.TABLA_MIACTIVIDAD, null, values);
            Toast.makeText(getContext(), "Esta actividad guardada", Toast.LENGTH_SHORT).show();
        }catch (Exception io){
            Toast.makeText(getContext(), "Esta actividad ya fue guardada anteriormente", Toast.LENGTH_SHORT).show();
        }

    }


}
