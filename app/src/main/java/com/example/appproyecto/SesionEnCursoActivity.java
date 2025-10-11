package com.example.appproyecto;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SesionEnCursoActivity extends AppCompatActivity {

    private TextView tvCronometro;
    private TextView tvMinutosValor;
    private TextView tvCaloriasValor;
    private TextView tvBpmValor;
    private Button btnPause;
    private Button btnStop;

    private Handler handler;
    private Runnable runnable;
    private int segundosTranscurridos = 2; // Iniciar en 2 segundos como en el ejemplo
    private boolean isPaused = false;
    private String nombreDeporte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sesion_en_curso);

        // Obtener el deporte desde el Intent
        nombreDeporte = getIntent().getStringExtra("NOMBRE_DEPORTE");
        if (nombreDeporte == null) {
            nombreDeporte = "Entrenamiento";
        }

        setupViews();
        setupToolbar();
        setupButtons();
        startTimer();
    }

    private void setupViews() {
        tvCronometro = findViewById(R.id.tv_cronometro);
        tvMinutosValor = findViewById(R.id.tv_minutos_valor);
        tvCaloriasValor = findViewById(R.id.tv_calorias_valor);
        tvBpmValor = findViewById(R.id.tv_bpm_valor);
        btnPause = findViewById(R.id.btn_pause);
        btnStop = findViewById(R.id.btn_stop);
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
        titleTextView.setText(nombreDeporte);
        titleTextView.setTextSize(20);
        titleTextView.setTextColor(0xFFFFFFFF);
        titleTextView.setTypeface(titleTextView.getTypeface(), android.graphics.Typeface.BOLD);

        // Agregar el título al toolbar
        Toolbar.LayoutParams layoutParams = new Toolbar.LayoutParams(
            Toolbar.LayoutParams.WRAP_CONTENT,
            Toolbar.LayoutParams.WRAP_CONTENT
        );
        layoutParams.gravity = android.view.Gravity.CENTER;
        toolbar.addView(titleTextView, layoutParams);

        // Configurar navegación hacia atrás
        toolbar.setNavigationOnClickListener(v -> {
            // Mostrar confirmación antes de salir
            showExitConfirmation();
        });
    }

    private void setupButtons() {
        btnPause.setOnClickListener(v -> {
            if (isPaused) {
                resumeTimer();
            } else {
                pauseTimer();
            }
        });

        btnStop.setOnClickListener(v -> {
            stopTimer();
            // Aquí puedes navegar a una pantalla de resumen
            finish();
        });
    }

    private void startTimer() {
        handler = new Handler(Looper.getMainLooper());
        runnable = new Runnable() {
            @Override
            public void run() {
                if (!isPaused) {
                    segundosTranscurridos++;
                    updateUI();
                }
                handler.postDelayed(this, 1000); // Actualizar cada segundo
            }
        };
        handler.post(runnable);
    }

    private void pauseTimer() {
        isPaused = true;
        // Aquí puedes cambiar el ícono del botón a "play" si quieres
    }

    private void resumeTimer() {
        isPaused = false;
        // Aquí puedes cambiar el ícono del botón a "pause"
    }

    private void stopTimer() {
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }

    private void updateUI() {
        // Actualizar cronómetro principal
        int minutos = segundosTranscurridos / 60;
        int segundos = segundosTranscurridos % 60;
        tvCronometro.setText(String.format("%02d:%02d", minutos, segundos));

        // Actualizar indicadores
        tvMinutosValor.setText(String.valueOf(minutos));

        // Calcular calorías aproximadas (ejemplo: 0.3 calorías por segundo)
        int calorias = (int) (segundosTranscurridos * 0.3);
        tvCaloriasValor.setText(String.valueOf(calorias));

        // Simular BPM (entre 120-160)
        int bpm = 120 + (segundosTranscurridos % 40);
        tvBpmValor.setText(String.valueOf(bpm));
    }

    private void showExitConfirmation() {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Salir de la sesión")
                .setMessage("¿Estás seguro de que quieres salir? Se perderá el progreso actual.")
                .setPositiveButton("Salir", (dialog, which) -> {
                    stopTimer();
                    finish();
                })
                .setNegativeButton("Continuar", null)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // No pausar automáticamente cuando la app va al fondo
    }
}
