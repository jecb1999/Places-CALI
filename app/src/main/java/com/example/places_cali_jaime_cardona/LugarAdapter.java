package com.example.places_cali_jaime_cardona;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LugarAdapter extends RecyclerView.Adapter<LugarView> implements LugarView.ButtonEyeLister {

    private ArrayList<Lugar> lugaresList;
    private RowListener listener;


    public LugarAdapter(){
        lugaresList = new ArrayList<>();
    }

    public void setListener(RowListener listener) {
        this.listener = listener;
    }

    public void addLugar(Lugar lugares){
        lugaresList.add(lugares);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LugarView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.lugarrow, null);
        ConstraintLayout rowroot = (ConstraintLayout) row;
        LugarView lugarView = new LugarView(rowroot);
        lugarView.setLister(this);
        return lugarView;
    }

    @Override
    public void onBindViewHolder(@NonNull LugarView holder, int position) {
        holder.getNombre().setText(lugaresList.get(position).getNombre());
        if(lugaresList.get(position).getCalificaciones().isEmpty()==true){
            holder.getScore().setText("NC");
        }else {
            holder.getScore().setText(String.valueOf(lugaresList.get(position).getScore()));
        }
        holder.getLugarImage().setImageBitmap(lugaresList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return lugaresList.size();
    }

    @Override
    public void clickEye(String nombre) {
        Log.e(">>>","ClickEye");
        listener.clickInSeeMore(nombre);
    }

    public void filterList(ArrayList<Lugar> listaFiltrada) {
        lugaresList = listaFiltrada;
        notifyDataSetChanged();
    }


    public interface RowListener{
        void clickInSeeMore(String nombreLugar);
    }







}
