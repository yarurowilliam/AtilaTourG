package com.example.atilaversionbeta.Interfaces;

import com.example.atilaversionbeta.Entidades.Actividad;
import com.example.atilaversionbeta.Entidades.Add.MiActividad;
import com.example.atilaversionbeta.Entidades.Evento;
import com.example.atilaversionbeta.Entidades.Informacion;
import com.example.atilaversionbeta.Entidades.Municipio;
import com.example.atilaversionbeta.Entidades.Sitio;

public interface iComunicaFragments {
    public void enviarMunicipio(Municipio municipio);
    public void enviarActividad(Actividad actividad);
    public void enviarEvento(Evento evento);
    public void enviarSitio(Sitio sitio);
    public void enviarInformacion(Informacion informacion);
    public void enviarMiActivadad(MiActividad miActividad);
}
