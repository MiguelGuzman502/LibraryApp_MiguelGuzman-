package org.libreria.model;

/**
 * Representa una editorial dentro del sistema de librería.
 * Contiene la información de identificación, nombre, teléfono
 * y dirección de la empresa editorial.
 * @author Miguel Guzman
 * @version 1.0.0
 */
public class Editorial {
    private String nit;
    private String nombreEditorial;
    private String telefonoEditorial;
    private String direccionEditoria;

    /**
     * Crea una nueva instancia de Editorial sin datos iniciales.
     */
    public Editorial() {
    }

    /**
     * Crea una editorial con todos sus datos.
     * @param nit Código de identificación tributaria de la editorial.
     * @param nombreEditorial Nombre de la empresa editorial.
     * @param telefonoEditorial Número telefónico de la editorial.
     * @param direccionEditoria Dirección de la empresa editorial.
     */
    public Editorial(String nit, String nombreEditorial,
                     String telefonoEditorial, String direccionEditoria) {
        this.nit = nit;
        this.nombreEditorial = nombreEditorial;
        this.telefonoEditorial = telefonoEditorial;
        this.direccionEditoria = direccionEditoria;
    }

    /**
     * Obtiene el NIT de la editorial.
     * @return NIT de la editorial.
     */
    public String getNit() {
        return nit;
    }

    /**
     * Establece el NIT de la editorial.
     * @param nit Nuevo NIT de la editorial.
     */
    public void setNit(String nit) {
        this.nit = nit;
    }

    /**
     * Obtiene el nombre de la editorial.
     * @return Nombre de la editorial.
     */
    public String getNombreEditorial() {
        return nombreEditorial;
    }

    /**
     * Establece el nombre de la editorial.
     * @param nombreEditorial Nuevo nombre de la editorial.
     */
    public void setNombreEditorial(String nombreEditorial) {
        this.nombreEditorial = nombreEditorial;
    }

    /**
     * Obtiene el número telefónico de la editorial.
     * @return Número telefónico de la editorial.
     */
    public String getTelefonoEditorial() {
        return telefonoEditorial;
    }

    /**
     * Establece el número telefónico de la editorial.
     * @param telefonoEditorial Nuevo número telefónico de la editorial.
     */
    public void setTelefonoEditorial(String telefonoEditorial) {
        this.telefonoEditorial = telefonoEditorial;
    }

    /**
     * Obtiene la dirección de la editorial.
     * @return Dirección de la editorial.
     */
    public String getDireccionEditoria() {
        return direccionEditoria;
    }

    /**
     * Establece la dirección de la editorial.
     * @param direccionEditoria Nueva dirección de la editorial.
     */
    public void setDireccionEditoria(String direccionEditoria) {
        this.direccionEditoria = direccionEditoria;
    }

    /**
     * Devuelve el nombre de la editorial como representación textual del objeto.
     * @return Nombre de la editorial.
     */
    @Override
    public String toString() {
        return nombreEditorial;
    }
}