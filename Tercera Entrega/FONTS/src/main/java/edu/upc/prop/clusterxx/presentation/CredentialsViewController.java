package edu.upc.prop.clusterxx.presentation;

import java.util.Scanner;

import javax.print.attribute.DocAttribute;

import edu.upc.prop.clusterxx.exceptions.*;
import edu.upc.prop.clusterxx.presentation.PresentationController;
import edu.upc.prop.clusterxx.presentation.CredentialsView;

/**
 * Controlador de la vista de registro/inicio de sesion.
 * 
 * Es el intermediario entre la vista registro/inicio de sesion y el controlador de presentacion.
 * Dispone de los metodos y estructuras de datos necesarias para que un usuario pueda iniciar sesión
 * o registrarse si no lo esta.
 * 
 * @author Guillem Nieto Ribo
 */

public class CredentialsViewController {
    /**
     * Controlador de presentación.
     */
    private PresentationController PresentationCntrl;

    /**
     * Vista de registrar / iniciar sesión.
     */
    private CredentialsView credentialsView;

    /**
     * Si se quiere registrar o en caso contrario iniciar sesión.
     */
    private boolean register;
    
    /**
     * Comprueba que las credenciales introducidas cumplan los parámetros establecidos.
     * 
     * @param username nombre de ususario.
     * @param password contraseña.
     * @return si las credenciales son correctas o no.
     */
    private boolean correctCredentials(String username, String password) {
        if (username == "" || password == "") return false;
        else if (password.length() < 8) return false;
        else return true;
    }

    /**
     * Creadora por defecto.
     * 
     * @param pc control de presentación.
     */
    public CredentialsViewController (PresentationController pc) {
        PresentationCntrl = pc;
        credentialsView = new CredentialsView(this);
    }

    /**
     * Establece la visibilidad de la vista.
     * 
     * @param show si se quiere mostrar o no.
     */
    public void showView (boolean show) {
        credentialsView.setVisible(show);
    }

    /**
     * Empezar con el registro.
     */
    public void startRegistration() {
        credentialsView.setRegistration();
        register = true;
    }

    /**
     * Empezar con el inicio de sesión.
     */
    public void startLogIn() {
        credentialsView.setLogin();
        register = false;
    }

    /**
     * Se confirma el registro / inicio de sesión.
     * 
     * @param name nombre de usuario.
     * @param pwd contraseña.
     * @param confirm si se quiere seguir o no.
     */
    public void confirmCredentials (String name, String pwd, boolean confirm) {
        if (confirm) {
            if (correctCredentials(name, pwd)) {
                credentialsView.clearCredentials();
                boolean ret;
                if (register) {
                    ret = PresentationCntrl.createAccount(name, pwd);
                
                    if (ret) {
                        this.showView(false);
                        PresentationCntrl.showMainView(true);
                    }
                    else PresentationCntrl.showError("User already exists.");
                }
                else {
                    ret = PresentationCntrl.logIn(name, pwd);
                
                    if (ret) {
                        this.showView(false);
                        PresentationCntrl.showMainView(true);
                    }
                    else PresentationCntrl.showError("User doesn't exists.");
                }
            }
            else PresentationCntrl.showError("Password must be at least 8 characters.");
        }
        else {
            this.showView(false);
            PresentationCntrl.showMainView(true);
        }
    }
}

/* Class created by Guillem Nieto */