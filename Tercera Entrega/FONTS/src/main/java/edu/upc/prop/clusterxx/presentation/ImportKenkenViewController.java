package edu.upc.prop.clusterxx.presentation;

import java.util.Scanner;

import javax.print.attribute.DocAttribute;

import edu.upc.prop.clusterxx.exceptions.*;
import edu.upc.prop.clusterxx.presentation.PresentationController;
import edu.upc.prop.clusterxx.presentation.ImportKenkenView;

/**
 * Controlador de la vista para importar kenkens
 * 
 * Es el intermediario entre la vista de importacion y el controlador de presentacion.
 * Dispone de los metodos y estructuras de datos necesarias para las funciones relacionadas con
 * importar kenkens y poder proporcionar el PATH hasta un fichero que contenga un kenken.
 * 
 * @author Guillem Nieto Ribo
 */

public class ImportKenkenViewController {
    /**
     * Controlador de presentación.
     */
    private PresentationController PresentationCntrl;

    /**
     * Vista para importar kenkens a la BD.
     */
    private ImportKenkenView importKenkenView;

    /**
     * Path hasta el fichero seleccionado.
     */
    private String filePath;
    
    /**
     * Creadora por defecto.
     * 
     * @param pc controlador de presentación.
     */
    public ImportKenkenViewController(PresentationController pc) {
        PresentationCntrl = pc;
        importKenkenView = new ImportKenkenView(this);
    }

    /**
     * Establecer la visibilidad de la vista.
     * 
     * @param show mostrar o no la vista.
     */
    public void showView (boolean show) {
        importKenkenView.setVisible(show);
    }

    /**
     * Se guarda el path al fichero seleccionado.
     * 
     * @param filePath path al fichero.
     */
    public void selectPath (String filePath) {
        this.filePath = filePath;
    }

    /**
     * Confirma la selección del fichero.
     * 
     * @param selected si se quiere seguir adelante o no.
     */
    public void confirmSelectedPath (boolean selected) {
        if (selected) PresentationCntrl.importKenken(filePath);
        this.showView(false);
        PresentationCntrl.showMainView(true);
    }
}

/* Class created by Guillem Nieto */