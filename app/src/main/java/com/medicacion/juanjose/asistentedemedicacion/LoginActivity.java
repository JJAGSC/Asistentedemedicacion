package com.medicacion.juanjose.asistentedemedicacion;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.medicacion.juanjose.asistentedemedicacion.constantes.G;
import com.medicacion.juanjose.asistentedemedicacion.pojos.Usuario;
import com.medicacion.juanjose.asistentedemedicacion.proveedor.UsuarioProveedor;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    EditText etNameR;
    EditText etPassR;
    Activity context;
    ArrayList<Usuario> listaUsuariosGuardados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this;

        etNameR = (EditText) findViewById(R.id.etName);
        etPassR = (EditText) findViewById(R.id.etPassw);



        // Se comprueba si los datos son correctos antes de hacer el login
        Button btnEnter = (Button) findViewById(R.id.btnEntrar);
        btnEnter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                // Comprobamos si los datos de las casillas de texto son correctos
                if (validarDatosCasillas()){

                    // Si los datos son válidos comprobamos si existe el usuario y si existe accedemos
                    if(comprobarUsuario()){

                        // Almacenamos el nombre de usuario como una constante ya que lo utilizaremos varias veces
                        // y queremos que sea fijo
                        G.usuarioalarma = etNameR.getText().toString();

                        Intent abrirVentanaMenu = new Intent(context, MenuSelectionActivity.class);
                        startActivity(abrirVentanaMenu);
                    }

                }

            }
        });

        // Permite al usuario registrarse con un nombre de usuario y una contraseña
        Button botonRegistrar = (Button) findViewById(R.id.btnRegister);
        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abrirVentanaRegistro = new Intent (context, RegisterActivity.class);
                startActivity(abrirVentanaRegistro);
            }
        });

        // Permite realizar una llamada al 112, número de emergencia
        ImageView botonEmergencia = (ImageView) findViewById(R.id.imgEmergency);
        botonEmergencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeCall("112");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Almacenamos los usuarios contenidos en la base de datos en el array para poder consultarlos
        listaUsuariosGuardados = UsuarioProveedor.readAllRecord(getContentResolver());
    }

    private void makeCall(String phoneNumber){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null));
        startActivity(intent);
    }

    private boolean validarDatosCasillas() {

        boolean datosCorrectos = true;

        etNameR.setError(null);
        etPassR.setError(null);

        String userName = etNameR.getText().toString();
        String userPass = etPassR.getText().toString();

        if (TextUtils.isEmpty(userName)) {
            etNameR.setError(getString(R.string.error_campo_obligatorio));
            etNameR.requestFocus();
            datosCorrectos = false;
        }

        if (TextUtils.isEmpty(userPass)) {
            etPassR.setError(getString(R.string.error_campo_obligatorio));
            etPassR.requestFocus();
            datosCorrectos = false;
        }

        return datosCorrectos;
    }

    private boolean comprobarUsuario() {

        String userNameIntroducido;
        String passIntroducida;
        String nombreUsuarioRecibido = "";
        String passUsuarioRecibida = "";

        boolean usuarioEncontrado = false;

        userNameIntroducido = etNameR.getText().toString();
        passIntroducida = etPassR.getText().toString();

        if (listaUsuariosGuardados.size() > 0) {
            for (int i = 0; i < listaUsuariosGuardados.size(); i++) {
                nombreUsuarioRecibido = listaUsuariosGuardados.get(i).getNombre();
                passUsuarioRecibida = listaUsuariosGuardados.get(i).getPassword();

                // Comprobamos si existe el usuario en la base de datos
                if (nombreUsuarioRecibido.equalsIgnoreCase(userNameIntroducido)){

                    usuarioEncontrado = true;

                    // Comprobamos si la contraseña es correcta
                    if (passUsuarioRecibida.equals(passIntroducida)){

                        Toast.makeText(getApplicationContext(), "¡Datos correctos!", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
            }
        }

        // Si no se encuentra el usuario, lo indicamos
        if (!usuarioEncontrado){
            etNameR.setError(getString(R.string.usuario_no_encontrado));
            etNameR.requestFocus();

        } else {
            // Si lo encontramos pero la contraseña no es correcta, lo indicamos
            etPassR.setError(getString(R.string.password_incorrecto));
            etPassR.requestFocus();
        }

        return false;
    }
}
