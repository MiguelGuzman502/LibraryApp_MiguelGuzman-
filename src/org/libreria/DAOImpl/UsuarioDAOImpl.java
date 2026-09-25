package org.libreria.DAOImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import org.libreria.dao.UsuarioDAO;
import org.libreria.exception.DaoException;
import org.libreria.model.Usuario;
import org.libreria.util.Conexion;

/**
 * Implementación de la interfaz {@link UsuarioDAO} para gestionar
 * las operaciones de acceso a datos relacionadas con los usuarios.
 * Utiliza procedimientos almacenados para realizar operaciones
 * de inicio de sesión, creación, actualización, consulta,
 * desactivación y eliminación de usuarios.
 * @author Miguel Guzman
 * @version 1.0.0
 * @see UsuarioDAO
 * @see Usuario
 * @see Conexion
 */
public class UsuarioDAOImpl implements UsuarioDAO {

    /**
     * Permite iniciar sesión utilizando el nombre de usuario
     * y la contraseña en formato hash.
     * @param usernarme Nombre de usuario utilizado para iniciar sesión.
     * @param passwordHash Contraseña del usuario en formato hash.
     * @return Usuario correspondiente a las credenciales proporcionadas
     *         o null si no se encuentra un usuario.
     * @throws DaoException si ocurre un error al consultar las credenciales
     *         en la base de datos.
     */
    @Override
    public Usuario iniciarSesion(String usernarme, String passwordHash) {
        Usuario usuario = null;
        String sql = "{call sp_iniciar_sesion(?,?)}";

        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {

            consulta.setString(1, usernarme);
            consulta.setString(2, passwordHash);

            try (ResultSet tablaResultado = consulta.executeQuery()) {
                if (tablaResultado.next()) {
                    usuario = new Usuario();
                    usuario.setId(tablaResultado.getInt(1));
                    usuario.setUsername(tablaResultado.getString(2));
                    usuario.setRol(tablaResultado.getString(3));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error al iniciar sesion: " + e.getMessage(), e);
        }

        return usuario;
    }

    /**
     * Crea un nuevo usuario en la base de datos.
     * @param usuario Usuario que se desea registrar.
     * @return true si el usuario fue creado correctamente;
     *         false en caso contrario.
     * @throws DaoException si ocurre un error al crear el usuario
     *         en la base de datos.
     */
    @Override
    public boolean crearUsuario(Usuario usuario) {
        String sql = "{call sp_crear_usuario(?,?,?,?,?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setString(1, usuario.getUsername());
            consulta.setString(2, usuario.getEmail());
            consulta.setString(3, usuario.getFirstName());
            consulta.setString(4, usuario.getLastName());
            consulta.setString(5, usuario.getPasswordHash());
            consulta.setString(6, usuario.getRol());
            int filasAfectadas = consulta.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al crear usuario: " + e.getMessage(), e);
        }
    }

    /**
     * Actualiza la información de un usuario existente.
     * @param usuario Usuario que contiene los datos actualizados.
     * @return true si el usuario fue actualizado correctamente;
     *         false en caso contrario.
     * @throws DaoException si ocurre un error al actualizar el usuario
     *         en la base de datos.
     */
    @Override
    public boolean actualizarUsuario(Usuario usuario) {
        String sql = "{call sp_actualizar_usuario(?,?,?,?,?,?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setInt(1, usuario.getId());
            consulta.setString(2, usuario.getUsername());
            consulta.setString(3, usuario.getEmail());
            consulta.setString(4, usuario.getFirstName());
            consulta.setString(5, usuario.getLastName());
            consulta.setString(6, usuario.getRol());
            consulta.setBoolean(7, usuario.isActivo());
            int filasAfectadas = consulta.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al actualizar usuario: " + e.getMessage(), e);
        }
    }

