package com.example.appproyecto;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class StartTrainingActivity extends AppCompatActivity {

    private CardView cardTipoEntrenamiento;
    private TiposAdapter tiposAdapter;
    private String nombreUsuario;
    private String correoUsuario;
    private String edadUsuario;
    private String deporteUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_training);

        // Obtener los datos del usuario desde el Intent
        nombreUsuario = getIntent().getStringExtra("NOMBRE_USUARIO");
        correoUsuario = getIntent().getStringExtra("CORREO_USUARIO");
        edadUsuario = getIntent().getStringExtra("EDAD_USUARIO");
        deporteUsuario = getIntent().getStringExtra("DEPORTE_USUARIO");

        setupToolbar();
        setupRecyclerViews();
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
        titleTextView.setText("Iniciar entrenamiento");
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

    private void setupRecyclerViews() {
        // Card de tipo de entrenamiento (inicialmente oculta)
        cardTipoEntrenamiento = findViewById(R.id.card_tipo_entrenamiento);

        // RecyclerView de deportes
        RecyclerView recyclerDeportes = findViewById(R.id.recycler_deportes);
        recyclerDeportes.setLayoutManager(new LinearLayoutManager(this));

        DeportesAdapter deportesAdapter = new DeportesAdapter(this, deporte -> {
            // Mostrar la segunda card con animación
            showTipoEntrenamientoCard();

            // Resetear selección de tipos
            if (tiposAdapter != null) {
                tiposAdapter.setDeporteSeleccionado(deporte);
            }
        });

        recyclerDeportes.setAdapter(deportesAdapter);

        // RecyclerView de tipos de entrenamiento con los datos del usuario
        RecyclerView recyclerTipos = findViewById(R.id.recycler_tipos);
        recyclerTipos.setLayoutManager(new LinearLayoutManager(this));

        tiposAdapter = new TiposAdapter(this, nombreUsuario, correoUsuario, edadUsuario, deporteUsuario);
        recyclerTipos.setAdapter(tiposAdapter);
    }

    private void showTipoEntrenamientoCard() {
        if (cardTipoEntrenamiento.getVisibility() == View.GONE) {
            // Mostrar con animación fadeIn
            cardTipoEntrenamiento.setVisibility(View.VISIBLE);
            cardTipoEntrenamiento.setAlpha(0f);
            cardTipoEntrenamiento.animate()
                    .alpha(1f)
                    .setDuration(300)
                    .start();
        }
    }
}
