package com.medicacion.juanjose.asistentedemedicacion;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.medicacion.juanjose.asistentedemedicacion.constantes.Utilidades;
import com.medicacion.juanjose.asistentedemedicacion.medicamento.MedicamentoActivity;

import java.io.IOException;

public class ManagePicturesActivity extends AppCompatActivity {

    final int PETICION_SACAR_FOTO = 1;
    final int PETICION_GALERIA = 2;
    ImageView imgCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_pictures);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button btnBD = (Button) findViewById(R.id.btnBD);
        btnBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MedicamentoActivity.class);
                startActivity(intent);
            }
        });

        Button btnCamera = (Button) findViewById(R.id.btnMakePicture);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sacarFoto();
            }
        });

        imgCamera = (ImageView) findViewById(R.id.imgCamera);

        Button btnGallery = (Button) findViewById(R.id.btnGallery);
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                elegirDeGaleria();
            }
        });
    }

    void sacarFoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, PETICION_SACAR_FOTO);
    }

    void elegirDeGaleria(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, PETICION_GALERIA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){
            case PETICION_SACAR_FOTO:
                if(resultCode==RESULT_OK){
                    Bitmap foto = (Bitmap) data.getExtras().get("data");
                    imgCamera.setImageBitmap(foto);
                    try {
                        Utilidades.storeImage(foto, this, "imagen.jpg");
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(), "No se pudo guardar la imagen", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // El usuario hizo click en cancelar
                }
                break;

            case PETICION_GALERIA:
                if(resultCode == RESULT_OK){
                    Uri uri = data.getData();
                    imgCamera.setImageURI(uri);
                } else {
                    // El usuario hizo click en cancelar
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
