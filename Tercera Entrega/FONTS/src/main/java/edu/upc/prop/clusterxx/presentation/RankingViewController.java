package edu.upc.prop.clusterxx.presentation;

import java.util.ArrayList;
import java.util.Scanner;

import javax.print.attribute.DocAttribute;

import edu.upc.prop.clusterxx.PairSI;
import edu.upc.prop.clusterxx.exceptions.*;
import edu.upc.prop.clusterxx.presentation.PresentationController;
import edu.upc.prop.clusterxx.presentation.RankingView;


/**
 * Controlador de la vista ranking
 * 
 * Es el intermediario entre la vista que muestra el ranking y el controlador de presentacion.
 * Dispone de los metodos y estructuras de datos necesarias para que el usuario pueda consultar el ranking de jugadores.
 * 
 * @author Guillem Nieto Ribo
 */

public class RankingViewController {
    /**
     * Control de presentación.
     */
    private PresentationController PresentationCntrl;

    /**
     * Vista del ranking.
     */
    private RankingView rankingView;
    
    /**
     * Constructora por defecto.
     * 
     * @param pc controlador de presentación.
     */
    public RankingViewController (PresentationController pc) {
        PresentationCntrl = pc;
        rankingView = new RankingView(this);
    }

    /**
     * Establece la visibilidad de la vista.
     * 
     * @param show si se muestra o no.
     * @param ranking información a mostrar.
     */
    public void showView (boolean show, ArrayList<PairSI> ranking) {
        rankingView.setVisible(show);
        rankingView.setRanking(ranking);
    }

    /**
     * Función para salir del ranking i volver a la vista principal.
     */
    public void exitRanking() {
        rankingView.setVisible(false);
        PresentationCntrl.showMainView(true);
    }
}

/* Class created by Guillem Nieto */