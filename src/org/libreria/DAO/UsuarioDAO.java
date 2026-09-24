package org.libreria.DAO;

import java.util.ArrayList;
import org.libreria.model.Usuario;

/**
 * Interfaz de acceso a datos para la gestión de usuarios
 * dentro del sistema de librería.
 * Define las operaciones relacionadas con el inicio de sesión,
 * creación, actualización, eliminación y consulta de usuarios.
 * @author Miguel Guzman
 * @version 1.0.0
 * @see Usuario
 */
public interface UsuarioDAO {

    /**
     * Permite iniciar sesión utilizando las credenciales
     * de un usuario.
     * @param usernarme Nombre de usuario utilizado para iniciar sesión.
     * @param passwordHash Contraseña del usuario en formato hash.
     * @return Usuario correspondiente a las credenciales proporcionadas,
     *         o null si las credenciales no son válidas.
     */
    public Usuario iniciarSesion(String usernarme, String passwordHash);

    /**
     * Crea un nuevo usuario en el sistema.
     * @param usuario Usuario que se desea registrar.
     * @return true si el usuario fue creado correctamente;
     *         false en caso contrario.
     */
    public boolean crearUsuario(Usuario usuario);

    /**
     * Actualiza la información de un usuario existente.
     * @param usuario Usuario con los datos actualizados.
     * @return true si el usuario fue actualizado correctamente;
     *         false en caso contrario.
     */
    public boolean actualizarUsuario(Usuario usuario);

    /**
     * Cambia la contraseña de un usuario.
     * @param idUsuario Identificador del usuario.
     * @param passwordHash Nueva contraseña en formato hash.
     * @return true si la contraseña fue cambiada correctamente;
     *         false en caso contrario.
     */
    public boolean cambiarPassword(int idUsuario, String passwordHash);

    /**
     * Desactiva un usuario dentro del sistema.
     * @param idUsuario Identificador del usuario que se desea desactivar.
     * @return true si el usuario fue desactivado correctamente;
     *         false en caso contrario.
     */
    public boolean desactivarUsuario(int idUsuario);

    /**
     * Elimina un usuario del sistema.
     * @param idUsuario Identificador del usuario que se desea eliminar.
     * @return true si el usuario fue eliminado correctamente;
     *         false en caso contrario.
     */
    public boolean eliminarUsuario(int idUsuario);

    /**
     * Obtiene todos los usuarios registrados en el sistema.
     * @return Lista que contiene todos los usuarios registrados.
     */
    public ArrayList<Usuario> listarTodosUsuarios();

    /**
     * Busca un usuario mediante su identificador.
     * @param idUsuario Identificador del usuario que se desea buscar.
     * @return Usuario encontrado o null si no existe.
     */
    public Usuario obtenerUsuarioPorId(int idUsuario);
}
