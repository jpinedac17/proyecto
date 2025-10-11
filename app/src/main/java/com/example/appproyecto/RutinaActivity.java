package com.example.appproyecto;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class RutinaActivity extends AppCompatActivity {

    private String nombreDeporte;
    private List<Ejercicio> ejercicios;
    private EjerciciosAdapter ejerciciosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutina);

        // Obtener el deporte seleccionado desde el Intent
        nombreDeporte = getIntent().getStringExtra("NOMBRE_DEPORTE");
        if (nombreDeporte == null) {
            nombreDeporte = "Entrenamiento";
        }

        setupToolbar();
        setupHeaderGradient();
        setupEjercicios();
        setupButtons();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Configurar título personalizado
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Crear TextView personalizado para el título
        TextView titleTextView = new TextView(this);
        titleTextView.setText("Rutina de " + nombreDeporte);
        titleTextView.setTextSize(20);
        titleTextView.setTextColor(0xFF111827);
        titleTextView.setTypeface(titleTextView.getTypeface(), android.graphics.Typeface.BOLD);

        // Agregar el título al toolbar
        Toolbar.LayoutParams layoutParams = new Toolbar.LayoutParams(
            Toolbar.LayoutParams.WRAP_CONTENT,
            Toolbar.LayoutParams.WRAP_CONTENT
        );
        layoutParams.gravity = android.view.Gravity.CENTER;
        toolbar.addView(titleTextView, layoutParams);

        // Configurar navegación hacia atrás
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void setupHeaderGradient() {
        LinearLayout headerGradient = findViewById(R.id.header_gradient);

        // Crear gradiente programáticamente
        GradientDrawable gradient = new GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT,
            new int[]{0xFF4FACFE, 0xFF9D4EDD}
        );
        gradient.setCornerRadius(48f); // 16dp en pixels

        headerGradient.setBackground(gradient);
    }

    private void setupEjercicios() {
        // Crear lista de ejercicios según el deporte
        ejercicios = createEjerciciosForDeporte(nombreDeporte);

        // Configurar RecyclerView
        RecyclerView recyclerEjercicios = findViewById(R.id.recycler_ejercicios);
        recyclerEjercicios.setLayoutManager(new LinearLayoutManager(this));

        ejerciciosAdapter = new EjerciciosAdapter(this, ejercicios);
        recyclerEjercicios.setAdapter(ejerciciosAdapter);
    }

    private List<Ejercicio> createEjerciciosForDeporte(String deporte) {
        List<Ejercicio> lista = new ArrayList<>();

        // Ejercicios generales que se adaptan según el deporte
        switch (deporte.toLowerCase()) {
            case "correr":
                lista.add(new Ejercicio("Calentamiento", "5 min"));
                lista.add(new Ejercicio("Trote suave", "10 min"));
                lista.add(new Ejercicio("Intervalos", "3 series × 2 min"));
                lista.add(new Ejercicio("Carrera continua", "15 min"));
                lista.add(new Ejercicio("Enfriamiento", "5 min"));
                lista.add(new Ejercicio("Estiramiento", "8 min"));
                break;
            case "ciclismo":
                lista.add(new Ejercicio("Calentamiento", "5 min"));
                lista.add(new Ejercicio("Pedaleo suave", "10 min"));
                lista.add(new Ejercicio("Subidas", "3 series × 3 min"));
                lista.add(new Ejercicio("Resistencia", "20 min"));
                lista.add(new Ejercicio("Enfriamiento", "5 min"));
                lista.add(new Ejercicio("Estiramiento", "7 min"));
                break;
            case "gimnasio":
                lista.add(new Ejercicio("Calentamiento", "5 min"));
                lista.add(new Ejercicio("Sentadillas", "3 series × 15 repeticiones"));
                lista.add(new Ejercicio("Flexiones", "3 series × 12 repeticiones"));
                lista.add(new Ejercicio("Plancha", "3 min"));
                lista.add(new Ejercicio("Burpees", "2 series × 10 repeticiones"));
                lista.add(new Ejercicio("Estiramiento", "5 min"));
                break;
            default:
                // Rutina genérica
                lista.add(new Ejercicio("Calentamiento", "5 min"));
                lista.add(new Ejercicio("Ejercicio principal", "20 min"));
                lista.add(new Ejercicio("Ejercicio complementario", "15 min"));
                lista.add(new Ejercicio("Enfriamiento", "5 min"));
                lista.add(new Ejercicio("Estiramiento", "5 min"));
                break;
        }

        return lista;
    }

    private void setupButtons() {
        Button btnComenzarRutina = findViewById(R.id.btn_comenzar_rutina);
        Button btnPersonalizarRutina = findViewById(R.id.btn_personalizar_rutina);

        btnComenzarRutina.setOnClickListener(v -> {
            // Navegar a la pantalla de sesión en curso
            Intent intent = new Intent(RutinaActivity.this, SesionEnCursoActivity.class);
            intent.putExtra("NOMBRE_DEPORTE", nombreDeporte);
            startActivity(intent);
        });

        btnPersonalizarRutina.setOnClickListener(v -> {
            // Aquí puedes agregar la lógica para personalizar la rutina
            // Por ejemplo, navegar a una pantalla de edición
        });
    }
}
