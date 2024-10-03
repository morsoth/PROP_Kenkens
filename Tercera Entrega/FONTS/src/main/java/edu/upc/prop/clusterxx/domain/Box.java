package edu.upc.prop.clusterxx.domain;

import java.util.*;

/**
 * Clase que representa una casilla de un kenken.
 * 
 * @author Guillem Nieto Ribo
 */

public class Box {
    /**
     * Kenken asociado a la casilla.
     */
    private Kenken kenken;

    /**
     * Region asociada.
     */
    private Region region;

    /**
     * Posicion de la casilla en el kenken.
     */
    private int x, y;

    /**
     * Numero que contiene la casilla.
     */
    private int value;

    /* CREATORS */

    /**
     * Creadora sin valor.
     * 
     * @param k Kenken asociado.
     * @param x Coordenada x.
     * @param y Coordenada y.
     */
    public Box (Kenken k, int x, int y) {
        this(k,x,y,-1);
    }

    /**
     * Creadora con valor.
     * 
     * @param k Kenken asociado.
     * @param x Coordenada x.
     * @param y Coordenada y.
     * @param v Valor asociado.
     */
    public Box (Kenken k, int x, int y, int v) {
        this.x = x;
        this.y = y;
        kenken = k;
        region = null;
        value = v;
    }

    /* GETTERS */

    /**
     * Retorna la coordenada x.
     * 
     * @return coordenada x.
     */
    public int getX() {
        return x;
    }

    /**
     * Retorna la coordenada y.
     * 
     * @return coordenada y.
     */
    public int getY() {
        return y;
    }

    /**
     * Retorna el valor de la casilla.
     * 
     * @return valor de la casilla.
     */
    public int getValue() {
        return value;
    }

    /**
     * Devuelve la region asociada.
     * 
     * @return region asociada.
     */
    public Region getRegion() {
        return region;
    }

    /**
     * Devuelve el kenken asociado.
     * 
     * @return kenken asociado.
     */
    public Kenken getKenken() {
        return kenken;
    }

    /**
     * Comprueba si la casilla esta vacia o no.
     * 
     * @return true si esta vacia, false en caso contrario.
     */
    public boolean isEmpty() {
        return value == -1;
    }

    /* SETTERS */

    /**
     * Introduce un nuevo valor a la casilla.
     * 
     * @param v nuevo valor de la casilla.
     */
    public void setValue(int v) {
        value = v;
    }

    /**
     * Borra el valor de la casilla.
     */
    public void clearValue() {
        value = -1;
    }

    /**
     * Asocia una region a la casilla.
     * 
     * @param r region a asociar.
     */
    public void setRegion(Region r) {
        region = r;
    }
}

/* Class created by Guillem Nieto */