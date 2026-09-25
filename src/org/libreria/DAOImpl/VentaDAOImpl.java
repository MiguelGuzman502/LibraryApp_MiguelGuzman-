package org.libreria.DAOImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.libreria.dao.DetalleVentaDAO;
import org.libreria.dao.VentaDAO;
import org.libreria.exception.DaoException;
import org.libreriamodel.DetalleVenta;
import org.libreria.model.LineaVenta;
import org.libreria.model.Venta;
import org.libreria.util.Conexion;

/**
 * Implementación de la interfaz {@link VentaDAO} para gestionar
 * las operaciones de acceso a datos relacionadas con las ventas.
 * Utiliza procedimientos almacenados para realizar las operaciones
 * de consulta, creación, actualización y eliminación de ventas.
 * También permite crear una venta junto con sus líneas y actualizar
 * el stock de los libros correspondientes.
 * @author Miguel Guzman
 * @version 1.0.0
 * @see VentaDAO
 * @see Venta
 * @see LineaVenta
 * @see DetalleVentaDAO
 * @see Conexion
 */
public class VentaDAOImpl implements VentaDAO {

    /**
     * Objeto utilizado para gestionar los detalles asociados
     * a cada venta.
     */
    private final DetalleVentaDAO detalleVentaDAO = new DetalleVentaDAOImpl();

