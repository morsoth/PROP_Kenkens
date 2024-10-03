package edu.upc.prop.clusterxx.presentation;

import edu.upc.prop.clusterxx.presentation.ImportKenkenViewController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.File;

/**
 * Vista de importación
 * 
 * <p>Implementa la interfaz gráfica donde se selecciona un fichero que
 * contiene los datos necesarios para generar un Kenken y poderlo jugar.</p>
 *
 * @see edu.upc.prop.clusterxx.presentation.ImportKenkenViewController
 *
 * @author David Cañadas López
 */
public class ImportKenkenView extends JFrame {
    /**
     * Controlador de la vista.
     */
    private ImportKenkenViewController control;
    
    /**
     * Label que contiene el path del fichero a cargar.
     */
    private static JLabel pathTitle = new JLabel("N/A");
        
    /**
     * Botón para aceptar la selección.
     */
    private JButton acceptButton;
    
    /**
     * Constructora por defecto.
     *
     * @param control Controlador de la vista
     */
    public ImportKenkenView (ImportKenkenViewController control) {
        super("IMPORT KENKEN");
        this.control = control;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(false);
        setFocusable(true);
        setResizable(false);
        setSize(500,200);

        // inicializar
	
        JLabel infoTitle = new JLabel("File to load is:");
        infoTitle.setFont(new Font("Serif", Font.BOLD, 14));
        infoTitle.setHorizontalAlignment(JButton.CENTER);
        infoTitle.setVerticalAlignment(JButton.CENTER);
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
        infoPanel.add(Box.createHorizontalStrut(20));
        infoPanel.add(infoTitle);
        infoPanel.add(Box.createHorizontalGlue());

        pathTitle.setFont(new Font("Serif", Font.PLAIN, 14));
        pathTitle.setHorizontalAlignment(JButton.CENTER);
        pathTitle.setVerticalAlignment(JButton.CENTER);
        pathTitle.setBorder(BorderFactory.createLineBorder(Color.black));
        JPanel pathPanel = new JPanel();
        pathPanel.setLayout(new BoxLayout(pathPanel, BoxLayout.X_AXIS));
        pathPanel.add(Box.createHorizontalStrut(20));
        pathPanel.add(pathTitle);
        pathPanel.add(Box.createHorizontalGlue());
	
        JPanel searchPanel = makeSearchPanel(this);
        JPanel confirmPanel = makeConfirmPanel();
        acceptButton.setEnabled(false);

        JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.Y_AXIS));
        selectionPanel.add(Box.createVerticalStrut(10));
        selectionPanel.add(infoPanel);
        selectionPanel.add(Box.createVerticalStrut(10));
        selectionPanel.add(pathPanel);
        selectionPanel.add(searchPanel);
        selectionPanel.add(confirmPanel);
        add(selectionPanel);

        control.selectPath("");
    }
    
    /**
     * Construir el panel de búsqueda.
     *
     * @param frame El frame de la vista
     * @return el panel de la vista, configurado
     */
    private JPanel makeSearchPanel (JFrame frame) {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	
        JButton searchButton = new JButton("Explore...");
        searchButton.setFont(new Font("Serif", Font.BOLD, 14));
        searchButton.setHorizontalAlignment(JButton.CENTER);
        searchButton.setVerticalAlignment(JButton.CENTER);
        searchButton.setBackground(Color.WHITE);
        searchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed (ActionEvent event) {
                    FileDialog dialog = new FileDialog(frame, "Chose a file to load", FileDialog.LOAD);
                    String file, filePath, fileAbsPath;
                    dialog.setFile("*.ken");
                    dialog.setVisible(true);
                    file = dialog.getFile();
                    if (file != null) {
                        filePath = dialog.getDirectory() + file;
                        fileAbsPath = new File(filePath).getAbsolutePath();
                        pathTitle.setText(fileAbsPath);
                        if (file.endsWith(".ken")) {
                            control.selectPath(fileAbsPath);
                            acceptButton.setEnabled(true);
                        }
                        else acceptButton.setEnabled(false);
                    }
                }
            });
        searchPanel.add(searchButton);

        return searchPanel;
    }
    
    /**
     * Construir el panel de confirmación.
     *
     * @return el panel de la vista, configurado
     */
    private JPanel makeConfirmPanel () {
        JPanel confirmPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        acceptButton = new JButton("Accept");
        acceptButton.setFont(new Font("Serif", Font.BOLD, 14));
        acceptButton.setHorizontalAlignment(JButton.CENTER);
        acceptButton.setVerticalAlignment(JButton.CENTER);
        acceptButton.setBackground(Color.GREEN);
        acceptButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed (ActionEvent event) {
                    control.confirmSelectedPath(true);
                }
            });
        confirmPanel.add(acceptButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Serif", Font.BOLD, 14));
        cancelButton.setHorizontalAlignment(JButton.CENTER);
        cancelButton.setVerticalAlignment(JButton.CENTER);
        cancelButton.setBackground(Color.RED);
        cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed (ActionEvent event) {
                    control.confirmSelectedPath(false);
                }
            });
        confirmPanel.add(cancelButton);

        return confirmPanel;
    }
}
