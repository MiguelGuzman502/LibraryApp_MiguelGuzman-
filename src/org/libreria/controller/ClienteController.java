
package org.libreria.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.libreria.DAO.ClienteDAO;
import org.libreria.DAOImpl.ClienteDAOImpl;
import org.libreria.exception.DaoException;
import org.libreria.exception.ValidacionException;
import org.libreria.model.Cliente;
import org.libreria.system.Main;

/**
 * Controlador para gestionar los clientes de la librería.
 * @author Miguel Guzman
 * @version 1.0.0
 */
public class ClienteController implements Initializable {
    @FXML
    private TextField txtCui;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtCorreo;
    @FXML
    private Label lblMensaje;
    @FXML
    private TableView<Cliente> tablaClientes;
    @FXML
    private TableColumn<Cliente, Long> colCUI;
    @FXML
    private TableColumn<Cliente, String> colNombreCliente;
    @FXML
    private TableColumn<Cliente, String> colApellidoCliente;
    @FXML
    private TableColumn<Cliente, String> colCorreoElectronico;
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
    private final ClienteDAO clienteDAO = new ClienteDAOImpl();
    private final ObservableList<Cliente> listaClientes =
            FXCollections.observableArrayList();

    private final FilteredList<Cliente> clientesFiltrados =
            new FilteredList<>(listaClientes, p -> true);

    /**
     * Inicializa el controlador y configura la tabla y la búsqueda.
     * @param location Ubicación utilizada para resolver recursos.
     * @param resources Recursos utilizados por la interfaz.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarTabla();
        tablaClientes.setItems(clientesFiltrados);
        seleccionarFila();
        configurarTabla();
        configurarBusqueda();
    }

    /**
     * Configura las columnas de la tabla de clientes.
     */
    public void configurarTabla() {
        colCUI.setCellValueFactory(
                new PropertyValueFactory<Cliente, Long>("cui"));

        colNombreCliente.setCellValueFactory(
                new PropertyValueFactory<Cliente, String>("nombreCliente"));

        colApellidoCliente.setCellValueFactory(
                new PropertyValueFactory<Cliente, String>("apellidoCliente"));

        colCorreoElectronico.setCellValueFactory(
                new PropertyValueFactory<Cliente, String>("correoElectronico"));
    }

