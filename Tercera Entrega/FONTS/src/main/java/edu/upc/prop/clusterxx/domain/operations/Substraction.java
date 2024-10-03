package edu.upc.prop.clusterxx.domain.operations;

import edu.upc.prop.clusterxx.domain.Box;

/**
 * Clase que representa la operación aritmética Resta.
 * 
 * @see edu.upc.prop.clusterxx.domain.operations.Operation
 * @see edu.upc.prop.clusterxx.domain.Box
 * 
 * @author Pau Zaragoza Gallardo
 */
public class Substraction extends Operation {
    /**
     * Constructora por defecto.
     * 
     * <p>Inicializa una nueva instancia de {@code Power} llamando al 
     * constructor de la superclase {@code Operation}.</p>
     */
    public Substraction() {
        super();
    }
    
    /**
     * Calcula la potencia de los valores de las casillas.
     * 
     * <p>Este método toma un array de objetos {@code Box}, obtiene el valor 
     * de cada uno y retorna la resta de los valores. Sólo se puede realizar
     * la operación sobre 2 casillas. Siempre resta el número mas grade menos
     * el mas pequeño.</p>
     * 
     * @param boxes Array de {@code Box} sobre los cuales se realizará la operación
     * @return Resultado de la potencia si es posible, -1 si no es posible
     */
    @Override 
    public int calculate(Box[] boxes) {
        if (boxes.length != 2) return -1;

        int bigNum = Math.max(boxes[0].getValue(), boxes[1].getValue());
        int smallNum = Math.min(boxes[0].getValue(), boxes[1].getValue());
        int result = bigNum - smallNum;
        return result;
    }

    /**
     * 
     * 
     * @return 
     */
    @Override 
    public int getOperationId() {
        return 2;
    }
}

// Class created by Pau Zaragoza Gallardo