
package org.libreria.DAO;

/**
 *es una interfz para el acceso a datos de desde las entidades del sistema
 * es una creacion, conuslta, actualizacion y eliminacion de datos
 * /**
 * Interfaz base para el acceso a datos de las entidades del sistema.
 
 * @param <T> 
 * @param <K> 
 
 * @author Miguel Guzman 
 * @version 1.0.0
 * @see Crud
 */
public interface Dao<T, K> extends Crud<T, K> {
}
