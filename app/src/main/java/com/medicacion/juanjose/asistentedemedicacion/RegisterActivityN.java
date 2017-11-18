package com.medicacion.juanjose.asistentedemedicacion;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivityN extends AppCompatActivity {

    EditText etNameReg;
    EditText etPassReg;
    EditText etEdadReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_n);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etNameReg = (EditText) findViewById(R.id.etNameReg);
        etPassReg = (EditText) findViewById(R.id.etPassReg);
        etEdadReg = (EditText) findViewById(R.id.etAgeReg);

        Button registrarUsuario = (Button) findViewById(R.id.btnRegisterUser);

        registrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validar();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_register_activity_n, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings2) {
            Toast.makeText(getApplicationContext(), "Ajustes no implementados todavía", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void validar(){

        boolean datosCorrectos = true;

        etNameReg.setError(null);
        etPassReg.setError(null);
        etEdadReg.setError(null);

        String userName = etNameReg.getText().toString();
        String userPass = etPassReg.getText().toString();
        String userAge = etEdadReg.getText().toString();

        if (TextUtils.isEmpty(userName)) {
            etNameReg.setError(getString(R.string.error_campo_obligatorio));
            etNameReg.requestFocus();
            datosCorrectos = false;
        }

        if (TextUtils.isEmpty(userPass)) {
            etPassReg.setError(getString(R.string.error_campo_obligatorio));
            etPassReg.requestFocus();
            datosCorrectos = false;
        }

        if (TextUtils.isEmpty(userAge)) {
            etEdadReg.setError(getString(R.string.error_campo_obligatorio));
            etEdadReg.requestFocus();
            return;
        }

        int edad = Integer.parseInt(userAge);

        if (edad<1 || edad>110){
            etEdadReg.setError(getString(R.string.error_edad));
            etEdadReg.requestFocus();
            return;
        }

        if (datosCorrectos){
            Toast.makeText(getApplicationContext(), "¡Usuario creado con éxito! (modo test, el usuario " +
                    "no es funcional todavía)", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(getApplicationContext(), "No se ha podido crear el usuario, faltan datos " +
                    "o no son correctos", Toast.LENGTH_LONG).show();
        }

    }
}
