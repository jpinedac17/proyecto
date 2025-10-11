package com.example.appproyecto;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

public class ConfiguracionActivity extends AppCompatActivity {

    private TextView tvUserInitial;
    private TextView tvUserName;
    private TextView tvUserGoal;
    private Button btnEditProfile;
    private Spinner spinnerLanguage;
    private SwitchCompat switchNotifications;
    private SwitchCompat switchReminders;
    private SwitchCompat switchDarkMode;
    private TextView tvTerms;
    private TextView tvPrivacy;
    private TextView tvSupport;
    private TextView btnLogout;

    private String nombreUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        // Obtener el nombre del usuario desde el Intent
        nombreUsuario = getIntent().getStringExtra("NOMBRE_USUARIO");
        if (nombreUsuario == null) {
            nombreUsuario = "Usuario";
        }

        setupToolbar();
        setupViews();
        setupUserInfo();
        setupListeners();
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
        titleTextView.setText("Configuración");
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
        tvUserInitial = findViewById(R.id.tv_user_initial);
        tvUserName = findViewById(R.id.tv_user_name);
        tvUserGoal = findViewById(R.id.tv_user_goal);
        btnEditProfile = findViewById(R.id.btn_edit_profile);
        spinnerLanguage = findViewById(R.id.spinner_language);
        switchNotifications = findViewById(R.id.switch_notifications);
        switchReminders = findViewById(R.id.switch_reminders);
        switchDarkMode = findViewById(R.id.switch_dark_mode);
        tvTerms = findViewById(R.id.tv_terms);
        tvPrivacy = findViewById(R.id.tv_privacy);
        tvSupport = findViewById(R.id.tv_support);
        btnLogout = findViewById(R.id.btn_logout);
    }

    private void setupUserInfo() {
        // Configurar información del usuario
        tvUserName.setText(nombreUsuario);

        // Mostrar inicial del usuario
        if (nombreUsuario != null && !nombreUsuario.isEmpty()) {
            tvUserInitial.setText(String.valueOf(nombreUsuario.charAt(0)).toUpperCase());
        } else {
            tvUserInitial.setText("U");
        }
    }

    private void setupListeners() {
        // Botón Editar perfil
        btnEditProfile.setOnClickListener(v -> {
            showEditProfileDialog();
        });

        // Switches
        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String message = isChecked ? "Notificaciones activadas" : "Notificaciones desactivadas";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });

        switchReminders.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String message = isChecked ? "Recordatorios activados" : "Recordatorios desactivados";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });

        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String message = isChecked ? "Modo oscuro activado" : "Modo oscuro desactivado";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            // Aquí puedes implementar la lógica para cambiar el tema
        });

        // Enlaces de información
        tvTerms.setOnClickListener(v -> {
            showInfoDialog("Términos y condiciones", "Aquí se mostrarían los términos y condiciones de la aplicación.");
        });

        tvPrivacy.setOnClickListener(v -> {
            showInfoDialog("Política de privacidad", "Aquí se mostraría la política de privacidad de la aplicación.");
        });

        tvSupport.setOnClickListener(v -> {
            showSupportDialog();
        });

        // Botón Cerrar sesión
        btnLogout.setOnClickListener(v -> {
            showLogoutConfirmation();
        });
    }

    private void showEditProfileDialog() {
        // Crear layout personalizado para el diálogo
        android.widget.LinearLayout layout = new android.widget.LinearLayout(this);
        layout.setOrientation(android.widget.LinearLayout.VERTICAL);
        layout.setPadding(50, 50, 50, 50);

        android.widget.EditText editName = new android.widget.EditText(this);
        editName.setHint("Nombre");
        editName.setText(nombreUsuario);
        layout.addView(editName);

        android.widget.EditText editGoal = new android.widget.EditText(this);
        editGoal.setHint("Objetivo");
        editGoal.setText("ganar-musculo");
        layout.addView(editGoal);

        new AlertDialog.Builder(this)
                .setTitle("Editar perfil")
                .setView(layout)
                .setPositiveButton("Guardar", (dialog, which) -> {
                    String newName = editName.getText().toString().trim();
                    String newGoal = editGoal.getText().toString().trim();

                    if (!newName.isEmpty()) {
                        nombreUsuario = newName;
                        tvUserName.setText(newName);
                        tvUserInitial.setText(String.valueOf(newName.charAt(0)).toUpperCase());
                    }

                    if (!newGoal.isEmpty()) {
                        tvUserGoal.setText(newGoal);
                    }

                    Toast.makeText(this, "Perfil actualizado", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void showInfoDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Entendido", null)
                .show();
    }

    private void showSupportDialog() {
        String[] options = {"Enviar email", "Llamar por teléfono", "Chat en vivo"};

        new AlertDialog.Builder(this)
                .setTitle("Contactar soporte")
                .setItems(options, (dialog, which) -> {
                    String selectedOption = options[which];
                    Toast.makeText(this, "Opción seleccionada: " + selectedOption, Toast.LENGTH_SHORT).show();
                    // Aquí puedes implementar la lógica para cada opción de contacto
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void showLogoutConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Cerrar sesión")
                .setMessage("¿Estás seguro de que quieres cerrar sesión?")
                .setPositiveButton("Cerrar sesión", (dialog, which) -> {
                    // Regresar a la pantalla de inicio/login
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}
