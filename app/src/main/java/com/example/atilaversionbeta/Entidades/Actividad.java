package com.example.atilaversionbeta.Entidades;

import java.io.Serializable;

public class Actividad implements Serializable {
    private int codigo;
    private String municipio;
    private String nombre;
    //private String info;
    private int foto;
    private int imagenDetalle;
    private String descripcion;
    private String urlInfo;
    private String lugares;
    private String urlMaps;



    public Actividad(){

    }

    public Actividad(int codigo, String municipio, String nombre, int foto, int imagenDetalle, String descripcion, String urlInfo, String lugares, String urlMaps) {
        this.codigo = codigo;
        this.municipio = municipio;
        this.nombre = nombre;
        this.foto = foto;
        this.imagenDetalle = imagenDetalle;
        this.descripcion = descripcion;
        this.urlInfo = urlInfo;
        this.lugares = lugares;
        this.urlMaps = urlMaps;
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

    public String getUrlInfo() {
        return urlInfo;
    }

    public void setUrlInfo(String urlInfo) {
        this.urlInfo = urlInfo;
    }

    public String getLugares() {
        return lugares;
    }

    public void setLugares(String lugares) {
        this.lugares = lugares;
    }

    public String getUrlMaps() {
        return urlMaps;
    }

    public void setUrlMaps(String urlMaps) {
        this.urlMaps = urlMaps;
    }
}