    /**
     * Carga los clientes registrados en la tabla.
     */
    private void cargarTabla() {
        try {
            listaClientes.setAll(clienteDAO.listarTodos());
        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Configura el campo de búsqueda.
     */
    private void configurarBusqueda() {
        txtBuscar.textProperty().addListener(
                (obs, oldValue, newValue) -> filtrarClientes());
    }

    /**
     * Filtra los clientes según el texto ingresado en la búsqueda.
     */
    private void filtrarClientes() {
        String busqueda = txtBuscar.getText().trim().toLowerCase();

        if (busqueda.isEmpty()) {
            clientesFiltrados.setPredicate(p -> true);
        } else {
            clientesFiltrados.setPredicate(cliente ->
                    String.valueOf(cliente.getCui()).contains(busqueda)
                    || cliente.getNombreCliente().toLowerCase().contains(busqueda)
                    || cliente.getApellidoCliente().toLowerCase().contains(busqueda));
        }
    }

    /**
     * Detecta el cliente seleccionado y muestra sus datos en el formulario.
     */
    private void seleccionarFila() {
        tablaClientes.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {

                    if (newSelection != null) {
                        txtCui.setText(String.valueOf(newSelection.getCui()));
                        txtNombre.setText(newSelection.getNombreCliente());
                        txtApellido.setText(newSelection.getApellidoCliente());
                        txtCorreo.setText(newSelection.getCorreoElectronico());
                        desactivarFormulario();
                    }
                });
    }

    /**
     * Valida y guarda un nuevo cliente o actualiza uno existente.
     */
    @FXML
    private void handleGuardar() {
        try {
            ValidacionException.validarNoVacio(
                    txtCui.getText(), "CUI");

            ValidacionException.validarNoVacio(
                    txtNombre.getText(), "nombre");

            ValidacionException.validarNoVacio(
                    txtApellido.getText(), "apellido");

            ValidacionException.validarNoVacio(
                    txtCorreo.getText(), "correo electrónico");

            ValidacionException.validarNumero(
                    txtCui.getText(), "CUI");

            ValidacionException.validarLongitudExacta(
                    txtCui.getText().trim(), 13,
                    "El CUI debe tener exactamente 13 dígitos.");

            ValidacionException.validarFormatoEmail(
                    txtCorreo.getText(),
                    "El correo electrónico no tiene un formato válido.");

            Cliente cliente = new Cliente();

            cliente.setCui(Long.parseLong(txtCui.getText().trim()));
            cliente.setNombreCliente(txtNombre.getText().trim());
            cliente.setApellidoCliente(txtApellido.getText().trim());
            cliente.setCorreoElectronico(txtCorreo.getText().trim());

            boolean guardado;

            if (modoEdicion) {
                guardado = clienteDAO.actualizar(cliente);
            } else {
                guardado = clienteDAO.crear(cliente);
            }

            if (guardado) {
                lblMensaje.setText(modoEdicion
                        ? "Cliente actualizado exitosamente."
                        : "Cliente registrado exitosamente.");

                cargarTabla();
                limpiarFormulario();
                desactivarFormulario();
                activarNavegacion();
                modoEdicion = false;

            } else {
                mostrarError("No se pudo guardar el cliente.");
            }

        } catch (ValidacionException e) {
            mostrarAdvertencia(e.getMessage());
            lblMensaje.setText(e.getMessage());

        } catch (Exception e) {
            mostrarError("Error al guardar: " + e.getMessage());
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
        lblMensaje.setText("");
    }

    /**
     * Prepara el formulario para registrar un nuevo cliente.
     */
    @FXML
    private void handleNuevoCliente() {
        modoEdicion = false;
        limpiarFormulario();
        activarFormulario();
        desactivarNavegacion();
        tablaClientes.getSelectionModel().clearSelection();
        lblMensaje.setText("");
        txtCui.requestFocus();
    }

    /**
     * Prepara el formulario para editar el cliente seleccionado.
     */
    @FXML
    private void handleEditar() {
        Cliente seleccion =
                tablaClientes.getSelectionModel().getSelectedItem();

        if (seleccion == null) {
            mostrarError("Seleccione un cliente de la tabla para editar.");
            return;
        }

        modoEdicion = true;
        activarFormulario();
        desactivarNavegacion();
        lblMensaje.setText("");
    }

    /**
     * Selecciona el primer cliente de la tabla.
     */
    @FXML
    private void handlePrimero() {
        if (!tablaClientes.getItems().isEmpty()) {
            tablaClientes.getSelectionModel().selectFirst();
            tablaClientes.scrollTo(0);
        }
    }

    /**
     * Selecciona el cliente anterior en la tabla.
     */
    @FXML
    private void handleAnterior() {
        if (!tablaClientes.getItems().isEmpty()) {
            tablaClientes.getSelectionModel().selectPrevious();

            if (tablaClientes.getSelectionModel().getSelectedIndex() >= 0) {
                tablaClientes.scrollTo(
                        tablaClientes.getSelectionModel().getSelectedIndex());
            }
        }
    }

    /**
     * Selecciona el siguiente cliente en la tabla.
     */
    @FXML
    private void handleSiguiente() {
        if (!tablaClientes.getItems().isEmpty()) {
            tablaClientes.getSelectionModel().selectNext();

            if (tablaClientes.getSelectionModel().getSelectedIndex() >= 0) {
                tablaClientes.scrollTo(
                        tablaClientes.getSelectionModel().getSelectedIndex());
            }
        }
    }

    /**
     * Selecciona el último cliente de la tabla.
     */
    @FXML
    private void handleUltimo() {
        if (!tablaClientes.getItems().isEmpty()) {
            tablaClientes.getSelectionModel().selectLast();
            tablaClientes.scrollTo(
                    tablaClientes.getItems().size() - 1);
        }
    }

    /**
     * Regresa al menú principal según el rol del usuario.
     */
    @FXML
    private void handleVolver() {
        try {
            Main.cambiarEscena(Main.rutaDashboardSegunRol());
        } catch (Exception e) {
            mostrarError("Error al volver al menú: " + e.getMessage());
        }
    }

    /**
     * Limpia todos los campos del formulario.
     */
    private void limpiarFormulario() {
        txtCui.clear();
        txtNombre.clear();
        txtApellido.clear();
        txtCorreo.clear();
    }

    /**
     * Activa los campos del formulario.
     */
    private void activarFormulario() {
        txtCui.setDisable(false);
        txtNombre.setDisable(false);
        txtApellido.setDisable(false);
        txtCorreo.setDisable(false);
    }

    /**
     * Desactiva los campos del formulario.
     */
    private void desactivarFormulario() {
        txtCui.setDisable(true);
        txtNombre.setDisable(true);
        txtApellido.setDisable(true);
        txtCorreo.setDisable(true);
    }

    /**
     * Activa los controles de navegación y búsqueda.
     */
    private void activarNavegacion() {
        tablaClientes.setDisable(false);
        btnNuevo.setDisable(false);
        btnEditar.setDisable(false);
        btnPrimero.setDisable(false);
        btnAnterior.setDisable(false);
        btnSiguiente.setDisable(false);
        btnUltimo.setDisable(false);
        txtBuscar.setDisable(false);
    }

    /**
     * Desactiva los controles de navegación y búsqueda.
     */
    private void desactivarNavegacion() {
        tablaClientes.setDisable(true);
        btnNuevo.setDisable(true);
        btnEditar.setDisable(true);
        btnPrimero.setDisable(true);
        btnAnterior.setDisable(true);
        btnSiguiente.setDisable(true);
        btnUltimo.setDisable(true);
        txtBuscar.setDisable(true);
    }

    /**
     * Muestra un mensaje de error.
     * @param mensaje Mensaje que se mostrará.
     */
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Muestra un mensaje de advertencia.
     * @param mensaje Mensaje que se mostrará.
     */
    private void mostrarAdvertencia(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

