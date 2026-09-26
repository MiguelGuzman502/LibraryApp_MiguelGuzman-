
package org.libreria.controller;

import java.awt.Button;
import java.awt.TextField;
import java.lang.classfile.Label;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.table.TableColumn;
import javax.swing.text.TableView;
import org.libreria.DAO.UsuarioDAO;
import org.libreria.DAOImpl.UsuarioDAOImpl;
import org.libreria.exception.DaoException;
import org.libreria.exception.ValidacionException;
import org.libreria.manager.SesionContext;
import org.libreria.model.Usuario;
import org.libreria.system.Main;
import org.libreria.util.SecurityUtil;

/**
 * Controlador para gestionar los usuarios del sistema.
 * @author Miguel Guzman
 * @version 1.0.0
 */
public class UsuarioController implements Initializable {

    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private ComboBox<String> cmbRol;
    @FXML
    private CheckBox chkActivo;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label lblMensaje;
    @FXML
    private TableView<Usuario> tablaUsuarios;
    @FXML
    private TableColumn colId;
    @FXML
    private TableColumn colUsername;
    @FXML
    private TableColumn colEmail;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colApellido;
    @FXML
    private TableColumn colRol;
    @FXML
    private TableColumn colActivo;
    @FXML
    private TableColumn colFecha;
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
    private Button btnCambiarPassword;
    @FXML
    private Button btnDesactivar;
    @FXML
    private Button btnEliminar;
    @FXML
    private TextField txtBuscar;

    private boolean modoEdicion = false;
    private Usuario enEdicion;
    private final UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
    private final ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();
    private final FilteredList<Usuario> usuariosFiltrados = new FilteredList<>(listaUsuarios, p -> true);

