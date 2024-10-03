package edu.upc.prop.clusterxx.domain.operations;

import edu.upc.prop.clusterxx.domain.Box;

/**
 * Clase abstracta que representa una operación aritmética.
 * 
 * @see edu.upc.prop.clusterxx.domain.Box
 * 
 * @author Pau Zaragoza Gallardo
 */
public abstract class Operation {
    /**
     * Constructora por defecto.
     */
    public Operation() {}
    
    /**
     * Método abstracto que debe ser implementado por las clases que extiendan {@code Operation}.
     * Define la lógica de la operación a realizar sobre el array de casillas.
     * 
     * @param boxes Array de {@code Box} sobre los cuales se realizará la operación
     * @return Resultado de la operación
     */
    abstract public int calculate(Box[] boxes);

    /**
     * 
     * 
     * @return 
     */
    abstract public int getOperationId();
}

// Class created by Pau Zaragoza Gallardo