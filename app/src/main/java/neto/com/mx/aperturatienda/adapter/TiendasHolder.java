package neto.com.mx.aperturatienda.adapter;

import neto.com.mx.aperturatienda.databinding.ItemTiendaBinding;
import neto.com.mx.aperturatienda.model.tiendasLista.Tienda;
import neto.com.mx.aperturatienda.utils.SortedListAdapter;

public class TiendasHolder extends SortedListAdapter.ViewHolder<Tienda> {
    ItemTiendaBinding itemTiendaBinding;

    public TiendasHolder(final ItemTiendaBinding  itemTiendaBinding, final TiendasHolder.Listener listener){
        super(itemTiendaBinding.getRoot());
        this.itemTiendaBinding = itemTiendaBinding;
    }

    @Override
    protected void performBind(Tienda item) {
        itemTiendaBinding.setTienda(item);
    }

    public interface Listener{
            void OnProcesoSelect(Tienda tienda);
        }
}
