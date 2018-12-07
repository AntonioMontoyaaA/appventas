package neto.com.mx.reporte.adapter;

import neto.com.mx.reporte.databinding.ItemTiendasBinding;
import neto.com.mx.reporte.model.dashboard.Ventas;
import neto.com.mx.reporte.utils.SortedListAdapter;

public class VentasHolder extends SortedListAdapter.ViewHolder<Ventas> {

    ItemTiendasBinding itemTiendasBinding;

    public VentasHolder(final ItemTiendasBinding itemTiendasBinding, final VentasHolder.Listener listener) {
        super(itemTiendasBinding.getRoot());
        this.itemTiendasBinding = itemTiendasBinding;
    }

    @Override
    protected void performBind(Ventas item) {
        itemTiendasBinding.setVentas(item);
    }

    public interface Listener {
        void onProcesoSelect(Ventas model);
    }

}
