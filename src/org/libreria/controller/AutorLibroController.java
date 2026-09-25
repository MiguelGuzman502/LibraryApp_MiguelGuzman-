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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.libreria.DAO.AutorDao;
import org.libreria.DAO.AutorLibroDAO;
import org.libreria.DAO.LibroDAO;
import org.libreria.DAOImpl.AutorDAOImpl;
import org.libreria.DAOImpl.AutorLibroDAOImpl;
import org.libreria.DAOImpl.LibroDAOImpl;
import org.libreria.exception.DaoException;
import org.libreria.exception.ValidacionException;
import org.libreria.model.Autor;
import org.libreria.model.AutorLibro;
import org.libreria.model.Libro;
import org.libreria.system.Main;

/**
 * Controlador para gestionar las relaciones entre autores y libros.
 * @author Miguel Guzman
 * @version 1.0.0
 */
public class AutorLibroController implements Initializable {

    @FXML
    private ComboBox<Autor> cmbAutor;

    @FXML
    private ComboBox<Libro> cmbLibro;

    @FXML
    private Label lblMensaje;

    @FXML
    private TableView<AutorLibro> tablaAutoresLibro;

    @FXML
    private TableColumn colIdAutorLibro;

    @FXML
    private TableColumn colIdAutor;

    @FXML
    private TableColumn colIsbn;

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
    private AutorLibro enEdicion;

    private final AutorLibroDAO autorLibroDAO = new AutorLibroDAOImpl();
    private final AutorDao autorDAO = new AutorDAOImpl();
    private final LibroDAO libroDAO = new LibroDAOImpl();

    private final ObservableList<AutorLibro> listaAutoresLibro
            = FXCollections.observableArrayList();

    private final FilteredList<AutorLibro> autoresLibroFiltrados
            = new FilteredList<>(listaAutoresLibro, p -> true);

    /**
     * Inicializa el controlador y configura la tabla, combos y búsqueda.
     * @param location Ubicación utilizada para resolver recursos.
     * @param resources Recursos utilizados por la interfaz.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarTabla();
        cargarCombos();
        tablaAutoresLibro.setItems(autoresLibroFiltrados);
        seleccionarFila();
        configurarTabla();
        configurarBusqueda();
    }

    /**
     * Configura las columnas de la tabla.
     */
    public void configurarTabla() {
        colIdAutorLibro.setCellValueFactory(
                new PropertyValueFactory<AutorLibro, Integer>("idAutorLibro"));

        colIdAutor.setCellValueFactory(
                new PropertyValueFactory<AutorLibro, Integer>("idAutor"));

        colIsbn.setCellValueFactory(
                new PropertyValueFactory<AutorLibro, String>("isbn"));
    }

