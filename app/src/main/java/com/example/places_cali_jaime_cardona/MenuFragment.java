package com.example.places_cali_jaime_cardona;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MenuFragment extends Fragment implements View.OnClickListener{

    private String imagePath;
    private ImageButton camera;
    private ImageButton gallery;
    private ImageButton location;
    private Button btnRegistrar;
    private ImageView imagenLugar;
    private HomeActivity homeActivity;
    private File file;
    private MenuListener listener;
    private MarkerListener markerListener;
    private EditText nombreLugar;
    private TextView address;
    private Geocoder geocoder;


    public MenuFragment(HomeActivity homeActivity){
        this.homeActivity = homeActivity;
    }

    public MenuFragment() {
        // Required empty public constructor
    }

    public static MenuFragment newInstance(HomeActivity homeActivity) {
        MenuFragment fragment = new MenuFragment(homeActivity);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_menu, container, false);
        camera = root.findViewById(R.id.btnCamera);
        gallery = root.findViewById(R.id.btnGallery);
        btnRegistrar = root.findViewById(R.id.btnRegistrar);
        imagenLugar = root.findViewById(R.id.fotoSitio);
        nombreLugar = root.findViewById(R.id.inputName);
        address = root.findViewById(R.id.addressText);
        location = root.findViewById(R.id.btnUbication);
        geocoder = new Geocoder(getContext());

        btnRegistrar.setOnClickListener(this);
        gallery.setOnClickListener(this);
        camera.setOnClickListener(this);
        location.setOnClickListener(this);
        return root;
    }

    public ImageButton getCamera() {
        return camera;
    }

    public void setCamera(ImageButton camera) {
        this.camera = camera;
    }

    public ImageButton getGallery() {
        return gallery;
    }

    public void setGallery(ImageButton gallery) {
        this.gallery = gallery;
    }

    public Button getBtnRegistrar() {
        return btnRegistrar;
    }

    public void setBtnRegistrar(Button btnRegistrar) {
        this.btnRegistrar = btnRegistrar;
    }

    public ImageView getImagenLugar() {
        return imagenLugar;
    }

    public void setImagenLugar(ImageView imagenLugar) {
        this.imagenLugar = imagenLugar;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public HomeActivity getHomeActivity() {
        return homeActivity;
    }

    public void setHomeActivity(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
    }

    public void setListener(MenuListener listener){
        this.listener = listener;
    }

     public void setMarkerListener(MarkerListener markerListener){
        this.markerListener = markerListener;
     }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCamera:
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                file = new File(homeActivity.getExternalFilesDir(null) + "/photo.png");
                Log.e(">>>>", "" + file);
                Uri uri = FileProvider.getUriForFile(homeActivity, homeActivity.getPackageName(), file);
                i.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                imagePath = uri.getPath();
                homeActivity.startActivityForResult(i,homeActivity.getCameraCallback());
                break;

            case R.id.btnGallery:
                Intent j = new Intent(Intent.ACTION_GET_CONTENT);
                j.setType("image/*");
                homeActivity.startActivityForResult(j, homeActivity.getGalleryCallback());
                break;

            case R.id.btnRegistrar:
                if(nombreLugar.getText().toString() != "" && file != null && address.getText().toString() != "") {
                    listener.newLugar(nombreLugar.getText().toString(), file.getAbsolutePath());
                    markerListener.newMarker(nombreLugar.getText().toString(), getLatLngFromName(nombreLugar.getText().toString()));
                    Toast.makeText(homeActivity,"Registrado Correctamente",Toast.LENGTH_LONG).show();
                    nombreLugar.setText("");
                    address.setText("");

                }else{
                    Toast.makeText(homeActivity,"Rellene Todos Los Campos",Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.btnUbication:
                LatLng latLng = null;
                latLng = getLatLngFromName(nombreLugar.getText().toString());
                if(latLng != null) {
                    String calle = getAddress(latLng.latitude,latLng.longitude);
                    address.setText(calle);
                }
                break;
        }
    }

    public LatLng getLatLngFromName(String nombreLugar)  {
        LatLng latLng = null;
        try {
        int maxResultados = 1;
        List<Address> adress = geocoder.getFromLocationName(nombreLugar, maxResultados);
        if(adress.size() == 1) {
            latLng = new LatLng(adress.get(0).getLatitude(), adress.get(0).getLongitude());
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return latLng;
    }

    private String getAddress(double lat, double lon){
        String address = "";
        try {
        List<Address> addresses;
        geocoder = new Geocoder(getContext(), Locale.getDefault());
        addresses = geocoder.getFromLocation(lat, lon, 1);
        address = addresses.get(0).getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }


    public interface MenuListener{
        void newLugar(String nombre, String pathImage);
    }

    public interface MarkerListener{
        void newMarker(String nombre, LatLng latLng);
    }

}