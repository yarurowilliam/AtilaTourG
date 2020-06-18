package com.example.atilaversionbeta.Fragments.Eventos;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.atilaversionbeta.Entidades.Actividad;
import com.example.atilaversionbeta.Entidades.Evento;
import com.example.atilaversionbeta.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DetalleEventoFragment extends Fragment {
    TextView nombre,descripcion;
    ImageView imagen;
    Button btnLink,btnCalendario;
    String link,nombre1,lugar,descripcion1;
    int dia ,mes,ano;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detalle_evento_fragment,container,false);
        nombre = view.findViewById(R.id.nombreEvento);
        imagen = view.findViewById(R.id.imagen_detalleidE);
        descripcion = view.findViewById(R.id.descripcion_detalleE);
        btnLink = view.findViewById(R.id.btnLinkEvent);
        btnCalendario = view.findViewById(R.id.btnAddCalendario);
        Bundle objetoEvento = getArguments();
        Evento evento = null;
        if(objetoEvento !=null) {
            evento = (Evento) objetoEvento.getSerializable("objeto");
            imagen.setImageResource(evento.getImagenDetalle());
            nombre.setText(evento.getNombre());
            descripcion.setText(evento.getDescripcion());
            link = evento.getLinkInfo();
            nombre1 = evento.getNombre();
            lugar = evento.getMunicipio();
            descripcion1 = evento.getDescripcion();
            dia = evento.getDia();
            mes = evento.getMes();
            ano = evento.getAno();
        }

        btnLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(link);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });

        btnCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar beginTime = Calendar.getInstance();
                beginTime.set(ano,mes,dia,7,00);
                Calendar endTime = Calendar.getInstance();
                endTime.set(ano, mes, dia, 22, 30);
                Intent intent = new Intent(Intent.ACTION_INSERT)
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                        .putExtra(CalendarContract.Events.TITLE, nombre1)
                        .putExtra(CalendarContract.Events.DESCRIPTION, nombre1)
                        .putExtra(CalendarContract.Events.EVENT_LOCATION, lugar)
                        .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                        .putExtra(Intent.EXTRA_EMAIL, "williamyaruro1@gmail.com");
                startActivity(intent);
            }
        });

        return view;
    }
}
