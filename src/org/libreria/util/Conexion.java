
package org.libreria.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Gestiona las conexiones a la base de datos MySQL del sistema de librería.
 * Utiliza el patrón Singleton para mantener una única instancia de esta clase
 * y obtiene los datos de conexión desde el archivo {@code db.properties}.
 * @author Miguel Guzman
 * @version 1.0.0
 */
public class Conexion {

    private static Conexion instancia;

    private static final String CONFIG_FILE = "/db.properties";

    private final String url;
    private final String user;
    private final String password;

    /**
     * Constructor privado que evita la creación de objetos
     * de esta clase desde otras clases.
     * @throws IllegalStateException si no se encuentra el archivo de configuración,
     *         ocurre un error al leerlo o faltan propiedades necesarias.
     */
    private Conexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Error Driver: " + e.getMessage());
        }

        Properties config = new Properties();

        try (InputStream in = getClass().getResourceAsStream(CONFIG_FILE)) {
            if (in == null) {
                throw new IllegalStateException(
                        "No se encontro " + CONFIG_FILE + " en el classpath. "
                        + "Copia db.properties.example como src/db.properties y ajusta los valores.");
            }

            config.load(in);

        } catch (IOException e) {
            throw new IllegalStateException("Error al leer " + CONFIG_FILE, e);
        }

        this.url = config.getProperty("db.url");
        this.user = config.getProperty("db.user");
        this.password = config.getProperty("db.password");

        if (url == null || user == null || password == null) {
            throw new IllegalStateException(
                    "Faltan propiedades (db.url, db.user, db.password) en " + CONFIG_FILE);
        }
    }

    /**
     * Obtiene la única instancia de la clase Conexion.
     * Si todavía no existe, crea una nueva instancia.
     * @return La instancia única de {@link Conexion}.
     */
    public static synchronized Conexion getInstancia() {
        if (instancia == null) {
            instancia = new Conexion();
        }

        return instancia;
    }

    /**
     * Establece una nueva conexión con la base de datos MySQL
     * utilizando los datos definidos en el archivo de configuración.
     * @return Una conexión activa a la base de datos.
     * @throws SQLException si ocurre un error al establecer la conexión.
     */
    public Connection conectar() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}

