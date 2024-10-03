package edu.upc.prop.clusterxx.presentation;

import edu.upc.prop.clusterxx.presentation.RankingViewController;
import edu.upc.prop.clusterxx.PairSI;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Vista de ranking
 *
 * Implementa la interfaz gráfica donde se muestran las cuentas de usuarios con
 * la mayor puntuación en partidas clasificatorias.
 *
 * @see edu.upc.prop.clusterxx.presentation.RankingViewController
 *
 * @author David Cañadas López
 */
public class RankingView extends JFrame {
    /**
     * Controlador de la vista.
     */
    private RankingViewController control;

    /**
     * Parejas nombre usuario-puntos del ranking.
     */
    private ArrayList<PairSI> ranking;

    /**
     * Panel principal de la vista.
     */
    private JPanel rankingPanel; 
    
    /**
     * Constructora por defecto.
     *
     * @param control Controlador de la vista
     */
    public RankingView (RankingViewController control) {
        // Configuración del JFrame
        this.control = control;
        setTitle("RANKING");
        setSize(300, 300);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel para mostrar el ranking
        rankingPanel = new JPanel();
        rankingPanel.setLayout(new BoxLayout(rankingPanel, BoxLayout.Y_AXIS));

        // Scroll para el panel de ranking
        JScrollPane scrollPane = new JScrollPane(rankingPanel);
        
        // Panel para el botón
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton backButton = new JButton("Back");
        backButton.setBackground(Color.WHITE);

        // Acción para el botón Volver
        backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    control.exitRanking();
                }
            });

        buttonPanel.add(backButton);

        // Añadir los paneles al JFrame
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Actualiza el ranking.
     *
     * @param ranking El nuevo listado de parejas nombre usuario-puntos
     */
    public void setRanking (ArrayList<PairSI> ranking) {
        this.ranking = ranking;

        rankingPanel.removeAll();
        rankingPanel.revalidate();
        rankingPanel.repaint();

        if (ranking != null) {
            // Añadir las parejas nombre-puntuación al panel
            int i = 1;
            for (PairSI pair : ranking) {
                JLabel rankingLabel = new JLabel(String.valueOf(i) + ". " + pair.getFirst() + " - " + pair.getSecond());
                rankingPanel.add(rankingLabel);
                ++i;
            }
        } else {
            JLabel message = new JLabel("No information available");
            rankingPanel.add(message);
        }
    }
}
