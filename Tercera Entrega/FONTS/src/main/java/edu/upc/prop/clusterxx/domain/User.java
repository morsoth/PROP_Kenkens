package edu.upc.prop.clusterxx.domain;

/**
 * Clase Usuario
 * @author Raúl Gilabert Gámez
 */
public class User {
    /**
     * Nombre de usuario
     */
    private String name;
    /**
     * Contraseña de usuario
     */
    private String password;
    /**
     * Puntos del usuario
     */
    private int points;

    /**
     * Constructor por defecto
     */
    public User() {
        this.name = "";
        this.password = "";
        this.points = 0;
    }

    /**
     * Constructor con nombre
     * @param name Nombre de usuario
     */
    public User(String name) {
        this.name = name;
        this.password = "";
        this.points = 0;
    }

    /**
     * Constructor con nombre y contraseña
     * @param name Nombre de usuario
     * @param password Contraseña de usuario
     */
    public User(String name, String password) {
        this.name = name;
        this.password = password;
        this.points = 0;
    }

    /**
     * Constructor con nombre, contraseña y puntos
     * @param name Nombre de usuario
     * @param password Contraseña de usuario
     * @param points Puntos del usuario
     */
    public User(String name, String password, int points) {
        this.name = name;
        this.password = password;
        if (points < 0)
            this.points = 0;
        else
            this.points = points;
    }

    /**
     * Obtiene el nombre de usuario
     * @return Nombre de usuario
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre de usuario
     * @param name Nombre de usuario
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Establece la contraseña de usuario
     * @param password Contraseña de usuario
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Comprueba si la contraseña es correcta
     * @param password Contraseña a comprobar
     * @return True si la contraseña es correcta, false en caso contrario
     */
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    /**
     * Obtiene los puntos del usuario
     * @return Puntos del usuario
     */
    public int getPoints() {
        return points;
    }

    /**
     * Obtiene la contraseña del usuario
     * @return Contraseña del usuario
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece los puntos del usuario
     * @param points Puntos del usuario
     */
    public void setPoints(int points) {
        if (points < 0)
            this.points = 0;
        else
            this.points = points;
    }

    /**
     * Actualiza los puntos del usuario
     * @param points Puntos a añadir
     */
    public void updatePoints(int points) {
        if (this.points + points < 0)
            this.points = 0;
        else
            this.points += points;
    }

    /**
     * Comprueba si el usuario está registrado
     * @return True si el usuario está registrado, false en caso contrario
     */
    public boolean isRegistered() {
        return !name.isEmpty();
    }
}
