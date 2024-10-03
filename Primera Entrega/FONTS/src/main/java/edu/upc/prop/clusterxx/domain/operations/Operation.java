package edu.upc.prop.clusterxx.domain.operations;

import edu.upc.prop.clusterxx.domain.Box;

public abstract class Operation {
    public Operation() {}

    abstract public int calculate(Box[] boxes);
}

// Class created by Pau Zaragoza Gallardo