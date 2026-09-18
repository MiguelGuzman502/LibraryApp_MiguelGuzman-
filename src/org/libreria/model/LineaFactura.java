
package org.libreria.model;

/**
 * Representa una proyección de solo lectura utilizada para mostrar
 * información detallada de una factura.
 * Combina datos de la venta, cliente, libro y usuario que atendió la venta.
 * @author Miguel Guzman
 * @version 1.0.0
 */
public class LineaFactura {

    private int numeroFactura;
    private String fechaEmision;
    private long cuiCliente;
    private String nombreCliente;
    private String correoCliente;
    private String isbnLibro;
    private String tituloLibro;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;
    private String usuarioAtendio;
    private double granTotal;

    /**
     * Crea un objeto LineaFactura sin establecer sus valores.
     */
    public LineaFactura() {
    }

    /**
     * Obtiene el número de factura.
     * @return El número de factura.
     */
    public int getNumeroFactura() {
        return numeroFactura;
    }

    /**
     * Modifica el número de factura.
     * @param numeroFactura Nuevo número de factura.
     */
    public void setNumeroFactura(int numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    /**
     * Obtiene la fecha de emisión de la factura.
     * @return La fecha de emisión.
     */
    public String getFechaEmision() {
        return fechaEmision;
    }

    /**
     * Modifica la fecha de emisión de la factura.
     * @param fechaEmision Nueva fecha de emisión.
     */
    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    /**
     * Obtiene el CUI del cliente.
     * @return El CUI del cliente.
     */
    public long getCuiCliente() {
        return cuiCliente;
    }

    /**
     * Modifica el CUI del cliente
     * @param cuiCliente Nuevo CUI del cliente.
     */
    public void setCuiCliente(long cuiCliente) {
        this.cuiCliente = cuiCliente;
    }

    /**
     * Obtiene el nombre del cliente.
     * @return El nombre del cliente.
     */
    public String getNombreCliente() {
        return nombreCliente;
    }

    /**
     * Modifica el nombre del cliente.
     * @param nombreCliente Nuevo nombre del cliente.
     */
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    /**
     * Obtiene el correo electrónico del cliente.
     * @return El correo electrónico del cliente.
     */
    public String getCorreoCliente() {
        return correoCliente;
    }

    /**
     * Modifica el correo electrónico del cliente.
     * @param correoCliente Nuevo correo electrónico del cliente.
     */
    public void setCorreoCliente(String correoCliente) {
        this.correoCliente = correoCliente;
    }

    /**
     * Obtiene el ISBN del libro.
     * @return El ISBN del libro.
     */
    public String getIsbnLibro() {
        return isbnLibro;
    }

    /**
     * Modifica el ISBN del libro.
     * @param isbnLibro Nuevo ISBN del libro.
     */
    public void setIsbnLibro(String isbnLibro) {
        this.isbnLibro = isbnLibro;
    }

    /**
     * Obtiene el título del libro.
     * @return El título del libro.
     */
    public String getTituloLibro() {
        return tituloLibro;
    }

    /**
     * Modifica el título del libro.
     * @param tituloLibro Nuevo título del libro.
     */
    public void setTituloLibro(String tituloLibro) {
        this.tituloLibro = tituloLibro;
    }

    /**
     * Obtiene la cantidad de libros vendidos.
     * @return La cantidad de libros.
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Modifica la cantidad de libros vendidos.
     * @param cantidad Nueva cantidad de libros.
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el precio unitario del libro.
     * @return El precio unitario.
     */
    public double getPrecioUnitario() {
        return precioUnitario;
    }

    /**
     * Modifica el precio unitario del libro.
     * @param precioUnitario Nuevo precio unitario.
     */
    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    /**
     * Obtiene el subtotal de la línea de factura.
     * @return El subtotal.
     */
    public double getSubtotal() {
        return subtotal;
    }

    /**
     * Modifica el subtotal de la línea de factura.
     * @param subtotal Nuevo subtotal.
     */
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    /**
     * Obtiene el usuario que atendió la venta.
     * @return El usuario que atendió la venta.
     */
    public String getUsuarioAtendio() {
        return usuarioAtendio;
    }

    /**
     * Modifica el usuario que atendió la venta.
     * @param usuarioAtendio Nuevo usuario que atendió la venta.
     */
    public void setUsuarioAtendio(String usuarioAtendio) {
        this.usuarioAtendio = usuarioAtendio;
    }

    /**
     * Obtiene el total de la factura.
     * @return El total de la factura.
     */
    public double getGranTotal() {
        return granTotal;
    }
    /**
     * Modifica el total de la factura.
     * @param granTotal Nuevo total de la factura.
     */
    public void setGranTotal(double granTotal) {
        this.granTotal = granTotal;
    }
}