    /**
     * Obtiene todas las ventas registradas en la base de datos.
     * @return Lista que contiene todas las ventas registradas.
     * @throws DaoException si ocurre un error al consultar las ventas
     *         en la base de datos.
     */
    @Override
    public ArrayList<Venta> listarTodos() {
        ArrayList<Venta> lista = new ArrayList<>();
        String sql = "{call sp_listar_ventas()}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql);
                ResultSet rs = consulta.executeQuery()) {
            while (rs.next()) {
                Venta v = new Venta();
                v.setNoVenta(rs.getInt("no_venta"));
                v.setFechaVenta(rs.getString("fecha_venta"));
                v.setTotalVenta(rs.getDouble("total_venta"));
                v.setCuiCliente(rs.getLong("cui_cliente"));
                v.setIdUsuario(rs.getInt("id_usuario"));
                lista.add(v);
            }
        } catch (SQLException e) {
            throw new DaoException("Error al listar ventas: " + e.getMessage(), e);
        }
        return lista;
    }

    /**
     * Busca una venta mediante su número de venta.
     * @param noVenta Número que identifica la venta que se desea buscar.
     * @return Venta encontrada o null si no existe.
     * @throws DaoException si ocurre un error al realizar la consulta
     *         en la base de datos.
     */
    @Override
    public Venta buscarPorId(Integer noVenta) {
        Venta v = null;
        String sql = "{call sp_buscar_venta(?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setInt(1, noVenta);
            try (ResultSet rs = consulta.executeQuery()) {
                if (rs.next()) {
                    v = new Venta();
                    v.setNoVenta(rs.getInt("no_venta"));
                    v.setFechaVenta(rs.getString("fecha_venta"));
                    v.setTotalVenta(rs.getDouble("total_venta"));
                    v.setCuiCliente(rs.getLong("cui_cliente"));
                    v.setIdUsuario(rs.getInt("id_usuario"));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error al buscar venta: " + e.getMessage(), e);
        }
        return v;
    }

    /**
     * Registra una nueva venta en la base de datos.
     * @param venta Venta que contiene la información que se desea registrar.
     * @return true si la venta fue creada correctamente; false en caso contrario.
     * @throws DaoException si ocurre un error al insertar la venta
     *         en la base de datos.
     */
    @Override
    public boolean crear(Venta venta) {
        String sql = "{call sp_insertar_venta(?,?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setDouble(1, venta.getTotalVenta());
            consulta.setString(2, String.valueOf(venta.getCuiCliente()));
            consulta.setInt(3, venta.getIdUsuario());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al insertar venta: " + e.getMessage(), e);
        }
    }

    /**
     * Actualiza la información de una venta existente.
     * @param venta Venta que contiene los datos actualizados.
     * @return true si la venta fue actualizada correctamente; false en caso contrario.
     * @throws DaoException si ocurre un error al actualizar la venta
     *         en la base de datos.
     */
    @Override
    public boolean actualizar(Venta venta) {
        String sql = "{call sp_actualizar_venta(?,?,?,?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setInt(1, venta.getNoVenta());
            if (venta.getFechaVenta() == null || venta.getFechaVenta().isEmpty()) {
                consulta.setNull(2, java.sql.Types.DATE);
            } else {
                consulta.setDate(2, java.sql.Date.valueOf(venta.getFechaVenta().substring(0, 10)));
            }
            consulta.setDouble(3, venta.getTotalVenta());
            consulta.setLong(4, venta.getCuiCliente());
            consulta.setInt(5, venta.getIdUsuario());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al actualizar venta: " + e.getMessage(), e);
        }
    }

    /**
     * Crea una venta completa, incluyendo el encabezado,
     * las líneas de venta y el descuento del stock correspondiente.
     * Obtiene el número de venta generado y posteriormente registra
     * cada detalle asociado.
     * @param venta Venta que contiene la información del encabezado.
     * @param lineas Lista de líneas que pertenecen a la venta.
     * @return Número de venta generado si la operación fue exitosa;
     *         -1 si no se pudo crear la venta.
     * @throws DaoException si ocurre un error al insertar la venta,
     *         registrar sus detalles o descontar el stock.
     */
    @Override
    public int crearVenta(Venta venta, List<LineaVenta> lineas) {
        int noVenta = -1;
        String sql = "{call sp_insertar_venta(?,?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setDouble(1, venta.getTotalVenta());
            consulta.setString(2, String.valueOf(venta.getCuiCliente()));
            consulta.setInt(3, venta.getIdUsuario());
            int filasAfectadas = consulta.executeUpdate();
            if (filasAfectadas > 0) {
                try (Statement sentencia = conexion.createStatement();
                        ResultSet rs = sentencia.executeQuery("SELECT LAST_INSERT_ID()")) {
                    if (rs.next()) {
                        noVenta = rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error al insertar venta: " + e.getMessage(), e);
        }

        if (noVenta > 0) {
            for (LineaVenta linea : lineas) {
                DetalleVenta detalle = new DetalleVenta(0, noVenta,
                        linea.getIsbn(), linea.getCantidad(), linea.getPrecio());
                detalleVentaDAO.crear(detalle);
                descontarStock(linea.getIsbn(), linea.getCantidad());
            }
        }
        return noVenta;
    }

    /**
     * Descuenta del inventario la cantidad correspondiente
     * a un libro vendido.
     * @param isbn ISBN que identifica el libro.
     * @param cantidad Cantidad de ejemplares que se deben descontar.
     * @return true si el stock fue actualizado correctamente;
     *         false en caso contrario.
     * @throws DaoException si ocurre un error al actualizar el stock
     *         en la base de datos.
     */
    private boolean descontarStock(String isbn, int cantidad) {
        String sql = "{call sp_descontar_stock(?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setString(1, isbn);
            consulta.setInt(2, cantidad);
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al descontar stock: " + e.getMessage(), e);
        }
    }

    /**
     * Elimina una venta mediante su número de venta.
     * @param noVenta Número que identifica la venta que se desea eliminar.
     * @return true si la venta fue eliminada correctamente; false en caso contrario.
     * @throws DaoException si ocurre un error al eliminar la venta
     *         de la base de datos.
     */
    @Override
    public boolean eliminar(Integer noVenta) {
        String sql = "{call sp_eliminar_venta(?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setInt(1, noVenta);
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al eliminar venta: " + e.getMessage(), e);
        }
    }
}