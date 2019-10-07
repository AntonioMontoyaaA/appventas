package neto.com.mx.aperturatienda.model.guardacaracteristica.kvm;

//import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public interface KvmSerializable {

    /**
     * Get the property at the given index
     */
    Object getProperty(int index);

    /**
     * @return the number of serializable properties
     */
    int getPropertyCount();

    /**
     * Sets the property with the given index to the given value.
     *
     * @param index the index to be set
     * @param value the value of the property
     */
    void setProperty(int index, Object value);

    /**
     * Fills the given property info record.
     *
     * @param index      the index to be queried
     * @param properties information about the (de)serializer.  Not frequently used.
     * @param info       The return parameter, to be filled with information about the
     *                   property with the given index.
     */
    void getPropertyInfo(int index, Hashtable properties, PropertyInfo info);


}
