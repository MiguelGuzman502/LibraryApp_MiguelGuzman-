package org.libreria.manager;

import org.libreria.model.Usuario;

/**
 * Gestiona el contexto de la sesión actual del usuario dentro del sistema.
 * Utiliza el patrón Singleton para garantizar que exista una única instancia
 * de esta clase durante la ejecución de la aplicación.
 * @author Miguel Guzman
 * @version 1.0.0
 * @see Usuario
 */
public class SesionContext {

    /**
     * Instancia única de la clase SesionContext.
     */
    private static SesionContext instancia;

    /**
     * Usuario que actualmente tiene una sesión iniciada.
     */
    private Usuario usuarioActual;

    /**
     * Constructor privado para evitar la creación directa de instancias.
     * La instancia se obtiene mediante el método {@link #getInstancia()}.
     */
    private SesionContext() {
    }

    /**
     * Obtiene la instancia única de SesionContext.
     * Si la instancia aún no existe, se crea automáticamente.
     * @return Instancia única de SesionContext.
     */
    public static synchronized SesionContext getInstancia() {
        if (instancia == null) {
            instancia = new SesionContext();
        }
        return instancia;
    }

    /**
     * Obtiene el usuario que actualmente tiene una sesión iniciada.
     * @return Usuario actualmente autenticado o null si no existe una sesión activa.
     */
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    /**
     * Establece el usuario que tendrá la sesión activa.
     * @param usuario Usuario que se establecerá como usuario actual.
     */
    public void setUsuarioActual(Usuario usuario) {
        this.usuarioActual = usuario;
    }

    /**
     * Cierra la sesión actual eliminando el usuario almacenado
     * en el contexto de sesión.
     */
    public void cerrarSesion() {
        this.usuarioActual = null;
    }
}