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


/**
 *
 * @author amna
 * @version 1.0
 */
public class RedMinasRecyclerAdapter extends RecyclerView.Adapter<RedMinasRecyclerAdapter.EnvaseRedMinas> {
    private List<Celda> celdas;
    private EventClick listener;

    /**
     * @param celdas   celdas del tablero
     * @param listener manejador de eventos para las celdas
     */
    public RedMinasRecyclerAdapter(List<Celda> celdas, EventClick listener) {
        this.celdas = celdas;
        this.listener = listener;
    }

    /**
     *
     * Infla el layout de las celdas
     * @param parent   para inflar las vistas de las celdas (en sí, es para actualizarlas)
     * @param viewType //
     * @return devuelve todas las casillas
     */
    @NonNull
    @Override
    public EnvaseRedMinas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_celda,
                parent, false);
        return new EnvaseRedMinas(itemView);
    }

    /**
     *
     *
     * @param envase   selecciona el itemview
     * @param posicion posicion de la celda
     */
    @Override
    public void onBindViewHolder(@NonNull EnvaseRedMinas envase, int posicion) {
        envase.bind(celdas.get(posicion));
        envase.setIsRecyclable(false);
    }

    /**
     * @return devuelve el número de celdas
     */
    @Override
    public int getItemCount() {
        return celdas.size();
    }

    /**
     * @param celdas guarda las celdas en un List
     *               notifyDataSetChanged() notifica si ha habido algún cambio a
     *               los observadores
     */
    @SuppressLint("NotifyDataSetChanged")
    public void setCeldas(List<Celda> celdas) {
        this.celdas = celdas;
        notifyDataSetChanged();
    }

    /**
     * Clase anónima
     * Determina el número y color de las celdas según sus bombas
     * adyacentes
     */
    class EnvaseRedMinas extends RecyclerView.ViewHolder {
        TextView valorTextView;

        /**
         * @param itemView id celda
         */
        public EnvaseRedMinas(@NonNull View itemView) {
            super(itemView);
            valorTextView = itemView.findViewById(R.id.valor_celda);
        }

        /**
         * método para el muestreo del tipo de celda (celda con bomba, celda vacía,
         * celda con 1 bomba cercana, y sucesivamente)
         *
         * @param celda celda seleccionada
         */
        public void bind(final Celda celda) {
            itemView.setBackgroundColor(Color.GRAY);
            itemView.setOnClickListener(view -> listener.clickCelda(celda));

            //muestra la imagen correspondiente según el num
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
                    }else if (celda.getNum() == 4) {
                        valorTextView.setTextColor(Color.MAGENTA);
                    }
                }
            } else if(celda.isBandera()){
                    valorTextView.setText(R.string.bandera);
            }
        }
    }
}
