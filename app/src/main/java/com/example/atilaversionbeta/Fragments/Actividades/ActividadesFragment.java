package com.example.atilaversionbeta.Fragments.Actividades;

import android.app.Activity;
import android.content.Context;
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
import com.example.atilaversionbeta.Adaptadores.AdapterMunicipio;
import com.example.atilaversionbeta.Entidades.Actividad;
import com.example.atilaversionbeta.Entidades.Municipio;
import com.example.atilaversionbeta.Interfaces.iComunicaFragments;
import com.example.atilaversionbeta.R;

import java.util.ArrayList;

public class ActividadesFragment extends Fragment{


    //private OnFragmentInteractionListener mListener;


    AdapterActividad adapterActividad;
    RecyclerView recyclerActividad;
    ArrayList<Actividad> listaActividades;

    EditText txtnombre;

    //Crear referencias para poder realizar la comunicacion entre el fragment detalle
    Activity actividad;
    iComunicaFragments interfaceComunicaFragments;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.actividades_fragment,container,false);
        //txtnombre = view.findViewById(R.id.txtnombre);

        recyclerActividad = view.findViewById(R.id.recyclerActivida);
        listaActividades = new ArrayList<>();
        cargarLista();
        mostrarData();
        return view;
    }

    private void cargarLista() {
        listaActividades.add(new Actividad(getString(R.string.ciclomontanismo),getString(R.string.cilom_descripcion)+"\n¿Quieres conocerlos?",R.drawable.mountain,R.drawable.ciclomontain,getString(R.string.cilom_descripcionLarga)));
        listaActividades.add(new Actividad("Ruta en bicicleta","El departamento del Cesar se destaca por tener diferentes puntos para realizar ruta en bicicleta\n¿Quieres conocerlos?",R.drawable.ruta,R.drawable.ruta2,getString(R.string.cilom_descripcionLarga)));
        listaActividades.add(new Actividad("Senderismo","El departamento del Cesar se destaca por tener diferentes puntos para realizar senderismo\n¿Quieres conocerlos?",R.drawable.senderismo,R.drawable.senderdescrip,"Este plan es perfecto para todo aquel que disfrute ejercitar su cuerpo, disfrutando de la naturaleza y el clima fresco de la mañana.\n\n" +
                "        Mirador del Santo Ecce Homo: Si visitas Valledupar puedes realizar caminatas por el parque lineal del rio Guatapurí y conectar con los senderos del cerro del mirador del Santo Ecce Homo.\n\n" +
                "        Manaure: En este municipio encontraras el Cerro de la Cruz y los caminos hasta Sabana Rubia.\n\n" +
                "        Recomedaciones: Ropa y calzado adecuados. Si realizas estas actividades en Valledupar ten en cuenta que el clima es cálido y seco, la hidratación es fundamental. En Manaure ten en cuenta\n\n" +
                "        que la mayoría de los senderos son en altura; en el caso de Sabana Rubia llegarás hasta los 3.000 m.s.n.m por lo que debes llevar ropa para clima frio."));
        listaActividades.add(new Actividad("Parapente","El departamento del Cesar se destaca por tener diferentes puntos para realizar parapente  \n¿Quieres conocerlos?",R.drawable.parapente,R.drawable.paramentedetll,"Admira la majestuosidad del la imponente Sierra Nevada de Santamarta desde el aire, disfruta de la belleza de nuestros atardeceres, sobrevuela un área con el espíritu mágico de la ancestral cultura Aruaca.\n" +
                "\n" +
                "¡Olvídate de tus miedos volando en parapente!\n" +
                "\u200B\n" +
                "En dónde puedo volar cerca a la costa?\n" +
                "Voladero Manaure Aventura, A 15 minutos de Manaure y 45 min de Valledupar.\n" +
                "\u200B\n" +
                "¿qué incluye el paquete?\n" +
                "1 vuelo en parapente de 20 minutos, Seguro, Transporte desde el punto de encuentro a la zona de despegue, y de regreso al punto de encuentro. \n" +
                "\u200B\n" +
                "¿Qué costo tiene?\n" +
                "$150.000 pesos \n" +
                "\u200B"));
        listaActividades.add(new Actividad("Avistamiento de aves","El departamento del Cesar se destaca por tener diferentes puntos para el avistamiento de avez\n¿Quieres conocerlos?",R.drawable.aves,R.drawable.avesdell,"En el Cesar puede encontrar alrededor de 500 especies de aves, con 4 focos de especial interés:\n" +
                "\n" +
                "Serranía del Perijá.\n" +
                "Sierra Nevada.\n" +
                "Ciénaga de Zapatosa.\n" +
                "Bosque seco del Valle del Cesar\n" +
                "Lo puede hacer en el municipio de Manaure Balcón Turístico del Cesar, en el Eco Parque Los Besotes y en la Ciénaga de Zapatosa en Chimichagua.\n" +
                "\n" +
                "Lugares destacados para observación de aves en Manaure:\n" +
                "\n" +
                "Reserva Natural Los Tananeos.\n" +
                "Centro Turístico Villa Adelaida.\n" +
                "Finca Ecoturística La Danta.\n" +
                "Finca Cafetera Las Nieves (vereda San Antonio).\n" +
                "Reserva Natural ProAves (vereda El Cinco).\n" +
                "Disponibilidad permanente. Los horarios, días e itinerarios se establecen con base en la preferencia del cliente."));

    }


    private void mostrarData(){
        recyclerActividad.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterActividad = new AdapterActividad(getContext(), listaActividades);
        recyclerActividad.setAdapter(adapterActividad);

        adapterActividad.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String nombre = listaActividades.get(recyclerActividad.getChildAdapterPosition(view)).getNombre();
                //txtnombre.setText(nombre);
                Toast.makeText(getContext(), "Seleccionó: "+ listaActividades.get(recyclerActividad.getChildAdapterPosition(view)).getNombre(), Toast.LENGTH_SHORT).show();
                //enviar mediante la interface el objeto seleccionado al detalle
                //se envia el objeto completo
                //se utiliza la interface como puente para enviar el objeto seleccionado
                interfaceComunicaFragments.enviarActividad(listaActividades.get(recyclerActividad.getChildAdapterPosition(view)));
                //luego en el mainactivity se hace la implementacion de la interface para implementar el metodo enviarpersona
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //esto es necesario para establecer la comunicacion entre la lista y el detalle
        //si el contexto que le esta llegando es una instancia de un activity:
        if(context instanceof Activity){
            //voy a decirle a mi actividad que sea igual a dicho contesto. castin correspondiente:
            this.actividad= (Activity) context;
            ////que la interface icomunicafragments sea igual ese contexto de la actividad:
            interfaceComunicaFragments= (iComunicaFragments) this.actividad;
            //esto es necesario para establecer la comunicacion entre la lista y el detalle
        }

       /* if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // mListener = null;
    }

    /*
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
