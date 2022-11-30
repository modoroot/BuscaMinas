package com.example.buscaminas;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RedMinasRecyclerAdapter extends RecyclerView.Adapter<RedMinasRecyclerAdapter.EnvaseRedMinas> {
    private List<Celda> celdas;
    private final EventClickCelda listener;

    public RedMinasRecyclerAdapter(List<Celda> celdas, EventClickCelda listener) {
        this.celdas = celdas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EnvaseRedMinas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_celda, parent, false);
        return new EnvaseRedMinas(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull EnvaseRedMinas envase, int posicion) {
        envase.bind(celdas.get(posicion));
        envase.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return celdas.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCeldas(List<Celda> celdas) {
        this.celdas = celdas;
        notifyDataSetChanged();
    }

    class EnvaseRedMinas extends RecyclerView.ViewHolder {
        TextView valorTextView;

        public EnvaseRedMinas(@NonNull View itemView) {
            super(itemView);
            valorTextView = itemView.findViewById(R.id.valor_celda);
        }

        public void bind(final Celda celda) {
            itemView.setBackgroundColor(Color.GRAY);
            itemView.setOnClickListener(v -> listener.ClickCelda(celda));

            if (celda.getRevelado()) {
                if (celda.getNum() == Celda.BOMBA) {
                    valorTextView.setText(R.string.bomba);
                } else if (celda.getNum() == Celda.VACIO) {
                    valorTextView.setText("");
                    itemView.setBackgroundColor(Color.WHITE);
                } else {
                    valorTextView.setText(String.valueOf(celda.getNum()));
                    if (celda.getNum() == 1) {
                        valorTextView.setTextColor(Color.BLUE);
                    } else if (celda.getNum() == 2) {
                        valorTextView.setTextColor(Color.GREEN);
                    } else if (celda.getNum() == 3) {
                        valorTextView.setTextColor(Color.RED);
                    }
                }
            } else if (celda.getMarcado()) {
                valorTextView.setText(R.string.bandera);
            }
        }
    }
}
