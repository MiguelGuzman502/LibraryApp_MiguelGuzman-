package org.libreria.DAO;

import org.libreria.model.Libro;

/**
 * Interfaz de acceso a datos para la entidad {@link Libro}.
 * Hereda las operaciones CRUD definidas en {@link Crud}.
 * @author Miguel Guzman
 * @version 1.0.0
 * @see Libro
 * @see Crud
 */
public interface LibroDAO extends Crud<Libro, String> {

}