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

public class AddAlarm extends AppCompatActivity {

    Activity context;
    EditText medName;
    String medNameUser;
    RadioGroup radioGroupDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        context = this;
        medName = (EditText) findViewById(R.id.etMedName);


        Button createAlarm = (Button) findViewById(R.id.btnCreateAlarm);
        radioGroupDays = (RadioGroup) findViewById(R.id.rGroupDays);

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

        if (radioGroupDays.getCheckedRadioButtonId()==-1){
            Toast.makeText(getApplicationContext(), "Error, no ha seleccionada la duración", Toast.LENGTH_SHORT).show();
            return;
        }


        if (TextUtils.isEmpty(medNameUser)) {
            medName.setError(getString(R.string.error_campo_obligatorio));
            medName.requestFocus();

        } else {

            Toast.makeText(getApplicationContext(), "Alarma creada con éxito (no funcional todavía desde el menú anterior)", Toast.LENGTH_SHORT).show();

            Intent openAlarms = new Intent(getApplicationContext(), MenuSelectionActivity.class);
            startActivity(openAlarms);
        }
    }
}
