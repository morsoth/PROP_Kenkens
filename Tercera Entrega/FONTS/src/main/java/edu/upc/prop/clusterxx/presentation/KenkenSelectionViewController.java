package edu.upc.prop.clusterxx.presentation;

import java.util.Scanner;

import javax.print.attribute.DocAttribute;

import edu.upc.prop.clusterxx.exceptions.*;
import edu.upc.prop.clusterxx.presentation.PresentationController;
import edu.upc.prop.clusterxx.presentation.KenkenSelectionView;

/**
 * Controlador de la vista de seleccion de kenken
 * 
 * Es el intermediario entre la vista de seleccion de kenken y el controlador de presentacion.
 * Dispone de los metodos y estructuras de datos necesarias para las funciones relacionadas con
 * la seleccion de un kenken ya creado para jugar y para la reanudacion de una partida.
 * 
 * @author Guillem Nieto Ribo
 */

public class KenkenSelectionViewController {
    /**
     * Controlador de presentación.
     */
    private PresentationController PresentationCntrl;

    /**
     * Vista de selección de kenkens.
     */
    private KenkenSelectionView kenkenSelectionView;

    /**
     * Id del kenken seleccionado para guardar.
     */
    private String kenkenSelected;
    
    /**
     * Creadora por defecto.
     * @param pc
     */
    public KenkenSelectionViewController (PresentationController pc) {
        PresentationCntrl = pc;
        kenkenSelectionView = new KenkenSelectionView(this);
    }

    /**
     * Establece la visibilidad de la vista.
     * 
     * @param show si se muestra o no.
     * @param kenkens listado de kenkens a mostrar.
     */
    public void showView (boolean show, String[] kenkens) {
        kenkenSelectionView.setVisible(show);
        kenkenSelectionView.setKenkenList(kenkens);
    }

    /**
     * Selecciona un kenken de la lista.
     * 
     * @param id id del kenken seleccionado.
     */
    public void selectKenken (String id) {
        this.kenkenSelected = id;
    }

    /**
     * Confirma la selección del kenken.
     * 
     * @param confirm si se quiere seguir adelante o no.
     */
    public void confirmSelectKenken(boolean confirm) {
        if (confirm) PresentationCntrl.loadKenken(this.kenkenSelected);
        kenkenSelectionView.setVisible(false);
        PresentationCntrl.showMainView(true);
    }
}

/* Class created by Guillem Nieto */