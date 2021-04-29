package com.example.places_cali_jaime_cardona;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Lugar {

    private String nombre;
    private String pathImagen;
    private double score;
    private Bitmap image;
    private ArrayList<Double> calificaciones;



    public Lugar(){

    }

    public Lugar(String nombre, String pathImagen, double score, Bitmap image) {
        this.nombre = nombre;
        this.pathImagen = pathImagen;
        this.score = 0;
        this.image = image;
        calificaciones = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getPathImagen() {
        return pathImagen;
    }

    public double getScore() {
        return score;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPathImagen(String pathImagen) {
        this.pathImagen = pathImagen;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public ArrayList<Double> getCalificaciones() {
        return calificaciones;
    }

    public void addNewCalificacion(double newCalificacion){
        calificaciones.add(newCalificacion);
    }

    public void changeScore(){
        double acumulado = 0;
        for(int i=0;i<calificaciones.size();i++){
            acumulado += calificaciones.get(i);
        }
        setScore(acumulado/calificaciones.size());
    }



}
