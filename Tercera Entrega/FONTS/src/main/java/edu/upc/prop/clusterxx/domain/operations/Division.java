package edu.upc.prop.clusterxx.domain.operations;

import edu.upc.prop.clusterxx.domain.Box;

/**
 * Clase que representa la operación aritmética División.
 * 
 * @see edu.upc.prop.clusterxx.domain.operations.Operation
 * @see edu.upc.prop.clusterxx.domain.Box
 * 
 * @author Pau Zaragoza Gallardo
 */
public class Division extends Operation {
    /**
     * Constructora por defecto.
     * 
     * <p>Inicializa una nueva instancia de {@code Division} llamando al 
     * constructor de la superclase {@code Operation}.</p>
     */
    public Division() {
        super();
    }
    
    /**
     * Divide los valores de las casillas.
     * 
     * <p>Este método toma un array de objetos {@code Box}, obtiene el valor 
     * de cada uno y retorna la disivión de los valores. Sólo se puede realizar
     * la operación sobre 2 casillas. Siempre divide el número mas grade entre
     * el mas pequeño.</p>
     * 
     * @param boxes Array de {@code Box} sobre los cuales se realizará la operación
     * @return Resultado de la división si es posible, -1 si no es posible
     */
    @Override 
    public int calculate(Box[] boxes) {
        if (boxes.length != 2) return -1;

        int bigNum = Math.max(boxes[0].getValue(), boxes[1].getValue());
        int smallNum = Math.min(boxes[0].getValue(), boxes[1].getValue());

        if ((bigNum % smallNum) != 0) return -1;

        int result = bigNum / smallNum;
        return result;
    }

    /**
     * 
     * 
     * @return 
     */
    @Override 
    public int getOperationId() {
        return 4;
    }
}

// Class created by Pau Zaragoza Gallardo