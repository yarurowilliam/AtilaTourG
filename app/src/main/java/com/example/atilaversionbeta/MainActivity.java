package com.example.atilaversionbeta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.atilaversionbeta.BaseDatos.AtilaBD;
import com.example.atilaversionbeta.Datos.ConexionSQLiteHelper;
import com.example.atilaversionbeta.Datos.ConexionSQLiteHelperActividad;
import com.example.atilaversionbeta.Datos.ConexionSQLiteHelperEvento;
import com.example.atilaversionbeta.Datos.ConexionSQLiteHelperInformacion;
import com.example.atilaversionbeta.Datos.ConexionSQLiteHelperSitio;
import com.example.atilaversionbeta.Entidades.Actividad;
import com.example.atilaversionbeta.Entidades.Evento;
import com.example.atilaversionbeta.Entidades.Informacion;
import com.example.atilaversionbeta.Entidades.Municipio;
import com.example.atilaversionbeta.Entidades.Sitio;
import com.example.atilaversionbeta.Fragments.Actividades.ActividadesFragment;
import com.example.atilaversionbeta.Fragments.Actividades.DetalleActividadFragment;
import com.example.atilaversionbeta.Fragments.Informacion.DetalleInformacionFragment;
import com.example.atilaversionbeta.Fragments.Informacion.InformacionFragment;
import com.example.atilaversionbeta.Fragments.Municipio.DetalleMunicipioFragment;
import com.example.atilaversionbeta.Fragments.Eventos.DetalleEventoFragment;
import com.example.atilaversionbeta.Fragments.Eventos.EventosFragment;
import com.example.atilaversionbeta.Fragments.MainFragment;
import com.example.atilaversionbeta.Fragments.Municipio.MunicipiosFragment;
import com.example.atilaversionbeta.Fragments.Sitios.DetalleSitioFragment;
import com.example.atilaversionbeta.Fragments.Sitios.SitiosFragment;
import com.example.atilaversionbeta.Interfaces.iComunicaFragments;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, iComunicaFragments {


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
    DetalleInformacionFragment detalleInformacionFragment;


    ConexionSQLiteHelperActividad actividadSave;
    ConexionSQLiteHelperEvento eventoSave;
    ConexionSQLiteHelperSitio sitioSave;
    ConexionSQLiteHelperInformacion informacionSave;

    int contador=0;

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);

        getApplicationContext().deleteDatabase("sitios");
        getApplicationContext().deleteDatabase("actividades");
        getApplicationContext().deleteDatabase("eventos");
        getApplicationContext().deleteDatabase("informacion");

        actividadSave = new ConexionSQLiteHelperActividad(this,"actividades",null,1);
        guardarActividades();

        sitioSave = new ConexionSQLiteHelperSitio(this,"sitios",null,1);
        guardarSitios();

        eventoSave = new ConexionSQLiteHelperEvento(this,"eventos",null,1);
        guardarEventos();

        informacionSave = new ConexionSQLiteHelperInformacion(this,"informacion",null,1);
        guardarInformacion();

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
            fragmentTransaction.replace(R.id.container_fragment,new InformacionFragment());
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

    @Override
    public void onBackPressed() {
        if(contador==0){
            Toast.makeText(getApplicationContext(),"Presione de nuevo para salir",Toast.LENGTH_SHORT).show();
            contador++;
        }else{
            super.onBackPressed();
        }
        new CountDownTimer(3000,1000){
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                contador=0;
            }
        }.start();
    }

    //----------------------------------------------------------------------------interfaces-----------------------------------------------------------------------------------///

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

    @Override
    public void enviarInformacion(Informacion informacion) {

        detalleInformacionFragment = new DetalleInformacionFragment();
        Bundle bundleEnvio = new Bundle();
        bundleEnvio.putSerializable("objeto", informacion);
        detalleInformacionFragment.setArguments(bundleEnvio);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_fragment, detalleInformacionFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    //----------------------------------------------------------------------------ACTIVIDADES-----------------------------------------------------------------------------------///

    public void guardarActividades(){
        guardarCiclomonta();
        guardarCaminata();
        guardarArtesania();
        guardarParranda();
        guardarNadarRio();
    }

    public void guardarCiclomonta(){
        SQLiteDatabase db = actividadSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_ACTIVIDAD,0);
        values.put(AtilaBD.MUNICIPIO_ACTIVIDAD,"Valledupar");
        values.put(AtilaBD.NOMBRE_ACTIVIDAD,"Cilomontañismo");
        values.put(AtilaBD.INFO_ACTIVIDAD,"El ciclomontañismo es uno de los deportes mas practicados en el departamento del Cesar, por tener varios lugares para su practica");
        values.put(AtilaBD.FOTO_ACTIVIDAD, R.drawable.mountain);
        values.put(AtilaBD.IMG_DETALLE_ACTIVIDAD, R.drawable.ciclomontain);
        values.put(AtilaBD.DESCRIPCION_ACTIVIDAD, "El ciclismo de montaña, considerado un deporte de riesgo, es un ciclismo de competición realizado en circuitos naturales generalmente a través de bosques por caminos angostos con cuestas empinadas y descensos muy rápidos.");
        values.put(AtilaBD.URLINFO_ACTIVIDAD, "https://es.wikipedia.org/wiki/Ciclismo_de_montaña");
        values.put(AtilaBD.LUGARES_ACTIVIDAD, "-Cerro de eccehomo\n-Guacoche\n-La Mesa\n-Vuelta del Jabo\n-Los Corazones\n-Puente Blanco\n-La Mina\n-Atanque\n-Chemesquemena");
        values.put(AtilaBD.URLMAPS_ACTIVIDAD, "https://www.google.com/maps/dir/10.5020496,-73.271639/10.5020601,-73.2716283/@10.5015274,-73.2692626,17z");


        db.insert(AtilaBD.TABLA_ACTIVIDAD, null, values);
    }

    public void guardarCaminata(){
        SQLiteDatabase db = actividadSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_ACTIVIDAD,1);
        values.put(AtilaBD.MUNICIPIO_ACTIVIDAD,"Valledupar");
        values.put(AtilaBD.NOMBRE_ACTIVIDAD,"Senderismo");
        values.put(AtilaBD.INFO_ACTIVIDAD,"Este plan es perfecto para todo aquel que disfrute ejercitar su cuerpo, disfrutando de la naturaleza y el clima fresco de la mañana.");
        values.put(AtilaBD.FOTO_ACTIVIDAD, R.drawable.senderismo);
        values.put(AtilaBD.IMG_DETALLE_ACTIVIDAD, R.drawable.senderdescrip);
        values.put(AtilaBD.DESCRIPCION_ACTIVIDAD, "El senderismo es una actividad no competitiva que consiste en caminar, preferentemente por el campo o la montaña, siguiendo un itinerario determinado. Se acostumbra a realizar sobre senderos balizados y homologados por el organismo competente de cada país, pero también por rutas no señalizadas.");
        values.put(AtilaBD.URLINFO_ACTIVIDAD, "https://es.wikipedia.org/wiki/Senderismo");
        values.put(AtilaBD.LUGARES_ACTIVIDAD, "- Mirador del Santo Ecce Homo: Si visitas Valledupar puedes realizar caminatas por el parque lineal del rio Guatapurí y conectar con los senderos del cerro del mirador del Santo Ecce Homo.");
        values.put(AtilaBD.URLMAPS_ACTIVIDAD, "https://www.google.com/maps/place/Capilla+Santo+EcceHomo/@10.5076273,-73.2611208,15.83z/data=!4m5!3m4!1s0x8e8ab816d1d944b3:0xbc64a367cb19b61d!8m2!3d10.5093017!4d-73.2610758");


        db.insert(AtilaBD.TABLA_ACTIVIDAD, null, values);
    }

    public void guardarArtesania(){
        SQLiteDatabase db = actividadSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_ACTIVIDAD,1);
        values.put(AtilaBD.MUNICIPIO_ACTIVIDAD,"Valledupar");
        values.put(AtilaBD.NOMBRE_ACTIVIDAD,"Artesania");
        values.put(AtilaBD.INFO_ACTIVIDAD,"En Valledupar puede encontrar artesanías locales como mochilas arhuacas y kankuamas, esteras y collares.");
        values.put(AtilaBD.FOTO_ACTIVIDAD, R.drawable.senderismo);
        values.put(AtilaBD.IMG_DETALLE_ACTIVIDAD, R.drawable.senderdescrip);
        values.put(AtilaBD.DESCRIPCION_ACTIVIDAD, "Artesanía se refiere al trabajo de un artesano o artesana (normalmente realizado de forma manual por una persona, sin el auxilio de maquinaria o automatizaciones), como al objeto o producto obtenido en el que cada pieza es distinta a las demás. La artesanía como actividad material se diferencia del trabajo en serie o industrial. Para que una artesanía sea tal debe ser trabajada a mano y cuanto menos procesos industriales tenga, más artesanal va a ser. La artesanía es un objeto totalmente cultural, ya que tiene la particularidad de variar dependiendo del contexto social, el paisaje, el clima, los recursos y la historia del lugar donde se realiza.");
        values.put(AtilaBD.URLINFO_ACTIVIDAD, "https://es.wikipedia.org/wiki/Artesanía");
        values.put(AtilaBD.LUGARES_ACTIVIDAD, "- Centro Artesanal Calle Grande\n- Parque de las Madres");
        values.put(AtilaBD.URLMAPS_ACTIVIDAD, "https://www.google.com/maps/place/Centro+Artesanal+Calle+Grande/@10.4761878,-73.2479444,17z/data=!3m1!4b1!4m5!3m4!1s0x8e8ab9b15e55655b:0x87bb632f5f429c49!8m2!3d10.4761878!4d-73.2457557");


        db.insert(AtilaBD.TABLA_ACTIVIDAD, null, values);
    }

    public void guardarParranda(){
        SQLiteDatabase db = actividadSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_ACTIVIDAD,2);
        values.put(AtilaBD.MUNICIPIO_ACTIVIDAD,"Valledupar");
        values.put(AtilaBD.NOMBRE_ACTIVIDAD,"Parranda Vallenata");
        values.put(AtilaBD.INFO_ACTIVIDAD,"¡Viva una verdadera Parranda Vallenata en la tierra del vallenato!");
        values.put(AtilaBD.FOTO_ACTIVIDAD, R.drawable.senderismo);
        values.put(AtilaBD.IMG_DETALLE_ACTIVIDAD, R.drawable.senderdescrip);
        values.put(AtilaBD.DESCRIPCION_ACTIVIDAD, "La Parranda es, fundamentalmente, un rito de amistad. La más alta celebración que se hace por motivos muy especiales y en honor de alguien o algo que se quiere exaltar. Los contertulios se sitúan alrededor del conjunto de música vallenata y en medio de gran silencio y atención escuchan los cantos que éste interpreta. Entre canto y canto aparecen los chistes, las anécdotas, los cuentos, las historias que dieron origen a algunos cantos, mientras el licor circula profusamente entre los invitados que finalmente, bien avanzadas las horas, acaban degustando el suculento sancocho que ha estado hirviendo en los fogones debajo del confortable palo de mango que está en el patio de la casa.");
        values.put(AtilaBD.URLINFO_ACTIVIDAD, "https://festivalvallenato.com/parranda-v/#:~:text=La%20parranda%20Vallenata&text=La%20más%20alta%20celebración%20que,los%20cantos%20que%20éste%20interpreta.");
        values.put(AtilaBD.LUGARES_ACTIVIDAD, "- Guacaó\n- La placita\n- Tierra de cantores\n- Plaza Alfonso López\n- Escuela del Turco Gil\n- Compai Chipuco");
        values.put(AtilaBD.URLMAPS_ACTIVIDAD, "https://www.google.com/maps/place/La+Placita+Bar/@10.4489892,-73.2527739,14z/data=!4m8!1m2!2m1!1sLa+placita!3m4!1s0x8e8ab9b3ea7053ab:0xcd4aec30e5b3bff3!8m2!3d10.4774918!4d-73.2456108");


        db.insert(AtilaBD.TABLA_ACTIVIDAD, null, values);
    }

    public void guardarNadarRio(){
        SQLiteDatabase db = actividadSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_ACTIVIDAD,3);
        values.put(AtilaBD.MUNICIPIO_ACTIVIDAD,"Valledupar");
        values.put(AtilaBD.NOMBRE_ACTIVIDAD,"Bañarse en el Rio");
        values.put(AtilaBD.INFO_ACTIVIDAD,"Valledupar cuenta con una riqueza hídrica que puede aprovechar para darse un refrescante chapuzón.");
        values.put(AtilaBD.FOTO_ACTIVIDAD, R.drawable.senderismo);
        values.put(AtilaBD.IMG_DETALLE_ACTIVIDAD, R.drawable.senderdescrip);
        values.put(AtilaBD.DESCRIPCION_ACTIVIDAD, "A su paso por el norte de Valledupar se encuentra el balneario de Hurtado, principal lugar de recreación y diversión de la capital del Cesar. En este mismo sitio se encuentran el Pueblito Vallenato y el parque Lineal. Además, alimenta de agua al acueducto de dicha ciudad.");
        values.put(AtilaBD.URLINFO_ACTIVIDAD, "https://es.wikipedia.org/wiki/Río_Guatapurí");
        values.put(AtilaBD.LUGARES_ACTIVIDAD, "- Río Guatapurío\n- La Mina\n- Chemesquemena\n- La Mesa\n- Ría Badillo\n- La Vega\n- El Mojao");
        values.put(AtilaBD.URLMAPS_ACTIVIDAD, "https://www.google.com/maps/place/Balneario+Hurtado/@10.4489877,-73.2702838,13z/data=!4m8!1m2!2m1!1sbalneario+hurtado!3m4!1s0x8e8abbfede10430b:0xe5ae13c22c036433!8m2!3d10.4235315!4d-73.255595");


        db.insert(AtilaBD.TABLA_ACTIVIDAD, null, values);
    }

    public void guardarCityTour(){
        SQLiteDatabase db = actividadSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_ACTIVIDAD,3);
        values.put(AtilaBD.MUNICIPIO_ACTIVIDAD,"Valledupar");
        values.put(AtilaBD.NOMBRE_ACTIVIDAD,"City Tour");
        values.put(AtilaBD.INFO_ACTIVIDAD,"Valledupar es reconocido por sus monumentos y glorietas, en homenaje a su historia y cultura. En la ciudad hay más de 20 monumentos y glorietas, la mayoría dedicados a la música y cultura tradicional vallenata.");
        values.put(AtilaBD.FOTO_ACTIVIDAD, R.drawable.senderismo);
        values.put(AtilaBD.IMG_DETALLE_ACTIVIDAD, R.drawable.senderdescrip);
        values.put(AtilaBD.DESCRIPCION_ACTIVIDAD, "A su paso por el norte de Valledupar se encuentra el balneario de Hurtado, principal lugar de recreación y diversión de la capital del Cesar. En este mismo sitio se encuentran el Pueblito Vallenato y el parque Lineal. Además, alimenta de agua al acueducto de dicha ciudad.");
        values.put(AtilaBD.URLINFO_ACTIVIDAD, "https://es.wikipedia.org/wiki/Río_Guatapurí");
        values.put(AtilaBD.LUGARES_ACTIVIDAD, "- Río Guatapurío\n- La Mina\n- Chemesquemena\n- La Mesa\n- Ría Badillo\n- La Vega\n- El Mojao");
        values.put(AtilaBD.URLMAPS_ACTIVIDAD, "https://www.google.com/maps/place/Balneario+Hurtado/@10.4489877,-73.2702838,13z/data=!4m8!1m2!2m1!1sbalneario+hurtado!3m4!1s0x8e8abbfede10430b:0xe5ae13c22c036433!8m2!3d10.4235315!4d-73.255595");


        db.insert(AtilaBD.TABLA_ACTIVIDAD, null, values);
    }




    public void guardarParapente(){
        SQLiteDatabase db = actividadSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_ACTIVIDAD,100);
        values.put(AtilaBD.MUNICIPIO_ACTIVIDAD,"Manaure");
        values.put(AtilaBD.NOMBRE_ACTIVIDAD,"Parapente");
        values.put(AtilaBD.INFO_ACTIVIDAD,"Este espacio es reservado para la informacion de la actividad");
        values.put(AtilaBD.FOTO_ACTIVIDAD, R.drawable.parapente);
        values.put(AtilaBD.IMG_DETALLE_ACTIVIDAD, R.drawable.paramentedetll);
        values.put(AtilaBD.DESCRIPCION_ACTIVIDAD, "Este espacio es reservado para la informacion interna de la actividad");
        db.insert(AtilaBD.TABLA_ACTIVIDAD, null, values);
    }

    //----------------------------------------------------------------------------SITIOS---------------------------------------------------------------------------------------///

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

    //----------------------------------------------------------------------------EVENTOS-------------------------------------------------------------------------------------///

    public void guardarEventos(){
        guardarFestival();
    }

    public void guardarFestival(){
        SQLiteDatabase db = eventoSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_EVENTO,0);
        values.put(AtilaBD.MUNICIPIO_EVENTO,"Valledupar");
        values.put(AtilaBD.NOMBRE_EVENTO,"Festival Vallenato 2021");
        values.put(AtilaBD.INFO_EVENTO,"Este festival vallenato se va a realizar el año 2021 por la cuarentena");
        values.put(AtilaBD.FOTO_EVENTO, R.drawable.senderismo);
        values.put(AtilaBD.IMG_DETALLE_EVENTO, R.drawable.senderdescrip);
        values.put(AtilaBD.DESCRIPCION_EVENTO, "Este espacio es reservado para la informacion interna del evento");

        db.insert(AtilaBD.TABLA_EVENTO, null, values);
    }

    //----------------------------------------------------------------------------INFORMACION-------------------------------------------------------------------------------------///

    public void guardarInformacion(){ guardarLeyendaSirena();
        guardarMusica();}

    private void guardarLeyendaSirena() {
        SQLiteDatabase db = informacionSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_INFORMACION,0);
        values.put(AtilaBD.TIPO_INFORMACION,"Leyenda");
        values.put(AtilaBD.MUNICIPIO_INFORMACION,"Valledupar");
        values.put(AtilaBD.NOMBRE_INFORMACION,"Sirena del Rio Guatapuri");
        values.put(AtilaBD.INFO_INFORMACION,"Este espacio es reservado para la informacion de la leyenda de la Sirena");
        values.put(AtilaBD.FOTO_INFORMACION, R.drawable.vallecityicon);
        values.put(AtilaBD.IMG_DETALLE_INFORMACION, R.drawable.vallecityicon);
        values.put(AtilaBD.DESCRIPCION_INFORMACION, "Este espacio es reservado para la informacion interna de la sirena");

        long ID =  db.insert(AtilaBD.TABLA_INFORMACION, null, values);
        Toast.makeText(this,"AA:"+ID,Toast.LENGTH_LONG);
    }

    private void guardarMusica() {
        SQLiteDatabase db = informacionSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_INFORMACION,0);
        values.put(AtilaBD.TIPO_INFORMACION,"Musica");
        values.put(AtilaBD.MUNICIPIO_INFORMACION,"Valledupar");
        values.put(AtilaBD.NOMBRE_INFORMACION,"Vallenato");
        values.put(AtilaBD.INFO_INFORMACION,"La musica vallenata es el tipo de musica que mas se escucha en el municipioo");
        values.put(AtilaBD.FOTO_INFORMACION, R.drawable.vallecityicon);
        values.put(AtilaBD.IMG_DETALLE_INFORMACION, R.drawable.vallecityicon);
        values.put(AtilaBD.DESCRIPCION_INFORMACION, "Este espacio es reservado para la informacion interna de la sirena");

        long ID =  db.insert(AtilaBD.TABLA_INFORMACION, null, values);
        Toast.makeText(this,"AA:"+ID,Toast.LENGTH_LONG);
    }

}
