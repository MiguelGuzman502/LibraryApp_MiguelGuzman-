package org.libreria.DAOImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.libreria.dao.AutorLibroDAO;
import org.libreria.exception.DaoException;
import org.libreria.model.AutorLibro;
import org.libreria.util.Conexion;

/**
 * Implementación de la interfaz {@link AutorLibroDAO} para gestionar
 * las operaciones de acceso a datos relacionadas con la relación
 * entre autores y libros.
 * Utiliza procedimientos almacenados para realizar las operaciones
 * de consulta, creación, actualización y eliminación.
 * @author Miguel Guzman
 * @version 1.0.0
 * @see AutorLibroDAO
 * @see AutorLibro
 * @see Conexion
 */
public class AutorLibroDAOImpl implements AutorLibroDAO {

    /**
     * Obtiene todos los registros de autores asociados a libros.
     * @return Lista que contiene todos los registros de autores y libros.
     * @throws DaoException si ocurre un error al consultar
     *         la información en la base de datos.
     */
    @Override
    public ArrayList<AutorLibro> listarTodos() {
        ArrayList<AutorLibro> lista = new ArrayList<>();
        String sql = "{call sp_listarautoreslibro()}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql);
                ResultSet rs = consulta.executeQuery()) {
            while (rs.next()) {
                AutorLibro al = new AutorLibro();
                al.setIdAutorLibro(rs.getInt("id_autor_libro"));
                al.setIdAutor(rs.getInt("id_autor"));
                al.setIsbn(rs.getString("isbn"));
                lista.add(al);
            }
        } catch (SQLException e) {
            throw new DaoException("Error al listar autores_libro: " + e.getMessage(), e);
        }
        return lista;
    }

    /**
     * Busca un registro de autor y libro mediante su identificador.
     * @param idAutorLibro Identificador de la relación entre autor y libro.
     * @return Registro encontrado o null si no existe.
     * @throws DaoException si ocurre un error al realizar la consulta.
     */
    @Override
    public AutorLibro buscarPorId(Integer idAutorLibro) {
        AutorLibro al = null;
        String sql = "{call sp_buscarautorlibro(?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setInt(1, idAutorLibro);
            try (ResultSet rs = consulta.executeQuery()) {
                if (rs.next()) {
                    al = new AutorLibro();
                    al.setIdAutorLibro(rs.getInt("id_autor_libro"));
                    al.setIdAutor(rs.getInt("id_autor"));
                    al.setIsbn(rs.getString("isbn"));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error al buscar autor_libro: " + e.getMessage(), e);
        }
        return al;
    }

    /**
     * Registra una nueva relación entre un autor y un libro.
     * @param autorLibro Relación entre el autor y el libro que se desea crear.
     * @return true si el registro fue creado correctamente;
     *         false en caso contrario.
     * @throws DaoException si ocurre un error al insertar
     *         la información en la base de datos.
     */
    @Override
    public boolean crear(AutorLibro autorLibro) {
        String sql = "{call sp_insertarautorlibro(?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setInt(1, autorLibro.getIdAutor());
            consulta.setString(2, autorLibro.getIsbn());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al insertar autor_libro: " + e.getMessage(), e);
        }
    }

    /**
     * Actualiza una relación existente entre un autor y un libro.
     * @param autorLibro Relación que contiene los datos actualizados.
     * @return true si la relación fue actualizada correctamente;
     *         false en caso contrario.
     * @throws DaoException si ocurre un error al actualizar
     *         la información en la base de datos.
     */
    @Override
    public boolean actualizar(AutorLibro autorLibro) {
        String sql = "{call sp_actualizarautorlibro(?,?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setInt(1, autorLibro.getIdAutorLibro());
            consulta.setInt(2, autorLibro.getIdAutor());
            consulta.setString(3, autorLibro.getIsbn());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al actualizar autor_libro: " + e.getMessage(), e);
        }
    }

    /**
     * Elimina una relación entre un autor y un libro mediante su identificador.
     * @param idAutorLibro Identificador de la relación que se desea eliminar.
     * @return true si la relación fue eliminada correctamente;
     *         false en caso contrario.
     * @throws DaoException si ocurre un error al eliminar
     *         la información de la base de datos.
     */
    @Override
    public boolean eliminar(Integer idAutorLibro) {
        String sql = "{call sp_eliminarautorlibro(?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setInt(1, idAutorLibro);
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al eliminar autor_libro: " + e.getMessage(), e);
        }
    }
}
