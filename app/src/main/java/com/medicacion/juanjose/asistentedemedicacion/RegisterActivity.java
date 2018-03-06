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
        etPassRegRep = (EditText) findViewById(R.id.etPassRegRep);

        Button registrarUsuario = (Button) findViewById(R.id.btnRegisterUser);

        // Código para hacer pruebas y ver cuantos usuarios hay
        /*ArrayList<Usuario> listaUsuariosGuardados;
        listaUsuariosGuardados = UsuarioProveedor.readAllRecord(getContentResolver());

        if (listaUsuariosGuardados.size() > 0) {

            for (int i = 0; i < listaUsuariosGuardados.size(); i++) {

                String nombreUsuario = listaUsuariosGuardados.get(i).getNombre();
                String passwordUsuario = listaUsuariosGuardados.get(i).getPassword();

                Toast.makeText(getApplicationContext(), nombreUsuario+" "+passwordUsuario, Toast.LENGTH_SHORT).show();
            }
        }*/

        registrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validarCampos()) {

                    crearUsuario();

                } else {

                    Toast.makeText(getApplicationContext(), "Error al intentar crear el usuario. Datos incorrectos", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private boolean validarCampos() {

        boolean datosCorrectos = true;

        etNameReg.setError(null);
        etPassReg.setError(null);
        etPassRegRep.setError(null);

        String userName = etNameReg.getText().toString();
        String userPassReg = etPassReg.getText().toString();
        String userPassRegRep = etPassRegRep.getText().toString();

        // Comprobamos que los campos no estén vacíos
        if (TextUtils.isEmpty(userName)) {
            etNameReg.setError(getString(R.string.error_campo_obligatorio));
            etNameReg.requestFocus();
            datosCorrectos = false;
        }

        if (TextUtils.isEmpty(userPassReg)) {
            etPassReg.setError(getString(R.string.error_campo_obligatorio));
            etPassReg.requestFocus();
            datosCorrectos = false;
        }

        if (TextUtils.isEmpty(userPassRegRep)) {
            etPassRegRep.setError(getString(R.string.error_campo_obligatorio));
            etPassRegRep.requestFocus();
            datosCorrectos = false;
        }

        if (!datosCorrectos) {
            return false;
        }

        // Comprobamos que la contraseña sea igual en los dos campos

        if (!userPassReg.equals(userPassRegRep)) {

            etPassReg.setError(getString(R.string.error_password_distinto));
            etPassRegRep.setError(getString(R.string.error_password_distinto));
            etPassReg.requestFocus();

            datosCorrectos = false;
        }

        return datosCorrectos;
    }

    private void crearUsuario() {

        Log.d("prueba", "Registrar usuario");

        String nombreUsuario = etNameReg.getText().toString();

        // Comprobamos si el usuario existe en la base de datos
        if (!comprobarSiExisteUsuario()){

            Usuario usuario = new Usuario(etNameReg.getText().toString(), etPassReg.getText().toString());

            // Si no existe el usuario, lo guardamos en la base de datos local
            usuario.setID(G.SIN_VALOR_INT);
            UsuarioProveedor.insertRecordConBitacora(getContentResolver(), usuario, getApplicationContext());

            Toast.makeText(getApplicationContext(), "¡Usuario "+nombreUsuario+" creado con éxito!", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getApplicationContext(), "No se ha podido crear. Ya existe un usuario con ese nombre.", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean comprobarSiExisteUsuario() {

        String userNameIntroducido;
        String nombreUsuarioRecibido = "";

        userNameIntroducido = etNameReg.getText().toString();

        ArrayList<Usuario> listaUsuariosGuardados;
        listaUsuariosGuardados = UsuarioProveedor.readAllRecord(getContentResolver());

        if (listaUsuariosGuardados.size() > 0) {
            for (int i = 0; i < listaUsuariosGuardados.size(); i++) {
                nombreUsuarioRecibido = listaUsuariosGuardados.get(i).getNombre();

                // Comprobamos si ya existe el usuario en la base de datos
                if (nombreUsuarioRecibido.equals(userNameIntroducido)){
                    return true;
                }
            }
        }

        return false;
    }
}
