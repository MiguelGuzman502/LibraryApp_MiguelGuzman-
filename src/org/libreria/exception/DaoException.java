package org.libreria.exception;

/**
 * Excepción de la capa de acceso a datos.
 * Se utiliza para informar errores que ocurren durante las operaciones
 * de acceso a la base de datos, como problemas de conexión, errores
 * relacionados con SQL o fallos en la ejecución de procedimientos almacenados.
 * Permite propagar los errores hacia las capas superiores de la aplicación
 * para que puedan ser tratados y mostrados de manera adecuada al usuario.
 * @author Miguel Guzman
 * @version 1.0.0
 */
public class DaoException extends RuntimeException {

    /**
     * Construye una nueva excepción de acceso a datos con el mensaje indicado.
     * @param mensaje Mensaje que describe el error ocurrido.
     */
    public DaoException(String mensaje) {
        super(mensaje);
    }

    /**
     * Construye una nueva excepción de acceso a datos con un mensaje
     * y la causa original del error.
     * @param mensaje Mensaje que describe el error ocurrido.
     * @param causa Excepción original que provocó el error.
     */
    public DaoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}