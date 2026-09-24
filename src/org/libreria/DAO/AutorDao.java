package org.libreria.DAO;

import org.libreria.model.Autor;

/**
 * Interfaz de acceso a datos para la entidad {@link Autor}.
 * Hereda las operaciones CRUD definidas en {@link Crud}.
 * @author Miguel Guzman
 * @version 1.0.0
 * @see Autor
 * @see Crud
 */
public interface AutorDao extends Crud<Autor, Integer> {

}
