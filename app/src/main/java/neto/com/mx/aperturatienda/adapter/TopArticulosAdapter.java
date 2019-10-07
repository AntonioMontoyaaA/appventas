package neto.com.mx.aperturatienda.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import neto.com.mx.aperturatienda.R;

public class TopArticulosAdapter extends RecyclerView.Adapter<TopArticulosAdapter.ViewHolder> {
        private String[] mDataSet;

        // Obtener referencias de los componentes visuales para cada elemento
        // Es decir, referencias de los EditText, TextViews, Buttons
        public static class ViewHolder extends RecyclerView.ViewHolder {
            // en este ejemplo cada elemento consta solo de un título
            public TextView textView;
            public ViewHolder(TextView tv) {
                super(tv);
                textView = tv;
            }
        }

        // Este es nuestro constructor (puede variar según lo que queremos mostrar)
        public TopArticulosAdapter(String[] myDataSet) {
            mDataSet = myDataSet;
        }

        // El layout manager invoca este método
        // para renderizar cada elemento del RecyclerView
        @Override
        public TopArticulosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
            // Creamos una nueva vista
            TextView v = (TextView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.contenido_rvindicadores, parent, false);

            // Aquí podemos definir tamaños, márgenes, paddings
            // ...

            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        // Este método reemplaza el contenido de cada view,
        // para cada elemento de la lista (nótese el argumento position)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - obtenemos un elemento del dataset según su posición
            // - reemplazamos el contenido de los views según tales datos

            holder.textView.setText(mDataSet[position]);
        }

        // Método que define la cantidad de elementos del RecyclerView
        // Puede ser más complejo (por ejemplo si implementamos filtros o búsquedas)
        @Override
        public int getItemCount() {
            return mDataSet.length;
        }
}
