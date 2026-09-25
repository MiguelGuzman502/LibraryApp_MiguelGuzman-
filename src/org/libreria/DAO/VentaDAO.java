package org.libreria.DAO;

import java.util.List;
import org.libreria.model.LineaVenta;
import org.libreria.model.Venta;

/**
 * Interfaz de acceso a datos para la entidad {@link Venta}.
 * Hereda las operaciones CRUD definidas en {@link Crud}.
 * Además, permite crear una venta junto con sus líneas
 * y actualizar el stock correspondiente.
 * @author Miguel Guzman
 * @version 1.0.0
 * @see Venta
 * @see LineaVenta
 * @see Crud
 */
public interface VentaDAO extends Crud<Venta, Integer> {

    /**
     * Crea una venta completa, incluyendo el encabezado,
     * sus líneas de venta y el descuento del stock correspondiente.
     * @param venta Venta que contiene la información del encabezado.
     * @param lineas Lista de líneas que pertenecen a la venta.
     * @return Número de venta generado si la operación fue exitosa;
     *         -1 si ocurrió algún error.
     */
    int crearVenta(Venta venta, List<LineaVenta> lineas);
}
