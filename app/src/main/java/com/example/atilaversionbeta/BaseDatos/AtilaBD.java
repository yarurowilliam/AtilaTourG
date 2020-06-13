package com.example.atilaversionbeta.BaseDatos;

public class AtilaBD {

    //CONTRACT ACTIVIDADES
    public static String TABLA_ACTIVIDAD = "actividades";
    public static String CODIGO_ACTIVIDAD = "codigo";
    public static String MUNICIPIO_ACTIVIDAD = "municipio";
    public static String NOMBRE_ACTIVIDAD = "nombre";
    public static String FOTO_ACTIVIDAD= "foto";
    public static String IMG_DETALLE_ACTIVIDAD = "imageDetalle";
    public static String DESCRIPCION_ACTIVIDAD = "descripcion";
    public static String URLINFO_ACTIVIDAD = "urlinfo";
    public static String LUGARES_ACTIVIDAD = "lugares";
    public static String URLMAPS_ACTIVIDAD = "urlmaps";


    //CONTRACT EVENTOS
    public static String TABLA_EVENTO = "eventos";
    public static String CODIGO_EVENTO = "codigo";
    public static String MUNICIPIO_EVENTO = "municipio";
    public static String NOMBRE_EVENTO = "nombre";
    public static String FOTO_EVENTO= "foto";
    public static String IMG_DETALLE_EVENTO = "imageDetalle";
    public static String DESCRIPCION_EVENTO = "descripcion";
    public static String URLINFO_EVENTO= "urlinfo";


    //CONTRACT SITIOS
    public static String TABLA_SITIO = "sitios";
    public static String CODIGO_SITIO = "codigo";
    public static String TIPO_SITIO = "tipo";
    public static String MUNICIPIO_SITIO = "municipio";
    public static String NOMBRE_SITIO = "nombre";
    public static String INFO_SITIO = "info";
    public static String FOTO_SITIO= "foto";
    public static String IMG_DETALLE_SITIO = "imageDetalle";
    public static String DESCRIPCION_SITIO = "descripcion";

    //CONTRACT INFORMACION
    public static String TABLA_INFORMACION = "informacion";
    public static String CODIGO_INFORMACION = "codigo";
    public static String TIPO_INFORMACION = "tipo";
    public static String MUNICIPIO_INFORMACION = "municipio";
    public static String NOMBRE_INFORMACION = "nombre";
    public static String FOTO_INFORMACION= "foto";
    public static String IMG_DETALLE_INFORMACION = "imageDetalle";
    public static String DESCRIPCION_INFORMACION = "descripcion";
    public static String URLINFO_INFORMACION = "urlinfo";


    //CREAR TABLA ACTIVIDAD
    public static final  String CREAR_TABLA_ACTIVIDAD="CREATE TABLE" +
            " " + TABLA_ACTIVIDAD + " (" +CODIGO_ACTIVIDAD+" "+
            "INTEGER,"+ MUNICIPIO_ACTIVIDAD+" " +" TEXT," +  NOMBRE_ACTIVIDAD+" " +" TEXT,"
            + FOTO_ACTIVIDAD+" " +" INTEGER,"+IMG_DETALLE_ACTIVIDAD+" "+ "INTEGER,"+DESCRIPCION_ACTIVIDAD+" "+ "TEXT,"+URLINFO_ACTIVIDAD+" "+ "TEXT,"+LUGARES_ACTIVIDAD+" "+ "TEXT,"+URLMAPS_ACTIVIDAD+" "+ "TEXT)";

    //CREAR TABLA EVENTO
    public static final  String CREAR_TABLA_EVENTO="CREATE TABLE" +
            " " + TABLA_EVENTO + " (" +CODIGO_EVENTO+" "+
            "INTEGER,"+ MUNICIPIO_EVENTO+" " +" TEXT," +  NOMBRE_EVENTO+" " +" TEXT,"
            + FOTO_EVENTO+" " +" INTEGER,"+IMG_DETALLE_EVENTO+" "+ "INTEGER,"+DESCRIPCION_EVENTO+" "+ "TEXT,"+URLINFO_EVENTO+" "+ "TEXT)";

    //CREAR TABLA SITIO
    public static final  String CREAR_TABLA_SITIO="CREATE TABLE" +
            " " + TABLA_SITIO + " (" +CODIGO_SITIO+" "+
            "INTEGER,"+ TIPO_SITIO + " "+" TEXT,"+ MUNICIPIO_SITIO+" " +" TEXT," +  NOMBRE_SITIO+" " +" TEXT,"+ INFO_SITIO+" " +" TEXT,"
            + FOTO_SITIO+" " +" INTEGER,"+IMG_DETALLE_SITIO+" "+ "INTEGER,"+DESCRIPCION_SITIO+" "+ "TEXT)";

    //CREAR TABLA INFORMACION
    public static final  String CREAR_TABLA_INFORMACION="CREATE TABLE" +
            " " + TABLA_INFORMACION + " (" +CODIGO_INFORMACION+" "+
            "INTEGER,"+ TIPO_INFORMACION + " "+" TEXT,"+ MUNICIPIO_INFORMACION+" " +" TEXT," +  NOMBRE_INFORMACION+" " +" TEXT,"
            + FOTO_INFORMACION+" " +" INTEGER,"+IMG_DETALLE_INFORMACION+" "+ "INTEGER,"+DESCRIPCION_INFORMACION+" "+ "TEXT,"+URLINFO_INFORMACION+" "+ "TEXT)";
}
