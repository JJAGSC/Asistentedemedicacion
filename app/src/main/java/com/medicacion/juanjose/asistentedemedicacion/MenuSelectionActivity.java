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
import com.medicacion.juanjose.asistentedemedicacion.pojos.Medicamento;
import com.medicacion.juanjose.asistentedemedicacion.proveedor.MedicamentoProveedor;

import java.util.ArrayList;
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
                Intent intent = new Intent(getApplicationContext(), AddAlarmMed.class);
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
                Intent intent = new Intent(getApplicationContext(), UserNearClinicMap.class);
                startActivity(intent);
            }
        });

    }

    protected void onResume() {
        super.onResume();
        iniciarAlarmas();
    }

    // El sistema iniciará automáticamente las alarmas almacenadas
    private void iniciarAlarmas() {

        AlarmManager managerAlarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();

        // Lista con todas las alarmas creadas
        ArrayList<PendingIntent> listaPendingIntent = new ArrayList<>();

        ArrayList<Medicamento> listaAlarmasGuardadas;

        listaAlarmasGuardadas = MedicamentoProveedor.readAllRecord(getContentResolver());

        if (listaAlarmasGuardadas.size() > 0) {

            for (int i = 0; i < listaAlarmasGuardadas.size(); i++) {

                int horaAlarm = listaAlarmasGuardadas.get(i).getHoraNum();
                int minutoAlarm = listaAlarmasGuardadas.get(i).getMinuteNum();

                calendar.set(
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH),
                        horaAlarm,
                        minutoAlarm,
                        0);


                if (calendar.getTimeInMillis() > (System.currentTimeMillis() + 5000)) {
                    Intent intent = new Intent(this, MyAlarm.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(this, i, intent, 0);

                    managerAlarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

                    listaPendingIntent.add(pendingIntent);

                    if (minutoAlarm<10){
                        Toast.makeText(this, "Alarma " + horaAlarm + ":0" + minutoAlarm + " activada", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Alarma " + horaAlarm + ":" + minutoAlarm + " activada", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        } else {
            Toast.makeText(this, "No hay alarmas almacenadas", Toast.LENGTH_SHORT).show();
        }

    }

}
