package org.libreria.system;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;

import org.libreria.model.Usuario;

/**
 * Clase principal de la aplicación de librería.
 * Se encarga de iniciar la aplicación JavaFX, administrar
 * el escenario principal y cambiar entre las diferentes escenas
 * de la aplicación.
 * @author Miguel Guzman
 * @version 1.0.0
 */
public class Main extends Application {

    private static Stage escenarioPrincipal;

    private static final Logger log =
            Logger.getLogger(Main.class.getName());

    /**
     * Cambia la escena actual de la aplicación utilizando
     * un archivo FXML como vista.
     * @param rutaFXML Ruta del archivo FXML que se desea cargar.
     * @throws IOException si ocurre un error al cargar el archivo FXML.
     */
    public static void cambiarEscena(String rutaFXML) throws IOException {
        log.log(Level.INFO, "Se cambio de escena a: {0}", rutaFXML);

        Parent raiz = FXMLLoader.load(
                Main.class.getResource(rutaFXML));

        Scene escena = new Scene(raiz);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        escenarioPrincipal.centerOnScreen();
        escenarioPrincipal.show();
    }

    /**
     * Obtiene la ruta del dashboard correspondiente al rol
     * del usuario que tiene una sesión activa.
     * Si no existe un usuario activo o el rol no es reconocido,
     * devuelve la ruta correspondiente a la pantalla de inicio de sesión.
     * @return Ruta del archivo FXML correspondiente al dashboard
     * según el rol del usuario.
     */
    public static String rutaDashboardSegunRol() {

   
        Usuario usuario =
                SesionContext.getInstancia().getUsuarioActual();

        if (usuario == null || usuario.getRol() == null) {
            return "/org/libreria/view/fxml/InicioSesionView.fxml";
        }

        switch (usuario.getRol().toLowerCase()) {
            case "admin":
                return "/org/libreria/view/fxml/AdminDashboradView.fxml";

            case "empleado":
                return "/org/libreria/view/fxml/EmpleadoView.fxml";

            case "cajero":
                return "/org/libreria/view/fxml/CajeroView.fxml";

            default:
                return "/org/libreria/view/fxml/InicioSesionView.fxml";
        }


        return "/org/libreria/view/fxml/InicioSesionView.fxml";
    }

    /**
     * Método principal que inicia la ejecución de la aplicación.
     * @param args Argumentos proporcionados al iniciar el programa.
     */
    public static void main(String[] args) {
        log.info("Se inicio el programa");

        // launch(args);
    }

    /**
     * Inicia la aplicación JavaFX y muestra inicialmente
     * la pantalla de inicio de sesión.
     * @param escenarioPrincipal Escenario principal de la aplicación.
     * @throws Exception si ocurre un error durante el inicio de la aplicación.
     */
    @Override
    public void start(Stage escenarioPrincipal) throws Exception {

        Main.escenarioPrincipal = escenarioPrincipal;

        cambiarEscena(
                "/org/libreria/view/fxml/InicioSesionView.fxml");
    }
}
