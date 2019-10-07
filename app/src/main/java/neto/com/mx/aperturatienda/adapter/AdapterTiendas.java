package neto.com.mx.aperturatienda.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Comparator;

import neto.com.mx.aperturatienda.databinding.ItemTiendaBinding;
import neto.com.mx.aperturatienda.model.tiendasLista.Tienda;
import neto.com.mx.aperturatienda.utils.SortedListAdapter;

public class AdapterTiendas extends SortedListAdapter<Tienda> {
    private static final String TAG = "AdapterTiendas";
    TiendasHolder.Listener listener;

    Context context;

    public AdapterTiendas(Context context, Comparator<Tienda> comparator, TiendasHolder.Listener listener) {
        super(context, Tienda.class, comparator);
        this.listener = listener;
        this.context = context;
    }

    @Override
    protected SortedListAdapter.ViewHolder<? extends Tienda> onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        final ItemTiendaBinding binding = ItemTiendaBinding.inflate(inflater, parent, false);
        //Log.d(TAG, "onCreateViewHolder: " + viewType);
        //binding.llitem.setBackgroundColor(2 % 2 == 0 ? Color.parseColor("#F7DED5") : Color.parseColor("#F7DED5"));
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
