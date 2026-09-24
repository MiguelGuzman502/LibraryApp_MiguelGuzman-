package org.libreria.DAO;

import org.libreria.model.Categoria;

/**
 * Interfaz de acceso a datos para la entidad {@link Categoria}.
 * Hereda las operaciones CRUD definidas en {@link Crud}.
 * @author  Miguel Guzman
 * @version 1.0.0
 * @see Categoria
 * @see Crud
 */
public interface CategoriaDAO extends Crud<Categoria, Integer> {

}
