package org.libreria.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import org.libreria.DAO.ClienteDAO;
import org.libreria.DAO.UsuarioDAO;
import org.libreria.DAO.VentaDAO;
import org.libreria.DAOImpl.ClienteDAOImpl;
import org.libreria.DAOImpl.UsuarioDAOImpl;
import org.libreria.DAOImpl.VentaDAOImpl;
import org.libreria.exception.DaoException;
import org.libreria.exception.ValidacionException;
import org.libreria.manager.SesionContext;
import org.libreria.model.Cliente;
import org.libreria.model.Usuario;
import org.libreria.model.Venta;
import org.libreria.system.Main;

/**
 * Controlador para gestionar la lista de ventas.
 * @author Miguel Guzman
 * @version 1.0.0
 */
public class ListaVentasController implements Initializable {

    @FXML
    private TextField txtTotal;
    @FXML
    private DatePicker dpFecha;
    @FXML
    private ComboBox<Usuario> cmbUsuario;
    @FXML
    private ComboBox<Cliente> cmbCliente;
    @FXML
    private Label lblMensaje;
    @FXML
    private TableView<Venta> tablaVentas;
    @FXML
    private TableColumn colNoVenta;
    @FXML
    private TableColumn colFechaVenta;
    @FXML
    private TableColumn colTotalVenta;
    @FXML
    private TableColumn colCuiCliente;
    @FXML
    private TableColumn colUsuario;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnPrimero;
    @FXML
    private Button btnAnterior;
    @FXML
    private Button btnSiguiente;
    @FXML
    private Button btnUltimo;
    @FXML
    private TextField txtBuscar;

    private boolean modoEdicion = false;
    private Venta enEdicion;

    private final VentaDAO ventaDAO = new VentaDAOImpl();
    private final ClienteDAO clienteDAO = new ClienteDAOImpl();
    private final UsuarioDAO usuarioDAO = new UsuarioDAOImpl();

    private final ObservableList<Venta> listaVentas
            = FXCollections.observableArrayList();

    private final FilteredList<Venta> ventasFiltradas
            = new FilteredList<>(listaVentas, p -> true);

    /**
     * Inicializa la pantalla de ventas.
     * @param location ubicación utilizada para resolver las rutas.
     * @param resources recursos utilizados por la interfaz.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarTabla();
        cargarClientes();
        cargarUsuarios();
        tablaVentas.setItems(ventasFiltradas);
        seleccionarFila();
        configurarTabla();
        configurarBusqueda();
    }

    /**
     * Configura las columnas de la tabla de ventas.
     */
    public void configurarTabla() {
        colNoVenta.setCellValueFactory(
                new PropertyValueFactory<Venta, Integer>("noVenta"));

        colFechaVenta.setCellValueFactory(
                new PropertyValueFactory<Venta, String>("fechaVenta"));

        colTotalVenta.setCellValueFactory(
                new PropertyValueFactory<Venta, Double>("totalVenta"));

        colCuiCliente.setCellValueFactory(
                new PropertyValueFactory<Venta, Long>("cuiCliente"));

        colUsuario.setCellValueFactory(
                new PropertyValueFactory<Venta, Integer>("idUsuario"));
    }

