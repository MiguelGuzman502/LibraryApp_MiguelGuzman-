package org.libreria.controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.libreria.DAO.LibroDAO;
import org.libreria.DAOImpl.LibroDAOImpl;
import org.libreria.exception.DaoException;
import org.libreria.model.Libro;
import org.libreria.system.Main;

/**
 * Controlador para mostrar y buscar los libros del inventario.
 * @author Miguel Guzman
 * @version 1.0.0
 */
public class InventarioController implements Initializable {
    @FXML
    private TableView<Libro> tablaInventario;
    @FXML
    private TableColumn<Libro, String> colIsbn;
    @FXML
    private TableColumn<Libro, String> colTitulo;
    @FXML
    private TableColumn<Libro, Double> colPrecio;
    @FXML
    private TableColumn<Libro, Integer> colStock;
    @FXML
    private TextField txtBuscar;

    private final LibroDAO libroDAO = new LibroDAOImpl();

    private final ObservableList<Libro> listaLibros =
            FXCollections.observableArrayList();

    private final FilteredList<Libro> librosFiltrados =
            new FilteredList<>(listaLibros, p -> true);

    /**
     * Inicializa la pantalla del inventario.
     * @param location ubicación utilizada para resolver las rutas.
     * @param resources recursos utilizados por la interfaz.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarTabla();
        tablaInventario.setItems(librosFiltrados);
        configurarTabla();
        configurarBusqueda();
    }

    /**
     * Configura las columnas de la tabla del inventario.
     */
    public void configurarTabla() {
        colIsbn.setCellValueFactory(
                new PropertyValueFactory<Libro, String>("isbn"));

        colTitulo.setCellValueFactory(
                new PropertyValueFactory<Libro, String>("titulo"));

        colPrecio.setCellValueFactory(
                new PropertyValueFactory<Libro, Double>("precio"));

        colStock.setCellValueFactory(
                new PropertyValueFactory<Libro, Integer>("stock"));
    }

    /**
     * Carga los libros desde la base de datos.
     */
    private void cargarTabla() {
        try {
            listaLibros.setAll(libroDAO.listarTodos());
        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Configura la búsqueda de libros.
     */
    private void configurarBusqueda() {
        txtBuscar.textProperty().addListener(
                (obs, oldValue, newValue) -> filtrarLibros());
    }

    /**
     * Filtra los libros según el texto ingresado.
     */
    private void filtrarLibros() {
        String busqueda =
                txtBuscar.getText().trim().toLowerCase();

        if (busqueda.isEmpty()) {
            librosFiltrados.setPredicate(p -> true);
        } else {
            librosFiltrados.setPredicate(libro ->
                    libro.getIsbn().toLowerCase().contains(busqueda)
                    || libro.getTitulo().toLowerCase().contains(busqueda)
                    || String.valueOf(libro.getPrecio()).contains(busqueda)
                    || String.valueOf(libro.getStock()).contains(busqueda));
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
                    "Error al volver al menú: " + e.getMessage());
        }
    }

    /**
     * Muestra un mensaje de error en pantalla.
     * @param mensaje mensaje que se mostrará.
     */
    private void mostrarError(String mensaje) {
        Alert alert =
                new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