    /**
     * Cambia la contraseña de un usuario.
     * @param idUsuario Identificador del usuario.
     * @param passwordHash Nueva contraseña del usuario en formato hash.
     * @return true si la contraseña fue cambiada correctamente;
     *         false en caso contrario.
     * @throws DaoException si ocurre un error al cambiar la contraseña
     *         en la base de datos.
     */
    @Override
    public boolean cambiarPassword(int idUsuario, String passwordHash) {
        String sql = "{call sp_cambiar_password(?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setInt(1, idUsuario);
            consulta.setString(2, passwordHash);
            int filasAfectadas = consulta.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al cambiar password: " + e.getMessage(), e);
        }
    }

    /**
     * Desactiva un usuario dentro del sistema.
     * @param idUsuario Identificador del usuario que se desea desactivar.
     * @return true si el usuario fue desactivado correctamente;
     *         false en caso contrario.
     * @throws DaoException si ocurre un error al desactivar el usuario
     *         en la base de datos.
     */
    @Override
    public boolean desactivarUsuario(int idUsuario) {
        String sql = "{call sp_desactivar_usuario(?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setInt(1, idUsuario);
            int filasAfectadas = consulta.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al desactivar usuario: " + e.getMessage(), e);
        }
    }

    /**
     * Elimina un usuario de la base de datos.
     * @param idUsuario Identificador del usuario que se desea eliminar.
     * @return true si el usuario fue eliminado correctamente;
     *         false en caso contrario.
     * @throws DaoException si ocurre un error al eliminar el usuario
     *         de la base de datos.
     */
    @Override
    public boolean eliminarUsuario(int idUsuario) {
        String sql = "{call sp_eliminar_usuario(?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setInt(1, idUsuario);
            int filasAfectadas = consulta.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al eliminar usuario: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene todos los usuarios registrados en la base de datos.
     * @return Lista que contiene todos los usuarios registrados.
     * @throws DaoException si ocurre un error al consultar los usuarios
     *         en la base de datos.
     */
    @Override
    public ArrayList<Usuario> listarTodosUsuarios() {
        ArrayList<Usuario> lista = new ArrayList<>();
        String sql = "{call sp_listar_todos_usuarios()}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql);
                ResultSet tablaResultado = consulta.executeQuery()) {
            while (tablaResultado.next()) {
                Usuario u = new Usuario();
                u.setId(tablaResultado.getInt("id_usuario"));
                u.setUsername(tablaResultado.getString("username"));
                u.setEmail(tablaResultado.getString("email"));
                u.setFirstName(tablaResultado.getString("first_name"));
                u.setLastName(tablaResultado.getString("last_name"));
                u.setRol(tablaResultado.getString("rol"));
                u.setActivo(tablaResultado.getBoolean("activo"));
                u.setFechaCreacion(tablaResultado.getTimestamp("fecha_creacion"));
                lista.add(u);
            }
        } catch (SQLException e) {
            throw new DaoException("Error al listar todos los usuarios: " + e.getMessage(), e);
        }
        return lista;
    }

    /**
     * Busca un usuario mediante su identificador.
     * @param idUsuario Identificador del usuario que se desea consultar.
     * @return Usuario encontrado o null si no existe.
     * @throws DaoException si ocurre un error al consultar el usuario
     *         en la base de datos.
     */
    @Override
    public Usuario obtenerUsuarioPorId(int idUsuario) {
        Usuario usuario = null;
        String sql = "{call sp_obtener_usuario_por_id(?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setInt(1, idUsuario);
            try (ResultSet tablaResultado = consulta.executeQuery()) {
                if (tablaResultado.next()) {
                    usuario = new Usuario();
                    usuario.setId(tablaResultado.getInt("id_usuario"));
                    usuario.setUsername(tablaResultado.getString("username"));
                    usuario.setEmail(tablaResultado.getString("email"));
                    usuario.setFirstName(tablaResultado.getString("first_name"));
                    usuario.setLastName(tablaResultado.getString("last_name"));
                    usuario.setRol(tablaResultado.getString("rol"));
                    usuario.setActivo(tablaResultado.getBoolean("activo"));
                    usuario.setFechaCreacion(tablaResultado.getTimestamp("fecha_creacion"));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error al obtener usuario por id: " + e.getMessage(), e);
        }
        return usuario;
    }

}