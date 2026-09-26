
package org.libreria.controller;

import java.lang.classfile.Label;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.table.TableColumn;
import javax.swing.text.TableView;
import org.libreria.DAO.FacturaDAO;
import org.libreria.dao.impl.FacturaDAOImpl;
import org.libreria.exception.DaoException;
import org.libreria.model.LineaFactura;
import org.libreria.system.Main;

/**
 * Controlador para mostrar la información de una factura.
 * @author Miguel Guzman
 * @version 1.0.0
 */
public class FacturaController implements Initializable {

    // Mecanismo del proyecto: no hay paso de datos entre vistas, se usa un campo
    // estático que ListaVentasController setea antes de abrir la vista.
    private static int noVentaSeleccionada;

    /**
     * Establece el número de venta que se utilizará para cargar la factura.
     * @param noVenta número de la venta seleccionada.
     */
    public static void setNoVentaSeleccionada(int noVenta) {
        noVentaSeleccionada = noVenta;
    }

    private final FacturaDAO facturaDAO = new FacturaDAOImpl();
    private final ObservableList<LineaFactura> lineasFactura =
            FXCollections.observableArrayList();

    @FXML
    private Label lblNoFactura;
    @FXML
    private Label lblFecha;
    @FXML
    private Label lblCliente;
    @FXML
    private Label lblCui;
    @FXML
    private Label lblCorreo;
    @FXML
    private Label lblUsuario;
    @FXML
    private Label lblTotal;
    @FXML
    private TableView<LineaFactura> tablaLineas;
    @FXML
    private TableColumn colTitulo;
    @FXML
    private TableColumn colIsbn;
    @FXML
    private TableColumn colCantidad;
    @FXML
    private TableColumn colPrecioUnitario;
    @FXML
    private TableColumn colSubtotal;
    @FXML
    private Button btnImprimir;

    /**
     * Inicializa la pantalla de factura y carga sus datos.
     * @param location ubicación utilizada para resolver las rutas.
     * @param resources recursos utilizados por la interfaz.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarTabla();
        cargarFactura();
    }

    /**
     * Configura las columnas de la tabla de detalles de la factura.
     */
    public void configurarTabla() {
        colTitulo.setCellValueFactory(
                new PropertyValueFactory<LineaFactura, String>("tituloLibro"));
        colIsbn.setCellValueFactory(
                new PropertyValueFactory<LineaFactura, String>("isbnLibro"));
        colCantidad.setCellValueFactory(
                new PropertyValueFactory<LineaFactura, Integer>("cantidad"));
        colPrecioUnitario.setCellValueFactory(
                new PropertyValueFactory<LineaFactura, Double>("precioUnitario"));
        colSubtotal.setCellValueFactory(
                new PropertyValueFactory<LineaFactura, Double>("subtotal"));
    }

    /**
     * Carga la información de la factura desde la base de datos.
     */
    private void cargarFactura() {
        try {
            lineasFactura.setAll(
                    facturaDAO.buscarFactura(noVentaSeleccionada));

            if (lineasFactura.isEmpty()) {
                mostrarError(
                        "No se encontró la factura de la venta "
                        + noVentaSeleccionada + ".");
                return;
            }

            // La primera fila trae el encabezado repetido;
            // se usa para llenar los labels.
            LineaFactura encabezado = lineasFactura.get(0);

            lblNoFactura.setText("# " + encabezado.getNumeroFactura());
            lblFecha.setText(encabezado.getFechaEmision());
            lblCliente.setText(encabezado.getNombreCliente());
            lblCui.setText(String.valueOf(encabezado.getCuiCliente()));
            lblCorreo.setText(encabezado.getCorreoCliente());
            lblUsuario.setText(encabezado.getUsuarioAtendio());
            lblTotal.setText(
                    String.format("Q %.2f", encabezado.getGranTotal()));

            tablaLineas.setItems(lineasFactura);

        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Regresa a la lista de ventas desde donde se abrió la factura.
     */
    @FXML
    private void handleVolver() {
        try {
            // Regresa a la lista de ventas (origen de la factura),
            // no al dashboard.
            Principal.cambiarEscena(
                    "/org/ac/view/fxml/ListaVentasView.fxml");

        } catch (Exception e) {
            mostrarError("Error al volver al menú: " + e.getMessage());
        }
    }

    /**
     * Muestra un mensaje indicando que la impresión está en desarrollo.
     */
    @FXML
    private void handleImprimir() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Imprimir");
        alert.setHeaderText(null);
        alert.setContentText(
                "La impresión de la factura está en desarrollo.");
        alert.showAndWait();
    }

    /**
     * Muestra un mensaje de error en pantalla.
     *
     * @param mensaje mensaje que se mostrará.
     */
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}