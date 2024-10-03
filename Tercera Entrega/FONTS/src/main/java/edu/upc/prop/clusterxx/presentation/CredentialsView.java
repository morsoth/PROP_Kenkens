package edu.upc.prop.clusterxx.presentation;

import edu.upc.prop.clusterxx.presentation.CredentialsViewController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Vista de obtención de credenciales
 *
 * <p>Implementa la interfaz gráfica con la que el usuario puede crear una
 * cuenta en el sistema, o bien iniciar sesión en una cuenta exitente.</p>
 *
 * @see edu.upc.prop.clusterxx.presentation.CredentialsViewController
 *
 * @author David Cañadas López
 */
public class CredentialsView extends JFrame {
    /**
     * Controlador de la vista.
     */
    private CredentialsViewController control;
    
    /**
     * Campo de texto del nombre de usuario.
     */
    private JTextField nameField;
    
    /**
     * Campo de texto de la contraseña.
     */
    private JPasswordField passwordField;

    /**
     * Constructora por defecto.
     *
     * @param control Controlador de la vista
     */
    public CredentialsView (CredentialsViewController control) {
        this.control = control;
        setSize(300, 150);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // inicializar

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 2));

        JLabel nameLabel = new JLabel("Nombre:");
        nameField = new JTextField();

        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordField = new JPasswordField();

        mainPanel.add(nameLabel);
        mainPanel.add(nameField);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton acceptButton = new JButton("Aceptar");
        acceptButton.setBackground(Color.GREEN);
        JButton cancelButton = new JButton("Cancelar");
        cancelButton.setBackground(Color.RED);

        acceptButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed (ActionEvent e) {
                    String nombre = nameField.getText();
                    String contraseña = new String(passwordField.getPassword());
                    control.confirmCredentials(nombre, contraseña, true);
                }
            });

        cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed (ActionEvent e) {
                    control.confirmCredentials("", "", false);
                }
            });

        buttonPanel.add(acceptButton);
        buttonPanel.add(cancelButton);

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Limpar las credenciales que haya en los campos de texto.
     */
    public void clearCredentials () {
        nameField.setText("");
        passwordField.setText("");
    }

    /**
     * Cambia el título del frame para indicar registro.
     */
    public void setRegistration () {
        setTitle("REGISTRATION");
    }

    /**
     * Cambia el título del frame para indicar inicio de sesión.
     */
    public void setLogin () {
        setTitle("LOGIN");
    }
}
