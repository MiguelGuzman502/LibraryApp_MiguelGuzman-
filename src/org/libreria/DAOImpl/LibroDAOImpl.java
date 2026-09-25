package org.libreria.DAOImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.libreria.DAO.LibroDAO;
import org.libreria.exception.DaoException;
import org.libreria.model.Libro;
import org.libreria.util.Conexion;

/**
 * Implementación del DAO para gestionar los libros de la librería.
 * Permite listar, buscar, crear, actualizar y eliminar libros.
 * @author Miguel Guzman
 * @version 1.0.0
 * @see LibroDAO
 * @see Libro
 */
public class LibroDAOImpl implements LibroDAO {

    /**
     * Obtiene todos los libros registrados.
     * @return Lista con los libros registrados.
     * @throws DaoException Si ocurre un error al consultar la base de datos.
     */
    @Override
    public ArrayList<Libro> listarTodos() {
        ArrayList<Libro> lista = new ArrayList<>();
        String sql = "{call sp_listar_todos_libros()}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql);
                ResultSet rs = consulta.executeQuery()) {
            while (rs.next()) {
                Libro l = new Libro();
                l.setIsbn(rs.getString("isbn"));
                l.setTitulo(rs.getString("titulo"));
                l.setFechaPublicacion(rs.getString("fecha_publicacion"));
                l.setPrecio(rs.getDouble("precio"));
                l.setIdCategoria(rs.getInt("id_categoria"));
                l.setNitEditorial(rs.getString("nit_editorial"));
                l.setStock(rs.getInt("stock"));
                lista.add(l);
            }
        } catch (SQLException e) {
            throw new DaoException("Error al listar libros: " + e.getMessage(), e);
        }
        return lista;
    }

    /**
     * Busca un libro utilizando su ISBN.
     * @param isbn ISBN del libro que se desea buscar.
     * @return Libro encontrado o null si no existe.
     * @throws DaoException Si ocurre un error al consultar la base de datos.
     */
    @Override
    public Libro buscarPorId(String isbn) {
        Libro l = null;
        String sql = "{call sp_buscar_libro_id(?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setString(1, isbn);
            try (ResultSet rs = consulta.executeQuery()) {
                if (rs.next()) {
                    l = new Libro();
                    l.setIsbn(rs.getString("isbn"));
                    l.setTitulo(rs.getString("titulo"));
                    l.setFechaPublicacion(rs.getString("fecha_publicacion"));
                    l.setPrecio(rs.getDouble("precio"));
                    l.setIdCategoria(rs.getInt("id_categoria"));
                    l.setNitEditorial(rs.getString("nit_editorial"));
                    l.setStock(rs.getInt("stock"));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error al buscar libro: " + e.getMessage(), e);
        }
        return l;
    }

    /**
     * Registra un nuevo libro.
     * @param libro Libro que se desea registrar.
     * @return true si el libro fue registrado correctamente; false en caso contrario.
     * @throws DaoException Si ocurre un error al insertar el libro.
     */
    @Override
    public boolean crear(Libro libro) {
        String sql = "{call sp_crear_libro(?,?,?,?,?,?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setString(1, libro.getIsbn());
            consulta.setString(2, libro.getTitulo());
            consulta.setString(3, libro.getFechaPublicacion());
            consulta.setDouble(4, libro.getPrecio());
            consulta.setInt(5, libro.getIdCategoria());
            consulta.setString(6, libro.getNitEditorial());
            consulta.setInt(7, libro.getStock());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al insertar libro: " + e.getMessage(), e);
        }
    }

    /**
     * Actualiza la información de un libro existente.
     * @param libro Libro con los datos actualizados.
     * @return true si el libro fue actualizado correctamente; false en caso contrario.
     * @throws DaoException Si ocurre un error al actualizar el libro.
     */
    @Override
    public boolean actualizar(Libro libro) {
        String sql = "{call sp_actualizar_libro(?,?,?,?,?,?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setString(1, libro.getIsbn());
            consulta.setString(2, libro.getTitulo());
            consulta.setString(3, libro.getFechaPublicacion());
            consulta.setDouble(4, libro.getPrecio());
            consulta.setInt(5, libro.getIdCategoria());
            consulta.setString(6, libro.getNitEditorial());
            consulta.setInt(7, libro.getStock());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al actualizar libro: " + e.getMessage(), e);
        }
    }

    /**
     * Elimina un libro utilizando su ISBN.
     * @param isbn ISBN del libro que se desea eliminar.
     * @return true si el libro fue eliminado correctamente; false en caso contrario.
     * @throws DaoException Si ocurre un error al eliminar el libro.
     */
    @Override
    public boolean eliminar(String isbn) {
        String sql = "{call sp_eliminar_libro(?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setString(1, isbn);
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al eliminar libro: " + e.getMessage(), e);
        }
    }
}