
package org.libreria.model;

/**
 * Representa el detalle de una venta dentro del sistema de librería
 * Contiene información sobre el libro vendido, la cantidad
 * el precio y la venta que pertenece
 * @author Miguel Guzman
 * @version 1.0.0
 */
public class DetalleVenta {

    private int idDetalleVenta;
    private int noVenta;
    private String isbn;
    private int cantidad;
    private double precio;

    /**
     * Crea un objeto DetalleVenta sin establecer sus valores
     */
    public DetalleVenta() {
    }

    /**
     * Crea un objeto DetalleVenta con la información proporcionada
     * @param idDetalleVenta Identificador del detalle de la venta
     * @param noVenta Número de la venta a la que pertenece el detalle
     * @param isbn ISBN del libro incluido en la venta
     * @param cantidad Cantidad de unidades del libro vendidas
     * @param precio Precio del libro en la venta
     */
    public DetalleVenta(int idDetalleVenta, int noVenta, String isbn, int cantidad, double precio){
        
        this.idDetalleVenta = idDetalleVenta;
        this.noVenta = noVenta;
        this.isbn = isbn;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    /**
     * Obtiene el identificador del detalle de la venta
     * @return El identificador del detalle
     */
    public int getIdDetalleVenta() {
        return idDetalleVenta;
    }

    /**
     * Modifica el identificador del detalle de la venta
     * @param idDetalleVenta Nuevo identificador del detalle
     */
    public void setIdDetalleVenta(int idDetalleVenta) {
        this.idDetalleVenta = idDetalleVenta;
    }

    /**
     * Obtiene el número de la venta
     * @return El número de la venta
     */
    public int getNoVenta() {
        return noVenta;
    }

    /**
     * Modifica el número de la venta
     * @param noVenta Nuevo número de la venta
     */
    public void setNoVenta(int noVenta) {
        this.noVenta = noVenta;
    }

    /**
     * Obtiene el ISBN del libro vendido
     * @return El ISBN del libro
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Modifica el ISBN del libro vendido
     * @param isbn Nuevo ISBN del libro
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Obtiene la cantidad de unidades vendidas
     * @return La cantidad de unidades
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Modifica la cantidad de unidades vendidas
     * @param cantidad Nueva cantidad de unidades
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el precio del libro en la venta
     * @return El precio del libro
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Modifica el precio del libro en la venta
     * @param precio Nuevo precio del libro
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
