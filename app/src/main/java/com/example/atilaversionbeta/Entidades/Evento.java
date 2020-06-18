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
    private int dia;
    private int mes;
    private int ano;



    public Evento(){}

    public Evento(int codigo, String municipio, String nombre, int foto, int imagenDetalle, String descripcion, String linkInfo, int dia, int mes, int ano) {
        this.codigo = codigo;
        this.municipio = municipio;
        this.nombre = nombre;
        this.foto = foto;
        this.imagenDetalle = imagenDetalle;
        this.descripcion = descripcion;
        this.linkInfo = linkInfo;
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
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

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }
}
