
package org.libreria.model;

/**
 * Representa un cliente dentro del sistema de librería.
 * Contiene la información personal y de contacto del cliente,
 * incluyendo su CUI, nombre, apellido y correo electrónico.
 * @author Miguel Guzman
 * @version 1.0.0
 */
public class Cliente {

    private long cui;
    private String nombreCliente;
    private String apellidoCliente;
    private String correoElectronico;

    /**
     * Crea un objeto Cliente sin establecer sus valores.
     */
    public Cliente() {
    }

    /**
     * Crea un objeto Cliente con la información proporcionada.
     * @param cui CUI que identifica al cliente.
     * @param nombreCliente Nombre del cliente.
     * @param apellidoCliente Apellido del cliente.
     * @param correoElectronico Correo electrónico del cliente.
     */
    public Cliente(long cui, String nombreCliente, String apellidoCliente, String correoElectronico) {
        this.cui = cui;
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.correoElectronico = correoElectronico;
    }

    /**
     * Obtiene el CUI del cliente.
     * @return El CUI del cliente.
     */
    public long getCui() {
        return cui;
    }

    /**
     * Modifica el CUI del cliente.
     * @param cui Nuevo CUI del cliente.
     */
    public void setCui(long cui) {
        this.cui = cui;
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
     * Obtiene el apellido del cliente.
     * @return El apellido del cliente.
     */
    public String getApellidoCliente() {
        return apellidoCliente;
    }

    /**
     * Modifica el apellido del cliente.
     * @param apellidoCliente Nuevo apellido del cliente.
     */
    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }

    /**
     * Obtiene el correo electrónico del cliente.
     * @return El correo electrónico del cliente.
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * Modifica el correo electrónico del cliente.
     * @param correoElectronico Nuevo correo electrónico del cliente.
     */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    /**
     * Devuelve el nombre completo del cliente como representación textual del objeto.
     * @return El nombre y apellido del cliente.
     */
    @Override
    public String toString() {
        return nombreCliente + " " + apellidoCliente;
    }
}
