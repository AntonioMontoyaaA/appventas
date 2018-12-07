package neto.com.mx.reporte.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Comparator;

import neto.com.mx.reporte.databinding.ItemTiendaBinding;
import neto.com.mx.reporte.databinding.ItemTiendasBinding;
import neto.com.mx.reporte.model.dashboard.Tienda;
import neto.com.mx.reporte.model.dashboard.Ventas;
import neto.com.mx.reporte.utils.SortedListAdapter;

public class AdapterTiendas extends SortedListAdapter<Tienda> {

    TiendasHolder.Listener listener;

    Context context;

    public AdapterTiendas(Context context, Comparator<Tienda> comparator, TiendasHolder.Listener listener) {
        super(context, Tienda.class, comparator);
        this.listener = listener;
        this.context = context;
    }

    @Override
    protected ViewHolder<? extends Tienda> onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        final ItemTiendaBinding binding = ItemTiendaBinding.inflate(inflater, parent, false);
        binding.setListener(listener);
        return new TiendasHolder(binding, listener);
    }

    @Override
    protected boolean areItemsTheSame(Tienda item1, Tienda item2) {
        return false;
    }

    @Override
    protected boolean areItemContentsTheSame(Tienda oldItem, Tienda newItem) {
        return false;
    }
}
