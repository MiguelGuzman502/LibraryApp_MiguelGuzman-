
package org.libreria.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Proporciona métodos de utilidad relacionados con la seguridad
 * de la información del sistema de librería.
 * @author Miguel Guzman
 * @version 1.0.0
 */
public class SecurityUtil {

    /**
     * Genera un hash SHA-256 a partir de una contraseña.
     * La contraseña se convierte a bytes utilizando UTF-8
     * y posteriormente se transforma a una cadena hexadecimal.
     * @param password Contraseña que se desea convertir a hash.
     * @return Cadena hexadecimal que representa el hash SHA-256 de la contraseña.
     * @throws RuntimeException si el algoritmo SHA-256 no está disponible.
     */
    public static String hashSHA256(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(
                    password.getBytes(java.nio.charset.StandardCharsets.UTF_8)
            );

            StringBuilder hexString = new StringBuilder(2 * encodedhash.length);

            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);

                if (hex.length() == 1) {
                    hexString.append('0');
                }

                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(
                    "Error al encriptar la contraseña", e
            );
        }
    }
}
