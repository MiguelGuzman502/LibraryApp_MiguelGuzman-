package org.libreria.DAO;
import java.util.ArrayList;

/**
 * Define las operaciones básicas para la gestión de entidades
 * dentro del sistema mediante operaciones CRUD. 
 *
 * @author Miguel Guzman 
 * @version 1.0.0
 */
public interface Crud<T, K> {

    /**
     * Crea una nueva entidad.
     * @param entidad Entidad que se desea crear.
     * @return true si la entidad fue creada correctamente;
     *         false en caso contrario.
     */
    boolean crear(T entidad);

    /**
     * Actualiza una entidad existente.
     * @param entidad Entidad con los datos que se desean actualizar.
     * @return true si la entidad fue actualizada correctamente;
     *         false en caso contrario.
     */
    boolean actualizar(T entidad);

    /**
     * Elimina una entidad utilizando su identificador.
     * @param id Identificador de la entidad que se desea eliminar.
     * @return true si la entidad fue eliminada correctamente;
     *         false en caso contrario.
     */
    boolean eliminar(K id);

    /**
     * Busca una entidad por medio de su identificador.
     * @param id Identificador de la entidad que se desea buscar.
     * @return La entidad encontrada o null si no existe.
     */
    T buscarPorId(K id);

    /**
     * Obtiene todas las entidades registradas.
     * @return Lista con todas las entidades registradas.
     */
    ArrayList<T> listarTodos();
}