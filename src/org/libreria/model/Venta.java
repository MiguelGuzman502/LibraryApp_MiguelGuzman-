
package org.libreria.model;

/**
 * Representa una venta realizada dentro del sistema de librería.
 * Contiene la información de la venta, incluyendo su número,
 * fecha, total, cliente y usuario que la registró.
 * @author Miguel Guzman
 * @version 1.0.0
 */
public class Venta {

    private int noVenta;
    private String fechaVenta;
    private double totalVenta;
    private long cuiCliente;
    private int idUsuario;

    /**
     * Crea una nueva instancia de Venta sin datos iniciales.
     */
    public Venta() {
    }

    /**
     * Crea una venta con todos sus datos.
     * @param noVenta Número que identifica la venta.
     * @param fechaVenta Fecha en que se realizó la venta.
     * @param totalVenta Total de la venta.
     * @param cuiCliente CUI del cliente asociado a la venta.
     * @param idUsuario Identificador del usuario que registró la venta.
     */
    public Venta(int noVenta, String fechaVenta, double totalVenta,
                 long cuiCliente, int idUsuario) {
        this.noVenta = noVenta;
        this.fechaVenta = fechaVenta;
        this.totalVenta = totalVenta;
        this.cuiCliente = cuiCliente;
        this.idUsuario = idUsuario;
    }

    /**
     * Obtiene el número de la venta.
     * @return Número de la venta.
     */
    public int getNoVenta() {
        return noVenta;
    }

    /**
     * Establece el número de la venta.
     * @param noVenta Nuevo número de la venta.
     */
    public void setNoVenta(int noVenta) {
        this.noVenta = noVenta;
    }

    /**
     * Obtiene la fecha en que se realizó la venta.
     * @return Fecha de la venta.
     */
    public String getFechaVenta() {
        return fechaVenta;
    }

    /**
     * Establece la fecha de la venta.
     * @param fechaVenta Nueva fecha de la venta.
     */
    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    /**
     * Obtiene el total de la venta.
     * @return Total de la venta.
     */
    public double getTotalVenta() {
        return totalVenta;
    }

    /**
     * Establece el total de la venta.
     * @param totalVenta Nuevo total de la venta.
     */
    public void setTotalVenta(double totalVenta) {
        this.totalVenta = totalVenta;
    }

    /**
     * Obtiene el CUI del cliente asociado a la venta.
     * @return CUI del cliente.
     */
    public long getCuiCliente() {
        return cuiCliente;
    }

    /**
     * Establece el CUI del cliente asociado a la venta.
     * @param cuiCliente CUI del cliente.
     */
    public void setCuiCliente(long cuiCliente) {
        this.cuiCliente = cuiCliente;
    }

    /**
     * Obtiene el identificador del usuario que registró la venta.
     * @return Identificador del usuario.
     */
    public int getIdUsuario() {
        return idUsuario;
    }

    /**
     * Establece el identificador del usuario que registró la venta.
     * @param idUsuario Identificador del usuario.
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}

