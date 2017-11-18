package com.medicacion.juanjose.asistentedemedicacion.medicamento;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.medicacion.juanjose.asistentedemedicacion.R;
import com.medicacion.juanjose.asistentedemedicacion.constantes.G;
import com.medicacion.juanjose.asistentedemedicacion.pojos.Medicamento;
import com.medicacion.juanjose.asistentedemedicacion.proveedor.Contrato;
import com.medicacion.juanjose.asistentedemedicacion.proveedor.MedicamentoProveedor;

public class MedicamentoModificarActivity extends AppCompatActivity {

    EditText etMedicamentoNombre;
    EditText etMedicamentoFormato;
    int cicloID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento_add);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_add_activity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etMedicamentoNombre = (EditText) findViewById(R.id.etMedicamentoNombre);
        etMedicamentoFormato = (EditText) findViewById(R.id.etMedicamentoFormato);

        cicloID = this.getIntent().getExtras().getInt(Contrato.Medicamento._ID);
        Medicamento medicamento = MedicamentoProveedor.readRecord(getContentResolver(), cicloID);

        etMedicamentoNombre.setText(medicamento.getNombre());
        etMedicamentoFormato.setText(medicamento.getFormato());

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

        Medicamento medicamento = new Medicamento(cicloID, nombre, formato);

        MedicamentoProveedor.updateRecord(getContentResolver(), medicamento);
        finish();
    }
}
