package org.libreria.DAOImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.libreria.DAO.DetalleVentaDAO;
import org.libreria.exception.DaoException;
import org.libreria.model.DetalleVenta;
import org.libreria.util.Conexion;

/**
 * Implementación de la interfaz {@link DetalleVentaDAO} para gestionar
 * las operaciones de acceso a datos relacionadas con los detalles de venta.
 * Utiliza procedimientos almacenados para realizar las operaciones
 * de consulta, creación, actualización y eliminación.
 * @author Miguel Guzman
 * @version 1.0.0
 * @see DetalleVentaDAO
 * @see DetalleVenta
 * @see Conexion
 */
public class DetalleVentaDAOImpl implements DetalleVentaDAO {

    /**
     * Obtiene todos los detalles de venta registrados en la base de datos.
     * @return Lista que contiene todos los detalles de venta registrados.
     * @throws DaoException si ocurre un error al consultar la información
     *         en la base de datos.
     */
    @Override
    public ArrayList<DetalleVenta> listarTodos() {
        ArrayList<DetalleVenta> lista = new ArrayList<>();
        String sql = "{call sp_listar_detalle_venta()}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql);
                ResultSet rs = consulta.executeQuery()) {
            while (rs.next()) {
                DetalleVenta dv = new DetalleVenta();
                dv.setIdDetalleVenta(rs.getInt("id_detalle_venta"));
                dv.setNoVenta(rs.getInt("no_venta"));
                dv.setIsbn(rs.getString("isbn"));
                dv.setCantidad(rs.getInt("cantidad"));
                dv.setPrecio(rs.getDouble("precio"));
                lista.add(dv);
            }
        } catch (SQLException e) {
            throw new DaoException("Error al listar detalle_venta: " + e.getMessage(), e);
        }
        return lista;
    }

    /**
     * Busca un detalle de venta mediante su identificador.
     * @param idDetalleVenta Identificador del detalle de venta que se desea buscar.
     * @return Detalle de venta encontrado o null si no existe.
     * @throws DaoException si ocurre un error al realizar la consulta
     *         en la base de datos.
     */
    @Override
    public DetalleVenta buscarPorId(Integer idDetalleVenta) {
        DetalleVenta dv = null;
        String sql = "{call sp_buscar_detalle_venta(?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setInt(1, idDetalleVenta);
            try (ResultSet rs = consulta.executeQuery()) {
                if (rs.next()) {
                    dv = new DetalleVenta();
                    dv.setIdDetalleVenta(rs.getInt("id_detalle_venta"));
                    dv.setNoVenta(rs.getInt("no_venta"));
                    dv.setIsbn(rs.getString("isbn"));
                    dv.setCantidad(rs.getInt("cantidad"));
                    dv.setPrecio(rs.getDouble("precio"));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error al buscar detalle_venta: " + e.getMessage(), e);
        }
        return dv;
    }

    /**
     * Registra un nuevo detalle de venta en la base de datos.
     * @param detalleVenta Detalle de venta que se desea registrar.
     * @return true si el detalle fue creado correctamente; false en caso contrario.
     * @throws DaoException si ocurre un error al insertar la información
     *         en la base de datos.
     */
    @Override
    public boolean crear(DetalleVenta detalleVenta) {
        String sql = "{call sp_insertar_detalle_venta(?,?,?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setInt(1, detalleVenta.getNoVenta());
            consulta.setString(2, detalleVenta.getIsbn());
            consulta.setInt(3, detalleVenta.getCantidad());
            consulta.setDouble(4, detalleVenta.getPrecio());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al insertar detalle_venta: " + e.getMessage(), e);
        }
    }

    /**
     * Actualiza un detalle de venta existente en la base de datos.
     * @param detalleVenta Detalle de venta que contiene los datos actualizados.
     * @return true si el detalle fue actualizado correctamente; false en caso contrario.
     * @throws DaoException si ocurre un error al actualizar la información
     *         en la base de datos.
     */
    @Override
    public boolean actualizar(DetalleVenta detalleVenta) {
        String sql = "{call sp_actualizar_detalle_venta(?,?,?,?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setInt(1, detalleVenta.getIdDetalleVenta());
            consulta.setInt(2, detalleVenta.getNoVenta());
            consulta.setString(3, detalleVenta.getIsbn());
            consulta.setInt(4, detalleVenta.getCantidad());
            consulta.setDouble(5, detalleVenta.getPrecio());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al actualizar detalle_venta: " + e.getMessage(), e);
        }
    }

    /**
     * Elimina un detalle de venta mediante su identificador.
     * @param idDetalleVenta Identificador del detalle de venta que se desea eliminar.
     * @return true si el detalle fue eliminado correctamente; false en caso contrario.
     * @throws DaoException si ocurre un error al eliminar la información
     *         en la base de datos.
     */
    @Override
    public boolean eliminar(Integer idDetalleVenta) {
        String sql = "{call sp_eliminar_detalle_venta(?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setInt(1, idDetalleVenta);
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al eliminar detalle_venta: " + e.getMessage(), e);
        }
    }
}