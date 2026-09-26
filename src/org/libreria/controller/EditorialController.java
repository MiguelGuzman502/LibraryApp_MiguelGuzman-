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
import org.libreria.DAO.EditorialDAO;
import org.libreria.DAOImpl.EditorialDAOImpl;
import org.libreria.exception.DaoException;
import org.libreria.exception.ValidacionException;
import org.libreria.model.Editorial;
import org.libreria.system.Main;

/**
 * Controlador para gestionar las editoriales de la biblioteca.
 * @author Miguel Guzman
 * @version 1.0.0
 */
public class EditorialController implements Initializable {
    @FXML
    private TextField txtNit;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtDireccion;
    @FXML
    private Label lblMensaje;
    @FXML
    private TableView<Editorial> tablaEditoriales;
    @FXML
    private TableColumn<Editorial, String> colNit;
    @FXML
    private TableColumn<Editorial, String> colNombre;
    @FXML
    private TableColumn<Editorial, String> colTelefono;
    @FXML
    private TableColumn<Editorial, String> colDireccion;

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

    private final EditorialDAO editorialDAO = new EditorialDAOImpl();

    private final ObservableList<Editorial> listaEditoriales =
            FXCollections.observableArrayList();

    private final FilteredList<Editorial> editorialesFiltradas =
            new FilteredList<>(listaEditoriales, p -> true);

    /**
     * Inicializa el controlador y configura la tabla, búsqueda y selección.
     * @param location ubicación utilizada para resolver las rutas.
     * @param resources recursos utilizados por la interfaz.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarTabla();
        tablaEditoriales.setItems(editorialesFiltradas);
        seleccionarFila();
        configurarTabla();
        configurarBusqueda();
    }

    /**
     * Configura las columnas de la tabla de editoriales.
     */
    public void configurarTabla() {
        colNit.setCellValueFactory(
                new PropertyValueFactory<Editorial, String>("nit"));

        colNombre.setCellValueFactory(
                new PropertyValueFactory<Editorial, String>("nombreEditorial"));

        colTelefono.setCellValueFactory(
                new PropertyValueFactory<Editorial, String>("telefonoEditorial"));

        colDireccion.setCellValueFactory(
                new PropertyValueFactory<Editorial, String>("direccionEditoria"));
    }

