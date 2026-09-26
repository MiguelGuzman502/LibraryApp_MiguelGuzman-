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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.libreria.DAO.UsuarioDAO;
import org.libreria.DAOImpl.UsuarioDAOImpl;
import org.libreria.exception.DaoException;
import org.libreria.exception.ValidacionException;
import org.libreria.manager.SesionContext;
import org.libreria.model.Usuario;
import org.libreria.system.Main;
import org.libreria.util.SecurityUtil;

/**
 * Controlador para iniciar sesión en el sistema.
 * @author Miguel Guzman
 * @version 1.0.0
 */
public class InicioSesionController implements Initializable {
    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnIniciarSesion;
    @FXML
    private Label lblMensaje;

    private UsuarioDAO usuarioDAO;

    /**
     * Inicializa la pantalla de inicio de sesión.
     * @param url ubicación utilizada para resolver las rutas.
     * @param rb recursos utilizados por la interfaz.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuarioDAO = new UsuarioDAOImpl();
        lblMensaje.setText("");

        // Detección de teclado: Enter en usuario o contraseña dispara el login,
        // igual que el botón INICIAR.
        txtUsuario.setOnAction(this::eventoInicioSesion);
        txtPassword.setOnAction(this::eventoInicioSesion);
    }

    /**
     * Realiza el inicio de sesión del usuario.
     * @param evento evento generado al iniciar sesión.
     */
    @FXML
    public void eventoInicioSesion(ActionEvent evento) {
        try {
            ValidacionException.validarNoVacio(
                    txtUsuario.getText(), "usuario");

            ValidacionException.validarNoVacio(
                    txtPassword.getText(), "contraseña");

            String usuario = txtUsuario.getText();
            String password = txtPassword.getText();
            String passwordHash = SecurityUtil.hashSHA256(password);

            Usuario usuarioIniciado =
                    usuarioDAO.iniciarSesion(usuario, passwordHash);

            if (usuarioIniciado != null) {
                mostrarAlerta(
                        Alert.AlertType.INFORMATION,
                        "Inicio correcto");

                abrirDashboard(usuarioIniciado);
            } else {
                mostrarAlerta(
                        Alert.AlertType.ERROR,
                        "Usuario o contraseña incorrectos");
            }

        } catch (ValidacionException e) {
            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    e.getMessage());

            lblMensaje.setText(e.getMessage());

        } catch (DaoException e) {
            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    e.getMessage());

            lblMensaje.setText("Error al iniciar sesión");
        }
    }

    /**
     * Abre la pantalla para registrar un nuevo usuario.
     * @param evento evento generado al seleccionar la opción de registro.
     */
    @FXML
    public void eventoRegistrarse(ActionEvent evento) {
        try {
            Main.cambiarEscena(
                    "/org/libreria/view/fxml/RegistrarUsuarioView.fxml");

        } catch (IOException e) {
            System.err.println(
                    "Error al cargar registro: " + e.getMessage());

            lblMensaje.setText("Error interno");
        }
    }

    /**
     * Abre el dashboard según el rol del usuario.
     * @param usuario usuario que inició sesión.
     */
    private void abrirDashboard(Usuario usuario) {
        SesionContext.getInstancia().setUsuarioActual(usuario);

        String rol = usuario.getRol();
        String rutaDashboard = "";

        switch (rol) {
            case "admin":
                rutaDashboard =
                        "/org/libreria/view/fxml/AdminDashboradView.fxml";
                break;

            case "cajero":
                rutaDashboard =
                        "/org/libreria/view/fxml/AdminDashboradView.fxml";
                break;

            case "empleado":
                rutaDashboard =
                        "/org/libreria/view/fxml/AdminDashboradView.fxml";
                break;

            default:
                throw new AssertionError();
        }

        // String rutaFXML = Main.rutaDashboardSegunRol();
        if (rutaDashboard.equals(
                "/org/libreria/view/fxml/InicioSesionView.fxml")) {

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Rol desconocido: " + usuario.getRol());

            SesionContext.getInstancia().cerrarSesion();
            return;
        }

        try {
            Main.cambiarEscena(rutaDashboard);

        } catch (IOException e) {
            System.err.println(
                    "Error al cargar la vista:"
                    + rutaDashboard
                    + e.getMessage());

            lblMensaje.setText("Error interno");
        }
    }

    /**
     * Muestra una alerta en pantalla.
     * @param tipo tipo de alerta que se mostrará.
     * @param mensaje mensaje que se mostrará.
     */
    private void mostrarAlerta(
            Alert.AlertType tipo,
            String mensaje) {

        Alert alerta = new Alert(
                tipo,
                mensaje,
                ButtonType.OK);

        alerta.showAndWait();
    }
}
