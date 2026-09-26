package org.libreria.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import org.libreria.manager.SesionContext;
import org.libreria.model.Usuario;
import org.libreria.system.Main;

/**
 * Controlador de la pantalla principal para los empleados.
 * @author Miguel Guzman
 * @version 1.0.0
 */
public class EmpleadoController implements Initializable {
    @FXML
    private Label lblBienvenida;
    @FXML
    private Label lblRol;
    @FXML
    private Button btnCerrarSesion;
    @FXML
    private Circle avatarCircle;
    @FXML
    private Button btnInventario;
    @FXML
    private Button btnLibro;
    @FXML
    private Button btnAutor;
    @FXML
    private Button btnCategoria;
    @FXML
    private Button btnEditorial;
    @FXML
    private Button btnClientes;
    @FXML
    private VBox cardVerInventario;
    @FXML
    private VBox cardNuevoLibro;
    @FXML
    private VBox cardNuevoAutor;
    @FXML
    private VBox cardNuevaCategoria;
    @FXML
    private VBox cardNuevaEditorial;
    @FXML
    private VBox cardNuevoCliente;

    private Usuario usuarioActual;

    /**
     * Inicializa la información del usuario que inició sesión.
     * @param url ubicación utilizada para resolver las rutas.
     * @param rb recursos utilizados por la interfaz.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuarioActual = SesionContext.getInstancia().getUsuarioActual();

        if (usuarioActual != null) {
            lblBienvenida.setText(usuarioActual.getUsername());

            String iniciales = usuarioActual.getUsername()
                    .substring(0, Math.min(2, usuarioActual.getUsername().length()))
                    .toUpperCase();

            lblRol.setText(iniciales + " · " + capitalize(usuarioActual.getRol()));
        } else {
            lblBienvenida.setText("Invitado");
            lblRol.setText("?? · Sin sesión");
        }
    }

    /**
     * Convierte la primera letra de un texto a mayúscula.
     * @param texto texto que se desea convertir.
     * @return texto con la primera letra en mayúscula.
     */
    private String capitalize(String texto) {
        if (texto == null || texto.isEmpty()) {
            return "";
        }

        return texto.substring(0, 1).toUpperCase()
                + texto.substring(1).toLowerCase();
    }

    /**
     * Cierra la sesión del usuario actual y regresa al inicio de sesión.
     * @param evento evento generado por la interfaz.
     */
    @FXML
    public void cerrarSesion(ActionEvent evento) {
        SesionContext.getInstancia().cerrarSesion();
        navegar("/org/libreria/view/fxml/InicioSesionView.fxml");
    }

    /**
     * Abre la pantalla de inventario.
     * @param evento evento generado por la interfaz.
     */
    @FXML
    public void irAInventario(ActionEvent evento) {
        navegar("/org/libreria/view/fxml/InventarioView.fxml");
    }

    /**
     * Abre la pantalla de libros.
     * @param evento evento generado por la interfaz.
     */
    @FXML
    public void irALibro(ActionEvent evento) {
        navegar("/org/libreria/view/fxml/LibroView.fxml");
    }

    /**
     * Abre la pantalla de autores.
     * @param evento evento generado por la interfaz.
     */
    @FXML
    public void irAAutor(ActionEvent evento) {
        navegar("/org/libreria/view/fxml/AutorView.fxml");
    }

    /**
     * Abre la pantalla de categorías.
     * @param evento evento generado por la interfaz.
     */
    @FXML
    public void irACategoria(ActionEvent evento) {
        navegar("/org/libreria/view/fxml/CategoriaView.fxml");
    }

    /**
     * Abre la pantalla de editoriales.
     * @param evento evento generado por la interfaz.
     */
    @FXML
    public void irAEditorial(ActionEvent evento) {
        navegar("/org/libreria/view/fxml/EditorialView.fxml");
    }

    /**
     * Abre la pantalla de clientes.
     * @param evento evento generado por la interfaz.
     */
    @FXML
    public void irAClientes(ActionEvent evento) {
        navegar("/org/libreria/view/fxml/ClienteView.fxml");
    }

    /**
     * Permite acceder al inventario desde la tarjeta correspondiente.
     * @param evento evento generado por la interfaz.
     */
    @FXML
    public void verInventario(MouseEvent evento) {
        navegar("/org/libreria/view/fxml/InventarioView.fxml");
    }

    /**
     * Permite acceder a la pantalla para registrar un libro.
     * @param evento evento generado por la interfaz.
     */
    @FXML
    public void nuevoLibro(MouseEvent evento) {
        navegar("/org/libreria/view/fxml/LibroView.fxml");
    }

    /**
     * Permite acceder a la pantalla para registrar un autor.
     * @param evento evento generado por la interfaz.
     */
    @FXML
    public void nuevoAutor(MouseEvent evento) {
        navegar("/org/libreria/view/fxml/AutorView.fxml");
    }

    /**
     * Permite acceder a la pantalla para registrar una categoría.
     * @param evento evento generado por la interfaz.
     */
    @FXML
    public void nuevaCategoria(MouseEvent evento) {
        navegar("/org/libreria/view/fxml/CategoriaView.fxml");
    }

    /**
     * Permite acceder a la pantalla para registrar una editorial.
     * @param evento evento generado por la interfaz.
     */
    @FXML
    public void nuevaEditorial(MouseEvent evento) {
        navegar("/org/libreria/view/fxml/EditorialView.fxml");
    }

    /**
     * Permite acceder a la pantalla para registrar un cliente.
     * @param evento evento generado por la interfaz.
     */
    @FXML
    public void nuevoCliente(MouseEvent evento) {
        navegar("/org/libreria/view/fxml/ClienteView.fxml");
    }

    /**
     * Cambia la escena de la aplicación a la ruta indicada.
     * @param ruta ruta de la vista que se desea abrir.
     */
    private void navegar(String ruta) {
        try {
            Main.cambiarEscena(ruta);
        } catch (IOException | NullPointerException e) {
            Alert alerta = new Alert(
                    Alert.AlertType.INFORMATION,
                    "Esta sección estará disponible próximamente.",
                    ButtonType.OK);

            alerta.setTitle("En construcción");
            alerta.setHeaderText(null);
            alerta.showAndWait();
        }
    }
}
