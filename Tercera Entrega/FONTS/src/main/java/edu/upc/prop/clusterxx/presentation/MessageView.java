package edu.upc.prop.clusterxx.presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Vista de mensajes
 *
 * <p>Esta vista implementa métodos sencillos que ofrecen al programa
 * una interfaz general para mostrar avisos gráficos al usuario.</p>
 *
 * @author David Cañadas López
 */
public class MessageView extends JFrame {
    /**
     * Constructora por defecto.
     */
    public MessageView () {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(false);
        setFocusable(false);
        setResizable(false);
    }

    /**
     * Mostrar un mensaje de información al usuario.
     *
     * @param message Texto del mensaje
     */
    public void showInfo (String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    /**
     * Mostrar un mensaje de aviso al usuario.
     *
     * @param message Texto del mensaje
     */
    public void showWarning (String message) {
        JOptionPane.showMessageDialog(this, message, "WARNING",
                                      JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Mostrar un mensaje de error al usuario.
     *
     * @param message Texto del mensaje
     */
    public void showError (String message) {
        JOptionPane.showMessageDialog(this, message, "ERROR",
                                      JOptionPane.ERROR_MESSAGE);
    }
}
