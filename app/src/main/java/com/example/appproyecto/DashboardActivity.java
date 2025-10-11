package com.example.appproyecto;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class DashboardActivity extends AppCompatActivity {

    private String nombreUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Obtener el nombre del usuario desde el Intent
        nombreUsuario = getIntent().getStringExtra("NOMBRE_USUARIO");
        if (nombreUsuario == null) {
            nombreUsuario = "Usuario";
        }

        setupUI();
    }

    private void setupUI() {
        // Configurar el texto de saludo con el nombre del usuario
        TextView tvGreeting = findViewById(R.id.tv_greeting);
        if (tvGreeting != null) {
            tvGreeting.setText("¡Hola, " + nombreUsuario + "!");
        }

        // Configurar clicks en las tarjetas de acceso rápido
        setupQuickAccessCards();
    }

    private void setupQuickAccessCards() {
        CardView cardEntrenamiento = findViewById(R.id.card_entrenamiento);
        CardView cardDeportes = findViewById(R.id.card_deportes);
        CardView cardHistorial = findViewById(R.id.card_historial);
        CardView cardConfiguracion = findViewById(R.id.card_configuracion);

        if (cardEntrenamiento != null) {
            cardEntrenamiento.setOnClickListener(v -> {
                // Navegar a la pantalla Iniciar entrenamiento
                Intent intent = new Intent(DashboardActivity.this, StartTrainingActivity.class);
                startActivity(intent);
            });
        }

        if (cardDeportes != null) {
            cardDeportes.setOnClickListener(v -> {
                // Navegar a la pantalla Mis deportes
                Intent intent = new Intent(DashboardActivity.this, MisDeportesActivity.class);
                startActivity(intent);
            });
        }

        if (cardHistorial != null) {
            cardHistorial.setOnClickListener(v -> {
                // Acción para historial
            });
        }

        if (cardConfiguracion != null) {
            cardConfiguracion.setOnClickListener(v -> {
                // Navegar a la pantalla Configuración
                Intent intent = new Intent(DashboardActivity.this, ConfiguracionActivity.class);
                intent.putExtra("NOMBRE_USUARIO", nombreUsuario);
                startActivity(intent);
            });
        }
    }
}
