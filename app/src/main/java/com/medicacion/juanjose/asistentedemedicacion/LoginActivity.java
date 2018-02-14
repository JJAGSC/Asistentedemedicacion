package com.medicacion.juanjose.asistentedemedicacion;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText etNameR;
    EditText etPassR;
    Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this;

        etNameR = (EditText) findViewById(R.id.etName);
        etPassR = (EditText) findViewById(R.id.etPassw);

        Button btnEnter = (Button) findViewById(R.id.btnEntrar);
        btnEnter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                validar();
            }
        });

        Button botonRegistrar = (Button) findViewById(R.id.btnRegister);
        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abrirVentanaRegistro = new Intent (context, RegisterActivity.class);
                startActivity(abrirVentanaRegistro);
            }
        });

        ImageView botonEmergencia = (ImageView) findViewById(R.id.imgEmergency);
        botonEmergencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeCall("112");
            }
        });
    }

    private void makeCall(String phoneNumber){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null));
        startActivity(intent);
    }

    private void validar() {
        etNameR.setError(null);
        etPassR.setError(null);

        String userName = etNameR.getText().toString();
        String userPass = etPassR.getText().toString();

        if (TextUtils.isEmpty(userName)) {
            etNameR.setError(getString(R.string.error_campo_obligatorio));
            etNameR.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(userPass)) {
            etPassR.setError(getString(R.string.error_campo_obligatorio));
            etPassR.requestFocus();
            return;
        }

        if (!userName.equalsIgnoreCase("antonio")) {
            etNameR.setError("Nombre no válido");
            etNameR.requestFocus();
            return;
        } else {
            if (userPass.equalsIgnoreCase("1234")) {
                Intent abrirVentanaMenu = new Intent(context, MenuSelectionActivity.class);
                startActivity(abrirVentanaMenu);

            } else {
                Toast.makeText(getApplicationContext(), "La contraseña no es correcta", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
