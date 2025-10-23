package com.example.appproyecto;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RutinaActivity extends AppCompatActivity {

    private static final String TAG = "RutinaActivity";
    private static final String WEBHOOK_URL = "https://primary-production-c529.up.railway.app/webhook/065a805f-55e7-48c4-9b7a-bbeddbddec84";

    private String nombreDeporte;
    private String nombreUsuario;
    private String correoUsuario;
    private String edadUsuario;
    private List<Ejercicio> ejercicios;
    private OkHttpClient httpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutina);

        // Inicializar cliente HTTP
        httpClient = new OkHttpClient();

        // Obtener el deporte seleccionado y datos del usuario desde el Intent
        nombreDeporte = getIntent().getStringExtra("NOMBRE_DEPORTE");
        nombreUsuario = getIntent().getStringExtra("NOMBRE_USUARIO");
        correoUsuario = getIntent().getStringExtra("CORREO_USUARIO");
        edadUsuario = getIntent().getStringExtra("EDAD_USUARIO");

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

        EjerciciosAdapter ejerciciosAdapter = new EjerciciosAdapter(this, ejercicios);
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

        // Al tocar "Personalizar rutina", enviar datos al webhook
        btnPersonalizarRutina.setOnClickListener(v -> {
            enviarRutinaAlWebhook();
        });
    }

    /**
     * Genera el JSON con los datos del usuario y la rutina, y lo envía al webhook
     */
    private void enviarRutinaAlWebhook() {
        try {
            // Crear el objeto JSON con los datos del usuario y la rutina
            JSONObject jsonData = new JSONObject();

            // Agregar datos del usuario
            jsonData.put("nombre", nombreUsuario != null ? nombreUsuario : "Usuario");
            jsonData.put("edad", edadUsuario != null ? Integer.parseInt(edadUsuario) : 0);
            jsonData.put("correo", correoUsuario != null ? correoUsuario : "");
            jsonData.put("deporte", nombreDeporte != null ? nombreDeporte : "");

            // Crear array con los ejercicios de la rutina
            JSONArray rutinaArray = new JSONArray();
            for (Ejercicio ejercicio : ejercicios) {
                rutinaArray.put(ejercicio.nombre + " - " + ejercicio.descripcion);
            }
            jsonData.put("rutina", rutinaArray);

            // Log para debug
            Log.d(TAG, "JSON generado: " + jsonData.toString(2));

            // Crear el cuerpo de la petición
            MediaType JSON = MediaType.get("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(jsonData.toString(), JSON);

            // Crear la petición HTTP POST
            Request request = new Request.Builder()
                    .url(WEBHOOK_URL)
                    .post(body)
                    .build();

            // Ejecutar la petición de forma asíncrona
            httpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    // Error en la petición
                    Log.e(TAG, "Error al enviar datos al webhook", e);
                    runOnUiThread(() ->
                        Toast.makeText(RutinaActivity.this,
                            "Error al enviar datos: " + e.getMessage(),
                            Toast.LENGTH_LONG).show()
                    );
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    // Petición exitosa
                    final String responseBody = response.body() != null ? response.body().string() : "";
                    final boolean isSuccessful = response.isSuccessful();

                    Log.d(TAG, "Respuesta del servidor - Código: " + response.code() + ", Body: " + responseBody);

                    runOnUiThread(() -> {
                        if (isSuccessful) {
                            Toast.makeText(RutinaActivity.this,
                                "¡Rutina enviada exitosamente! ✓",
                                Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(RutinaActivity.this,
                                "Error del servidor (código " + response.code() + ")",
                                Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });

            // Mostrar mensaje inmediato al usuario
            Toast.makeText(this, "Enviando rutina personalizada...", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Log.e(TAG, "Error al crear el JSON", e);
            Toast.makeText(this, "Error al preparar los datos: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