    /**
     * Carga las ventas desde la base de datos.
     */
    private void cargarTabla() {
        try {
            listaVentas.setAll(ventaDAO.listarTodos());
        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Carga los clientes en el ComboBox.
     */
    private void cargarClientes() {
        try {
            cmbCliente.setItems(
                    FXCollections.observableArrayList(
                            clienteDAO.listarTodos()));
        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Carga los usuarios en el ComboBox.
     */
    private void cargarUsuarios() {
        cmbUsuario.setConverter(new StringConverter<Usuario>() {

            @Override
            public String toString(Usuario usuario) {
                return usuario == null
                        ? ""
                        : usuario.getId()
                        + " - "
                        + usuario.getUsername();
            }

            @Override
            public Usuario fromString(String string) {
                return null;
            }
        });

        try {
            cmbUsuario.setItems(
                    FXCollections.observableArrayList(
                            usuarioDAO.listarTodosUsuarios()));
        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Configura el campo de búsqueda de ventas.
     */
    private void configurarBusqueda() {
        txtBuscar.textProperty().addListener(
                (obs, oldValue, newValue) -> filtrarVentas());
    }

    /**
     * Filtra las ventas según el texto ingresado.
     */
    private void filtrarVentas() {
        String busqueda
                = txtBuscar.getText().trim().toLowerCase();

        if (busqueda.isEmpty()) {
            ventasFiltradas.setPredicate(p -> true);
        } else {
            ventasFiltradas.setPredicate(venta
                    -> String.valueOf(venta.getNoVenta())
                            .contains(busqueda)
                    || venta.getFechaVenta()
                            .toLowerCase().contains(busqueda)
                    || String.valueOf(venta.getTotalVenta())
                            .contains(busqueda)
                    || String.valueOf(venta.getCuiCliente())
                            .contains(busqueda)
                    || String.valueOf(venta.getIdUsuario())
                            .contains(busqueda));
        }
    }

    /**
     * Permite seleccionar una venta de la tabla y mostrar sus datos.
     */
    private void seleccionarFila() {
        tablaVentas.getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (obs, oldSelection, newSelection) -> {

                            if (newSelection != null) {
                                txtTotal.setText(
                                        String.valueOf(
                                                newSelection.getTotalVenta()));

                                cmbCliente.setValue(null);

                                for (Cliente cliente
                                : cmbCliente.getItems()) {

                                    if (cliente.getCui()
                                    == newSelection.getCuiCliente()) {

                                        cmbCliente.setValue(cliente);
                                        break;
                                    }
                                }

                                desactivarFormulario();

                                String fecha
                                = newSelection.getFechaVenta();

                                if (fecha != null
                                && !fecha.isEmpty()) {

                                    try {
                                        dpFecha.setValue(
                                                LocalDate.parse(
                                                        fecha.substring(0, 10)));
                                    } catch (Exception e) {
                                        dpFecha.setValue(null);
                                    }

                                } else {
                                    dpFecha.setValue(null);
                                }

                                cmbUsuario.setValue(null);

                                for (Usuario usuario
                                : cmbUsuario.getItems()) {

                                    if (usuario.getId()
                                    == newSelection.getIdUsuario()) {

                                        cmbUsuario.setValue(usuario);
                                        break;
                                    }
                                }
                            }
                        });
    }

    /**
     * Guarda una nueva venta o actualiza una existente.
     */
    @FXML
    private void handleGuardar() {
        try {
            ValidacionException.validarNoVacio(
                    txtTotal.getText(), "total");

            ValidacionException.validarDecimal(
                    txtTotal.getText(), "total");

            ValidacionException.validarNoNulo(
                    cmbCliente.getValue(),
                    "Seleccione un cliente.");

            ValidacionException.validarNoNulo(
                    cmbUsuario.getValue(),
                    "Seleccione el usuario que atendió.");

            Venta venta = new Venta(
                    modoEdicion
                            ? enEdicion.getNoVenta()
                            : 0,
                    dpFecha.getValue() != null
                    ? dpFecha.getValue().toString()
                    : null,
                    Double.parseDouble(
                            txtTotal.getText().trim()),
                    cmbCliente.getValue().getCui(),
                    cmbUsuario.getValue() != null
                    ? cmbUsuario.getValue().getId()
                    : SesionContext.getInstancia()
                            .getUsuarioActual()
                            .getId());

            boolean guardado;

            if (modoEdicion) {
                guardado = ventaDAO.actualizar(venta);
            } else {
                guardado = ventaDAO.crear(venta);
            }

            if (guardado) {
                lblMensaje.setText(
                        modoEdicion
                                ? "Venta actualizada exitosamente."
                                : "Venta registrada exitosamente.");

                cargarTabla();
                limpiarFormulario();
                desactivarFormulario();
                activarNavegacion();
                modoEdicion = false;

            } else {
                mostrarError("No se pudo guardar la venta.");
            }

        } catch (ValidacionException e) {
            mostrarAdvertencia(e.getMessage());
            lblMensaje.setText(e.getMessage());

        } catch (Exception e) {
            mostrarError(
                    "Error al guardar: " + e.getMessage());
        }
    }

    /**
     * Cancela la operación actual y limpia el formulario.
     */
    @FXML
    private void handleCancelar() {
        limpiarFormulario();
        desactivarFormulario();
        activarNavegacion();
        modoEdicion = false;
        enEdicion = null;
        lblMensaje.setText("");
    }

    /**
     * Prepara el formulario para registrar una nueva venta.
     */
    @FXML
    private void handleNuevo() {
        modoEdicion = false;
        enEdicion = null;
        limpiarFormulario();
        activarFormulario();
        desactivarNavegacion();
        tablaVentas.getSelectionModel().clearSelection();
        lblMensaje.setText("");
        dpFecha.setValue(LocalDate.now());
        txtTotal.requestFocus();
    }

    /**
     * Permite editar la venta seleccionada.
     */
    @FXML
    private void handleEditar() {
        Venta seleccion
                = tablaVentas.getSelectionModel().getSelectedItem();

        if (seleccion == null) {
            mostrarError(
                    "Seleccione una venta de la tabla para editar.");
            return;
        }

        modoEdicion = true;
        enEdicion = seleccion;
        activarFormulario();
        desactivarNavegacion();
        lblMensaje.setText("");
    }

    /**
     * Selecciona la primera venta de la tabla.
     */
    @FXML
    private void handlePrimero() {
        if (!tablaVentas.getItems().isEmpty()) {
            tablaVentas.getSelectionModel().selectFirst();
            tablaVentas.scrollTo(0);
        }
    }

    /**
     * Selecciona la venta anterior.
     */
    @FXML
    private void handleAnterior() {
        if (!tablaVentas.getItems().isEmpty()) {
            tablaVentas.getSelectionModel().selectPrevious();

            if (tablaVentas.getSelectionModel()
                    .getSelectedIndex() >= 0) {

                tablaVentas.scrollTo(
                        tablaVentas.getSelectionModel()
                                .getSelectedIndex());
            }
        }
    }

    /**
     * Selecciona la siguiente venta.
     */
    @FXML
    private void handleSiguiente() {
        if (!tablaVentas.getItems().isEmpty()) {
            tablaVentas.getSelectionModel().selectNext();

            if (tablaVentas.getSelectionModel()
                    .getSelectedIndex() >= 0) {

                tablaVentas.scrollTo(
                        tablaVentas.getSelectionModel()
                                .getSelectedIndex());
            }
        }
    }

    /**
     * Selecciona la última venta de la tabla.
     */
    @FXML
    private void handleUltimo() {
        if (!tablaVentas.getItems().isEmpty()) {
            tablaVentas.getSelectionModel().selectLast();

            tablaVentas.scrollTo(
                    tablaVentas.getItems().size() - 1);
        }
    }

    /**
     * Abre la factura de la venta seleccionada.
     */
    @FXML
    private void handleVerFactura() {
        Venta seleccion
                = tablaVentas.getSelectionModel().getSelectedItem();

        if (seleccion == null) {
            mostrarError(
                    "Seleccione una venta de la tabla para ver su factura.");
            return;
        }

        FacturaController.setNoVentaSeleccionada(
                seleccion.getNoVenta());

        try {
            Main.cambiarEscena(
                    "/org/libreria/view/fxml/FacturaView.fxml");

        } catch (Exception e) {
            mostrarError(
                    "Error al abrir la factura: "
                    + e.getMessage());
        }
    }

    /**
     * Regresa al dashboard principal.
     */
    @FXML
    private void handleVolver() {
        try {
            Main.cambiarEscena(
                    Main.rutaDashboardSegunRol());

        } catch (Exception e) {
            mostrarError(
                    "Error al volver al menú: "
                    + e.getMessage());
        }
    }

    /**
     * Limpia los campos del formulario.
     */
    private void limpiarFormulario() {
        txtTotal.clear();
        dpFecha.setValue(null);
        cmbUsuario.setValue(null);
        cmbCliente.setValue(null);
    }

    /**
     * Activa los campos del formulario.
     */
    private void activarFormulario() {
        txtTotal.setDisable(false);
        dpFecha.setDisable(false);
        cmbCliente.setDisable(false);
        cmbUsuario.setDisable(false);
    }

    /**
     * Desactiva los campos del formulario.
     */
    private void desactivarFormulario() {
        txtTotal.setDisable(true);
        dpFecha.setDisable(true);
        cmbUsuario.setDisable(true);
        cmbCliente.setDisable(true);
    }

    /**
     * Activa los controles de navegación.
     */
    private void activarNavegacion() {
        tablaVentas.setDisable(false);
        btnNuevo.setDisable(false);
        btnEditar.setDisable(false);
        btnPrimero.setDisable(false);
        btnAnterior.setDisable(false);
        btnSiguiente.setDisable(false);
        btnUltimo.setDisable(false);
        txtBuscar.setDisable(false);
    }

    /**
     * Desactiva los controles de navegación.
     */
    private void desactivarNavegacion() {
        tablaVentas.setDisable(true);
        btnNuevo.setDisable(true);
        btnEditar.setDisable(true);
        btnPrimero.setDisable(true);
        btnAnterior.setDisable(true);
        btnSiguiente.setDisable(true);
        btnUltimo.setDisable(true);
        txtBuscar.setDisable(true);
    }

    /**
     * Muestra un mensaje de error en pantalla.
     * @param mensaje mensaje que se mostrará.
     */
    private void mostrarError(String mensaje) {
        Alert alert
                = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Muestra un mensaje de advertencia en pantalla.
     * @param mensaje mensaje que se mostrará.
     */
    private void mostrarAdvertencia(String mensaje) {
        Alert alert
                = new Alert(Alert.AlertType.WARNING);

        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
