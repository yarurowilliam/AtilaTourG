package com.example.atilaversionbeta.BaseDatos;

public class AtilaBD {
    //CONTRACT ACTIVIDADES
    public static String TABLA_ACTIVIDAD = "actividades";
    public static String CODIGO_ACTIVIDAD = "codigo";
    public static String MUNICIPIO_ACTIVIDAD = "municipio";
    public static String NOMBRE_ACTIVIDAD = "nombre";
    public static String INFO_ACTIVIDAD = "info";
    public static String FOTO_ACTIVIDAD= "foto";
    public static String IMG_DETALLE_ACTIVIDAD = "imageDetalle";
    public static String DESCRIPCION_ACTIVIDAD = "descripcion";

    //CONTRACT EVENTOS
    public static String TABLA_EVENTO = "eventos";
    public static String CODIGO_EVENTO = "codigo";
    public static String MUNICIPIO_EVENTO = "municipio";
    public static String NOMBRE_EVENTO = "nombre";
    public static String INFO_EVENTO = "info";
    public static String FOTO_EVENTO= "foto";
    public static String IMG_DETALLE_EVENTO = "imageDetalle";
    public static String DESCRIPCION_EVENTO = "descripcion";



    //CREAR TABLA
    public static final  String CREAR_TABLA_ACTIVIDAD="CREATE TABLE" +
            " " + TABLA_ACTIVIDAD + " (" +CODIGO_ACTIVIDAD+" "+
            "INTEGER,"+ MUNICIPIO_ACTIVIDAD+" " +" TEXT," +  NOMBRE_ACTIVIDAD+" " +" TEXT,"+ INFO_ACTIVIDAD+" " +" TEXT,"
            + FOTO_ACTIVIDAD+" " +" INTEGER,"+IMG_DETALLE_ACTIVIDAD+" "+ "INTEGER,"+DESCRIPCION_ACTIVIDAD+" "+ "TEXT)";

    //CREAR TABLA
    public static final  String CREAR_TABLA_EVENTO="CREATE TABLE" +
            " " + TABLA_EVENTO + " (" +CODIGO_EVENTO+" "+
            "INTEGER,"+ MUNICIPIO_EVENTO+" " +" TEXT," +  NOMBRE_EVENTO+" " +" TEXT,"+ INFO_EVENTO+" " +" TEXT,"
            + FOTO_EVENTO+" " +" INTEGER,"+IMG_DETALLE_EVENTO+" "+ "INTEGER,"+DESCRIPCION_EVENTO+" "+ "TEXT)";
}
