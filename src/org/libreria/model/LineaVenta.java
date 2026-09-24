
package org.libreria.model;

/**
 * Representa una línea de venta dentro del sistema de librería.
 * Contiene un libro y la cantidad de unidades que se desean vender.
 * Se utiliza temporalmente en la pantalla de venta antes de guardar
 * los detalles de la venta en la base de datos.
 * @author Miguel Guzman
 * @version 1.0.0
 */
public class LineaVenta {

    private Libro libro;
    private int cantidad;

    /**
     * Crea un objeto LineaVenta sin establecer sus valores.
     */
    public LineaVenta() {
    }

    /**
     * Crea una línea de venta con un libro y una cantidad determinada.
     * @param libro Libro que forma parte de la venta.
     * @param cantidad Cantidad de unidades del libro.
     */
    public LineaVenta(Libro libro, int cantidad) {
        this.libro = libro;
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el libro asociado a la línea de venta.
     * @return El libro de la línea de venta.
     */
    public Libro getLibro() {
        return libro;
    }

    /**
     * Modifica el libro asociado a la línea de venta.
     * @param libro Nuevo libro de la línea de venta.
     */
    public void setLibro(Libro libro) {
        this.libro = libro;
    }
    /**
     * Obtiene la cantidad de unidades del libro.
     * @return La cantidad de unidades.
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Modifica la cantidad de unidades del libro.
     * @param cantidad Nueva cantidad de unidades.
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el ISBN del libro asociado.
     * @return El ISBN del libro.
     */
    public String getIsbn() {
        return libro.getIsbn();
    }

    /**
     * Obtiene el título del libro asociado.
     * @return El título del libro.
     */
    public String getTitulo() {
        return libro.getTitulo();
    }

    /**
     * Obtiene el precio del libro asociado.
     * @return El precio del libro.
     */
    public double getPrecio() {
        return libro.getPrecio();
    }

    /**
     * Calcula el subtotal de la línea de venta.
     * El subtotal se obtiene multiplicando el precio del libro
     * por la cantidad de unidades.
     * @return El subtotal de la línea de venta.
     */
    public double getSubtotal() {
        return libro.getPrecio() * cantidad;
    }
}