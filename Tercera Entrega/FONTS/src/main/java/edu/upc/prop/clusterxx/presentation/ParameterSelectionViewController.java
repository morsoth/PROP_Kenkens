package edu.upc.prop.clusterxx.presentation;

import java.util.Arrays;

import javax.print.attribute.DocAttribute;

import edu.upc.prop.clusterxx.exceptions.*;
import edu.upc.prop.clusterxx.presentation.PresentationController;
import edu.upc.prop.clusterxx.presentation.ParameterSelectionView;

/**
 * Controlador de la vista donde se seleccionan los parametros para la creacion/generacion de kenkens.
 * 
 * Es el intermediario entre la vista de seleccionar parametros y el controlador de presentacion.
 * Dispone de los metodos y estructuras de datos necesarias para que el usuario pueda personalizar su
 * creacion de kenkens (creacion manual o automatica, tamaño, operaciones permitidas).
 * 
 * @author Guillem Nieto Ribo
 */

public class ParameterSelectionViewController {
    /**
     * Controlador de presentación.
     */
    private PresentationController PresentationCntrl;

    /**
     * Vista de selección de parametros.
     */
    private ParameterSelectionView parameterSelectionView;

    /**
     * Tamaño seleccionado.
     */
    private int size;

    /**
     * Tipo de creación.
     */
    private CreationType type;

    /**
     * Vector con las operaciones permitidas en la creación automática.
     */
    private boolean[] permitted_op;

    /**
     * Los diferentes tipos de creación de kenkens.
     */
    public enum CreationType {
        MANUAL,
        AUTO
    }
    
    /**
     * Creadora por defecto.
     * 
     * @param pc Controlador de presentación.
     */
    public ParameterSelectionViewController (PresentationController pc) {
        PresentationCntrl = pc;
        parameterSelectionView = new ParameterSelectionView(this);
        permitted_op = new boolean[6];
        Arrays.fill(permitted_op, true);
    }

    /**
     * Establece la visibilidad de la vista.
     * 
     * @param show si se muestra o no.
     */
    public void showView (boolean show) {
        parameterSelectionView.setVisible(show);
    }
    
    /**
     * Se guarda el tamaño seleccionado.
     * 
     * @param size tamaño seleccionado.
     */
    public void selectKenkenSize(int size) {
        this.size = size;
    }

    /**
     * Selección del tipo de creación.
     * 
     * @param auto selección automática o en caso contrario manual.
     */
    public void selectGenerate(boolean auto) {
        if (auto) type = CreationType.AUTO;
        else type = CreationType.MANUAL;
    }

    /**
     * Se actualiza la selección de operaciones permitidas.
     * 
     * @param op operación a actualizar el estado.
     */
    public void toggleOperation(char op) {
        int count = 0;
        int index = -1;
        for (int i = 0; i < permitted_op.length; ++i) {
            if (permitted_op[i]) ++count;
        }

        switch (op) {
            case '+':
                index = 0;
                break;

            case '-':
                index = 1;
                break;

            case 'x':
                index = 2;
                break;

            case '/':
                index = 3;
                break;

            case '%':
                index = 4;
                break;

            case '^':
                index = 5;
                break;

            default:
                //error
                break;
        }
        if (!permitted_op[index] || count > 2) {
            permitted_op[index] = !permitted_op[index];
            parameterSelectionView.setSelected(op,permitted_op[index]);
        }
        else PresentationCntrl.showWarning("You must leave at least 2 permitted operations.");
    }

    /**
     * Confirmación de los parámetros seleccionados.
     * 
     * @param confirm si se quiere seguir adelante o no.
     */
    public void confirmSelectionParameters(boolean confirm) {
        if (confirm) {
            switch (type) {
                case MANUAL:
                    PresentationCntrl.startKenkenCreation(size);
                    break;
            
                case AUTO:
                    PresentationCntrl.generateKenken(size, permitted_op);
                    break;
                
                default: break;
            }
            PresentationCntrl.showMainView(true);
            this.showView(false);
        }
        else {
            PresentationCntrl.showMainView(true);
            this.showView(false);
        }
    }
}

/* Class created by Guillem Nieto */