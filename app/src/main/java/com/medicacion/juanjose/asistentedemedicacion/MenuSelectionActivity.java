package com.medicacion.juanjose.asistentedemedicacion;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.medicacion.juanjose.asistentedemedicacion.medicamento.MedicamentoActivity;

import java.util.Calendar;


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

    protected void onResume(){
        super.onResume();
        iniciarAlarmas();
    }

    // El sistema iniciará automáticamente las alarmas almacenadas
    private void iniciarAlarmas() {

        for(int i = 1; i<3; i++){
            Calendar calendar = Calendar.getInstance();

            calendar.set(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH),
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE)+i,
                    0);

            setAlarm(calendar.getTimeInMillis(), i);
        }

    }

    // Alarma que se activará cuando llegue la hora indicada
    private void setAlarm(long timeInMillis, int numAlarma) {

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent (this, MyAlarm.class);
        intent.setAction(""+numAlarma);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, numAlarma, intent, 0);
        alarmManager.setRepeating(AlarmManager.RTC, timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent);

        Toast.makeText(this, "Alarmas del usuario activadas", Toast.LENGTH_SHORT).show();
    }
}
