package com.medicacion.juanjose.asistentedemedicacion;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class ClinicMap extends AppCompatActivity {

    EditText etSearchCl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_map);

        etSearchCl = (EditText)findViewById(R.id.etSearchClinic);

        Button btnSearchCl = (Button)findViewById(R.id.btnSearchClinic);

        btnSearchCl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validar();
            }
        });

    }

    private void validar(){

        boolean datosCorrectos = true;

        etSearchCl.setError(null);

        String searchCl = etSearchCl.getText().toString();

        if (TextUtils.isEmpty(searchCl)) {
            etSearchCl.setError(getString(R.string.error_campo_obligatorio));
            etSearchCl.requestFocus();
            datosCorrectos = false;
        }

        if (etSearchCl.length()>10){
            etSearchCl.setError(getString(R.string.error_codigo_postal));
            etSearchCl.requestFocus();
            datosCorrectos = false;
        }

        if (datosCorrectos){
            Toast.makeText(getApplicationContext(), "¡Se ha localizado la clínica más cercana!", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(getApplicationContext(), "Datos incorrectos", Toast.LENGTH_SHORT).show();
        }

    }
}
