
package org.libreria.DAO;

import org.libreria.model.AutorLibro;

/**
 * Interfaz de acceso a datos para la entidad {@link AutorLibro}.
 * Hereda las operaciones CRUD definidas en {@link Crud}.
 * @author Miguel Guzman
 * @version 1.0.0
 * @see AutorLibro
 * @see Crud
 */
public interface AutorLibroDAO extends Crud<AutorLibro, Integer> {

}
