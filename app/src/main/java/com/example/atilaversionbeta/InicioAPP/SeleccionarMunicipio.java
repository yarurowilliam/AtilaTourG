package com.example.atilaversionbeta.InicioAPP;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.atilaversionbeta.MainActivity;
import com.example.atilaversionbeta.R;

public class SeleccionarMunicipio extends AppCompatActivity {
    private ImageButton boton,botonBosconia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_municipio);
        boton = (ImageButton) findViewById(R.id.button);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.municipioBuscado = "Valledupar";
                Intent intent = new Intent(SeleccionarMunicipio.this, MainActivity.class); //ELEGIMOS LA ACTIVITY QUE QUEREMOS EJECUTAR
                startActivity(intent);
                finish();
            }
        });

        botonBosconia = (ImageButton) findViewById(R.id.buttonManaure);
        botonBosconia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.municipioBuscado = "Manaure";
                Intent intent = new Intent(SeleccionarMunicipio.this, MainActivity.class); //ELEGIMOS LA ACTIVITY QUE QUEREMOS EJECUTAR
                startActivity(intent);
                finish();
            }
        });

    }
}
