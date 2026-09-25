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
import org.libreria.model.Usuario;
import org.libreria.system.Main;
import org.libreria.manager.SesionContext;

/**
 * Controlador del panel principal del administrador.
 * Permite navegar entre las diferentes secciones del sistema.
 * @author Miguel Guzman
 * @version 1.0.0
 */
public class AdminDashboradController implements Initializable {

    /** Etiqueta que muestra el nombre del usuario. */
    @FXML private Label lblBienvenida;

    /** Etiqueta que muestra el rol del usuario. */
    @FXML private Label lblRol;

    /** Botón para cerrar la sesión. */
    @FXML private Button btnCerrarSesion;

    /** Círculo utilizado como avatar del usuario. */
    @FXML private Circle avatarCircle;

    /** Botón para acceder a la gestión de usuarios. */
    @FXML private Button btnUsuario;

    /** Botón para acceder a la gestión de libros. */
    @FXML private Button btnLibro;

    /** Botón para acceder a la gestión de autores. */
    @FXML private Button btnAutor;

    /** Botón para acceder a la gestión de categorías. */
    @FXML private Button btnCategoria;

    /** Botón para acceder a la gestión de editoriales. */
    @FXML private Button btnEditorial;

    /** Botón para acceder a las ventas. */
    @FXML private Button btnVentas;

    /** Botón para acceder a la relación entre autores y libros. */
    @FXML private Button btnAutorLibro;

    /** Botón para acceder al detalle de las ventas. */
    @FXML private Button btnDetalleVenta;

    /** Tarjeta para crear un nuevo libro. */
    @FXML private VBox cardNuevoLibro;

    /** Tarjeta para agregar una venta. */
    @FXML private VBox cardAgregarVenta;

    /** Tarjeta para consultar el inventario. */
    @FXML private VBox cardVerInventario;

    /** Tarjeta para gestionar usuarios. */
    @FXML private VBox cardGestionarUsuarios;

    /** Tarjeta para acceder a los reportes. */
    @FXML private VBox cardReportes;

    /** Tarjeta para acceder a la configuración. */
    @FXML private VBox cardConfiguracion;

    /** Usuario que tiene la sesión actual. */
    private Usuario usuarioActual;

    /**
     * Inicializa el controlador y muestra los datos del usuario actual.
     * @param url URL utilizada para localizar el archivo FXML.
     * @param rb Recursos utilizados por la interfaz.
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
     * @param texto Texto que se desea modificar.
     * @return Texto con la primera letra en mayúscula.
     */
    private String capitalize(String texto) {
        if (texto == null || texto.isEmpty()) return "";
        return texto.substring(0, 1).toUpperCase()
                + texto.substring(1).toLowerCase();
    }

    /**
     * Cierra la sesión del usuario actual y regresa a la pantalla de inicio.
     * @param evento Evento generado por la acción del usuario.
     */
    @FXML
    public void cerrarSesion(ActionEvent evento) {
        SesionContext.getInstancia().cerrarSesion();
        navegar("/org/libreria/view/fxml/InicioSesionView.fxml");
    }

    /**
     * Abre la pantalla de usuarios.
     *
     * @param evento Evento generado por el botón.
     */
    @FXML
    public void irAUsuario(ActionEvent evento) {
        navegar("/org/libreria/view/fxml/UsuarioView.fxml");
    }

    /**
     * Abre la pantalla de libros.
     * @param evento Evento generado por el botón.
     */
    @FXML
    public void irALibro(ActionEvent evento) {
        navegar("/org/libreria/view/fxml/LibroView.fxml");
    }

    /**
     * Abre la pantalla de autores.
     * @param evento Evento generado por el botón.
     */
    @FXML
    public void irAAutor(ActionEvent evento) {
        navegar("/org/libreria/view/fxml/AutorView.fxml");
    }

    /**
     * Abre la pantalla de categorías.
     * @param evento Evento generado por el botón.
     */
    @FXML
    public void irACategoria(ActionEvent evento) {
        navegar("/org/libreria/view/fxml/CategoriaView.fxml");
    }

