package org.libreria.DAOImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.libreria.DAO.FacturaDAO;
import org.libreria.exception.DaoException;
import org.libreria.model.LineaFactura;
import org.libreria.util.Conexion;

/**
 * Implementación de la interfaz {@link FacturaDAO} para realizar
 * las operaciones de consulta relacionadas con las facturas.
 * Utiliza procedimientos almacenados para obtener la información
 * detallada de una factura.
 * @author Miguel Guzman
 * @version 1.0.0
 * @see FacturaDAO
 * @see LineaFactura
 * @see Conexion
 */
public class FacturaDAOImpl implements FacturaDAO {

    /**
     * Busca la información de una factura mediante el número de venta.
     * @param noVenta Número de venta que identifica la factura que se desea consultar.
     * @return Lista que contiene las líneas y la información correspondiente
     *         a la factura consultada.
     * @throws DaoException si ocurre un error al consultar la información
     *         de la factura en la base de datos.
     */
    @Override
    public ArrayList<LineaFactura> buscarFactura(int noVenta) {
        ArrayList<LineaFactura> lista = new ArrayList<>();
        String sql = "{call sp_buscar_factura(?)}";
        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setInt(1, noVenta);
            try (ResultSet rs = consulta.executeQuery()) {
                while (rs.next()) {
                    LineaFactura linea = new LineaFactura();
                    linea.setNumeroFactura(rs.getInt("numero_factura"));
                    linea.setFechaEmision(rs.getString("fecha_emision"));
                    linea.setCuiCliente(rs.getLong("cui_cliente"));
                    linea.setNombreCliente(rs.getString("nombre_cliente"));
                    linea.setCorreoCliente(rs.getString("correo_cliente"));
                    linea.setIsbnLibro(rs.getString("isbn_libro"));
                    linea.setTituloLibro(rs.getString("titulo_libro"));
                    linea.setCantidad(rs.getInt("cantidad"));
                    linea.setPrecioUnitario(rs.getDouble("precio_unitario"));
                    linea.setSubtotal(rs.getDouble("subtotal"));
                    linea.setUsuarioAtendio(rs.getString("usuario_atendio"));
                    linea.setGranTotal(rs.getDouble("gran_total"));
                    lista.add(linea);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error al buscar la factura: " + e.getMessage(), e);
        }
        return lista;
    }
}