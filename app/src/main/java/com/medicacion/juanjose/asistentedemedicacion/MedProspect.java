package com.medicacion.juanjose.asistentedemedicacion;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MedProspect extends AppCompatActivity {

    Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_prospect);

        ImageView botonCamara = (ImageView) findViewById(R.id.btnCamera);

        botonCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Botón no implementado todavía", Toast.LENGTH_SHORT).show();
            }
        });

        Button savedProspects = (Button)findViewById(R.id.btnSavedProspects);

        context = this;

        savedProspects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SavedProspects.class);
                startActivity(intent);
            }
        });
    }
}
