package org.libreria.DAOImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.libreria.dao.AutorDAO;
import org.libreria.exception.DaoException;
import org.libreira.model.Autor;
import org.libreria.util.Conexion;

/**
 * Implementación de la interfaz {@link AutorDAO} para gestionar
 * las operaciones de acceso a datos relacionadas con los autores.
 * Utiliza procedimientos almacenados para realizar las operaciones
 * de consulta, creación, actualización y eliminación de autores.
 * @author Miguel Guzman
 * @version 1.0.0
 * @see AutorDAO
 * @see Autor
 * @see Conexion
 */
public class AutorDAOImpl implements AutorDAO {

    /**
     * Obtiene todos los autores registrados en la base de datos.
     * @return Lista que contiene todos los autores registrados.
     * @throws DaoException si ocurre un error al consultar
     *         la información de los autores.
     */
    @Override
    public ArrayList<Autor> listarTodos() {
        ArrayList<Autor> lista = new ArrayList<>();
        String sql = "{call sp_listarautores()}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql);
                ResultSet rs = consulta.executeQuery()) {
            while (rs.next()) {
                Autor a = new Autor();
                a.setIdAutor(rs.getInt("id_autor"));
                a.setNombreAutor(rs.getString("nombre_autor"));
                a.setApellidoAutor(rs.getString("apellido_autor"));
                a.setNacionalidad(rs.getString("nacionalidad"));
                a.setBiografia(rs.getString("biografia"));
                lista.add(a);
            }
        } catch (SQLException e) {
            throw new DaoException("Error al listar autores: " + e.getMessage(), e);
        }
        return lista;
    }

    /**
     * Busca un autor por medio de su identificador.
     * @param idAutor Identificador del autor que se desea buscar.
     * @return Autor encontrado o null si no existe.
     * @throws DaoException si ocurre un error al realizar la consulta.
     */
    @Override
    public Autor buscarPorId(Integer idAutor) {
        Autor a = null;
        String sql = "{call sp_buscarautor(?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setInt(1, idAutor);
            try (ResultSet rs = consulta.executeQuery()) {
                if (rs.next()) {
                    a = new Autor();
                    a.setIdAutor(rs.getInt("id_autor"));
                    a.setNombreAutor(rs.getString("nombre_autor"));
                    a.setApellidoAutor(rs.getString("apellido_autor"));
                    a.setNacionalidad(rs.getString("nacionalidad"));
                    a.setBiografia(rs.getString("biografia"));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error al buscar autor: " + e.getMessage(), e);
        }
        return a;
    }

    /**
     * Registra un nuevo autor en la base de datos.
     * @param autor Autor que se desea registrar.
     * @return true si el autor fue creado correctamente;
     *         false en caso contrario.
     * @throws DaoException si ocurre un error al insertar
     *         la información del autor.
     */
    @Override
    public boolean crear(Autor autor) {
        String sql = "{call sp_insertarautor(?,?,?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setString(1, autor.getNombreAutor());
            consulta.setString(2, autor.getApellidoAutor());
            consulta.setString(3, autor.getNacionalidad());
            consulta.setString(4, autor.getBiografia());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al insertar autor: " + e.getMessage(), e);
        }
    }

    /**
     * Actualiza la información de un autor existente.
     * @param autor Autor que contiene los datos actualizados.
     * @return true si el autor fue actualizado correctamente;
     *         false en caso contrario.
     * @throws DaoException si ocurre un error al actualizar
     *         la información del autor.
     */
    @Override
    public boolean actualizar(Autor autor) {
        String sql = "{call sp_actualizarautor(?,?,?,?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setInt(1, autor.getIdAutor());
            consulta.setString(2, autor.getNombreAutor());
            consulta.setString(3, autor.getApellidoAutor());
            consulta.setString(4, autor.getNacionalidad());
            consulta.setString(5, autor.getBiografia());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al actualizar autor: " + e.getMessage(), e);
        }
    }

    /**
     * Elimina un autor de la base de datos utilizando su identificador.
     * @param idAutor Identificador del autor que se desea eliminar.
     * @return true si el autor fue eliminado correctamente;
     *         false en caso contrario.
     * @throws DaoException si ocurre un error al eliminar
     *         la información del autor.
     */
    @Override
    public boolean eliminar(Integer idAutor) {
        String sql = "{call sp_eliminarautor(?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setInt(1, idAutor);
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al eliminar autor: " + e.getMessage(), e);
        }
    }
}