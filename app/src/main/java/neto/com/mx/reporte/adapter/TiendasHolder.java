package neto.com.mx.reporte.adapter;

import neto.com.mx.reporte.databinding.ItemTiendaBinding;
import neto.com.mx.reporte.model.dashboard.Tienda;
import neto.com.mx.reporte.model.dashboard.Ventas;
import neto.com.mx.reporte.utils.SortedListAdapter;

public class TiendasHolder extends SortedListAdapter.ViewHolder<Tienda> {

    ItemTiendaBinding itemTiendasBinding;

    public TiendasHolder(final ItemTiendaBinding itemTiendasBinding, final TiendasHolder.Listener listener) {
        super(itemTiendasBinding.getRoot());
        this.itemTiendasBinding = itemTiendasBinding;
    }

    @Override
    protected void performBind(Tienda item) {
        itemTiendasBinding.setTienda(item);
    }

    public interface Listener {
        void onTiendasSelect(Tienda model);
    }

}
