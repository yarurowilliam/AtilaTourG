package com.example.atilaversionbeta.Entidades;

import java.io.Serializable;

public class Actividad implements Serializable {
    private String codigo;
    private String municipio;
    private String nombre;
    private String info;
    private int foto;
    private int imagenDetalle;
    private String descripcion;


    public Actividad(){

    }

    public Actividad(String codigo,String municipio,String nombre, String info, int foto, int imagenDetalle, String descripcion) {
        this.codigo = codigo;
        this.municipio = municipio;
        this.nombre = nombre;
        this.info = info;
        this.foto = foto;
        this.imagenDetalle = imagenDetalle;
        this.descripcion = descripcion;
    }


    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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

}
