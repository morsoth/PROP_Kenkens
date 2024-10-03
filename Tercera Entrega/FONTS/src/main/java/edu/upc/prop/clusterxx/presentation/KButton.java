package edu.upc.prop.clusterxx.presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Extensión de la clase JButton. Representa una casilla del Kenken.
 *
 * @see edu.upc.prop.clusterxx.presentation.MainView
 *
 * @author David Cañadas López
 */
public class KButton extends JButton {
    /**
     * Texto de región.
     */
    private String regionText = "";
    
    /**
     * Grosor por defecto de los bordes.
     */
    private int defaultBorder = 1;
    
    /**
     * Grosor de cada borde.
     */
    private int borders[] = {1,1,1,1};

    /**
     * Constructora por defecto.
     *
     * @param text Texto del botón
     * @param size Tamaño mínimo del botón
     */
    public KButton(String text, Dimension size) {
        super(text);
        setFont(new Font("Serif", Font.BOLD, 30));
        setHorizontalAlignment(JButton.CENTER);
        setVerticalAlignment(JButton.CENTER);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createMatteBorder(borders[0],
                                                  borders[1],
                                                  borders[2],
                                                  borders[3],
                                                  Color.BLACK));
        setMinimumSize(size);
        setMaximumSize(size);
        setPreferredSize(size);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Serif", Font.PLAIN, 12));
        g.setColor(Color.BLACK);
        g.drawString(regionText, 3, 13);
    }

    /**
     * Cambia el texto de región.
     *
     * @param text El nuevo texto de región
     */
    public void setRegionText(String text) {
        regionText = text;
        repaint();
    }

    /**
     * Actualiza el grosor de los bordes.
     *
     * @param top Grosor del borde superior
     * @param left Grosor del borde izquierdo
     * @param bottom Grosor del borde inferior
     * @param right Grosor del borde derecho
     */
    public void updateBorders (int top, int left, int bottom, int right) {
        borders[0] = top;
        borders[1] = left;
        borders[2] = bottom;
        borders[3] = right;
        setBorder(BorderFactory.createMatteBorder(borders[0],
                                                  borders[1],
                                                  borders[2],
                                                  borders[3],
                                                  Color.BLACK));
    }

    /**
     * Reinicia el grosor de los bordes.
     */
    public void resetBorders () {
        setBorder(BorderFactory.createMatteBorder(borders[0],
                                                  borders[1],
                                                  borders[2],
                                                  borders[3],
                                                  Color.BLACK));
    }
}
