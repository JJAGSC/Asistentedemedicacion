package com.medicacion.juanjose.asistentedemedicacion;

import com.medicacion.juanjose.asistentedemedicacion.constantes.G;
import com.medicacion.juanjose.asistentedemedicacion.pojos.Medicamento;
import com.medicacion.juanjose.asistentedemedicacion.proveedor.MedicamentoProveedor;
import com.medicacion.juanjose.asistentedemedicacion.sync.Sincronizacion;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class AddAlarmMed extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{

    EditText medName;
    String medNameUser;
    Button btnAlarm1, btnAlarm2, btnAlarm3, btnAlarm4, btnAlarm5, btnAlarm6;
    int btnPulsado;
    ArrayList<Medicamento>listaAlarmasMedicamento;
    Medicamento medicamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm_med);

        // Creamos la lista que contendrá las alarmas para después guardarlas en la base de datos
        listaAlarmasMedicamento = new ArrayList<>();

        medName = (EditText) findViewById(R.id.etMedName);

        // Botones para configurar las alarmas
        btnAlarm1 = (Button) findViewById(R.id.btnAlarm1);
        btnAlarm2 = (Button) findViewById(R.id.btnAlarm2);
        btnAlarm3 = (Button) findViewById(R.id.btnAlarm3);
        btnAlarm4 = (Button) findViewById(R.id.btnAlarm4);
        btnAlarm5 = (Button) findViewById(R.id.btnAlarm5);
        btnAlarm6 = (Button) findViewById(R.id.btnAlarm6);

        // Añadimos un selector de tiempo al botón donde se podrá elegir la hora y los minutos
        // en formato 24 horas
        btnAlarm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(AddAlarmMed.this
                        , now.get(Calendar.HOUR_OF_DAY)
                        , now.get(Calendar.MINUTE)
                        ,true);
                timePickerDialog.setTitle("Seleccione la hora");
                timePickerDialog.show(getFragmentManager(), "Selector hora");
                btnPulsado = 1;
            }
        });

        btnAlarm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(AddAlarmMed.this
                        , now.get(Calendar.HOUR_OF_DAY)
                        , now.get(Calendar.MINUTE)
                        ,true);
                timePickerDialog.setTitle("Seleccione la hora");
                timePickerDialog.show(getFragmentManager(), "Selector hora");
                btnPulsado = 2;
            }
        });

        btnAlarm3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(AddAlarmMed.this
                        , now.get(Calendar.HOUR_OF_DAY)
                        , now.get(Calendar.MINUTE)
                        ,true);
                timePickerDialog.setTitle("Seleccione la hora");
                timePickerDialog.show(getFragmentManager(), "Selector hora");
                btnPulsado = 3;
            }
        });

        btnAlarm4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(AddAlarmMed.this
                        , now.get(Calendar.HOUR_OF_DAY)
                        , now.get(Calendar.MINUTE)
                        ,true);
                timePickerDialog.setTitle("Seleccione la hora");
                timePickerDialog.show(getFragmentManager(), "Selector hora");
                btnPulsado = 4;
            }
        });

        btnAlarm5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(AddAlarmMed.this
                        , now.get(Calendar.HOUR_OF_DAY)
                        , now.get(Calendar.MINUTE)
                        ,true);
                timePickerDialog.setTitle("Seleccione la hora");
                timePickerDialog.show(getFragmentManager(), "Selector hora");
                btnPulsado = 5;
            }
        });

        btnAlarm6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(AddAlarmMed.this
                        , now.get(Calendar.HOUR_OF_DAY)
                        , now.get(Calendar.MINUTE)
                        ,true);
                timePickerDialog.setTitle("Seleccione la hora");
                timePickerDialog.show(getFragmentManager(), "Selector hora");
                btnPulsado = 6;
            }
        });


        // Botón que crea las alarmas
        Button createAlarm = (Button) findViewById(R.id.btnCreateAlarm);

        createAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validar();

                attemptGuardarAlarmasBaseDatos();
            }
        });
    }


    private void validar(){
        medName.setError(null);

        medNameUser = medName.getText().toString();

        if (TextUtils.isEmpty(medNameUser)) {
            medName.setError(getString(R.string.error_campo_obligatorio));
            medName.requestFocus();

        } else {
            Toast.makeText(getApplicationContext(), "Alarmas creadas con éxito", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

        Toast.makeText(this, String.format("%02d:%02d", hourOfDay, minute), Toast.LENGTH_SHORT).show();

        String hourFormat = String.format("%02d:%02d", hourOfDay, minute);

        switch (btnPulsado){
            case 1:
                btnAlarm1.setText(hourFormat);
                break;
            case 2:
                btnAlarm2.setText(hourFormat);
                break;
            case 3:
                btnAlarm3.setText(hourFormat);
                break;
            case 4:
                btnAlarm4.setText(hourFormat);
                break;
            case 5:
                btnAlarm5.setText(hourFormat);
                break;
            case 6:
                btnAlarm6.setText(hourFormat);
                break;
        }

        medicamento = new Medicamento(String.valueOf(medName.getText()), String.format("%02d:%02d", hourOfDay, minute));
        listaAlarmasMedicamento.add(medicamento);
    }

    void attemptGuardarAlarmasBaseDatos(){
//        etMedicamentoNombre = (EditText) findViewById(R.id.etMedicamentoNombre);
//        etMedicamentoHora = (EditText) findViewById(R.id.etMedicamentoHora);
//
//        etMedicamentoNombre.setError(null);
//        etMedicamentoHora.setError(null);
//
//        String nombre = String.valueOf(etMedicamentoNombre.getText());
//        String hora = String.valueOf(etMedicamentoHora.getText());
//
//        if (TextUtils.isEmpty(nombre)){
//            etMedicamentoNombre.setError(getString(R.string.error_campo_obligatorio));
//            etMedicamentoNombre.requestFocus();
//        }
//
//        if (TextUtils.isEmpty(hora)){
//            etMedicamentoHora.setError(getString(R.string.error_campo_obligatorio));
//            etMedicamentoHora.requestFocus();
//        }

        //String nombre = String.valueOf(medName.getText());

        //Medicamento medicamento = new Medicamento(G.SIN_VALOR_INT, nombre, hora);

        for (Medicamento medicamento:listaAlarmasMedicamento) {
            medicamento.setID(G.SIN_VALOR_INT);
            MedicamentoProveedor.insertRecordConBitacora(getContentResolver(), medicamento, this);
        }


        //MedicamentoProveedor.insertRecordConBitacora(getContentResolver(), medicamento, this);
        //finish();
    }
}
