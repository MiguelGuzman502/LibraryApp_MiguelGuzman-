package org.libreria.DAOImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.libreria.dao.ClienteDAO;
import org.libreria.exception.DaoException;
import org.libreria.model.Cliente;
import org.libreria.util.Conexion;

/**
 * Implementación de la interfaz {@link ClienteDAO} para gestionar
 * las operaciones de acceso a datos relacionadas con los clientes.
 * Utiliza procedimientos almacenados para realizar las operaciones
 * de consulta, creación, actualización y eliminación de clientes.
 * @author Miguel Guzman
 * @version 1.0.0
 * @see ClienteDAO
 * @see Cliente
 * @see Conexion
 */
public class ClienteDAOImpl implements ClienteDAO {

    /**
     * Obtiene todos los clientes registrados en la base de datos.
     * @return Lista que contiene todos los clientes registrados.
     * @throws DaoException si ocurre un error al consultar la información
     *         de los clientes en la base de datos.
     */
    @Override
    public ArrayList<Cliente> listarTodos() {
        ArrayList<Cliente> lista = new ArrayList<>();
        String sql = "{call sp_listarclientes()}";
        try (Connection conexion = Conexion.getInstancia().conectar(); CallableStatement consulta = conexion.prepareCall(sql); ResultSet rs = consulta.executeQuery()) {
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setCui(rs.getLong("cui"));
                c.setNombreCliente(rs.getString("nombre_cliente"));
                c.setApellidoCliente(rs.getString("apellido_cliente"));
                c.setCorreoElectronico(rs.getString("correo_electronico"));
                lista.add(c);
            }
        } catch (SQLException e) {
            throw new DaoException("Error al listar clientes: " + e.getMessage(), e);
        }
        return lista;
    }

    /**
     * Busca un cliente mediante su número de CUI.
     * @param cui Número de CUI que identifica al cliente.
     * @return Cliente encontrado o null si no existe.
     * @throws DaoException si ocurre un error al realizar la consulta
     *         en la base de datos.
     */
    @Override
    public Cliente buscarPorId(Long cui) {
        Cliente c = null;
        String sql = "{call sp_buscarcliente(?)}";
        try (Connection conexion = Conexion.getInstancia().conectar(); CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setLong(1, cui);
            try (ResultSet rs = consulta.executeQuery()) {
                if (rs.next()) {
                    c = new Cliente();
                    c.setCui(rs.getLong("cui"));
                    c.setNombreCliente(rs.getString("nombre_cliente"));
                    c.setApellidoCliente(rs.getString("apellido_cliente"));
                    c.setCorreoElectronico(rs.getString("correo_electronico"));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Error al buscar cliente: " + e.getMessage(), e);
        }
        return c;
    }

    /**
     * Registra un nuevo cliente en la base de datos.
     * @param cliente Cliente que se desea registrar.
     * @return true si el cliente fue creado correctamente; false en caso contrario.
     * @throws DaoException si ocurre un error al insertar la información
     *         del cliente en la base de datos.
     */
    @Override
    public boolean crear(Cliente cliente) {
        String sql = "{call sp_insertarcliente(?,?,?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar(); CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setLong(1, cliente.getCui());
            consulta.setString(2, cliente.getNombreCliente());
            consulta.setString(3, cliente.getApellidoCliente());
            consulta.setString(4, cliente.getCorreoElectronico());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al insertar cliente: " + e.getMessage(), e);
        }
    }

    /**
     * Actualiza la información de un cliente existente.
     * @param cliente Cliente que contiene los datos actualizados.
     * @return true si el cliente fue actualizado correctamente; false en caso contrario.
     * @throws DaoException si ocurre un error al actualizar la información
     *         del cliente en la base de datos.
     */
    @Override
    public boolean actualizar(Cliente cliente) {
        String sql = "{call sp_actualizarcliente(?,?,?,?)}";
        try (Connection conexion = Conexion.getInstancia().conectar(); 
                CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setLong(1, cliente.getCui());
            consulta.setString(2, cliente.getNombreCliente());
            consulta.setString(3, cliente.getApellidoCliente());
            consulta.setString(4, cliente.getCorreoElectronico());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al actualizar cliente: " + e.getMessage(), e);
        }
    }

    /**
     * Elimina un cliente de la base de datos mediante su número de CUI.
     * @param cui Número de CUI del cliente que se desea eliminar.
     * @return true si el cliente fue eliminado correctamente; false en caso contrario.
     * @throws DaoException si ocurre un error al eliminar la información
     *         del cliente en la base de datos.
     */
    @Override
    public boolean eliminar(Long cui) {
        String sql = "{call sp_eliminarcliente(?)}";
        try (Connection conexion = Conexion.getInstancia().conectar(); CallableStatement consulta = conexion.prepareCall(sql)) {
            consulta.setLong(1, cui);
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Error al eliminar cliente: " + e.getMessage(), e);
        }
    }
}