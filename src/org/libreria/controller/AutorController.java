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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
//import javax.swing.table.TableColumn;
import org.libreria.DAO.AutorDao;
import org.libreria.DAOImpl.AutorDAOImpl;
import org.libreria.exception.DaoException;
import org.libreria.exception.ValidacionException;
import org.libreria.model.Autor;
import org.libreria.system.Main;

/**
 * Controlador para gestionar los autores de la librería.
 * Permite registrar, editar, buscar y navegar entre los autores.
 * @author Miguel Guzman
 * @version 1.0.0
 */
public class AutorController implements Initializable {

    /** Campo para ingresar el nombre del autor. */
    @FXML
    private TextField txtNombre;

    /** Campo para ingresar el apellido del autor. */
    @FXML
    private TextField txtApellido;

    /** Campo para ingresar la nacionalidad del autor. */
    @FXML
    private TextField txtNacionalidad;

    /** Campo para ingresar la biografía del autor. */
    @FXML
    private TextArea txtBiografia;

    /** Etiqueta para mostrar mensajes al usuario. */
    @FXML
    private Label lblMensaje;

    /** Tabla que muestra los autores registrados. */
    @FXML
    private TableView<Autor> tablaAutores;

    /** Columna que muestra el identificador del autor. */
    @FXML
    private TableColumn colIdAutor;

    /** Columna que muestra el nombre del autor. */
    @FXML
    private TableColumn colNombreAutor;

    /** Columna que muestra el apellido del autor. */
    @FXML
    private TableColumn colApellidoAutor;

    /** Columna que muestra la nacionalidad del autor. */
    @FXML
    private TableColumn colNacionalidad;

    /** Columna que muestra la biografía del autor. */
    @FXML
    private TableColumn colBiografia;

    /** Botón para crear un nuevo autor. */
    @FXML
    private Button btnNuevo;

    /** Botón para editar un autor. */
    @FXML
    private Button btnEditar;

    /** Botón para seleccionar el primer autor. */
    @FXML
    private Button btnPrimero;

    /** Botón para seleccionar el autor anterior. */
    @FXML
    private Button btnAnterior;

    /** Botón para seleccionar el siguiente autor. */
    @FXML
    private Button btnSiguiente;

    /** Botón para seleccionar el último autor. */
    @FXML
    private Button btnUltimo;

    /** Campo utilizado para buscar autores. */
    @FXML
    private TextField txtBuscar;

    /** Indica si el formulario se encuentra en modo edición. */
    private boolean modoEdicion = false;

    /** Autor que se encuentra actualmente en edición. */
    private Autor enEdicion;

    /** Objeto utilizado para acceder a los datos de los autores. */
    private final AutorDao autorDao = new AutorDAOImpl();

    /** Lista con todos los autores registrados. */
    private final ObservableList<Autor> listaAutores = FXCollections.observableArrayList();

    /** Lista filtrada de autores para realizar búsquedas. */
    private final FilteredList<Autor> autoresFiltrados =
            new FilteredList<>(listaAutores, p -> true);

    /**
     * Inicializa el controlador y configura la tabla y búsqueda.
     * @param location Ubicación utilizada para resolver rutas.
     * @param resources Recursos utilizados por la interfaz.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarTabla();
        tablaAutores.setItems(autoresFiltrados);
        seleccionarFila();
        configurarTabla();
        configurarBusqueda();
    }

    /**
     * Configura las columnas de la tabla de autores.
     */
    public void configurarTabla() {
        colIdAutor.setCellValueFactory(
                new PropertyValueFactory<Autor, Integer>("idAutor"));
        colNombreAutor.setCellValueFactory(
                new PropertyValueFactory<Autor, String>("nombreAutor"));
        colApellidoAutor.setCellValueFactory(
                new PropertyValueFactory<Autor, String>("apellidoAutor"));
        colNacionalidad.setCellValueFactory(
                new PropertyValueFactory<Autor, String>("nacionalidad"));
        colBiografia.setCellValueFactory(
                new PropertyValueFactory<Autor, String>("biografia"));
    }

