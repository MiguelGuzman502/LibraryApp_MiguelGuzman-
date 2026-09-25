package org.libreria.DAOImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.libreria.DAO.EditorialDAO;
import org.libreria.exception.DaoException;
import org.libreria.model.Editorial;
import org.libreria.util.Conexion;


/**
 * Implementación de la interfaz {@link EditorialDAO} para gestionar
 * las operaciones de acceso a datos relacionadas con las editoriales.
 * Utiliza procedimientos almacenados para realizar las operaciones
 * de consulta, creación, actualización y eliminación.
 * @author Miguel Guzman
 * @version 1.0.0
 * @see EditorialDAO
 * @see Editorial
 * @see Conexion
 */
public class EditorialDAOImpl implements EditorialDAO {

    /**
     * Obtiene todas las editoriales registradas en la base de datos.
     * @return Lista que contiene todas las editoriales registradas.
     * @throws DaoException si ocurre un error al consultar la información
     *         de las editoriales en la base de datos.
     */
    @Override
    public ArrayList<Editorial> listarTodos() {
        ArrayList<Editorial> lista = new ArrayList<>();
        String sql = "{call sp_listar_todos_editoriales()}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql);
                ResultSet rs = consulta.executeQuery()) {
            while (rs.next()) {
                Editorial e = new Editorial();
                e.setNit(rs.getString("nit"));
                e.setNombreEditorial(rs.getString("nombre_editorial"));
                e.setTelefonoEditorial(rs.getString("telefono_editorial"));
                e.setDireccionEditoria(rs.getString("direccion_editorial"));
                lista.add(e);
            }
        } catch (SQLException ex) {
            throw new DaoException("Error al listar editoriales: " + ex.getMessage(), ex);
        }
        return lista;
    }

    /**
     * Busca una editorial mediante su número de identificación tributaria.
     * @param nit Número de identificación tributaria de la editorial.
     * @return Editorial encontrada o null si no existe.
     * @throws DaoException si ocurre un error al realizar la consulta
     *         en la base de datos.
     */
    @Override
    public Editorial buscarPorId(String nit) {
        Editorial e = null;
        String sql = "{call sp_buscar_editorial_por_id(?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setString(1, nit);
            try (ResultSet rs = consulta.executeQuery()) {
                if (rs.next()) {
                    e = new Editorial();
                    e.setNit(rs.getString("nit"));
                    e.setNombreEditorial(rs.getString("nombre_editorial"));
                    e.setTelefonoEditorial(rs.getString("telefono_editorial"));
                    e.setDireccionEditoria(rs.getString("direccion_editorial"));
                }
            }
        } catch (SQLException ex) {
            throw new DaoException("Error al buscar editorial: " + ex.getMessage(), ex);
        }
        return e;
    }

    /**
     * Registra una nueva editorial en la base de datos.
     * @param editorial Editorial que se desea registrar.
     * @return true si la editorial fue creada correctamente; false en caso contrario.
     * @throws DaoException si ocurre un error al insertar la información
     *         de la editorial en la base de datos.
     */
    @Override
    public boolean crear(Editorial editorial) {
        String sql = "{call sp_crear_editorial(?,?,?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setString(1, editorial.getNit());
            consulta.setString(2, editorial.getNombreEditorial());
            consulta.setString(3, editorial.getTelefonoEditorial());
            consulta.setString(4, editorial.getDireccionEditoria());
            return consulta.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DaoException("Error al insertar editorial: " + ex.getMessage(), ex);
        }
    }

    /**
     * Actualiza la información de una editorial existente.
     * @param editorial Editorial que contiene los datos actualizados.
     * @return true si la editorial fue actualizada correctamente; false en caso contrario.
     * @throws DaoException si ocurre un error al actualizar la información
     *         de la editorial en la base de datos.
     */
    @Override
    public boolean actualizar(Editorial editorial) {
        String sql = "{call sp_actualizar_editorial(?,?,?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setString(1, editorial.getNit());
            consulta.setString(2, editorial.getNombreEditorial());
            consulta.setString(3, editorial.getTelefonoEditorial());
            consulta.setString(4, editorial.getDireccionEditoria());
            return consulta.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DaoException("Error al actualizar editorial: " + ex.getMessage(), ex);
        }
    }

    /**
     * Elimina una editorial mediante su número de identificación tributaria.
     * @param nit Número de identificación tributaria de la editorial que se desea eliminar.
     * @return true si la editorial fue eliminada correctamente; false en caso contrario.
     * @throws DaoException si ocurre un error al eliminar la información
     *         de la editorial en la base de datos.
     */
    @Override
    public boolean eliminar(String nit) {
        String sql = "{call sp_eliminar_editorial(?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setString(1, nit);
            return consulta.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DaoException("Error al eliminar editorial: " + ex.getMessage(), ex);
        }
    }
}