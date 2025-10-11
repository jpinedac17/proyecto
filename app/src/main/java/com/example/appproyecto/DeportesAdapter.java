package com.example.appproyecto;

import android.content.Context;
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

public class DeportesAdapter extends RecyclerView.Adapter<DeportesAdapter.DeporteViewHolder> {

    public interface OnDeporteSelectedListener {
        void onDeporteSelected(Deporte deporte);
    }

    private final Context context;
    private final OnDeporteSelectedListener listener;
    private int selectedPosition = -1;

    private final Deporte[] deportes = {
            new Deporte("Correr", R.drawable.ic_run, "#3B82F6"),
            new Deporte("Ciclismo", R.drawable.ic_bike, "#22C55E"),
            new Deporte("Escalada", R.drawable.ic_mountain, "#F97316"),
            new Deporte("Gimnasio", R.drawable.ic_gym, "#A855F7"),
            new Deporte("Yoga", R.drawable.ic_heart, "#EC4899"),
            new Deporte("Natación", R.drawable.ic_swim, "#0EA5E9")
    };

    public DeportesAdapter(Context context, OnDeporteSelectedListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DeporteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_deporte, parent, false);
        return new DeporteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeporteViewHolder holder, int position) {
        Deporte deporte = deportes[position];
        holder.txtNombre.setText(deporte.nombre);
        holder.icono.setImageResource(deporte.iconoRes);

        // Crear drawable circular con el color del deporte
        GradientDrawable circleDrawable = new GradientDrawable();
        circleDrawable.setShape(GradientDrawable.OVAL);
        circleDrawable.setColor(Color.parseColor(deporte.colorFondo));
        holder.iconoFondo.setBackground(circleDrawable);

        // Estado seleccionado vs no seleccionado
        if (selectedPosition == position) {
            // Estado seleccionado: fondo azul, texto blanco
            holder.card.setCardBackgroundColor(Color.parseColor("#2563EB"));
            holder.txtNombre.setTextColor(Color.WHITE);
            holder.icono.setColorFilter(Color.WHITE);
        } else {
            // Estado normal: fondo blanco, borde gris, texto negro
            holder.card.setCardBackgroundColor(Color.WHITE);
            holder.card.setCardBackgroundColor(Color.WHITE);
            holder.txtNombre.setTextColor(Color.parseColor("#111827"));
            holder.icono.setColorFilter(Color.WHITE);
        }

        holder.card.setOnClickListener(v -> {
            int previousPosition = selectedPosition;
            selectedPosition = position;

            // Actualizar las vistas
            if (previousPosition != -1) {
                notifyItemChanged(previousPosition);
            }
            notifyItemChanged(selectedPosition);

            // Notificar al listener
            listener.onDeporteSelected(deporte);
        });
    }

    @Override
    public int getItemCount() {
        return deportes.length;
    }

    static class DeporteViewHolder extends RecyclerView.ViewHolder {
        CardView card;
        View iconoFondo;
        ImageView icono;
        TextView txtNombre;

        DeporteViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card_deporte);
            iconoFondo = itemView.findViewById(R.id.icono_fondo);
            icono = itemView.findViewById(R.id.icono);
            txtNombre = itemView.findViewById(R.id.txt_nombre_deporte);
        }
    }
}