    /**
     * Carga las relaciones entre autores y libros en la tabla.
     * @throws DaoException Si ocurre un error al consultar los datos.
     */
    private void cargarTabla() {
        try {
            listaAutoresLibro.setAll(autorLibroDAO.listarTodos());
        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Carga los autores y libros disponibles en los combos.
     * @throws DaoException Si ocurre un error al cargar los datos.
     */
    private void cargarCombos() {
        try {
            cmbAutor.setItems(
                    FXCollections.observableArrayList(autorDAO.listarTodos()));

            cmbLibro.setItems(
                    FXCollections.observableArrayList(libroDAO.listarTodos()));
        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Configura el campo de búsqueda.
     */
    private void configurarBusqueda() {
        txtBuscar.textProperty().addListener(
                (obs, oldValue, newValue) -> filtrarAutoresLibro());
    }

    /**
     * Filtra las relaciones de autores y libros según el texto ingresado.
     */
    private void filtrarAutoresLibro() {
        String busqueda = txtBuscar.getText().trim().toLowerCase();

        if (busqueda.isEmpty()) {
            autoresLibroFiltrados.setPredicate(p -> true);
        } else {
            autoresLibroFiltrados.setPredicate(autorLibro
                    -> String.valueOf(autorLibro.getIdAutorLibro()).contains(busqueda)
                    || String.valueOf(autorLibro.getIdAutor()).contains(busqueda)
                    || autorLibro.getIsbn().toLowerCase().contains(busqueda));
        }
    }

    /**
     * Detecta la fila seleccionada y muestra sus datos en los combos.
     */
    private void seleccionarFila() {
        tablaAutoresLibro.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {

                    if (newSelection != null) {

                        cmbAutor.setValue(null);

                        for (Autor autor : cmbAutor.getItems()) {
                            if (autor.getIdAutor() == newSelection.getIdAutor()) {
                                cmbAutor.setValue(autor);
                                break;
                            }
                        }

                        cmbLibro.setValue(null);

                        for (Libro libro : cmbLibro.getItems()) {
                            if (libro.getIsbn().equals(newSelection.getIsbn())) {
                                cmbLibro.setValue(libro);
                                break;
                            }
                        }

                        desactivarFormulario();
                    }
                });
    }

    /**
     * Guarda una nueva relación o actualiza una existente.
     */
    @FXML
    private void handleGuardar() {
        try {
            ValidacionException.validarNoNulo(
                    cmbAutor.getValue(),
                    "Seleccione un autor.");

            ValidacionException.validarNoNulo(
                    cmbLibro.getValue(),
                    "Seleccione un libro.");

            AutorLibro autorLibro = new AutorLibro(
                    modoEdicion ? enEdicion.getIdAutorLibro() : 0,
                    cmbAutor.getValue().getIdAutor(),
                    cmbLibro.getValue().getIsbn());

            boolean guardado;

            if (modoEdicion) {
                guardado = autorLibroDAO.actualizar(autorLibro);
            } else {
                guardado = autorLibroDAO.crear(autorLibro);
            }

            if (guardado) {
                lblMensaje.setText(
                        modoEdicion
                                ? "Relación autor-libro actualizada exitosamente."
                                : "Relación autor-libro registrada exitosamente.");

                cargarTabla();
                limpiarFormulario();
                desactivarFormulario();
                activarNavegacion();
                modoEdicion = false;

            } else {
                mostrarError("No se pudo guardar la relación autor-libro.");
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
        enEdicion = null;
        lblMensaje.setText("");
    }

    /**
     * Prepara el formulario para registrar una nueva relación.
     */
    @FXML
    private void handleNuevo() {
        modoEdicion = false;
        enEdicion = null;
        limpiarFormulario();
        activarFormulario();
        desactivarNavegacion();
        tablaAutoresLibro.getSelectionModel().clearSelection();
        lblMensaje.setText("");
        cmbAutor.requestFocus();
    }

    /**
     * Prepara el formulario para editar la relación seleccionada.
     */
    @FXML
    private void handleEditar() {
        AutorLibro seleccion
                = tablaAutoresLibro.getSelectionModel().getSelectedItem();

        if (seleccion == null) {
            mostrarError(
                    "Seleccione una relación autor-libro de la tabla para editar.");
            return;
        }

        modoEdicion = true;
        enEdicion = seleccion;
        activarFormulario();
        desactivarNavegacion();
        lblMensaje.setText("");
    }

    /**
     * Selecciona el primer registro de la tabla.
     */
    @FXML
    private void handlePrimero() {
        if (!tablaAutoresLibro.getItems().isEmpty()) {
            tablaAutoresLibro.getSelectionModel().selectFirst();
            tablaAutoresLibro.scrollTo(0);
        }
    }

    /**
     * Selecciona el registro anterior de la tabla.
     */
    @FXML
    private void handleAnterior() {
        if (!tablaAutoresLibro.getItems().isEmpty()) {
            tablaAutoresLibro.getSelectionModel().selectPrevious();

            if (tablaAutoresLibro.getSelectionModel().getSelectedIndex() >= 0) {
                tablaAutoresLibro.scrollTo(
                        tablaAutoresLibro.getSelectionModel().getSelectedIndex());
            }
        }
    }

    /**
     * Selecciona el siguiente registro de la tabla.
     */
    @FXML
    private void handleSiguiente() {
        if (!tablaAutoresLibro.getItems().isEmpty()) {
            tablaAutoresLibro.getSelectionModel().selectNext();

            if (tablaAutoresLibro.getSelectionModel().getSelectedIndex() >= 0) {
                tablaAutoresLibro.scrollTo(
                        tablaAutoresLibro.getSelectionModel().getSelectedIndex());
            }
        }
    }

    /**
     * Selecciona el último registro de la tabla.
     */
    @FXML
    private void handleUltimo() {
        if (!tablaAutoresLibro.getItems().isEmpty()) {
            tablaAutoresLibro.getSelectionModel().selectLast();
            tablaAutoresLibro.scrollTo(
                    tablaAutoresLibro.getItems().size() - 1);
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
     * Limpia los valores del formulario.
     */
    private void limpiarFormulario() {
        cmbAutor.setValue(null);
        cmbLibro.setValue(null);
    }

    /**
     * Activa los controles del formulario.
     */
    private void activarFormulario() {
        cmbAutor.setDisable(false);
        cmbLibro.setDisable(false);
    }

    /**
     * Desactiva los controles del formulario.
     */
    private void desactivarFormulario() {
        cmbAutor.setDisable(true);
        cmbLibro.setDisable(true);
    }

    /**
     * Activa los controles de navegación y búsqueda.
     */
    private void activarNavegacion() {
        tablaAutoresLibro.setDisable(false);
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
        tablaAutoresLibro.setDisable(true);
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
     * @param mensaje Texto que se mostrará en la alerta.
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
     * @param mensaje Texto que se mostrará en la alerta.
     */
    private void mostrarAdvertencia(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}