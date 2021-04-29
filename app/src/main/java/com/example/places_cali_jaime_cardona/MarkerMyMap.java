package com.example.places_cali_jaime_cardona;

import com.google.android.gms.maps.model.LatLng;

public class MarkerMyMap {

    private String nombre;
    private LatLng latLng;

    public MarkerMyMap() {
    }

    public MarkerMyMap(String nombre, LatLng latLng) {
        this.nombre = nombre;
        this.latLng = latLng;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
