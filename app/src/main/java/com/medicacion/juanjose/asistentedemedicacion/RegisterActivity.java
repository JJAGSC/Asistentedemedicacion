package com.medicacion.juanjose.asistentedemedicacion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.medicacion.juanjose.asistentedemedicacion.constantes.G;
import com.medicacion.juanjose.asistentedemedicacion.pojos.Medicamento;
import com.medicacion.juanjose.asistentedemedicacion.pojos.Usuario;
import com.medicacion.juanjose.asistentedemedicacion.proveedor.MedicamentoProveedor;
import com.medicacion.juanjose.asistentedemedicacion.proveedor.UsuarioProveedor;
import com.medicacion.juanjose.asistentedemedicacion.sync.Sincronizacion;

import java.util.ArrayList;

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
        //etPassRegRep = (EditText) findViewById(R.id.etPassReg);

        Button registrarUsuario = (Button) findViewById(R.id.btnRegisterUser);

        registrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //validar();

                Log.d("prueba", "Entra al hacer click en el botón registrar usuario");

                Usuario usuario = new Usuario(etNameReg.getText().toString(), etPassReg.getText().toString());

                // Guardamos el usuario en la base de datos local
                usuario.setID(G.SIN_VALOR_INT);
                UsuarioProveedor.insertRecordConBitacora(getContentResolver(), usuario, getApplicationContext());

                ArrayList<Usuario> listaUsuariosGuardados;

                listaUsuariosGuardados = UsuarioProveedor.readAllRecord(getContentResolver());

                if (listaUsuariosGuardados.size() > 0) {

                    for (int i = 0; i < listaUsuariosGuardados.size(); i++) {

                        String nombreUsuario = listaUsuariosGuardados.get(i).getNombre();
                        String passwordUsuario = listaUsuariosGuardados.get(i).getPassword();


                        Toast.makeText(getApplicationContext(), nombreUsuario+" "+passwordUsuario, Toast.LENGTH_SHORT).show();


                        // Al crear un usuario, enviamos la actualización al servidor remoto
                        Sincronizacion.enviarActualizacionesAlServidorUsuario();
                    }
                }
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
