package edu.upc.prop.clusterxx.domain.operations;

import edu.upc.prop.clusterxx.domain.Box;

/**
 * Clase que representa la operación aritmética Suma.
 * 
 * @see edu.upc.prop.clusterxx.domain.operations.Operation
 * @see edu.upc.prop.clusterxx.domain.Box
 * 
 * @author Pau Zaragoza Gallardo
 */
public class Addition extends Operation {
    /**
     * Constructora por defecto.
     * 
     * <p>Inicializa una nueva instancia de {@code Addition} llamando al 
     * constructor de la superclase {@code Operation}.</p>
     */
    public Addition() {
        super();
    }
    
    /**
     * Suma los valores de las casillas.
     * 
     * <p>Este método toma un array de objetos {@code Box}, obtiene el valor 
     * de cada uno y retorna la suma de todos estos valores.</p>
     * 
     * @param boxes Array de {@code Box} sobre los cuales se realizará la operación
     * @return Resultado de la suma de los valores de las casillas
     */
    @Override 
    public int calculate(Box[] boxes) {
        int result = 0;
        for (int i = 0; i < boxes.length; ++i) {
            result += boxes[i].getValue();
        }
        return result;
    }

    /**
     * 
     * 
     * @return 
     */
    @Override 
    public int getOperationId() {
        return 1;
    }
}

// Class created by Pau Zaragoza Gallardo