    /**
     * Carga las editoriales desde la base de datos.
     */
    private void cargarTabla() {
        try {
            listaEditoriales.setAll(editorialDAO.listarTodos());
        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Configura el campo de búsqueda de editoriales.
     */
    private void configurarBusqueda() {
        txtBuscar.textProperty().addListener(
                (obs, oldValue, newValue) -> filtrarEditoriales());
    }

    /**
     * Filtra las editoriales según el texto ingresado.
     */
    private void filtrarEditoriales() {
        String busqueda = txtBuscar.getText().trim().toLowerCase();

        if (busqueda.isEmpty()) {
            editorialesFiltradas.setPredicate(p -> true);
        } else {
            editorialesFiltradas.setPredicate(editorial ->
                    editorial.getNit().toLowerCase().contains(busqueda)
                    || editorial.getNombreEditorial().toLowerCase().contains(busqueda)
                    || editorial.getTelefonoEditorial().toLowerCase().contains(busqueda)
                    || editorial.getDireccionEditoria().toLowerCase().contains(busqueda));
        }
    }

    /**
     * Permite seleccionar una editorial de la tabla y mostrar sus datos.
     */
    private void seleccionarFila() {
        tablaEditoriales.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        txtNit.setText(newSelection.getNit());
                        txtNombre.setText(newSelection.getNombreEditorial());
                        txtTelefono.setText(newSelection.getTelefonoEditorial());
                        txtDireccion.setText(newSelection.getDireccionEditoria());
                        desactivarFormulario();
                    }
                });
    }

    /**
     * Guarda una nueva editorial o actualiza una existente.
     */
    @FXML
    private void handleGuardar() {
        try {
            ValidacionException.validarNoVacio(txtNit.getText(), "NIT");
            ValidacionException.validarNoVacio(txtNombre.getText(), "nombre");
            ValidacionException.validarNoVacio(txtTelefono.getText(), "teléfono");
            ValidacionException.validarNoVacio(txtDireccion.getText(), "dirección");

            Editorial editorial = new Editorial();

            editorial.setNit(txtNit.getText().trim());
            editorial.setNombreEditorial(txtNombre.getText().trim());
            editorial.setTelefonoEditorial(txtTelefono.getText().trim());
            editorial.setDireccionEditoria(txtDireccion.getText().trim());

            boolean guardado;

            if (modoEdicion) {
                guardado = editorialDAO.actualizar(editorial);
            } else {
                guardado = editorialDAO.crear(editorial);
            }

            if (guardado) {
                lblMensaje.setText(modoEdicion
                        ? "Editorial actualizada exitosamente."
                        : "Editorial registrada exitosamente.");

                cargarTabla();
                limpiarFormulario();
                desactivarFormulario();
                activarNavegacion();
                modoEdicion = false;
            } else {
                mostrarError("No se pudo guardar la editorial.");
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
     * Prepara el formulario para registrar una nueva editorial.
     */
    @FXML
    private void handleNuevo() {
        modoEdicion = false;
        limpiarFormulario();
        activarFormulario();
        desactivarNavegacion();
        tablaEditoriales.getSelectionModel().clearSelection();
        lblMensaje.setText("");
        txtNit.requestFocus();
    }

    /**
     * Permite editar la editorial seleccionada.
     */
    @FXML
    private void handleEditar() {
        Editorial seleccion =
                tablaEditoriales.getSelectionModel().getSelectedItem();

        if (seleccion == null) {
            mostrarError("Seleccione una editorial de la tabla para editar.");
            return;
        }

        modoEdicion = true;
        activarFormulario();
        desactivarNavegacion();
        lblMensaje.setText("");
    }

    /**
     * Selecciona la primera editorial de la tabla.
     */
    @FXML
    private void handlePrimero() {
        if (!tablaEditoriales.getItems().isEmpty()) {
            tablaEditoriales.getSelectionModel().selectFirst();
            tablaEditoriales.scrollTo(0);
        }
    }

    /**
     * Selecciona la editorial anterior en la tabla.
     */
    @FXML
    private void handleAnterior() {
        if (!tablaEditoriales.getItems().isEmpty()) {
            tablaEditoriales.getSelectionModel().selectPrevious();

            if (tablaEditoriales.getSelectionModel().getSelectedIndex() >= 0) {
                tablaEditoriales.scrollTo(
                        tablaEditoriales.getSelectionModel().getSelectedIndex());
            }
        }
    }

    /**
     * Selecciona la siguiente editorial en la tabla.
     */
    @FXML
    private void handleSiguiente() {
        if (!tablaEditoriales.getItems().isEmpty()) {
            tablaEditoriales.getSelectionModel().selectNext();

            if (tablaEditoriales.getSelectionModel().getSelectedIndex() >= 0) {
                tablaEditoriales.scrollTo(
                        tablaEditoriales.getSelectionModel().getSelectedIndex());
            }
        }
    }

    /**
     * Selecciona la última editorial de la tabla.
     */
    @FXML
    private void handleUltimo() {
        if (!tablaEditoriales.getItems().isEmpty()) {
            tablaEditoriales.getSelectionModel().selectLast();
            tablaEditoriales.scrollTo(
                    tablaEditoriales.getItems().size() - 1);
        }
    }

    /**
     * Regresa al menú principal de la aplicación.
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
     * Limpia los campos del formulario.
     */
    private void limpiarFormulario() {
        txtNit.clear();
        txtNombre.clear();
        txtTelefono.clear();
        txtDireccion.clear();
    }

    /**
     * Activa los campos del formulario.
     */
    private void activarFormulario() {
        txtNit.setDisable(false);
        txtNombre.setDisable(false);
        txtTelefono.setDisable(false);
        txtDireccion.setDisable(false);
    }

    /**
     * Desactiva los campos del formulario.
     */
    private void desactivarFormulario() {
        txtNit.setDisable(true);
        txtNombre.setDisable(true);
        txtTelefono.setDisable(true);
        txtDireccion.setDisable(true);
    }

    /**
     * Activa los controles de navegación de la tabla.
     */
    private void activarNavegacion() {
        tablaEditoriales.setDisable(false);
        btnNuevo.setDisable(false);
        btnEditar.setDisable(false);
        btnPrimero.setDisable(false);
        btnAnterior.setDisable(false);
        btnSiguiente.setDisable(false);
        btnUltimo.setDisable(false);
        txtBuscar.setDisable(false);
    }

    /**
     * Desactiva los controles de navegación de la tabla.
     */
    private void desactivarNavegacion() {
        tablaEditoriales.setDisable(true);
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
     * @param mensaje mensaje que se mostrará.
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
     * @param mensaje mensaje que se mostrará.
     */
    private void mostrarAdvertencia(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
