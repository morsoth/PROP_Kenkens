package edu.upc.prop.clusterxx.domain;

import java.util.*;

public class Box {
    private Kenken kenken;
    private Region region;
    private int x, y;
    private int value;
    private Set<Integer> possibleValues;

    /* CREATORS */
    public Box (Kenken k, int x, int y) {
        this(k,x,y,-1);
    }

    public Box (Kenken k, int x, int y, int v) {
        this.x = x;
        this.y = y;
        kenken = k;
        region = null;
        value = v;
        possibleValues = new HashSet<Integer>();
    }

    /* GETTERS */
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getValue() {
        return value;
    }

    public Set<Integer> getPossibleValues() {
        return possibleValues;
    }

    public Region getRegion() {
        return region;
    }

    public Kenken getKenken() {
        return kenken;
    }

    public boolean isEmpty() {
        return value == -1;
    }

    /* SETTERS */
    public boolean setValue(int v) {
        value = v;
        return true;
    }

    public boolean clearValue() {
        value = -1;
        return true;
    }

    public boolean setPossibleValue(int v) {
        possibleValues.add(v);
        return true;
    }

    public boolean removePossibleValue(int v) {
        possibleValues.remove(v);
        return true;
    }

    public boolean setRegion(Region r) {
        region = r;
        return true;
    }

    public boolean removeRegion() {
        if (region == null) return false;
        region = null;
        return true;
    }
}

/* Class created by Guillem Nieto */