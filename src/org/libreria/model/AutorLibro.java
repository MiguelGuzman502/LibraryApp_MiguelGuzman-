
package org.libreria.model;

/**
 * Representa la relación entre un autor y un libro dentro del sistema de librería.
 * Contiene el identificador de la relación, el identificador del autor
 * y el ISBN del libro asociado.
 * @author Miguel Guzman 
 * @version 1.0.0
 */
public class AutorLibro {

    private int idAutorLibro;
    private int idAutor;
    private String isbn;

    /**
     * Crea un objeto AutorLibro sin establecer sus valores.
     */
    public AutorLibro() {
    }

    /**
     * Crea un objeto AutorLibro con la información proporcionada.
     * @param idAutorLibro Identificador de la relación entre el autor y el libro.
     * @param idAutor Identificador del autor.
     * @param isbn ISBN del libro relacionado.
     */
    public AutorLibro(int idAutorLibro, int idAutor, String isbn) {
        this.idAutorLibro = idAutorLibro;
        this.idAutor = idAutor;
        this.isbn = isbn;
    }

    /**
     * Obtiene el identificador de la relación entre autor y libro.
     * @return El identificador de la relación.
     */
    public int getIdAutorLibro() {
        return idAutorLibro;
    }

    /**
     * Modifica el identificador de la relación entre autor y libro.
     * @param idAutorLibro Nuevo identificador de la relación.
     */
    public void setIdAutorLibro(int idAutorLibro) {
        this.idAutorLibro = idAutorLibro;
    }

    /**
     * Obtiene el identificador del autor.
     * @return El identificador del autor.
     */
    public int getIdAutor() {
        return idAutor;
    }

    /**
     * Modifica el identificador del autor.
     * @param idAutor Nuevo identificador del autor.
     */
    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    /**
     * Obtiene el ISBN del libro relacionado.
     * @return El ISBN del libro.
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Modifica el ISBN del libro relacionado.
     * @param isbn Nuevo ISBN del libro.
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
