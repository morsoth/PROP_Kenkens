package edu.upc.prop.clusterxx.presentation;

import edu.upc.prop.clusterxx.presentation.KenkenSelectionViewController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Vista de selección de Kenken
 *
 * <p>Implementa la interfaz gráfica con la que se pueden seleccionar Kenken
 * del sistema para poder jugarlos. También muestra la partida guardada
 * en la cuenta del usuario, si la hubiera.</p>
 *
 * @see edu.upc.prop.clusterxx.presentation.KenkenSelectionViewController
 *
 * @author David Cañadas López
 */
public class KenkenSelectionView extends JFrame {
    /**
     * Controlador de la vista.
     */
    private KenkenSelectionViewController control;
    
    /**
     * Panel principal de la vista.
     */
    private JPanel listPanel;

    /**
     * Constructora por defecto.
     *
     * @param control Controlador de la vista
     */
    public KenkenSelectionView (KenkenSelectionViewController control) {
        // Configuración del JFrame
        this.control = control;
        setTitle("KENKEN SELECTION");
        setSize(400, 400);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel para mostrar la lista
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        // Scroll para el panel de la lista
        JScrollPane scrollPane = new JScrollPane(listPanel);
        
        // Panel para el botón
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton backButton = new JButton("Back");
        backButton.setBackground(Color.WHITE);

        // Acción para el botón Volver
        backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    control.confirmSelectKenken(false);
                }
            });

        buttonPanel.add(backButton);

        // Añadir los paneles al JFrame
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Actualiza el listado de Kenken.
     *
     * @param ids El nuevo listado de Kenken
     */
    public void setKenkenList (String ids[]) {
        // Reiniciar el panel y redibujar
        listPanel.removeAll();
        listPanel.revalidate();
        listPanel.repaint();

        if (ids.length > 0) {
            // Añadir los IDs al panel
            for (String id : ids) {
                JButton kenkenButton = new JButton(id);
                kenkenButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JButton selectedButton = (JButton) e.getSource();
                            control.selectKenken(selectedButton.getText());
                            control.confirmSelectKenken(true);
                        }
                    });
                listPanel.add(kenkenButton);
            }
        } else {
            JLabel message = new JLabel("No Kenken data available.");
            listPanel.add(message);
        }
    }
}
