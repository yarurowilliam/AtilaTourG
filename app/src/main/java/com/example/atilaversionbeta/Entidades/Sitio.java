package com.example.atilaversionbeta.Entidades;

import java.io.Serializable;

public class Sitio implements Serializable {
    private int codigo;
    private String municipio;
    private String tipo;
    private String nombre;
    private int foto;
    private int imagenDetalle;
    private String descripcion;
    private String linkUrl;
    private String linkMaps;

    public Sitio(){}

    public Sitio(int codigo, String municipio, String tipo, String nombre, int foto, int imagenDetalle, String descripcion, String linkUrl, String linkMaps) {
        this.codigo = codigo;
        this.municipio = municipio;
        this.tipo = tipo;
        this.nombre = nombre;
        this.foto = foto;
        this.imagenDetalle = imagenDetalle;
        this.descripcion = descripcion;
        this.linkUrl = linkUrl;
        this.linkMaps = linkMaps;
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

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getLinkMaps() {
        return linkMaps;
    }

    public void setLinkMaps(String linkMaps) {
        this.linkMaps = linkMaps;
    }
}
