package com.example.atilaversionbeta.Entidades;

import java.io.Serializable;

public class Informacion implements Serializable {
    private int codigo;
    private String municipio;
    private String tipo;
    private String nombre;
    //private String info;
    private int foto;
    private int imagenDetalle;
    private String descripcion;
    private String link;


    public Informacion(){}

    public Informacion(int codigo, String municipio, String tipo, String nombre, int foto, int imagenDetalle, String descripcion, String link) {
        this.codigo = codigo;
        this.municipio = municipio;
        this.tipo = tipo;
        this.nombre = nombre;
        this.foto = foto;
        this.imagenDetalle = imagenDetalle;
        this.descripcion = descripcion;
        this.link = link;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public int getImagenDetalle() {
        return imagenDetalle;
    }

    public void setImagenDetalle(int imagenDetalle) {
        this.imagenDetalle = imagenDetalle;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
