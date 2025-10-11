package com.example.appproyecto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class EjerciciosAdapter extends RecyclerView.Adapter<EjerciciosAdapter.EjercicioViewHolder> {

    private final Context context;
    private final List<Ejercicio> ejercicios;

    public EjerciciosAdapter(Context context, List<Ejercicio> ejercicios) {
        this.context = context;
        this.ejercicios = ejercicios;
    }

    @NonNull
    @Override
    public EjercicioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ejercicio, parent, false);
        return new EjercicioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EjercicioViewHolder holder, int position) {
        Ejercicio ejercicio = ejercicios.get(position);
        holder.txtNombre.setText(ejercicio.nombre);
        holder.txtDescripcion.setText(ejercicio.descripcion);

        // Configurar el círculo de check según el estado
        if (ejercicio.completado) {
            holder.checkCircle.setBackgroundResource(R.drawable.circle_check_filled);
        } else {
            holder.checkCircle.setBackgroundResource(R.drawable.circle_check_empty);
        }

        // Click listener para marcar/desmarcar ejercicio como completado
        holder.card.setOnClickListener(v -> {
            ejercicio.completado = !ejercicio.completado;
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return ejercicios.size();
    }

    static class EjercicioViewHolder extends RecyclerView.ViewHolder {
        CardView card;
        TextView txtNombre;
        TextView txtDescripcion;
        View checkCircle;

        EjercicioViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card_ejercicio);
            txtNombre = itemView.findViewById(R.id.txt_nombre_ejercicio);
            txtDescripcion = itemView.findViewById(R.id.txt_descripcion_ejercicio);
            checkCircle = itemView.findViewById(R.id.check_circle);
        }
    }
}
