package org.libreria.DAO;

import org.libreria.model.DetalleVenta;

/**
 * Interfaz de acceso a datos para la entidad {@link DetalleVenta}.
 * Hereda las operaciones CRUD definidas en {@link Crud}.
 * @author Miguel Guzman
 * @version 1.0.0
 * @see DetalleVenta
 * @see Crud
 */
public interface DetalleVentaDAO extends Crud<DetalleVenta, Integer> {

}
