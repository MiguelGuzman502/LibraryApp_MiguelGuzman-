package org.libreria.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Excepción utilizada para controlar errores relacionados con la validación
 * de datos ingresados en el sistema.
 * Proporciona métodos estáticos para validar campos vacíos, valores nulos,
 * números, decimales, correos electrónicos, fechas, longitudes y valores positivos.
 * @author Miguel Guzman
 * @version 1.0.0
 */
public class ValidacionException extends Exception {

    /**
     * Objeto utilizado para registrar mensajes relacionados con las validaciones.
     */
    private static final Logger log = Logger.getLogger(ValidacionException.class.getName());

    /**
     * Construye una nueva excepción de validación con el mensaje indicado.
     * @param mensaje Mensaje que describe el motivo de la excepción.
     */
    public ValidacionException(String mensaje) {
        super(mensaje);
    }

    /**
     * Valida que una cadena no sea nula ni esté vacía.
     * @param valor Valor que se desea validar.
     * @param nombreCampo Nombre del campo que se está validando.
     * @throws ValidacionException Si el valor es nulo o está vacío.
     */
    public static void validarNoVacio(String valor, String nombreCampo)
            throws ValidacionException {
        if (valor == null || valor.trim().isEmpty()) {
            throw new ValidacionException(
                    "El campo " + nombreCampo + " no puede estar vacío.");
        }

        log.log(Level.WARNING, "No puede estar vacío el campo: ", nombreCampo);
    }

    /**
     * Valida que dos valores sean iguales.
     * @param a Primer valor que se desea comparar.
     * @param b Segundo valor que se desea comparar.
     * @param mensaje Mensaje que se mostrará si los valores no coinciden.
     * @throws ValidacionException Si los valores son diferentes.
     */
    public static void validarCoinciden(String a, String b, String mensaje)
            throws ValidacionException {
        if (!a.equals(b)) {
            throw new ValidacionException(mensaje);
        }
    }

    /**
     * Valida que una cadena tenga como mínimo una cantidad determinada de caracteres.
     * @param valor Valor que se desea validar.
     * @param min Longitud mínima requerida.
     * @param mensaje Mensaje que se mostrará si no se cumple la longitud mínima.
     * @throws ValidacionException Si la longitud del valor es menor al mínimo establecido.
     */
    public static void validarLongitudMinima(String valor, int min, String mensaje)
            throws ValidacionException {
        if (valor.length() < min) {
            throw new ValidacionException(mensaje);
        }
    }

    /**
     * Valida que un objeto no sea nulo.
     * @param obj Objeto que se desea validar.
     * @param mensaje Mensaje que se mostrará si el objeto es nulo.
     * @throws ValidacionException Si el objeto es nulo.
     */
    public static void validarNoNulo(Object obj, String mensaje)
            throws ValidacionException {
        if (obj == null) {
            throw new ValidacionException(mensaje);
        }
    }

    /**
     * Valida que una cadena represente un número entero válido.
     * @param valor Valor que se desea validar.
     * @param nombreCampo Nombre del campo que se está validando.
     * @throws ValidacionException Si el valor no representa un número válido.
     */
    public static void validarNumero(String valor, String nombreCampo)
            throws ValidacionException {
        try {
            Long.parseLong(valor.trim());
        } catch (NumberFormatException e) {
            throw new ValidacionException(
                    "El campo " + nombreCampo + " debe ser un número válido.");
        }
    }

    /**
     * Valida que una cadena tenga una longitud exacta.
     * @param valor Valor que se desea validar.
     * @param longitud Cantidad exacta de caracteres requerida.
     * @param mensaje Mensaje que se mostrará si la longitud no coincide.
     * @throws ValidacionException Si la longitud del valor no coincide con la indicada.
     */
    public static void validarLongitudExacta(String valor, int longitud, String mensaje)
            throws ValidacionException {
        if (valor.length() != longitud) {
            throw new ValidacionException(mensaje);
        }
    }

    /**
     * Valida que una cadena tenga un formato de correo electrónico válido.
     * @param valor Correo electrónico que se desea validar.
     * @param mensaje Mensaje que se mostrará si el formato no es válido.
     * @throws ValidacionException Si el correo electrónico no cumple con el formato esperado.
     */
    public static void validarFormatoEmail(String valor, String mensaje)
            throws ValidacionException {
        if (!valor.matches("[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+")) {
            throw new ValidacionException(mensaje);
        }
    }

    /**
     * Valida que una cadena represente un número decimal válido.
     * @param valor Valor que se desea validar.
     * @param nombreCampo Nombre del campo que se está validando.
     * @throws ValidacionException Si el valor no representa un número decimal válido.
     */
    public static void validarDecimal(String valor, String nombreCampo)
            throws ValidacionException {
        try {
            Double.parseDouble(valor.trim());
        } catch (NumberFormatException e) {
            throw new ValidacionException(
                    "El campo " + nombreCampo + " debe ser un número válido.");
        }
    }

    /**
     * Valida que una cadena tenga el formato de fecha yyyy-MM-dd.
     * @param valor Fecha que se desea validar.
     * @param mensaje Mensaje que se mostrará si el formato no es válido.
     * @throws ValidacionException Si la fecha no cumple con el formato establecido.
     */
    public static void validarFormatoFecha(String valor, String mensaje)
            throws ValidacionException {
        if (!valor.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new ValidacionException(mensaje);
        }
    }

    /**
     * Valida que una cadena represente un número entero positivo.
     * @param valor Valor que se desea validar.
     * @param nombreCampo Nombre del campo que se está validando.
     * @throws ValidacionException Si el valor no es un número válido o es menor o igual a cero.
     */
    public static void validarPositivo(String valor, String nombreCampo)
            throws ValidacionException {
        validarNumero(valor, nombreCampo);

        if (Long.parseLong(valor.trim()) <= 0) {
            throw new ValidacionException(
                    "El campo " + nombreCampo + " debe ser un número mayor que cero.");
        }
    }
}