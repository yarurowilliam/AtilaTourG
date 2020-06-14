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
        guardarCityTour();
    }

    //VALLEDUPAR

    public void guardarCiclomonta(){
        SQLiteDatabase db = actividadSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_ACTIVIDAD,0);
        values.put(AtilaBD.MUNICIPIO_ACTIVIDAD,"Valledupar");
        values.put(AtilaBD.NOMBRE_ACTIVIDAD,"Cilomontañismo");
        //values.put(AtilaBD.INFO_ACTIVIDAD,"El ciclomontañismo es uno de los deportes mas practicados en el departamento del Cesar, por tener varios lugares para su practica");
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
        //values.put(AtilaBD.INFO_ACTIVIDAD,"Este plan es perfecto para todo aquel que disfrute ejercitar su cuerpo, disfrutando de la naturaleza y el clima fresco de la mañana.");
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
        values.put(AtilaBD.CODIGO_ACTIVIDAD,2);
        values.put(AtilaBD.MUNICIPIO_ACTIVIDAD,"Valledupar");
        values.put(AtilaBD.NOMBRE_ACTIVIDAD,"Artesania");
        //values.put(AtilaBD.INFO_ACTIVIDAD,"En Valledupar puede encontrar artesanías locales como mochilas arhuacas y kankuamas, esteras y collares.");
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
        values.put(AtilaBD.CODIGO_ACTIVIDAD,3);
        values.put(AtilaBD.MUNICIPIO_ACTIVIDAD,"Valledupar");
        values.put(AtilaBD.NOMBRE_ACTIVIDAD,"Parranda Vallenata");
        //values.put(AtilaBD.INFO_ACTIVIDAD,"¡Viva una verdadera Parranda Vallenata en la tierra del vallenato!");
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
        values.put(AtilaBD.CODIGO_ACTIVIDAD,4);
        values.put(AtilaBD.MUNICIPIO_ACTIVIDAD,"Valledupar");
        values.put(AtilaBD.NOMBRE_ACTIVIDAD,"Bañarse en el Rio");
        //values.put(AtilaBD.INFO_ACTIVIDAD,"Valledupar cuenta con una riqueza hídrica que puede aprovechar para darse un refrescante chapuzón.");
        values.put(AtilaBD.FOTO_ACTIVIDAD, R.drawable.senderismo);
        values.put(AtilaBD.IMG_DETALLE_ACTIVIDAD, R.drawable.senderdescrip);
        values.put(AtilaBD.DESCRIPCION_ACTIVIDAD, "A su paso por el norte de Valledupar se encuentra el balneario de Hurtado, principal lugar de recreación y diversión de la capital del Cesar. En este mismo sitio se encuentran el Pueblito Vallenato y el parque Lineal. Además, alimenta de agua al acueducto de dicha ciudad.");
        values.put(AtilaBD.URLINFO_ACTIVIDAD, "https://es.wikipedia.org/wiki/Río_Guatapurí");
        values.put(AtilaBD.LUGARES_ACTIVIDAD, "- Río Guatapurío\n- La Mina\n- Chemesquemena\n- La Mesa\n- Río Badillo\n- La Vega\n- El Mojao");
        values.put(AtilaBD.URLMAPS_ACTIVIDAD, "https://www.google.com/maps/place/Balneario+Hurtado/@10.4489877,-73.2702838,13z/data=!4m8!1m2!2m1!1sbalneario+hurtado!3m4!1s0x8e8abbfede10430b:0xe5ae13c22c036433!8m2!3d10.4235315!4d-73.255595");


        db.insert(AtilaBD.TABLA_ACTIVIDAD, null, values);
    }

    public void guardarCityTour(){
        SQLiteDatabase db = actividadSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_ACTIVIDAD,5);
        values.put(AtilaBD.MUNICIPIO_ACTIVIDAD,"Valledupar");
        values.put(AtilaBD.NOMBRE_ACTIVIDAD,"City Tour");
        //values.put(AtilaBD.INFO_ACTIVIDAD,"Valledupar es reconocido por sus monumentos y glorietas, en homenaje a su historia y cultura. En la ciudad hay más de 20 monumentos y glorietas, la mayoría dedicados a la música y cultura tradicional vallenata.");
        values.put(AtilaBD.FOTO_ACTIVIDAD, R.drawable.senderismo);
        values.put(AtilaBD.IMG_DETALLE_ACTIVIDAD, R.drawable.senderdescrip);
        values.put(AtilaBD.DESCRIPCION_ACTIVIDAD, "PaseoVallenato Tour es una compañía de servicios turísticos que ofrece experiencias extraordinarias y significativas en destinos únicos del caribe colombiano, especialmente, en los departamentos del Cesar, La Guajira, El Magdalena y el Sur de Bolivar.\n" +
                "\n" +
                "Gracias a que su fundadora María Elisa Ayala F. es una gran conocedora de la cultura y el folclor vallenato y una digna representante y heredera de juglares y cantores, los paquetes turísticos ofrecidos por PaseoVallenato Tour son únicos; es decir, cada uno cuenta con recorridos y experiencias diferentes.\n" +
                "\n" +
                "El propósito de esta compañía turística es dar a conocer los orígenes, ancestros, tradiciones y el folclor único de la región caribe; por esta razón, los planes cuentan con guías calificados, al igual que música en vivo para que viajeros y turistas vivan una experiencia completa e inigualable.");
        values.put(AtilaBD.URLINFO_ACTIVIDAD, "https://paseovallenato.com");
        values.put(AtilaBD.LUGARES_ACTIVIDAD, "- Glorieta \n- Glorieta Los Juglares\n- Glorieta Mi Pedazo de Acordeón\n- Glorieta Los Músicos\n- Río Guatapurí \n- Museo del Acordeón");
        values.put(AtilaBD.URLMAPS_ACTIVIDAD, "https://www.google.com/maps/place/Paseo+Vallenato+Tour/@10.4714877,-73.2582133,17z/data=!3m1!4b1!4m5!3m4!1s0x8e8ab99328149105:0x9346ce9451262adf!8m2!3d10.4714877!4d-73.2560246");


        db.insert(AtilaBD.TABLA_ACTIVIDAD, null, values);
    }


    //MANAURE

    public void guardarParapente(){
        SQLiteDatabase db = actividadSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_ACTIVIDAD,100);
        values.put(AtilaBD.MUNICIPIO_ACTIVIDAD,"Manaure");
        values.put(AtilaBD.NOMBRE_ACTIVIDAD,"Parapente");
        //values.put(AtilaBD.INFO_ACTIVIDAD,"Este espacio es reservado para la informacion de la actividad");
        values.put(AtilaBD.FOTO_ACTIVIDAD, R.drawable.parapente);
        values.put(AtilaBD.IMG_DETALLE_ACTIVIDAD, R.drawable.paramentedetll);
        values.put(AtilaBD.DESCRIPCION_ACTIVIDAD, "Este espacio es reservado para la informacion interna de la actividad");
        db.insert(AtilaBD.TABLA_ACTIVIDAD, null, values);
    }

    //----------------------------------------------------------------------------SITIOS---------------------------------------------------------------------------------------///

    public void guardarSitios(){
        restaurantesSitios();
        hotelesSitios();
        baresSitios();
        parquesSitios();
        emergenciasSitios();
        monumentosSitios();
    }

    public void restaurantesSitios(){
        guardarRestauranteMontaCarga();
        guardarRestauranteCafe();
        guardarRestauranteCompae();
        guardarRestauranteGokela();
    }

    public void guardarRestauranteMontaCarga(){
        SQLiteDatabase db = sitioSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_SITIO,0);
        values.put(AtilaBD.TIPO_SITIO,"Restaurante");
        values.put(AtilaBD.MUNICIPIO_SITIO,"Valledupar");
        values.put(AtilaBD.NOMBRE_SITIO,"Montacarga del sur");
        values.put(AtilaBD.FOTO_SITIO, R.drawable.ciclomontain);
        values.put(AtilaBD.IMG_DETALLE_SITIO, R.drawable.mountain);
        values.put(AtilaBD.DESCRIPCION_SITIO, "Este restaurante es uno de los mas conocidos de la ciudad por su comida tipica y asados\n\n Teléfono: 316 7528373\n\nDirección: Avenida Salguero,Frente de Postobon");
        values.put(AtilaBD.URLINFO_SITIO, "https://www.google.com/search?client=opera-gx&hs=QuJ&ei=J2_lXpXcOISvggeIprfwBA&q=Restaurante+monta+carga+del+sur+n1&oq=Restaurante+monta+carga+del+sur+n1&gs_lcp=CgZwc3ktYWIQAzIICCEQFhAdEB46BAgAEEdQoKMEWNemBGDIpwRoAHABeACAAdQBiAGNA5IBBTAuMS4xmAEAoAEBqgEHZ3dzLXdpeg&sclient=psy-ab&ved=0ahUKEwjVi-yahoDqAhWEl-AKHQjTDU4Q4dUDCAs&uact=5");
        values.put(AtilaBD.URLMAPS_SITIO, "https://www.google.com/maps/place/Monta+Carga+N°+1/@10.4674561,-73.270904,14z/data=!4m8!1m2!2m1!1sRestaurante+monta+carga+del+sur+n1!3m4!1s0x8e8ab98fc1c00843:0xf7f9e50a3acb4f0c!8m2!3d10.447974!4d-73.2427168");

        long ID =  db.insert(AtilaBD.TABLA_SITIO, null, values);
        Toast.makeText(this,"AA:"+ID,Toast.LENGTH_LONG);
    }

    public void guardarRestauranteCafe(){
        SQLiteDatabase db = sitioSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_SITIO,1);
        values.put(AtilaBD.TIPO_SITIO,"Restaurante");
        values.put(AtilaBD.MUNICIPIO_SITIO,"Valledupar");
        values.put(AtilaBD.NOMBRE_SITIO,"ArteSano Natural Cafe");
        values.put(AtilaBD.FOTO_SITIO, R.drawable.ciclomontain);
        values.put(AtilaBD.IMG_DETALLE_SITIO, R.drawable.mountain);
        values.put(AtilaBD.DESCRIPCION_SITIO, "Este restaurante es uno de los mas conocidos de la ciudad por su comida, ademas es recomendado por la secretaria de Turismo del municipio de Valledupar\n\n Teléfono: 304 5085827\n\nDirección: Calle 13 # 6-81");
        values.put(AtilaBD.URLINFO_SITIO, "https://www.facebook.com/artesanorestaurantenatural/");
        values.put(AtilaBD.URLMAPS_SITIO, "https://www.google.com/maps/place/ArteSano+Natural+Cafe+Restaurante/@10.4808174,-73.2506712,17z/data=!3m1!4b1!4m5!3m4!1s0x8e8ab9b4bf6612db:0x39044500ecf59b34!8m2!3d10.4808174!4d-73.2484825");

        long ID =  db.insert(AtilaBD.TABLA_SITIO, null, values);
        Toast.makeText(this,"AA:"+ID,Toast.LENGTH_LONG);
    }

    public void guardarRestauranteCompae(){
        SQLiteDatabase db = sitioSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_SITIO,2);
        values.put(AtilaBD.TIPO_SITIO,"Restaurante");
        values.put(AtilaBD.MUNICIPIO_SITIO,"Valledupar");
        values.put(AtilaBD.NOMBRE_SITIO,"Compae Chipuco");
        values.put(AtilaBD.FOTO_SITIO, R.drawable.ciclomontain);
        values.put(AtilaBD.IMG_DETALLE_SITIO, R.drawable.mountain);
        values.put(AtilaBD.DESCRIPCION_SITIO, "Compae chipuco es un acogedor restaurante fundado en 28 de Enero 2006, al estilo de un patio típico del Valle del cacique Upar,  decorado al estilo colonial, la tradición vive en cada rincón, objeto, pintura y detalles que enriquecen nuestro bagaje cultural donde podrá apreciar la calidad y el toque especial de los sabores de Valledupar y sus alrededores.\n\n Teléfono: (5) 5805635\n\nDirección: Cra. 6 #No. 16-24");
        values.put(AtilaBD.URLINFO_SITIO, "http://www.compaechipucorestobar.com");
        values.put(AtilaBD.URLMAPS_SITIO, "https://www.google.com/maps/place/Bar+Restaurante+Compae+Chipuco/@10.4767824,-73.246602,17z/data=!3m1!4b1!4m5!3m4!1s0x8e8ab9b3d19ade1f:0xa0ba49b9acb10074!8m2!3d10.4767824!4d-73.2444133");

        long ID =  db.insert(AtilaBD.TABLA_SITIO, null, values);
        Toast.makeText(this,"AA:"+ID,Toast.LENGTH_LONG);
    }

    public void guardarRestauranteGokela(){
        SQLiteDatabase db = sitioSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_SITIO,3);
        values.put(AtilaBD.TIPO_SITIO,"Restaurante");
        values.put(AtilaBD.MUNICIPIO_SITIO,"Valledupar");
        values.put(AtilaBD.NOMBRE_SITIO,"Gokéla");
        values.put(AtilaBD.FOTO_SITIO, R.drawable.ciclomontain);
        values.put(AtilaBD.IMG_DETALLE_SITIO, R.drawable.mountain);
        values.put(AtilaBD.DESCRIPCION_SITIO, "Si comer alimentos saludables y a la vez deliciosos, en un ambiente sofisticado mientras escuchas el sonido relajante de Bossa Nova es tu estilo, entonces bienvenido a Gokela! Fundado en Cartagena, Colombia en el año 2012, la franquicia Gokela ha experimentado un crecimiento explosivo debido a su propuesta gastronómica única y la gran selección de ingredientes frescos y saludables a la disposición de sus clientes que saben que escoger a la hora de cuidar su salud y bienestar. Seguimos la metodología ‘fast casual’: un servicio de comida rápida a un precio razonable – uno híbrido entre un restaurante tradicional y uno de comida rápida. .\n\n Teléfono: 323 4559492\n\nDirección: Cra. 9 ##7b- 39");
        values.put(AtilaBD.URLINFO_SITIO, "https://www.gokela.com");
        values.put(AtilaBD.URLMAPS_SITIO, "https://www.google.com/maps/place/Gokéla/@10.4851959,-73.2581529,17z/data=!3m1!4b1!4m5!3m4!1s0x8e8ab83500d25eb1:0xcd8d5ff91984779e!8m2!3d10.4851959!4d-73.2559642");

        long ID =  db.insert(AtilaBD.TABLA_SITIO, null, values);
        Toast.makeText(this,"AA:"+ID,Toast.LENGTH_LONG);
    }

    public void hotelesSitios(){
        guardarHotelHilton();
        guardarHotelSonesta();
        guardarHotelCasa();
    }

    public void guardarHotelHilton(){
        SQLiteDatabase db = sitioSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_SITIO,4);
        values.put(AtilaBD.TIPO_SITIO,"Hotel");
        values.put(AtilaBD.MUNICIPIO_SITIO,"Valledupar");
        values.put(AtilaBD.NOMBRE_SITIO,"Hampton by Hilton Valledupar");
        values.put(AtilaBD.FOTO_SITIO, R.drawable.vallecityicon);
        values.put(AtilaBD.IMG_DETALLE_SITIO, R.drawable.mountain);
        values.put(AtilaBD.DESCRIPCION_SITIO, "El hotel Hampton by Hilton Valledupar está conectado a la plaza comercial Mayales, y tiene una ubicación céntrica en uno de los vecindarios más exclusivos de la ciudad, a solo cinco minutos en automóvil del Aeropuerto Alfonso López Pumarejo.\n" +
                "\n\n" +
                "Este hotel en Valledupar ofrece acceso fácil a varios de los principales parques empresariales, tiendas y restaurantes. Disfruta de la animada vida nocturna y de la música tradicional colombiana, el vallenato, por la cual es famosa Valledupar." +
                "\nTeléfono: (5) 5898555\nDirección: Cl. 30 ##6 a 133");
        values.put(AtilaBD.URLINFO_SITIO, "https://www.hiltonhotels.com/es_XM/colombia/hampton-by-hilton-valledupar/");
        values.put(AtilaBD.URLMAPS_SITIO, "https://www.google.com/maps/place/Hampton+by+Hilton+Valledupar,+Colombia/@10.4502687,-73.2909949,13z/data=!4m11!1m2!2m1!1shilton+valledupar!3m7!1s0x8e8ab82e2fe53121:0x7ec7731db7655e17!5m2!4m1!1i2!8m2!3d10.4564296!4d-73.2425447");



        long ID =  db.insert(AtilaBD.TABLA_SITIO, null, values);
        Toast.makeText(this,"AA:"+ID,Toast.LENGTH_LONG);
    }

    public void guardarHotelSonesta(){
        SQLiteDatabase db = sitioSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_SITIO,5);
        values.put(AtilaBD.TIPO_SITIO,"Hotel");
        values.put(AtilaBD.MUNICIPIO_SITIO,"Valledupar");
        values.put(AtilaBD.NOMBRE_SITIO,"Sonesta Hotel Valledupar");
        values.put(AtilaBD.FOTO_SITIO, R.drawable.vallecityicon);
        values.put(AtilaBD.IMG_DETALLE_SITIO, R.drawable.mountain);
        values.put(AtilaBD.DESCRIPCION_SITIO, "El Sonesta Hotel Valledupar de GHL Hoteles, ubicado al norte de la ciudad a 20 minutos del Aeropuerto Alfonso Lopez Pumarejo, contempla la magia natural de la Sierra Nevada de Santa Marta, situado dentro del complejo del centro comercial Guatapurí y frente al emblemático Parque de la Leyenda Vallenata \"Consuelo Araujo Noguera”, cuenta con las mejores instalaciones y servicios para brindar la mejor atención a sus huéspedes.\nTeléfono: (5) 5748686\nDirección: Diagonal 10 No. 6N - 15, Centro Comercial Guatapuri");
        values.put(AtilaBD.URLINFO_SITIO, "https://www.sonestavalledupar.com/?partner=1520&gclid=EAIaIQobChMIj8-BvYXx4gIVDXGGCh2VTQvoEAAYAiAAEgIfwvD_BwE");
        values.put(AtilaBD.URLMAPS_SITIO, "https://www.google.com/maps/place/Sonesta+Hotel+Valledupar/@10.4959187,-73.2713782,17z/data=!3m1!4b1!4m8!3m7!1s0x8e8ab9ce002ef01b:0x25deaa32528cbbf!5m2!4m1!1i2!8m2!3d10.4959187!4d-73.2691895");



        long ID =  db.insert(AtilaBD.TABLA_SITIO, null, values);
        Toast.makeText(this,"AA:"+ID,Toast.LENGTH_LONG);
    }

    public void guardarHotelCasa(){
        SQLiteDatabase db = sitioSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_SITIO,6);
        values.put(AtilaBD.TIPO_SITIO,"Hotel");
        values.put(AtilaBD.MUNICIPIO_SITIO,"Valledupar");
        values.put(AtilaBD.NOMBRE_SITIO,"Casa de Los Santos Reyes Hotel Boutique");
        values.put(AtilaBD.FOTO_SITIO, R.drawable.vallecityicon);
        values.put(AtilaBD.IMG_DETALLE_SITIO, R.drawable.mountain);
        values.put(AtilaBD.DESCRIPCION_SITIO, "Casa de Los Santos Reyes Hotel Boutique en Valledupar, Una joya colonial patrimonial del siglo XVIII ubicado en el centro histórico de Valledupar, Colombia.\n" +
                "\n" +
                "\u200B\n" +
                "\n" +
                "Casa de los Santos Reyes cuenta con 8 exclusivas habitaciones y una colección de arte local en cada habitación y en zonas exteriores, nuestro hotel en Valledupar es una conexión con la historia, la riqueza cultural, la naturaleza, las tradiciones, donde se crean espacios que realzan el placer del descanso y el silencio.\n" +
                "\n" +
                "\u200B\nTeléfono: 313 5308269\nDirección: Centro Histórico, Cl. 13c #4A-90");
        values.put(AtilaBD.URLINFO_SITIO, "https://www.sonestavalledupar.com/?partner=1520&gclid=EAIaIQobChMIj8-BvYXx4gIVDXGGCh2VTQvoEAAYAiAAEgIfwvD_BwE");
        values.put(AtilaBD.URLMAPS_SITIO, "https://www.google.com/maps/place/Hotel+Boutique+Casa+de+Los+Santos+Reyes+Valledupar/@10.4798078,-73.2476335,17z/data=!3m1!4b1!4m8!3m7!1s0x8e8ab9b30d820fdd:0x315bb6bc8ca8f51a!5m2!4m1!1i2!8m2!3d10.4798078!4d-73.2454448?hl=es");



        long ID =  db.insert(AtilaBD.TABLA_SITIO, null, values);
        Toast.makeText(this,"AA:"+ID,Toast.LENGTH_LONG);
    }

    public void baresSitios(){
        guardarWinners();
        guardarPremiere();
    }

    public void guardarWinners(){
        SQLiteDatabase db = sitioSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_SITIO,7);
        values.put(AtilaBD.TIPO_SITIO,"Bares");
        values.put(AtilaBD.MUNICIPIO_SITIO,"Valledupar");
        values.put(AtilaBD.NOMBRE_SITIO,"Winners Sport Bar");
        values.put(AtilaBD.FOTO_SITIO, R.drawable.vallecityicon);
        values.put(AtilaBD.IMG_DETALLE_SITIO, R.drawable.mountain);
        values.put(AtilaBD.DESCRIPCION_SITIO, "Vivelo Como En El Estadio. El Unico Bar Deportivo De Valledupar \nTeléfono: 313 5308269\nDirección: Calle 12 #16-20 ");
        values.put(AtilaBD.URLINFO_SITIO, "https://www.instagram.com/winnerssport_bar/");
        values.put(AtilaBD.URLMAPS_SITIO, "https://www.google.com/maps?client=opera-gx&hs=OhK&q=winner+sport+bar+valledupar&gs_lcp=CgZwc3ktYWIQAzICCCY6BAgAEEc6BQgAEIMBOgIIADoFCAAQsQM6BwgAELEDEEM6BAgAEEM6BwgAEIMBEEM6BggAEBYQHjoICAAQFhAKEB5QsvEiWMqRI2CdkiNoAHADeACAAZsCiAH8H5IBBjAuMTIuOJgBAKABAaoBB2d3cy13aXo&uact=5&um=1&ie=UTF-8&sa=X&ved=2ahUKEwit28vVk4DqAhUjgnIEHdXkCUQQ_AUoAnoECBIQBA");



        long ID =  db.insert(AtilaBD.TABLA_SITIO, null, values);
        Toast.makeText(this,"AA:"+ID,Toast.LENGTH_LONG);
    }

    public void guardarPremiere(){
        SQLiteDatabase db = sitioSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_SITIO,8);
        values.put(AtilaBD.TIPO_SITIO,"Bares");
        values.put(AtilaBD.MUNICIPIO_SITIO,"Valledupar");
        values.put(AtilaBD.NOMBRE_SITIO,"La Premiere");
        values.put(AtilaBD.FOTO_SITIO, R.drawable.vallecityicon);
        values.put(AtilaBD.IMG_DETALLE_SITIO, R.drawable.mountain);
        values.put(AtilaBD.DESCRIPCION_SITIO, "Dos experiencias diferentes, un sólo lugar. \nTeléfono: 311 2093564\nDirección: Carrera 9 # 10-88");
        values.put(AtilaBD.URLINFO_SITIO, "https://www.facebook.com/lapremierevalledupar/");
        values.put(AtilaBD.URLMAPS_SITIO, "https://www.google.com/maps/place/La+Premiere/@10.4809375,-73.2538753,17z/data=!3m1!4b1!4m5!3m4!1s0x8e8ab9b540fc079f:0x56c7d21ba31a557e!8m2!3d10.4809375!4d-73.2516866");



        long ID =  db.insert(AtilaBD.TABLA_SITIO, null, values);
        Toast.makeText(this,"AA:"+ID,Toast.LENGTH_LONG);
    }

    public void parquesSitios(){
        guardarParqueProvincia();
    }

    public void guardarParqueProvincia(){
        SQLiteDatabase db = sitioSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_SITIO,9);
        values.put(AtilaBD.TIPO_SITIO,"Parques");
        values.put(AtilaBD.MUNICIPIO_SITIO,"Valledupar");
        values.put(AtilaBD.NOMBRE_SITIO,"Parque la provincia");
        values.put(AtilaBD.FOTO_SITIO, R.drawable.vallecityicon);
        values.put(AtilaBD.IMG_DETALLE_SITIO, R.drawable.mountain);
        values.put(AtilaBD.DESCRIPCION_SITIO, "Un parque turístico, cultural y ecológico, con una extensión de 3.3 hectáreas, ubicado a pocos metros del río Guatapurí, de la glorieta Los Juglares y del Parque de la Leyenda Vallenata. es entregado a Valledupar, Colombia y el mundo por parte del alcalde Augusto Daniel Ramírez Uhía. Lugar con el que se le rinde un homenaje al Magdalena Grande y entre sus atractivos cuenta con una escultura a escala humana del músico Carlos Vives.\n" +
                "\n" +
                "Este escenario cuenta con figuras en piedras, alusivas a animales propios de la región; juegos didácticos, senderos en losetas, jardines secos y verdes, sistema de riego, ciclo-ruta, locales comerciales, bancas antivandálicas, canecas de basura, zonas de parqueo, espejo de agua, gimnasio biosaludable, cancha sintética y máquinas de diversión para personas con movilidad reducida. En el Parque de La Provincia también funciona una fuente interactiva y una seca, contemplativa con la frase: Valledupar la más linda.\nDirección: Antiguo parque del helado");
        values.put(AtilaBD.URLINFO_SITIO, "https://www.google.com/search?client=opera-gx&q=parque+la+provincia&sourceid=opera&ie=UTF-8&oe=UTF-8");
        values.put(AtilaBD.URLMAPS_SITIO, "https://www.google.com/maps?client=opera-gx&q=parque+la+provincia&oe=UTF-8&um=1&ie=UTF-8&sa=X&ved=2ahUKEwjh7ZmjloDqAhUqn-AKHQmHDoYQ_AUoAnoECBYQBA");



        long ID =  db.insert(AtilaBD.TABLA_SITIO, null, values);
        Toast.makeText(this,"AA:"+ID,Toast.LENGTH_LONG);
    }

    private void emergenciasSitios(){
        guardarPolicia();
        guardarBomberos();
        guardarHospitalRosario();
        guardarHospitalEduardo();
    }

    public void guardarPolicia(){
        SQLiteDatabase db = sitioSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_SITIO,10);
        values.put(AtilaBD.TIPO_SITIO,"Emergencias");
        values.put(AtilaBD.MUNICIPIO_SITIO,"Valledupar");
        values.put(AtilaBD.NOMBRE_SITIO,"Comando de Policia Nacional");
        values.put(AtilaBD.FOTO_SITIO, R.drawable.vallecityicon);
        values.put(AtilaBD.IMG_DETALLE_SITIO, R.drawable.mountain);
        values.put(AtilaBD.DESCRIPCION_SITIO, "DIRECCIÓN: \n" +
                "Carrera 7 # 23-96 12 de Octubre\n" +
                "HORARIOS: \n" +
                "lunes a domingo de 07:00 am a 12:00 m y de 02:30 pm a 07:00 pm\n" +
                "TELÉFONOS: \n" +
                "3503403116\n" +
                "123\n" +
                "CORREOS: \n" +
                "deces.oac@policia.gov.co\n" +
                "TWITTER: \n" +
                "@PolicíaDeCesar");
        values.put(AtilaBD.URLINFO_SITIO, "https://www.policia.gov.co/cesar");
        values.put(AtilaBD.URLMAPS_SITIO, "https://www.google.com/maps/place/Departamento+de+Policia+Cesar/@10.4502702,-73.2734849,14z/data=!4m8!1m2!2m1!1scomando+de+policia+valledupar!3m4!1s0x8e8ab9bd6dcb0723:0x8bf003ebf61b6307!8m2!3d10.4605845!4d-73.243393");

        long ID =  db.insert(AtilaBD.TABLA_SITIO, null, values);
        Toast.makeText(this,"AA:"+ID,Toast.LENGTH_LONG);
    }

    public void guardarBomberos(){
        SQLiteDatabase db = sitioSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_SITIO,11);
        values.put(AtilaBD.TIPO_SITIO,"Emergencias");
        values.put(AtilaBD.MUNICIPIO_SITIO,"Valledupar");
        values.put(AtilaBD.NOMBRE_SITIO,"Estación de Bomberos");
        values.put(AtilaBD.FOTO_SITIO, R.drawable.vallecityicon);
        values.put(AtilaBD.IMG_DETALLE_SITIO, R.drawable.mountain);
        values.put(AtilaBD.DESCRIPCION_SITIO, "\nDirección: Calle 16 # 19-85\n");
        values.put(AtilaBD.URLINFO_SITIO, "https://www.facebook.com/Sinaltrabombeross/");
        values.put(AtilaBD.URLMAPS_SITIO, "https://www.google.com/maps?client=opera-gx&q=Bomberos+valledupar&oe=UTF-8&um=1&ie=UTF-8&sa=X&ved=2ahUKEwiQw5SroIDqAhWidt8KHV3SCUsQ_AUoAnoECBcQBA");



        long ID =  db.insert(AtilaBD.TABLA_SITIO, null, values);
        Toast.makeText(this,"AA:"+ID,Toast.LENGTH_LONG);
    }

    public void guardarHospitalRosario(){
        SQLiteDatabase db = sitioSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_SITIO,12);
        values.put(AtilaBD.TIPO_SITIO,"Emergencias");
        values.put(AtilaBD.MUNICIPIO_SITIO,"Valledupar");
        values.put(AtilaBD.NOMBRE_SITIO,"Hospital Rosario Pumarejo De López");
        values.put(AtilaBD.FOTO_SITIO, R.drawable.vallecityicon);
        values.put(AtilaBD.IMG_DETALLE_SITIO, R.drawable.mountain);
        values.put(AtilaBD.DESCRIPCION_SITIO, "Teléfono: (5) 5712339\nDirección: Calle 16C No. 17 - 141");
        values.put(AtilaBD.URLINFO_SITIO, "https://hrplopez.gov.co/sitio/index.php/es/");
        values.put(AtilaBD.URLMAPS_SITIO, "https://www.google.com/maps/place/Hospital+Rosario+Pumarejo+De+López/@10.46909,-73.2634847,15z/data=!4m8!1m2!2m1!1shospital+valledupar!3m4!1s0x8e8ab9c7a3e5869f:0xc6ffb7f998a20023!8m2!3d10.46909!4d-73.25473");



        long ID =  db.insert(AtilaBD.TABLA_SITIO, null, values);
        Toast.makeText(this,"AA:"+ID,Toast.LENGTH_LONG);
    }


    public void guardarHospitalEduardo(){
        SQLiteDatabase db = sitioSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_SITIO,13);
        values.put(AtilaBD.TIPO_SITIO,"Emergencias");
        values.put(AtilaBD.MUNICIPIO_SITIO,"Valledupar");
        values.put(AtilaBD.NOMBRE_SITIO,"Hospital Eduardo Arredondo Daza");
        values.put(AtilaBD.FOTO_SITIO, R.drawable.vallecityicon);
        values.put(AtilaBD.IMG_DETALLE_SITIO, R.drawable.mountain);
        values.put(AtilaBD.DESCRIPCION_SITIO, "Teléfono: (5) 5842828\nDirección: Cl. 39 #21-38");
        values.put(AtilaBD.URLINFO_SITIO, "http://www.headese.gov.co");
        values.put(AtilaBD.URLMAPS_SITIO, "https://www.google.com/maps/place/Hospital+Eduardo+Arredondo+Daza/@10.4503125,-73.264773,15z/data=!4m8!1m2!2m1!1shospital+valledupar!3m4!1s0x8e8ab98dace038bf:0x56bfe9fd4d753dfe!8m2!3d10.4465548!4d-73.2474585");



        long ID =  db.insert(AtilaBD.TABLA_SITIO, null, values);
        Toast.makeText(this,"AA:"+ID,Toast.LENGTH_LONG);
    }

    public void monumentosSitios(){
        guardarPlaza();
        guardarRio();
        guardarGlorietaAcordion();
        guardarGlorietaJuglares();
        guardarLosPoporos();
        guardarMuseoAcordeon();
        guardarPilonera();
        guardarParqueLeyenda();
        guardarObelisco();
    }

    public void guardarPlaza(){
        SQLiteDatabase db = sitioSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_SITIO,14);
        values.put(AtilaBD.TIPO_SITIO,"Atracciones");
        values.put(AtilaBD.MUNICIPIO_SITIO,"Valledupar");
        values.put(AtilaBD.NOMBRE_SITIO,"Plaza Alfonso López");
        values.put(AtilaBD.FOTO_SITIO, R.drawable.vallecityicon);
        values.put(AtilaBD.IMG_DETALLE_SITIO, R.drawable.mountain);
        values.put(AtilaBD.DESCRIPCION_SITIO, "Ubicada en el corazón de la ciudad, en cuyo centro se levanta un frondoso árbol de mango convertido en símbolo de identidad de la plaza. Allí está la emblemática tarima Francisco el Hombre, que sirve de escenario a los principales certámenes que realizan en Valledupar. Está enmarcada por un conjunto de casas de arquitectura colonial muy bien conservadas, de la iglesia de la Concepción y la sede del gobierno local. La Iglesia de La Inmaculada Concepción, ubicada en la Plaza Alfonso López de Valledupar, fue construida en el siglo XVII y recientemente restaurada con el apoyo del Ministerio de la Cultura.\nDirección: Carrera 7");
        values.put(AtilaBD.URLINFO_SITIO, "https://es.wikipedia.org/wiki/Plaza_Alfonso_López_de_Valledupar");
        values.put(AtilaBD.URLMAPS_SITIO, "https://www.google.com/maps/place/Plaza+Alfonso+López+de+Valledupar/@10.4775904,-73.2467264,17z/data=!3m1!4b1!4m5!3m4!1s0x8e8ab9b3ca245405:0x7792b2f1f2d255d7!8m2!3d10.4775904!4d-73.2445377");



        long ID =  db.insert(AtilaBD.TABLA_SITIO, null, values);
        Toast.makeText(this,"AA:"+ID,Toast.LENGTH_LONG);
    }

    public void guardarRio(){
        SQLiteDatabase db = sitioSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_SITIO,15);
        values.put(AtilaBD.TIPO_SITIO,"Atracciones");
        values.put(AtilaBD.MUNICIPIO_SITIO,"Valledupar");
        values.put(AtilaBD.NOMBRE_SITIO,"Río Guatapurí");
        values.put(AtilaBD.FOTO_SITIO, R.drawable.vallecityicon);
        values.put(AtilaBD.IMG_DETALLE_SITIO, R.drawable.mountain);
        values.put(AtilaBD.DESCRIPCION_SITIO, "Nace en la laguna Curigua, de la Sierra Nevada de Santa Marta, y en un vertiginoso descenso de 80 kilómetros entrega sus aguas a Valledupar. Su balneario Hurtado, de cristalinas aguas, genera un ambiente refrescante donde los turistas pueden bañarse; sus contorneadas rocas enmarcan paisajes que han contribuido a crear misteriosas leyendas y son fuente de inspiración de melodías del folclor vallenato.");
        values.put(AtilaBD.URLINFO_SITIO, "https://es.wikipedia.org/wiki/Río_Guatapurí");
        values.put(AtilaBD.URLMAPS_SITIO, "https://www.google.com/maps?client=opera-gx&q=Rio+guatapuri&oe=UTF-8&um=1&ie=UTF-8&sa=X&ved=2ahUKEwjXm8TWpIDqAhVjT98KHR1ABaAQ_AUoAnoECBgQBA");



        long ID =  db.insert(AtilaBD.TABLA_SITIO, null, values);
        Toast.makeText(this,"AA:"+ID,Toast.LENGTH_LONG);
    }

    public void guardarGlorietaAcordion(){
        SQLiteDatabase db = sitioSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_SITIO,16);
        values.put(AtilaBD.TIPO_SITIO,"Atracciones");
        values.put(AtilaBD.MUNICIPIO_SITIO,"Valledupar");
        values.put(AtilaBD.NOMBRE_SITIO,"Mi Pedazo de Acordeón");
        values.put(AtilaBD.FOTO_SITIO, R.drawable.vallecityicon);
        values.put(AtilaBD.IMG_DETALLE_SITIO, R.drawable.mountain);
        values.put(AtilaBD.DESCRIPCION_SITIO, "El Monumento Mi Pedazo de Acordeón rinde homenaje a Alejo Durán, primer Rey Vallenato y al principal instrumento e la música vallenata..");
        values.put(AtilaBD.URLINFO_SITIO, "https://www.google.com/maps?client=opera-gx&q=Mi+Pedazo+de+Acordeón&oe=UTF-8&um=1&ie=UTF-8&sa=X&ved=2ahUKEwjZ39qLpYDqAhWKm-AKHdX4AWQQ_AUoBHoECBgQBg");
        values.put(AtilaBD.URLMAPS_SITIO, "https://www.google.com/maps?client=opera-gx&q=Mi+Pedazo+de+Acordeón&oe=UTF-8&um=1&ie=UTF-8&sa=X&ved=2ahUKEwjZ39qLpYDqAhWKm-AKHdX4AWQQ_AUoBHoECBgQBg");



        long ID =  db.insert(AtilaBD.TABLA_SITIO, null, values);
        Toast.makeText(this,"AA:"+ID,Toast.LENGTH_LONG);
    }


    public void guardarGlorietaJuglares(){
        SQLiteDatabase db = sitioSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_SITIO,17);
        values.put(AtilaBD.TIPO_SITIO,"Atracciones");
        values.put(AtilaBD.MUNICIPIO_SITIO,"Valledupar");
        values.put(AtilaBD.NOMBRE_SITIO,"Glorieta Los Juglares");
        values.put(AtilaBD.FOTO_SITIO, R.drawable.vallecityicon);
        values.put(AtilaBD.IMG_DETALLE_SITIO, R.drawable.mountain);
        values.put(AtilaBD.DESCRIPCION_SITIO, "Ubicada en el Balneario Hurtado, a orillas del Río Guatapurí, podrá visitar este sitio turístico que exalta y rinde homenaje a algunos de los más grandes representantes de la música vallenata, como Leandro Díaz, Lorenzo Morales, Rafael Escalona, Emiliano Zuleta Baquero, Diomedes Díaz, Poncho Zuleta, Jorge Oñate y Martín Elías.");
        values.put(AtilaBD.URLINFO_SITIO, "https://www.google.com/maps/place/Glorieta+de+Los+Juglares/@10.5004141,-73.2701535,17z/data=!3m1!4b1!4m5!3m4!1s0x8e8ab8239ee7fa71:0x8d1c30ad5c5c3b76!8m2!3d10.5004141!4d-73.2679648");
        values.put(AtilaBD.URLMAPS_SITIO, "https://www.google.com/maps/place/Glorieta+de+Los+Juglares/@10.5004141,-73.2701535,17z/data=!3m1!4b1!4m5!3m4!1s0x8e8ab8239ee7fa71:0x8d1c30ad5c5c3b76!8m2!3d10.5004141!4d-73.2679648");



        long ID =  db.insert(AtilaBD.TABLA_SITIO, null, values);
        Toast.makeText(this,"AA:"+ID,Toast.LENGTH_LONG);
    }

    public void guardarLosPoporos(){
        SQLiteDatabase db = sitioSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_SITIO,18);
        values.put(AtilaBD.TIPO_SITIO,"Atracciones");
        values.put(AtilaBD.MUNICIPIO_SITIO,"Valledupar");
        values.put(AtilaBD.NOMBRE_SITIO,"Monumento Los Poporos");
        values.put(AtilaBD.FOTO_SITIO, R.drawable.vallecityicon);
        values.put(AtilaBD.IMG_DETALLE_SITIO, R.drawable.mountain);
        values.put(AtilaBD.DESCRIPCION_SITIO, "Monumento del maestro Jorge Maestre, en homenaje a las etnias indígenas que habitan nuestra Sierra Nevada, los hermanos mayores, arhuacos, koguis y wiwas. En este instrumento ellos conservan el hayo (hoja de coca), la cual muelen y consumen como un ritual diario, para conservar las energías en sus largas jornadas de esfuerzo físico.\n" +
                "\n" +
                "El poporo simboliza la madurez del hombre indígena, ya que solo lo poseen cuando llegan a la edad adulta.\n" +
                "\n" +
                "El monumento se encuentra ubicado continuo al Coliseo Cubierto Julio Monsalvo.");
        values.put(AtilaBD.URLINFO_SITIO, "https://www.google.com/maps/place/Los+Poporos/@10.4744578,-73.2650692,17z/data=!3m1!4b1!4m5!3m4!1s0x8e8ab9cfb368129b:0x417eb6a079b49659!8m2!3d10.4744578!4d-73.2628805");
        values.put(AtilaBD.URLMAPS_SITIO, "https://www.google.com/maps/place/Los+Poporos/@10.4744578,-73.2650692,17z/data=!3m1!4b1!4m5!3m4!1s0x8e8ab9cfb368129b:0x417eb6a079b49659!8m2!3d10.4744578!4d-73.2628805");


        long ID =  db.insert(AtilaBD.TABLA_SITIO, null, values);
        Toast.makeText(this,"AA:"+ID,Toast.LENGTH_LONG);
    }

    public void guardarMuseoAcordeon(){
        SQLiteDatabase db = sitioSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_SITIO,19);
        values.put(AtilaBD.TIPO_SITIO,"Atracciones");
        values.put(AtilaBD.MUNICIPIO_SITIO,"Valledupar");
        values.put(AtilaBD.NOMBRE_SITIO,"Museo del Acordeón");
        values.put(AtilaBD.FOTO_SITIO, R.drawable.vallecityicon);
        values.put(AtilaBD.IMG_DETALLE_SITIO, R.drawable.mountain);
        values.put(AtilaBD.DESCRIPCION_SITIO, "La casa Museo del Acordeón ofrece un recorrido guiado en el que se cuenta la historia del principal instrumento musical que le da vida a la música vallenata y se muestra las piezas que alberga el museo. El ingreso al museo tiene un costo/donación de $ 20.000, para el mantenimiento y preservación del recinto. Es necesario llamar para reservar el cupo/ingreso al museo.");
        values.put(AtilaBD.URLINFO_SITIO, "https://www.google.com/maps/place/Museo+del+Acordeón/@10.4788788,-73.2619963,17z/data=!3m1!4b1!4m5!3m4!1s0x8e8ab9cc71d8a0c3:0x2e8ca7aa7c557f40!8m2!3d10.4788788!4d-73.2598076");
        values.put(AtilaBD.URLMAPS_SITIO, "https://www.google.com/maps/place/Museo+del+Acordeón/@10.4788788,-73.2619963,17z/data=!3m1!4b1!4m5!3m4!1s0x8e8ab9cc71d8a0c3:0x2e8ca7aa7c557f40!8m2!3d10.4788788!4d-73.2598076");


        long ID =  db.insert(AtilaBD.TABLA_SITIO, null, values);
        Toast.makeText(this,"AA:"+ID,Toast.LENGTH_LONG);
    }

    public void guardarPilonera(){
        SQLiteDatabase db = sitioSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_SITIO,20);
        values.put(AtilaBD.TIPO_SITIO,"Atracciones");
        values.put(AtilaBD.MUNICIPIO_SITIO,"Valledupar");
        values.put(AtilaBD.NOMBRE_SITIO,"La Pilonera Mayor");
        values.put(AtilaBD.FOTO_SITIO, R.drawable.vallecityicon);
        values.put(AtilaBD.IMG_DETALLE_SITIO, R.drawable.mountain);
        values.put(AtilaBD.DESCRIPCION_SITIO, "La Glorieta – Monumento a La Pilonera Mayor, en homenaje a Consuelo Araújonoguera, la creadora del Festival de la Leyenda Vallenata. El monumento en su reconocimiento lleva puesto el vestido típico del baile de piloneras que se realiza cada año como acto inaugural del festival.\n" +
                "\n" +
                "Se ubica a 1 minuto del Balneario Hurtado, por lo que puede aprovechar para bañarse en las refrescantes aguas del río Guatapurí..");
        values.put(AtilaBD.URLINFO_SITIO, "https://www.google.com/maps?client=opera-gx&q=La+Pilonera+Mayor&oe=UTF-8&um=1&ie=UTF-8&sa=X&ved=2ahUKEwiXueLpp4DqAhVwUt8KHZGTBCUQ_AUoAnoECBcQBA");
        values.put(AtilaBD.URLMAPS_SITIO, "https://www.google.com/maps?client=opera-gx&q=La+Pilonera+Mayor&oe=UTF-8&um=1&ie=UTF-8&sa=X&ved=2ahUKEwiXueLpp4DqAhVwUt8KHZGTBCUQ_AUoAnoECBcQBA");


        long ID =  db.insert(AtilaBD.TABLA_SITIO, null, values);
        Toast.makeText(this,"AA:"+ID,Toast.LENGTH_LONG);
    }

    public void guardarParqueLeyenda(){
        SQLiteDatabase db = sitioSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_SITIO,21);
        values.put(AtilaBD.TIPO_SITIO,"Atracciones");
        values.put(AtilaBD.MUNICIPIO_SITIO,"Valledupar");
        values.put(AtilaBD.NOMBRE_SITIO,"Parque de la Leyenda Vallenata");
        values.put(AtilaBD.FOTO_SITIO, R.drawable.vallecityicon);
        values.put(AtilaBD.IMG_DETALLE_SITIO, R.drawable.mountain);
        values.put(AtilaBD.DESCRIPCION_SITIO, "Escenario donde se realizan los shows principales del Festival de la Leyenda Vallenata. Cada noche del Festival (3 noches) se presentan los mejores exponentes del vallenato, junto a los artistas más reconocidos de la música latina.");
        values.put(AtilaBD.URLINFO_SITIO, "https://es.wikipedia.org/wiki/Parque_de_la_Leyenda_Vallenata");
        values.put(AtilaBD.URLMAPS_SITIO, "https://www.google.com/maps/place/Parque+de+La+Leyenda+Vallenata+Consuelo+Araujonoguera/@10.4968743,-73.26675,17z/data=!3m1!4b1!4m5!3m4!1s0x8e8ab83b7540bedf:0x3f6957c2b897c4d8!8m2!3d10.4968743!4d-73.2645613");


        long ID =  db.insert(AtilaBD.TABLA_SITIO, null, values);
        Toast.makeText(this,"AA:"+ID,Toast.LENGTH_LONG);
    }

    public void guardarObelisco(){
        SQLiteDatabase db = sitioSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_SITIO,22);
        values.put(AtilaBD.TIPO_SITIO,"Atracciones");
        values.put(AtilaBD.MUNICIPIO_SITIO,"Valledupar");
        values.put(AtilaBD.NOMBRE_SITIO,"Glorieta del Obelisco");
        values.put(AtilaBD.FOTO_SITIO, R.drawable.vallecityicon);
        values.put(AtilaBD.IMG_DETALLE_SITIO, R.drawable.mountain);
        values.put(AtilaBD.DESCRIPCION_SITIO, "Es la escultura más alta del municipio, ya que tiene 30 metros de alto. Es un homenaje a la vida y se encuentra ubicada en la entrada sur oeste de Valledupar. Desde 1994, año en que fue inaugurada, se convirtió en un hermoso referente para propios y visitantes.");
        values.put(AtilaBD.URLINFO_SITIO, "https://www.google.com/maps?client=opera-gx&hs=4h1&q=obelisco+valledupar&gs_lcp=CgZwc3ktYWIQAxgAMgIIADIGCAAQFhAeMgYIABAWEB4yBggAEBYQHjIGCAAQFhAeMgYIABAWEB4yBggAEBYQHjIGCAAQFhAeMgYIABAWEB4yBggAEBYQHjoECAAQRzoICAAQFhAKEB46BQghEKABOgQIABBDOgUIABCxAzoFCAAQgwE6BwgAELEDEEM6BAgAEApQwSVYrkZglUpoA3ABeAWAAcsCiAG4JpIBCDAuNC4xNS4ymAEAoAEBqgEHZ3dzLXdperABAA&um=1&ie=UTF-8&sa=X&ved=2ahUKEwjTlYHMqYDqAhUDneAKHU1FCFQQ_AUoAnoECBYQBA");
        values.put(AtilaBD.URLMAPS_SITIO, "https://www.google.com/maps?client=opera-gx&hs=4h1&q=obelisco+valledupar&gs_lcp=CgZwc3ktYWIQAxgAMgIIADIGCAAQFhAeMgYIABAWEB4yBggAEBYQHjIGCAAQFhAeMgYIABAWEB4yBggAEBYQHjIGCAAQFhAeMgYIABAWEB4yBggAEBYQHjoECAAQRzoICAAQFhAKEB46BQghEKABOgQIABBDOgUIABCxAzoFCAAQgwE6BwgAELEDEEM6BAgAEApQwSVYrkZglUpoA3ABeAWAAcsCiAG4JpIBCDAuNC4xNS4ymAEAoAEBqgEHZ3dzLXdperABAA&um=1&ie=UTF-8&sa=X&ved=2ahUKEwjTlYHMqYDqAhUDneAKHU1FCFQQ_AUoAnoECBYQBA");


        long ID =  db.insert(AtilaBD.TABLA_SITIO, null, values);
        Toast.makeText(this,"AA:"+ID,Toast.LENGTH_LONG);
    }

    //----------------------------------------------------------------------------EVENTOS-------------------------------------------------------------------------------------///

    public void guardarEventos(){
        guardarFestival();
        guardarFestivalQuinta();
    }

    public void guardarFestival(){
        SQLiteDatabase db = eventoSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_EVENTO,0);
        values.put(AtilaBD.MUNICIPIO_EVENTO,"Valledupar");
        values.put(AtilaBD.NOMBRE_EVENTO,"Festival de la Leyenda Vallenata");
        values.put(AtilaBD.FOTO_EVENTO, R.drawable.senderismo);
        values.put(AtilaBD.IMG_DETALLE_EVENTO, R.drawable.senderdescrip);
        values.put(AtilaBD.DESCRIPCION_EVENTO, "El Festival de la Leyenda Vallenata, llamado también Festival Vallenato, es el evento más importante del vallenato. Se celebra anualmente a finales de abril o principios de mayo desde 1968 en Valledupar, y es organizado por la Fundación Festival de la Leyenda Vallenata, la cual vela por la defensa y difusión de la expresiones folclóricas y populares que rodean la música vallenata. El festival busca preservar cuatro de los cinco aires o ritmos del vallenato: paseo, merengue, son y puya. Además, el género de la piqueria, la parranda, la poesía campesina, cuentos, leyendas, mitos, tradición oral, expresiones literarias, socioculturales y artísticas asociadas al vallenato.");
        values.put(AtilaBD.URLINFO_EVENTO,"https://festivalvallenato.com");

        db.insert(AtilaBD.TABLA_EVENTO, null, values);
    }

    public void guardarFestivalQuinta(){
        SQLiteDatabase db = eventoSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_EVENTO,1);
        values.put(AtilaBD.MUNICIPIO_EVENTO,"Valledupar");
        values.put(AtilaBD.NOMBRE_EVENTO,"Festival de la Quinta");
        values.put(AtilaBD.FOTO_EVENTO, R.drawable.senderismo);
        values.put(AtilaBD.IMG_DETALLE_EVENTO, R.drawable.senderdescrip);
        values.put(AtilaBD.DESCRIPCION_EVENTO, "EL FESTIVAL DE LA QUINTA es una iniciativa de un grupo de empresarios y gestores culturales del centro histórico de la ciudad de Valledupar. Quienes buscamos generar espacios culturales independientes, Incentivar el talento local y las nuevas generaciones de talentos en otras áreas y expresiones culturales que permitan aumentar la oferta de estas en la Ciudad.\n" +
                "\n" +
                "En Valledupar siempre hemos impulsado y sobresalido en el ámbito cultural y creativo, a tal punto de ser una de las ciudades que mas ha explotado este potencial innato.");
        values.put(AtilaBD.URLINFO_EVENTO,"https://www.festivaldelaquinta.com");

        db.insert(AtilaBD.TABLA_EVENTO, null, values);
    }


    //----------------------------------------------------------------------------INFORMACION-------------------------------------------------------------------------------------///

    public void guardarInformacion(){
        guardarLeyendaVallenata();
        guardarLeyendaSirena();
        guardarLeyendaFrancisco();
        guardarEcceHomo();
        guardarMusica();
        guardarJuglarTobias();
    }

    private void guardarLeyendaVallenata() {
        SQLiteDatabase db = informacionSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_INFORMACION,0);
        values.put(AtilaBD.TIPO_INFORMACION,"Leyenda");
        values.put(AtilaBD.MUNICIPIO_INFORMACION,"Valledupar");
        values.put(AtilaBD.NOMBRE_INFORMACION,"Leyenda Vallenata");
        values.put(AtilaBD.FOTO_INFORMACION, R.drawable.vallecityicon);
        values.put(AtilaBD.IMG_DETALLE_INFORMACION, R.drawable.vallecityicon);
        values.put(AtilaBD.DESCRIPCION_INFORMACION, "Dicen las crónicas que corría el año de 1576, cuando en casa del lusitano Antonio de Pereira se desarrolla una intriga sentimental de la esposa de éste. La india Francisca, bella y sensual, despierta celos en la esposa del portugués, que sospecha que entre ésta y aquel existen relaciones amorosas, no obstante ser ella casada con el indio Gregorio. Llevada de celos, la dama hispana, Ana de Peña, maltrata y azota a la india y en presencia del resto de la servidumbre le corta los cabellos, perpetrando de este modo grave ofensa y humillación a la nativa. Un indiecito tupe de nombre Antoñuelo, burlando la vigilancia, logra escapar y acude al cacique de los tupes, Coroponiaimo, informándole de este incidente. El Cacique monta en ira, comunica lo sucedido al resto de la tribu y convoca una reunión con participación de sus aliados, entre otros, el Cacique Coroniaimo, y allí, previas deliberaciones, se toman decisiones de ataque contra los españoles para vengar la ofensa.");
        values.put(AtilaBD.URLINFO_INFORMACION, "https://festivalvallenato.com/mito-leyenda/");

        long ID =  db.insert(AtilaBD.TABLA_INFORMACION, null, values);
        Toast.makeText(this,"AA:"+ID,Toast.LENGTH_LONG);
    }

    private void guardarLeyendaSirena() {
        SQLiteDatabase db = informacionSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_INFORMACION,1);
        values.put(AtilaBD.TIPO_INFORMACION,"Leyenda");
        values.put(AtilaBD.MUNICIPIO_INFORMACION,"Valledupar");
        values.put(AtilaBD.NOMBRE_INFORMACION,"Sirena de Hurtado");
        values.put(AtilaBD.FOTO_INFORMACION, R.drawable.vallecityicon);
        values.put(AtilaBD.IMG_DETALLE_INFORMACION, R.drawable.vallecityicon);
        values.put(AtilaBD.DESCRIPCION_INFORMACION, "Cuentan los abuelos que Rosario Arciniegas, era una niña muy linda y caprichosa, nacida en el barrio «Cañaguate» de Valledupar. Acostumbrada a hacer siempre su voluntad, no hizo caso cuando sus padres, fieles a la tradición, le prohibieron que fuera a bañarse a las profundas aguas del pozo de Hurtado en el río Guatapurí, por ser un Jueves Santo, día consagrado a rememorar la Pasión de Nuestro Señor Jesucristo. Orgullosa y resuelta, Rosario se marchó a escondidas y al llegar al pozo, soltó sus largos cabellos, se quitó la ropa y se lanzó al agua desde las más altas rocas. Eran las dos de la tarde y, no obstante, el cielo se oscureció y cuando Rosario trató de salir de las aguas no pudo.");
        values.put(AtilaBD.URLINFO_INFORMACION, "https://festivalvallenato.com/mito-leyenda/");

        long ID =  db.insert(AtilaBD.TABLA_INFORMACION, null, values);
        Toast.makeText(this,"AA:"+ID,Toast.LENGTH_LONG);
    }

    private void guardarLeyendaFrancisco() {
        SQLiteDatabase db = informacionSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_INFORMACION,2);
        values.put(AtilaBD.TIPO_INFORMACION,"Leyenda");
        values.put(AtilaBD.MUNICIPIO_INFORMACION,"Valledupar");
        values.put(AtilaBD.NOMBRE_INFORMACION,"Francisco El Hombre");
        values.put(AtilaBD.FOTO_INFORMACION, R.drawable.vallecityicon);
        values.put(AtilaBD.IMG_DETALLE_INFORMACION, R.drawable.vallecityicon);
        values.put(AtilaBD.DESCRIPCION_INFORMACION, "Narra la leyenda que una noche al regresar Francisco después de una parranda de varios días y al ir hacia su pueblo, para distraerse en la soledad de la noche, abrió el acordeón y, sobre su burro, como era usual en aquella época, empezó a interpretar sus melodías; de pronto, al terminar una pieza, surgió de inmediato el repertorio de otro acordeonero, que desafiante trataba de superarlo; de inmediato Francisco marchó hacia él hasta tenerlo a la vista; su competidor, para sorpresa, era Satanás, quien al instante se sentó sobre las raíces de un árbol, abrió su acordeón, y con las notas que le brotaban hizo apagar la luna y todas las estrellas.");
        values.put(AtilaBD.URLINFO_INFORMACION, "https://festivalvallenato.com/mito-leyenda/");

        long ID =  db.insert(AtilaBD.TABLA_INFORMACION, null, values);
        Toast.makeText(this,"AA:"+ID,Toast.LENGTH_LONG);
    }

    private void guardarEcceHomo() {
        SQLiteDatabase db = informacionSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_INFORMACION,3);
        values.put(AtilaBD.TIPO_INFORMACION,"Leyenda");
        values.put(AtilaBD.MUNICIPIO_INFORMACION,"Valledupar");
        values.put(AtilaBD.NOMBRE_INFORMACION,"El Ecce Homo");
        values.put(AtilaBD.FOTO_INFORMACION, R.drawable.vallecityicon);
        values.put(AtilaBD.IMG_DETALLE_INFORMACION, R.drawable.vallecityicon);
        values.put(AtilaBD.DESCRIPCION_INFORMACION, "El Santo Patrono de la ciudad tiene tantos devotos como número de milagros a Él atribuidos. Numerosas personas, desde distintos sitios del país, e incluso del exterior, se desplazan a Valledupar a conmemorar el Lunes Santo, su día. El origen de la imagen que se venera en Valledupar se confunde en la Leyenda. No hay un registro histórico que certifique su procedencia.\n" +
                "\n" +
                "De Él se dice que apareció un día en la antigua Catedral, luego de que alguien que dijo ser artesano y ebanista ofreció regalar una imagen para adornar la iglesia, a cambio de que le dieran los materiales para trabajar. Por habérsele salvado de un accidente aéreo, Alfonso López Pumarejo de regaló al Ecce homo unas cadenas de oro y como en ocasión de una procesión de Semana Santa se las quitaron, él se puso pesado y solo lo pudieron sacar después de colocarle las cadenas nuevas de oro que López le había regalado. Solo así aligeró su peso y los devotos lo pudieron llevar en hombros nuevamente, como siempre.");
        values.put(AtilaBD.URLINFO_INFORMACION, "https://festivalvallenato.com/mito-leyenda/");

        long ID =  db.insert(AtilaBD.TABLA_INFORMACION, null, values);
        Toast.makeText(this,"AA:"+ID,Toast.LENGTH_LONG);
    }

    private void guardarMusica() {
        SQLiteDatabase db = informacionSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_INFORMACION,4);
        values.put(AtilaBD.TIPO_INFORMACION,"Musica");
        values.put(AtilaBD.MUNICIPIO_INFORMACION,"Valledupar");
        values.put(AtilaBD.NOMBRE_INFORMACION,"Vallenato");
        values.put(AtilaBD.FOTO_INFORMACION, R.drawable.vallecityicon);
        values.put(AtilaBD.IMG_DETALLE_INFORMACION, R.drawable.vallecityicon);
        values.put(AtilaBD.DESCRIPCION_INFORMACION, "El vallenato es un género musical autóctono de la Región Caribe de Colombia con su origen en la antigua provincia de Padilla (actuales sur de La Guajira, norte del Cesar y oriente del Magdalena). Tiene notable influencia de la inmigración europea, ya que el acordeón fue traído por pobladores alemanes a Riohacha, La Guajira, a finales del siglo XIX, y tanto la organización estrófica como la métrica se valen de la tradición española; por otra parte, el componente de los esclavos afrocolombianos hace presencia con la caja vallenata, una especie de tambor que en gran medida le da el ritmo a la melodía del acordeón, y por último lo indígena se evidencia con la guacharaca.1\u200B Su popularidad se ha extendido hoy a todas las regiones de Colombia, a países vecinos como Ecuador, Panamá, Venezuela e incluso países de Europa. Se interpreta tradicionalmente con tres instrumentos: el acordeón diatónico, la guacharaca y la caja vallenata. Los ritmos o aires musicales del vallenato son el paseo, el merengue, la puya, el son y la tambora. El vallenato también se interpreta con guitarra y con la instrumentación de la cumbia en cumbiambas " +
                "y grupos de millo.\n\nArtistas como:\n-El churo’ Díaz\n-Martín Elias\n-Mono Zabaleta\n-Silvestre Dangond\n-Kvrass\n-Diomedes Díaz\n-Jorge Oñate\n-Peter Manjarrés\n-Poncho Zuleta\n-Iván Villazón\n\nEstos son los artistas más solicitados.");
        values.put(AtilaBD.URLINFO_INFORMACION, "https://es.wikipedia.org/wiki/Vallenato");

        long ID =  db.insert(AtilaBD.TABLA_INFORMACION, null, values);
        Toast.makeText(this,"AA:"+ID,Toast.LENGTH_LONG);
    }

    private void guardarJuglarTobias() {
        SQLiteDatabase db = informacionSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_INFORMACION,5);
        values.put(AtilaBD.TIPO_INFORMACION,"Juglares");
        values.put(AtilaBD.MUNICIPIO_INFORMACION,"Valledupar");
        values.put(AtilaBD.NOMBRE_INFORMACION,"Tobías Enrique Pumarejo Gutiérrez");
        values.put(AtilaBD.FOTO_INFORMACION, R.drawable.vallecityicon);
        values.put(AtilaBD.IMG_DETALLE_INFORMACION, R.drawable.vallecityicon);
        values.put(AtilaBD.DESCRIPCION_INFORMACION, "Nació en Valledupar, Cesar el 8 de agosto de 1906\nMuere en Barranquilla, el 8 de abril de 1995.\n\nFue parte del jurado del primer Festival Vallenato, al lado de Rafael Escalona y Gustavo Gutiérrez. Fue también el primer compositor vallenato desligado de los instrumentos, el primer miembro de la sociedad de Valledupar que cantó y compuso este ritmo y a quien le cupo el honor de abrirle las puertas en el Club de Valledupar a este nuevo aire musical.\n" +
                "\n" +
                "Sus estudios de bachillerato los hizo en Medellín en el Liceo de la Universidad de Antioquia, del que recibió su mayor influencia académica, poética y musical. En Medellín, al lado de sus amigos del Valle de Upar fundaron la Orquesta Magdalenense, integrada por José María y Pedro Castro Monsalvo, Pedro y Celso Domingo Castro Trespalacios y Ovidio Palmera.\n" +
                "\n" +
                "Cabe destacar que el vallenato en esta orquesta no se ejecutaba acordeón, caja ni guacharaca, sino piano, violín, guitarra, saxo y flauta, los instrumentos musicales más relevantes de la época.\n" +
                "\n" +
                "Como compositor, fue un enamorado de la vida, de la naturaleza, de los animales y en especial de las mujeres a las que amó con devoción.");
        values.put(AtilaBD.URLINFO_INFORMACION, "https://www.ecured.cu/Tobías_Enrique_Pumarejo_Gutiérrez");

        long ID =  db.insert(AtilaBD.TABLA_INFORMACION, null, values);
        Toast.makeText(this,"AA:"+ID,Toast.LENGTH_LONG);
    }


}
