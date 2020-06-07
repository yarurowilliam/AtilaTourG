package com.example.atilaversionbeta.Entidades;

import java.io.Serializable;

public class Municipio implements Serializable {
    private int id;
    private String nombre;
    private String decripcion;
    private String aux;
    private int foto;
    private int imagenDetalle;
    private String descripcion;
   /* private String historia;
    private int imagenHistoria;
    private String leyendas;
    private int imagenLeyendas;*/

    public Municipio(){}

    public Municipio(Integer id, String nombre, String decripcion, String aux, int foto,int imagenDetalle, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.decripcion = decripcion;
        this.aux = aux;
        this.foto = foto;
        this.imagenDetalle = imagenDetalle;
        this.descripcion = descripcion;
    }

    public Integer getImagenDetalle() {
        return imagenDetalle;
    }

    public void setImagenDetalle(Integer imagenDetalle) {
        this.imagenDetalle = imagenDetalle;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDecripcion() {
        return decripcion;
    }

    public void setDecripcion(String decripcion) {
        this.decripcion = decripcion;
    }

    public String getAux() {
        return aux;
    }

    public void setAux(String aux) {
        this.aux = aux;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(Integer foto) {
        this.foto = foto;
    }
}
