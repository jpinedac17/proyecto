package com.example.appproyecto;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MisDeportesActivity extends AppCompatActivity implements DeportesFavoritosAdapter.OnDeporteActionListener {

    private RecyclerView recyclerDeportes;
    private DeportesFavoritosAdapter adapter;
    private List<DeporteFavorito> deportesFavoritos;
    private Button btnAddDeporte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_deportes);

        setupToolbar();
        setupViews();
        setupRecyclerView();
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
        titleTextView.setText("Mis deportes");
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

    private void setupViews() {
        recyclerDeportes = findViewById(R.id.recycler_deportes_favoritos);
        btnAddDeporte = findViewById(R.id.btn_add_deporte);
    }

    private void setupRecyclerView() {
        // Crear lista inicial de deportes favoritos
        deportesFavoritos = createInitialDeportesFavoritos();

        // Configurar RecyclerView
        recyclerDeportes.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DeportesFavoritosAdapter(this, deportesFavoritos, this);
        recyclerDeportes.setAdapter(adapter);
    }

    private List<DeporteFavorito> createInitialDeportesFavoritos() {
        List<DeporteFavorito> lista = new ArrayList<>();

        // Agregar deportes favoritos predeterminados según los colores especificados
        lista.add(new DeporteFavorito("Correr", R.drawable.ic_run, "#3B82F6"));        // Azul
        lista.add(new DeporteFavorito("Ciclismo", R.drawable.ic_bike, "#22C55E"));     // Verde
        lista.add(new DeporteFavorito("Gimnasio", R.drawable.ic_gym, "#A855F7"));     // Morado
        lista.add(new DeporteFavorito("Yoga", R.drawable.ic_heart, "#EC4899"));       // Rosa
        lista.add(new DeporteFavorito("Natación", R.drawable.ic_swim, "#0EA5E9"));    // Azul

        return lista;
    }

    private void setupButtons() {
        btnAddDeporte.setOnClickListener(v -> {
            // Mostrar opciones para añadir nuevo deporte
            showAddDeporteDialog();
        });
    }

    private void showAddDeporteDialog() {
        String[] deportesDisponibles = {
            "Tenis", "Fútbol", "Basketball", "Escalada", "Boxeo", "Pilates"
        };

        new AlertDialog.Builder(this)
                .setTitle("Añadir deporte")
                .setItems(deportesDisponibles, (dialog, which) -> {
                    String deporteSeleccionado = deportesDisponibles[which];
                    addNewDeporte(deporteSeleccionado);
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void addNewDeporte(String nombreDeporte) {
        // Crear nuevo deporte con color aleatorio
        String[] colores = {"#3B82F6", "#22C55E", "#A855F7", "#EC4899", "#F97316", "#10B981"};
        String colorAleatorio = colores[(int) (Math.random() * colores.length)];

        DeporteFavorito nuevoDeporte = new DeporteFavorito(
            nombreDeporte,
            R.drawable.ic_heart, // Usar ícono por defecto
            colorAleatorio
        );

        deportesFavoritos.add(nuevoDeporte);
        adapter.notifyItemInserted(deportesFavoritos.size() - 1);

        Toast.makeText(this, nombreDeporte + " añadido a tus deportes favoritos", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEditDeporte(DeporteFavorito deporte, int position) {
        // Mostrar diálogo para editar el nombre del deporte
        showEditDeporteDialog(deporte, position);
    }

    @Override
    public void onDeleteDeporte(DeporteFavorito deporte, int position) {
        // Mostrar confirmación antes de eliminar
        new AlertDialog.Builder(this)
                .setTitle("Eliminar deporte")
                .setMessage("¿Estás seguro de que quieres eliminar " + deporte.nombre + " de tus deportes favoritos?")
                .setPositiveButton("Eliminar", (dialog, which) -> {
                    adapter.removeItem(position);
                    Toast.makeText(this, deporte.nombre + " eliminado", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void showEditDeporteDialog(DeporteFavorito deporte, int position) {
        android.widget.EditText editText = new android.widget.EditText(this);
        editText.setText(deporte.nombre);

        new AlertDialog.Builder(this)
                .setTitle("Editar deporte")
                .setView(editText)
                .setPositiveButton("Guardar", (dialog, which) -> {
                    String nuevoNombre = editText.getText().toString().trim();
                    if (!nuevoNombre.isEmpty()) {
                        deporte.nombre = nuevoNombre;
                        adapter.notifyItemChanged(position);
                        Toast.makeText(this, "Deporte actualizado", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}
