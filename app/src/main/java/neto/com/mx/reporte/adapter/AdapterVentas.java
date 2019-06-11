package neto.com.mx.reporte.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Comparator;

import neto.com.mx.reporte.databinding.ItemTiendasBinding;
import neto.com.mx.reporte.model.dashboard.Ventas;
import neto.com.mx.reporte.utils.SortedListAdapter;

public class AdapterVentas extends SortedListAdapter<Ventas> {

    VentasHolder.Listener listener;

    Context context;
    boolean isTienda = false;

    public AdapterVentas(Context context, Comparator<Ventas> comparator, VentasHolder.Listener listener) {
        super(context, Ventas.class, comparator);
        this.listener = listener;
        this.context = context;
        this.isTienda = false;
    }

    public AdapterVentas(Context context, Comparator<Ventas> comparator, VentasHolder.Listener listener,boolean isTienda) {
        super(context, Ventas.class, comparator);
        this.listener = listener;
        this.context = context;
        this.isTienda = isTienda;
    }

    @Override
    protected SortedListAdapter.ViewHolder<? extends Ventas> onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        if(this.isTienda){
            final ItemTiendasBinding binding = ItemTiendasBinding.inflate(inflater, parent, false);
            binding.rtvTiendas.setText("Transacciones");
            binding.setListener(listener);
            return new VentasHolder(binding, listener);
        }
        else{
            final ItemTiendasBinding binding = ItemTiendasBinding.inflate(inflater, parent, false);
            binding.setListener(listener);
            return new VentasHolder(binding, listener);
        }
    }

    @Override
    protected boolean areItemsTheSame(Ventas item1, Ventas item2) {
        return false;
    }

    @Override
    protected boolean areItemContentsTheSame(Ventas oldItem, Ventas newItem) {
        return false;
    }
}
