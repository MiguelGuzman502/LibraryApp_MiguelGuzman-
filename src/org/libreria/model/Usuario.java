
package org.libreria.model;

import java.sql.Timestamp;

/**
 * Representa un usuario dentro del sistema de librería.
 * Contiene la información de identificación, acceso, datos personales,
 * rol, estado y fecha de creación del usuario.
 * @author Miguel Guzman
 * @version 1.0.0
 */
public class Usuario {

    private int id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String passwordHash;
    private String rol;
    private boolean activo;
    private Timestamp fechaCreacion;

    /**
     * Crea un objeto Usuario sin establecer sus valores.
     */
    public Usuario() {
    }

    /**
     * Crea un usuario con su identificador, nombre de usuario y rol.
     * @param id Identificador del usuario.
     * @param username Nombre de usuario.
     * @param rol Rol asignado al usuario.
     */
    public Usuario(int id, String username, String rol) {
        this.id = id;
        this.username = username;
        this.rol = rol;
    }

    /**
     * Crea un usuario con sus datos de acceso y datos personales.
     * @param username Nombre de usuario.
     * @param email Correo electrónico del usuario.
     * @param firstName Nombre del usuario.
     * @param lastName Apellido del usuario.
     * @param passwordHash Contraseña almacenada mediante un hash.
     * @param rol Rol asignado al usuario.
     */
    public Usuario(String username, String email, String firstName, String lastName,
       String passwordHash, String rol) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passwordHash = passwordHash;
        this.rol = rol;
    }

    /**
     * Obtiene el rol del usuario.
     * @return El rol del usuario.
     */
    public String getRol() {
        return rol;
    }

    /**
     * Modifica el rol del usuario.
     * @param rol Nuevo rol del usuario.
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * Obtiene el identificador del usuario.
     * @return El identificador del usuario.
     */
    public int getId() {
        return id;
    }

    /**
     * Modifica el identificador del usuario.
     * @param id Nuevo identificador del usuario.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de usuario.
     * @return El nombre de usuario.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Modifica el nombre de usuario.
     * @param username Nuevo nombre de usuario.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     * @return El correo electrónico del usuario.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Modifica el correo electrónico del usuario.
     * @param email Nuevo correo electrónico.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene el nombre del usuario.
     * @return El nombre del usuario.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Modifica el nombre del usuario.
     * @param firstName Nuevo nombre del usuario.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Obtiene el apellido del usuario.
     * @return El apellido del usuario.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Modifica el apellido del usuario.
     * @param lastName Nuevo apellido del usuario.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Obtiene la contraseña almacenada mediante un hash.
     * @return El hash de la contraseña.
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Modifica el hash de la contraseña del usuario.
     * @param passwordHash Nuevo hash de la contraseña.
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * Verifica si el usuario se encuentra activo.
     * @return {@code true} si el usuario está activo; {@code false} en caso contrario.
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * Modifica el estado activo del usuario.
     * @param activo Nuevo estado del usuario.
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    /**
     * Obtiene la fecha de creación del usuario.
     * @return La fecha y hora de creación del usuario.
     */
    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * Modifica la fecha de creación del usuario.
     * @param fechaCreacion Nueva fecha y hora de creación.
     */
    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * Devuelve el nombre de usuario como representación textual del objeto.
     * @return El nombre de usuario.
     */
    @Override
    public String toString() {
        return username;
    }
}