    /**
     * Abre la pantalla de editoriales.
     * @param evento Evento generado por el botón.
     */
    @FXML
    public void irAEditorial(ActionEvent evento) {
        navegar("/org/libreria/view/fxml/EditorialView.fxml");
    }

    /**
     * Abre la pantalla de ventas.
     * @param evento Evento generado por el botón.
     */
    @FXML
    public void irAVentas(ActionEvent evento) {
        navegar("/org/libreria/view/fxml/ListaVentasView.fxml");
    }

    /**
     * Abre la pantalla de relación entre autores y libros.
     * @param evento Evento generado por el botón.
     */
    @FXML
    public void irAAutorLibro(ActionEvent evento) {
        navegar("/org/libreria/view/fxml/AutorLibroView.fxml");
    }

    /**
     * Abre la pantalla de detalle de venta.
     * @param evento Evento generado por el botón.
     */
    @FXML
    public void irADetalleVenta(ActionEvent evento) {
        navegar("/org/ac/view/fxml/DetalleVentaView.fxml");
    }

    /**
     * Abre la pantalla de clientes.
     * @param evento Evento generado por el botón.
     */
    @FXML
    public void irAClientes(ActionEvent evento) {
        try {
            Main.cambiarEscena("/org/libreria/view/fxml/ClienteView.fxml");
        } catch (IOException e) {
            System.err.println("Error al cargar clientes: " + e.getMessage());
        }
    }

    /**
     * Abre el formulario para crear un nuevo libro.
     * @param evento Evento generado por el usuario.
     */
    @FXML
    public void nuevoLibro(MouseEvent evento) {
        navegar("/org/libreria/view/fxml/LibroFormView.fxml");
    }

    /**
     * Abre la pantalla para agregar una venta.
     * @param evento Evento generado por el usuario.
     */
    @FXML
    public void agregarVenta(MouseEvent evento) {
        navegar("/org/libreria/view/fxml/VentaView.fxml");
    }

    /**
     * Abre la pantalla del inventario.
     * @param evento Evento generado por el usuario.
     */
    @FXML
    public void verInventario(MouseEvent evento) {
        navegar("/org/libreria/view/fxml/InventarioView.fxml");
    }

    /**
     * Abre la pantalla para gestionar usuarios.
     * @param evento Evento generado por el usuario.
     */
    @FXML
    public void gestionarUsuarios(MouseEvent evento) {
        navegar("/org/libreria/view/fxml/GestionUsuariosView.fxml");
    }

    /**
     * Abre la pantalla de reportes.
     * @param evento Evento generado por el usuario.
     */
    @FXML
    public void reportes(MouseEvent evento) {
        navegar("/org/libreria/view/fxml/ReportesView.fxml");
    }

    /**
     * Abre la pantalla de configuración.
     * @param evento Evento generado por el usuario.
     */
    @FXML
    public void configuracion(MouseEvent evento) {
        navegar("/org/libreria/view/fxml/ConfiguracionView.fxml");
    }

    /**
     * Cambia a la pantalla indicada.
     * @param ruta Ruta del archivo FXML que se desea abrir.
     */
    private void navegar(String ruta) {
        try {
            Main.cambiarEscena(ruta);
        } catch (IOException | NullPointerException e) {
            Alert alerta = new Alert(
                    Alert.AlertType.INFORMATION,
                    "Esta sección estará disponible próximamente.",
                    ButtonType.OK
            );

            alerta.setTitle("En construcción");
            alerta.setHeaderText(null);
            alerta.showAndWait();
        }
    }

    /**
     * Inicia la información del usuario en el panel.
     * @param usuario Usuario que se mostrará en el panel.
     */
    public void iniciarUsuario(Usuario usuario) {
        this.usuarioActual = usuario;
        lblBienvenida.setText(usuario.getUsername());

        String iniciales = usuario.getUsername()
                .substring(0, Math.min(2, usuario.getUsername().length()))
                .toUpperCase();

        lblRol.setText(iniciales + " · " + capitalize(usuario.getRol()));
    }
}