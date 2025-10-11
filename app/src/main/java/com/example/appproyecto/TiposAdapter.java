package com.example.appproyecto;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class TiposAdapter extends RecyclerView.Adapter<TiposAdapter.TipoViewHolder> {

    private final Context context;
    private int selectedPosition = -1;
    private Deporte deporteSeleccionado;

    private final TipoEntrenamiento[] tipos = {
            new TipoEntrenamiento("Rutina personalizada", R.drawable.ic_target, "#60A5FA"),
            new TipoEntrenamiento("Cronómetro/Temporizador", R.drawable.ic_timer, "#22C55E"),
            new TipoEntrenamiento("Registrar manualmente", R.drawable.ic_edit, "#F97316")
    };

    public TiposAdapter(Context context) {
        this.context = context;
    }

    public void setDeporteSeleccionado(Deporte deporte) {
        this.deporteSeleccionado = deporte;
        selectedPosition = -1;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TipoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tipo_entrenamiento, parent, false);
        return new TipoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TipoViewHolder holder, int position) {
        TipoEntrenamiento tipo = tipos[position];
        holder.txtNombre.setText(tipo.nombre);
        holder.icono.setImageResource(tipo.iconoRes);

        // Crear drawable circular con el color del tipo
        GradientDrawable circleDrawable = new GradientDrawable();
        circleDrawable.setShape(GradientDrawable.OVAL);
        circleDrawable.setColor(Color.parseColor(tipo.colorFondo));
        holder.iconoFondo.setBackground(circleDrawable);

        // Configurar colores estándar (fondo blanco, borde gris)
        holder.card.setCardBackgroundColor(Color.WHITE);
        holder.txtNombre.setTextColor(Color.parseColor("#111827"));
        holder.icono.setColorFilter(Color.WHITE);

        holder.card.setOnClickListener(v -> {
            int previousPosition = selectedPosition;
            selectedPosition = position;

            // Actualizar las vistas
            if (previousPosition != -1) {
                notifyItemChanged(previousPosition);
            }
            notifyItemChanged(selectedPosition);

            // Navegación según el tipo seleccionado
            if (position == 0 && deporteSeleccionado != null) { // Rutina personalizada
                Intent intent = new Intent(context, RutinaActivity.class);
                intent.putExtra("NOMBRE_DEPORTE", deporteSeleccionado.nombre);
                context.startActivity(intent);
            }
            // Aquí puedes agregar más navegaciones para otros tipos
        });
    }

    @Override
    public int getItemCount() {
        return tipos.length;
    }

    static class TipoViewHolder extends RecyclerView.ViewHolder {
        CardView card;
        View iconoFondo;
        ImageView icono;
        TextView txtNombre;

        TipoViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card_tipo);
            iconoFondo = itemView.findViewById(R.id.icono_fondo);
            icono = itemView.findViewById(R.id.icono);
            txtNombre = itemView.findViewById(R.id.txt_nombre_tipo);
        }
    }
}
