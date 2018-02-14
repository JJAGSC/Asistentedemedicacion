package com.medicacion.juanjose.asistentedemedicacion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText etNameReg;
    EditText etPassReg;
    EditText etPassRegRep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etNameReg = (EditText) findViewById(R.id.etNameReg);
        etPassReg = (EditText) findViewById(R.id.etPassReg);
        etPassRegRep = (EditText) findViewById(R.id.etPassReg);

        Button registrarUsuario = (Button) findViewById(R.id.btnRegisterUser);

        registrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validar();
            }
        });
    }

    private void validar(){

        boolean datosCorrectos = true;

        etNameReg.setError(null);
        etPassReg.setError(null);

        String userName = etNameReg.getText().toString();
        String userPass = etPassReg.getText().toString();

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


        if (datosCorrectos){
            Toast.makeText(getApplicationContext(), "¡Usuario creado con éxito! (modo test, el usuario " +
                    "no es funcional todavía)", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(getApplicationContext(), "No se ha podido crear el usuario, faltan datos " +
                    "o no son correctos", Toast.LENGTH_LONG).show();
        }

    }
}
