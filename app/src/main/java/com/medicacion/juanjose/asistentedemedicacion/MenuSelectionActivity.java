package com.medicacion.juanjose.asistentedemedicacion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.medicacion.juanjose.asistentedemedicacion.medicamento.MedicamentoActivity;


public class MenuSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_selection);

        Button addMed = (Button) findViewById(R.id.addMed);
        Button manageAlarms = (Button) findViewById(R.id.gestionarAlarmas);
        Button buttonClinic = (Button) findViewById(R.id.centroSalud);

        addMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddAlarmMed.class);
                startActivity(intent);
            }
        });

        manageAlarms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openAlarms = new Intent(getApplicationContext(), MedicamentoActivity.class);

                startActivity(openAlarms);
            }
        });

        buttonClinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getApplicationContext(), ClinicMap.class);
                startActivity(intent);
            }
        });

    }

}
