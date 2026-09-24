
package org.libreria.model;

/**
 * Representa un autor dentro del sistema de librería.
 * Contiene la información del autor, incluyendo su nombre,
 * apellido, nacionalidad y biografía.
 * * @author Miguel Guzman
 * @version 1.0.0
 */
public class Autor {

    private int idAutor;
    private String nombreAutor;
    private String apellidoAutor;
    private String nacionalidad;
    private String biografia;

    /**
     * Crea un objeto Autor sin establecer sus valores.
     */
    public Autor() {
    }

    /**
     * Crea un objeto Autor con la información proporcionada.
     * @param idAutor Identificador del autor.
     * @param nombreAutor Nombre del autor.
     * @param apellidoAutor Apellido del autor.
     * @param nacionalidad Nacionalidad del autor.
     * @param biografia Información biográfica del autor.
     */
    public Autor(int idAutor, String nombreAutor, String apellidoAutor,
                 String nacionalidad, String biografia) {
        this.idAutor = idAutor;
        this.nombreAutor = nombreAutor;
        this.apellidoAutor = apellidoAutor;
        this.nacionalidad = nacionalidad;
        this.biografia = biografia;
    }

    /**
     * Obtiene el identificador del autor.
     * @return El identificador del autor.
     */
    public int getIdAutor() {
        return idAutor;
    }

    /**
     * Modifica el identificador del autor.
     * @param idAutor Nuevo identificador del autor.
     */
    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    /**
     * Obtiene el nombre del autor.
     * @return El nombre del autor.
     */
    public String getNombreAutor() {
        return nombreAutor;
    }

    /**
     * Modifica el nombre del autor.
     * @param nombreAutor Nuevo nombre del autor.
     */
    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    /**
     * Obtiene el apellido del autor.
     * @return El apellido del autor.
     */
    public String getApellidoAutor() {
        return apellidoAutor;
    }

    /**
     * Modifica el apellido del autor.
     * @param apellidoAutor Nuevo apellido del autor.
     */
    public void setApellidoAutor(String apellidoAutor) {
        this.apellidoAutor = apellidoAutor;
    }

    /**
     * Obtiene la nacionalidad del autor.
     * @return La nacionalidad del autor.
     */
    public String getNacionalidad() {
        return nacionalidad;
    }

    /**
     * Modifica la nacionalidad del autor.
     * @param nacionalidad Nueva nacionalidad del autor.
     */
    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    /**
     * Obtiene la biografía del autor.
     * @return La biografía del autor.
     */
    public String getBiografia() {
        return biografia;
    }

    /**
     * Modifica la biografía del autor.
     * @param biografia Nueva biografía del autor.
     */
    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    /**
     * Devuelve el nombre completo del autor como representación textual del objeto.
     * @return El nombre y apellido del autor.
     */
    @Override
    public String toString() {
        return nombreAutor + " " + apellidoAutor;
    }
}

