package edu.upc.prop.clusterxx.domain;

import edu.upc.prop.clusterxx.domain.operations.Operation;

/**
 * Clase que representa una region de un kenken.
 * 
 * @author Guillem Nieto Ribo
 */

public class Region {
    /**
     * Array con las casillas de la region.
     */
    private Box[] boxes;

    /**
     * Kenken al que pertenece la region.
     */
    private Kenken kenken;

    /**
     * Operacion asociada a la region.
     */
    private Operation operation;

    /**
     * Resultado de la operacion con las casillas de la region.
     */
    private int resultOp;


    /* CREATORS */

    /**
     * Crea una region sin especificar resultado.
     * 
     * @param k Kenken asociado.
     * @param b Vector de casillas de la region.
     * @param op Operacion asociada.
     */
    public Region(Kenken k, Box[] b, Operation op) {
        if (op != null) {
            this.kenken = k;
            this.boxes = b;
            this.operation = op;
            this.resultOp = op.calculate(b);
        }
        else {
            this.kenken = k;
            this.boxes = b;
            this.operation = op;
            this.resultOp = boxes[0].getValue();
        }
    }

    /**
     * Crea una region especificando el resultado.
     * 
     * @param k Kenken asociado.
     * @param b Vector de casillas de la region.
     * @param op Operacion asociada.
     * @param r Resultado de la region.
     */
    public Region(Kenken k, Box[] b, Operation op, int r) {
        boxes = b;
        kenken = k;
        operation = op;
        resultOp = r;
    }

    /* GETTERS */

    /**
     * Comprueba que el resultado asociado es igual al resultado obtenido al aplicar la operacion sobre las casillas.
     * 
     * @return True si el resultado es el mismo, false en caso contrario.
     */
    public boolean check() {
        if (operation == null) {
            return resultOp == boxes[0].getValue();
        }
        else {
            return resultOp == operation.calculate(boxes);
        }
    }

    /**
     * Devuelve el resultado asociado a la region.
     * 
     * @return resultado asociado.
     */
    public int getResult() {
        return resultOp;
    }

    /**
     * Devuelve el array de casillas de la region.
     * 
     * @return casillas de la region.
     */
    public Box[] getBoxList() {
        return boxes;
    }

    /**
     * Devuelve el kenken asociado a la region.
     * 
     * @return kenken asociado.
     */
    public Kenken getKenken() {
        return kenken;
    }

    /**
     * Devuelve la operacion asociada a la region.
     * 
     * @return operacion asociada.
     */
    public Operation getOperation() {
        return operation;
    }

    /**
     * Devuelve el numero de casillas de la region.
     * 
     * @return tamaño de la region.
     */
    public int getSize() {
        return boxes.length;
    }

    /**
     * Indica si las casillas de la region estan completas o no.
     * 
     * @return true si estan completas, false en caso contrario.
     */
    public boolean isFull() {
        for(Box b : boxes) {
            if(b.isEmpty()) return false;
        }
        return true;
    }

    /* SETTERS */
}

/* Class created by Guillem Nieto */
