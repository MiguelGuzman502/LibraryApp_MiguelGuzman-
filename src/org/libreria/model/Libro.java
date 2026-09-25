
package org.libreria.model;

/**
 * Representa un libro dentro del sistema de librería.
 * Contiene la información principal del libro, como ISBN,
 * título, fecha de publicación, precio, categoría, editorial y stock.
 * @author Miguel Guzman 
 * @version 1.0.0
 */
public class Libro {

    private String isbn;
    private String titulo;
    private String fechaPublicacion;
    private double precio;
    private int idCategoria;
    private String nitEditorial;
    private int stock;

    /**
     * Crea un nuevo objeto Libro con la información proporcionada.
     * @param isbn Código ISBN que identifica al libro.
     * @param titulo Título del libro.
     * @param fechaPublicacion Fecha en que fue publicado el libro.
     * @param precio Precio del libro.
     * @param idCategoria Identificador de la categoría a la que pertenece.
     * @param nitEditorial NIT de la editorial que publica el libro.
     * @param stock Cantidad disponible del libro.
     */
    public Libro(String isbn, String titulo, String fechaPublicacion,
      double precio, int idCategoria, String nitEditorial, int stock) {

        this.isbn = isbn;
        this.titulo = titulo;
        this.fechaPublicacion = fechaPublicacion;
        this.precio = precio;
        this.idCategoria = idCategoria;
        this.nitEditorial = nitEditorial;
        this.stock = stock;
    }

    public Libro() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * Obtiene el ISBN del libro.
     * @return El ISBN del libro.
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Modifica el ISBN del libro.
     * @param isbn Nuevo ISBN del libro.
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Obtiene el título del libro.
     * @return El título del libro.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Modifica el título del libro.

     * @param titulo Nuevo título del libro.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtiene la fecha de publicación del libro.
     * @return La fecha de publicación.
     */
    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    /**
     * Modifica la fecha de publicación del libro.
     * @param fechaPublicacion Nueva fecha de publicación.
     */
    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    /**
     * Obtiene el precio del libro.
     * @return El precio del libro.
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Modifica el precio del libro.
     * @param precio Nuevo precio del libro.
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Obtiene el identificador de la categoría del libro.
     * @return El ID de la categoría.
     */
    public int getIdCategoria() {
        return idCategoria;
    }

    /**
     * Modifica el identificador de la categoría del libro.
     * @param idCategoria Nuevo ID de la categoría.
     */
    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    /**
     * Obtiene el NIT de la editorial del libro.
     * @return El NIT de la editorial.
     */
    public String getNitEditorial() {
        return nitEditorial;
    }

    /**
     * Modifica el NIT de la editorial del libro.
     * @param nitEditorial Nuevo NIT de la editorial.
     */
    public void setNitEditorial(String nitEditorial) {
        this.nitEditorial = nitEditorial;
    }

    /**
     * Obtiene la cantidad disponible del libro.
     * @return La cantidad de libros disponibles en stock.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Modifica la cantidad disponible del libro.
     * @param stock Nueva cantidad disponible en stock.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Devuelve el título del libro como representación textual del objeto.
     * @return El título del libro.
     */
    @Override
    public String toString() {
        return titulo;
    }
}