    /**
     * Inicializa la pantalla de usuarios y configura sus componentes.
     * @param location ubicación utilizada para resolver las rutas.
     * @param resources recursos utilizados por la interfaz.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbRol.setItems(FXCollections.observableArrayList("admin", "empleado", "cajero"));
        cargarTabla();
        tablaUsuarios.setItems(usuariosFiltrados);
        seleccionarFila();
        configurarTabla();
        configurarBusqueda();
        desactivarFormulario();
    }

    /**
     * Configura las columnas de la tabla de usuarios.
     */
    public void configurarTabla() {
        colId.setCellValueFactory(new PropertyValueFactory<Usuario, Integer>("id"));
        colUsername.setCellValueFactory(new PropertyValueFactory<Usuario, String>("username"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Usuario, String>("email"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Usuario, String>("firstName"));
        colApellido.setCellValueFactory(new PropertyValueFactory<Usuario, String>("lastName"));
        colRol.setCellValueFactory(new PropertyValueFactory<Usuario, String>("rol"));
        colActivo.setCellValueFactory(new PropertyValueFactory<Usuario, Boolean>("activo"));
        colFecha.setCellValueFactory(new PropertyValueFactory<Usuario, Timestamp>("fechaCreacion"));
    }

    /**
     * Carga los usuarios desde la base de datos.
     */
    private void cargarTabla() {
        try {
            listaUsuarios.setAll(usuarioDAO.listarTodosUsuarios());
        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Configura el campo utilizado para buscar usuarios.
     */
    private void configurarBusqueda() {
        txtBuscar.textProperty().addListener((obs, oldValue, newValue) -> filtrarUsuarios());
    }

    /**
     * Filtra los usuarios de acuerdo con el texto ingresado.
     */
    private void filtrarUsuarios() {
        String busqueda = txtBuscar.getText().trim().toLowerCase();

        if (busqueda.isEmpty()) {
            usuariosFiltrados.setPredicate(p -> true);
        } else {
            usuariosFiltrados.setPredicate(usuario ->
                    String.valueOf(usuario.getId()).contains(busqueda)
                    || usuario.getUsername().toLowerCase().contains(busqueda)
                    || (usuario.getEmail() != null && usuario.getEmail().toLowerCase().contains(busqueda))
                    || usuario.getRol().toLowerCase().contains(busqueda));
        }
    }

    /**
     * Configura la selección de usuarios en la tabla.
     */
    private void seleccionarFila() {
        tablaUsuarios.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        mostrarEnFormulario(newSelection);
                        desactivarFormulario();
                    }
                });
    }

    /**
     * Muestra los datos del usuario seleccionado en el formulario.
     * @param usuario usuario que se mostrará.
     */
    private void mostrarEnFormulario(Usuario usuario) {
        txtUsername.setText(usuario.getUsername());
        txtEmail.setText(usuario.getEmail());
        txtNombre.setText(usuario.getFirstName());
        txtApellido.setText(usuario.getLastName());
        cmbRol.setValue(usuario.getRol());
        chkActivo.setSelected(usuario.isActivo());
        txtPassword.clear();
    }

    /**
     * Guarda un usuario nuevo o actualiza uno existente.
     */
    @FXML
    private void handleGuardar() {
        try {
            ValidacionException.validarNoVacio(txtUsername.getText(), "username");
            ValidacionException.validarNoVacio(txtEmail.getText(), "correo electrónico");
            ValidacionException.validarFormatoEmail(
                    txtEmail.getText(),
                    "El correo electrónico no es válido."
            );
            ValidacionException.validarNoNulo(
                    cmbRol.getValue(),
                    "Debe seleccionar un rol."
            );

            Usuario usuario = new Usuario();
            usuario.setId(modoEdicion ? enEdicion.getId() : 0);
            usuario.setUsername(txtUsername.getText().trim());
            usuario.setEmail(txtEmail.getText().trim());
            usuario.setFirstName(txtNombre.getText().trim());
            usuario.setLastName(txtApellido.getText().trim());
            usuario.setRol(cmbRol.getValue());
            usuario.setActivo(chkActivo.isSelected());

            boolean guardado;

            if (modoEdicion) {
                guardado = usuarioDAO.actualizarUsuario(usuario);
            } else {
                ValidacionException.validarNoVacio(txtPassword.getText(), "contraseña");
                ValidacionException.validarLongitudMinima(
                        txtPassword.getText(),
                        6,
                        "La contraseña debe tener al menos 6 caracteres."
                );

                usuario.setPasswordHash(SecurityUtil.hashSHA256(txtPassword.getText()));
                guardado = usuarioDAO.crearUsuario(usuario);
            }

            if (guardado) {
                lblMensaje.setText(modoEdicion
                        ? "Usuario actualizado exitosamente."
                        : "Usuario registrado exitosamente.");

                cargarTabla();
                limpiarFormulario();
                desactivarFormulario();
                activarNavegacion();
                modoEdicion = false;
                enEdicion = null;
            } else {
                mostrarError("No se pudo guardar el usuario.");
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
     * Prepara el formulario para registrar un nuevo usuario.
     */
    @FXML
    private void handleNuevo() {
        modoEdicion = false;
        enEdicion = null;
        limpiarFormulario();
        chkActivo.setSelected(true);
        cmbRol.setValue("empleado");
        activarFormulario();
        desactivarNavegacion();
        tablaUsuarios.getSelectionModel().clearSelection();
        lblMensaje.setText("");
        txtUsername.requestFocus();
    }

    /**
     * Prepara el formulario para editar el usuario seleccionado.
     */
    @FXML
    private void handleEditar() {
        Usuario seleccion = tablaUsuarios.getSelectionModel().getSelectedItem();

        if (seleccion == null) {
            mostrarError("Seleccione un usuario de la tabla para editar.");
            return;
        }

        modoEdicion = true;
        enEdicion = seleccion;
        mostrarEnFormulario(seleccion);
        activarFormulario();
        desactivarNavegacion();
        lblMensaje.setText("");
    }

    /**
     * Permite cambiar la contraseña del usuario seleccionado.
     */
    @FXML
    private void handleCambiarPassword() {
        Usuario seleccion = tablaUsuarios.getSelectionModel().getSelectedItem();

        if (seleccion == null) {
            mostrarError("Seleccione un usuario para cambiar la contraseña.");
            return;
        }

        TextInputDialog dialogo = new TextInputDialog();
        dialogo.setTitle("Cambiar Contraseña");
        dialogo.setHeaderText("Nueva contraseña para: " + seleccion.getUsername());
        dialogo.setContentText("Contraseña:");

        dialogo.showAndWait().ifPresent(password -> {
            try {
                ValidacionException.validarNoVacio(password, "contraseña");
                ValidacionException.validarLongitudMinima(
                        password,
                        6,
                        "La contraseña debe tener al menos 6 caracteres."
                );

                String hash = SecurityUtil.hashSHA256(password);

                if (usuarioDAO.cambiarPassword(seleccion.getId(), hash)) {
                    lblMensaje.setText("Contraseña actualizada exitosamente.");
                } else {
                    mostrarError("No se pudo cambiar la contraseña.");
                }

            } catch (ValidacionException e) {
                mostrarAdvertencia(e.getMessage());

            } catch (DaoException e) {
                mostrarError(e.getMessage());
            }
        });
    }

    /**
     * Desactiva el usuario seleccionado.
     */
    @FXML
    private void handleDesactivar() {
        Usuario seleccion = tablaUsuarios.getSelectionModel().getSelectedItem();

        if (seleccion == null) {
            mostrarError("Seleccione un usuario para desactivar.");
            return;
        }

        if (esUsuarioActual(seleccion)) {
            mostrarError("No puede desactivar su propio usuario.");
            return;
        }

        if (!confirmar(
                "Desactivar usuario",
                "¿Desea desactivar al usuario " + seleccion.getUsername() + "?"
        )) {
            return;
        }

        try {
            if (usuarioDAO.desactivarUsuario(seleccion.getId())) {
                lblMensaje.setText("Usuario desactivado exitosamente.");
                cargarTabla();
            } else {
                mostrarError("No se pudo desactivar el usuario.");
            }

        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Elimina el usuario seleccionado después de confirmar la acción.
     */
    @FXML
    private void handleEliminar() {
        Usuario seleccion = tablaUsuarios.getSelectionModel().getSelectedItem();

        if (seleccion == null) {
            mostrarError("Seleccione un usuario para eliminar.");
            return;
        }

        if (esUsuarioActual(seleccion)) {
            mostrarError("No puede eliminar su propio usuario.");
            return;
        }

        if (!confirmar(
                "Eliminar usuario",
                "¿Desea eliminar definitivamente al usuario "
                + seleccion.getUsername() + "?"
        )) {
            return;
        }

        try {
            if (usuarioDAO.eliminarUsuario(seleccion.getId())) {
                lblMensaje.setText("Usuario eliminado exitosamente.");
                cargarTabla();
            } else {
                mostrarError("No se pudo eliminar el usuario.");
            }

        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Comprueba si el usuario seleccionado corresponde al usuario que inició sesión.
     * @param usuario usuario que se desea comprobar.
     * @return true si es el usuario actual; false en caso contrario.
     */
    private boolean esUsuarioActual(Usuario usuario) {
        Usuario actual = SesionContext.getInstancia().getUsuarioActual();
        return actual != null && actual.getId() == usuario.getId();
    }

    /**
     * Selecciona el primer usuario de la tabla.
     */
    @FXML
    private void handlePrimero() {
        if (!tablaUsuarios.getItems().isEmpty()) {
            tablaUsuarios.getSelectionModel().selectFirst();
            tablaUsuarios.scrollTo(0);
        }
    }

    /**
     * Selecciona el usuario anterior en la tabla.
     */
    @FXML
    private void handleAnterior() {
        if (!tablaUsuarios.getItems().isEmpty()) {
            tablaUsuarios.getSelectionModel().selectPrevious();

            if (tablaUsuarios.getSelectionModel().getSelectedIndex() >= 0) {
                tablaUsuarios.scrollTo(
                        tablaUsuarios.getSelectionModel().getSelectedIndex()
                );
            }
        }
    }

    /**
     * Selecciona el siguiente usuario en la tabla.
     */
    @FXML
    private void handleSiguiente() {
        if (!tablaUsuarios.getItems().isEmpty()) {
            tablaUsuarios.getSelectionModel().selectNext();

            if (tablaUsuarios.getSelectionModel().getSelectedIndex() >= 0) {
                tablaUsuarios.scrollTo(
                        tablaUsuarios.getSelectionModel().getSelectedIndex()
                );
            }
        }
    }

    /**
     * Selecciona el último usuario de la tabla.
     */
    @FXML
    private void handleUltimo() {
        if (!tablaUsuarios.getItems().isEmpty()) {
            tablaUsuarios.getSelectionModel().selectLast();
            tablaUsuarios.scrollTo(tablaUsuarios.getItems().size() - 1);
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
        txtUsername.clear();
        txtEmail.clear();
        txtNombre.clear();
        txtApellido.clear();
        cmbRol.setValue(null);
        chkActivo.setSelected(false);
        txtPassword.clear();
    }

    /**
     * Activa los campos del formulario para poder editarlos.
     */
    private void activarFormulario() {
        txtUsername.setDisable(false);
        txtEmail.setDisable(false);
        txtNombre.setDisable(false);
        txtApellido.setDisable(false);
        cmbRol.setDisable(false);
        chkActivo.setDisable(false);
        txtPassword.setDisable(modoEdicion);
    }

    /**
     * Desactiva los campos del formulario.
     */
    private void desactivarFormulario() {
        txtUsername.setDisable(true);
        txtEmail.setDisable(true);
        txtNombre.setDisable(true);
        txtApellido.setDisable(true);
        cmbRol.setDisable(true);
        chkActivo.setDisable(true);
        txtPassword.setDisable(true);
    }

    /**
     * Activa los botones y controles de navegación.
     */
    private void activarNavegacion() {
        tablaUsuarios.setDisable(false);
        btnNuevo.setDisable(false);
        btnEditar.setDisable(false);
        btnPrimero.setDisable(false);
        btnAnterior.setDisable(false);
        btnSiguiente.setDisable(false);
        btnUltimo.setDisable(false);
        btnCambiarPassword.setDisable(false);
        btnDesactivar.setDisable(false);
        btnEliminar.setDisable(false);
        txtBuscar.setDisable(false);
    }

    /**
     * Desactiva los botones y controles de navegación.
     */
    private void desactivarNavegacion() {
        tablaUsuarios.setDisable(true);
        btnNuevo.setDisable(true);
        btnEditar.setDisable(true);
        btnPrimero.setDisable(true);
        btnAnterior.setDisable(true);
        btnSiguiente.setDisable(true);
        btnUltimo.setDisable(true);
        btnCambiarPassword.setDisable(true);
        btnDesactivar.setDisable(true);
        btnEliminar.setDisable(true);
        txtBuscar.setDisable(true);
    }

    /**
     * Muestra un cuadro de confirmación para una acción.
     * @param titulo título del cuadro de confirmación.
     * @param mensaje mensaje que se mostrará.
     * @return true si se confirma la acción; false en caso contrario.
     */
    private boolean confirmar(String titulo, String mensaje) {
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                mensaje,
                ButtonType.YES,
                ButtonType.NO
        );
        alert.setTitle(titulo);
        alert.setHeaderText(null);

        return alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES;
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
