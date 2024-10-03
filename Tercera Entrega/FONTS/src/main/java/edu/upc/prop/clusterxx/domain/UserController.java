package edu.upc.prop.clusterxx.domain;

import edu.upc.prop.clusterxx.domain.*;
import edu.upc.prop.clusterxx.exceptions.*;
import edu.upc.prop.clusterxx.PairSI;

import java.util.ArrayList;

/**
 * Controlador de usuarios.
 * 
 * @see edu.upc.prop.clusterxx.domain.User
 * 
 * @author Pau Zaragoza Gallardo
 */
public class UserController {

    /**
     * Usuario actual.
     */
    private User user;

    /**
     * Ránking.
     */
    private Ranking ranking;

    /**
     * Constructora por defecto.
     * 
     * <p>Crea un usuario sin registrar.</p>
     */
    public UserController() {
        user = new User();
        ranking = new Ranking();
    }

    /**
     * Inicia la sesión del usuario especificado.
     * 
     * <p>Este método crea un usuario registrado.</p>
     * 
     * @param name Nombre del usuario
     * @param password Contraseña del usuario
     * @param points Puntos del usuario
     * @return true si todo sale correctamente
     */
    public boolean logIn(String name, String password, int points) {
        user = new User(name, password, points);

        return true;
    }

    /**
     * Cierra la sesión del usuario actual.
     * 
     * <p>Este método crea un usuario no registrado.</p>
     * 
     * @return true si todo sale correctamente
     */
    public boolean logOut() {
        user = new User();

        return true;
    }

    /**
     * Actualiza los puntos de un usuario.
     * 
     * @return true si todo sale correctamente
     */
    public boolean updatePoints(int points) {
        user.updatePoints(points);

        ranking.updatePoints(user.getName(), points);
        ranking.sort();

        return true;
    }

    /**
     * Inicializa el ránking con los usuarios del sistema.
     * 
     * @return true si todo sale correctamente
     */
    public boolean initializeRanking(ArrayList<PairSI> usersData) {
        for (PairSI user : usersData) {
            ranking.add(user.getFirst(), user.getSecond());
        }
        ranking.sort();

        return true;
    }

    /**
     * Añade un nuevo usuairo al ránking.
     * 
     * @return true si todo sale correctamente
     */
    public boolean addUserRanking(PairSI newUser) {
        ranking.add(newUser.getFirst(), newUser.getSecond());
        ranking.sort();

        return true;
    }

    /**
     * Devuelve si el usuario está registrado en el sistema.
     * 
     * @return true si el usuario esta registrado, false en caso contrario
     */
    public boolean sessionActive() {
        return (user != null && user.isRegistered());
    }

    /**
     * Devuelve el nombre del usuario actual.
     * 
     * @return Nombre del usuario actual
     */
    public String getUserName() {
        return user.getName();
    }

    /**
     * Devuelve la contraseña del usuario actual.
     * 
     * @return Contraseña del usuario actual
     */
    public String getUserPassword() {
        return user.getPassword();
    }

    /**
     * Devuelve los puntos del usuario actual.
     * 
     * @return Puntos del usuario actual
     */
    public int getUserPoints() {
        return user.getPoints();
    }

    /**
     * Devuelve la información del ránking.
     * 
     * @return ArrayList de PairSI con los usuarios y sus puntos en el ránking.
     */
    public ArrayList<PairSI> getRanking() {
        return ranking.getRanking();
    }
}

// Class created by Pau Zaragoza Gallardo