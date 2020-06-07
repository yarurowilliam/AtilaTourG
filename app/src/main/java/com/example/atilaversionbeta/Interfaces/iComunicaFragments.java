package com.example.atilaversionbeta.Interfaces;

import com.example.atilaversionbeta.Entidades.Actividad;
import com.example.atilaversionbeta.Entidades.Evento;
import com.example.atilaversionbeta.Entidades.Municipio;

public interface iComunicaFragments {
    //esta interface se encarga de realizar la comunicacion entre la lista de personas y el detalle
    public void enviarMunicipio(Municipio municipio); //se transportara un objeto de tipo persona
    public void enviarActividad(Actividad actividad);
    public void enviarEvento(Evento evento);
    //(En la clase Persona se implementa Serializable para poder transportar un objeteo a otro)
}