    /**
     * Carga los autores desde la base de datos.
     */
    private void cargarTabla() {
        try {
            listaAutores.setAll(autorDao.listarTodos());
        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Configura el campo de búsqueda de autores.
     */
    private void configurarBusqueda() {
        txtBuscar.textProperty().addListener(
                (obs, oldValue, newValue) -> filtrarAutores());
    }

    /**
     * Filtra los autores según el texto ingresado en la búsqueda.
     */
    private void filtrarAutores() {
        String busqueda = txtBuscar.getText().trim().toLowerCase();

        if (busqueda.isEmpty()) {
            autoresFiltrados.setPredicate(p -> true);
        } else {
            autoresFiltrados.setPredicate(autor ->
                    String.valueOf(autor.getIdAutor()).contains(busqueda)
                    || autor.getNombreAutor().toLowerCase().contains(busqueda)
                    || autor.getApellidoAutor().toLowerCase().contains(busqueda)
                    || autor.getNacionalidad().toLowerCase().contains(busqueda)
                    || autor.getBiografia().toLowerCase().contains(busqueda));
        }
    }

    /**
     * Permite seleccionar un autor desde la tabla y mostrar sus datos
     * en el formulario.
     */
    private void seleccionarFila() {
        tablaAutores.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        txtNombre.setText(newSelection.getNombreAutor());
                        txtApellido.setText(newSelection.getApellidoAutor());
                        txtNacionalidad.setText(newSelection.getNacionalidad());
                        txtBiografia.setText(newSelection.getBiografia());
                        desactivarFormulario();
                    }
                });
    }

    /**
     * Guarda un autor nuevo o actualiza uno existente.
     * @throws ValidacionException Si algún dato obligatorio no es válido.
     */
    @FXML
    private void handleGuardar() {
        try {
            ValidacionException.validarNoVacio(txtNombre.getText(), "nombre");
            ValidacionException.validarNoVacio(txtApellido.getText(), "apellido");
            ValidacionException.validarNoVacio(txtNacionalidad.getText(), "nacionalidad");

            Autor autor = new Autor(
                    modoEdicion ? enEdicion.getIdAutor() : 0,
                    txtNombre.getText().trim(),
                    txtApellido.getText().trim(),
                    txtNacionalidad.getText().trim(),
                    txtBiografia.getText().trim());

            boolean guardado;

            if (modoEdicion) {
                guardado = autorDao.actualizar(autor);
            } else {
                guardado = autorDao.crear(autor);
            }

            if (guardado) {
                lblMensaje.setText(modoEdicion
                        ? "Autor actualizado exitosamente."
                        : "Autor registrado exitosamente.");
                cargarTabla();
                limpiarFormulario();
                desactivarFormulario();
                activarNavegacion();
                modoEdicion = false;
            } else {
                mostrarError("No se pudo guardar el autor.");
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
     * Prepara el formulario para registrar un nuevo autor.
     */
    @FXML
    private void handleNuevo() {
        modoEdicion = false;
        enEdicion = null;
        limpiarFormulario();
        activarFormulario();
        desactivarNavegacion();
        tablaAutores.getSelectionModel().clearSelection();
        lblMensaje.setText("");
        txtNombre.requestFocus();
    }

    /**
     * Prepara el formulario para editar el autor seleccionado.
     */
    @FXML
    private void handleEditar() {
        Autor seleccion = tablaAutores.getSelectionModel().getSelectedItem();

        if (seleccion == null) {
            mostrarError("Seleccione un autor de la tabla para editar.");
            return;
        }

        modoEdicion = true;
        enEdicion = seleccion;
        activarFormulario();
        desactivarNavegacion();
        lblMensaje.setText("");
    }

    /**
     * Selecciona el primer autor de la tabla.
     */
    @FXML
    private void handlePrimero() {
        if (!tablaAutores.getItems().isEmpty()) {
            tablaAutores.getSelectionModel().selectFirst();
            tablaAutores.scrollTo(0);
        }
    }

    /**
     * Selecciona el autor anterior en la tabla.
     */
    @FXML
    private void handleAnterior() {
        if (!tablaAutores.getItems().isEmpty()) {
            tablaAutores.getSelectionModel().selectPrevious();

            if (tablaAutores.getSelectionModel().getSelectedIndex() >= 0) {
                tablaAutores.scrollTo(
                        tablaAutores.getSelectionModel().getSelectedIndex());
            }
        }
    }

    /**
     * Selecciona el siguiente autor en la tabla.
     */
    @FXML
    private void handleSiguiente() {
        if (!tablaAutores.getItems().isEmpty()) {
            tablaAutores.getSelectionModel().selectNext();

            if (tablaAutores.getSelectionModel().getSelectedIndex() >= 0) {
                tablaAutores.scrollTo(
                        tablaAutores.getSelectionModel().getSelectedIndex());
            }
        }
    }

    /**
     * Selecciona el último autor de la tabla.
     */
    @FXML
    private void handleUltimo() {
        if (!tablaAutores.getItems().isEmpty()) {
            tablaAutores.getSelectionModel().selectLast();
            tablaAutores.scrollTo(tablaAutores.getItems().size() - 1);
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
        txtNombre.clear();
        txtApellido.clear();
        txtNacionalidad.clear();
        txtBiografia.clear();
    }

    /**
     * Activa los campos del formulario.
     */
    private void activarFormulario() {
        txtNombre.setDisable(false);
        txtApellido.setDisable(false);
        txtNacionalidad.setDisable(false);
        txtBiografia.setDisable(false);
    }

    /**
     * Desactiva los campos del formulario.
     */
    private void desactivarFormulario() {
        txtNombre.setDisable(true);
        txtApellido.setDisable(true);
        txtNacionalidad.setDisable(true);
        txtBiografia.setDisable(true);
    }

    /**
     * Activa los controles de navegación y búsqueda.
     */
    private void activarNavegacion() {
        tablaAutores.setDisable(false);
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
        tablaAutores.setDisable(true);
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