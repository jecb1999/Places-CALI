package com.example.places_cali_jaime_cardona;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.lang.reflect.Array;

public class HomeActivity extends AppCompatActivity  implements SearchFragment.MoveToMapListener {

    private MapsFragment mapFragment;
    private MenuFragment menuFragment;
    private SearchFragment searchFragment;
    private BottomNavigationView navigationView;
    private ListenerZoom listenerZoom;

    public static final int PERMISSION_CALLBACK = 11;
    private static final int CAMERA_CALLBACK = 12;
    private static final int GALLERY_CALLBACK = 13;

    public static int getPermissionCallback() {
        return PERMISSION_CALLBACK;
    }

    public static int getCameraCallback() {
        return CAMERA_CALLBACK;
    }

    public static int getGalleryCallback() {
        return GALLERY_CALLBACK;
    }

    public void setListenerZoom(ListenerZoom listenerZoom) {
        this.listenerZoom = listenerZoom;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navigationView = findViewById(R.id.navigationView);

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        }, PERMISSION_CALLBACK);

        menuFragment = menuFragment.newInstance(this);
        mapFragment = mapFragment.newInstance();
        searchFragment = searchFragment.newInstance();

        menuFragment.setListener(searchFragment);
        menuFragment.setMarkerListener(mapFragment);
        searchFragment.setListener(this);
        mapFragment.setSearchFragment(searchFragment);
        this.setListenerZoom(mapFragment);




        showFragment(menuFragment);

        navigationView.setOnNavigationItemSelectedListener(
                (item)->{
                    switch (item.getItemId()){
                        case R.id.agregar:
                            showFragment(menuFragment);
                            break;
                        case R.id.mapa:
                            mapFragment.setZoom(false);
                            showFragment(mapFragment);
                            break;
                        case R.id.buscar:
                            showFragment(searchFragment);
                            break;
                    }
                    return true;
                }
        );

    }

    public void showFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentView, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_CALLBACK && resultCode == RESULT_OK) {
            Log.e(">>>>", "hola" );
            Bitmap image = BitmapFactory.decodeFile(menuFragment.getFile().getPath());
            Bitmap thumbnail = Bitmap.createScaledBitmap(
                    image, image.getWidth() / 14, image.getHeight() / 14, true
            );
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            Bitmap rotatedBitmap = Bitmap.createBitmap(thumbnail, 0, 0, thumbnail.getWidth(), thumbnail.getHeight(), matrix, true);
            Log.e(">>>>", "" + rotatedBitmap);
            menuFragment.getImagenLugar().setImageBitmap(rotatedBitmap);
        }
        else if(requestCode == GALLERY_CALLBACK && resultCode == RESULT_OK){
            Uri uri = data.getData();
            Log.e(">>>",uri+"");
            String path = UtilDomi.getPath(this, uri);
            menuFragment.setFile(new File(path));
            Log.e(">>>",path+"");
            Bitmap image = BitmapFactory.decodeFile(path);
            Bitmap thumbnail = Bitmap.createScaledBitmap(
                    image, image.getWidth() / 14, image.getHeight() / 14, true
            );
            Matrix matrix = new Matrix();
            Bitmap rotatedBitmap = Bitmap.createBitmap(thumbnail, 0, 0, thumbnail.getWidth(), thumbnail.getHeight(), matrix, true);
            menuFragment.getImagenLugar().setImageBitmap(rotatedBitmap);
        }
    }


    @Override
    public void moveToMapFregment(String nombre) {
        mapFragment.setZoom(true);
        listenerZoom.zoomEnEspecifico(nombre);
        showFragment(mapFragment);

    }

    public interface ListenerZoom{
        void zoomEnEspecifico(String nombre);
    }
}