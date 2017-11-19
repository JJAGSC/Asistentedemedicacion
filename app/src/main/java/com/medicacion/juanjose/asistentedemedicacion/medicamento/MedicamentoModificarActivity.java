package com.medicacion.juanjose.asistentedemedicacion.medicamento;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.medicacion.juanjose.asistentedemedicacion.R;
import com.medicacion.juanjose.asistentedemedicacion.constantes.G;
import com.medicacion.juanjose.asistentedemedicacion.constantes.Utilidades;
import com.medicacion.juanjose.asistentedemedicacion.pojos.Medicamento;
import com.medicacion.juanjose.asistentedemedicacion.proveedor.Contrato;
import com.medicacion.juanjose.asistentedemedicacion.proveedor.MedicamentoProveedor;

import java.io.FileNotFoundException;

public class MedicamentoModificarActivity extends AppCompatActivity {

    EditText etMedicamentoNombre;
    EditText etMedicamentoFormato;

    int medID;

    final int PETICION_SACAR_FOTO = 1;
    final int PETICION_GALERIA = 2;

    ImageView imgMed;
    Bitmap foto = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento_add);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_add_activity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etMedicamentoNombre = (EditText) findViewById(R.id.etMedicamentoNombre);
        etMedicamentoFormato = (EditText) findViewById(R.id.etMedicamentoFormato);

        medID = this.getIntent().getExtras().getInt(Contrato.Medicamento._ID);
        Medicamento medicamento = MedicamentoProveedor.readRecord(getContentResolver(), medID);

        etMedicamentoNombre.setText(medicamento.getNombre());
        etMedicamentoFormato.setText(medicamento.getFormato());

        imgMed = (ImageView) findViewById(R.id.imgAddMed);

        try {
            Utilidades.loadImageFromStorage(this, "img_"+ medID + ".jpg", imgMed);
            foto = ((BitmapDrawable) imgMed.getDrawable()).getBitmap();
        } catch (FileNotFoundException e) {
            // No existe imagen para este medicamento
        }

        ImageButton imgButtonCamera = (ImageButton) findViewById(R.id.imageButtonCamera);
        imgButtonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sacarFoto();
            }
        });

        ImageButton imgButtonGallery = (ImageButton) findViewById(R.id.imageButtonGallery);
        imgButtonGallery.setOnClickListener(new View.OnClickListener() {
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

                    foto = (Bitmap) data.getExtras().get("data");
                    imgMed.setImageBitmap(foto);

                } else {
                    // El usuario hizo click en cancelar
                }
                break;

            case PETICION_GALERIA:
                if(resultCode == RESULT_OK){

                    Uri uri = data.getData();
                    imgMed.setImageURI(uri);
                    foto = ((BitmapDrawable) imgMed.getDrawable()).getBitmap();

                } else {
                    // El usuario hizo click en cancelar
                }
                break;
        }

        //super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.add(Menu.NONE, G.GUARDAR, Menu.NONE, "Guardar");
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menuItem.setIcon(R.drawable.ic_action_guardar);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case G.GUARDAR:
                attemptGuardar();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    void attemptGuardar(){

        etMedicamentoNombre.setError(null);
        etMedicamentoFormato.setError(null);

        String nombre = String.valueOf(etMedicamentoNombre.getText());
        String formato = String.valueOf(etMedicamentoFormato.getText());

        if (TextUtils.isEmpty(nombre)){
            etMedicamentoNombre.setError(getString(R.string.error_campo_obligatorio));
            etMedicamentoNombre.requestFocus();
        }

        if (TextUtils.isEmpty(formato)){
            etMedicamentoFormato.setError(getString(R.string.error_campo_obligatorio));
            etMedicamentoFormato.requestFocus();
        }

        Medicamento medicamento = new Medicamento(medID, nombre, formato, foto);

        MedicamentoProveedor.updateRecord(getContentResolver(), medicamento, this);
        finish();
    }
}
