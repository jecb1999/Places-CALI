package com.example.places_cali_jaime_cardona;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;


public class SearchFragment extends Fragment implements MenuFragment.MenuListener, LugarAdapter.RowListener{

    private EditText buscarPlace;
    private RecyclerView listaLugares;
    private LinearLayoutManager layoutManager;
    private ArrayList<Lugar> listaLugaresAux;
    private LugarAdapter adapter;
    private MoveToMapListener listener;



    public SearchFragment() {
        listaLugaresAux =  new ArrayList<>();
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ArrayList<Lugar> getListaLugaresAux() {
        return listaLugaresAux;
    }

    public void setListaLugaresAux(ArrayList<Lugar> listaLugaresAux) {
        this.listaLugaresAux = listaLugaresAux;
    }

    public void setListener(MoveToMapListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        buscarPlace = root.findViewById(R.id.SearchLugar);
        buscarPlace.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
        listaLugares = root.findViewById(R.id.listaLugares);
        listaLugares.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getContext());
        listaLugares.setLayoutManager(layoutManager);
        adapter = new LugarAdapter();
        adapter.setListener(this);
        listaLugares.setAdapter(adapter);
        for(int i = 0;i<listaLugaresAux.size();i++){
            adapter.addLugar(listaLugaresAux.get(i));
        }

        return root;
    }

    private void filter(String text) {
        ArrayList<Lugar> listaFiltrada = new ArrayList<>();
        for(Lugar lugar:listaLugaresAux){
            if(lugar.getNombre().toLowerCase().contains(text.toLowerCase())){
                listaFiltrada.add(lugar);
            }
        }
        adapter.filterList(listaFiltrada);
    }

    public Bitmap workInImage(String pathImage){
        Bitmap image = BitmapFactory.decodeFile(pathImage);
        Bitmap thumbnail = Bitmap.createScaledBitmap(
                image, image.getWidth() / 14, image.getHeight() / 14, true
        );
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap rotatedBitmap = Bitmap.createBitmap(thumbnail, 0, 0, thumbnail.getWidth(), thumbnail.getHeight(), matrix, true);
        return rotatedBitmap;
    }

    @Override
    public void newLugar(String nombre, String pathImage) {
            listaLugaresAux.add(new Lugar(nombre,pathImage,0,workInImage(pathImage)));
    }


    @Override
    public void clickInSeeMore(String nombre) {
        Log.e(">>>","clickInSeeMore");
        listener.moveToMapFregment(nombre);
    }



    public interface MoveToMapListener{
        void moveToMapFregment(String nombre);
    }

}