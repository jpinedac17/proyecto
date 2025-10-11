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
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DeportesFavoritosAdapter extends RecyclerView.Adapter<DeportesFavoritosAdapter.DeporteFavoritoViewHolder> {

    public interface OnDeporteActionListener {
        void onEditDeporte(DeporteFavorito deporte, int position);
        void onDeleteDeporte(DeporteFavorito deporte, int position);
    }

    private final Context context;
    private final List<DeporteFavorito> deportes;
    private final OnDeporteActionListener listener;

    public DeportesFavoritosAdapter(Context context, List<DeporteFavorito> deportes, OnDeporteActionListener listener) {
        this.context = context;
        this.deportes = deportes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DeporteFavoritoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_deporte_favorito, parent, false);
        return new DeporteFavoritoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeporteFavoritoViewHolder holder, int position) {
        DeporteFavorito deporte = deportes.get(position);

        // Configurar nombre del deporte
        holder.txtNombre.setText(deporte.nombre);

        // Configurar ícono
        holder.iconoDeporte.setImageResource(deporte.iconoRes);

        // Crear drawable circular con el color del deporte
        GradientDrawable circleDrawable = new GradientDrawable();
        circleDrawable.setShape(GradientDrawable.OVAL);
        circleDrawable.setColor(Color.parseColor(deporte.colorFondo));
        holder.iconoFondo.setBackground(circleDrawable);

        // Configurar listeners para los botones de acción
        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditDeporte(deporte, position);
            }
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteDeporte(deporte, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return deportes.size();
    }

    public void removeItem(int position) {
        if (position >= 0 && position < deportes.size()) {
            deportes.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, deportes.size());
        }
    }

    static class DeporteFavoritoViewHolder extends RecyclerView.ViewHolder {
        View iconoFondo;
        ImageView iconoDeporte;
        TextView txtNombre;
        ImageView btnEdit;
        ImageView btnDelete;

        DeporteFavoritoViewHolder(@NonNull View itemView) {
            super(itemView);
            iconoFondo = itemView.findViewById(R.id.icono_fondo);
            iconoDeporte = itemView.findViewById(R.id.icono_deporte);
            txtNombre = itemView.findViewById(R.id.txt_nombre_deporte);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
