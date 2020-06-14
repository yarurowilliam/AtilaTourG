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
        values.put(AtilaBD.CODIGO_SITIO,0);
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
        values.put(AtilaBD.CODIGO_SITIO,0);
        values.put(AtilaBD.TIPO_SITIO,"Restaurante");
        values.put(AtilaBD.MUNICIPIO_SITIO,"Valledupar");
        values.put(AtilaBD.NOMBRE_SITIO,"Bar Restaurante Compae Chipuco");
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
        values.put(AtilaBD.CODIGO_SITIO,0);
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

    public void hotelesSitios(){guardarHotelHilton();}

    public void guardarHotelHilton(){
        SQLiteDatabase db = sitioSave.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(AtilaBD.CODIGO_SITIO,0);
        values.put(AtilaBD.TIPO_SITIO,"Hotel");
        values.put(AtilaBD.MUNICIPIO_SITIO,"Valledupar");
        values.put(AtilaBD.NOMBRE_SITIO,"Hilton");
        values.put(AtilaBD.FOTO_SITIO, R.drawable.vallecityicon);
        values.put(AtilaBD.IMG_DETALLE_SITIO, R.drawable.mountain);
        values.put(AtilaBD.DESCRIPCION_SITIO, "Este espacio es reservado para la informacion interna del restaurante");

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
