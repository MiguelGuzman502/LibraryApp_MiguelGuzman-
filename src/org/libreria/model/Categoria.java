
package org.libreria.model;

/**
 * Representa una categoría dentro del sistema de librería.
 * Contiene el identificador y el nombre de la categoría
 * utilizada para clasificar los libros.
 * @author Miguel Guzman 
 * @version 1.0.0
 */
public class Categoria {

    private int idCategoria;
    private String nombreCategoria;

    /**
     * Crea un objeto Categoria sin establecer sus valores.
     */
    public Categoria() {
    }

    /**
     * Crea un objeto Categoria con la información proporcionada.
     * @param idCategoria Identificador de la categoría.
     * @param nombreCategoria Nombre de la categoría.
     */
    public Categoria(int idCategoria, String nombreCategoria) {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
    }

    /**
     * Obtiene el identificador de la categoría.
     * @return El identificador de la categoría.
     */
    public int getIdCategoria() {
        return idCategoria;
    }

    /**
     * Modifica el identificador de la categoría.
     * @param idCategoria Nuevo identificador de la categoría.
     */
    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    /**
     * Obtiene el nombre de la categoría.
     * @return El nombre de la categoría.
     */
    public String getNombreCategoria() {
        return nombreCategoria;
    }

    /**
     * Modifica el nombre de la categoría.
     * @param nombreCategoria Nuevo nombre de la categoría.
     */
    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    /**
     * Devuelve el nombre de la categoría como representación textual del objeto.
     * @return El nombre de la categoría.
     */
    @Override
    public String toString() {
        return nombreCategoria;
    }
}