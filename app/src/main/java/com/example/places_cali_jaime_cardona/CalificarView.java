package com.example.places_cali_jaime_cardona;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.File;

public class CalificarView extends DialogFragment implements View.OnClickListener {

    private Button calificar;
    private String nombre;
    private Button salir;
    private RatingBar calificacion;
    private TextView nombreLugar;
    private CalificacionListener calificacionListener;

    public CalificarView() {

    }

    public CalificarView(String nombre) {
        this.nombre = nombre;
    }

    public void setCalificacionListener(CalificacionListener calificacionListener) {
        this.calificacionListener = calificacionListener;
    }

    public static CalificarView newInstance() {
        CalificarView fragment = new CalificarView();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_calificar_view, container, false);
        calificar = root.findViewById(R.id.btnCalificar);
        salir = root.findViewById(R.id.btnCalificarDespues);
        calificacion = root.findViewById(R.id.calificacion);
        nombreLugar = root.findViewById(R.id.nombreLugarCalifi);
        nombreLugar.setText(nombre);

        calificar.setOnClickListener(this);
        salir.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnCalificar:
                calificacionListener.nuevaCalificacion(nombreLugar.getText().toString(), calificacion.getRating());
                dismiss();
                break;

            case R.id.btnCalificarDespues:
                dismiss();
                break;
        }

    }

    public interface CalificacionListener{
        void nuevaCalificacion(String nombre, float calificacion);
    }
}