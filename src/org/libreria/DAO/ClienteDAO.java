
package org.libreria.DAO;
import org.libreria.model.Cliente;

/**
 * Interfaz de acceso a datos para la entidad {@link Cliente}.
 * Hereda las operaciones CRUD definidas en {@link Crud}.
 * @author Miguel Guzman
 * @version 1.0.0
 * @see Cliente
 * @see Crud
 */
public interface ClienteDAO extends Crud<Cliente, Long> {

}