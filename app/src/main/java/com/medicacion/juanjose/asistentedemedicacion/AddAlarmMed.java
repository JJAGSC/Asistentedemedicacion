package com.medicacion.juanjose.asistentedemedicacion;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AddAlarmMed extends AppCompatActivity {

    EditText medName;
    String medNameUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm_med);

        medName = (EditText) findViewById(R.id.etMedName);

        Button createAlarm = (Button) findViewById(R.id.btnCreateAlarm);

        createAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validar();
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
            Toast.makeText(getApplicationContext(), "Alarmas creadas con éxito (no funcional todavía)", Toast.LENGTH_SHORT).show();
        }
    }
}
