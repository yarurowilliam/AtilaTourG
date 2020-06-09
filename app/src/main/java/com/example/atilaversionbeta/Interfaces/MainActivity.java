package com.example.atilaversionbeta.Interfaces;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.atilaversionbeta.BaseDatos.AtilaBD;
import com.example.atilaversionbeta.Datos.ConexionSQLiteHelperActividad;
import com.example.atilaversionbeta.Datos.ConexionSQLiteHelperEvento;
import com.example.atilaversionbeta.Datos.ConexionSQLiteHelperSitio;
import com.example.atilaversionbeta.Entidades.Actividad;
import com.example.atilaversionbeta.Entidades.Evento;
import com.example.atilaversionbeta.Entidades.Municipio;
import com.example.atilaversionbeta.Entidades.Sitio;
import com.example.atilaversionbeta.Fragments.Actividades.ActividadesFragment;
import com.example.atilaversionbeta.Fragments.Actividades.DetalleActividadFragment;
import com.example.atilaversionbeta.Fragments.DetalleMunicipioFragment;
import com.example.atilaversionbeta.Fragments.Eventos.DetalleEventoFragment;
import com.example.atilaversionbeta.Fragments.Eventos.EventosFragment;
import com.example.atilaversionbeta.Fragments.MainFragment;
import com.example.atilaversionbeta.Fragments.MunicipiosFragment;
import com.example.atilaversionbeta.Fragments.Sitios.DetalleSitioFragment;
import com.example.atilaversionbeta.Fragments.Sitios.SitiosFragment;
import com.example.atilaversionbeta.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, iComunicaFragments{


    public static String municipioBuscado;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    DetalleMunicipioFragment detalleMunicipioFragment;
    DetalleActividadFragment detalleActividadFragment;
    DetalleEventoFragment detalleEventoFragment;
    DetalleSitioFragment detalleSitioFragment;

    ConexionSQLiteHelperActividad actividadSave;
    ConexionSQLiteHelperEvento eventoSave;
    ConexionSQLiteHelperSitio sitioSave;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        updateBD();
    }

    private void updateBD(){
        try{
            SQLiteDatabase db = sitioSave.getWritableDatabase();
            db.delete(AtilaBD.TABLA_SITIO,"",null);
            SQLiteDatabase db2 = actividadSave.getWritableDatabase();
            db2.delete(AtilaBD.TABLA_ACTIVIDAD,"",null);
        }catch (Exception e){
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);


        actividadSave = new ConexionSQLiteHelperActividad(this,"actividades",null,1);
        guardarActividades();

        sitioSave = new ConexionSQLiteHelperSitio(this,"sitios",null,1);
        guardarSitios();


        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_fragment,new MainFragment());
        fragmentTransaction.commit();



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if(menuItem.getItemId() == R.id.home){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new MainFragment());
            fragmentTransaction.commit();
        }
        if(menuItem.getItemId() == R.id.municipios){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new MunicipiosFragment());
            fragmentTransaction.commit();
        }
        if(menuItem.getItemId() == R.id.actividades){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new ActividadesFragment());
            fragmentTransaction.commit();
        }
        if(menuItem.getItemId() == R.id.eventos){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new EventosFragment());
            fragmentTransaction.commit();
        }
        if(menuItem.getItemId() == R.id.sitios){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new SitiosFragment());
            fragmentTransaction.commit();
        }
        return false;
    }


    //===========================================================================================//
    //=======================================INTERFACES==========================================//
    //===========================================================================================//
    @Override
    public void enviarMunicipio(Municipio municipio) {

        detalleMunicipioFragment = new DetalleMunicipioFragment();
        Bundle bundleEnvio = new Bundle();
        bundleEnvio.putSerializable("objeto", municipio);
        detalleMunicipioFragment.setArguments(bundleEnvio);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_fragment, detalleMunicipioFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public void enviarActividad(Actividad actividad) {

        detalleActividadFragment = new DetalleActividadFragment();
        Bundle bundleEnvio = new Bundle();
        bundleEnvio.putSerializable("objeto", actividad);
        detalleActividadFragment.setArguments(bundleEnvio);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_fragment, detalleActividadFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
    @Override
    public void enviarEvento(Evento evento) {

        detalleEventoFragment = new DetalleEventoFragment();
        Bundle bundleEnvio = new Bundle();
        bundleEnvio.putSerializable("objeto", evento);
        detalleEventoFragment.setArguments(bundleEnvio);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_fragment, detalleEventoFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public void enviarSitio(Sitio sitio) {

        detalleSitioFragment = new DetalleSitioFragment();
        Bundle bundleEnvio = new Bundle();
        bundleEnvio.putSerializable("objeto", sitio);
        detalleSitioFragment.setArguments(bundleEnvio);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_fragment, detalleSitioFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
    //===========================================================================================//
    //===========================================================================================//
    //===========================================================================================//
    //---------------------------------------------------------------------------------------------///
    //===========================================================================================//
    //=======================================ACTIVIDADES==============================================//
    //===========================================================================================//


    public void guardarActividades(){
        guardarCiclomonta();
        guardarParapente();
    }

    public void guardarCiclomonta(){
        SQLiteDatabase db = actividadSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_ACTIVIDAD,0);
        values.put(AtilaBD.MUNICIPIO_ACTIVIDAD,"Valledupar");
        values.put(AtilaBD.NOMBRE_ACTIVIDAD,"Cilomontañismo");
        values.put(AtilaBD.INFO_ACTIVIDAD,"Este espacio es reservado para la informacion de la actividad");
        values.put(AtilaBD.FOTO_ACTIVIDAD, R.drawable.ciclomontain);
        values.put(AtilaBD.IMG_DETALLE_ACTIVIDAD, R.drawable.mountain);
        values.put(AtilaBD.DESCRIPCION_ACTIVIDAD, "Este espacio es reservado para la informacion interna de la actividad");

        db.insert(AtilaBD.TABLA_ACTIVIDAD, null, values);
    }

    public void guardarParapente(){
        SQLiteDatabase db = actividadSave.getWritableDatabase();
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

    //===========================================================================================//
    //===========================================================================================//
    //===========================================================================================//
    //---------------------------------------------------------------------------------------------///
    //===========================================================================================//
    //=======================================SITIOS==============================================//
    //===========================================================================================//
    public void guardarSitios(){
        restaurantesSitios();
        hotelesSitios();
    }

    public void restaurantesSitios(){
        guardarRestauranteMontaCarga();
    }

    public void guardarRestauranteMontaCarga(){
        SQLiteDatabase db = sitioSave.getWritableDatabase();
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
        Toast.makeText(this,"AA:"+ID,Toast.LENGTH_LONG);
    }

    public void hotelesSitios(){guardarHotelHilton();}

    public void guardarHotelHilton(){
        SQLiteDatabase db = sitioSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_SITIO,0);
        values.put(AtilaBD.TIPO_SITIO,"Hotel");
        values.put(AtilaBD.MUNICIPIO_SITIO,"Valledupar");
        values.put(AtilaBD.NOMBRE_SITIO,"Hilton");
        values.put(AtilaBD.INFO_SITIO,"Este espacio es reservado para la informacion del hotel");
        values.put(AtilaBD.FOTO_SITIO, R.drawable.vallecityicon);
        values.put(AtilaBD.IMG_DETALLE_SITIO, R.drawable.mountain);
        values.put(AtilaBD.DESCRIPCION_SITIO, "Este espacio es reservado para la informacion interna del restaurante");

        long ID =  db.insert(AtilaBD.TABLA_SITIO, null, values);
        Toast.makeText(this,"AA:"+ID,Toast.LENGTH_LONG);
    }

    //===========================================================================================//
    //===========================================================================================//
    //===========================================================================================//



}
