package com.example.atilaversionbeta.Entidades;

import java.io.Serializable;

public class Evento implements Serializable {
    private int codigo;
    private String municipio;
    private String nombre;
    private int foto;
    private int imagenDetalle;
    private String descripcion;
    private String linkInfo;

    public Evento(){}

    public Evento(int codigo, String municipio, String nombre, int foto, int imagenDetalle, String descripcion, String linkInfo) {
        this.codigo = codigo;
        this.municipio = municipio;
        this.nombre = nombre;
        this.foto = foto;
        this.imagenDetalle = imagenDetalle;
        this.descripcion = descripcion;
        this.linkInfo = linkInfo;
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

    public String getLinkInfo() {
        return linkInfo;
    }

    public void setLinkInfo(String linkInfo) {
        this.linkInfo = linkInfo;
    }
}
