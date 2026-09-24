package org.libreria.DAO;

import java.util.ArrayList;
import org.libreria.model.LineaFactura;

/**
 * Interfaz de acceso a datos para la consulta de facturas.
 * Define las operaciones necesarias para obtener la información
 * de una factura mediante el número de venta.
 * @author Miguel Guzman
 * @version 1.0.0
 * @see LineaFactura
 */
public interface FacturaDAO {

    /**
     * Busca la información de una factura utilizando
     * el número de venta.
     * @param noVenta Número que identifica la venta asociada
     *                a la factura.
     * @return Lista de líneas que contienen la información de la factura.
     */
    ArrayList<LineaFactura> buscarFactura(int noVenta);
}
