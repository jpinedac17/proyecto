package com.example.appproyecto;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    private EditText etNombre, etCorreo, etEdad, etPeso, etEstatura;
    private Spinner spinnerSexo, spinnerNivel, spinnerObjetivo, spinnerDeporte;
    private Button btnComenzar;

    // Arrays para los spinners
    private final String[] sexoOptions = {"Seleccionar", "Masculino", "Femenino", "Otro"};
    private final String[] nivelOptions = {"Seleccionar nivel", "Principiante", "Intermedio", "Avanzado", "Experto"};
    private final String[] objetivoOptions = {"¿Cuál es tu objetivo?", "Perder peso", "Ganar músculo", "Mantenerse en forma", "Mejorar resistencia"};
    private final String[] deporteOptions = {"Elige tu deporte favorito", "Gym", "Running", "Natación", "Ciclismo", "Yoga", "Crossfit"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        initializeViews();
        setupSpinners();
        setupButton();
    }

    private void initializeViews() {
        etNombre = findViewById(R.id.et_nombre);
        etCorreo = findViewById(R.id.et_correo);
        etEdad = findViewById(R.id.et_edad);
        etPeso = findViewById(R.id.et_peso);
        etEstatura = findViewById(R.id.et_estatura);

        spinnerSexo = findViewById(R.id.spinner_sexo);
        spinnerNivel = findViewById(R.id.spinner_nivel);
        spinnerObjetivo = findViewById(R.id.spinner_objetivo);
        spinnerDeporte = findViewById(R.id.spinner_deporte);

        btnComenzar = findViewById(R.id.btn_comenzar);
    }

    private void setupSpinners() {
        // Configurar spinner de sexo
        ArrayAdapter<String> sexoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sexoOptions);
        sexoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSexo.setAdapter(sexoAdapter);

        // Configurar spinner de nivel
        ArrayAdapter<String> nivelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nivelOptions);
        nivelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNivel.setAdapter(nivelAdapter);

        // Configurar spinner de objetivo
        ArrayAdapter<String> objetivoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, objetivoOptions);
        objetivoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerObjetivo.setAdapter(objetivoAdapter);

        // Configurar spinner de deporte
        ArrayAdapter<String> deporteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, deporteOptions);
        deporteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDeporte.setAdapter(deporteAdapter);
    }

    private void setupButton() {
        btnComenzar.setOnClickListener(v -> {
            if (validateForm()) {
                // Obtener los datos del usuario
                String nombre = etNombre.getText().toString().trim();
                String correo = etCorreo.getText().toString().trim();
                String edad = etEdad.getText().toString().trim();
                String deporte = spinnerDeporte.getSelectedItem().toString();

                // Navegar al dashboard con los datos del usuario
                Intent intent = new Intent(WelcomeActivity.this, com.example.appproyecto.DashboardActivity.class);
                intent.putExtra("NOMBRE_USUARIO", nombre);
                intent.putExtra("CORREO_USUARIO", correo);
                intent.putExtra("EDAD_USUARIO", edad);
                intent.putExtra("DEPORTE_USUARIO", deporte);
                startActivity(intent);
                finish(); // Opcional: cerrar la actividad anterior
            } else {
                Toast.makeText(WelcomeActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateForm() {
        String nombre = etNombre.getText().toString().trim();
        String correo = etCorreo.getText().toString().trim();
        String edad = etEdad.getText().toString().trim();
        String peso = etPeso.getText().toString().trim();
        String estatura = etEstatura.getText().toString().trim();

        boolean sexoSelected = spinnerSexo.getSelectedItemPosition() > 0;
        boolean nivelSelected = spinnerNivel.getSelectedItemPosition() > 0;
        boolean objetivoSelected = spinnerObjetivo.getSelectedItemPosition() > 0;
        boolean deporteSelected = spinnerDeporte.getSelectedItemPosition() > 0;

        return !nombre.isEmpty() && !correo.isEmpty() && !edad.isEmpty() && !peso.isEmpty() && !estatura.isEmpty()
               && sexoSelected && nivelSelected && objetivoSelected && deporteSelected;
    }
}
